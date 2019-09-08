package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationTarget.*

/**标注为不推荐的项。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotRecommended(
	val value: String = "Not recommended."
)
