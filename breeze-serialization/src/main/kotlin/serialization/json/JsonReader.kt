// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.json

import icu.windea.breezeframework.serialization.*
import java.lang.reflect.*

/**
 * Json数据的读取器。
 */
internal class JsonReader @PublishedApi internal constructor(
	override val config: JsonConfig = JsonConfig()
) : DataReader {
	override fun <T> read(value: String, type: Class<T>): T {
		TODO()
	}

	override fun <T> read(value: String, type: Type): T {
		TODO()
	}
}

