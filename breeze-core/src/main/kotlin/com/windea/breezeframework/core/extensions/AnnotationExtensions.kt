package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.annotations.messages.*
import java.util.*
import kotlin.reflect.*

/**得到可注解元素的指定类型的满足指定预测的首个注解。*/
@OutlookImplementationApi
inline fun <reified A : Annotation> KAnnotatedElement.findAnnotation(predicate: (A) -> Boolean): A? =
	this.annotations.filterIsInstance<A>().firstOrNull(predicate)

/**得到可注解元素的指定类型的注解列表。注意：Kotlin目前只能使用SOURCE级别的可重复注解。*/
@OutlookImplementationApi
inline fun <reified A : Annotation> KAnnotatedElement.findAnnotations(): List<A> =
	this.annotations.filterIsInstance<A>()

/**得到可注解元素的指定类型的满足指定预测的注解列表。注意：Kotlin目前只能使用SOURCE级别的可重复注解。*/
@OutlookImplementationApi
inline fun <reified A : Annotation> KAnnotatedElement.findAnnotations(predicate: (A) -> Boolean): List<A> =
	this.annotations.filterIsInstance<A>().filter(predicate)


/**访问注解值。*/
val KAnnotatedElement.annotatedValues by AnnotatedValuesAccessor()

class AnnotatedValuesAccessor internal constructor() {
	private lateinit var target: KAnnotatedElement
	
	private var currentLocale: String = Locale.getDefault().toString()
	
	/**得到目标的本地化名字的文本-语言环境对。*/
	val namePairs: List<Pair<String, String>> by lazy { target.findAnnotations<Name>().map { it.text to it.locale } }
	
	/**得到目标的本地化标签的文本-语言环境对。*/
	val tagsPairs: List<Pair<List<String>, String>> by lazy { target.findAnnotations<Tags>().map { it.text.toList() to it.locale } }
	
	/**得到目标的本地化概述的文本-语言环境对。*/
	val summaryPairs: List<Pair<String, String>> by lazy { target.findAnnotations<Summary>().map { it.text to it.locale } }
	
	/**得到目标的本地化描述的文本-语言环境对。*/
	val descriptionPairs: List<Pair<String, String>> by lazy { target.findAnnotations<Description>().map { it.text.toMultilineText() to it.locale } }
	
	
	/**得到目标的本地化名字。可指定语言环境，默认为默认语言环境。可以指定索引，默认为第1个。*/
	fun name(locale: String? = null, index: Int = 0): String? =
		namePairs.filter { it.second == locale ?: currentLocale }[index].first
	
	/**得到目标的本地化标签。可指定语言环境，默认为默认语言环境。可以指定索引，默认为第1个。*/
	fun tags(locale: String? = null, index: Int = 0): List<String> =
		tagsPairs.filter { it.second == locale ?: currentLocale }[index].first
	
	/**得到目标的本地化概述。可指定语言环境，默认为默认语言环境。可以指定索引，默认为第1个。*/
	fun summary(locale: String? = null, index: Int = 0): String? =
		summaryPairs.filter { it.second == locale ?: currentLocale }[index].first
	
	/**得到目标的本地化描述。可指定语言环境，默认为默认语言环境。可以指定索引，默认为第1个。*/
	fun description(locale: String? = null, index: Int = 0): String? =
		descriptionPairs.filter { it.second == locale ?: currentLocale }[index].first
	
	operator fun getValue(thisRef: KAnnotatedElement, property: KProperty<*>) = this.apply { target = thisRef }
}
