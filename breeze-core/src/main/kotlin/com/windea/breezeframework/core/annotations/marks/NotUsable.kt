package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationTarget.*

/**标注为不可用的项。*/
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@MustBeDocumented
annotation class NotUsable(
	val message: String = "Not usable."
)
