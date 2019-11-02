package com.windea.breezeframework.core.annotations.core

/**适用Kotlin的allOpen编译器插件的标准注解。让被注解的类及其属性和方法默认是开放的。需要自行配置。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class AllOpen
