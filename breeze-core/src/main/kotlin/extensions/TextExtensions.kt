// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("TextExtensions")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.domain.*
import java.util.*

//region Format extensions
private val defaultPlaceholder = "{" to "}"

/**
 * 根据指定的格式化类型，格式化当前字符串。可以指定可选的语言环境和占位符。
 */
@UnstableApi
fun String.formatBy(type: FormatType, vararg args: Any?, locale: Locale? = null, placeholder: Pair<String, String>? = defaultPlaceholder): String {
	return type.formatter(this, args, locale, placeholder)
}
//endregion

//region Quote extensions
private val quotes = charArrayOf('\'', '\"', '`')

/**
 * 尝试使用指定的引号包围当前字符串。
 * 适用于单引号、双引号、反引号。
 * 默认忽略其中的引号，不对其进行转义。
 */
fun String.quote(quote: Char, omitQuotes: Boolean = true): String {
	return when {
		quote !in quotes -> throw IllegalArgumentException("Invalid quote: $quote.")
		this.surroundsWith(quote) -> this
		omitQuotes -> this.addSurrounding(quote.toString())
		else -> this.replace(quote.toString(), "\\$quote").addSurrounding(quote.toString())
	}
}

/**
 * 尝试去除当前字符串两侧的引号。如果没有，则返回自身。
 * 适用于单引号、双引号、反引号。
 * 默认忽略其中的引号，不对其进行反转义。
 */
fun String.unquote(omitQuotes: Boolean = true): String {
	val quote = this.firstOrNull()
	return when {
		quote == null -> this
		quote !in quotes -> this
		!this.surroundsWith(quote) -> this
		omitQuotes -> this.removeSurrounding(quote.toString())
		else -> this.removeSurrounding(quote.toString()).replace("\\$quote", quote.toString())
	}
}
//endregion

//region Escape extensions
/**
 * 根据指定的转义器，转义当前字符串。
 */
fun String.escapeBy(escaper: Escaper): String {
	return escaper.escape(this)
}

/**
 * 根据指定的转义器，反转义当前字符串。
 */
fun String.unescapeBy(escaper: Escaper): String {
	return escaper.unescape(this)
}
//endregion

//region Match extensions
/**
 * 根据指定的匹配类型，将当前字符串转化为对应的正则表达式。
 */
fun String.toRegexBy(type: MatchType): Regex {
	return type.regexTransform(this).toRegex()
}

/**
 * 根据指定的匹配类型，将当前字符串转化为对应的正则表达式。
 */
fun String.toRegexBy(type: MatchType, option: RegexOption): Regex {
	return type.regexTransform(this).toRegex(option)
}

/**
 * 根据指定的匹配类型，将当前字符串转化为对应的正则表达式。
 */
fun String.toRegexBy(type: MatchType, options: Set<RegexOption>): Regex {
	return type.regexTransform(this).toRegex(options)
}
//endregion

//region Letter Case extensions
/**
 * 得到当前字符串的字母格式。
 */
val String.letterCase: LetterCase? get() = LetterCase.infer(this)

/**
 * 根据指定的字母格式，分割当前字符串，返回对应的字符串列表。
 */
fun String.splitBy(letterCase: LetterCase): List<String> {
	return letterCase.split(this)
}

/**
 * 根据指定的字母格式，分割当前字符串，返回对应的字符串序列。
 */
fun String.splitToSequenceBy(letterCase: LetterCase): Sequence<String> {
	return letterCase.splitToSequence(this)
}

/**
 * 根据指定的字母格式，将当前字符串数组中的元素加入到字符串。
 */
fun Array<String>.joinToStringBy(letterCase: LetterCase): String {
	return letterCase.joinToString(this)
}

/**
 * 根据指定的字母格式，将当前字符串集合中的元素加入到字符串。
 */
fun Iterable<String>.joinToStringBy(letterCase: LetterCase): String {
	return letterCase.joinToString(this)
}

/**
 * 根据指定的字母格式，将当前字符串序列中的元素加入到字符串。
 */
fun Sequence<String>.joinToStringBy(letterCase: LetterCase): String {
	return letterCase.joinToString(this)
}

/**
 * 根据指定的字母格式，切换当前字符串的格式。
 */
fun String.switchCaseBy(sourceLetterCase: LetterCase, targetLetterCase: LetterCase): String {
	return splitBy(sourceLetterCase).joinToStringBy(targetLetterCase)
}

/**
 * 根据指定的字母格式，切换当前字符串的格式。可以自动推导出当前字符串的字母格式，
 */
fun String.switchCaseBy(targetLetterCase: LetterCase): String {
	val sourceLetterCase = this.letterCase ?: throw IllegalArgumentException("Cannot infer letter case for string '$this'.")
	return switchCaseBy(sourceLetterCase, targetLetterCase)
}
//endregion

//region ReferenceCase extensions

//TODO

fun String.splitBy(referenceCase: ReferenceCase): List<String> {
	return referenceCase.splitter(this)
}

fun Iterable<CharSequence>.joinToStringBy(referenceCase: ReferenceCase): String {
	return referenceCase.joiner(this)
}

fun Array<out CharSequence>.joinToStringBy(referenceCase: ReferenceCase): String {
	return referenceCase.arrayJoiner(this)
}
//endregion

//region Convert extensions
/**
 * 将当前对象转化成文本。
 * * 如果当前对象是空值，则返回空字符串。
 * * 如果存在转化方法，则使用该方法将当前对象转化为字符串。
 */
fun <T : Any> T?.toText(transform:((T) -> String)? = null):String {
	return if(this == null) "" else if(transform != null) transform(this) else toString()
}

/**
 * 将当前布尔值转换成文本。
 * * 如果当前对象是空值，则返回空字符串。
 */
fun Boolean?.toText(trueValue:String,falseValue:String):String{
	return if(this == null) "" else if(this) trueValue else falseValue
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
//endregion
