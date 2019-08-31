@file:Suppress("NOTHING_TO_INLINE")

package com.windea.utility.common.extensions

import kotlin.reflect.*

/**得到指定类型的名字。*/
@ExperimentalStdlibApi
inline fun <reified T> nameOf(): String? {
	return T::class.simpleName
}

/**得到指定项的名字。适用于：类型、属性引用、方法引用、实例。不适用于：类型参数，参数，局部变量。*/
@ExperimentalStdlibApi
inline fun nameOf(target: Any?): String? {
	return when {
		target == null -> null
		target is KClass<*> -> target.simpleName
		target is KCallable<*> -> target.name
		target is KParameter -> target.name //无法直接通过方法的引用得到参数
		//无法得到局部变量的任何信息
		else -> target::class.simpleName
	}
}
