package com.windea.breezeframework.serializer.impl.yaml

import com.fasterxml.jackson.dataformat.yaml.*
import com.fasterxml.jackson.module.kotlin.*
import com.windea.breezeframework.core.extensions.*
import java.io.*
import java.lang.reflect.*

internal object JacksonYamlSerializer : YamlSerializer {
	internal val mapper = YAMLMapper()

	init {
		if(presentInClassPath("com.fasterxml.jackson.module.kotlin.KotlinModule")) mapper.registerKotlinModule()
	}

	override fun <T> read(string: String, type: Class<T>): T {
		return mapper.readValue(string, type)
	}

	override fun <T> read(file: File, type: Class<T>): T {
		return mapper.readValue(file, type)
	}

	override fun <T> read(string: String, type: Type): T {
		return mapper.readValue(string, mapper.typeFactory.constructType(type))
	}

	override fun <T> read(file: File, type: Type): T {
		return mapper.readValue(file, mapper.typeFactory.constructType(type))
	}

	override fun readAll(string: String): List<Any?> {
		throw UnsupportedOperationException("Could not find suitable methods to delegate.")
	}

	override fun readAll(file: File): List<Any?> {
		throw UnsupportedOperationException("Could not find suitable methods to delegate.")
	}

	override fun <T> write(data: T): String {
		return mapper.writeValueAsString(data)
	}

	override fun <T> write(data: T, file: File) {
		return mapper.writeValue(file, data)
	}

	override fun <T> writeAll(data: Iterable<T>): String {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}

	override fun <T> writeAll(data: Iterable<T>, file: File) {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}
}

