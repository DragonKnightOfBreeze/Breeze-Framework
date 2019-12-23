package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**未确定的项。*/
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotSure(
	/**备注信息。*/
	val message: String = ""
)
