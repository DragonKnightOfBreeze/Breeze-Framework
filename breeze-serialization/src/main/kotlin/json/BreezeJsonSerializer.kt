// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.json

import icu.windea.breezeframework.serialization.*
import java.lang.reflect.*

/**
 * 框架本身实现的Json数据的序列化器。
 */
class BreezeJsonSerializer(
	val config: JsonConfig = JsonConfig()
) : JsonSerializer, BreezeSerializer {
	private val reader = JsonReader(config)
	private val writer = JsonWriter(config)

	override fun <T> serialize(target: T): String {
		return writer.write(target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		return reader.read(value,type)
	}

	override fun <T> deserialize(value: String, type: Type): T {
		return reader.read(value,type)
	}
}
