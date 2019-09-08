package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationTarget.*

/**标注为未确定的项。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotSure(
	val value: String = "Not sure."
)
