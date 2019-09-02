package com.windea.breezeframework.data.serializers.yaml

import java.io.*

class JacksonYamlSerializer : YamlSerializer {
	override fun <T : Any> load(string: String, type: Class<T>): T {
		TODO("not implemented")
	}
	
	override fun <T : Any> load(file: File, type: Class<T>): T {
		TODO("not implemented")
	}
	
	override fun <T : Any> load(reader: Reader, type: Class<T>): T {
		TODO("not implemented")
	}
	
	override fun loadList(string: String): List<Any?> {
		TODO("not implemented")
	}
	
	override fun loadList(file: File): List<Any?> {
		TODO("not implemented")
	}
	
	override fun loadList(reader: Reader): List<Any?> {
		TODO("not implemented")
	}
	
	override fun loadMap(file: File): Map<String, Any?> {
		TODO("not implemented")
	}
	
	override fun loadMap(string: String): Map<String, Any?> {
		TODO("not implemented")
	}
	
	override fun loadMap(reader: Reader): Map<String, Any?> {
		TODO("not implemented")
	}
	
	override fun <T : Any> loadAll(string: String, type: Class<T>): List<T> {
		TODO("not implemented")
	}
	
	override fun <T : Any> loadAll(file: File, type: Class<T>): List<T> {
		TODO("not implemented")
	}
	
	override fun <T : Any> loadAll(reader: Reader, type: Class<T>): List<T> {
		TODO("not implemented")
	}
	
	
	override fun <T : Any> dump(data: T): String {
		TODO("not implemented")
	}
	
	override fun <T : Any> dump(data: T, file: File) {
		TODO("not implemented")
	}
	
	override fun <T : Any> dump(data: T, writer: Writer) {
		TODO("not implemented")
	}
	
	override fun <T : Any> dumpAll(data: List<T>): String {
		TODO("not implemented")
	}
	
	override fun <T : Any> dumpAll(data: List<T>, file: File) {
		TODO("not implemented")
	}
	
	override fun <T : Any> dumpAll(data: List<T>, writer: Writer) {
		TODO("not implemented")
	}
}
