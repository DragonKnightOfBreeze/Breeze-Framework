/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * Items that is not implemented (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotImplemented(
	val message: String = ""
)

/**
 * Items that is not included (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotIncluded(
	val message: String = ""
)

/**
 * Item that is not tested (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotTested(
	val message: String = ""
)

/**
 * Item that is not optimized (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotOptimized(
	val message: String = ""
)

/**
 * Item that is not recommended (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotRecommended(
	val message: String = ""
)

/**
 * Item that is not included (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotSuitable(
	val message: String = ""
)

/**
 * Item that is not usable (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotUsable(
	val message:String = ""
)

/**
 * Item that is not sure (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY, FUNCTION, FILE)
@Retention(SOURCE)
annotation class NotSure(
	val message:String = ""
)
