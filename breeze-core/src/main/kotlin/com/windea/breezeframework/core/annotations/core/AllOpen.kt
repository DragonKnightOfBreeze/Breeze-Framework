package com.windea.breezeframework.core.annotations.core

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
