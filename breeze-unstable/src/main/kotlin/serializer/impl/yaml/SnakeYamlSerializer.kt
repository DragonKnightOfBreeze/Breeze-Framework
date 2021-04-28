// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serializer.impl.yaml

import org.yaml.snakeyaml.*
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.representer.*
import java.io.*
import java.lang.reflect.*

/**
 * 由SnakeYaml实现的Yaml的序列化器。
 * @see org.yaml.snakeyaml.Yaml
 */
internal object SnakeYamlSerializer : YamlSerializer {
	internal val loaderOptions = LoaderOptions()
	internal val dumperOptions = DumperOptions()
	internal val yaml by lazy { Yaml(Constructor(), Representer(), dumperOptions, loaderOptions) }

	init {
		dumperOptions.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
	}

	override fun <T : Any> read(string: String, type: Class<T>): T {
		return yaml.loadAs(string, type)
	}

	override fun <T : Any> read(string: String, type: Type): T {
		return yaml.load(string)
	}

	override fun <T : Any> read(file: File, type: Class<T>): T {
		return yaml.loadAs(file.reader(), type)
	}

	override fun <T : Any> read(file: File, type: Type): T {
		throw yaml.load(file.reader())
	}

	override fun readAll(string: String): List<Any?> {
		return yaml.loadAll(string).toList()
	}

	override fun readAll(file: File): List<Any?> {
		return yaml.loadAll(file.reader()).toList()
	}

	override fun <T : Any> write(data: T): String {
		return yaml.dump(data)
	}

	override fun <T : Any> write(data: T, file: File) {
		return yaml.dump(data, file.writer())
	}

	override fun <T : Any> writeAll(data: Iterable<T>): String {
		return yaml.dumpAll(data.iterator())
	}

	override fun <T : Any> writeAll(data: Iterable<T>, file: File) {
		return yaml.dumpAll(data.iterator(), file.writer())
	}
}

