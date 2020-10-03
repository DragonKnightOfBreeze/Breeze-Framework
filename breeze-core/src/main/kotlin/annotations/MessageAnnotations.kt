// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationTarget.*

/**
 * 为被注解的声明提供一组名字。
 *
 * Annotation that provide a list of names the to annotated declaration.
 * */
@MustBeDocumented
@Target(CLASS, PROPERTY, FIELD)
annotation class Name(
	vararg val value: String
)

/**
 * 为被注解的声明提供一组别名。
 *
 * Annotation that provide a list of aliases the to annotated declaration.
 */
@MustBeDocumented
@Target(CLASS, PROPERTY, FIELD)
annotation class Alias(
	vararg val value: String
)

/**
 * 为被注解的声明提供一组引用。
 *
 * Annotation that provide a list of references the to annotated declaration.
 */
@MustBeDocumented
@Target(CLASS, PROPERTY, FIELD, FUNCTION, FILE)
annotation class Reference(
	vararg val value:String
)
