package com.windea.breezeframework.data.serializers.yaml

import com.fasterxml.jackson.dataformat.yaml.*
import java.io.*

class JacksonYamlSerializer : YamlSerializer<JacksonYamlSerializer, YAMLMapper> {
	private val mapper = YAMLMapper()
	
	
	/**配置持久化选项。这个方法必须首先被调用。*/
	override fun configure(handler: (YAMLMapper) -> Unit): JacksonYamlSerializer {
		return this.also { handler(mapper) }
	}
	
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
		throw UnsupportedOperationException("Could not find suitable methods to delegate in yamlMapper.")
	}
	
	override fun loadAll(file: File): List<Any> {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in yamlMapper.")
	}
	
	override fun loadAll(reader: Reader): List<Any> {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in yamlMapper.")
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
		throw UnsupportedOperationException("Could not find suitable methods to delegate in yamlMapper.")
	}
	
	override fun <T : Any> dumpAll(data: List<T>, file: File) {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in yamlMapper.")
	}
	
	override fun <T : Any> dumpAll(data: List<T>, writer: Writer) {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in yamlMapper.")
	}
}
