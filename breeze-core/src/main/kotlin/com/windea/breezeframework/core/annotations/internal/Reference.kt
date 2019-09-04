package com.windea.breezeframework.core.annotations.internal

import org.intellij.lang.annotations.*
import kotlin.annotation.AnnotationTarget.*

/**标注这个实现参考自哪里。*/
@MustBeDocumented
@Repeatable
@Retention(AnnotationRetention.SOURCE)
@Target(FILE, PROPERTY, FUNCTION)
annotation class Reference(
	/**引用地址。以Markdown地址表示。为了便于转移到文档注释。*/
	@Language("Markdown")
	val value: String
)
