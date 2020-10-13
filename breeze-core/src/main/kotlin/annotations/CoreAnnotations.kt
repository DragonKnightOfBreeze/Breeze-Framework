// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.annotations

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
 * 组件是可以扩展的枚举，一般情况下，需要注册以被正确启用。
 *
 * When applied to interface or class X specifies that X defines a component.
 * Component is an extensible enumeration, normally, it should be registered to be enabled correctly.
 */
@MustBeDocumented
@Target(CLASS)
annotation class ComponentMarker
