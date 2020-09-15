/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.core.annotations

import kotlin.annotation.AnnotationTarget.*

/**
 * Annotation that provide a list of names the to annotated declaration.
 * @property value the name list.
 * */
@MustBeDocumented
@Target(CLASS, PROPERTY, FIELD)
annotation class Name(
	vararg val value: String
)

/**
 * Annotation that provide a list of aliases the to annotated declaration.
 * @property value the alias list.
 * */
@MustBeDocumented
@Target(CLASS, PROPERTY, FIELD)
annotation class Alias(
	vararg val value: String
)

/**
 * Annotation that provide a list of references the to annotated declaration.
 * @property value the reference list.
 * */
@MustBeDocumented
@Target(CLASS, PROPERTY, FIELD, FUNCTION, FILE)
annotation class Reference(
	vararg val value:String
)
