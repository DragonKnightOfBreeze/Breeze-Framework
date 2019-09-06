package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationTarget.*

/**适用Kotlin的noArg编译器插件的注解。为被注解的类生成仅能通过反射调用的无参构造防擦。*/
@Target(CLASS)
annotation class NoArg