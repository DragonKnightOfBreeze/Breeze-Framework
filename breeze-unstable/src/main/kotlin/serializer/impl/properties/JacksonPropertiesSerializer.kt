// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serializer.impl.properties

import com.fasterxml.jackson.dataformat.javaprop.*
import com.fasterxml.jackson.module.kotlin.*
import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.serializer.impl.*
import java.io.*
import java.lang.reflect.*
import java.util.*

/**
 * 由Jackson实现的Properties的序列化器。
 * @see com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper
 */
internal object JacksonPropertiesSerializer : PropertiesSerializer, JacksonSerializer<JavaPropsMapper> {
	internal val mapper = JavaPropsMapper()
	override val delegate: JavaPropsMapper get() = mapper

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

	override fun <T : Any> read(properties: Properties, type: Class<T>): T {
		return mapper.readPropertiesAs(properties, type)
	}

	override fun <T : Any> read(properties: Properties, type: Type): T {
		return mapper.readPropertiesAs(properties, mapper.typeFactory.constructType(type))
	}

	override fun <T : Any> write(data: T): String {
		return mapper.writeValueAsString(data)
	}

	override fun <T : Any> write(data: T, file: File) {
		return mapper.writeValue(file, data)
	}

	override fun <T : Any> write(data: T, properties: Properties) {
		properties += mapper.writeValueAsProperties(data)
	}
}

