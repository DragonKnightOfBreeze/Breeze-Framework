package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**未测试/未通过测试的项。*/
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotTested(
	/**备注信息。*/
	val message: String = ""
)
