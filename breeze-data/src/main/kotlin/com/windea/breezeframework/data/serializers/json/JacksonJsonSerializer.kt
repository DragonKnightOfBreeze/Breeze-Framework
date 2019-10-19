package com.windea.breezeframework.data.serializers.json

import com.fasterxml.jackson.databind.json.*
import java.io.*
import java.lang.reflect.*

object JacksonJsonSerializer : JsonSerializer {
	@PublishedApi internal val mapper = JsonMapper()
	
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

object JacksonJsonSerializerConfig : JsonSerializerConfig {
	inline fun configure(builder: (JsonMapper) -> Unit) = builder(JacksonJsonSerializer.mapper)
}
