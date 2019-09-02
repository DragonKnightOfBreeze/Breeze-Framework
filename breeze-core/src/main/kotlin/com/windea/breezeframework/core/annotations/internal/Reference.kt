package com.windea.breezeframework.core.annotations.internal

import org.intellij.lang.annotations.*

/**标注这个实现参考自哪里。*/
@Target(AnnotationTarget.FILE, AnnotationTarget.PROPERTY)
@MustBeDocumented
internal annotation class Reference(
	/**引用地址。以Markdown地址表示。为了便于转移到文档注释。*/
	@Language("Markdown")
	val reference: String = ""
)
