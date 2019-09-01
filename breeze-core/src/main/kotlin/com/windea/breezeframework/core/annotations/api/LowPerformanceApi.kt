package com.windea.breezeframework.core.annotations.api

import kotlin.annotation.AnnotationTarget.*

/**可能执行低性能操作的api。*/
@MustBeDocumented
@Target(PROPERTY, FUNCTION)
annotation class LowPerformanceApi
