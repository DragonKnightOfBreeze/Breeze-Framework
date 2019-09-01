package com.windea.breezeframework.core.annotations.api

import kotlin.annotation.AnnotationTarget.*

/**几乎不可能成为Kotlin后续版本的新特性的api。限于内部实现、访问限制、语法等。*/
@MustBeDocumented
@Target(PROPERTY, FUNCTION)
annotation class NeverFeatureApi
