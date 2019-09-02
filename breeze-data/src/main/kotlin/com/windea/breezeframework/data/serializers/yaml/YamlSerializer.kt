package com.windea.breezeframework.data.serializers.yaml

import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.serializers.json.*
import java.io.*

interface YamlSerializer : JsonSerializer, YamlLoader, YamlDumper {
	override val dataType: DataType get() = DataType.Yaml
}

interface YamlLoader : JsonLoader {
	override val dataType: DataType get() = DataType.Yaml
	
	fun <T : Any> loadAll(string: String, type: Class<T>): List<T>
	
	fun <T : Any> loadAll(file: File, type: Class<T>): List<T>
	
	fun <T : Any> loadAll(reader: Reader, type: Class<T>): List<T>
}

interface YamlDumper : JsonDumper {
	override val dataType: DataType get() = DataType.Yaml
	
	fun <T : Any> dumpAll(data: List<T>): String
	
	fun <T : Any> dumpAll(data: List<T>, file: File)
	
	fun <T : Any> dumpAll(data: List<T>, writer: Writer)
}
