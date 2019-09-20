package com.windea.breezeframework.core.annotations.marks

import org.intellij.lang.annotations.*
import kotlin.annotation.AnnotationTarget.*

/**标注这个实现参考自哪里。*/
@MustBeDocumented
@Repeatable
@Retention(AnnotationRetention.SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class Reference(
	/**引用地址。*/
	@Language("Markdown")
	val value: String
)
