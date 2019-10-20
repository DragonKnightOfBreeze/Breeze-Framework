@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markup.YamlConfig.indent
import com.windea.breezeframework.dsl.markup.YamlConfig.quote

//TODO basic support
//TODO complex key support

//REGION Top annotations and interfaces

@DslMarker
private annotation class YamlDsl

/**Yaml。*/
@YamlDsl
class Yaml @PublishedApi internal constructor() : DslBuilder {
	lateinit var rootElement: YamlElement<*>
	
	override fun toString(): String {
		return rootElement.toString()
	}
	
	@YamlDsl
	inline fun Any?.map() = this.toYamlElement()
}

//REGION Dsl elements

/**Yaml Dsl的元素。*/
@YamlDsl
interface YamlDslElement : DslElement


/**Yaml元素。*/
@YamlDsl
sealed class YamlElement<T>(
	val value: T
) : YamlDslElement {
	override fun equals(other: Any?): Boolean {
		return this === other || (other is YamlElement<*> && other.value == value)
	}
	
	override fun hashCode(): Int {
		return value.hashCode()
	}
	
	override fun toString(): String {
		return value.toString()
	}
}

/**Yaml原始值。*/
@YamlDsl
sealed class YamlPrimitive<T>(value: T) : YamlElement<T>(value)

/**Yaml null。*/
@YamlDsl
object YamlNull : YamlPrimitive<Nothing?>(null)

/**Yaml布尔值。*/
@YamlDsl
class YamlBoolean @PublishedApi internal constructor(value: Boolean) : YamlPrimitive<Boolean>(value)

/**Yaml数值。*/
@YamlDsl
class YamlNumber @PublishedApi internal constructor(value: Number) : YamlPrimitive<Number>(value)

/**Yaml字符串值。*/
@YamlDsl
class YamlString @PublishedApi internal constructor(value: String) : YamlPrimitive<String>(value) {
	override fun toString(): String {
		return value.wrapQuote(quote)
	}
}

/**Yaml数组。*/
@YamlDsl
class YamlArray @PublishedApi internal constructor(
	value: List<YamlElement<*>> = listOf()
) : YamlElement<List<*>>(value), List<YamlElement<*>> by value, WrapContent, IndentContent {
	override var wrapContent: Boolean = true
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		return when {
			wrapContent && indentContent -> value.joinToString(",\n", "[\n", "\n]") {
				it.toString().prependIndent(indent)
			}
			wrapContent -> value.joinToString(",\n", "[\n", "\n]")
			else -> value.joinToString(", ", "[", "]")
		}
	}
}

/**Yaml对象。*/
@YamlDsl
class YamlObject @PublishedApi internal constructor(
	value: Map<String, YamlElement<*>> = mapOf()
) : YamlElement<Map<String, *>>(value), Map<String, YamlElement<*>> by value, WrapContent, IndentContent {
	override var wrapContent: Boolean = true
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		return when {
			wrapContent && indentContent -> value.joinToString(",\n", "{\n", "\n}") { (k, v) ->
				"${k.wrapQuote(quote)}: $v".prependIndent(indent)
			}
			wrapContent -> value.joinToString(",\n", "{\n", "\n}") { (k, v) -> "${k.wrapQuote(quote)}: $v" }
			else -> value.joinToString(", ", "{", "}") { (k, v) -> "${k.wrapQuote(quote)}: $v" }
		}
	}
}

class YamlDocument

class YamlDirective

class YamlTag

class YamlAlias

class YamlAnchor

//REGION Enumerations and constants

enum class YamlScalarStyle(
	val text: String
) {
	None(""), DoubleQuoted("\""), SingleQuoted("'"), Literal("|"), Folded(">")
}

enum class YamlFlowStyle {
	Auto, Flow, Block
}

//REGION Build extensions

@YamlDsl
object YamlInlineBuilder {
	@YamlDsl
	inline fun yamlNull() = YamlNull
	
	@YamlDsl
	inline fun yamlBoolean(value: Boolean) = YamlBoolean(value)
	
	@YamlDsl
	inline fun yamlNumber(value: Number) = YamlNumber(value)
	
	@YamlDsl
	inline fun yamlString(value: Any?) = YamlString(value.toString())
	
	@YamlDsl
	inline fun yamlObject(value: Map<String, YamlElement<*>>) = YamlObject(value)
	
	@YamlDsl
	inline fun yamlObjectOf(vararg value: Pair<String, YamlElement<*>>) = YamlObject(value.toMap())
	
	@YamlDsl
	inline fun yamlArray(value: Iterable<YamlElement<*>>) = YamlArray(value.toList())
	
	@YamlDsl
	inline fun yamlArrayOf(vararg value: YamlElement<*>) = YamlArray(value.toList())
}


@YamlDsl
inline fun yaml(builder: Yaml.() -> Any?) = Yaml().also { it.rootElement = it.builder().toYamlElement() }

@YamlDsl
inline fun yamlTree(builder: Yaml.() -> YamlElement<*>) = Yaml().also { it.rootElement = it.builder() }

//REGION Helpful extensions

@PublishedApi
internal fun Any?.toYamlElement(): YamlElement<*> {
	return when {
		this is YamlElement<*> -> this
		this == null -> YamlNull
		this is Boolean -> YamlBoolean(this)
		this is Number -> YamlNumber(this)
		this is Map<*, *> -> YamlObject(this.toStringKeyMap().mapValues { (_, v) -> v.toYamlElement() })
		this is Array<*> -> YamlArray(this.map { it.toYamlElement() })
		this is Iterable<*> -> YamlArray(this.map { it.toYamlElement() })
		else -> YamlString(this.toString())
	}
}

//REGION Dsl config

/**Yaml的配置。*/
@YamlDsl
object YamlConfig : DslConfig {
	private val indentSizeRange = 0..8
	
	var indentSize = 2
		set(value) = run { if(value in indentSizeRange) field = value }
	var indicatorIndentSize = 0
		set(value) = run { if(value in indentSizeRange) field = value }
	var preferDoubleQuote: Boolean = true
	var preferNoQuote: Boolean = true
	
	@PublishedApi internal val indent get() = " " * indentSize
	@PublishedApi internal val indicatorIndent get() = " " * indicatorIndentSize
	@PublishedApi internal val quote get() = if(preferDoubleQuote) '"' else '\''
}
