package com.windea.breezeframework.mapper.impl

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.mapper.*
import java.lang.reflect.*

/**
 * Xml映射器。
 */
class XmlMapper(
	val config: Config = Config.Default
) : Mapper {
	constructor(configBlock: Config.Builder.() -> Unit) : this(Config.Builder().apply(configBlock).build())

	data class Config(
		val indent: String = "  ",
		val doubleQuoted: Boolean = true,
		val unquoted: Boolean = false,
		val trimSpaces: Boolean = false,
		val prettyFormat: Boolean = false
	) : DataEntity {
		companion object {
			@JvmStatic val Default = Config()
			@JvmStatic val PrettyFormat = Config(prettyFormat = true)
		}

		class Builder : DataBuilder<Config> {
			var indent: String = "  "
			var doubleQuoted: Boolean = true
			var unquoted: Boolean = false
			var uglyFormat: Boolean = false
			var prettyFormat: Boolean = true
			override fun build() = Config(indent, doubleQuoted, unquoted, uglyFormat, prettyFormat)
		}
	}


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
