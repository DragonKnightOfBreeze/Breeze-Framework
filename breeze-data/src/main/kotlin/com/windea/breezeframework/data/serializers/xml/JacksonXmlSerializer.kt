package com.windea.breezeframework.data.serializers.xml

import com.fasterxml.jackson.dataformat.xml.*
import java.io.*
import java.lang.reflect.*

object JacksonXmlSerializer : XmlSerializer {
	@PublishedApi internal val mapper = XmlMapper()
	
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
	
	override fun <T> dump(data: T): String {
		return mapper.writeValueAsString(data)
	}
	
	override fun <T> dump(data: T, file: File) {
		return mapper.writeValue(file, data)
	}
}

object JacksonXmlSerializerConfig : XmlSerializerConfig {
	inline fun configure(builder: (XmlMapper) -> Unit) = builder(JacksonXmlSerializer.mapper)
}
