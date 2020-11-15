// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serializer.impl.xml

import com.fasterxml.jackson.dataformat.xml.*
import com.fasterxml.jackson.module.kotlin.*
import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.serializer.impl.*
import java.io.*
import java.lang.reflect.*

/**
 * 由Jackson实现的Xml的序列化器。
 * @see com.fasterxml.jackson.dataformat.xml.XmlMapper
 */
internal object JacksonXmlSerializer : XmlSerializer, JacksonSerializer<XmlMapper> {
	internal val mapper = XmlMapper()
	override val delegate: XmlMapper get() = mapper

	init {
		if(presentInClassPath("com.fasterxml.jackson.module.kotlin.KotlinModule")) mapper.registerKotlinModule()
	}

	override fun <T : Any> read(string: String, type: Class<T>): T {
		return mapper.readValue(string, type)
	}

	override fun <T : Any> read(string: String, type: Type): T {
		return mapper.readValue(string, mapper.typeFactory.constructType(type))
	}

	override fun <T : Any> read(file: File, type: Class<T>): T {
		return mapper.readValue(file, type)
	}

	override fun <T : Any> read(file: File, type: Type): T {
		return mapper.readValue(file, mapper.typeFactory.constructType(type))
	}

	override fun <T : Any> write(data: T): String {
		return mapper.writeValueAsString(data)
	}

	override fun <T : Any> write(data: T, file: File) {
		return mapper.writeValue(file, data)
	}
}

