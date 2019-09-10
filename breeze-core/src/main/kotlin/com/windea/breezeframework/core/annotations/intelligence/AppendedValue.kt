package com.windea.breezeframework.core.annotations.intelligence

import kotlin.annotation.AnnotationTarget.*

/**标注为可被添加的项。*/
@MustBeDocumented
@Repeatable
@Target(PROPERTY, FIELD, LOCAL_VARIABLE, FUNCTION)
annotation class AppendedValue(
	/**值表达式。*/
	val valueExpression: String,
	/**条件。*/
	val condition: String
)
