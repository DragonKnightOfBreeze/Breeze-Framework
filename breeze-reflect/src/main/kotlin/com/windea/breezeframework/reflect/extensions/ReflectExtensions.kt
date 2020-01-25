@file:JvmName("ReflectExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.reflect.extensions

import com.windea.breezeframework.core.annotations.*
import kotlin.reflect.*

//region generic extensions
/**判断指定名字的Class是否在classpath中。*/
fun checkClassForName(className: String): Boolean {
	return try {
		Class.forName(className)
		true
	} catch(e: Error) {
		false
	}
}


/**得到指定类型的名字。*/
@TrickImplementationApi("Cannot get actual name of a function parameter or a local variable.")
inline fun <reified T> nameOf(): String? = T::class.java.simpleName

/**得到指定项的名字。适用于：类引用、属性引用、方法引用、实例。不适用于：类型参数，参数，局部变量。*/
@TrickImplementationApi("Cannot get actual name of a function parameter or a local variable.")
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
//endregion

//region Any extensions
/**判断当前对象是否是指定类型的实例。兼容Java原始类型。*/
@JvmSynthetic
infix fun Any.isInstanceOf(type: KClass<*>): Boolean = type.isInstance(this)
//endregion
