@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "unused")

package com.windea.breezeframework.dsl.json

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.json.JsonConfig.indent
import com.windea.breezeframework.dsl.json.JsonConfig.prettyPrint
import com.windea.breezeframework.dsl.json.JsonConfig.quote

//region dsl top declarations
/**Json领域特定语言。*/
@DslMarker
@MustBeDocumented
annotation class JsonDsl

/**Json。*/
@JsonDsl
class Json @PublishedApi internal constructor() : DslDocument, JsonDslInlineEntry {
	lateinit var rootElement: JsonElement<*>

	override fun toString(): String {
		return rootElement.toString()
	}

	@JsonDsl
	inline fun Any?.map() = this.toJsonElement()
}

/**Json配置。*/
@JsonDsl
object JsonConfig {
	private val indentSizeRange = -2..8

	var indentSize = 2
		set(value) = run { if(value in indentSizeRange) field = value }
	var preferDoubleQuote:Boolean = true
	var prettyPrint:Boolean = true

	@PublishedApi internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	@PublishedApi internal val quote get() = if(preferDoubleQuote) '"' else '\''
}
//endregion

//region dsl declarations
/**Json Dsl的元素的内联入口。*/
@JsonDsl
interface JsonDslInlineEntry : DslEntry

/**Json Dsl的元素。*/
@JsonDsl
interface JsonDslElement : DslElement
//endregion

//region dsl elements
/**Json元素。*/
@JsonDsl
sealed class JsonElement<T>(
	val value: T
) : JsonDslElement, JsonDslInlineEntry {
	override fun equals(other: Any?) = equalsByOne(this, other) { value }

	override fun hashCode() = hashCodeByOne(this) { value }

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
class JsonBoolean @PublishedApi internal constructor(
	value: Boolean
) : JsonPrimitive<Boolean>(value)

/**Json数值。*/
@JsonDsl
class JsonNumber @PublishedApi internal constructor(
	value: Number
) : JsonPrimitive<Number>(value)

/**Json字符串值。*/
@JsonDsl
class JsonString @PublishedApi internal constructor(
	value: String
) : JsonPrimitive<String>(value) {
	override fun toString(): String {
		return value.quote(quote)
	}
}

/**Json数组。*/
@JsonDsl
class JsonArray @PublishedApi internal constructor(
	value: List<JsonElement<*>> = listOf()
) : JsonElement<List<*>>(value), List<JsonElement<*>> by value, CanWrapLine, CanIndent {
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
) : JsonElement<Map<String, *>>(value), Map<String, JsonElement<*>> by value, CanWrapLine, CanIndent {
	override var wrapContent: Boolean = prettyPrint
	override var indentContent: Boolean = prettyPrint

	override fun toString(): String {
		return when {
			wrapContent && indentContent -> value.joinToString(",\n", "{\n", "\n}") { (k, v) ->
				"${k.quote(quote)}: $v".prependIndent(indent)
			}
			wrapContent -> value.joinToString(",\n", "{\n", "\n}") { (k, v) -> "${k.quote(quote)}: $v" }
			else -> value.joinToString(", ", "{", "}") { (k, v) -> "${k.quote(quote)}: $v" }
		}
	}
}
//endregion

//region dsl build extensions
@JsonDsl
inline fun json(block: Json.() -> Any?) = Json().also { it.rootElement = it.block().toJsonElement() }

@JsonDsl
inline fun jsonTree(block: Json.() -> JsonElement<*>) = Json().also { it.rootElement = it.block() }

@JsonDsl
inline fun jsonConfig(block: JsonConfig.() -> Unit) = JsonConfig.apply(block)

@InlineDslFunction
@JsonDsl
fun JsonDslInlineEntry.jsonNull() = JsonNull

@InlineDslFunction
@JsonDsl
fun JsonDslInlineEntry.jsonBoolean(value: Boolean) = JsonBoolean(value)

@InlineDslFunction
@JsonDsl
fun JsonDslInlineEntry.jsonNumber(value: Number) = JsonNumber(value)

@InlineDslFunction
@JsonDsl
fun JsonDslInlineEntry.jsonString(value: Any?) = JsonString(value.toString())

@InlineDslFunction
@JsonDsl
fun JsonDslInlineEntry.jsonObject(value: Map<String, JsonElement<*>>) = JsonObject(value)

@InlineDslFunction
@JsonDsl
fun JsonDslInlineEntry.jsonObjectOf(vararg value: Pair<String, JsonElement<*>>) = JsonObject(value.toMap())

@InlineDslFunction
@JsonDsl
fun JsonDslInlineEntry.jsonArray(value: Iterable<JsonElement<*>>) = JsonArray(value.toList())

@InlineDslFunction
@JsonDsl
fun JsonDslInlineEntry.jsonArrayOf(vararg value: JsonElement<*>) = JsonArray(value.toList())
//endregion

//region helpful extensions
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
//endregion
