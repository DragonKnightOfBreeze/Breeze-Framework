// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("TextExtensions")

package com.windea.breezeframework.core.extensions

/**
 * 将当前对象转化成文本。
 * * 如果当前对象是空值，则返回空字符串。
 * * 如果存在转化方法，则使用该方法将当前对象转化为字符串。
 */
fun <T : Any> T?.toText(transform:((T) -> String)? = null):String {
	return if(this == null) "" else if(transform != null) transform(this) else toString()
}


/**
 * 将当前数组拼接并转化成文本。
 * 默认忽略空数组，忽略空元素，没有缩进。
 * * 允许指定前缀、后缀、分隔符和缩进。
 * * 如果忽略空数组，且元素全部为空值，则返回空字符串。
 * * 如果忽略空元素，且元素为空值，则忽略该元素。
 * * 如果存在转化方法，则使用该方法将元素转化为字符串。
 */
fun <T : Any> Array<out T?>.joinToText(
	separator: CharSequence = ", ",
	prefix: CharSequence = "",
	postfix: CharSequence = "",
	indent: CharSequence = "",
	omitEmpty: Boolean = true,
	omitEmptyElement: Boolean = true,
	transform: ((T) -> CharSequence)? = null
):String {
	var result = buildString {
		var count = 0
		for(element in this@joinToText) {
			val snippet = if(element == null) null else if(transform != null) transform(element) else element.toString()
			if(omitEmptyElement && snippet.isNullOrEmpty()) continue
			if(count++ > 0) append(separator)
			append(snippet)
		}
	}
	if(indent.isNotEmpty()) result = result.prependIndent(indent.toString())
	if(omitEmpty && result.isNotEmpty()) result = "$prefix$result$postfix"
	return result
}

/**
 * 将当前集合拼接并转化成文本。
 * 默认忽略空集合，忽略空元素，没有缩进。
 * * 允许指定前缀、后缀、分隔符和缩进。
 * * 如果忽略空集合，且元素全部为空值，则返回空字符串。
 * * 如果忽略空元素，且元素为空值，则忽略该元素。
 * * 如果存在转化方法，则使用该方法将元素转化为字符串。
 */
fun <T : Any> Iterable<T?>.joinToText(
	separator: CharSequence = ", ",
	prefix: CharSequence = "",
	postfix: CharSequence = "",
	indent: CharSequence = "",
	omitEmpty: Boolean = true,
	omitEmptyElement: Boolean = true,
	transform: ((T) -> CharSequence)? = null,
):String {
	var result = buildString {
		var count = 0
		for(element in this@joinToText) {
			val snippet = if(element == null) null else if(transform != null) transform(element) else element.toString()
			if(omitEmptyElement && snippet.isNullOrEmpty()) continue
			if(count++ > 0) append(separator)
			append(snippet)
		}
	}
	if(indent.isNotEmpty()) result = result.prependIndent(indent.toString())
	if(omitEmpty && result.isNotEmpty()) result = "$prefix$result$postfix"
	return result
}

/**
 * 将当前映射拼接并转化成文本。
 * 默认忽略空映射，忽略空值，没有缩进。
 * * 允许指定前缀、后缀、分隔符和缩进。
 * * 如果忽略空映射，且键值对的值全部为空值，则返回空字符串。
 * * 如果忽略空值，且键值对的值为空值，则忽略该键值对。
 * * 如果存在转化方法，则使用该方法将键值对转化为字符串。
 */
fun <K, V> Map<K, V>.joinToText(
	separator: CharSequence = ", ",
	prefix: CharSequence = "",
	postfix: CharSequence = "",
	indent: CharSequence = "",
	omitEmpty: Boolean = true,
	omitEmptyValue: Boolean = true,
	transform: ((Map.Entry<K, V>) -> CharSequence)? = null,
):String {
	var result =  buildString {
		var count = 0
		for(entry in this@joinToText) {
			val valueSnippet = entry.value?.toString()
			if(omitEmptyValue && valueSnippet.isNullOrEmpty()) continue
			val snippet = if(transform == null) entry.toString() else transform(entry)
			if(count++ > 0) append(separator)
			append(snippet)
		}
	}
	if(indent.isNotEmpty()) result = result.prependIndent(indent.toString())
	if(omitEmpty && result.isNotEmpty()) result = "$prefix$result$postfix"
	return result
}

/**
 * 将当前序列拼接并转化成文本。
 * 默认忽略空序列，忽略空元素，没有缩进。
 * * 允许指定前缀、后缀、分隔符和缩进。
 * * 如果忽略空序列，且元素全部为空值，则返回空字符串。
 * * 如果忽略空元素，且元素为空值，则忽略该元素。
 * * 如果存在转化方法，则使用该方法将元素转化为字符串。
 */
fun <T : Any> Sequence<T?>.joinToText(
	separator: CharSequence = ", ",
	prefix: CharSequence = "",
	postfix: CharSequence = "",
	indent: CharSequence = "",
	omitEmpty: Boolean = true,
	omitEmptyElement: Boolean = true,
	transform: ((T) -> CharSequence)? = null,
):String {
	var result =  buildString {
		var count = 0
		for(element in this@joinToText) {
			val snippet = if(element == null) null else if(transform != null) transform(element) else element.toString()
			if(omitEmptyElement && snippet.isNullOrEmpty()) continue
			if(count++ > 0) append(separator)
			append(snippet)
		}
	}
	if(indent.isNotEmpty()) result = result.prependIndent(indent.toString())
	if(omitEmpty && result.isNotEmpty()) result = "$prefix$result$postfix"
	return result
}
