@file:JvmName("ReflectExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.reflect.extensions

import com.windea.breezeframework.core.extensions.*
import kotlin.random.*
import kotlin.reflect.full.*

/**尝试得到当前类型的默认值。*/
@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
inline fun <reified T : Any> defaultValue():T = when(val type = T::class) {
	Byte::class -> 0.toByte()
	Short::class -> 0.toShort()
	Int::class -> 0
	Long::class -> 0L
	Float::class -> 0F
	Double::class -> 0.0
	Boolean::class -> false
	Char::class -> '\u0000'
	String::class -> ""
	else -> type.createInstance()
} as T

/**尝试得到当前类型的随机值。*/
@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
inline fun <reified T : Any> randomValue():T = when(val type = T::class) {
	Byte::class -> Random.nextByte()
	Short::class -> Random.nextShort()
	Int::class -> Random.nextLong()
	Long::class -> Random.nextLong()
	Float::class -> Random.nextFloat()
	Double::class -> Random.nextDouble()
	Boolean::class -> Random.nextBoolean()
	Char::class -> Random.nextChar()
	String::class -> Random.nextUUID()
	else -> type.createInstance()
} as T
