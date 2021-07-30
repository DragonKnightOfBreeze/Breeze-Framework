// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.properties

import icu.windea.breezeframework.serialization.*

/**
 * Properties数据的配置。
 */
data class PropertiesConfig(
	val lineSeparator: String = "\n"
) : DataConfig
