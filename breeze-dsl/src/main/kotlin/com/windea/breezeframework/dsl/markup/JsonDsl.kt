@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "unused")

package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markup.JsonConfig.indent
import com.windea.breezeframework.dsl.markup.JsonConfig.prettyPrint
import com.windea.breezeframework.dsl.markup.JsonConfig.quote

//REGION top annotations and interfaces

@DslMarker
private annotation class JsonDsl

/**Json。*/
@JsonDsl
class Json @PublishedApi internal constructor() : DslBuilder, JsonDslInlineEntry {
	lateinit var rootElement: JsonElement<*>
	
	override fun toString(): String {
		return rootElement.toString()
	}
	
	@JsonDsl
	inline fun Any?.map() = this.toJsonElement()
}

/**Json的配置。*/
@JsonDsl
object JsonConfig : DslConfig {
	private val indentSizeRange = -2..8
	
	var indentSize = 2
		set(value) = run { if(value in indentSizeRange) field = value }
	var preferDoubleQuote: Boolean = true
	var prettyPrint: Boolean = true
	
	@PublishedApi internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	@PublishedApi internal val quote get() = if(preferDoubleQuote) '"' else '\''
}

//REGION dsl interfaces

/**Json Dsl的元素的内联入口。*/
@JsonDsl
interface JsonDslInlineEntry : DslEntry

/**Json Dsl的元素。*/
@JsonDsl
interface JsonDslElement : DslElement

//REGION dsl elements

/**Json元素。*/
@JsonDsl
sealed class JsonElement<T>(
	val value: T
) : JsonDslElement, JsonDslInlineEntry {
	override fun equals(other: Any?): Boolean {
		return this === other || (other is JsonElement<*> && other.value == value)
	}
	
	override fun hashCode(): Int {
		return value.hashCode()
	}
	
	override fun toString(): String {
		return value.toString()
	}
}

/**Json原始值。*/
@JsonDsl
sealed class JsonPrimitive<T>(value: T) : JsonElement<T>(value)

/**Json null。*/
@JsonDsl
object JsonNull : JsonPrimitive<Nothing?>(null)

/**Json布尔值。*/
@JsonDsl
class JsonBoolean @PublishedApi internal constructor(value: Boolean) : JsonPrimitive<Boolean>(value)

/**Json数值。*/
@JsonDsl
class JsonNumber @PublishedApi internal constructor(value: Number) : JsonPrimitive<Number>(value)

/**Json字符串值。*/
@JsonDsl
class JsonString @PublishedApi internal constructor(value: String) : JsonPrimitive<String>(value) {
	override fun toString(): String {
		return value.wrapQuote(quote)
	}
}

/**Json数组。*/
@JsonDsl
class JsonArray @PublishedApi internal constructor(
	value: List<JsonElement<*>> = listOf()
) : JsonElement<List<*>>(value), List<JsonElement<*>> by value, WrapContent, IndentContent {
	override var wrapContent: Boolean = prettyPrint
	override var indentContent: Boolean = prettyPrint
	
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

/**Json对象。*/
@JsonDsl
class JsonObject @PublishedApi internal constructor(
	value: Map<String, JsonElement<*>> = mapOf()
) : JsonElement<Map<String, *>>(value), Map<String, JsonElement<*>> by value, WrapContent, IndentContent {
	override var wrapContent: Boolean = prettyPrint
	override var indentContent: Boolean = prettyPrint
	
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

//REGION build extensions

@JsonDsl
inline fun jsonTree(builder: Json.() -> Any?) = Json().also { it.rootElement = it.builder().toJsonElement() }

@JsonDsl
inline fun json(builder: Json.() -> JsonElement<*>) = Json().also { it.rootElement = it.builder() }

@InlineDsl
@JsonDsl
inline fun JsonDslInlineEntry.jsonNull() = JsonNull

@InlineDsl
@JsonDsl
inline fun JsonDslInlineEntry.jsonBoolean(value: Boolean) = JsonBoolean(value)

@InlineDsl
@JsonDsl
inline fun JsonDslInlineEntry.jsonNumber(value: Number) = JsonNumber(value)

@InlineDsl
@JsonDsl
inline fun JsonDslInlineEntry.jsonString(value: Any?) = JsonString(value.toString())

@InlineDsl
@JsonDsl
inline fun JsonDslInlineEntry.jsonObject(value: Map<String, JsonElement<*>>) = JsonObject(value)

@InlineDsl
@JsonDsl
inline fun JsonDslInlineEntry.jsonObjectOf(vararg value: Pair<String, JsonElement<*>>) = JsonObject(value.toMap())

@InlineDsl
@JsonDsl
inline fun JsonDslInlineEntry.jsonArray(value: Iterable<JsonElement<*>>) = JsonArray(value.toList())

@InlineDsl
@JsonDsl
inline fun JsonDslInlineEntry.jsonArrayOf(vararg value: JsonElement<*>) = JsonArray(value.toList())

//REGION helpful extensions

@PublishedApi
internal fun Any?.toJsonElement(): JsonElement<*> {
	return when {
		this is JsonElement<*> -> this
		this == null -> JsonNull
		this is Boolean -> JsonBoolean(this)
		this is Number -> JsonNumber(this)
		this is Map<*, *> -> JsonObject(this.toStringKeyMap().mapValues { (_, v) -> v.toJsonElement() })
		this is Array<*> -> JsonArray(this.map { it.toJsonElement() })
		this is Iterable<*> -> JsonArray(this.map { it.toJsonElement() })
		else -> JsonString(this.toString())
	}
}

