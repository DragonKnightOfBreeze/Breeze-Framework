package com.windea.breezeframework.mapper.impl

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.mapper.*
import java.lang.reflect.*

//DONE 生成json
//TODO 解析json
//TODO 允许空格和换行
//TODO 允许特殊情况：单引号，多行字符串，注释等
//DONE 允许映射一般对象
//TODO 允许特殊情况：默认参数，构造方法映射，忽略空值等
//TODO 支持锚点

/**json映射器。*/
class JsonMapper(
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
			val trimSpaces: Boolean = false
			var prettyFormat: Boolean = false
			override fun build() = Config(indent, doubleQuoted, unquoted, trimSpaces, prettyFormat)
		}
	}

	private fun indent(depth: Int) = if(config.prettyFormat) config.indent.repeat(depth) else ""
	private val quote = if(config.unquoted) null else if(config.doubleQuoted) '\"' else '\''
	private val separator = if(config.prettyFormat) ",\n" else if(config.trimSpaces) "," else ", "
	private val kvSeparator = if(config.trimSpaces) ":" else ": "
	private val arrayPrefix = if(config.prettyFormat) "[\n" else "["
	private fun arraySuffix(depth: Int) = if(config.prettyFormat) "\n${indent(depth - 1)}]" else "]"
	private val objectPrefix = if(config.prettyFormat) "{\n" else "{"
	private fun objectSuffix(depth: Int) = if(config.prettyFormat) "\n${indent(depth - 1)}}" else "}"


	override fun <T> map(data: T): String {
		return data.mapJson(0)
	}

	private fun Any?.mapJson(depth: Int): String {
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

	private fun Any.mapString(): String {
		return this.toString().quote(quote)
	}

	private fun Array<*>.mapArray(depth: Int): String {
		return this.joinToString(separator, arrayPrefix, arraySuffix(depth)) {
			indent(depth) + it.mapJson(depth)
		}
	}

	private fun Iterable<*>.mapArray(depth: Int): String {
		return this.joinToString(separator, arrayPrefix, arraySuffix(depth)) {
			indent(depth) + it.mapJson(depth)
		}
	}

	private fun Sequence<*>.mapArray(depth: Int): String {
		return this.joinToString(separator, arrayPrefix, arraySuffix(depth)) {
			indent(depth) + it.mapJson(depth)
		}
	}

	private fun Map<*, *>.mapObject(depth: Int): String {
		return this.joinToString(separator, objectPrefix, objectSuffix(depth)) { (k, v) ->
			indent(depth) + k.mapKey() + kvSeparator + v.mapJson(depth)
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

	private fun <T> String.unmapJson(type: Class<T>): T {
		return this.reader().use {
			val map = mutableMapOf<Any?, Any?>()
			val char = it.read().toChar()
			when {
				char == '{' -> TODO()
				else -> TODO()
			}
		}
	}
}
