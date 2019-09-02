package com.windea.breezeframework.core.annotations.intelligence

import kotlin.annotation.AnnotationTarget.*

/**标注为被省略的项。*/
@MustBeDocumented
@Repeatable
@Target(PROPERTY, FIELD, LOCAL_VARIABLE, FUNCTION)
annotation class OmitValue(
	val valueExpression: String,
	val message: String = "Removed value."
)
