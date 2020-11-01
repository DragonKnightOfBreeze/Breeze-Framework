// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("KTypeExtensions")

package com.windea.breezeframework.reflect.extensions

import java.lang.reflect.*
import kotlin.reflect.*
import kotlin.reflect.jvm.*

/**
 * 得到当前类型的被擦除类型。
 */
@Suppress("UNCHECKED_CAST") val Type.erasedType: Class<Any>
	get() = when (this) {
		is Class<*> -> this as Class<Any>
		is ParameterizedType -> this.rawType.erasedType
		is GenericArrayType -> {
			val elementType = this.genericComponentType.erasedType
			val testArray = java.lang.reflect.Array.newInstance(elementType, 0)
			testArray.javaClass
		}
		is TypeVariable<*> -> {
			throw IllegalStateException("Not sure what to do here yet")
		}
		is WildcardType -> {
			this.upperBounds[0].erasedType
		}
		else -> throw IllegalStateException("Should not get here.")
	}

/**
 * 得到当前Kotlin类型的被擦除类型。
 */
val KType.erasureType: Class<out Any>
	get() = this.jvmErasure.java
