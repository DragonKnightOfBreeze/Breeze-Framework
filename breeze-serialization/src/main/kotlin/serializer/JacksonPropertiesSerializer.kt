// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.fasterxml.jackson.dataformat.javaprop.*
import com.windea.breezeframework.core.model.*
import java.lang.reflect.*
import java.util.*

/**
 * 由Jackson委托实现的Properties数据的序列化器。
 *
 * @see com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper
 */
 class JacksonPropertiesSerializer(
	val mapper:JavaPropsMapper = JavaPropsMapper()
 ) : PropertiesSerializer, JacksonSerializer{
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

	override fun <T> serializeProperties(target: T): Properties {
		return Properties().apply { this.putAll(mapper.writeValueAsProperties(target)) }
	}

	override fun <T> deserializeProperties(properties: Properties, type: Class<T>): T {
		return mapper.readPropertiesAs(properties, type)
	}

	override fun <T> deserializeProperties(properties: Properties, type: Type): T {
		return mapper.readPropertiesAs(properties, mapper.typeFactory.constructType(type))
	}
}
