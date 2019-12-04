package com.windea.breezeframework.data.serializers.xml

import com.fasterxml.jackson.dataformat.xml.*
import com.fasterxml.jackson.module.kotlin.*
import com.windea.breezeframework.reflect.extensions.*
import java.io.*
import java.lang.reflect.*

internal object JacksonXmlSerializer : XmlSerializer {
	internal val mapper = XmlMapper()
	
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
	
	override fun <T> dump(data: T): String {
		return mapper.writeValueAsString(data)
	}
	
	override fun <T> dump(data: T, file: File) {
		return mapper.writeValue(file, data)
	}
}

object JacksonXmlSerializerConfig : XmlSerializerConfig {
	fun configure(builder: (XmlMapper) -> Unit) = builder(JacksonXmlSerializer.mapper)
}
