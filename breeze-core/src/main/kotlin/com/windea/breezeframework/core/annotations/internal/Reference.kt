package com.windea.breezeframework.core.annotations.internal

import org.intellij.lang.annotations.*
import kotlin.annotation.AnnotationTarget.*

/**标注这个实现参考自哪里。*/
@Target(FILE, PROPERTY)
@MustBeDocumented
@Repeatable
@Retention(AnnotationRetention.SOURCE)
@Suppress("SpellCheckingInspection")
internal annotation class Reference(
	/**引用地址。以Markdown地址表示。为了便于转移到文档注释。*/
	@Language("Markdown")
	val value: String = "a1231bdljlka"
)
