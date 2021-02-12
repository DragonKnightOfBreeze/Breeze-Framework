// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.fasterxml.jackson.databind.json.*
import com.windea.breezeframework.core.model.*
import java.lang.reflect.*

/**
 * 由Jackson实现的Json的序列化器。
 *
 * @see com.fasterxml.jackson.databind.json.JsonMapper
 */
class JacksonJsonSerializer : JsonSerializer, JacksonSerializer, Configurable<JsonMapper> {
	val mapper by lazy { JsonMapper() }

	init {
		mapper.findAndRegisterModules()
	}

	override fun configure(block: JsonMapper.() -> Unit) {
		mapper.block()
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
