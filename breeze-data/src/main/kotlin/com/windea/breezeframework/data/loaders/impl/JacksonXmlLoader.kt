@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.data.loaders.impl

import com.fasterxml.jackson.dataformat.xml.*
import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.data.loaders.*
import java.io.*

@NotTested
class JacksonXmlLoader : XmlLoader {
	private val mapper = XmlMapper()
	
	
	fun configureMapper(handler: (XmlMapper) -> Unit): JacksonXmlLoader {
		handler.invoke(mapper)
		return this
	}
	
	
	override fun <T : Any> fromString(string: String, type: Class<T>): T {
		return mapper.readValue(StringReader(string), type)
	}
	
	override fun fromString(string: String): Map<String, Any?> {
		return fromString(string, Map::class.java) as Map<String, Any?>
	}
	
	override fun <T : Any> fromFile(path: String, type: Class<T>): T {
		return mapper.readValue(FileReader(path), type)
	}
	
	override fun fromFile(path: String): Map<String, Any?> {
		return fromFile(path, Map::class.java) as Map<String, Any?>
	}
	
	override fun <T : Any> toString(data: T): String {
		return mapper.writeValueAsString(data)
	}
	
	override fun <T : Any> toFile(data: T, path: String) {
		return mapper.writeValue(FileWriter(path), data)
	}
}
