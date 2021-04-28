// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.annotation

/**
 * 可配置对象的参数信息。
 */
@MustBeDocumented
@Target()
annotation class ConfigurableParam(
	val name: String,
	val type: String,
	val defaultValue: String = "",
	val comment: String = ""
)
