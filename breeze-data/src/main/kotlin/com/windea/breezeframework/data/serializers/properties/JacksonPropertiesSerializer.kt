package com.windea.breezeframework.data.serializers.properties

import com.fasterxml.jackson.dataformat.javaprop.*
import java.io.*
import java.util.*

class JacksonPropertiesSerializer : PropertiesSerializer<JacksonPropertiesSerializer, JavaPropsMapper> {
	private val mapper = JavaPropsMapper()
	
	
	/**配置持久化选项。这个方法必须首先被调用。*/
	override fun configure(handler: (JavaPropsMapper) -> Unit): JacksonPropertiesSerializer {
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
	
	override fun <T> load(properties: Properties, type: Class<T>): T {
		return mapper.readPropertiesAs(properties, type)
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
	
	override fun <T : Any> dump(data: T, properties: Properties) {
		properties += mapper.writeValueAsProperties(data)
	}
}
