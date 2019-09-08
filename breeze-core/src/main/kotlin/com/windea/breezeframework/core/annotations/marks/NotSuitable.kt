package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationTarget.*

/**标注为不适用的项。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotSuitable(
	val value: String = "Not suitable."
)
