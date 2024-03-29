// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.annotation

import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**
 * 适用于Kotlin的allOpen编译器插件的标准注解。
 * 让被注解的类及其属性和方法默认是开放的。
 * 需要自行配置。
 *
 * Annotation that is used together with kotlin compiler plugin `allOpen`.
 * Let the annotated classes and it's properties and methods be open by default.
 * Should be configured manually.
 */
@Target(CLASS)
@Retention(BINARY)
annotation class AllOpen

/**
 * 适用于Kotlin的noArg编译器插件的标准注解。
 * 为被注解的类生成仅能通过反射调用的无参构造方法。
 * 需要自行配置。
 *
 * Annotation that is used together with kotlin compiler plugin 'noArg'.
 * Let the annotated classes generated an no-arg constructor that can be invoked by reflection.
 * Should be configured manually.
 */
@Target(CLASS)
@Retention(BINARY)
annotation class NoArg

/**
 * 注明这个方法不应以内联以外的方式调用。
 * 这个注解是[kotlin.internal.InlineOnly]的占位符。
 *
 * Specifies that this function should not be called directly without inlining.
 * This annotation is a placeholder of [kotlin.internal.InlineOnly].
 */
@Target(FUNCTION, PROPERTY, PROPERTY_GETTER, PROPERTY_SETTER)
@Retention(BINARY)
annotation class InlineOnly

/**
 * 注明这个注解对应的项（在通常情况下）已废弃。
 * 相比[Deprecated]，这个注解不会附带警告。
 *
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
