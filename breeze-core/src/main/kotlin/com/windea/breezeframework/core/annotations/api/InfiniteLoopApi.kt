package com.windea.breezeframework.core.annotations.api

import kotlin.annotation.AnnotationTarget.*

/**可能引起无限循环的api。*/
@MustBeDocumented
@Target(PROPERTY, FUNCTION)
annotation class InfiniteLoopApi

