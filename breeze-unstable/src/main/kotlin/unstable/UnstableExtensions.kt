package com.windea.breezeframework.unstable

import kotlin.reflect.*
import kotlin.reflect.full.*

/**得到当前类型的默认值。*/
@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
val <T:Any> KClass<T>.defaultValue:T
	get() = when(this) {
		Byte::class -> 0.toByte()
		Short::class -> 0.toShort()
		Int::class -> 0
		Long::class -> 0L
		Float::class -> 0F
		Double::class -> 0.0
		Boolean::class -> false
		Char::class -> '\u0000'
		String::class -> ""
		else -> this.createInstance()
	} as T
