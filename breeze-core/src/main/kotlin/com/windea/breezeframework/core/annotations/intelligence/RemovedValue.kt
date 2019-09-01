package com.windea.breezeframework.core.annotations.intelligence

import kotlin.annotation.AnnotationTarget.*

/**可被移除的项的注解。*/
@MustBeDocumented
@Repeatable
@Target(PROPERTY, FIELD, LOCAL_VARIABLE, FUNCTION)
annotation class RemovedValue(
	val valueExpression: String,
	val message: String = "Removed value."
)
