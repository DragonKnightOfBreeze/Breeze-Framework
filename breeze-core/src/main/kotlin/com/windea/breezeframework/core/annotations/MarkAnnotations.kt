package com.windea.breezeframework.core.annotations

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

/**未包含的项。*/
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotIncluded(
	/**备注信息。*/
	val message: String = ""
)

/**不推荐的项。*/
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

/**未确定的项。*/
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotSure(
	/**备注信息。*/
	val message: String = ""
)

/**未测试的项。*/
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotTested(
	/**备注信息。*/
	val message: String = ""
)

/**不可用的项。*/
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotUsable(
	/**备注信息。*/
	val message: String = ""
)
