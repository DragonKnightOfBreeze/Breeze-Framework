// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.model.*
import java.util.*

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


fun String.splitBy(referenceCase: ReferenceCase): List<String> {
	return referenceCase.splitter(this)
}

fun Iterable<CharSequence>.joinToStringBy(referenceCase: ReferenceCase): String {
	return referenceCase.joiner(this)
}

fun Array<out CharSequence>.joinToStringBy(referenceCase: ReferenceCase): String {
	return referenceCase.arrayJoiner(this)
}


private val defaultPlaceholder = "{" to "}"

/**
 * 根据指定的格式化类型，格式化当前字符串。可以指定可选的语言环境和占位符。
 *
 * @see FormatType
 */
@UnstableApi
fun String.formatBy(type: FormatType, vararg args: Any?, locale: Locale? = null, placeholder: Pair<String, String>? = defaultPlaceholder): String {
	return type.formatter(this, args, locale, placeholder)
}
