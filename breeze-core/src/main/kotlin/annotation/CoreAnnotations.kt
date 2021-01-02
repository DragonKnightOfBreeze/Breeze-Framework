// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.annotation

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * Annotation that is used together with kotlin compiler plugin `allOpen`.
 * Let the annotated classes and it's properties and methods be open by default.
 * Should be configured manually.
 */
@Target(CLASS)
@Retention(BINARY)
annotation class AllOpen

/**
 * Annotation that is used together with kotlin compiler plugin 'noArg'.
 * Let the annotated classes generated an no-arg constructor that can be invoked by reflection.
 * Should be configured manually.
 */
@Target(CLASS)
@Retention(BINARY)
annotation class NoArg

/**
 * Specifies that this function should not be called directly without inlining.
 * This annotation is a placeholder of [kotlin.internal.InlineOnly].
 */
@Target(FUNCTION, PROPERTY, PROPERTY_GETTER, PROPERTY_SETTER)
@Retention(BINARY)
annotation class InlineOnly

/**
 * Marks the annotated declaration as deprecated in common situations.
 * While comparing to [Deprecated], this annotation do not get an IDE warning.
 */
@MustBeDocumented
@Target(CLASS, FUNCTION, PROPERTY, ANNOTATION_CLASS, CONSTRUCTOR, PROPERTY_SETTER, PROPERTY_GETTER, TYPEALIAS)
annotation class WeakDeprecated(
	val message: String,
	val replaceWith: ReplaceWith = ReplaceWith(""),
)


/**
 * 当应用到方法X时，表示X定义了一个TODO方法。
 *
 * When applied to method X specifies that X defines a Todo method.
 */
@MustBeDocumented
@Target(FUNCTION)
@Retention(BINARY)
annotation class TodoMarker


/**
 * 当应用到接口或类X时，表示X定义了一个组件。
 * 组件用于实现某个特定的功能，拥有数种不同类型的实现，并可进行扩展。
 * 一般情况下，需要注册以被正确启用。
 *
 * When applied to interface or class X specifies that X defines a component.
 * Component is used to implement a specific function, with various types of implementation, and is extensible.
 * Normally, it should be registered to be enabled correctly.
 */
@MustBeDocumented
@Target(CLASS)
annotation class BreezeComponent

/**
 * 当应用到扩展方法X时，表示X定义了一个基于组件的扩展。
 *
 * When applied to extension function X specifies that X defines a component-based extension.
 */
@MustBeDocumented
@Target(FUNCTION)
annotation class BreezeComponentExtension
