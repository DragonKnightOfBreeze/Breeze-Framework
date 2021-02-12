// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import java.lang.reflect.*

/**
 * 由Breeze Framework实现的轻量的Yaml的序列化器。
 */
class BreezeYamlSerializer : YamlSerializer, BreezeSerializer {
	override fun serializeAll(value: List<Any>): String {
		TODO()
	}

	override fun deserializeAll(value: String): List<Any> {
		TODO()
	}

	override fun <T> serialize(target: T): String {
		TODO()
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		TODO()
	}

	override fun <T> deserialize(value: String, type: Type): T {
		TODO()
	}

}
