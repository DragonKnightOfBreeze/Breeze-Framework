package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**未实现的项。*/
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotImplemented(
	/**备注信息。*/
	val message: String = ""
)
