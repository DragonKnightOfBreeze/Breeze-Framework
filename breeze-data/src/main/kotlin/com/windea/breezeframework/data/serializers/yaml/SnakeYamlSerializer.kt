package com.windea.breezeframework.data.serializers.yaml

import org.yaml.snakeyaml.*
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.representer.*
import java.io.*
import java.lang.reflect.*

object SnakeYamlSerializer : YamlSerializer {
	@PublishedApi internal val loaderOptions = LoaderOptions()
	@PublishedApi internal val dumperOptions = DumperOptions()
	
	private val yaml by lazy { Yaml(Constructor(), Representer(), dumperOptions, loaderOptions) }
	
	init {
		dumperOptions.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
	}
	
	override fun <T> load(string: String, type: Class<T>): T {
		return yaml.loadAs(string, type)
	}
	
	override fun <T> load(file: File, type: Class<T>): T {
		return yaml.loadAs(file.reader(), type)
	}
	
	override fun <T> load(string: String, type: Type): T {
		throw UnsupportedOperationException("Could not find suitable methods to delegate.")
	}
	
	override fun <T> load(file: File, type: Type): T {
		throw UnsupportedOperationException("Could not find suitable methods to delegate.")
	}
	
	override fun loadAll(string: String): List<Any?> {
		return yaml.loadAll(string).toList()
	}
	
	override fun loadAll(file: File): List<Any?> {
		return yaml.loadAll(file.reader()).toList()
	}
	
	override fun <T> dump(data: T): String {
		return yaml.dump(data)
	}
	
	override fun <T> dump(data: T, file: File) {
		return yaml.dump(data, file.writer())
	}
	
	override fun <T> dumpAll(data: List<T>): String {
		return yaml.dumpAll(data.iterator())
	}
	
	override fun <T> dumpAll(data: List<T>, file: File) {
		return yaml.dumpAll(data.iterator(), file.writer())
	}
}

object SnakeYamlSerializerConfig : YamlSerializerConfig {
	inline fun configure(builder: (LoaderOptions, DumperOptions) -> Unit) =
		builder(SnakeYamlSerializer.loaderOptions, SnakeYamlSerializer.dumperOptions)
}
