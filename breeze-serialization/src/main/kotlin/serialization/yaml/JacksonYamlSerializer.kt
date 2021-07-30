// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.yaml

import com.fasterxml.jackson.dataformat.yaml.*
import icu.windea.breezeframework.serialization.*
import java.lang.reflect.*

/**
 * 由Jackson委托实现的Yaml数据的序列化器。
 *
 * @see com.fasterxml.jackson.dataformat.yaml.YAMLMapper
 */
class JacksonYamlSerializer(
	val mapper: YAMLMapper = YAMLMapper()
) : YamlSerializer, JacksonSerializer {
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

	override fun serializeAll(value: List<Any>): String {
		throw UnsupportedOperationException("Cannot not find suitable method to delegate.")
	}

	override fun deserializeAll(value: String): List<Any> {
		throw UnsupportedOperationException("Cannot not find suitable method to delegate.")
	}
}
