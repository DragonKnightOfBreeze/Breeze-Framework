// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.mapper.impl

import com.windea.breezeframework.core.domain.data.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.mapper.*
import java.lang.reflect.*
import java.time.temporal.*
import java.util.*

//TODO

/**
 * Yaml映射器。
 */
class YamlMapper(
	val config: Config = Config.Default,
) : Mapper {
	constructor(configBlock: Config.Builder.() -> Unit) : this(Config.Builder().apply(configBlock).build())

	/**
	 * Yaml映射器的配置。
	 * @property indent 文本缩进。默认为`"  "`。
	 * @property indent 指示器缩进。默认为`""`。
	 * @property lineSeparator 行分隔符。默认为`"\n"`。
	 * @property doubleQuoted 是否使用双引号括起。默认为`true`。
	 * @property unquoted 是否不使用任何引号括起。默认为`true`。
	 * @property blockStyle 是否使用块风格进行输出。这种风格会输出类似Json的文本。默认为`true`。
	 * @property literalStyle 是否使用字面量风格进行输出。这种风格会输出多行字符串。默认为`true`。
	 * @property trimSpaces 是否去除不必要的空格。默认为`false`。
	 * @property prettyFormat 是否使用良好的格式进行输出。默认为`false`。
	 */
	data class Config(
		val indent: String = "  ",
		val indicatorIndent: String = "",
		val lineSeparator: String = "\n",
		val doubleQuoted: Boolean = true,
		val unquoted: Boolean = true,
		val blockStyle: Boolean = true,
		val literalStyle: Boolean = true,
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
			var indicatorIndent: String = ""
			var lineSeparator: String = "\n"
			var doubleQuoted: Boolean = true
			var unquoted: Boolean = true
			var blockStyle: Boolean = true
			var literalStyle: Boolean = true
			var trimSpaces: Boolean = false
			var prettyFormat: Boolean = false

			override fun build() = Config(indent, indicatorIndent, lineSeparator, doubleQuoted, unquoted, blockStyle,
				literalStyle, trimSpaces, prettyFormat)
		}
	}


	private val indent = config.indent
	private val indicatorIndent = config.indicatorIndent
	private val quote = if(config.doubleQuoted) '\"' else '\''
	private val lineSeparator = config.lineSeparator
	private val separator = ": " //冒号左边必须存在一个空格
	private val valueSeparator = if(config.prettyFormat) ",$lineSeparator" else if(config.trimSpaces) "," else ", "
	private val arrayPrefix = if(config.prettyFormat) "[$lineSeparator" else "["
	private val arraySuffix = if(config.prettyFormat) "$lineSeparator]" else "]"
	private val objectPrefix = if(config.prettyFormat) "{$lineSeparator" else "{"
	private val objectSuffix = if(config.prettyFormat) "$lineSeparator}" else "}"

	private val String.shouldQuoted get() = this.isEmpty() || this.first().isWhitespace() || this.last().isWhitespace()
	private fun String.doIndent() = if(config.prettyFormat) this.prependIndent(indent) else this
	private fun String.doQuote() = if(!config.unquoted || this.shouldQuoted) this.quote(quote) else this


	override fun <T> map(data: T): String {
		return data.mapYaml()
	}

	private fun Any?.mapYaml(): String {
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

	private fun mapNull(): String {
		return "null"
	}

	private fun Boolean.mapBoolean(): String {
		return this.toString()
	}

	private fun Number.mapNumber(): String {
		return this.toString()
	}

	private fun Any.mapString(): String {
		return this.toString().doQuote()
	}

	private fun Array<*>.mapArray(): String {
		return this.joinToString(valueSeparator) { it.mapYaml() }.doIndent().let { "$arrayPrefix$it$arraySuffix" }
	}

	private fun Iterable<*>.mapArray(): String {
		return this.joinToString(valueSeparator) { it.mapYaml() }.doIndent().let { "$arrayPrefix$it$arraySuffix" }
	}

	private fun Sequence<*>.mapArray(): String {
		return this.joinToString(valueSeparator) { it.mapYaml() }.doIndent().let { "$arrayPrefix$it$arraySuffix" }
	}

	private fun Map<*, *>.mapObject(): String {
		return this.joinToString(valueSeparator) { (k, v) -> "${k.mapKey()}$separator${v.mapValue()}" }
			.doIndent().let { "$objectPrefix$it$objectSuffix" }
	}

	private fun Any.mapObject(): String {
		return ObjectMapper.map(this).joinToString(valueSeparator) { (k, v) -> "${k.mapKey()}$separator${v.mapValue()}" }
			.doIndent().let { "$objectPrefix$it$objectSuffix" }
	}

	private fun Any?.mapKey(): String {
		return this.toString().doQuote()
	}

	private fun Any?.mapValue(): String {
		return this.mapYaml()
	}


	override fun <T> unmap(string: String, type: Class<T>): T {
		TODO("not implemented")
	}

	override fun <T> unmap(string: String, type: Type): T {
		TODO("not implemented")
	}
}
