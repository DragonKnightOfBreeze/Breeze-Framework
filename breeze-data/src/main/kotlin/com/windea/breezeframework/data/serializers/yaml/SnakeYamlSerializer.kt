package com.windea.breezeframework.data.serializers.yaml

import org.yaml.snakeyaml.*
import java.io.*

class SnakeYamlSerializer : YamlSerializer<SnakeYamlSerializer, DumperOptions> {
	private val dumperOptions = DumperOptions()
	
	private val yaml by lazy { Yaml(dumperOptions) }
	
	init {
		dumperOptions.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
	}
	
	
	/**配置持久化选项。这个方法必须首先被调用。*/
	override fun configure(handler: (options: DumperOptions) -> Unit): SnakeYamlSerializer {
		return this.also { handler(dumperOptions) }
	}
	
	override fun <T : Any> load(string: String, type: Class<T>): T {
		return yaml.loadAs(string, type)
	}
	
	override fun <T : Any> load(file: File, type: Class<T>): T {
		return yaml.loadAs(file.reader(), type)
	}
	
	override fun <T : Any> load(reader: Reader, type: Class<T>): T {
		return yaml.loadAs(reader, type)
	}
	
	override fun loadAll(string: String): List<Any> {
		return yaml.loadAll(string).toList()
	}
	
	override fun loadAll(file: File): List<Any> {
		return yaml.loadAll(file.reader()).toList()
	}
	
	override fun loadAll(reader: Reader): List<Any> {
		return yaml.loadAll(reader).toList()
	}
	
	override fun <T : Any> dump(data: T): String {
		return yaml.dump(data)
	}
	
	override fun <T : Any> dump(data: T, file: File) {
		return yaml.dump(data, file.writer())
	}
	
	override fun <T : Any> dump(data: T, writer: Writer) {
		return yaml.dump(data, writer)
	}
	
	override fun <T : Any> dumpAll(data: List<T>): String {
		return yaml.dumpAll(data.iterator())
	}
	
	override fun <T : Any> dumpAll(data: List<T>, file: File) {
		return yaml.dumpAll(data.iterator(), file.writer())
	}
	
	override fun <T : Any> dumpAll(data: List<T>, writer: Writer) {
		return yaml.dumpAll(data.iterator(), writer)
	}
}
