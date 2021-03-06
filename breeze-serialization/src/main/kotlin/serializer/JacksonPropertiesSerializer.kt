// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.fasterxml.jackson.dataformat.javaprop.*
import com.windea.breezeframework.core.model.*
import java.lang.reflect.*
import java.util.*

/**
 * 由Jackson实现的Properties的序列化器。
 *
 * @see com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper
 */
 class JacksonPropertiesSerializer : PropertiesSerializer, JacksonSerializer, Configurable<JavaPropsMapper> {
	val mapper by lazy { JavaPropsMapper() }

	init {
		mapper.findAndRegisterModules()
	}

	override fun configure(block: JavaPropsMapper.() -> Unit) {
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
