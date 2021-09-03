// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("RandomGeneratorExtensions")

package icu.windea.breezeframework.core.component.extension

import icu.windea.breezeframework.core.component.RandomGenerator
import icu.windea.breezeframework.core.component.RandomGenerators
import java.lang.reflect.Type

/**
 * 根据可选的配置参数，生成指定类型的随机值。
 *
 * @see RandomGenerator
 */
inline fun <reified T : Any> randomValue(componentParams: Map<String, Any?> = emptyMap()): T {
	return RandomGenerators.generate(componentParams)
}

/**
 * 根据可选的配置参数，生成指定类型的随机值。
 *
 * @see RandomGenerator
 */
fun <T : Any> randomValue(targetType: Class<T>, componentParams: Map<String, Any?> = emptyMap()): T {
	return RandomGenerators.generate(targetType, componentParams)
}

/**
 * 根据可选的配置参数，生成指定类型的随机值。
 *
 * @see RandomGenerator
 */
fun <T : Any> randomValue(targetType: Type, componentParams: Map<String, Any?> = emptyMap()): T {
	return RandomGenerators.generate(targetType, componentParams)
}
