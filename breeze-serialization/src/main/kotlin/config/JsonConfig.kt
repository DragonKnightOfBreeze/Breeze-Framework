// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.config

/**
 * Json数据的配置。
 */
data class JsonConfig(
	val indent: String = "  ",
	val lineSeparator: String = "\n",
	val doubleQuoted: Boolean = true,
	val unquoteKey: Boolean = false,
	val unquoteValue: Boolean = false,
	val prettyPrint: Boolean = false
):DataConfig
