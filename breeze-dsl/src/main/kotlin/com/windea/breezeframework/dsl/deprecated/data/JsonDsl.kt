package com.windea.breezeframework.dsl.deprecated.data

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.deprecated.*
import com.windea.breezeframework.dsl.deprecated.data.JsonDslConfig.defaultName
import com.windea.breezeframework.dsl.deprecated.data.JsonDslConfig.indent
import com.windea.breezeframework.dsl.deprecated.data.JsonDslConfig.quote
import java.lang.annotation.*

/**Json Dsl。*/
@NotTested
@NotRecommended("可以直接从集合结构生成Json文本")
data class JsonDsl @PublishedApi internal constructor(
	override val name: String,
	var root: JsonCollection<*> = JsonObject()
) : Dsl, JsonDslElement {
	override fun toString(): String {
		return root.toString()
	}
}

/**Json Dsl的配置。*/
object JsonDslConfig {
	const val defaultName: String = "json"
	var indentSize: Int = 2
		set(value) {
			field = value.coerceIn(2, 8)
		}
	var preferDoubleQuote: Boolean = true
	
	internal val indent get() = " ".repeat(indentSize)
	internal val quote get() = if(preferDoubleQuote) "\"" else "'"
}


/**Json Dsl标记。*/
@DslMarker
internal annotation class JsonDslMarker

/**扩展的Json功能。*/
@MustBeDocumented
@Inherited
internal annotation class JsonDslExtendedFeature


/**Json Dsl的元素。*/
@JsonDslMarker
interface JsonDslElement

/**Json Dsl的可换行元素。*/
interface JsonDslNewLineElement : JsonDslElement {
	var newLine: Boolean
}

/**Json Dsl的可以空行分割内容的元素。*/
interface JsonDslBlankLineElement : JsonDslElement {
	var blankLineSize: Int
}


/**Json Dsl的项元素。*/
abstract class JsonItem<T>(
	open val value: T
) : JsonDslElement

/**Json布尔项。*/
data class JsonBoolean @PublishedApi internal constructor(
	override val value: Boolean
) : JsonItem<Boolean>(value) {
	override fun toString(): String {
		return value.toString()
	}
}

/**Json整数项。*/
data class JsonInteger @PublishedApi internal constructor(
	override val value: Int
) : JsonItem<Int>(value) {
	override fun toString(): String {
		return value.toString()
	}
}

/**Json数值项。*/
data class JsonNumber @PublishedApi internal constructor(
	override val value: Float
) : JsonItem<Float>(value) {
	override fun toString(): String {
		return value.toString()
	}
}

/**Json字符串项。*/
data class JsonString @PublishedApi internal constructor(
	override val value: String
) : JsonItem<String>(value) {
	override fun toString(): String {
		return "$quote${value.escape()}$quote"
	}
}


/**Json属性。*/
data class JsonProperty<T> @PublishedApi internal constructor(
	val key: String,
	val value: JsonItem<T>?
) : JsonDslElement {
	override fun toString(): String {
		return "$quote$key$quote: $value"
	}
}


/**Json Dsl的集合元素。*/
abstract class JsonCollection<T : MutableList<*>>(
	override val value: T
) : JsonItem<T>(value)

/**Json数组。*/
data class JsonArray @PublishedApi internal constructor(
	override val value: MutableList<JsonItem<*>?> = mutableListOf(),
	override var newLine: Boolean = true,
	override var blankLineSize: Int = 0
) : JsonCollection<MutableList<JsonItem<*>?>>(value), JsonDslNewLineElement, JsonDslBlankLineElement {
	override fun toString(): String {
		val prefix = if(newLine) "[\n" else "["
		val delimiter = if(newLine) ",\n${"\n".repeat(blankLineSize)}" else ", "
		val suffix = if(newLine) "\n]" else "]"
		return value.joinToString(delimiter, prefix, suffix) { "$indent$it" }
	}
	
	operator fun JsonItem<*>?.plus(item: JsonItem<*>?) = item
}

/**Json对象。*/
data class JsonObject @PublishedApi internal constructor(
	override val value: MutableList<JsonProperty<*>> = mutableListOf(),
	override var newLine: Boolean = true,
	override var blankLineSize: Int = 0
) : JsonCollection<MutableList<JsonProperty<*>>>(value), JsonDslNewLineElement, JsonDslBlankLineElement {
	override fun toString(): String {
		val prefix = if(newLine) "{\n" else "}"
		val delimiter = if(newLine) ",\n${"\n".repeat(blankLineSize)}" else ", "
		val suffix = if(newLine) "\n}" else "}"
		return value.joinToString(delimiter, prefix, suffix) { "$indent$it" }
	}
	
	operator fun JsonProperty<*>?.plus(prop: JsonProperty<*>?) = prop
}


/**构建Json Dsl。*/
inline fun Dsl.Companion.json(name: String = defaultName, content: JsonDsl.() -> Unit) = JsonDsl(name).also { it.content() }

/**配置Json Dsl。*/
inline fun DslConfig.Companion.json(config: JsonDslConfig.() -> Unit) = JsonDslConfig.config()


/**创建Json数组。*/
inline fun JsonDsl.array(value: JsonArray.() -> Unit) = JsonArray().also { it.value() }.also { this.root = it }

/**创建Json对象。*/
inline fun JsonDsl.obj(value: JsonObject.() -> Unit) = JsonObject().also { it.value() }.also { this.root = it }


/**创建Json项。*/
fun JsonArray.item() = run { this.value.add(null) }

/**创建Json项。*/
fun JsonArray.item(value: Boolean) = JsonBoolean(value).also { this.value += it }

/**创建Json项。*/
fun JsonArray.item(value: Int) = JsonInteger(value).also { this.value += it }

/**创建Json项。*/
fun JsonArray.item(value: Float) = JsonNumber(value).also { this.value += it }

/**创建Json项。*/
fun JsonArray.item(value: String) = JsonString(value).also { this.value += it }

/**创建Json数组。*/
inline fun JsonArray.array(value: JsonArray.() -> Unit) = JsonArray().also { it.value() }.also { this.value += it }

/**创建Json对象。*/
inline fun JsonArray.obj(value: JsonObject.() -> Unit) = JsonObject().also { it.value() }.also { this.value += it }


/**创建Json属性。*/
fun JsonObject.prop(key: String) = run { this.value += JsonProperty<Any?>(key, null) }

/**创建Json属性。*/
fun JsonObject.prop(key: String, value: Boolean) = JsonProperty(key, JsonBoolean(value)).also { this.value += it }

/**创建Json属性。*/
fun JsonObject.prop(key: String, value: Int) = JsonProperty(key, JsonInteger(value)).also { this.value += it }

/**创建Json属性。*/
fun JsonObject.prop(key: String, value: Float) = JsonProperty(key, JsonNumber(value)).also { this.value += it }

/**创建Json属性。*/
fun JsonObject.prop(key: String, value: String) = JsonProperty(key, JsonString(value)).also { this.value += it }

/**创建Json数组属性。*/
inline fun JsonObject.arrayProp(key: String, value: JsonArray.() -> Unit) = JsonProperty(key, JsonArray().also { it.value() }).also { this.value += it }

/**创建Json对象属性。*/
inline fun JsonObject.objProp(key: String, value: JsonObject.() -> Unit) = JsonProperty(key, JsonObject().also { it.value() }).also { this.value += it }


/**配置当前元素的换行。默认换行。*/
fun <T : JsonDslNewLineElement> T.n(newLine: Boolean = true): T {
	return this.also { it.newLine = newLine }
}

/**配置当前元素的内容间空行数量。默认为1。*/
fun <T : JsonDslBlankLineElement> T.bn(blankLineSize: Int = 1): T {
	return this.also { it.blankLineSize = blankLineSize }
}
