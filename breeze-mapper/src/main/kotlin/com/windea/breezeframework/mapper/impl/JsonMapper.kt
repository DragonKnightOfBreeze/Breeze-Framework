package com.windea.breezeframework.mapper.impl

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.mapper.*
import java.lang.reflect.*
import java.time.temporal.*
import java.util.*

/**
 * Json映射器。
 */
class JsonMapper(
	val config:Config = Config.Default
) : Mapper {
	constructor(configBlock:Config.Builder.() -> Unit) : this(Config.Builder().apply(configBlock).build())

	/**
	 * Json映射器的配置。
	 * @property indent 文本缩进。默认为`"  "`。
	 * @property doubleQuoted 是否使用双引号括起。默认为`true`。
	 * @property unquoted 是否不使用任何引号括起。默认为`false`。
	 * @property trimSpaces 是否去除不必要的空格。默认为`false`。
	 * @property prettyFormat 是否使用良好的格式进行输出。默认为`false`。
	 */
	data class Config(
		val indent:String = "  ",
		val lineSeparator:String = "\n",
		val doubleQuoted:Boolean = true,
		val unquoted:Boolean = false,
		val trimSpaces:Boolean = false,
		val prettyFormat:Boolean = false
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
			var indent:String = "  "
			var lineSeparator:String = "\n"
			var doubleQuoted:Boolean = true
			var unquoted:Boolean = false
			var trimSpaces:Boolean = false
			var prettyFormat:Boolean = false

			override fun build() = Config(indent, lineSeparator, doubleQuoted, unquoted, trimSpaces, prettyFormat)
		}
	}


	private val indent = config.indent
	private val quote = if(config.unquoted) null else if(config.doubleQuoted) '\"' else '\''
	private val lineSeparator = config.lineSeparator
	private val separator = if(config.trimSpaces) ":" else ": "
	private val valueSeparator = if(config.prettyFormat) ",$lineSeparator" else if(config.trimSpaces) "," else ", "
	private val arrayPrefix = if(config.prettyFormat) "[$lineSeparator" else "["
	private val arraySuffix = if(config.prettyFormat) "$lineSeparator]" else "]"
	private val objectPrefix = if(config.prettyFormat) "{$lineSeparator" else "{"
	private val objectSuffix = if(config.prettyFormat) "$lineSeparator}" else "}"


	override fun <T> map(data:T):String {
		return data.mapJson()
	}

	//支持的类型：
	//* null
	//* Boolean, Number
	//* CharSequence, Char, Temporal, Date（映射为为字符串）
	//* Array, Sequence, Iterable, Sequence, !ClosedRange（映射为Json数组）
	//* Map（映射为Json对象）
	//* else（尝试映射为Map，然后映射为Json对象）

	private fun Any?.mapJson():String {
		return when {
			this == null -> mapNull()
			this is Boolean -> this.mapBoolean()
			this is Number -> this.mapNumber()
			this is CharSequence || this is Char || this is Temporal || this is Date -> this.mapString()
			this is Array<*> -> this.mapArray()
			this is Iterable<*> && this !is ClosedRange<*> -> this.mapArray()
			this is Sequence<*> -> this.mapArray()
			this is Map<*, *> -> this.mapObject()
			else -> this.mapObject()
		}
	}

	private fun mapNull():String {
		return "null"
	}

	private fun Boolean.mapBoolean():String {
		return this.toString()
	}

	private fun Number.mapNumber():String {
		return this.toString()
	}

	private fun Any.mapString():String {
		return this.toString().quote(quote)
	}

	private fun Array<*>.mapArray():String {
		return this.joinToString(valueSeparator) { it.mapJson() }.doIndent().let { "$arrayPrefix$it$arraySuffix" }
	}

	private fun Iterable<*>.mapArray():String {
		return this.joinToString(valueSeparator) { it.mapJson() }.doIndent().let { "$arrayPrefix$it$arraySuffix" }
	}

	private fun Sequence<*>.mapArray():String {
		return this.joinToString(valueSeparator) { it.mapJson() }.doIndent().let { "$arrayPrefix$it$arraySuffix" }
	}

	private fun Map<*, *>.mapObject():String {
		return this.joinToString(valueSeparator) { (k, v) -> "${k.mapKey()}$separator${v.mapValue()}" }
			.doIndent().let { "$objectPrefix$it$objectSuffix" }
	}

	private fun Any.mapObject():String {
		return Mapper.mapObject(this).joinToString(valueSeparator) { (k, v) -> "${k.mapKey()}$separator${v.mapValue()}" }
			.doIndent().let { "$objectPrefix$it$objectSuffix" }
	}

	private fun Any?.mapKey():String {
		return this.toString().quote(quote)
	}

	private fun Any?.mapValue():String {
		return this.mapJson()
	}

	private fun Any?.doIndent():String{
		return if(config.prettyFormat) this.toString().prependIndent(indent) else this.toString()
	}


	override fun <T> unmap(string:String, type:Class<T>):T {
		TODO("not implemented")
	}

	override fun <T> unmap(string:String, type:Type):T {
		TODO("not implemented")
	}
}
