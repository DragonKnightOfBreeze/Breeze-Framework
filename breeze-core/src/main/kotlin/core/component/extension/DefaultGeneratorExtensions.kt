// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("DefaultGeneratorExtensions")

package icu.windea.breezeframework.core.component.extension

import icu.windea.breezeframework.core.component.DefaultGenerator
import icu.windea.breezeframework.core.component.DefaultGenerators
import java.lang.reflect.Type

/**
 * 根据可选的配置参数，生成指定类型的默认值。
 *
 * @see DefaultGenerator
 */
inline fun <reified T : Any> defaultValue(componentParams: Map<String, Any?> = emptyMap()): T {
	return DefaultGenerators.generate(componentParams)
}

/**
 * 根据可选的配置参数，生成指定类型的默认值。
 *
 * @see DefaultGenerator
 */
fun <T : Any> defaultValue(targetType: Class<T>, componentParams: Map<String, Any?> = emptyMap()): T {
	return DefaultGenerators.generate(targetType, componentParams)
}

/**
 * 根据可选的配置参数，生成指定类型的默认值。
 *
 * @see DefaultGenerator
 */
fun <T : Any> defaultValue(targetType: Type, componentParams: Map<String, Any?> = emptyMap()): T {
	return DefaultGenerators.generate(targetType, componentParams)
}
