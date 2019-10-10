package com.windea.breezeframework.core.annotations.di

import kotlin.annotation.AnnotationTarget.*

/**标注为可省略的项。*/
@MustBeDocumented
@Target(PROPERTY, FIELD, LOCAL_VARIABLE, FUNCTION)
annotation class OmittedValue(
	/**值（表达式）。*/
	val value: String
)
