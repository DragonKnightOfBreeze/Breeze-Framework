// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.mapper.impl

import com.windea.breezeframework.core.*
import com.windea.breezeframework.mapper.*
import java.lang.reflect.*

//TODO

/**
 * Xml映射器。
 */
class XmlMapper(
	val config: Config = Config.Default,
) : Mapper {
	constructor(configBlock: Config.Builder.() -> Unit) : this(Config.Builder().apply(configBlock).build())

	/**
	 * Xml映射器的配置。
	 * @property indent 文本缩进。默认为`"  "`。
	 * @property lineSeparator 行分隔符。默认为`"\n"`。
	 * @property doubleQuoted 是否使用双引号括起。默认为`true`。
	 * @property trimSpaces 是否去除不必要的空格。默认为`true`。
	 * @property autoCloseTag 是否自关闭标签。默认为`false`。
	 * @property prettyFormat 是否使用良好的格式进行输出。默认为`false`。
	 */
	data class Config(
		val indent: String = "  ",
		val lineSeparator: String = "\n",
		val doubleQuoted: Boolean = true,
		val trimSpaces: Boolean = true,
		val autoCloseTag: Boolean = false,
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

		class Builder : com.windea.breezeframework.core.model.Builder<Config> {
			var indent: String = "  "
			var lineSeparator: String = "\n"
			var doubleQuoted: Boolean = true
			var trimSpaces: Boolean = false
			var autoCloseTag: Boolean = false
			var prettyFormat: Boolean = false

			override fun build() = Config(indent, lineSeparator, doubleQuoted, trimSpaces, autoCloseTag, prettyFormat)
		}
	}


	private val indent = config.indent
	private val quote = if(config.doubleQuoted) '\"' else '\''
	private val lineSeparator = config.lineSeparator
	private val separator = if(config.trimSpaces) "=" else " = "
	private val commentPrefix = "<!--"
	private val commentSuffix = "-->"
	private val cdataPrefix = "<![CDATA["
	private val cdataSuffix = "]]>"


	override fun <T> map(data: T): String {
		TODO("not implemented")
	}

	override fun <T> unmap(string: String, type: Class<T>): T {
		TODO("not implemented")
	}

	override fun <T> unmap(string: String, type: Type): T {
		TODO("not implemented")
	}
}
