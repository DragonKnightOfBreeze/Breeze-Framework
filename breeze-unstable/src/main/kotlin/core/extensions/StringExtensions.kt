// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.domain.*

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
