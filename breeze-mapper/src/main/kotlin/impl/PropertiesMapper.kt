// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.mapper.impl

import com.windea.breezeframework.core.domain.data.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.mapper.*
import java.lang.reflect.*

/**
 * Properties映射器。
 */
class PropertiesMapper(
	val config: Config = Config.Default,
) : Mapper {
	constructor(configBlock: Config.Builder.() -> Unit) : this(Config.Builder().apply(configBlock).build())

	/**
	 * Properties映射器的配置。
	 * @property indent 文本缩进。默认为`"  "`。
	 * @property separator 键值分隔符。默认为`"="`。
	 * @property lineSeparator 行分隔符。默认为`"\n"`。
	 * @property trimSpaces 是否去除不必要的空格。默认为`false`。
	 * @property flattenKeys 是否平滑键，将其作为单一的键而非路径进行映射。默认为`true`。
	 */
	data class Config(
		val indent: String = "  ",
		val lineSeparator: String = "\n",
		val separator: String = "=",
		val trimSpaces: Boolean = false,
		val flattenKeys: Boolean = true,
	) : DataEntity {
		init {
			require(lineSeparator in validLineSeparators) { "Line Separator should be '\\n', '\\r' or '\\r\\n'." }
			require(separator in validSeparators) { "Key value separator should be '=' or ':'." }
		}

		companion object {
			private val validLineSeparators = arrayOf("\n", "\r", "\r\n")
			private val validSeparators = arrayOf("=", ":")

			@JvmStatic val Default = Config()
		}

		class Builder : DataBuilder<Config> {
			var indent: String = "  "
			var separator: String = "="
			var lineSeparator: String = "\n"
			var trimSpaces: Boolean = true
			var flattenKeys: Boolean = true

			override fun build() = Config(indent, lineSeparator, separator, trimSpaces, flattenKeys)
		}
	}


	private val indent = config.indent
	private val lineJoint = "\\"
	private val lineSeparator = config.lineSeparator
	private val separator = if(config.trimSpaces) " ${config.separator} " else config.separator
	private val valueSeparator = if(config.trimSpaces) ",$lineJoint$lineSeparator" else ", $lineJoint$lineSeparator"
	private val arrayPrefix = "$lineJoint$lineSeparator"

	private fun Any?.doIndent() = this.toString().prependIndent(indent)
	private fun Any?.doWrap() = this.toString().replace("\r\n", "\\\r\n").replace("\r", "\\\r").replace("\n", "\\\n")


	override fun <T> map(data: T): String {
		return data.mapProperties()
	}

	//支持的类型：
	//* null（抛出异常）
	//* Array, Iterable, Sequence, !ClosedRange（将索引作为属性名处理）
	//* Map（将键作为属性名处理）
	//* else（尝试映射为Map，然后将键作为属性名处理）

	private fun Any?.mapProperties(): String {
		return when {
			this == null -> throw IllegalArgumentException("Cannot map null value to properties.")
			this is Array<*> -> this.mapIndexedProperty()
			this is Iterable<*> && this !is ClosedRange<*> -> this.mapIndexedProperty()
			this is Sequence<*> -> this.mapIndexedProperty()
			this is Map<*, *> -> this.mapMappedProperty()
			else -> this.mapMappedProperty()
		}
	}

	private fun Array<*>.mapIndexedProperty(): String {
		return this.withIndex().joinToString(lineSeparator) { (i, e) -> "${i.mapKey()}$separator${e.mapValue()}" }
	}

	private fun Iterable<*>.mapIndexedProperty(): String {
		return this.withIndex().joinToString(lineSeparator) { (i, e) -> "${i.mapKey()}$separator${e.mapValue()}" }
	}

	private fun Sequence<*>.mapIndexedProperty(): String {
		return this.withIndex().joinToString(lineSeparator) { (i, e) -> "${i.mapKey()}$separator${e.mapValue()}" }
	}

	private fun Map<*, *>.mapMappedProperty(): String {
		return this.joinToString(lineSeparator) { (k, v) -> "${k.mapKey()}$separator${v.mapValue()}" }
	}

	private fun Any.mapMappedProperty(): String {
		return ObjectMapper.map(this).joinToString(lineSeparator) { (k, v) -> "${k.mapKey()}$separator${v.mapValue()}" }
	}

	//支持的类型（属性的名字）：
	//* Any（转换为字符串）

	private fun Any?.mapKey(): String {
		return this.toString()
	}

	//支持的类型（属性的值）：
	//* null
	//* Array, Iterable, Sequence, !ClosedRange（转换为多行逗号分隔表达式）
	//* else（转换为字符串）

	private fun Any?.mapValue(): String {
		return when {
			this == null -> mapNullValue()
			this is Array<*> -> this.mapIndexedValue()
			this is Iterable<*> && this !is ClosedRange<*> -> this.mapIndexedValue()
			this is Sequence<*> -> this.mapIndexedValue()
			else -> this.mapNormalValue()
		}
	}

	private fun mapNullValue(): String {
		return "null"
	}

	private fun Array<*>.mapIndexedValue(): String {
		return this.joinToString(valueSeparator) { it.doWrap() }.doIndent().let { "$arrayPrefix$it" }
	}

	private fun Iterable<*>.mapIndexedValue(): String {
		return this.joinToString(valueSeparator) { it.doWrap() }.doIndent().let { "$arrayPrefix$it" }
	}

	private fun Sequence<*>.mapIndexedValue(): String {
		return this.joinToString(valueSeparator) { it.toString().doWrap() }.doIndent().let { "$arrayPrefix$it" }
	}

	private fun Any.mapNormalValue(): String {
		return this.doWrap()
	}


	override fun <T> unmap(string: String, type: Class<T>): T {
		TODO("not implemented")
	}

	override fun <T> unmap(string: String, type: Type): T {
		TODO("not implemented")
	}
}
