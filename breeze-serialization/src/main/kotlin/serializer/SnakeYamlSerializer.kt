// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.serializer

import icu.windea.breezeframework.core.model.*
import org.yaml.snakeyaml.*
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.representer.*
import java.lang.reflect.*

/**
 * 由SnakeYaml委托实现的Yaml数据的序列化器。
 * @see org.yaml.snakeyaml.Yaml
 */
class SnakeYamlSerializer(
	val yaml:Yaml = Yaml()
) : YamlSerializer, DelegateSerializer{
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
