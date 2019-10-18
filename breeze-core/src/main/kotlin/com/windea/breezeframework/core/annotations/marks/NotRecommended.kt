package com.windea.breezeframework.core.annotations.marks

import kotlin.annotation.AnnotationTarget.*

/**标注为不推荐的项。*/
@MustBeDocumented
@Repeatable
@Retention(AnnotationRetention.SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotRecommended(
	/**备注信息。*/
	val message: String = "",
	/**替换项。*/
	val replaceWith: ReplaceWith = ReplaceWith("")
)
