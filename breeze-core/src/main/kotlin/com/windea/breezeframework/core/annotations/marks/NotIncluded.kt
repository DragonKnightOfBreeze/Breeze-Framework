package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationTarget.*

/**标注为未包含的项。*/
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@MustBeDocumented
annotation class NotIncluded(
	val message: String = "Not included."
)
