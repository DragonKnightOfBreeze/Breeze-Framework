// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("CoreExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.model.*
import java.lang.reflect.*
import java.math.*
import kotlin.random.*
import kotlin.reflect.*

//得到带有泛型信息的类型

/**
 * 得到指定类型的带有泛型参数信息的Java类型对象。
 */
inline fun <reified T> javaTypeOf(): Type = object : TypeReference<T>() {}.type

/**
 * 得到当前对象的带有泛型参数信息的Java类型对象。
 */
@Suppress("UNUSED_PARAMETER")
inline fun <reified T> javaTypeOf(target: T) = object : TypeReference<T>() {}.type

//得到类型、方法、属性、参数等的名字
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

//得到指定类型的默认值

/**
 * 尝试得到当前类型的默认值。
 */
@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
inline fun <reified T : Any> defaultValue(): T = when(val type = T::class) {
	Byte::class -> 0.toByte()
	Short::class -> 0.toShort()
	Int::class -> 0
	Long::class -> 0L
	Float::class -> 0F
	Double::class -> 0.0
	BigInteger::class -> BigInteger.ZERO
	BigDecimal::class -> BigDecimal.ZERO
	Boolean::class -> false
	Char::class -> '\u0000'
	String::class -> ""
	else -> type.java.getDeclaredConstructor().newInstance()
} as T

//得到指定类型的随机值

/**
 * 尝试得到当前类型的随机值。
 */
@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
inline fun <reified T : Any> randomValue(): T = when(val type = T::class) {
	Byte::class -> Random.nextByte()
	Short::class -> Random.nextShort()
	Int::class -> Random.nextInt()
	Long::class -> Random.nextLong()
	Float::class -> Random.nextFloat()
	Double::class -> Random.nextDouble()
	Boolean::class -> Random.nextBoolean()
	Char::class -> Random.nextChar()
	String::class -> Random.nextUUIDString()
	else -> type.java.getDeclaredConstructor().newInstance()
} as T
