package com.windea.breezeframework.data.serializers.yaml

import com.fasterxml.jackson.dataformat.yaml.*
import java.io.*


object JacksonYamlSerializer : YamlSerializer {
	@PublishedApi internal val mapper = YAMLMapper()
	
	
	override fun <T : Any> load(string: String, type: Class<T>): T {
		return mapper.readValue(string, type)
	}
	
	override fun <T : Any> load(file: File, type: Class<T>): T {
		return mapper.readValue(file, type)
	}
	
	override fun <T : Any> load(reader: Reader, type: Class<T>): T {
		return mapper.readValue(reader, type)
	}
	
	override fun loadAll(string: String): List<Any> {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}
	
	override fun loadAll(file: File): List<Any> {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}
	
	override fun loadAll(reader: Reader): List<Any> {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}
	
	override fun <T : Any> dump(data: T): String {
		return mapper.writeValueAsString(data)
	}
	
	override fun <T : Any> dump(data: T, file: File) {
		return mapper.writeValue(file, data)
	}
	
	override fun <T : Any> dump(data: T, writer: Writer) {
		return mapper.writeValue(writer, data)
	}
	
	override fun <T : Any> dumpAll(data: List<T>): String {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}
	
	override fun <T : Any> dumpAll(data: List<T>, file: File) {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}
	
	override fun <T : Any> dumpAll(data: List<T>, writer: Writer) {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}
}

object JacksonYamlSerializerConfig : YamlSerializerConfig {
	inline operator fun invoke(builder: (YAMLMapper) -> Unit) = builder(JacksonYamlSerializer.mapper)
}
