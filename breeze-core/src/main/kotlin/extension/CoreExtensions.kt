// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("CoreExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.model.*
import java.lang.reflect.*
import kotlin.reflect.*

//通过这个方法可以得到泛型参数的信息

/**
 * 得到指定类型的带有泛型参数信息的Java类型对象。
 */
inline fun <reified T> javaTypeOf(): Type = object : TypeReference<T>() {}.type

/**
 * 得到当前对象的带有泛型参数信息的Java类型对象。
 */
@Suppress("UNUSED_PARAMETER")
inline fun <reified T> javaTypeOf(target: T) = object : TypeReference<T>() {}.type

//无法直接通过方法的引用得到参数，也无法得到局部变量的任何信息

/**
 * 得到指定类型的名字。
 */
@TrickApi
inline fun <reified T> nameOf(): String? = T::class.java.simpleName

/**
 * 得到指定项的名字。
 *
 * 适用于：类引用、属性引用、方法引用、实例。
 *
 * 不适用于：类型参数，参数，局部变量。
 */
@TrickApi
@JvmSynthetic
inline fun nameOf(target: Any?): String? = when(target) {
	null -> null
	is Class<*> -> target.simpleName
	is KClass<*> -> target.simpleName
	is KCallable<*> -> target.name
	is KParameter -> target.name
	else -> target::class.java.simpleName
}
