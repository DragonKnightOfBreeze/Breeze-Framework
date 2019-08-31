package com.windea.utility.common.annotations.intelligence

import kotlin.annotation.AnnotationTarget.*

/**被省略的项的注解。*/
@MustBeDocumented
@Repeatable
@Target(PROPERTY, FIELD, LOCAL_VARIABLE)
annotation class OmitValue(
	val valueExpression: String,
	val message: String = "Removed value."
)
