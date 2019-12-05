package com.windea.breezeframework.core.annotations.messages

import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.*

/**引用。*/
@MustBeDocumented
@Target(CLASS, PROPERTY, FIELD)
annotation class Reference(
	/**引用一览。*/
	vararg val value: KClass<*>
)
