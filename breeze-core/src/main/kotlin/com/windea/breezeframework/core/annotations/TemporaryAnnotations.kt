package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationRetention.*

//NOTE 临时性的注解，不要在框架外部使用

/**不需要在非空类型上调用的扩展方法。*/
@Retention(SOURCE)
annotation class UselessCallOnNotNullType

/**暗示智能类型转换，但是无法适用的扩展方法。*/
@Retention(SOURCE)
annotation class ImpliesSmartCast

/**有待以自动生成的方式编写的代码。*/
@Retention(SOURCE)
annotation class ToBeGeneratedCode
