package com.windea.breezeframework.data.serializers.json

import com.google.gson.*
import java.io.*

class GsonJsonSerializer : JsonSerializer<GsonJsonSerializer, GsonBuilder> {
	private val gsonBuilder = GsonBuilder()
	
	private val gson by lazy { gsonBuilder.create() }
	
	init {
		gsonBuilder.setPrettyPrinting()
	}
	
	
	/**配置持久化选项。这个方法必须首先被调用。*/
	override fun configure(handler: (GsonBuilder) -> Unit): GsonJsonSerializer {
		return this.also { handler(gsonBuilder) }
	}
	
	override fun <T : Any> load(string: String, type: Class<T>): T {
		return gson.fromJson(string, type)
	}
	
	override fun <T : Any> load(file: File, type: Class<T>): T {
		return gson.fromJson(file.reader(), type)
	}
	
	override fun <T : Any> load(reader: Reader, type: Class<T>): T {
		return gson.fromJson(reader, type)
	}
	
	override fun <T : Any> dump(data: T): String {
		return gson.toJson(data)
	}
	
	override fun <T : Any> dump(data: T, file: File) {
		//Do not use gson.toJson(Any, Appendable)
		gson.toJson(data).let { file.writeText(it) }
	}
	
	override fun <T : Any> dump(data: T, writer: Writer) {
		//Do not use gson.toJson(Any, Appendable)
		gson.toJson(data).let { writer.write(it) }
	}
}
