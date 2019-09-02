package com.windea.breezeframework.core.annotations.intelligence

import kotlin.annotation.AnnotationTarget.*

/**标注为可被替换的项。*/
@MustBeDocumented
@Repeatable
@Target(PROPERTY, FIELD, LOCAL_VARIABLE, FUNCTION)
annotation class ReplacedValue(
	val valueExpression: String,
	val message: String = "Replaced value."
)
