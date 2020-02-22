package com.windea.breezeframework.serializer.impl.properties

import com.fasterxml.jackson.dataformat.javaprop.*
import com.fasterxml.jackson.module.kotlin.*
import com.windea.breezeframework.core.extensions.*
import java.io.*
import java.lang.reflect.*
import java.util.*

internal object JacksonPropertiesSerializer : PropertiesSerializer {
	internal val mapper = JavaPropsMapper()

	init {
		if(presentInClassPath("com.fasterxml.jackson.module.kotlin.KotlinModule")) mapper.registerKotlinModule()
	}

	override fun <T> read(string: String, type: Class<T>): T {
		return mapper.readValue(string, type)
	}

	override fun <T> read(file: File, type: Class<T>): T {
		return mapper.readValue(file, type)
	}

	override fun <T> read(properties: Properties, type: Class<T>): T {
		return mapper.readPropertiesAs(properties, type)
	}

	override fun <T> read(string: String, type: Type): T {
		return mapper.readValue(string, mapper.typeFactory.constructType(type))
	}

	override fun <T> read(file: File, type: Type): T {
		return mapper.readValue(file, mapper.typeFactory.constructType(type))
	}

	override fun <T> read(properties: Properties, type: Type): T {
		return mapper.readPropertiesAs(properties, mapper.typeFactory.constructType(type))
	}

	override fun <T> write(data: T): String {
		return mapper.writeValueAsString(data)
	}

	override fun <T> write(data: T, file: File) {
		return mapper.writeValue(file, data)
	}

	override fun <T> write(data: T, properties: Properties) {
		properties += mapper.writeValueAsProperties(data)
	}
}

