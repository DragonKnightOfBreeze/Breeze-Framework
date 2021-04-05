// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.io

import com.windea.breezeframework.serialization.config.*

/**
 * Yaml数据的写入器。
 */
internal class YamlWriter @PublishedApi internal constructor(
	override val config: YamlConfig = YamlConfig()
): DataWriter {
	override fun <T> write(target: T): String {
		TODO()
	}
}
