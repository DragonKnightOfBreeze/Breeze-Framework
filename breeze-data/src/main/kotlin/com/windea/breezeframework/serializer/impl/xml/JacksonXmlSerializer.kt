package com.windea.breezeframework.serializer.impl.xml

import com.fasterxml.jackson.dataformat.xml.*
import com.fasterxml.jackson.module.kotlin.*
import com.windea.breezeframework.core.extensions.*
import java.io.*
import java.lang.reflect.*

internal object JacksonXmlSerializer : XmlSerializer {
	internal val mapper = XmlMapper()

	init {
		if(presentInClassPath("com.fasterxml.jackson.module.kotlin.KotlinModule")) mapper.registerKotlinModule()
	}

	override fun <T> read(string: String, type: Class<T>): T {
		return mapper.readValue(string, type)
	}

	override fun <T> read(file: File, type: Class<T>): T {
		return mapper.readValue(file, type)
	}

	override fun <T> read(string: String, type: Type): T {
		return mapper.readValue(string, mapper.typeFactory.constructType(type))
	}

	override fun <T> read(file: File, type: Type): T {
		return mapper.readValue(file, mapper.typeFactory.constructType(type))
	}

	override fun <T> write(data: T): String {
		return mapper.writeValueAsString(data)
	}

	override fun <T> write(data: T, file: File) {
		return mapper.writeValue(file, data)
	}
}

