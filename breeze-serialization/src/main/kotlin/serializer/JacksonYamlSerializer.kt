// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.fasterxml.jackson.dataformat.yaml.*
import com.windea.breezeframework.core.*
import java.lang.reflect.*

/**
 * 由Jackson实现的Yaml的序列化器。
 *
 * @see com.fasterxml.jackson.dataformat.yaml.YAMLMapper
 */
class JacksonYamlSerializer : YamlSerializer, JacksonSerializer, Configurable<YAMLMapper> {
	val mapper by lazy { YAMLMapper() }

	init {
		mapper.findAndRegisterModules()
	}

	override fun configure(block: YAMLMapper.() -> Unit) {
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

	override fun serializeAll(value: List<Any>): String {
		throw UnsupportedOperationException("Cannot not find suitable method to delegate.")
	}

	override fun deserializeAll(value: String): List<Any> {
		throw UnsupportedOperationException("Cannot not find suitable method to delegate.")
	}
}
