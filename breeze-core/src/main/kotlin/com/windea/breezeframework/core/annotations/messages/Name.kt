package com.windea.breezeframework.core.annotations.messages

import com.windea.breezeframework.core.annotations.marks.*
import org.intellij.lang.annotations.*
import org.jetbrains.annotations.*
import kotlin.annotation.AnnotationTarget.*

/**本地化名字。*/
@NotImplemented("Kotlin暂不支持非Source保留级别的可重复注解。")
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
