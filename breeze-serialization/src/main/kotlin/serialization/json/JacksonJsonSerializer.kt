// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.json

import com.fasterxml.jackson.databind.json.*
import icu.windea.breezeframework.serialization.*
import java.lang.reflect.*

/**
 * 由Jackson委托实现的Json数据的序列化器。
 *
 * @see com.fasterxml.jackson.databind.json.JsonMapper
 */
class JacksonJsonSerializer(
	val mapper: JsonMapper = JsonMapper()
) : JsonSerializer, JacksonSerializer {
	init {
		mapper.findAndRegisterModules()
	}

	override fun <T> serialize(target: T): String {
		return mapper.writeValueAsString(target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		return mapper.readValue(value, type)
	}

	override fun <T> deserialize(value: String, type: Type): T {
		return mapper.readValue(value, mapper.typeFactory.constructType(type))
	}
}
