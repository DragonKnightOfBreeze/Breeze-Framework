// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serializer.impl.json

import com.windea.breezeframework.serializer.impl.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*
import java.io.*
import java.lang.reflect.*
import kotlin.reflect.full.*

/**
 * 由KotlinxSerialization实现的Json的序列化器。
 * @see kotlinx.serialization.json.Json
 */
@Suppress("UNCHECKED_CAST")
internal object KotlinJsonSerializer : JsonSerializer, KotlinSerializer<Json> {
	internal val json by lazy { Json }
	override val delegate: Json get() = json

	override fun <T : Any> read(string: String, type: Class<T>): T {
		return json.decodeFromString(json.serializersModule.getContextualOrDefault(type.kotlin.createType()), string)
	}

	override fun <T : Any> read(string: String, type: Type): T {
		return json.decodeFromString(serializer(type), string) as T
	}

	override fun <T : Any> read(file: File, type: Class<T>): T {
		return json.decodeFromString(json.serializersModule.getContextualOrDefault(type.kotlin.createType()), file.readText())
	}

	override fun <T : Any> read(file: File, type: Type): T {
		return json.decodeFromString(serializer(type), file.readText()) as T
	}

	override fun <T : Any> write(data: T): String {
		return json.encodeToString(json.serializersModule.getContextualOrDefault(data::class.createType()), data)
	}

	override fun <T : Any> write(data: T, file: File) {
		return json.encodeToString(json.serializersModule.getContextualOrDefault(data::class.createType()), data).let { file.writeText(it) }
	}
}
