// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.xml

import icu.windea.breezeframework.serialization.*

/**
 * Xml数据的配置。
 */
data class XmlConfig(
	val lineSeparator: String = "\n"
) : DataConfig
