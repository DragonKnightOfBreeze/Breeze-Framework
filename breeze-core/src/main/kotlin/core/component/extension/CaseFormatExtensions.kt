// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("CaseFormatExtensions")

package icu.windea.breezeframework.core.component.extension

import icu.windea.breezeframework.core.component.CaseFormat
import icu.windea.breezeframework.core.component.CaseFormats

/**
 * 尝试推断当前字符串的字母格式。
 *
 * @see CaseFormat
 */
fun String.inferCase(): CaseFormat? {
	return CaseFormats.infer(this)
}

/**
 * 判断当前字符串是否匹配指定的字母格式。
 *
 * @see CaseFormat
 */
fun String.matchesBy(caseFormat: CaseFormat): Boolean {
	return caseFormat.matches(this)
}

/**
 * 根据指定的字母格式，分割当前字符串，返回对应的字符串列表。
 *
 * @see CaseFormat
 */
fun String.splitBy(caseFormat: CaseFormat): List<String> {
	return caseFormat.split(this)
}

/**
 * 根据指定的字母格式，分割当前字符串，返回对应的字符串序列。
 *
 * @see CaseFormat
 */
fun String.splitToSequenceBy(caseFormat: CaseFormat): Sequence<String> {
	return caseFormat.splitToSequence(this)
}

/**
 * 根据指定的字母格式，将当前字符串数组中的元素加入到字符串。
 *
 * @see CaseFormat
 */
fun Array<String>.joinToStringBy(caseFormat: CaseFormat): String {
	return caseFormat.joinToString(this)
}

/**
 * 根据指定的字母格式，将当前字符串集合中的元素加入到字符串。
 *
 * @see CaseFormat
 */
fun Iterable<String>.joinToStringBy(caseFormat: CaseFormat): String {
	return caseFormat.joinToString(this)
}

/**
 * 根据指定的字母格式，将当前字符串序列中的元素加入到字符串。
 *
 * @see CaseFormat
 */
fun Sequence<String>.joinToStringBy(caseFormat: CaseFormat): String {
	return caseFormat.joinToString(this)
}

/**
 * 根据指定的字母格式，切换当前字符串的格式。
 *
 * @see CaseFormat
 */
fun String.switchCaseBy(sourceCaseFormat: CaseFormat, targetCaseFormat: CaseFormat): String {
	return splitBy(sourceCaseFormat).joinToStringBy(targetCaseFormat)
}

/**
 * 根据指定的字母格式，切换当前字符串的格式。如果不指定字母格式，则尝试推断或者抛出异常。
 *
 * @see CaseFormat
 */
fun String.switchCaseBy(targetCaseFormat: CaseFormat): String {
	val sourceLetterCase = inferCase() ?: throw IllegalArgumentException("Cannot infer letter case for string '$this'.")
	return splitBy(sourceLetterCase).joinToStringBy(targetCaseFormat)
}
