package com.windea.breezeframework.core.annotations.di

import kotlin.annotation.AnnotationTarget.*

/**标注为可被注入的项。*/
@MustBeDocumented
@Repeatable
@Target(PROPERTY, FIELD, LOCAL_VARIABLE, FUNCTION)
annotation class InjectedValue(
	/**值（表达式）。*/
	val value: String
)