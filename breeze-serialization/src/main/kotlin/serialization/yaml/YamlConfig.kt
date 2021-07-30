// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.yaml

import icu.windea.breezeframework.serialization.*

/**
 * Yaml数据的配置。
 */
data class YamlConfig(
	val lineSeparator: String = "\n"
) : DataConfig
