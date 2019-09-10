package com.windea.breezeframework.data.serializers.xml

import com.fasterxml.jackson.dataformat.xml.*
import java.io.*

object JacksonXmlSerializer : XmlSerializer {
	@PublishedApi internal val mapper = XmlMapper()
	
	
	override fun <T : Any> load(string: String, type: Class<T>): T {
		return mapper.readValue(string, type)
	}
	
	override fun <T : Any> load(file: File, type: Class<T>): T {
		return mapper.readValue(file, type)
	}
	
	override fun <T : Any> load(reader: Reader, type: Class<T>): T {
		return mapper.readValue(reader, type)
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
}

object JacksonXmlSerializerConfig : XmlSerializerConfig {
	inline operator fun invoke(builder: (XmlMapper) -> Unit) = builder(JacksonXmlSerializer.mapper)
}
