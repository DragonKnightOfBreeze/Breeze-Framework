package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.annotations.marks.*
import kotlin.reflect.*

/**得到可注解元素的指定类型的满足指定预测的首个注解。*/
@OutlookImplementationApi
@NotSuitable("Kotlin目前只支持SOURCE级别的可重复注解。")
inline fun <reified T : Annotation> KAnnotatedElement.findAnnotation(predicate: (T) -> Boolean): T? =
	this.annotations.filterIsInstance<T>().firstOrNull(predicate)

/**得到可注解元素的指定类型的注解列表。*/
@OutlookImplementationApi
@NotSuitable("Kotlin目前只支持SOURCE级别的可重复注解。")
inline fun <reified T : Annotation> KAnnotatedElement.findAnnotations(): List<T> =
	this.annotations.filterIsInstance<T>()

/**得到可注解元素的指定类型的满足指定预测的注解列表。*/
@OutlookImplementationApi
@NotSuitable("Kotlin目前只支持SOURCE级别的可重复注解。")
inline fun <reified T : Annotation> KAnnotatedElement.findAnnotations(predicate: (T) -> Boolean): List<T> =
	this.annotations.filterIsInstance<T>().filter(predicate)
