// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.yaml

import icu.windea.breezeframework.serialization.*
import java.lang.reflect.*

/**
 * 框架本身实现的Yaml数据的序列化器。
 */
class BreezeYamlSerializer(
	val config: YamlConfig = YamlConfig()
) : YamlSerializer, BreezeSerializer {
	private val reader = YamlReader(config)
	private val writer = YamlWriter(config)

	override fun <T> serialize(target: T): String {
		return writer.write(target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		return reader.read(value, type)
	}

	override fun <T> deserialize(value: String, type: Type): T {
		return reader.read(value, type)
	}

	override fun serializeAll(value: List<Any>): String {
		TODO()
	}

	override fun deserializeAll(value: String): List<Any> {
		TODO()
	}
}
