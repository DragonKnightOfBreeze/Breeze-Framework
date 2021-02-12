// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import java.lang.reflect.*

/**
 * 由Breeze Framework实现的轻量的Csv的序列化器。
 */
class BreezeCsvSerializer : CsvSerializer, BreezeSerializer {
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
