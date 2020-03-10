package com.windea.breezeframework.mapper.impl

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.mapper.*

//DONE 生成json
//TODO 解析json
//TODO 允许空格和换行
//TODO 允许特殊情况：单引号，多行字符串，注释等
//TODO 允许映射一般对象
//TODO 允许特殊情况：默认参数，构造方法映射，忽略空值等

class JsonMapper(
	val config: Config = Config()
) : Mapper {
	constructor(configBlock: Config.Builder.() -> Unit) : this(Config.Builder().apply(configBlock).build())

	data class Config(
		val indent: String = "  ",
		val doubleQuoted: Boolean = true,
		val unquoted: Boolean = false,
		val prettyFormat: Boolean = false
	) : DataEntity {
		class Builder : DataBuilder<Config> {
			var indent: String = "  "
			var doubleQuoted: Boolean = true
			var unquoted: Boolean = false
			var prettyFormat: Boolean = false
			override fun build() = Config(indent, doubleQuoted, unquoted, prettyFormat)
		}
	}

	companion object {
		private const val separator = ", "
		private const val kvSeparator = ": "
		private const val arrayPrefix = "["
		private const val arraySuffix = "]"
		private const val objectPrefix = "{"
		private const val objectSuffix = "}"
	}

	private fun indent(depth: Int = 0) = if(config.prettyFormat) config.indent.repeat(depth) else ""
	private fun quote() = if(config.doubleQuoted) '\"' else '\''
	private fun separator() = if(config.prettyFormat) "$separator\n" else separator
	private fun kvSeparator() = kvSeparator
	private fun arrayPrefix() = if(config.prettyFormat) "$arrayPrefix\n" else arrayPrefix
	private fun arraySuffix(depth: Int = 0) = if(config.prettyFormat) "\n${indent(depth - 1)}$arraySuffix" else arraySuffix
	private fun objectPrefix() = if(config.prettyFormat) "$objectPrefix\n" else objectPrefix
	private fun objectSuffix(depth: Int = 0) = if(config.prettyFormat) "\n${indent(depth - 1)}$objectSuffix" else objectSuffix


	override fun <T> map(data: T): String {
		return data.mapJson()
	}

	private fun Any?.mapJson(depth: Int = 0): String = when {
		this == null -> this.mapNull()
		this is Number -> this.mapNumber()
		this is CharSequence -> this.mapString()
		this is Array<*> -> this.mapArray(depth + 1)
		this is Iterable<*> -> this.mapArray(depth + 1)
		this is Sequence<*> -> this.mapArray(depth + 1)
		this is Map<*, *> -> this.mapObject(depth + 1)
		else -> this.mapObject(depth + 1)
	}

	private fun Nothing?.mapNull() = "null"

	private fun Number.mapNumber() = this.toString()

	private fun CharSequence.mapString() = this.toString().quote(quote())

	private fun Array<*>.mapArray(depth: Int) = this.joinToString(separator(), arrayPrefix(), arraySuffix(depth)) {
		indent(depth) + it.mapJson(depth)
	}

	private fun Iterable<*>.mapArray(depth: Int) = this.joinToString(separator(), arrayPrefix(), arraySuffix(depth)) {
		indent(depth) + it.mapJson(depth)
	}

	private fun Sequence<*>.mapArray(depth: Int) = this.joinToString(separator(), arrayPrefix(), arraySuffix(depth)) {
		indent(depth) + it.mapJson(depth)
	}

	private fun Map<*, *>.mapObject(depth: Int) = this.joinToString(separator(), objectPrefix(), objectSuffix(depth)) { (k, v) ->
		indent(depth) + k.toString().quote(quote()) + kvSeparator() + v.mapJson(depth)
	}

	private fun Any.mapObject(depth: Int) = Mapper.mapObject(this).mapObject(depth)


	override fun <T> unmap(string: String, type: Class<T>): T {
		TODO("not implemented")
	}
}
