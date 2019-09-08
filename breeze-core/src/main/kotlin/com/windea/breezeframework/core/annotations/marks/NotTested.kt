package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationTarget.*

/**标注为未测试/未通过测试的项。*/
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotTested(
	val value: String = "Not tested."
)
