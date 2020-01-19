package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**不推荐的项。可以用作弱化版的[Deprecated]。*/
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotRecommended(
	/**备注信息。*/
	val message: String = "",
	/**替换项。*/
	val replaceWith: ReplaceWith = ReplaceWith("")
)
