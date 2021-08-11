// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.properties

import icu.windea.breezeframework.serialization.*

/**
 * Properties数据的写入器。
 */
internal class PropertiesWriter @PublishedApi internal constructor(
	override val config: PropertiesConfig = PropertiesConfig()
) : DataWriter {
	override fun <T> write(value: T): String {
		TODO()
	}
}
