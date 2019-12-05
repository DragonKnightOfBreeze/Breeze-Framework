package com.windea.breezeframework.core.annotations.messages

import kotlin.annotation.AnnotationTarget.*

/**名字。*/
@MustBeDocumented
@Target(CLASS, PROPERTY, FIELD)
annotation class Name(
	/**名字一览。*/
	vararg val value: String
)

