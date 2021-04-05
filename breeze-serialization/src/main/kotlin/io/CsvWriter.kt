// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.io

import com.windea.breezeframework.serialization.config.*

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
