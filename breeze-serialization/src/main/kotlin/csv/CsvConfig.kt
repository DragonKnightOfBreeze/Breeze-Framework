// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.csv

import icu.windea.breezeframework.serialization.*

/**
 * Csv数据的配置。
 */
data class CsvConfig(
	val lineSeparator: String = "\n"
) : DataConfig
