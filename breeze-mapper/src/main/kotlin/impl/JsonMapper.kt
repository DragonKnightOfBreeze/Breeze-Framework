// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.mapper.impl

/*

实现思路：
map
* joinToString + when条件分支 + 递归转换（可行）
* 解释器模式

unmap
* split + when条件分支 + 递归转换（不支持多层结构）
* 解释器模式（比较复杂）

*/

import com.windea.breezeframework.core.domain.data.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.mapper.*
import java.lang.reflect.*
import java.time.temporal.*
import java.util.*

/**
 * Json映射器。
 */
class JsonMapper(
	val config: Config = Config.Default,
) : Mapper {
	constructor(configBlock: Config.Builder.() -> Unit) : this(Config.Builder().apply(configBlock).build())

	/**
	 * Json映射器的配置。
	 * @property indent 文本缩进。默认为`"  "`。
	 * @property lineSeparator 行分隔符。默认为`"\n"`。
	 * @property doubleQuoted 是否使用双引号括起。默认为`true`。
	 * @property unquoted 是否不使用任何引号括起。默认为`false`。
	 * @property trimSpaces 是否去除不必要的空格。默认为`false`。
	 * @property prettyFormat 是否使用良好的格式进行输出。默认为`false`。
	 */
	data class Config(
		val indent: String = "  ",
		val lineSeparator: String = "\n",
		val doubleQuoted: Boolean = true,
		val unquoted: Boolean = false,
		val trimSpaces: Boolean = false,
		val prettyFormat: Boolean = false,
	) : DataEntity {
		init {
			require(lineSeparator in validLineSeparators) { "Line Separator should be '\\n', '\\r' or '\\r\\n'." }
		}

		companion object {
			private val validLineSeparators = arrayOf("\n", "\r", "\r\n")

			@JvmStatic val Default = Config()
			@JvmStatic val PrettyFormat = Config(prettyFormat = true)
		}

		class Builder : DataBuilder<Config> {
			var indent: String = "  "
			var lineSeparator: String = "\n"
			var doubleQuoted: Boolean = true
			var unquoted: Boolean = false
			var trimSpaces: Boolean = false
			var prettyFormat: Boolean = false

			override fun build() = Config(indent, lineSeparator, doubleQuoted, unquoted, trimSpaces, prettyFormat)
		}
	}


	private val indent = config.indent
	private val quote = if(config.doubleQuoted) '\"' else '\''
	private val lineSeparator = config.lineSeparator
	private val separator = if(config.trimSpaces) ":" else ": "
	private val valueSeparator = if(config.prettyFormat) ",$lineSeparator" else if(config.trimSpaces) "," else ", "
	private val arrayPrefix = if(config.prettyFormat) "[$lineSeparator" else "["
	private val arraySuffix = if(config.prettyFormat) "$lineSeparator]" else "]"
	private val objectPrefix = if(config.prettyFormat) "{$lineSeparator" else "{"
	private val objectSuffix = if(config.prettyFormat) "$lineSeparator}" else "}"

	private fun String.shouldQuoted() = this.isEmpty() || this.first().isWhitespace() || this.last().isWhitespace()
	private fun String.doQuote() = if(!config.unquoted || this.shouldQuoted()) this.quote(quote) else this
	private fun String.doIndent() = if(config.prettyFormat) this.prependIndent(indent) else this


	override fun <T> map(data: T): String {
		return data.map0()
	}

	//支持的类型：
	//* null - json null
	//* Boolean - json boolean
	//* Number - json number
	//* CharSequence, Char, Temporal, Date - json string
	//* Array, Sequence, Iterable, Sequence, !ClosedRange - json array
	//* Map - json object
	//* else - json object

	private fun Any?.map0(): String {
		return when {
			this == null -> mapNull()
			this is Boolean -> mapBoolean()
			this is Number -> mapNumber()
			this is CharSequence || this is Char || this is Temporal || this is Date -> mapString()
			this is Array<*> -> mapArray()
			this is Iterable<*> && this !is ClosedRange<*> -> mapArray()
			this is Sequence<*> -> mapArray()
			this is Map<*, *> -> mapObject()
			else -> mapObject()
		}
	}

	private fun mapNull() = "null"

	private fun Boolean.mapBoolean() = this.toString()

	private fun Number.mapNumber() = this.toString()

	private fun Any.mapString() = this.toString().doQuote()

	private fun Array<*>.mapArray() = this.joinToString(valueSeparator) { it.map0() }.doIndent()
		.let { "$arrayPrefix$it$arraySuffix" }

	private fun Iterable<*>.mapArray() = this.joinToString(valueSeparator) { it.map0() }.doIndent()
		.let { "$arrayPrefix$it$arraySuffix" }

	private fun Sequence<*>.mapArray() = this.joinToString(valueSeparator) { it.map0() }.doIndent()
		.let { "$arrayPrefix$it$arraySuffix" }

	private fun Map<*, *>.mapObject() = this.joinToString(valueSeparator) { (k, v) -> "${k.mapKey()}$separator${v.mapValue()}" }.doIndent()
		.let { "$objectPrefix$it$objectSuffix" }

	private fun Any.mapObject() =
		ObjectMapper.map(this).mapObject()

	private fun Any?.mapKey() = this.toString().doQuote()

	private fun Any?.mapValue() = this.map0()


	override fun <T> unmap(string: String, type: Class<T>): T {
		return string.unmap0() as T
	}

	override fun <T> unmap(string: String, type: Type): T {
		return string.unmap0() as T
	}

	//支持的类型：
	//* null - json null
	//* Boolean - json boolean
	//* Number - json number
	//* CharSequence, Char, Temporal, Date - json string
	//* Array, Sequence, Iterable, Sequence, !ClosedRange - json array
	//* Map - json object
	//* else - json object

	private fun String.unmap0(): Any? {
		val string = this.trim()
		return when {
			string.startsWith("{") && string.contains("}") -> unmapJsonObject()
			string.startsWith("[") && string.contains("]") -> unmapJsonObject()
			string.startsWith("\"") || string.startsWith("\'") -> unmapJsonString()
			string.matches("[1-9.]+".toRegex()) -> unmapJsonNumber()
			string == "true" || string == "false" -> unmapJsonBoolean()
			string == "null" -> unmapJsonNull()
			else -> throw IllegalArgumentException()
		}
	}

	private fun unmapJsonNull() = null

	private fun String.unmapJsonBoolean() = this.toBoolean()

	private fun String.unmapJsonNumber(): Number = if(this.contains(".")) this.toDouble() else this.toInt()

	private fun String.unmapJsonString() = this.unquote()

	private fun String.unmapJsonObject() = this.split(",", "{", "}")
		.map { it.unmapJsonKey() to it.unmapJsonValue() }

	private fun String.unmapJsonArray() = this.split(",", "[", "]")
		.map { it.trim() }

	private fun String.unmapJsonKey() = this.substringBefore(":").trim().unquote()

	private fun String.unmapJsonValue() = this.substringAfter(":").trim().unmap0()
}
