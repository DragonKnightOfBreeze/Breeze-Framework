// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.fasterxml.jackson.dataformat.xml.*
import com.windea.breezeframework.core.model.*
import java.lang.reflect.*

/**
 * 由Jackson实现的Xml的序列化器。
 *
 * @see com.fasterxml.jackson.dataformat.xml.XmlMapper
 */
 class JacksonXmlSerializer : XmlSerializer, JacksonSerializer, Configurable<XmlMapper> {
	val mapper by lazy { XmlMapper() }

	init {
		mapper.findAndRegisterModules()
	}

	override fun configure(block: XmlMapper.() -> Unit) {
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
