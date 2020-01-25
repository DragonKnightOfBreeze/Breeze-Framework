@file:JvmName("TypeExtensions")

package com.windea.breezeframework.reflect.extensions

import java.lang.reflect.*

//https://github.com/kohesive/klutter/blob/master/reflect/src/main/kotlin/uy/klutter/reflect/TypeErasure.kt
/**得到当前类型的被擦除类型。*/
@Suppress("UNCHECKED_CAST")
val Type.erasure: Class<out Any>
	get() = when(this) {
		is Class<*> -> this as Class<Any>
		is ParameterizedType -> this.rawType.erasure
		is GenericArrayType -> {
			// getting the array type is a bit trickier
			val elementType = this.genericComponentType.erasure
			val testArray = java.lang.reflect.Array.newInstance(elementType, 0)
			testArray.javaClass
		}
		is TypeVariable<*> -> {
			throw IllegalStateException("Not sure what to do here yet.")
		}
		is WildcardType -> {
			this.upperBounds[0].erasure
		}
		else -> throw IllegalStateException("Should not get here.")
	}
