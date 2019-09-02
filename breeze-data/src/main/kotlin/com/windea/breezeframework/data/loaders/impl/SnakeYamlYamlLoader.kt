@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.data.loaders.impl

import com.windea.breezeframework.data.loaders.*
import org.yaml.snakeyaml.*
import java.io.*

class SnakeYamlYamlLoader : YamlLoader {
	private val dumperOptions = DumperOptions()
	private val yaml get() = Yaml(dumperOptions)
	
	init {
		dumperOptions.width = 120
		dumperOptions.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
	}
	
	
	fun configureDumperOptions(handler: (options: DumperOptions) -> Unit): SnakeYamlYamlLoader {
		handler.invoke(dumperOptions)
		return this
	}
	
	fun addTags(tags: Map<String, String>): SnakeYamlYamlLoader {
		dumperOptions.tags.putAll(tags)
		return this
	}
	
	
	override fun <T : Any> fromString(string: String, type: Class<T>): T {
		return yaml.load(string)
	}
	
	override fun fromString(string: String): Map<String, Any?> {
		return fromString(string, Map::class.java) as Map<String, Any?>
	}
	
	override fun fromStringAll(string: String): List<Any> {
		return yaml.loadAll(string).toList()
	}
	
	override fun <T : Any> fromFile(path: String, type: Class<T>): T {
		return yaml.load(FileReader(path))
	}
	
	override fun fromFile(path: String): Map<String, Any?> {
		return fromFile(path, Map::class.java) as Map<String, Any?>
	}
	
	override fun fromFileAll(path: String): List<Any> {
		return yaml.loadAll(FileReader(path)).toList()
	}
	
	override fun <T : Any> toString(data: T): String {
		return yaml.dump(data)
	}
	
	override fun <T : Any> toStringAll(dataList: List<T>): String {
		return yaml.dumpAll(dataList.iterator())
	}
	
	override fun <T : Any> toFile(data: T, path: String) {
		yaml.dump(data, FileWriter(path))
	}
	
	override fun <T : Any> toFileAll(dataList: List<T>, path: String) {
		yaml.dumpAll(dataList.iterator(), FileWriter(path))
	}
}
