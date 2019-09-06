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
	
	/**从指定字符串读取所有数据。*/
	fun loadAll(string: String): List<Any>
	
	/**从指定文件读取所有数据。*/
	fun loadAll(file: File): List<Any>
	
	/**从指定读取器读取所有数据。*/
	fun loadAll(reader: Reader): List<Any>
}

interface YamlDumper : JsonDumper {
	override val dataType: DataType get() = DataType.Yaml
	
	/**转储所有数据到字符串。*/
	fun <T : Any> dumpAll(data: List<T>): String
	
	/**转储所有数据到文件。*/
	fun <T : Any> dumpAll(data: List<T>, file: File)
	
	/**转储所有数据到写入器。*/
	fun <T : Any> dumpAll(data: List<T>, writer: Writer)
}
