// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.io

import icu.windea.breezeframework.serialization.config.*
import java.lang.reflect.*

/**
 * Properties数据的读取器。
 */
internal class PropertiesReader @PublishedApi internal constructor(
	override val config: PropertiesConfig = PropertiesConfig()
): DataReader {
	override fun <T> read(value: String, type: Class<T>): T {
		TODO()
	}

	override fun <T> read(value: String, type: Type): T {
		TODO()
	}
}
