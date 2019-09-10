package com.windea.breezeframework.core.annotations.messages

import org.intellij.lang.annotations.*
import org.jetbrains.annotations.*
import kotlin.annotation.AnnotationTarget.*

/**本地化名字。*/
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY)
annotation class Name(
	/**本地化文本。*/
	@Nls @Language("Markdown")
	val text: String,
	/**语言环境。*/
	val locale: String
)
