// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.annotation

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * 未实现的项（至今为止，总是如此或者在多数情况下）。
 *
 * Items that is not implemented (yet, always or at most times).
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotImplemented(
	val message: String = "",
)

/**
 * 未包含的项（至今为止，总是如此或者在多数情况下）。
 *
 * Items that is not included (yet, always or at most times).
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotIncluded(
	val message: String = "",
)

/**
 * 未测试的项（至今为止，总是如此或者在多数情况下）。
 *
 * Item that is not tested (yet, always or at most times).
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotTested(
	val message: String = "",
)

/**
 * 未优化的项（至今为止，总是如此或者在多数情况下）。
 *
 * Item that is not optimized (yet, always or at most times).
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotOptimized(
	val message: String = "",
)

/**
 * 不推荐的项（至今为止，总是如此或者在多数情况下）。
 *
 * Item that is not recommended (yet, always or at most times).
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotRecommended(
	val message: String = "",
)

/**
 * 不适用的项（至今为止，总是如此或者在多数情况下）。
 *
 * Item that is not suitable (yet, always or at most times).
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotSuitable(
	val message: String = "",
)

/**
 * 不可用的项（至今为止，总是如此或者在多数情况下）。
 *
 * Item that is not usable (yet, always or at most times).
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotUsable(
	val message: String = "",
)

/**
 * 不确定的项（至今为止，总是如此或者在多数情况下）。
 *
 * Item that is not sure (yet, always or at most times).
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotSure(
	val message: String = "",
)
