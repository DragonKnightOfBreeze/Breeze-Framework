package com.windea.breezeframework.core.annotations.messages

import kotlin.annotation.AnnotationTarget.*

/**本地化名字组。*/
@MustBeDocumented
@Target(CLASS, PROPERTY)
annotation class Names(
	/**本地化名字组。*/
	val value: Array<Name>
)
