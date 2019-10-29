package com.windea.breezeframework.core.annotations.messages

import kotlin.annotation.AnnotationTarget.*

//NOTE Kotlin暂不支持非Source保留级别的可重复注解。

/**本地化名字。*/
@MustBeDocumented
@Target(CLASS, PROPERTY)
annotation class Name(
	/**本地化文本。*/
	val text: String,
	/**语言环境。*/
	val locale: String
)

