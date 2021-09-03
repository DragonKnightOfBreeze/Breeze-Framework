// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("ConverterExtensions")
@file:Suppress("UNCHECKED_CAST")

package icu.windea.breezeframework.core.component.extension

import icu.windea.breezeframework.core.component.Converter
import icu.windea.breezeframework.core.component.Converters
import java.lang.reflect.Type

/**
 * 根据可选的配置参数，将当前对象转化为指定类型。如果指定的对象是null，或者转化失败，则抛出异常。
 *
 * @see Converter
 */
inline fun <reified T> Any?.convert(componentParams: Map<String, Any?> = emptyMap()): T {
	return Converters.convert(this, componentParams)
}

/**
 * 根据可选的配置参数，将当前对象转化为指定类型。如果指定的对象是null，或者转化失败，则抛出异常。
 *
 * @see Converter
 */
fun <T> Any?.convert(targetType: Class<T>, componentParams: Map<String, Any?> = emptyMap()): T {
	return Converters.convert(this, targetType, componentParams)
}

/**
 * 根据可选的配置参数，将当前对象转化为指定类型。如果指定的对象是null，或者转化失败，则抛出异常。
 *
 * @see Converter
 */
fun Any?.convert(targetType: Type, componentParams: Map<String, Any?> = emptyMap()): Any {
	return Converters.convert(this, targetType, componentParams)
}

/**
 * 根据可选的配置参数，将当前对象转化为指定类型。如果指定的对象是null，或者转化失败，则返回null。
 *
 * @see Converter
 */
inline fun <reified T> Any?.convertOrNull(componentParams: Map<String, Any?> = emptyMap()): T? {
	return Converters.convertOrNull(this, componentParams)
}

/**
 * 根据可选的配置参数，将当前对象转化为指定类型。如果指定的对象是null，或者转化失败，则返回null。
 *
 * @see Converter
 */
fun <T> Any?.convertOrNull(targetType: Class<T>, componentParams: Map<String, Any?> = emptyMap()): T? {
	return Converters.convertOrNull(this, targetType, componentParams)
}

/**
 * 根据可选的配置参数，将当前对象转化为指定类型。如果指定的对象是null，或者转化失败，则返回null。
 *
 * @see Converter
 */
fun Any?.convertOrNull(targetType: Type, componentParams: Map<String, Any?> = emptyMap()): Any? {
	return Converters.convertOrNull(this, targetType, componentParams)
}
