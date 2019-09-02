@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.annotations.internal.*
import java.lang.reflect.*
import kotlin.reflect.*
import kotlin.reflect.jvm.*

/**得到指定类型的名字。*/
@TrickImplementationApi("Can never be implemented for all situations.")
@ExperimentalStdlibApi
inline fun <reified T> nameOf(): String? {
	return T::class.simpleName
}

/**得到指定项的名字。适用于：类型、属性引用、方法引用、实例。不适用于：类型参数，参数，局部变量。*/
@TrickImplementationApi("Can never be implemented for all situations.")
@ExperimentalStdlibApi
inline fun nameOf(target: Any?): String? {
	return when {
		target == null -> null
		target is KClass<*> -> target.simpleName
		target is KCallable<*> -> target.name
		//无法直接通过方法的引用得到参数
		target is KParameter -> target.name
		//无法得到局部变量的任何信息
		else -> target::class.simpleName
	}
}


/**得到指定类型的引用。等同于`this::class`。*/
@TrickImplementationApi
inline fun <reified T> refOf() {
	TODO()
}

/**得到指定项的引用。参见引用操作符`::`。*/
@TrickImplementationApi
inline fun refOf(target: Any?) {
	TODO()
}


/**
 * 得到当前类型的被擦除类型。
 */
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

/**得到当前Kotlin类型的被擦除类型。*/
val KType.erasedType: Class<out Any> get() = this.jvmErasure.java
