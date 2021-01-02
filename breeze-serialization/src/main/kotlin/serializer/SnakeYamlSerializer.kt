// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.windea.breezeframework.core.model.*
import org.yaml.snakeyaml.*
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.representer.*
import java.lang.reflect.*

/**
 * 由SnakeYaml实现的Yaml的序列化器。
 * @see org.yaml.snakeyaml.Yaml
 */
class SnakeYamlSerializer : YamlSerializer, DelegateSerializer, Configurable<Pair<LoaderOptions, DumperOptions>> {
	private val loaderOptions = LoaderOptions()
	private val dumperOptions = DumperOptions()
	val yaml by lazy { Yaml(Constructor(), Representer(), dumperOptions, loaderOptions) }

	init {
		dumperOptions.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
	}

	override fun configure(block: Pair<LoaderOptions, DumperOptions>.() -> Unit) {
		(loaderOptions to dumperOptions).block()
	}

	override fun <T> serialize(target: T): String {
		return yaml.dump(target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		return yaml.loadAs(value, type)
	}

	override fun <T> deserialize(value: String, type: Type): T {
		return yaml.load(value)
	}

	override fun serializeAll(value: List<Any>): String {
		return yaml.dumpAll(value.iterator())
	}

	override fun deserializeAll(value: String): List<Any> {
		return yaml.loadAll(value).toList()
	}
}
