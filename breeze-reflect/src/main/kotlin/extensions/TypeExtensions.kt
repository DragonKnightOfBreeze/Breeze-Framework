// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("TypeExtensions")

package com.windea.breezeframework.reflect.extensions

import java.lang.reflect.*

/**
 * 得到当前类型f的擦除类型。可用于将[Type]转花成[Class]。
 */
@Suppress("UNCHECKED_CAST")
val Type.erasedType: Class<Any>
	get() = when(this) {
		is Class<*> -> this as Class<Any>
		is ParameterizedType -> this.rawType.erasedType
		is GenericArrayType -> {
			val elementType = this.genericComponentType.erasedType
			val array = java.lang.reflect.Array.newInstance(elementType, 0)
			array.javaClass
		}
		is TypeVariable<*> -> {
			throw IllegalStateException("Not sure what to do here yet")
		}
		is WildcardType -> {
			this.upperBounds[0].erasedType
		}
		else -> throw IllegalStateException("Should not get here.")
	}
