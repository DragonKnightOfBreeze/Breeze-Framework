package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationTarget.*

/**标注为不适用的项。*/
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@MustBeDocumented
annotation class NotSuitable(
	val message: String = "Not suitable."
)
