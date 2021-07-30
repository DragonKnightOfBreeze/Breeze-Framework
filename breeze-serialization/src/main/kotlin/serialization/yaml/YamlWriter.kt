// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.yaml

import icu.windea.breezeframework.serialization.*

/**
 * Yaml数据的写入器。
 */
internal class YamlWriter @PublishedApi internal constructor(
	override val config: YamlConfig = YamlConfig()
) : DataWriter {
	override fun <T> write(value: T): String {
		TODO()
	}

	fun writeAll(value: List<Any>): String {
		TODO()
	}
}
