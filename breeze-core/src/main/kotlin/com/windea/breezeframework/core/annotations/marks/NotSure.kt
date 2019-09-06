package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationTarget.*

/**标注为未确定的项。*/
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@MustBeDocumented
annotation class NotSure(
	val message: String = "Not sure."
)
