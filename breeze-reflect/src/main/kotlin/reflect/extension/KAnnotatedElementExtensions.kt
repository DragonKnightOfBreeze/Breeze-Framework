// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.reflect.extension

import icu.windea.breezeframework.core.annotation.*
import kotlin.reflect.*
import kotlin.reflect.full.*
import kotlin.reflect.jvm.*

/**
 * 得到指定的额外注解。
 *
 * 查找的范围包括：属性对应的字段、主构造参数等。
 */
@UnstableApi
inline fun <reified T : Annotation> KAnnotatedElement.findExtraAnnotation(): T? {
	return findAnnotation<T>() ?: when(this) {
		is KProperty<*> -> javaField?.getAnnotation(T::class.java)
			?: javaGetter?.getAnnotation(T::class.java)
		else -> null
	}
}

/**
 * 判断是否拥有指定的额外的解。
 *
 * 查找的范围包括：属性对应的字段、主构造参数等。
 */
@UnstableApi
inline fun <reified T : Annotation> KAnnotatedElement.hasExtraAnnotation(): Boolean {
	return hasAnnotation<T>() || when(this) {
		is KProperty<*> -> javaField?.isAnnotationPresent(T::class.java) ?: false
			|| javaGetter?.isAnnotationPresent(T::class.java) ?: false
		else -> false
	}
}
