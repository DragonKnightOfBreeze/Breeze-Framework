package com.windea.breezeframework.core.annotations.di

import kotlin.annotation.AnnotationTarget.*

/**标注为可省略的项。*/
@MustBeDocumented
@Target(PROPERTY, FIELD, LOCAL_VARIABLE, FUNCTION)
annotation class OmitValue(
	/**值表达式。*/
	val valueExpression: String
)
