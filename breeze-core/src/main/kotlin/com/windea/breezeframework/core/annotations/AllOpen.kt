package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationTarget.*

/**适用Kotlin的allOpen编译器插件的注解。让被注解的类及其属性和方法默认是开放的。*/
@Target(CLASS)
annotation class AllOpen
