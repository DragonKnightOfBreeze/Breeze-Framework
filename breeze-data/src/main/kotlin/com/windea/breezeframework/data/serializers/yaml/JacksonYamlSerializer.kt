package com.windea.breezeframework.data.serializers.yaml

import com.fasterxml.jackson.dataformat.yaml.*
import com.fasterxml.jackson.module.kotlin.*
import com.windea.breezeframework.reflect.extensions.*
import java.io.*
import java.lang.reflect.*

internal object JacksonYamlSerializer : YamlSerializer {
	internal val mapper = YAMLMapper()
	
	init {
		if(checkClassForName("com.fasterxml.jackson.module.kotlin.KotlinModule")) mapper.registerKotlinModule()
	}
	
	override fun <T> load(string: String, type: Class<T>): T {
		return mapper.readValue(string, type)
	}
	
	override fun <T> load(file: File, type: Class<T>): T {
		return mapper.readValue(file, type)
	}
	
	override fun <T> load(string: String, type: Type): T {
		return mapper.readValue(string, mapper.typeFactory.constructType(type))
	}
	
	override fun <T> load(file: File, type: Type): T {
		return mapper.readValue(file, mapper.typeFactory.constructType(type))
	}
	
	override fun loadAll(string: String): List<Any?> {
		throw UnsupportedOperationException("Could not find suitable methods to delegate.")
	}
	
	override fun loadAll(file: File): List<Any?> {
		throw UnsupportedOperationException("Could not find suitable methods to delegate.")
	}
	
	override fun <T> dump(data: T): String {
		return mapper.writeValueAsString(data)
	}
	
	override fun <T> dump(data: T, file: File) {
		return mapper.writeValue(file, data)
	}
	
	override fun <T> dumpAll(data: List<T>): String {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}
	
	override fun <T> dumpAll(data: List<T>, file: File) {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}
}

object JacksonYamlSerializerConfig : YamlSerializerConfig {
	fun configure(builder: (YAMLMapper) -> Unit) = builder(JacksonYamlSerializer.mapper)
}
