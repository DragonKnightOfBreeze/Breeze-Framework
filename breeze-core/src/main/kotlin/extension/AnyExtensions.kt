// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("AnyExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.model.*

/**
 * 得到当前对象的带有泛型参数信息的Java类型对象。
 */
inline val <reified T> T.javaType get() = object : TypeReference<T>() {}.type

/**
 * 将当前对象转换为指定类型。如果转换失败，则抛出异常。
 */
inline fun <reified T> Any?.cast(): T = this as T

/**
 * 将当前对象转换为指定类型。如果转换失败，则返回null。
 */
inline fun <reified T> Any?.castOrNull(): T? = this as? T
