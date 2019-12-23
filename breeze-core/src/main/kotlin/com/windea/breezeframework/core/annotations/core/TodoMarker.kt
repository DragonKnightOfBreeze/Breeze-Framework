package com.windea.breezeframework.core.annotations.core

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

@MustBeDocumented
@Retention(BINARY)
@Target(FUNCTION)
/**注明这个注解定义了一个TODO方法。*/
annotation class TodoMarker
