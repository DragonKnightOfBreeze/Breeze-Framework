package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**不适用的项。*/
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotSuitable(
	/**备注信息。*/
	val message: String = "",
	/**替换项。*/
	val replaceWith: ReplaceWith = ReplaceWith("")
)
