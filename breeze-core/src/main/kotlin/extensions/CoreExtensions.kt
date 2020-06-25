@file:JvmName("CoreExtensions")
@file:Suppress("NOTHING_TO_INLINE", "FunctionName", "UseWithIndex")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import java.lang.reflect.*
import kotlin.contracts.*
import kotlin.reflect.*

/**得到指定类型的带有泛型参数信息的Java类型对象。*/
inline fun <reified T> javaTypeOf(): Type = object : TypeReference<T>() {}.type

/**类型引用。*/
@PublishedApi
internal abstract class TypeReference<T> {
	val type: Type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
}


/**得到指定类型的名字。*/
@TrickImplementationApi
inline fun <reified T> nameOf(): String? = T::class.java.simpleName

/**得到指定项的名字。适用于：类引用、属性引用、方法引用、实例。不适用于：类型参数，参数，局部变量。*/
@TrickImplementationApi
@JvmSynthetic
inline fun nameOf(target: Any?): String? = when {
	//无法直接通过方法的引用得到参数，也无法得到局部变量的任何信息
	target == null -> null
	target is Class<*> -> target.simpleName
	target is KClass<*> -> target.simpleName
	target is KCallable<*> -> target.name
	target is KParameter -> target.name
	else -> target::class.java.simpleName
}
