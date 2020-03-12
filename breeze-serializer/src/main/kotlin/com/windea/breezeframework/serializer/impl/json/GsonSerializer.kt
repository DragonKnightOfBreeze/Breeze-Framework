package com.windea.breezeframework.serializer.impl.json

import com.google.gson.*
import java.io.*
import java.lang.reflect.*

/**由Gson实现的json的序列化器。*/
internal object GsonSerializer : JsonSerializer {
	internal val gsonBuilder = GsonBuilder()
	internal val gson by lazy { gsonBuilder.create() }

	override fun <T : Any> read(string: String, type: Class<T>): T {
		return gson.fromJson(string, type)
	}

	override fun <T : Any> read(string: String, type: Type): T {
		return gson.fromJson(string, type)
	}

	override fun <T : Any> read(file: File, type: Class<T>): T {
		return gson.fromJson(file.reader(), type)
	}

	override fun <T : Any> read(file: File, type: Type): T {
		return gson.fromJson(file.reader(), type)
	}

	override fun <T : Any> write(data: T): String {
		return gson.toJson(data)
	}

	override fun <T : Any> write(data: T, file: File) {
		//Do not use gson.toJson(Any, Appendable)
		gson.toJson(data).let { file.writeText(it) }
	}
}
