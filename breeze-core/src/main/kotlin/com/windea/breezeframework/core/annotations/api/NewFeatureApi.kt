package com.windea.breezeframework.core.annotations.api

import kotlin.annotation.AnnotationTarget.*

/**可能成为Kotlin后续版本的新特性的api。*/
@MustBeDocumented
@Target(PROPERTY, FUNCTION)
annotation class NewFeatureApi
