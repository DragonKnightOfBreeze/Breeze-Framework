@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.data.loaders.impl

import com.google.gson.*
import com.windea.breezeframework.data.loaders.*
import java.io.*

class GsonJsonLoader : JsonLoader {
	private val gsonBuilder = GsonBuilder()
	private val gson get() = gsonBuilder.create()
	
	init {
		gsonBuilder.setPrettyPrinting()
	}
	
	
	fun configureGsonBuilder(handler: (gsonBuilder: GsonBuilder) -> Unit): GsonJsonLoader {
		handler.invoke(gsonBuilder)
		return this
	}
	
	
	override fun <T : Any> fromString(string: String, type: Class<T>): T {
		return gson.fromJson(string, type)
	}
	
	override fun fromString(string: String): Map<String, Any?> {
		return fromString(string, Map::class.java) as Map<String, Any?>
	}
	
	override fun <T : Any> fromFile(path: String, type: Class<T>): T {
		return gson.fromJson(FileReader(path), type)
	}
	
	override fun fromFile(path: String): Map<String, Any?> {
		return fromFile(path, Map::class.java) as Map<String, Any?>
	}
	
	override fun <T : Any> toString(data: T): String {
		return gson.toJson(data)
	}
	
	override fun <T : Any> toFile(data: T, path: String) {
		File(path).writeText(toString(data))
	}
}
