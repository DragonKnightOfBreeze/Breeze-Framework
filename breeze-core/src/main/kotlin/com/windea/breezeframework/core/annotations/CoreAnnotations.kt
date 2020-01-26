package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * 适用于Kotlin的allOpen编译器插件的标准注解。
 * 让被注解的类及其属性和方法默认是开放的。
 * 需要自行配置。
 */
@Retention(BINARY)
@Target(CLASS)
annotation class AllOpen

/**
 * 适用于Kotlin的noArg编译器插件的标准注解。
 * 为被注解的类生成仅能通过反射调用的无参构造方法。
 * 需要自行配置。
 */
@Retention(BINARY)
@Target(CLASS)
annotation class NoArg

/**注明这个注解对应的项一个待办方法。*/
@MustBeDocumented
@Retention(BINARY)
@Target(FUNCTION)
annotation class TodoMarker

/**注明这个注解对应的项（在通常情况下）已废弃。相比[Deprecated]，这个注解不会附带警告。*/
@MustBeDocumented
@Target(CLASS, FUNCTION, PROPERTY, ANNOTATION_CLASS, CONSTRUCTOR, PROPERTY_SETTER, PROPERTY_GETTER, TYPEALIAS)
annotation class WeakDeprecated(
	val message: String,
	val replaceWith: ReplaceWith = ReplaceWith("")
)
