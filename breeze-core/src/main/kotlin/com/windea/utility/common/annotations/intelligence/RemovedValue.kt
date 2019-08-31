package com.windea.utility.common.annotations.intelligence

import kotlin.annotation.AnnotationTarget.*

/**可被移除的项的注解。*/
@MustBeDocumented
@Repeatable
@Target(PROPERTY, FIELD, LOCAL_VARIABLE)
annotation class RemovedValue(
	val valueExpression: String,
	val message: String = "Removed value."
)
