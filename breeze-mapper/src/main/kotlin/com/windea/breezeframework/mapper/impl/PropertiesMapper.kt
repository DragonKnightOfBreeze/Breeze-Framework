package com.windea.breezeframework.mapper.impl

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.mapper.*

//DONE 生成Properties
//TODO 解析Properties
//TODO 支持值的折行和数组解析
//TODO 支持多级结构

class PropertiesMapper(
	val config: Config = Config()
) : Mapper {
	constructor(configBlock: Config.Builder.() -> Unit) : this(Config.Builder().apply(configBlock).build())

	data class Config(
		val separator: String = "=",
		val uglyFormat: Boolean = true,
		val flattenKeys: Boolean = true
	) : DataEntity {
		init {
			require(separator == "=" || separator == ":") { "Separator should be '=' or ':'." }
		}

		class Builder : DataBuilder<Config> {
			var separator: String = "="
			var spaceAroundSeparator: Boolean = true
			var flattenKeys: Boolean = true
			override fun build() = Config(separator, spaceAroundSeparator, flattenKeys)
		}
	}

	private fun separator() = if(config.uglyFormat) " ${config.separator} " else config.separator


	override fun <T> map(data: T): String {
		return data.mapProperties()
	}

	private fun Any?.mapProperties(): String {
		return when {
			this == null -> throw IllegalArgumentException("Cannot map null value to properties.")
			this is Array<*> -> this.mapIndexedProperty()
			this is Iterable<*> -> this.mapIndexedProperty()
			this is Sequence<*> -> this.mapIndexedProperty()
			this is Map<*, *> -> this.mapProperty()
			else -> this.mapProperty()
		}
	}

	private fun Array<*>.mapIndexedProperty(): String {
		return this.withIndex().joinToString("\n") { (i, e) ->
			i.toString() + separator() + e.toString()
		}
	}

	private fun Iterable<*>.mapIndexedProperty(): String {
		return this.withIndex().joinToString("\n") { (i, e) ->
			i.toString() + separator() + e.toString()
		}
	}

	private fun Sequence<*>.mapIndexedProperty(): String {
		return this.withIndex().joinToString("\n") { (i, e) ->
			i.toString() + separator() + e.toString()
		}
	}

	private fun Map<*, *>.mapProperty(): String {
		return this.joinToString("\n") { (k, v) ->
			k.toString() + separator() + v.toString()
		}
	}

	private fun Any.mapProperty(): String {
		return Mapper.mapObject(this).mapProperty()
	}


	override fun <T> unmap(string: String, type: Class<T>): T {
		TODO("not implemented")
	}
}
