// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.core.model.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.lang.reflect.*

/**
 * 由Kotlinx Serialization实现的Json的序列化器。
 *
 * @see kotlinx.serialization.json.Json
 */
@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalSerializationApi::class)
class KotlinxJsonSerializer : JsonSerializer, KotlinxSerializer, Configurable<JsonBuilder> {
	private var jsonDelegate: Json = Json

	val json by lazy { jsonDelegate }

	override fun configure(block: JsonBuilder.() -> Unit) {
		jsonDelegate = Json(Json, block)
	}

	override fun <T> serialize(target: T): String {
		//FIXME 这里必须要指定类型，否则无法序列化泛型类型的对象
		if(target == null) return "null"
		return json.encodeToString(json.serializersModule.serializer(target.javaClass), target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		return json.decodeFromString(json.serializersModule.serializer(type), value) as T
	}

	override fun <T> deserialize(value: String, type: Type): T {
		return json.decodeFromString(json.serializersModule.serializer(type), value) as T
	}
}
