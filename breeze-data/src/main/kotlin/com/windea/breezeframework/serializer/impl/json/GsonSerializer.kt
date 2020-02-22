package com.windea.breezeframework.serializer.impl.json

import com.google.gson.*
import java.io.*
import java.lang.reflect.*

internal object GsonSerializer : JsonSerializer {
	internal val gsonBuilder = GsonBuilder()

	private val gson by lazy { gsonBuilder.create() }
	private val gsonWithPrettyPrint by lazy { gson.newBuilder().setPrettyPrinting().create() }

	override fun <T> read(string: String, type: Class<T>): T {
		return gson.fromJson(string, type)
	}

	override fun <T> read(file: File, type: Class<T>): T {
		return gson.fromJson(file.reader(), type)
	}

	override fun <T> read(string: String, type: Type): T {
		return gson.fromJson(string, type)
	}

	override fun <T> read(file: File, type: Type): T {
		return gson.fromJson(file.reader(), type)
	}

	override fun <T> write(data: T): String {
		return gson.toJson(data)
	}

	override fun <T> write(data: T, file: File) {
		//Do not use gson.toJson(Any, Appendable)
		//Default to pretty print
		gsonWithPrettyPrint.toJson(data).let { file.writeText(it) }
	}
}
