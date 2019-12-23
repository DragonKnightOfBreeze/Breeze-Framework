package com.windea.breezeframework.core.annotations.api

import org.intellij.lang.annotations.*
import kotlin.annotation.AnnotationRetention.*
import kotlin.annotation.AnnotationTarget.*

/**带有外部引用的Api。*/
@MustBeDocumented
@Repeatable
@Retention(SOURCE)
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class ReferenceApi(
	/**备注信息。*/
	@Language("Markdown")
	val message: String
)
