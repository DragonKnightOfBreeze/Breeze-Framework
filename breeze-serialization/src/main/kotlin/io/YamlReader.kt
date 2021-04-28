// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.io

import icu.windea.breezeframework.serialization.config.*
import java.lang.reflect.*

/**
 * Yaml数据的读取器。
 */
internal class YamlReader @PublishedApi internal constructor(
	override val config: YamlConfig = YamlConfig()
): DataReader {
	override fun <T> read(value: String, type: Class<T>): T {
		TODO()
	}

	override fun <T> read(value: String, type: Type): T {
		TODO()
	}
}
