package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationTarget.*

/**标注为未测试/未通过测试的项。*/
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@MustBeDocumented
annotation class NotTested(
	val message: String = "Not tested."
)
