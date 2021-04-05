// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.io

import com.windea.breezeframework.serialization.config.*

/**
 * Xml数据的写入器。
 */
internal class XmlWriter @PublishedApi internal constructor(
	override val config: XmlConfig = XmlConfig()
): DataWriter {
	override fun <T> write(target: T): String {
		TODO()
	}
}
