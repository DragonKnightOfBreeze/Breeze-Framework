package com.windea.utility.common.annotations.intelligence

import kotlin.annotation.AnnotationTarget.*

/**可被替换的项的注解。*/
@MustBeDocumented
@Repeatable
@Target(PROPERTY, FIELD, LOCAL_VARIABLE)
annotation class ReplacedValue(
	val valueExpression: String,
	val message: String = "Replaced value."
)
