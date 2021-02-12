// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import java.lang.reflect.*
import java.util.*

/**
 * 由Breeze Framework实现的轻量的Properties的序列化器。
 */
class BreezePropertiesSerializer : PropertiesSerializer, BreezeSerializer {
	override fun <T> serializeProperties(target: T): Properties {
		TODO()
	}

	override fun <T> deserializeProperties(properties: Properties, type: Class<T>): T {
		TODO()
	}

	override fun <T> deserializeProperties(properties: Properties, type: Type): T {
		TODO()
	}

	override fun <T> serialize(target: T): String {
		TODO()
	}

	override fun <T> deserialize(value: String, type: Type): T {
		TODO()
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		TODO()
	}
}
