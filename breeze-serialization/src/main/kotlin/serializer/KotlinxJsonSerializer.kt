// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.core.model.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.lang.reflect.*

/**
 * 由Kotlinx Serialization委托实现的Json数据的序列化器。
 *
 * @see kotlinx.serialization.json.Json
 */
@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalSerializationApi::class)
class KotlinxJsonSerializer(
	val json:Json = Json.Default
) : JsonSerializer, KotlinxSerializer {
	//NOTE 这里必须要指定类型，否则无法序列化泛型类型的对象
	override fun <T> serialize(target: T): String {
		if(target == null) return "null"
		return json.encodeToString(json.serializersModule.serializer(target.javaClass), target)
	}

	override fun <T> serialize(target: T, type: Class<T>): String {
		return json.encodeToString(json.serializersModule.serializer(type) as SerializationStrategy<T>, target)
	}

	override fun <T> serialize(target: T, type: Type): String {
		return json.encodeToString(json.serializersModule.serializer(type) as SerializationStrategy<T>, target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		return json.decodeFromString(json.serializersModule.serializer(type), value) as T
	}

	override fun <T> deserialize(value: String, type: Type): T {
		return json.decodeFromString(json.serializersModule.serializer(type), value) as T
	}
}
