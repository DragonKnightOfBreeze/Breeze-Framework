package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * 适用于Kotlin的allOpen编译器插件的标准注解。
 * 让被注解的类及其属性和方法默认是开放的。
 * 需要自行配置。
 */
@MustBeDocumented
@Retention(BINARY)
@Target(CLASS)
annotation class AllOpen

/**
 * 适用于Kotlin的noArg编译器插件的标准注解。
 * 为被注解的类生成仅能通过反射调用的无参构造方法。
 * 需要自行配置。
 */
@MustBeDocumented
@Retention(BINARY)
@Target(CLASS)
annotation class NoArg

/**注明这个注解定义了一个待办方法。*/
@MustBeDocumented
@Retention(BINARY)
@Target(FUNCTION)
annotation class TodoMarker
