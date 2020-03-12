package com.windea.breezeframework.mapper.impl

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.mapper.*
import java.lang.reflect.*

class XmlMapper(
	val config: Config = Config()
) : Mapper {
	constructor(configBlock: Config.Builder.() -> Unit) : this(Config.Builder().apply(configBlock).build())

	data class Config(
		val indent: String = "  ",
		val doubleQuoted: Boolean = true,
		val unquoted: Boolean = false,
		val uglyFormat: Boolean = false,
		val prettyFormat: Boolean = false
	) : DataEntity {
		init {
			require(!uglyFormat || !prettyFormat) { "Ugly format and pretty format cannot be both applied." }
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
