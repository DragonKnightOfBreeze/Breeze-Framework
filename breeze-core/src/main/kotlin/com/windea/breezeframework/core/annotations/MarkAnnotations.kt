package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * Items that is not implemented (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotImplemented(
	val message: String = ""
)

/**
 * Items that is not included (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotIncluded(
	val message: String = ""
)

/**
 * Items that is not tested (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotTested(
	val message: String = ""
)

/**
 * Items that is not optimized (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotOptimized(
	val message: String = ""
)

/**
 * Items that is not recommended (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotRecommended(
	val message: String = ""
)

/**
 * Items that is not included (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotSuitable(
	val message: String = ""
)

/**
 * Items that is not usable (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotUsable(
	val message:String = ""
)

/**
 * Items that is not sure (yet, always or at most times).
 * @property message additional note information.
 */
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class NotSure(
	val message:String = ""
)
