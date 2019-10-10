package com.windea.breezeframework.core.annotations.di

import kotlin.annotation.AnnotationTarget.*

/**标注为可被替换的项。*/
@MustBeDocumented
@Repeatable
@Target(PROPERTY, FIELD, LOCAL_VARIABLE, FUNCTION)
annotation class ReplacedValue(
	/**值（表达式）。*/
	val value: String,
	/**条件（表达式）。*/
	val condition: String
)
