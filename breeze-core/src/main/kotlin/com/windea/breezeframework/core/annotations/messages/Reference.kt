package com.windea.breezeframework.core.annotations.messages

import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.*

/**外部引用。*/
@MustBeDocumented
@Target(CLASS, PROPERTY)
annotation class Reference(
	val value: Array<KClass<*>>
)
