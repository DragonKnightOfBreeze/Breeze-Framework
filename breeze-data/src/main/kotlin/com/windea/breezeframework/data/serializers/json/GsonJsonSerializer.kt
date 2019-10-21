package com.windea.breezeframework.data.serializers.json

import com.google.gson.*
import java.io.*
import java.lang.reflect.*

object GsonJsonSerializer : JsonSerializer {
	@PublishedApi internal val gsonBuilder = GsonBuilder()
	
	private val gson by lazy { gsonBuilder.create() }
	private val gsonWithPrettyPrint by lazy { gson.newBuilder().setPrettyPrinting().create() }
	
	override fun <T> load(string: String, type: Class<T>): T {
		return gson.fromJson(string, type)
	}
	
	override fun <T> load(file: File, type: Class<T>): T {
		return gson.fromJson(file.reader(), type)
	}
	
	override fun <T> load(string: String, type: Type): T {
		return gson.fromJson(string, type)
	}
	
	override fun <T> load(file: File, type: Type): T {
		return gson.fromJson(file.reader(), type)
	}
	
	override fun <T> dump(data: T): String {
		return gson.toJson(data)
	}
	
	override fun <T> dump(data: T, file: File) {
		//Do not use gson.toJson(Any, Appendable)
		//Default to pretty print
		gsonWithPrettyPrint.toJson(data).let { file.writeText(it) }
	}
}

object GsonJsonSerializerConfig : JsonSerializerConfig {
	inline fun configure(builder: (GsonBuilder) -> Unit) = builder(GsonJsonSerializer.gsonBuilder)
}
