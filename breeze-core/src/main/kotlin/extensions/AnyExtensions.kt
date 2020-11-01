// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("AnyExtensions")
@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

//真的可以这么做

/**
 * 得到当前对象的带有泛型参数信息的Java类型对象。
 */
inline val <reified T> T.javaType get() = object : TypeReference<T>() {}.type

//注意cast方法不适用于不同泛型的类型，因为它们实际上是同一种类型

/**
 * 将当前对象转换为指定类型。如果转换失败，则抛出异常。
 */
inline fun <reified T> Any?.cast(): T = this as T

/**
 * 将当前对象转换为指定类型。如果转换失败，则返回null。
 */
inline fun <reified T> Any?.castOrNull(): T? = this as? T
