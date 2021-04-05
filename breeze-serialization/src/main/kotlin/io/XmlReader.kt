// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.io

import com.windea.breezeframework.serialization.config.*
import java.lang.reflect.*

/**
 * Xml数据的读取器。
 */
internal class XmlReader @PublishedApi internal constructor(
	override val config: XmlConfig = XmlConfig()
): DataReader {
	override fun <T> read(value: String, type: Class<T>): T {
		TODO()
	}

	override fun <T> read(value: String, type: Type): T {
		TODO()
	}
}
