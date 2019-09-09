package com.windea.breezeframework.core.annotations.messages

import org.intellij.lang.annotations.*
import org.jetbrains.annotations.*
import kotlin.annotation.AnnotationTarget.*

/**本地化概述。*/
@MustBeDocumented
@Repeatable
@Target(CLASS, PROPERTY)
annotation class Summary(
	/**本地化文本。*/
	@Nls @Language("Markdown")
	val text: String,
	/**语言环境。*/
	val locale: String
)
