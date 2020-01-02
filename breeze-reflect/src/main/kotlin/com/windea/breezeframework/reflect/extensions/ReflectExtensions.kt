@file:JvmName("ReflectExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.reflect.extensions

import com.windea.breezeframework.core.annotations.api.*
import java.lang.reflect.*
import kotlin.reflect.*

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
@TrickImplementationApi("Can never be implemented for all situations.")
inline fun <reified T> nameOf(): String? {
	return T::class.java.simpleName
}

/**得到指定项的名字。适用于：类引用、属性引用、方法引用、实例。不适用于：类型参数，参数，局部变量。*/
@TrickImplementationApi("Can never be implemented for all situations.")
inline fun nameOf(target: Any?): String? {
	//无法直接通过方法的引用得到参数，无法得到局部变量的任何信息
	return when {
		target == null -> null
		target is Class<*> -> target.simpleName
		target is KClass<*> -> target.simpleName
		target is KCallable<*> -> target.name
		target is KParameter -> target.name
		else -> target::class.java.simpleName
	}
}


/**得到指定类型的带有泛型参数信息的Java类型对象。*/
inline fun <reified T> javaTypeOf(): Type {
	return object : TypeReference<T>() {}.type
}

//com.fasterxml.jackson.core.type.TypeReference
/**类型引用。*/
@PublishedApi
internal abstract class TypeReference<T> {
	val type: Type = run {
		val superClass = this::class.java.genericSuperclass
		require(superClass !is Class<*>) { "TypeReference constructed without actual type information." }
		(superClass as ParameterizedType).actualTypeArguments[0]
	}
}
