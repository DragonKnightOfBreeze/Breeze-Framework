package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * Annotation that is used together with kotlin compiler plugin `allOpen`.
 * Let the annotated classes and it's properties and methods be open by default.
 * Should be configured manually.
 */
@Retention(BINARY)
@Target(CLASS)
annotation class AllOpen

/**
 * Annotation that is used together with kotlin compiler plugin 'noArg'.
 * Let the annotated classes generated an no-arg constructor that can be invoked by reflection.
 * Should be configured manually.
 */
@Retention(BINARY)
@Target(CLASS)
annotation class NoArg

/**
 * When applied to method X specifies that X defines a Todo method.
 */
@MustBeDocumented
@Retention(BINARY)
@Target(FUNCTION)
@DslMarker
annotation class TodoMarker

/**
 * Marks the annotated declaration as deprecated **in common situations**.
 * While comparing to [Deprecated], this annotation do not get an IDE waring.
 */
@MustBeDocumented
@Target(CLASS, FUNCTION, PROPERTY, ANNOTATION_CLASS, CONSTRUCTOR, PROPERTY_SETTER, PROPERTY_GETTER, TYPEALIAS)
annotation class WeakDeprecated(
	val message: String,
	val replaceWith: ReplaceWith = ReplaceWith("")
)
