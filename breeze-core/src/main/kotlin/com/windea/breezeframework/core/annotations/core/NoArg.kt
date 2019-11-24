package com.windea.breezeframework.core.annotations.core

/**适用Kotlin的noArg编译器插件的标准注解。为被注解的类生成仅能通过反射调用的无参构造方法。需要自行配置。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
annotation class NoArg
