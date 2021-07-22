// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.xml

import icu.windea.breezeframework.serialization.*

/**
 * Xml数据的写入器。
 */
internal class XmlWriter @PublishedApi internal constructor(
	override val config: XmlConfig = XmlConfig()
): DataWriter {
	override fun <T> write(value: T): String {
		TODO()
	}
}
