package com.windea.breezeframework.mapper.impl

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.mapper.*
import java.lang.reflect.*

class YamlMapper(
	val config: Config = Config()
) : Mapper {
	constructor(configBlock: Config.Builder.() -> Unit) : this(Config.Builder().apply(configBlock).build())

	data class Config(
		val indent: String = "  ",
		val indicatorIndent: String = "",
		val doubleQuoted: Boolean = true,
		val unquoted: Boolean = true,
		val blockStyle: Boolean = true,
		val literalStyle: Boolean = true,
		val uglyFormat: Boolean = false,
		val prettyFormat: Boolean = false
	) : DataEntity {
		init {
			require(!uglyFormat || !prettyFormat) { "Ugly format and pretty format cannot be both applied." }
		}

		class Builder : DataBuilder<Config> {
			var indent: String = "  "
			var indicatorIndent: String = ""
			var doubleQuoted: Boolean = true
			var unquoted: Boolean = false
			var blockStyle: Boolean = true
			var literalStyle: Boolean = true
			var uglyFormat: Boolean = false
			var prettyFormat: Boolean = false
			override fun build() = Config(indent, indicatorIndent, doubleQuoted, unquoted, blockStyle, literalStyle, uglyFormat, prettyFormat)
		}
	}

	//TODO
	private fun indent(depth: Int) = if(config.prettyFormat) config.indent.repeat(depth) else ""
	private val indicatorIndent = config.indicatorIndent
	private val quote = if(config.unquoted) null else if(config.doubleQuoted) '\"' else '\''
	private val separator = if(config.prettyFormat) ",\n" else if(config.uglyFormat) "," else ", "
	private val kvSeparator = if(config.uglyFormat) ":" else ": "
	private val arrayPrefix = if(config.prettyFormat) "[\n" else "["
	private fun arraySuffix(depth: Int) = if(config.prettyFormat) "\n${indent(depth - 1)}]" else "]"
	private val objectPrefix = if(config.prettyFormat) "{\n" else "{"
	private fun objectSuffix(depth: Int) = if(config.prettyFormat) "\n${indent(depth - 1)}}" else "}"


	override fun <T> map(data: T): String {
		return data.mapYaml(0)
	}

	private fun Any?.mapYaml(depth: Int): String {
		return when {
			this == null -> mapNull()
			this is Boolean -> this.mapBoolean()
			this is Number -> this.mapNumber()
			this is CharSequence || this is Char -> this.mapString()
			this is Array<*> -> this.mapArray(depth + 1)
			this is Iterable<*> -> this.mapArray(depth + 1)
			this is Sequence<*> -> this.mapArray(depth + 1)
			this is Map<*, *> -> this.mapObject(depth + 1)
			else -> this.mapObject(depth + 1)
		}
	}


	private fun Any?.mapKey(): String {
		return this.toString().quote(quote)
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

	//如果结果为空，需要用引号包围
	private fun Any.mapString(): String {
		return this.toString().quote(quote).ifEmpty { "\"\"" }
	}

	private fun Array<*>.mapArray(depth: Int): String {
		return this.joinToString(separator, arrayPrefix, arraySuffix(depth)) {
			indent(depth) + it.mapYaml(depth)
		}
	}

	private fun Iterable<*>.mapArray(depth: Int): String {
		return this.joinToString(separator, arrayPrefix, arraySuffix(depth)) {
			indent(depth) + it.mapYaml(depth)
		}
	}

	private fun Sequence<*>.mapArray(depth: Int): String {
		return this.joinToString(separator, arrayPrefix, arraySuffix(depth)) {
			indent(depth) + it.mapYaml(depth)
		}
	}

	private fun Map<*, *>.mapObject(depth: Int): String {
		return this.joinToString(separator, objectPrefix, objectSuffix(depth)) { (k, v) ->
			indent(depth) + k.mapKey() + kvSeparator + v.mapYaml(depth)
		}
	}

	private fun Any.mapObject(depth: Int): String {
		return Mapper.mapObject(this).mapObject(depth)
	}


	override fun <T> unmap(string: String, type: Class<T>): T {
		TODO("not implemented")
	}

	override fun <T> unmap(string: String, type: Type): T {
		TODO("not implemented")
	}
}
