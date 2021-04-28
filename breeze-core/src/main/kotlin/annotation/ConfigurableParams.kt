// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.annotation

/**
 * 可配置对象的一组参数信息。
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class ConfigurableParams(
	vararg val value: ConfigurableParam
)

