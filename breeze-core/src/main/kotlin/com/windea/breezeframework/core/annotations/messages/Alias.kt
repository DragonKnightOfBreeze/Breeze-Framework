package com.windea.breezeframework.core.annotations.messages

import kotlin.annotation.AnnotationTarget.*

/**别名。*/
@MustBeDocumented
@Target(CLASS, PROPERTY, FIELD)
annotation class Alias(
	/**别名一览。*/
	vararg val value: String
)
