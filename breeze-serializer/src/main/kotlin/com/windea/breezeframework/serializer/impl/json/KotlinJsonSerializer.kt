package com.windea.breezeframework.serializer.impl.json

import com.windea.breezeframework.serializer.impl.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*
import java.io.*
import java.lang.reflect.*

/**由KotlinxSerialization实现的json的序列化器。*/
@Suppress("UNCHECKED_CAST")
@UseExperimental(UnstableDefault::class, ImplicitReflectionSerializer::class)
internal object KotlinJsonSerializer : JsonSerializer, KotlinSerializer<Json> {
	internal val jsonBuilder = JsonBuilder()
	internal val json by lazy { Json(jsonBuilder.buildConfiguration()) }
	internal val jsonWithPrettyPrint by lazy { Json(jsonBuilder.buildConfiguration()) }
	override val delegate: Json get() = json

	override fun <T : Any> read(string: String, type: Class<T>): T {
		return json.parse(json.context.getContextualOrDefault(type.kotlin), string)
	}

	override fun <T : Any> read(string: String, type: Type): T {
		return json.parse(serializerByTypeToken(type), string) as T
	}

	override fun <T : Any> read(file: File, type: Class<T>): T {
		return json.parse(json.context.getContextualOrDefault(type.kotlin), file.readText())
	}

	override fun <T : Any> read(file: File, type: Type): T {
		return json.parse(serializerByTypeToken(type), file.readText()) as T
	}

	override fun <T : Any> write(data: T): String {
		return json.stringify(json.context.getContextualOrDefault(data), data)
	}

	override fun <T : Any> write(data: T, file: File) {
		return json.stringify(json.context.getContextualOrDefault(data), data).let { file.writeText(it) }
	}
}
