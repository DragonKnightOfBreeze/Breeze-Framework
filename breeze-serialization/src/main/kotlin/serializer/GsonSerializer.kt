// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.serializer

import com.google.gson.*
import icu.windea.breezeframework.core.model.*
import java.lang.reflect.*

/**
 * 由Gson委托实现的Json数据的序列化器。
 *
 * @see com.google.gson.Gson
 */
class GsonSerializer(
	val gsonBuilder: GsonBuilder = GsonBuilder()
) : JsonSerializer, DelegateSerializer {
	val gson: Gson = gsonBuilder.create()

	override fun <T> serialize(target: T): String {
		return gson.toJson(target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		return gson.fromJson(value, type)
	}

	override fun <T> deserialize(value: String, type: Type): T {
		return gson.fromJson(value, type)
	}
}
