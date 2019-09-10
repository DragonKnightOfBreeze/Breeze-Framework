package com.windea.breezeframework.data.serializers.json

import com.fasterxml.jackson.databind.json.*
import java.io.*

object JacksonJsonSerializer : JsonSerializer {
	@PublishedApi internal val mapper = JsonMapper()
	
	
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

object JacksonJsonSerializerConfig : JsonSerializerConfig {
	inline operator fun invoke(builder: (JsonMapper) -> Unit) = builder(JacksonJsonSerializer.mapper)
}
