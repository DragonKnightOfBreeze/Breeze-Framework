// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.io

import icu.windea.breezeframework.serialization.config.*

/**
 * Csv数据的写入器。
 */
internal class CsvWriter @PublishedApi internal constructor(
	override val config: CsvConfig = CsvConfig()
): DataWriter {
	override fun <T> write(target: T): String {
		TODO()
	}
}
