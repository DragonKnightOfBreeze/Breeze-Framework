package com.windea.breezeframework.core.annotations.di

import kotlin.annotation.AnnotationTarget.*

/**标注为可被移除的项。*/
@MustBeDocumented
@Repeatable
@Target(PROPERTY, FIELD, LOCAL_VARIABLE, FUNCTION)
annotation class RemovedValue(
	/**值表达式。*/
	val valueExpression: String,
	/**条件。*/
	val condition: String
)
