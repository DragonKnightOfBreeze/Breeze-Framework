package com.windea.breezeframework.serializer.impl.json

import com.fasterxml.jackson.databind.json.*
import com.fasterxml.jackson.module.kotlin.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.serializer.impl.*
import java.io.*
import java.lang.reflect.*

/**
 * 由Jackson实现的Json序列化器。
 * @see com.fasterxml.jackson.databind.json.JsonMapper
 */
internal object JacksonJsonSerializer : JsonSerializer, JacksonSerializer<JsonMapper> {
	internal val mapper = JsonMapper()
	override val delegate: JsonMapper get() = mapper

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

