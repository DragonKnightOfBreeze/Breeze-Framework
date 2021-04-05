// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.io

import com.windea.breezeframework.serialization.config.*

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
