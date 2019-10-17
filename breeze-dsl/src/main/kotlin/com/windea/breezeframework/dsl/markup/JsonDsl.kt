@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markup.JsonConfig.indent
import com.windea.breezeframework.dsl.markup.JsonConfig.prettyPrint
import com.windea.breezeframework.dsl.markup.JsonConfig.quote

//REGION Dsl annotations

@DslMarker
private annotation class JsonDsl

//REGION Dsl & Dsl config & Dsl elements

/**Json。*/
@JsonDsl
class Json @PublishedApi internal constructor() : DslBuilder {
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
	/**缩进长度。*/
	var indentSize = 2
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**是否使用双引号。*/
	var useDoubleQuote: Boolean = true
	/**是否以美观的形式输出。*/
	var prettyPrint: Boolean = true
	
	@PublishedApi internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	@PublishedApi internal val quote get() = if(useDoubleQuote) "\"" else "'"
}


/**Json Dsl的元素。*/
@JsonDsl
interface JsonDslElement : DslElement


/**Json元素。*/
@JsonDsl
sealed class JsonElement<T>(
	val value: T
) : JsonDslElement {
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

//REGION Build extensions

@JsonDsl
object JsonInlineBuilder {
	@JsonDsl
	inline fun jsonNull() = JsonNull
	
	@JsonDsl
	inline fun jsonBoolean(value: Boolean) = JsonBoolean(value)
	
	@JsonDsl
	inline fun jsonNumber(value: Number) = JsonNumber(value)
	
	@JsonDsl
	inline fun jsonString(value: Any?) = JsonString(value.toString())
	
	@JsonDsl
	inline fun jsonObject(value: Map<String, JsonElement<*>>) = JsonObject(value)
	
	@JsonDsl
	inline fun jsonObjectOf(vararg value: Pair<String, JsonElement<*>>) = JsonObject(value.toMap())
	
	@JsonDsl
	inline fun jsonArray(value: Iterable<JsonElement<*>>) = JsonArray(value.toList())
	
	@JsonDsl
	inline fun jsonArrayOf(vararg value: JsonElement<*>) = JsonArray(value.toList())
}


@JsonDsl
inline fun json(builder: Json.() -> Any?) = Json().also { it.rootElement = it.builder().toJsonElement() }

@JsonDsl
inline fun jsonTree(builder: Json.() -> JsonElement<*>) = Json().also { it.rootElement = it.builder() }

//REGION Internal extensions

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
