package com.windea.breezeframework.reflect.extensions.java

import com.windea.breezeframework.core.annotations.internal.*
import java.lang.reflect.*

/**得到当前类型的被擦除类型。*/
@Reference("[klutter](https://github.com/kohesive/klutter/blob/master/reflect/src/main/kotlin/uy/klutter/reflect/TypeErasure.kt)")
@Suppress("UNCHECKED_CAST")
val Type.erasedType: Class<out Any>
	get() = when(this) {
		is Class<*> -> this as Class<Any>
		is ParameterizedType -> this.rawType.erasedType
		is GenericArrayType -> {
			// getting the array type is a bit trickier
			val elementType = this.genericComponentType.erasedType
			val testArray = java.lang.reflect.Array.newInstance(elementType, 0)
			testArray.javaClass
		}
		is TypeVariable<*> -> {
			throw IllegalStateException("Not sure what to do here yet.")
		}
		is WildcardType -> {
			this.upperBounds[0].erasedType
		}
		else -> throw IllegalStateException("Should not get here.")
	}
