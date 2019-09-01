@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.core.loaders.impl

import com.fasterxml.jackson.dataformat.javaprop.*
import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.loaders.*
import java.io.*
import java.util.*

@NotTested
class JacksonPropertiesLoader : PropertiesLoader {
	private val mapper = JavaPropsMapper()
	private val schema = JavaPropsSchema()
	
	
	fun configureMapper(handler: (JavaPropsMapper) -> Unit): JacksonPropertiesLoader {
		handler.invoke(mapper)
		return this
	}
	
	fun configureSchema(handler: (JavaPropsSchema) -> Unit): JacksonPropertiesLoader {
		handler.invoke(schema)
		return this
	}
	
	
	override fun <T : Any> fromString(string: String, type: Class<T>): T {
		return mapper.readPropertiesAs(properties(StringReader(string)), type)
	}
	
	override fun fromString(string: String): Map<String, Any?> {
		return fromString(string, Map::class.java) as Map<String, Any?>
	}
	
	override fun <T : Any> fromFile(path: String, type: Class<T>): T {
		return mapper.readPropertiesAs(properties(FileReader(path)), type)
	}
	
	override fun fromFile(path: String): Map<String, Any?> {
		return fromFile(path, Map::class.java) as Map<String, Any?>
	}
	
	override fun <T : Any> toString(data: T): String {
		return mapper.writeValueAsString(data)
	}
	
	override fun <T : Any> toFile(data: T, path: String) {
		File(path).writeText(toString(data))
	}
	
	private fun properties(reader: Reader): Properties {
		val properties = Properties()
		properties.load(reader)
		return properties
	}
}
