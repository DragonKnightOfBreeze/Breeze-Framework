package com.windea.breezeframework.data.serializers.yaml

import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.serializers.json.*
import com.windea.breezeframework.reflect.extensions.*
import java.io.*

interface YamlSerializer<S, C> : JsonSerializer<S, C>, YamlLoader, YamlDumper {
	override val dataType: DataType get() = DataType.Yaml
	
	companion object {
		val instance: YamlSerializer<*, *> by lazy {
			when {
				checkClassForName("com.fasterxml.jackson.dataformat.yaml.YAMLMapper") -> JacksonYamlSerializer()
				checkClassForName("org.yaml.snakeyaml.Yaml") -> SnakeYamlSerializer()
				else -> throw IllegalStateException("Please contains at least one data serializer impl in classpath.")
			}
		}
	}
}

interface YamlLoader : JsonLoader {
	override val dataType: DataType get() = DataType.Yaml
	
	fun loadAll(string: String): List<Any>
	
	fun loadAll(file: File): List<Any>
	
	fun loadAll(reader: Reader): List<Any>
}

interface YamlDumper : JsonDumper {
	override val dataType: DataType get() = DataType.Yaml
	
	fun <T : Any> dumpAll(data: List<T>): String
	
	fun <T : Any> dumpAll(data: List<T>, file: File)
	
	fun <T : Any> dumpAll(data: List<T>, writer: Writer)
}
