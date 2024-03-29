// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.properties

import icu.windea.breezeframework.serialization.*
import java.lang.reflect.*
import java.util.*

/**
 * 框架本身实现的Properties数据的序列化器。
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
