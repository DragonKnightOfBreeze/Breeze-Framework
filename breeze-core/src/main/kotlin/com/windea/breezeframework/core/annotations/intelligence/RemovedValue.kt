package com.windea.breezeframework.core.annotations.intelligence

import kotlin.annotation.AnnotationTarget.*

/**标注为可被移除的项。*/
@MustBeDocumented
@Repeatable
@Target(PROPERTY, FIELD, LOCAL_VARIABLE, FUNCTION)
annotation class RemovedValue(
	val valueExpression: String,
	val message: String = "Removed value."
)
