// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.io

import icu.windea.breezeframework.serialization.config.*

/**
 * Properties数据的写入器。
 */
internal class PropertiesWriter @PublishedApi internal constructor(
	override val config: PropertiesConfig = PropertiesConfig()
): DataWriter {
	override fun <T> write(target: T): String {
		TODO()
	}
}
