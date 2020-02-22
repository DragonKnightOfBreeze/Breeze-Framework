package com.windea.breezeframework.serializer.impl.yaml

import com.fasterxml.jackson.dataformat.yaml.*
import com.fasterxml.jackson.module.kotlin.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.serializer.impl.*
import java.io.*
import java.lang.reflect.*

/**由Jackson实现的yaml的序列化器。*/
internal object JacksonYamlSerializer : YamlSerializer, JacksonSerializer<YAMLMapper> {
	internal val mapper = YAMLMapper()
	override val delegate: YAMLMapper get() = mapper

	init {
		if(presentInClassPath("com.fasterxml.jackson.module.kotlin.KotlinModule")) mapper.registerKotlinModule()
	}

	override fun <T : Any> read(string: String, type: Class<T>): T {
		return mapper.readValue(string, type)
	}

	override fun <T : Any> read(file: File, type: Class<T>): T {
		mapper.readValue<Int>("")
		return mapper.readValue(file, type)
	}

	override fun <T : Any> read(string: String, type: Type): T {
		return mapper.readValue(string, mapper.typeFactory.constructType(type))
	}

	override fun <T : Any> read(file: File, type: Type): T {
		return mapper.readValue(file, mapper.typeFactory.constructType(type))
	}

	override fun readAll(string: String): List<Any?> {
		throw UnsupportedOperationException("Could not find suitable methods to delegate.")
	}

	override fun readAll(file: File): List<Any?> {
		throw UnsupportedOperationException("Could not find suitable methods to delegate.")
	}

	override fun <T : Any> write(data: T): String {
		return mapper.writeValueAsString(data)
	}

	override fun <T : Any> write(data: T, file: File) {
		return mapper.writeValue(file, data)
	}

	override fun <T : Any> writeAll(data: Iterable<T>): String {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}

	override fun <T : Any> writeAll(data: Iterable<T>, file: File) {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}
}

