package com.windea.utility.common.extensions

import com.windea.utility.common.annotations.messages.*
import kotlin.reflect.*

/**得到可注解元素的指定类型的满足指定预测的首个注解。*/
inline fun <reified A : Annotation> KAnnotatedElement.findAnnotation(predicate: (A) -> Boolean): A? {
	return this.annotations.filterIsInstance<A>().firstOrNull(predicate)
}

/**得到可注解元素的指定类型的注解列表。*/
inline fun <reified A : Annotation> KAnnotatedElement.findAnnotations(): List<A> =
	this.annotations.filterIsInstance<A>()

/**得到可注解元素的指定类型的满足指定预测的注解列表。*/
inline fun <reified A : Annotation> KAnnotatedElement.findAnnotations(predicate: (A) -> Boolean): List<A> =
	this.annotations.filterIsInstance<A>().filter(predicate)


/**得到目标的本地化名字。*/
fun Any.annotatedName(locale: String = "Chs"): String? =
	this::class.findAnnotation<Name> { it.locale == locale }?.text

/**得到目标的本地化概述。*/
fun Any.annotatedSummary(locale: String = "Chs"): String? =
	this::class.findAnnotation<Summary> { it.locale == locale }?.text

/**得到目标的本地化描述。*/
fun Any.annotatedDescription(locale: String = "Chs"): String? =
	this::class.findAnnotation<Description> { it.locale == locale }?.text
