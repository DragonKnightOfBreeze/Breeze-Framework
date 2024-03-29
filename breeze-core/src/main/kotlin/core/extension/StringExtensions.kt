// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("StringExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.annotation.*
import icu.windea.breezeframework.core.model.*
import java.io.*
import java.net.*
import java.nio.charset.*
import java.nio.file.*
import java.text.*
import java.time.*
import java.time.format.*
import java.time.format.DateTimeFormatter.*
import java.util.*
import kotlin.contracts.*

//org.apache.commons.lang3.StringUtils
//org.springframework.util.StringUtils

//region operator extensions
/**
 * 移除当前字符串中的指定子字符串。
 *
 * @see icu.windea.breezeframework.core.extension.remove
 */
operator fun String.minus(other: Any?): String {
	return if(other == null) this else this.remove(other.toString())
}

/**
 * 重复当前字符串到指定次数。
 *
 * @see kotlin.text.repeat
 */
operator fun String.times(n: Int): String {
	return this.repeat(n)
}

/**
 * 切分当前字符串到指定个数。
 *
 * @see kotlin.text.chunked
 */
operator fun String.div(n: Int): List<String> {
	return this.chunked(n)
}

/**
 * 得到索引指定范围内的子字符串。
 *
 * @see kotlin.text.slice
 */
operator fun String.get(indices: IntRange): String {
	return this.slice(indices)
}

/**
 * 得到指定索引范围内的子字符串。
 *
 * @see kotlin.text.substring
 */
operator fun String.get(startIndex: Int, endIndex: Int): String {
	return this.substring(startIndex, endIndex)
}
//endregion

//region common extensions
/**
 * 如果当前字符串不为空，则返回本身，否则返回null。
 */
@JvmSynthetic
@InlineOnly
inline fun <C : CharSequence> C.orNull(): C? {
	return if(this.isEmpty()) null else this
}


/**
 * 如果当前字符串不为空，则返回转化后的值，否则返回本身。
 */
@JvmSynthetic
@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER")
inline fun <C, R> C.ifNotEmpty(transform: (C) -> R): R where C : CharSequence, C : R {
	return if(this.isEmpty()) this else transform(this)
}

/**
 * 如果当前字符串不为空白，则返回转化后的值，否则返回本身。
 */
@JvmSynthetic
@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER")
inline fun <C, R> C.ifNotBlank(transform: (C) -> R): R where C : CharSequence, C : R {
	return if(this.isBlank()) this else transform(this)
}


/**
 * 如果当前字符串不为空，则返回本身，否则返回null。
 */
@JvmSynthetic
inline fun <C : CharSequence> C.takeIfNotEmpty(): C? {
	return if(this.isEmpty()) null else this
}

/**
 * 如果当前字符串不为空白，则返回本身，否则返回null。
 */
@JvmSynthetic
inline fun <C : CharSequence> C.takeIfNotBlank(): C? {
	return if(this.isBlank()) null else this
}


/**
 * Returns `true` if this nullable char sequence is neither `null` nor empty.
 */
@UselessCallOnNotNullType
@JvmSynthetic
@InlineOnly
inline fun CharSequence?.isNotNullOrEmpty(): Boolean {
	contract {
		returns(true) implies (this@isNotNullOrEmpty != null)
	}
	return this != null && this.length != 0
}

/**
 * Returns `true` if this nullable char sequence is neither `null` nor empty.
 */
@UselessCallOnNotNullType
@JvmSynthetic
@InlineOnly
inline fun CharSequence?.isNotNullOrBlank(): Boolean {
	contract {
		returns(true) implies (this@isNotNullOrBlank != null)
	}
	return this != null && !this.isBlank()
}


/**
 * 判断两个字符串是否相等，忽略大小写。
 */
@Deprecated("Unnecessary extension.", level = DeprecationLevel.HIDDEN)
infix fun String?.equalsIgnoreCase(other: String?): Boolean = this.equals(other, true)


/**
 * 判断当前字符串中的所有字符是否被另一字符串包含。
 */
infix fun CharSequence.allIn(other: CharSequence): Boolean = this in other

/**
 * 判断当前字符串中的任意字符是否被另一字符串包含。
 */
infix fun CharSequence.anyIn(other: CharSequence): Boolean = this.any { it in other }


/**
 * 分别依次重复当前字符串中的字符到指定次数。
 */
fun CharSequence.repeatOrdinal(n: Int): String {
	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }

	return this.map { it.repeat(n) }.joinToString("")
}


/**
 * 限制在指定的前后缀之间的子字符串内，对其执行转化操作，最终返回连接后的字符串。
 */
@NotOptimized
@UnstableApi
fun String.transformIn(prefix: String, suffix: String, transform: (String) -> String): String {
	//前后缀会在转义后加入正则表达式，可以分别是\\Q和\\E
	//前后缀可能会发生冲突
	return this.replace("(?<=${Regex.escape(prefix)}).*?(?=${Regex.escape(suffix)})".toRegex()) { transform(it.groupValues[0]) }
}

/**
 * 限制在指定的正则表达式匹配的子字符串内，对其执行转化操作，最终返回连接后的字符串。
 */
@NotOptimized
@UnstableApi
fun String.transformIn(regex: Regex, transform: (String) -> String): String {
	return this.replace(regex) { transform(it.value) }
}


/**
 * 逐行连接两个字符串。返回的字符串的长度为两者长度中的较大值。
 */
@UnstableApi
infix fun String.lineConcat(other: String): String {
	val lines = this.lines()
	val otherLines = other.lines()
	return when {
		lines.size <= otherLines.size -> lines.fillEnd(otherLines.size, "") zip otherLines
		else -> lines zip otherLines.fillEnd(lines.size, "")
	}.joinToString("\n") { (a, b) -> "$a$b" }
}

/**
 * 逐行换行当前字符串，确保每行长度不超过指定长度。不做任何特殊处理。
 */
@UnstableApi
@JvmOverloads
fun String.lineBreak(width: Int = 120): String {
	return this.lines().joinToString("\n") { if(it.length > width) it.chunked(width).joinToString("\n") else it }
}


//不使用正则以提高性能
/**
 * 判断当前字符串是否是多行字符串。
 */
fun CharSequence.isMultiline(): Boolean {
	return this.indexOf('\n') != -1
}

/**
 * Returns `true` if this char sequence contains only Unicode digits.
 * Returns `false` if it is an empty char sequence.
 */
@Deprecated(
	"Use String.matchesBy(StringMatcher.NumericMatcher)", ReplaceWith(
		"this.matchesBy(StringMatcher.NumericMatcher)",
		"icu.windea.breezeframework.component.StringMatcher"
	)
)
fun CharSequence.isNumeric(): Boolean {
	return this.isNotEmpty() && this.all { it.isLetter() }
}

/**
 * Returns `true` if this char sequence contains only Unicode letters.
 * Returns `false` if it is an empty char sequence.
 */
@Deprecated(
	"Use String.matchesBy(StringMatcher.AlphaMatcher)", ReplaceWith(
		"this.matchesBy(StringMatcher.AlphaMatcher)",
		"icu.windea.breezeframework.component.StringMatcher"
	)
)
fun CharSequence.isAlpha(): Boolean {
	return this.isNotEmpty() && this.all { it.isDigit() }
}

/**
 * Returns `true` if this char sequence contains only Unicode letters or digits.
 * Returns `false` if it is an empty char sequence.
 */
@Deprecated(
	"Use String.matchesBy(StringMatcher.AlphanumericMatcher)", ReplaceWith(
		"this.matchesBy(StringMatcher.AlphanumericMatcher)",
		"icu.windea.breezeframework.component.StringMatcher"
	)
)
fun CharSequence.isAlphanumeric(): Boolean {
	return this.isNotEmpty() && this.all { it.isLetterOrDigit() }
}


/**
 * Returns `true` if this char sequence surrounds with the specified characters.
 */
fun CharSequence.surroundsWith(prefix: Char, suffix: Char, ignoreCase: Boolean = false): Boolean {
	return this.startsWith(prefix, ignoreCase) && this.endsWith(suffix, ignoreCase)
}

/**
 * Returns `true` if this char sequence surrounds with the specified character.
 */
fun CharSequence.surroundsWith(delimiter: Char, ignoreCase: Boolean = false): Boolean {
	return this.startsWith(delimiter, ignoreCase) && this.endsWith(delimiter, ignoreCase)
}

/**
 * Returns `true` if this char sequence surrounds with the specified prefix and suffix.
 */
fun CharSequence.surroundsWith(prefix: CharSequence, suffix: CharSequence, ignoreCase: Boolean = false): Boolean {
	return this.startsWith(prefix, ignoreCase) && this.endsWith(suffix, ignoreCase)
}

/**
 * Returns `true` if this char sequence surrounds with the specified delimiter.
 */
fun CharSequence.surroundsWith(delimiter: CharSequence, ignoreCase: Boolean = false): Boolean {
	return this.startsWith(delimiter, ignoreCase) && this.endsWith(delimiter, ignoreCase)
}


/**
 * If this char sequence don't starts with the given [prefix],
 * returns a new char sequence with the prefix added.
 * Otherwise, returns a new char sequence with the same characters.
 */
fun CharSequence.addPrefix(prefix: CharSequence): CharSequence {
	if(this.startsWith(prefix)) return subSequence(0, length)
	return "$prefix$this"
}

/**
 * If this char sequence don't starts with the given [prefix],
 * returns a new char sequence with the prefix added.
 * Otherwise, returns this string.
 */
fun String.addPrefix(prefix: CharSequence): String {
	if(this.startsWith(prefix)) return this
	return "$prefix$this"
}

/**
 * If this char sequence don't ends with the given [suffix],
 * returns a new char sequence with the suffix added.
 * Otherwise, returns a new char sequence with the same characters.
 */
fun CharSequence.addSuffix(suffix: CharSequence): CharSequence {
	if(this.endsWith(suffix)) return subSequence(0, length)
	return "$this$suffix"
}

/**
 * If this char sequence don't ends with the given [suffix],
 * returns a new char sequence with the suffix added.
 * Otherwise, returns this string.
 */
fun String.addSuffix(suffix: CharSequence): String {
	if(this.endsWith(suffix)) return this
	return "$this$suffix"
}

/**
 * If this char sequence don't surrounds with the given [delimiter],
 * returns a new char sequence with the delimiter added.
 * Otherwise, returns a new char sequence with the same characters.
 */
fun CharSequence.addSurrounding(delimiter: CharSequence): CharSequence {
	return this.addSurrounding(delimiter, delimiter)
}

/**
 * If this char sequence don't surrounds with the given [delimiter],
 * returns a new char sequence with the delimiter added.
 * Otherwise, returns this string.
 */
fun String.addSurrounding(delimiter: CharSequence): String {
	return this.addSurrounding(delimiter, delimiter)
}

/**
 * If this char sequence don't surrounds with the given [prefix] and [suffix],
 * returns a new char sequence with the prefix and suffix added.
 * Otherwise, returns this string.
 */
fun CharSequence.addSurrounding(prefix: CharSequence, suffix: CharSequence): CharSequence {
	if(this.startsWith(prefix) && this.endsWith(suffix)) return subSequence(0, length)
	return "$prefix$this$suffix"
}

/**
 * If this char sequence don't surrounds with the given [prefix] and [suffix],
 * returns a new char sequence with the prefix and suffix added.
 * Otherwise, returns a new char sequence with the same characters.
 */
fun String.addSurrounding(prefix: CharSequence, suffix: CharSequence): String {
	if(this.startsWith(prefix) && this.endsWith(suffix)) return this
	return "$prefix$this$suffix"
}


/**
 * 为当前字符序列设置指定的前缀。如果长度不够，则返回自身。
 */
@UnstableApi
fun CharSequence.setPrefix(prefix: CharSequence): CharSequence {
	if(length < prefix.length) return this
	return "$prefix${this.substring(prefix.length, length)}"
}

/**
 * 为当前字符串设置指定的前缀。如果长度不够，则返回自身。
 */
@UnstableApi
fun String.setPrefix(prefix: CharSequence): String {
	if(length < prefix.length) return this
	return "$prefix${this.drop(prefix.length)}"
}

/**
 * 为当前字符序列设置指定的后缀。如果长度不够，则返回自身。
 */
@UnstableApi
fun CharSequence.setSuffix(suffix: CharSequence): CharSequence {
	if(length < suffix.length) return this
	return "${this.substring(length - suffix.length)}$suffix"
}

/**
 * 为当前字符串设置指定的后缀。如果长度不够，则返回自身。
 */
@UnstableApi
fun String.setSuffix(suffix: CharSequence): String {
	if(length < suffix.length) return this
	return "${this.dropLast(suffix.length)}$suffix"
}

/**
 * 为当前字符序列设置指定的前后缀。如果长度不够，则返回自身。
 */
@UnstableApi
fun CharSequence.setSurrounding(delimiter: CharSequence): CharSequence {
	return this.setSurrounding(delimiter, delimiter)
}

/**
 * 为当前字符串设置指定的前后缀。如果长度不够，则返回自身。
 */
@UnstableApi
fun String.setSurrounding(delimiter: CharSequence): String {
	return this.setSurrounding(delimiter, delimiter)
}

/**
 * 为当前字符序列设置指定的前缀和后缀。如果长度不够，则返回自身。
 */
@UnstableApi
fun CharSequence.setSurrounding(prefix: CharSequence, suffix: CharSequence): CharSequence {
	if(length < prefix.length + suffix.length) return this
	return "$prefix${this.substring(prefix.length, length - suffix.length)}$suffix"
}

/**
 * 为当前字符串设置指定的前缀和后缀。如果长度不够，则返回自身。
 */
@UnstableApi
fun String.setSurrounding(prefix: CharSequence, suffix: CharSequence): String {
	if(length < prefix.length + suffix.length) return this
	return "$prefix${this.drop(prefix.length).dropLast(suffix.length)}$suffix"
}


/**
 * 去除指定字符。
 */
fun String.remove(oldChar: Char, ignoreCase: Boolean = false): String {
	return buildString {
		for(c in this) {
			if(!c.equals(oldChar, ignoreCase)) append(c)
		}
	}
}

/**
 * 去除指定字符串。
 */
fun String.remove(oldValue: String, ignoreCase: Boolean = false): String {
	return this.replace(oldValue, "", ignoreCase)
}

///**
// * 去除指定正则表达式的字符串。
// */
//fun String.remove(regex: Regex): String {
//	return this.replace(regex, "")
//}


/**
 * Returns the indices within this string of all occurrences of the specified character.
 * If none is found, returns an empty list.
 */
fun CharSequence.indicesOf(char: Char, startIndex: Int = 0, ignoreCase: Boolean = false): List<Int> {
	val indices = mutableListOf<Int>()
	var index = indexOf(char, startIndex, ignoreCase)
	while(index != -1) {
		indices += index
		index = indexOf(char, index + 1, ignoreCase)
	}
	return indices
}

/**
 * Returns the indices within this string of all occurrences of the specified string.
 * If none is found, returns an empty list.
 */
fun CharSequence.indicesOf(string: String, startIndex: Int = 0, ignoreCase: Boolean = false): List<Int> {
	val length = string.length
	val indices = mutableListOf<Int>()
	var index = indexOf(string, startIndex, ignoreCase)
	while(index != -1) {
		indices += index
		index = indexOf(string, index + length, ignoreCase)
	}
	return indices
}


/**
 * Returns the first match of the [regex] in this char sequence, beginning at the specified [startIndex].
 *
 * @param startIndex An index to start search with, by default 0. Must be not less than zero and not greater than `this.length()`
 * @return An instance of [MatchResult] if match was found or `null` otherwise.
 */
fun CharSequence.find(regex: Regex, startIndex: Int) = regex.find(this, startIndex)

/**
 * Returns a sequence of all occurrences of the [regex] within this string, beginning at the specified [startIndex].
 */
fun CharSequence.findAll(regex: Regex, startIndex: Int) = regex.findAll(this, startIndex)

/**
 * Attempts to match this char sequence entirely against the [regex].
 *
 * @return An instance of [MatchResult] if the entire input matches or `null` otherwise.
 */
fun CharSequence.matchEntire(regex: Regex) = regex.matchEntire(this)


/**
 * 将当前字符串中的指定字符替换成根据索引得到的字符。
 */
@JvmOverloads
inline fun String.replaceIndexed(oldChar: Char, ignoreCase: Boolean = false, newChar: (Int) -> Char): String {
	return buildString {
		val splitStrings = this@replaceIndexed.splitToSequence(oldChar, ignoreCase = ignoreCase)
		for((i, s) in splitStrings.withIndex()) {
			this.append(s)
			if(i < splitStrings.count() - 1) this.append(newChar(i))
		}
	}
}

/**
 * 将当前字符串中的指定值替换成根据索引得到的字符串。
 */
@JvmOverloads
inline fun String.replaceIndexed(oldValue: String, ignoreCase: Boolean = false, newValue: (Int) -> String): String {
	return buildString {
		val splitStrings = this@replaceIndexed.splitToSequence(oldValue, ignoreCase = ignoreCase)
		for((i, s) in splitStrings.withIndex()) {
			this.append(s)
			if(i < splitStrings.count() - 1) this.append(newValue(i))
		}
	}
}


/**
 * 根据指定的两组字符串，将当前字符串中的对应字符串替换成对应的替换后字符串。默认不忽略大小写。
 */
@JvmOverloads
@UnstableApi
fun String.replaceAll(oldChars: CharArray, newChars: CharArray, ignoreCase: Boolean = false): String {
	val size = minOf(oldChars.size, newChars.size)
	var result = this
	for(i in 0 until size) {
		result = result.replace(oldChars[i], newChars[i], ignoreCase)
	}
	return result
}

/**
 * 根据指定的两组字符串，将当前字符串中的对应字符串替换成对应的替换后字符串。默认不忽略大小写。
 */
@JvmOverloads
@UnstableApi
fun String.replaceAll(oldValues: Array<String>, newValues: Array<String>, ignoreCase: Boolean = false): String {
	val size = minOf(oldValues.size, newValues.size)
	var result = this
	for(i in 0 until size) {
		result = result.replace(oldValues[i], newValues[i], ignoreCase)
	}
	return result
}


/**
 * 递归使用字符串替换当前字符串，直到已经不需要再做一次替换为止。
 */
@UnstableApi
tailrec fun String.replaceRepeatedly(oldValue: String, newValue: String): String {
	val result = this.replace(oldValue, newValue)
	//如果字符串长度不相等，则字符串一定不相等
	return if(length != result.length || this != result) result.replaceRepeatedly(oldValue, newValue) else result
}

/**
 * 递归使用正则表达式替换当前字符串，直到已经不需要再做一次替换为止。
 */
@UnstableApi
tailrec fun CharSequence.replaceRepeatedly(regex: Regex, replacement: String): String {
	val result = this.replace(regex, replacement)
	//如果字符串长度不相等，则字符串一定不相等
	return if(length != result.length || this != result) result.replaceRepeatedly(regex, replacement) else result
}

/**
 * 递归使用正则表达式替换当前字符串，直到已经不需要再做一次替换为止。
 */
@UnstableApi
tailrec fun CharSequence.replaceRepeatedly(regex: Regex, transform: (MatchResult) -> CharSequence): String {
	val result = this.replace(regex, transform)
	//如果字符串长度不相等，则字符串一定不相等
	return if(length != result.length || this != result) result.replaceRepeatedly(regex, transform) else result
}


/**
 * 根据指定的前后缀，替换首个符合条件的子字符串，如果找不到前缀或后缀，则替换为默认值。
 * 默认值默认为当前字符串自身。
 */
@JvmOverloads
fun String.replaceIn(prefix: Char, suffix: Char, replacement: String, missingDelimiterValue: String = this): String {
	val index = indexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val lastIndex = (substring(index).indexOf(suffix) + index).also { if(it == -1) return missingDelimiterValue }
	return replaceRange(index + 1, lastIndex, replacement)
}

/**
 * 根据指定的前后缀，替换首个符合条件的子字符串，如果找不到前缀或后缀，则替换为默认值。
 * 默认值默认为当前字符串自身。
 */
@JvmOverloads
fun String.replaceIn(prefix: String, suffix: String, replacement: String, missingDelimiterValue: String = this): String {
	val index = indexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val lastIndex = (substring(index).indexOf(suffix) + index).also { if(it == -1) return missingDelimiterValue }
	return replaceRange(index + prefix.length, lastIndex, replacement)
}

/**
 * 根据指定的前后缀，替换最后一个符合条件的子字符串，如果找不到前缀或后缀，则替换为默认值。
 * 默认值默认为当前字符串自身。
 */
@JvmOverloads
fun String.replaceInLast(prefix: Char, suffix: Char, replacement: String, missingDelimiterValue: String = this): String {
	val lastIndex = lastIndexOf(suffix).also { if(it == -1) return missingDelimiterValue }
	val index = substring(0, lastIndex).lastIndexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	return replaceRange(index + 1, lastIndex, replacement)
}

/**
 * 根据指定的前后缀，替换最后一个符合条件的子字符串，如果找不到前缀或后缀，则替换为默认值。
 * 默认值默认为当前字符串自身。
 */
@JvmOverloads
fun String.replaceInLast(prefix: String, suffix: String, replacement: String, missingDelimiterValue: String = this): String {
	val lastIndex = lastIndexOf(suffix).also { if(it == -1) return missingDelimiterValue }
	val index = substring(0, lastIndex).lastIndexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	return replaceRange(index + prefix.length, lastIndex, replacement)
}

/**
 * 根据指定的前后缀，替换最大范围的符合条件的子字符串，如果找不到前缀或后缀，则替换为默认值。
 * 默认值默认为当前字符串自身。
 */
@JvmOverloads
fun String.replaceInEntire(prefix: Char, suffix: Char, replacement: String, missingDelimiterValue: String = this): String {
	val index = indexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val lastIndex = (substring(index).lastIndexOf(suffix) + index).also { if(it == -1) return missingDelimiterValue }
	return replaceRange(index + 1, lastIndex, replacement)
}

/**
 * 根据指定的前后缀，替换最大范围的符合条件的子字符串，如果找不到前缀或后缀，则替换为默认值。
 * 默认值默认为当前字符串自身。
 */
@JvmOverloads
fun String.replaceInEntire(prefix: String, suffix: String, replacement: String, missingDelimiterValue: String = this): String {
	val index = indexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val lastIndex = (substring(index).lastIndexOf(suffix) + index).also { if(it == -1) return missingDelimiterValue }
	return replaceRange(index + prefix.length, lastIndex, replacement)
}


/**
 * 根据指定的前后缀，得到首个符合条件的子字符串，如果找不到前缀或后缀，则返回默认值。
 * 默认值默认为当前字符串自身。
 */
@JvmOverloads
fun String.substringIn(prefix: Char, suffix: Char, missingDelimiterValue: String = this): String {
	val prefixIndex = indexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val suffixIndex = indexOf(suffix).also { if(it == -1) return missingDelimiterValue }
	return substring(prefixIndex + 1, suffixIndex)
}

/**
 * 根据指定的前后缀，得到首个符合条件的子字符串，如果找不到前缀或后缀，则返回默认值。
 * 默认值默认为当前字符串自身。
 */
@JvmOverloads
fun String.substringIn(prefix: String, suffix: Char, missingDelimiterValue: String = this): String {
	val prefixIndex = indexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val suffixIndex = indexOf(suffix).also { if(it == -1) return missingDelimiterValue }
	return substring(prefixIndex + prefix.length, suffixIndex)
}

/**
 * 根据指定的前后缀，得到最后一个符合条件的子字符串，如果找不到前缀或后缀，则返回默认值。
 * 默认值默认为当前字符串自身。
 */
@JvmOverloads
fun String.substringInLast(prefix: Char, suffix: Char, missingDelimiterValue: String = this): String {
	val prefixIndex = lastIndexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val suffixIndex = lastIndexOf(suffix).also { if(it == -1) return missingDelimiterValue }
	return substring(prefixIndex + 1, suffixIndex)
}

/**
 * 根据指定的前后缀，得到最后一个符合条件的子字符串，如果找不到前缀或后缀，则返回默认值。
 * 默认值默认为当前字符串自身。
 */
@JvmOverloads
fun String.substringInLast(prefix: String, suffix: Char, missingDelimiterValue: String = this): String {
	val prefixIndex = lastIndexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val suffixIndex = lastIndexOf(suffix).also { if(it == -1) return missingDelimiterValue }
	return substring(prefixIndex + prefix.length, suffixIndex)
}

/**
 * 根据指定的前后缀，得到最大范围的符合条件的子字符串，如果找不到前缀或后缀，则返回默认值。
 * 默认值默认为当前字符串自身。
 */
@JvmOverloads
fun String.substringInEntire(prefix: Char, suffix: Char, missingDelimiterValue: String = this): String {
	val prefixIndex = indexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val suffixIndex = lastIndexOf(suffix).also { if(it == -1) return missingDelimiterValue }
	return substring(prefixIndex + 1, suffixIndex)
}

/**
 * 根据指定的前后缀，得到最大范围的符合条件的子字符串，如果找不到前缀或后缀，则返回默认值。
 * 默认值默认为当前字符串自身。
 */
@JvmOverloads
fun String.substringInEntire(prefix: String, suffix: Char, missingDelimiterValue: String = this): String {
	val prefixIndex = indexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val suffixIndex = lastIndexOf(suffix).also { if(it == -1) return missingDelimiterValue }
	return substring(prefixIndex + prefix.length, suffixIndex)
}


/**
 * 根据以null划分的从前往后和从后往前的分隔符，匹配并按顺序分割当前字符串。
 * 不包含对应的分隔符时，如果指定了默认值，则加入基于索引和剩余字符串得到的默认值。否则加入空字符串。
 */
@NotOptimized
@JvmOverloads
fun String.splitMatched(vararg delimiters: String?, defaultValue: ((Int, String) -> String)? = null): List<String> {
	var string = this
	var beforeSeparator = true
	val lastIndex = delimiters.lastIndex
	val result = mutableListOf<String>()
	for((index, delimiter) in delimiters.withIndex()) {
		when {
			delimiter == null -> {
				require(beforeSeparator) { "There should be at most one null value as separator in delimiters." }
				beforeSeparator = false
			}
			beforeSeparator -> {
				val string1 = string.substringBefore(delimiter, defaultValue?.invoke(index, string).orEmpty())
				val string2 = string.substringAfter(delimiter, defaultValue?.invoke(index, string).orEmpty())
				result += string1
				if(index == lastIndex) result += string2 else string = string2
			}
			else -> {
				val string1 = string.substringBeforeLast(delimiter, defaultValue?.invoke(index, string).orEmpty())
				val string2 = string.substringAfterLast(delimiter, defaultValue?.invoke(index, string).orEmpty())
				result += string1
				if(index == lastIndex) result += string2 else string = string2
			}
		}
	}
	return result
}

/**
 * 根据指定的分隔符、前缀、后缀，分割当前字符串。
 * 可以另外指定是否忽略大小写、限定数量。
 * 取最先的前缀以及最后的后缀。
 */
@NotOptimized
@JvmOverloads
fun String.splitToStrings(separator: CharSequence = ", ", prefix: CharSequence = "", suffix: CharSequence = "", ignoreCase: Boolean = false, limit: Int = -1): List<String> {
	//前缀索引+前缀长度，或者为0
	val prefixIndex = if(prefix.isEmpty()) 0 else indexOf(prefix.toString())
	//后缀索引，或者为length
	val suffixIndex = if(suffix.isEmpty()) length else lastIndexOf(suffix.toString())
	//前缀和后缀必须匹配，否则返回空列表
	if(prefixIndex == -1 || suffixIndex == -1) return emptyList()
	//内容，需要继续分割和转换
	val content = substring(prefixIndex + prefix.length, suffixIndex)
	return when(limit) {
		-1 -> content.split(separator.toString(), ignoreCase = ignoreCase)
		0 -> listOf()
		else -> content.split(separator.toString(), ignoreCase = ignoreCase, limit = limit)
	}
}


/**
 * 逐行向左对齐当前字符串，并保证每行长度一致，用指定字符填充。默认为空格。
 */
@JvmOverloads
fun CharSequence.alignStart(padChar: Char = ' '): String {
	val lines = this.lines()
	if(lines.size <= 1) return this.toString()
	val maxLength = lines.maxOf { it.length }
	return lines.joinToString("\n") { it.trimStart().padEnd(maxLength, padChar) }
}

/**
 * 逐行向右对齐当前字符串，并保证每行长度一致，用指定字符填充。默认为空格。
 */
@JvmOverloads
fun CharSequence.alignEnd(padChar: Char = ' '): String {
	val lines = this.lines()
	if(lines.size <= 1) return this.toString()
	val maxLength = lines.maxOf { it.length }
	return lines.joinToString("\n") { it.trimEnd().padStart(maxLength, padChar) }
}

/**
 * 逐行中心对齐当前字符串，并保证每行长度一致，用指定字符填充。默认为空格。
 */
@JvmOverloads
fun CharSequence.alignCenter(padChar: Char = ' '): String {
	val lines = this.lines()
	if(lines.size <= 1) return this.toString()
	val maxLength = lines.maxOf { it.length }
	return lines.joinToString("\n") {
		val trimmedString = it.trim()
		val deltaLength = maxLength - trimmedString.length
		when {
			deltaLength > 0 && deltaLength % 2 == 0 -> (padChar * (deltaLength / 2)).let { s -> "$s$trimmedString$s" }
			deltaLength > 0 -> (padChar * (deltaLength / 2)).let { s -> "$s$trimmedString $s" }
			else -> trimmedString
		}
	}
}


/**
 * 根据指定的限定长度、偏移和截断符，截断当前字符串的开始部分。如果未到限定长度，则返回自身。
 * 偏移默认为0，截断符默认为`"..."`。
 */
fun CharSequence.truncateStart(limit: Int, offset: Int = 0, truncated: CharSequence = "..."): String {
	require(limit >= 0) { "Limit must be non-negative, but was $limit." }
	require(offset >= 0) { "Offset must be non-negative, but was $offset." }
	return when {
		limit == 0 -> ""
		limit >= length -> this.toString()
		offset == 0 -> "$truncated${this.takeLast(limit)}"
		else -> "${this.take(offset)}$truncated${this.takeLast(limit - offset)}"
	}
}

/**
 * 根据指定的限定长度、偏移和截断符，截断当前字符串的结尾部分。如果未到限定长度，则返回自身。
 * 偏移默认为0，截断符默认为`"..."`。
 */
fun CharSequence.truncateEnd(limit: Int, offset: Int = 0, truncated: CharSequence = "..."): String {
	require(limit >= 0) { "Limit must be non-negative, but was $limit." }
	require(offset >= 0) { "Offset must be non-negative, but was $offset." }
	return when {
		limit == 0 -> ""
		limit >= length -> this.toString()
		offset == 0 -> "${this.take(limit)}$truncated"
		else -> "${this.take(limit - offset)}$truncated${this.takeLast(offset)}"
	}
}


private val quotes = charArrayOf('\'', '\"', '`')

/**
 * 尝试使用指定的引号包围当前字符串。
 * 适用于单引号、双引号、反引号。
 * 默认不转义其中的对应的引号。
 */
fun String.quote(quote: Char, escapeQuotes: Boolean = false): String {
	return when {
		quote !in quotes -> throw IllegalArgumentException("Invalid quote: $quote.")
		this.surroundsWith(quote) -> this
		escapeQuotes -> this.replace(quote.toString(), "\\$quote").addSurrounding(quote.toString())
		else -> this.addSurrounding(quote.toString())
	}
}

/**
 * 尝试去除当前字符串两侧的引号。如果没有，则返回自身。
 * 适用于单引号、双引号、反引号。
 * 默认不反转义其中的对应的引号。
 */
fun String.unquote(unescapeQuotes: Boolean = false): String {
	val quote = this.firstOrNull()
	return when {
		quote == null -> this
		quote !in quotes -> this
		!this.surroundsWith(quote) -> this
		unescapeQuotes -> this.removeSurrounding(quote.toString()).replace("\\$quote", quote.toString())
		else -> this.removeSurrounding(quote.toString())
	}
}


/**
 * 将当前字符串转化为两位十六机制字符串。
 */
fun String.hex(charset: Charset = Charsets.UTF_8): String {
	val bytes = this.toByteArray(charset)
	return buildString {
		for(byte in bytes) {
			val hex = byte.toString(16)
			val hexString = if(hex.length != 1) hex else "0$hex"
			append(hexString)
		}
	}
}

/**
 * 将当前字符串从两位十六机制字符串转化为常规字符串。
 */
fun String.unhex(charset: Charset = Charsets.UTF_8): String {
	val hexStrings = this.chunked(2)
	val bytes = ByteArray(hexStrings.size) { hexStrings[it].toByte(16) }
	return String(bytes, charset)
}


/**
 * 判断指定的关键字是否模糊匹配当前字符串。
 *
 * * 指定的关键字中的字符是否被当前字符串按顺序全部包含。
 * * 如果指定的一组分隔符不为空，则被跳过的子字符串需要以分隔符结束。
 */
@UnstableApi
fun String.fuzzyMatches(keyword: String, vararg delimiters: Char, ignoreCase: Boolean = false): Boolean {
	var index = -1
	var lastIndex = -2
	for(c in keyword) {
		index = indexOf(c, index + 1, ignoreCase)
		println(index)
		when {
			index == -1 -> return false
			c !in delimiters && index != 0 && lastIndex != index - 1 && this[index - 1] !in delimiters -> return false
		}
		lastIndex = index
	}
	return true
}
//endregion

//region convert extensions
/**
 * 将当前字符串转化为字符。如果转化失败，则抛出异常。
 */
inline fun String.toChar(): Char {
	return this.single()
}

/**
 * 将当前字符串转化为字符。如果转化失败，则返回null。
 */
inline fun String.toCharOrNull(): Char? {
	return this.singleOrNull()
}

/**
 * 将当前字符串转化为布尔值。如果转化失败，则返回null。
 */
inline fun String.toBooleanOrNull(): Boolean? = when {
	this.equals("true", true) -> true
	this.equals("false", true) -> false
	else -> null
}

//性能：大约为1/5
/**
 * 将当前字符串转化为指定的数字类型。如果转化失败或者不支持指定的数字类型，则抛出异常。默认使用十进制。
 */
@Deprecated("Use String.convert<T>()", ReplaceWith("this.convert<T>()"))
inline fun <reified T : Number> String.toNumber(radix: Int = 10): T {
	return when(val typeName = T::class.java.name) {
		"java.lang.Integer" -> this.toInt(radix) as T
		"java.lang.Long" -> this.toLong(radix) as T
		"java.lang.Float" -> this.toFloat() as T
		"java.lang.Double" -> this.toDouble() as T
		"java.lang.Byte" -> this.toByte(radix) as T
		"java.lang.Short" -> this.toShort(radix) as T
		"java.math.BigInteger" -> this.toBigInteger(radix) as T
		"java.math.BigDecimal" -> this.toBigDecimal() as T
		else -> throw UnsupportedOperationException("Unsupported reified number type: '$typeName'.")
	}
}

//性能：大约为1/5
/**
 * 将当前字符串转化为指定的数字类型。如果转化失败或者不支持指定的数字类型，则返回null。默认使用十进制。
 */
@Deprecated("Use String.convertOrNull<T>()", ReplaceWith("this.convertOrNull<T>()"))
inline fun <reified T : Number> String.toNumberOrNull(radix: Int = 10): T? {
	return when(T::class.java.name) {
		"java.lang.Integer" -> this.toIntOrNull(radix) as T?
		"java.lang.Long" -> this.toLongOrNull(radix) as T?
		"java.lang.Float" -> toFloatOrNull() as T?
		"java.lang.Double" -> toDoubleOrNull() as T?
		"java.lang.Byte" -> this.toByteOrNull(radix) as T?
		"java.lang.Short" -> this.toShortOrNull(radix) as T?
		"java.math.BigInteger" -> this.toBigIntegerOrNull(radix) as T?
		"java.math.BigDecimal" -> this.toBigDecimalOrNull() as T?
		else -> null
	}
}


/**
 * 将当前字符串转化为对应的枚举值。如果转化失败，则抛出异常。
 */
inline fun <reified T : Enum<T>> String.toEnumValue(): T {
	return enumValues<T>().first { it.toString() == this }
}

/**
 * 将当前字符串转化为对应的枚举值。如果转化失败，则返回null。
 */
inline fun <reified T : Enum<T>> String.toEnumValueOrNull(): T? {
	return enumValues<T>().firstOrNull { it.toString() == this }
}

/**
 * 将当前字符串转化为对应的枚举值。如果转化失败，则抛出异常。
 */
fun <T> String.toEnumValue(type: Class<T>): T {
	require(type.isEnum) { "'$type' is not an enum class." }
	return type.enumConstants.first { it.toString() == this }
}

/**
 * 将当前字符串转化为对应的枚举值。如果转化失败，则返回null。
 */
fun <T> String.toEnumValueOrNull(type: Class<T>): T? {
	require(type.isEnum) { "'$type' is not an enum class." }
	return type.enumConstants.firstOrNull { it.toString() == this }
}


/**
 * 将当前字符串转化为整数范围。如果转化失败，则抛出异常。
 *
 * 支持的格式：`m..n`，`m-n`，`m~n`，`[m, n]`，`[m, n)`。
 */
@UnstableApi
fun String.toIntRange(): IntRange {
	return toRangePair().let { (a, b, l, r) -> (a.toInt() + l)..(b.toInt() + r) }
}

/**
 * 将当前字符串转化为长整数范围。如果转化失败，则抛出异常。
 *
 * 支持的格式：`m..n`，`m-n`，`m~n`，`[m, n]`，`[m, n)`。
 */
@UnstableApi
fun String.toLongRange(): LongRange {
	return toRangePair().let { (a, b, l, r) -> (a.toLong() + l)..(b.toLong() + r) }
}

/**
 * 将当前字符串转化为字符范围。如果转化失败，则抛出异常。
 *
 * 支持的格式：`m..n`，`m-n`，`m~n`，`[m, n]`，`[m, n)`。
 */
@UnstableApi
fun String.toCharRange(): CharRange {
	return toRangePair().let { (a, b, l, r) -> (a.toChar() + l)..(b.toChar() + r) }
}

private val rangeDelimiters = arrayOf("..", "-", "~")

private fun String.toRangePair(): Tuple4<String, String, Int, Int> {
	return when {
		rangeDelimiters.any { this.contains(it) } -> this.split(*rangeDelimiters, limit = 2)
			.let { it[0].trim() to it[1].trim() fromTo 0 fromTo 0 }
		this.contains(",") -> this.substring(1, length - 1).split(",", limit = 2)
			.let { it[0].trim() to it[1].trim() fromTo getLeftRangeOffset() fromTo getRightRangeOffset() }
		else -> notARange()
	}
}

private fun String.getLeftRangeOffset(): Int {
	return if(this.startsWith("[")) 0 else if(this.startsWith("(")) 1 else notARange()
}

private fun String.getRightRangeOffset(): Int {
	return if(this.endsWith("]")) 0 else if(this.endsWith(")")) -1 else notARange()
}

private fun String.notARange(): Nothing {
	throw IllegalArgumentException("String '$this' cannot be resolved as a range.")
}


/**
 * 将当前字符串转化为文件。
 */
fun String.toFile(): File {
	return File(this)
}

/**
 * 将当前字符串转化为路径。
 */
fun String.toPath(): Path {
	return File(this).toPath()
}

/**
 * 将当前字符串转化为统一资源标识符。可能需要事先对查询参数进行适当的编码。
 */
fun String.toUri(): URI {
	return URI.create(this)
}

/**
 * 将当前字符串转化为统一资源定位符。
 */
@JvmOverloads
fun String.toUrl(content: URL? = null, handler: URLStreamHandler? = null): URL {
	return URL(content, this, handler)
}


/**
 * 将当前字符串转化为字符集。如果转化失败，则抛出异常。
 */
fun String.toCharset(): Charset {
	return Charset.forName(this)
}

/**
 * 将当前字符串转化为字符集。如果转化失败，则返回null。
 */
fun String.toCharsetOrNull(): Charset? {
	return runCatching { Charset.forName(this) }.getOrNull()
}


/**
 * 将当前字符串转化为类型。如果转化失败，则抛出异常。
 */
fun String.toClass(): Class<*> {
	return Class.forName(this)
}

/**
 * 将当前字符串转化为类型。如果转化失败，则返回null。
 */
fun String.toClassOrNull(): Class<*>? {
	return runCatching { Class.forName(this) }.getOrNull()
}


/**
 * 将当前字符串转化为语言区域。如果转化失败，则抛出异常。
 */
fun String.toLocale(): Locale {
	val strings = this.split('_')
	val language = strings.getOrNull(0) ?: ""
	val region = strings.getOrNull(1) ?: ""
	val variant = strings.getOrNull(2) ?: ""
	return Locale(language, region, variant)
}


/**
 * 将当前字符串转化为时区。如果转化失败，则抛出异常。
 */
fun String.toTimeZone(): TimeZone {
	val timeZone = TimeZone.getTimeZone(this)
	if(!(timeZone.id == "GMT" && !this.startsWith("GMT"))) {
		throw IllegalArgumentException("Invalid time zone specification '$this'")
	}
	return timeZone
}

/**
 * 将当前字符串转化为时区。如果转化失败，则返回null。
 */
fun String.toTimeZoneOrNull(): TimeZone? {
	val timeZone = TimeZone.getTimeZone(this)
	if(!(timeZone.id == "GMT" && !this.startsWith("GMT"))) {
		return null
	}
	return timeZone
}

/**
 * 将当前字符串转化为日期。
 */
@JvmOverloads
fun String.toDate(format: String, locale: Locale = defaultLocale, timeZone: TimeZone = defaultTimeZone): Date {
	val dateFormat = threadLocalDateFormatMapCache.getOrPut(format) {
		ThreadLocal.withInitial { SimpleDateFormat(format, locale).also { it.timeZone = timeZone } }
	}.get()
	return dateFormat.parse(this)
}

/**
 * 将当前字符串转化为日期。
 */
fun String.toDate(dateFormat: DateFormat): Date {
	return dateFormat.parse(this)
}

/**
 * 将当前字符串转化为本地日期。
 */
@JvmOverloads
fun CharSequence.toLocalDate(formatter: DateTimeFormatter = ISO_LOCAL_DATE): LocalDate {
	return LocalDate.parse(this, formatter)
}

/**
 * 将当前字符串转化为本地日期时间。
 */
@JvmOverloads
fun CharSequence.toLocalDateTime(formatter: DateTimeFormatter = ISO_LOCAL_DATE_TIME): LocalDateTime {
	return LocalDateTime.parse(this, formatter)
}

/**
 * 将当前字符串转化为本地时间。
 */
@JvmOverloads
fun CharSequence.toLocalTime(formatter: DateTimeFormatter = ISO_LOCAL_TIME): LocalDateTime {
	return LocalDateTime.parse(this, formatter)
}

/**
 * 将当前字符串转化为时长。
 */
fun CharSequence.toDuration(): Duration {
	return Duration.parse(this)
}

/**
 * 将当前字符串转化为时期。
 */
fun CharSequence.toPeriod(): Period {
	return Period.parse(this)
}

/**
 * 将当前字符串转化为瞬时。
 */
fun CharSequence.toInstant(): Instant {
	return Instant.parse(this)
}


/**
 * 将当前字符串转化为颜色。如果转化失败，则抛出异常。
 *
 * 允许的格式：`red`，`#ffffff`，`rgb(0,0,0)`，`rgba(0,0,0,255)`
 */
fun String.toColor(): Color {
	return Color.parse(this)
}

/**
 * 将当前字符串转换为颜色。如果转化失败，则返回null。
 *
 * 允许的格式：`red`，`#ffffff`，`rgb(0,0,0)`，`rgba(0,0,0,255)`
 */
fun String.toColorOrNull(): Color? {
	return Color.parseOrNull(this)
}


/**
 * 将当前字符串转化为查询参数映射。
 */
internal fun String.toQueryParams(): Map<String, List<String>> {
	return this.split("&").groupBy({ it.substringBefore("=") }, { it.substringAfter("=", "") })
}


/**
 * 将当前字符串解码为base64格式的字节数组。
 */
@Deprecated("Use String.decodeBy(Encoder.Base64Encoder)", ReplaceWith(
	"this.decodeBy(Encoder.Base64Encoder)",
	"icu.windea.breezeframework.core.component.Encoder"
))
fun String.decodeToBase64ByteArray(): ByteArray {
	return Base64.getDecoder().decode(this)
}
//endregion

//region raw string convert extensions
/**
 * 将当前字符串转为内联文本。
 *
 * @see icu.windea.breezeframework.core.extension.trimWrap
 */
inline fun String.inline(): String = trimWrap()

/**
 * 将当前字符串转为多行文本。
 *
 * @see kotlin.text.trimIndent
 */
inline fun String.multiline(): String = trimIndent()

/**
 * 去除当前字符串中的所有换行符。
 */
fun String.trimWrap(): String {
	return buildString {
		for(c in this) {
			if(c != '\r' && c != '\n') append(c)
		}
	}
}

/**
 * 去除当前字符串的首尾空白行，然后基于之前的尾随空白行的缩进，尝试去除每一行的缩进。
 * 相对缩进长度默认为0。使用负数表示`"\t"`。
 */
@JvmOverloads
fun String.trimRelativeIndent(relativeIndentSize: Int = 0): String {
	require(relativeIndentSize in -2..8) { "Relative indent size should between -2 and 8, but was $relativeIndentSize." }

	val lines = this.lines()
	val additionalIndent = if(relativeIndentSize > 0) " " * relativeIndentSize else "\t" * relativeIndentSize
	val trimmedIndent = lines.last().ifNotBlank { "" } + additionalIndent
	return if(trimmedIndent.isEmpty()) trimIndent()
	else lines.dropBlank().dropLastBlank().joinToString("\n") { it.removePrefix(trimmedIndent) }
}
//endregion

//region text extensions
/**
 * 将当前对象转化成文本。
 * * 如果当前对象是空值，则返回空字符串。
 * * 如果存在转化方法，则使用该方法将当前对象转化为字符串。
 */
fun <T : Any> T?.toText(transform: ((T) -> String)? = null): String {
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
	transform: ((T) -> CharSequence)? = null,
): String {
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
): String {
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
): String {
	var result = buildString {
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
): String {
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
//endregion
