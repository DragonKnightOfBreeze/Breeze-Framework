// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.io

import com.windea.breezeframework.serialization.serializer.*
import java.lang.reflect.*

/**
 * Json数据的读取器。
 */
class JsonReader @PublishedApi internal constructor(
	private val config: BreezeJsonSerializer.Config = BreezeJsonSerializer.Config()
): DataReader {
	override fun <T> read(value: String, type: Class<T>): T {
		TODO()
	}

	override fun <T> read(value: String, type: Type): T {
		TODO()
	}
}
