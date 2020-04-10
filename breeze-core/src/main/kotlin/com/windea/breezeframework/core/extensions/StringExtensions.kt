@file:JvmName("StringExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.domain.text.*
import java.io.*
import java.net.*
import java.nio.charset.*
import java.nio.file.*
import java.text.*
import java.time.*
import java.time.format.*
import java.util.*
import kotlin.contracts.*

//注意：某些情况下，如果直接参照标准库的写法编写扩展方法，会报编译器错误

//region operator extensions
/**
 * 移除当前字符串中的指定子字符串。
 * @see com.windea.breezeframework.core.extensions.remove
 */
operator fun String.minus(other:Any?):String = if(other == null) this else this.remove(other.toString())

/**
 * 重复当前字符串到指定次数。
 * @see kotlin.text.repeat
 */
operator fun String.times(n:Int):String = this.repeat(n)

/**
 * 切分当前字符串到指定个数。
 * @see kotlin.text.chunked
 */
operator fun String.div(n:Int):List<String> = this.chunked(n)

/**
 * 得到索引指定范围内的子字符串。
 * @see kotlin.text.slice
 */
operator fun String.get(indices:IntRange):String = this.slice(indices)

/**
 * 得到指定索引范围内的子字符串。
 * @see kotlin.text.substring
 */
operator fun String.get(startIndex:Int, endIndex:Int):String = this.substring(startIndex, endIndex)
//endregion

//region common extensions
/**判断两个字符串是否相等，忽略大小写。。*/
infix fun String?.equalsIgnoreCase(other:String?):Boolean = this.equals(other, true)


/**判断当前字符串中的所有字符是否被另一字符串包含。*/
infix fun CharSequence.allIn(other:CharSequence):Boolean = this in other

/**判断当前字符串中的任意字符是否被另一字符串包含。*/
infix fun CharSequence.anyIn(other:CharSequence):Boolean = this.any { it in other }


/**判断当前字符串是否以指定前缀开头。*/
inline infix fun CharSequence.startsWith(prefix:CharSequence):Boolean =
	this.startsWith(prefix, false)

/**判断当前字符串是否以指定前缀开头。忽略大小写。*/
inline infix fun CharSequence.startsWithIgnoreCase(prefix:CharSequence):Boolean =
	this.startsWith(prefix, true)

/**判断当前字符串是否以任意指定前缀开头。*/
inline infix fun CharSequence.startsWith(prefixes:Array<out CharSequence>):Boolean =
	prefixes.any { this.startsWith(it, false) }

/**判断当前字符串是否以任意指定前缀开头。忽略大小写。*/
inline infix fun CharSequence.startsWithIgnoreCase(prefixes:Array<out CharSequence>):Boolean =
	prefixes.any { this.startsWith(it, true) }

/**判断当前字符串是否以指定后缀结尾。*/
inline infix fun CharSequence.endsWith(suffixes:CharSequence):Boolean =
	this.endsWith(suffixes, false)

/**判断当前字符串是否以指定后缀结尾。忽略大小写。*/
inline infix fun CharSequence.endsWithIgnoreCase(suffix:CharSequence):Boolean =
	this.endsWith(suffix, true)

/**判断当前字符串是否以任意指定后缀结尾。*/
inline infix fun CharSequence.endsWith(suffixes:Array<out CharSequence>):Boolean =
	suffixes.any { this.endsWith(it, false) }

/**判断当前字符串是否以任意指定后缀结尾。忽略大小写。*/
inline infix fun CharSequence.endsWithIgnoreCase(suffixes:Array<out CharSequence>):Boolean =
	suffixes.any { this.endsWith(it, true) }


/**判断当前字符串是否以指定前缀开始且以指定后缀结尾。默认不忽略大小写。*/
fun CharSequence.surroundsWith(prefix:Char, suffix:Char, ignoreCase:Boolean = false):Boolean {
	return this.startsWith(prefix, ignoreCase) && this.endsWith(suffix, ignoreCase)
}

/**判断当前字符串是否以指定前后缀开头和结尾。默认不忽略大小写。*/
fun CharSequence.surroundsWith(delimiter:Char, ignoreCase:Boolean = false):Boolean {
	return this.startsWith(delimiter, ignoreCase) && this.endsWith(delimiter, ignoreCase)
}

/**判断当前字符串是否以指定前缀开始且以指定后缀结尾。默认不忽略大小写。*/
fun CharSequence.surroundsWith(prefix:CharSequence, suffix:CharSequence, ignoreCase:Boolean = false):Boolean {
	return this.startsWith(prefix, ignoreCase) && this.endsWith(suffix, ignoreCase)
}

/**判断当前字符串是否以指定前后缀开头和结尾。默认不忽略大小写。*/
fun CharSequence.surroundsWith(delimiter:CharSequence, ignoreCase:Boolean = false):Boolean {
	return this.startsWith(delimiter, ignoreCase) && this.endsWith(delimiter, ignoreCase)
}


/**判断当前字符串是否不为null，且不为空字符串。*/
@UselessCallOnNotNullType
@JvmSynthetic
inline fun CharSequence?.isNotNullOrEmpty():Boolean {
	contract {
		returns(true) implies (this@isNotNullOrEmpty != null)
	}
	return this != null && this.isNotEmpty()
}

/**判断当前字符串是否不为null，且不为空白字符串。*/
@UselessCallOnNotNullType
@JvmSynthetic
inline fun CharSequence?.isNotNullOrBlank():Boolean {
	contract {
		returns(true) implies (this@isNotNullOrBlank != null)
	}
	return this != null && this.isNotBlank()
}

/**判断当前字符串是否仅包含字母，且不为空/空白字符串。*/
@UnstableImplementationApi
fun CharSequence.isAlphabetic():Boolean {
	return this matches "[a-zA-Z]+".toRegex()
}

/**判断当前字符串是否仅包含数字，且不为空/空白字符串。*/
@UnstableImplementationApi
fun CharSequence.isNumeric():Boolean {
	return this matches "[1-9]+".toRegex()
}

/**判断当前字符串是否仅包含字母、数字和下划线，且不为空/空白字符串。*/
@UnstableImplementationApi
fun CharSequence.isAlphanumeric():Boolean {
	return this matches "[1-9a-zA-Z_]+".toRegex()
}


/**如果当前字符串不为空，则返回本身，否则返回null。*/
@JvmSynthetic
inline fun <C : CharSequence> C.orNull():C? = if(this.isEmpty()) null else this


/**如果当前字符串不为空，则返回转化后的值，否则返回本身。*/
@JvmSynthetic
@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER")
inline fun <C, R> C.ifNotEmpty(transform:(C) -> R):R where C : CharSequence, C : R =
	if(this.isEmpty()) this else transform(this)

/**如果当前字符串不为空白，则返回转化后的值，否则返回本身。*/
@JvmSynthetic
@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER")
inline fun <C, R> C.ifNotBlank(transform:(C) -> R):R where C : CharSequence, C : R =
	if(this.isBlank()) this else transform(this)


/**如果当前字符串不为空，则返回本身，否则返回null。*/
@JvmSynthetic
inline fun <C : CharSequence> C.takeIfNotEmpty():C? = if(this.isEmpty()) null else this

/**如果当前字符串不为空白，则返回本身，否则返回null。*/
@JvmSynthetic
inline fun <C : CharSequence> C.takeIfNotBlank():C? = if(this.isBlank()) null else this


/**分别依次重复当前字符串中的字符到指定次数。*/
fun CharSequence.repeatOrdinal(n:Int):String {
	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }

	return this.map { it.repeat(n) }.joinToString("")
}


/**限制在指定的前后缀之间的子字符串内，对其执行转化操作，最终返回连接后的字符串。*/
@NotOptimized
fun String.transformIn(prefix:String, suffix:String, transform:(String) -> String):String {
	//前后缀会在转义后加入正则表达式，可以分别是\\Q和\\E
	//前后缀可能会发生冲突
	return this.replace("(?<=${Regex.escape(prefix)}).*?(?=${Regex.escape(suffix)})".toRegex()) { transform(it[0]) }
}

/**限制在指定的正则表达式匹配的子字符串内，对其执行转化操作，最终返回连接后的字符串。*/
@NotOptimized
fun String.transformIn(regex:Regex, transform:(String) -> String):String {
	return this.replace(regex) { transform(it[0]) }
}


///**并行处理当前字符串。注意应在作为参数的代码块中使用[replaceFirst]方法替换字符串，而非[replace]方法。*/
//@NotSure
//tailrec fun String.sequential(vararg blocks: (String) -> String): String {
//	var result = this
//	for(block in blocks) {
//		result = block(this)
//	}
//	return if(this != result) this.sequential(*blocks) else this
//}


/**根据指定的前后缀，替换首个符合条件的子字符串，如果找不到前缀或后缀，则替换为默认值。*/
@JvmOverloads
fun String.replaceIn(prefix:Char, suffix:Char, replacement:String, missingDelimiterValue:String = this):String {
	val index = indexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val lastIndex = (substring(index).indexOf(suffix) + index).also { if(it == -1) return missingDelimiterValue }
	return replaceRange(index + 1, lastIndex, replacement)
}

/**根据指定的前后缀，替换首个符合条件的子字符串，如果找不到前缀或后缀，则替换为默认值。*/
@JvmOverloads
fun String.replaceIn(prefix:String, suffix:String, replacement:String, missingDelimiterValue:String = this):String {
	val index = indexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val lastIndex = (substring(index).indexOf(suffix) + index).also { if(it == -1) return missingDelimiterValue }
	return replaceRange(index + prefix.length, lastIndex, replacement)
}

/**根据指定的前后缀，替换最后一个符合条件的子字符串，如果找不到前缀或后缀，则替换为默认值。*/
@JvmOverloads
fun String.replaceInLast(prefix:Char, suffix:Char, replacement:String, missingDelimiterValue:String = this):String {
	val lastIndex = lastIndexOf(suffix).also { if(it == -1) return missingDelimiterValue }
	val index = substring(0, lastIndex).lastIndexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	return replaceRange(index + 1, lastIndex, replacement)
}

/**根据指定的前后缀，替换最后一个符合条件的子字符串，如果找不到前缀或后缀，则替换为默认值。*/
@JvmOverloads
fun String.replaceInLast(prefix:String, suffix:String, replacement:String, missingDelimiterValue:String = this):String {
	val lastIndex = lastIndexOf(suffix).also { if(it == -1) return missingDelimiterValue }
	val index = substring(0, lastIndex).lastIndexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	return replaceRange(index + prefix.length, lastIndex, replacement)
}

/**根据指定的前后缀，替换最大范围的符合条件的子字符串，如果找不到前缀或后缀，则替换为默认值。*/
@JvmOverloads
fun String.replaceInEntire(prefix:Char, suffix:Char, replacement:String, missingDelimiterValue:String = this):String {
	val index = indexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val lastIndex = (substring(index).lastIndexOf(suffix) + index).also { if(it == -1) return missingDelimiterValue }
	return replaceRange(index + 1, lastIndex, replacement)
}

/**根据指定的前后缀，替换最大范围的符合条件的子字符串，如果找不到前缀或后缀，则替换为默认值。*/
@JvmOverloads
fun String.replaceInEntire(prefix:String, suffix:String, replacement:String, missingDelimiterValue:String = this):String {
	val index = indexOf(prefix).also { if(it == -1) return missingDelimiterValue }
	val lastIndex = (substring(index).lastIndexOf(suffix) + index).also { if(it == -1) return missingDelimiterValue }
	return replaceRange(index + prefix.length, lastIndex, replacement)
}


/**将当前字符串中的指定字符替换成根据索引得到的字符。*/
@JvmOverloads
inline fun CharSequence.replaceIndexed(oldChar:Char, ignoreCase:Boolean = false, newChar:(Int) -> Char):String {
	return buildString {
		val splitStrings = this@replaceIndexed.splitToSequence(oldChar, ignoreCase = ignoreCase)
		for((i, s) in splitStrings.withIndex()) {
			this.append(s)
			if(i < splitStrings.count() - 1) this.append(newChar(i))
		}
	}
}

/**将当前字符串中的指定值替换成根据索引得到的字符串。*/
@JvmOverloads
inline fun CharSequence.replaceIndexed(oldValue:String, ignoreCase:Boolean = false, newValue:(Int) -> String):String {
	return buildString {
		val splitStrings = this@replaceIndexed.splitToSequence(oldValue, ignoreCase = ignoreCase)
		for((i, s) in splitStrings.withIndex()) {
			this.append(s)
			if(i < splitStrings.count() - 1) this.append(newValue(i))
		}
	}
}


/**根据指定的两组字符串，将当前字符串中的对应字符串替换成对应的替换后字符串。默认不忽略大小写。*/
@JvmOverloads
fun String.replaceAll(oldChars:CharArray, newChars:CharArray, ignoreCase:Boolean = false):String {
	val size = minOf(oldChars.size, newChars.size)
	var result = this
	for(i in 0 until size) {
		result = result.replace(oldChars[i], newChars[i], ignoreCase)
	}
	return result
}

/**根据指定的两组字符串，将当前字符串中的对应字符串替换成对应的替换后字符串。默认不忽略大小写。*/
@JvmOverloads
fun String.replaceAll(oldValues:Array<String>, newValues:Array<String>, ignoreCase:Boolean = false):String {
	val size = minOf(oldValues.size, newValues.size)
	var result = this
	for(i in 0 until size) {
		result = result.replace(oldValues[i], newValues[i], ignoreCase)
	}
	return result
}


/**递归使用字符串替换当前字符串，直到已经不需要再做一次替换为止。*/
@UnstableImplementationApi
tailrec fun String.replaceLooped(oldValue:String, newValue:String):String {
	val result = this.replace(oldValue, newValue)
	return if(this != result) result.replaceLooped(oldValue, newValue) else result
}

/**递归使用正则表达式替换当前字符串，直到已经不需要再做一次替换为止。*/
@UnstableImplementationApi
tailrec fun CharSequence.replaceLooped(regex:Regex, replacement:String):String {
	val result = this.replace(regex, replacement)
	return if(this != result) result.replaceLooped(regex, replacement) else result
}

/**递归使用正则表达式替换当前字符串，直到已经不需要再做一次替换为止。*/
@UnstableImplementationApi
tailrec fun CharSequence.replaceLooped(regex:Regex, transform:(MatchResult) -> CharSequence):String {
	val newString = this.replace(regex, transform)
	//如果字符串长度不相等，则字符串一定不相等
	return if(this.length != newString.length || this != newString) {
		newString.replaceLooped(regex, transform)
	} else {
		newString
	}
}


/**根据指定的限定长度和截断符截断当前字符串。截断符默认为英文省略号。*/
fun String.truncate(limit:Int, truncated:CharSequence = "..."):String {
	return if(this.length <= limit) this else this.take(limit) + truncated
}


/**为当前字符串设置指定的前缀。如果长度不够，则返回自身。*/
infix fun String.setPrefix(prefix:CharSequence):String {
	if(this.length < prefix.length) return this
	return "$prefix${this.drop(prefix.length)}"
}

/**为当前字符串设置指定的后缀。如果长度不够，则返回自身。*/
infix fun String.setSuffix(suffix:CharSequence):String {
	if(this.length < suffix.length) return this
	return "${this.dropLast(suffix.length)}$suffix"
}

/**为当前字符串设置指定的前后缀。如果长度不够，则返回自身。*/
infix fun String.setSurrounding(delimiter:CharSequence):String {
	return this.setSurrounding(delimiter, delimiter)
}

/**为当前字符串设置指定的前缀和后缀。如果长度不够，则返回自身。*/
fun String.setSurrounding(prefix:CharSequence, suffix:CharSequence):String {
	if(this.length < prefix.length + suffix.length) return this
	return "$prefix${this.drop(prefix.length).dropLast(suffix.length)}$suffix"
}


/**为当前字符串添加指定的前缀。如果已存在，则返回自身。*/
infix fun String.addPrefix(prefix:CharSequence):String {
	if(this.startsWith(prefix)) return this
	return "$prefix$this"
}

/**为当前字符串添加指定的后缀。如果已存在，则返回自身。*/
infix fun String.addSuffix(suffix:CharSequence):String {
	if(this.endsWith(suffix)) return this
	return "$this$suffix"
}

/**为当前字符串添加指定的前后缀。如果已存在，则返回自身。*/
infix fun String.addSurrounding(delimiter:CharSequence):String {
	return this.addSurrounding(delimiter, delimiter)
}

/**为当前字符串添加指定的前缀和后缀。如果已存在，则返回自身。*/
fun String.addSurrounding(prefix:CharSequence, suffix:CharSequence):String {
	if(this.startsWith(prefix) && this.endsWith(suffix)) return this
	return "$prefix$this$suffix"
}


/**逐行向左对齐当前字符串，并保证每行长度一致，用指定字符填充。默认为空格。*/
@JvmOverloads
fun String.alignStart(padChar:Char = ' '):String {
	val lines = this.lines()
	if(lines.size <= 1) return this
	val maxLength = lines.map { it.length }.max()!!
	return lines.joinToString("\n") { it.trimStart().padEnd(maxLength, padChar) }
}

/**逐行向右对齐当前字符串，并保证每行长度一致，用指定字符填充。默认为空格。*/
@JvmOverloads
fun String.alignEnd(padChar:Char = ' '):String {
	val lines = this.lines()
	if(lines.size <= 1) return this
	val maxLength = lines.map { it.length }.max()!!
	return lines.joinToString("\n") { it.trimEnd().padStart(maxLength, padChar) }
}

/**逐行中心对齐当前字符串，并保证每行长度一致，用指定字符填充。默认为空格。*/
@JvmOverloads
fun String.alignCenter(padChar:Char = ' '):String {
	val lines = this.lines()
	if(lines.size <= 1) return this
	val maxLength = lines.map { it.length }.max()!!
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


/**去除指定字符。*/
fun String.remove(oldChar:Char, ignoreCase:Boolean = false):String {
	return this.replace(oldChar.toString(), "", ignoreCase)
}

/**去除指定字符串。*/
fun String.remove(oldValue:String, ignoreCase:Boolean = false):String {
	return this.replace(oldValue, "", ignoreCase)
}

/**去除指定正则表达式的字符串。*/
fun String.remove(regex:Regex):String {
	return this.replace(regex, "")
}


/**
 * 根据以null隔离的从前往后和从后往前的分隔符，匹配并按顺序分割当前字符串。
 * 不包含分隔符时，加入空字符串。
 */
fun String.substringMatch(vararg delimiters:String?):List<String> =
	substringMatch0(*delimiters) { _, _ -> "" }

/**
 * 根据以null隔离的从前往后和从后往前的分隔符，匹配并按顺序分割当前字符串。
 * 不包含分隔符时，加入基于索引和剩余字符串得到的默认值。
 */
fun String.substringMatchOrElse(vararg delimiters:String?, defaultValue:(Int, String) -> String):List<String> =
	substringMatch0(*delimiters, defaultValue = defaultValue)

/**
 * 根据以null隔离的从前往后和从后往前的分隔符，匹配并按顺序分割当前字符串。
 * 不包含分隔符时，加入基于剩余字符串得到的默认值数组中的对应索引的值。
 */
fun String.substringMatchOrElse(vararg delimiters:String?, defaultValue:(String) -> Array<String>):List<String> =
	substringMatch0(*delimiters) { index, str -> defaultValue(str).getOrEmpty(index) }

@NotOptimized
private fun String.substringMatch0(vararg delimiters:String?, defaultValue:(Int, String) -> String):List<String> {
	require(delimiters.count { it == null } <= 1) { "There should be at most one null value as separator in delimiters." }

	var rawString = this
	val fixedDelimiters = delimiters.filterNotNull()
	val size = fixedDelimiters.size
	val indexOfNull = delimiters.indexOf(null).let { if(it == -1) size else it }
	val result = mutableListOf<String>()

	for((index, delimiter) in fixedDelimiters.withIndex()) {
		if(index < indexOfNull) {
			result += rawString.substringBefore(delimiter, defaultValue(index, rawString))
			if(index == size - 1) {
				result += rawString.substringAfter(delimiter, defaultValue(index, rawString))
			} else {
				rawString = rawString.substringAfter(delimiter, defaultValue(index, rawString))
			}
		} else {
			result += rawString.substringBeforeLast(delimiter, defaultValue(index, rawString))
			if(index == size - 1) {
				result += rawString.substringAfterLast(delimiter, defaultValue(index, rawString))
			} else {
				rawString = rawString.substringAfterLast(delimiter, defaultValue(index, rawString))
			}
		}
	}
	return result
}


/**将当前字符串解码为base64格式的字节数组。*/
fun String.decodeToBase64ByteArray():ByteArray = Base64.getDecoder().decode(this)
//endregion

//region line extensions
///**逐行缩进当前字符串。默认适用4个空格进行缩进。*/
//fun String.lineIndent(indent:String = "    "):String {
//	return this.lineSequence().map {
//			when {
//				it.isBlank() -> when {
//					it.length < indent.length -> indent
//					else -> it
//				}
//				else -> indent + it
//			}
//	}.joinToString("\n")
//}
//
///**除了首行和尾行以外，逐行缩进当前字符串。默认适用4个空格进行缩进。*/
//fun String.lineContentIndent(indent:String = "    "):String {
//	val lines = this.lines()
//	return lines.mapIndexed { i, s ->
//		when{
//			i == 0 || i == lines.lastIndex -> s
//			s.isBlank() -> when{
//				s.length < indent.length -> indent
//				else -> s
//			}
//			else -> indent + s
//		}
//	}.joinToString("\n")
//}

/**逐行连接两个字符串。返回的字符串的长度为两者长度中的较大值。*/
@UnstableImplementationApi
infix fun String.lineConcat(other:String):String {
	val lines = this.lines()
	val otherLines = other.lines()
	return when {
		lines.size <= otherLines.size -> lines.fillEnd(otherLines.size, "") zip otherLines
		else -> lines zip otherLines.fillEnd(lines.size, "")
	}.joinToString("\n") { (a, b) -> "$a$b" }
}

/**逐行换行当前字符串，确保每行长度不超过指定长度。不做任何特殊处理。*/
@UnstableImplementationApi
@JvmOverloads
fun String.lineBreak(width:Int = 120):String {
	return this.lines().joinToString("\n") { if(it.length > width) it.chunked(width).joinToString("\n") else it }
}
//endregion

//region specific extensions
/**尝试使用指定的引号包围当前字符串。默认忽略其中的引号，不对其进行转义。*/
fun String.quote(quote:Char, omitQuotes:Boolean = true):String {
	return when {
		quote !in quotes -> throw IllegalArgumentException("Invalid quote: $quote.")
		this.surroundsWith(quote) -> this
		omitQuotes -> this.addSurrounding(quote.toString())
		else -> this.replace(quote.toString(), "\\$quote").addSurrounding(quote.toString())
	}
}

/**尝试去除当前字符串两侧的引号。如果没有，则返回自身。默认忽略其中的引号，不对其进行反转义。*/
fun String.unquote(omitQuotes:Boolean = true):String {
	val quote = this.firstOrNull()
	return when {
		quote == null -> this
		quote !in quotes -> this
		!this.surroundsWith(quote) -> this
		omitQuotes -> this.removeSurrounding(quote.toString())
		else -> this.removeSurrounding(quote.toString()).replace("\\$quote", quote.toString())
	}
}

private val quotes = charArrayOf('\"', '\'', '`')


/**根据指定的格式化类型，格式化当前字符串。可以指定可选的语言环境和占位符。*/
fun String.formatBy(type:FormatType, vararg args:Any?, locale:Locale? = null, placeholder:Pair<String, String>? = null):String {
	return type.formatter(this, args, locale, placeholder)
}


/**根据指定的转义类型，转义当前字符串。默认不转义反斜线。*/
fun String.escapeBy(type:EscapeType, omitBackslashes:Boolean = true):String {
	val tempString = if(omitBackslashes) this else this.replace("\\", "\\\\")
	return tempString.replaceAll(type.escapeStrings, type.escapedStrings)
}

/**根据指定的转义类型，反转义当前字符串。默认不反转一反斜线*/
fun String.unescapeBy(type:EscapeType, omitBackslashes:Boolean = true):String {
	val tempString = this.replaceAll(type.escapedStrings, type.escapeStrings)
	return if(omitBackslashes) tempString else tempString.replace("\\\\", "\\")
}


/**根据指定的匹配类型，将当前字符串转化为对应的正则表达式。*/
fun String.toRegexBy(type:MatchType):Regex {
	return type.regexTransform(this).toRegex()
}

/**根据指定的匹配类型，将当前字符串转化为对应的正则表达式。*/
fun String.toRegexBy(type:MatchType, option:RegexOption):Regex {
	return type.regexTransform(this).toRegex(option)
}

/**根据指定的匹配类型，将当前字符串转化为对应的正则表达式。*/
fun String.toRegexBy(type:MatchType, options:Set<RegexOption>):Regex {
	return type.regexTransform(this).toRegex(options)
}


/**得到当前字符串的字母格式。*/
val String.letterCase:LetterCase get() = enumValues<LetterCase>().first { it.predicate(this) }

/**得到当前字符串的引用格式。*/
val String.referenceCase:ReferenceCase get() = enumValues<ReferenceCase>().first { it.predicate(this) }

/**根据指定的显示格式，分割当前字符串，返回对应的字符串列表。*/
fun String.splitBy(case:DisplayCase):List<String> {
	return case.splitter(this)
}

/**根据指定的显示格式，分割当前字符串，返回对应的字符串序列。*/
fun String.splitToSequenceBy(case:DisplayCase):Sequence<String> {
	return case.sequenceSplitter(this)
}

/**根据指定的显示格式，将当前字符串数组中的元素加入到字符串。*/
fun Array<out CharSequence>.joinToStringBy(case:DisplayCase):String {
	return case.arrayJoiner(this)
}

/**根据指定的显示格式，将当前字符串集合中的元素加入到字符串。*/
fun Iterable<CharSequence>.joinToStringBy(case:DisplayCase):String {
	return case.joiner(this)
}

/**根据指定的显示格式，切换当前字符串的格式。*/
fun String.switchCaseBy(fromCase:DisplayCase, toCase:DisplayCase):String {
	return this.splitBy(fromCase).joinToStringBy(toCase)
}

/**根据指定的显示格式，切换当前字符串的格式。可以根据目标格式类型自动推导出当前格式，但某些格式需要显式指定。*/
fun String.switchCaseBy(case:DisplayCase):String {
	return this.splitBy(when(case) {
		is LetterCase -> this.letterCase
		is ReferenceCase -> this.referenceCase
		else -> throw IllegalArgumentException("Cannot find an actual way to get actual display case from a string.")
	}).joinToStringBy(case)
}
//endregion

//region handle extensions
/**
 * 将当前字符串转为内联文本。
 * @see com.windea.breezeframework.core.extensions.trimWrap
 */
inline val String.inline:String get() = this.trimWrap()

/**
 * 将当前字符串转为多行文本。
 * @see kotlin.text.trimIndent
 */
inline val String.multiline:String get() = this.trimIndent()

/**
 * 去除当前字符串中的所有换行符以及换行符周围的空白。
 */
fun String.trimWrap():String {
	return this.remove("""\s*(\r|\n|\r\n)\s*""".toRegex())
}

/**
 * 去除当前字符串的首尾空白行，然后基于之前的尾随空白行的缩进，尝试去除每一行的缩进。默认为0。
 **/
@JvmOverloads
fun String.trimRelativeIndent(relativeIndentSize:Int = 0):String {
	require(relativeIndentSize in -2..8) { "Relative indent size should between -2 and 8, but was $relativeIndentSize." }

	val lines = this.lines()
	val additionalIndent = if(relativeIndentSize > 0) " " * relativeIndentSize else "\t" * relativeIndentSize
	val trimmedIndent = lines.last().ifNotBlank { "" } + additionalIndent
	return if(trimmedIndent.isEmpty()) this.trimIndent()
	else lines.dropBlank().dropLastBlank().joinToString("\n") { it.removePrefix(trimmedIndent) }
}
//endregion

//region convert extensions
/**将当前字符串转化为字符。如果转化失败，则抛出异常。这个方法由[String.single]委托实现。*/
inline fun String.toChar():Char = this.single()

/**将当前字符串转化为字符。如果转化失败，则返回null。这个方法由[String.single]委托实现。*/
inline fun String.toCharOrNull():Char? = this.singleOrNull()


/**将当前字符串转化为指定的数字类型。如果转化失败或者不支持指定的数字类型，则抛出异常。默认使用十进制。*/
inline fun <reified T : Number> String.toNumber(radix:Int = 10):T {
	//performance note: approach to 1/5
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

/**将当前字符串转化为指定的数字类型。如果转化失败或者不支持指定的数字类型，则返回null。默认使用十进制。*/
inline fun <reified T : Number> String.toNumberOrNull(radix:Int = 10):T? {
	//performance note: approach to 1/5
	return when(T::class.java.name) {
		"java.lang.Integer" -> this.toIntOrNull(radix) as T?
		"java.lang.Long" -> this.toLongOrNull(radix) as T?
		"java.lang.Float" -> this.toFloatOrNull() as T?
		"java.lang.Double" -> this.toDoubleOrNull() as T?
		"java.lang.Byte" -> this.toByteOrNull(radix) as T?
		"java.lang.Short" -> this.toShortOrNull(radix) as T?
		"java.math.BigInteger" -> this.toBigIntegerOrNull(radix) as T?
		"java.math.BigDecimal" -> this.toBigDecimalOrNull() as T?
		else -> null
	}
}


/**将当前字符串转化为对应的枚举值。如果转化失败，则抛出异常。*/
inline fun <reified T : Enum<T>> String.toEnumValue(ignoreCase:Boolean = false):T {
	return enumValues<T>().first { it.toString().equals(this, ignoreCase) }
}

/**将当前字符串转化为对应的枚举值。如果转化失败，则返回null。*/
inline fun <reified T : Enum<T>> String.toEnumValueOrNull(ignoreCase:Boolean = false):T? {
	return enumValues<T>().firstOrNull { it.toString().equals(this, ignoreCase) }
}

/**将当前字符串转化为对应的枚举值。如果转化失败，则抛出异常。*/
@JvmOverloads
fun <T> String.toEnumValue(type:Class<T>, ignoreCase:Boolean = false):T {
	requireNotNull(type.isEnum) { "$type is not an enum class." }

	return type.enumConstants.first { it.toString().equals(this, ignoreCase) }
}

/**将当前字符串转化为对应的枚举值。如果转化失败，则返回null。*/
@JvmOverloads
fun <T> String.toEnumValueOrNull(type:Class<T>, ignoreCase:Boolean = false):T? {
	requireNotNull(type.isEnum) { "$type is not an enum class." }

	return type.enumConstants.firstOrNull { it.toString().equals(this, ignoreCase) }
}


/**将当前字符串转化为字符范围。如果转化失败，则抛出异常。支持的格式包括`m..n`，`m-n`，`m~n`，`[m, n]`，`[m, n)`等。*/
fun String.toCharRange():CharRange {
	return this.toRangePair().let { (a, b, l, r) -> a.toChar() + l..b.toChar() + r }
}

/**将当前字符串转化为整数范围。如果转化失败，则抛出异常。支持的格式包括`m..n`，`m-n`，`m~n`，`[m, n]`，`[m, n)`等。*/
fun String.toIntRange():IntRange {
	return this.toRangePair().let { (a, b, l, r) -> a.toInt() + l..b.toInt() + r }
}

/**将当前字符串转化为长整数范围。如果转化失败，则抛出异常。支持的格式包括`m..n`，`m-n`，`m~n`，`[m, n]`，`[m, n)`等。*/
fun String.toLongRange():LongRange {
	return this.toRangePair().let { (a, b, l, r) -> a.toLong() + l..b.toLong() + r }
}

private val rangeDelimiters = arrayOf("..", "-", "~")

private fun String.toRangePair() = when {
	rangeDelimiters.any { this.contains(it) } -> this.split(*rangeDelimiters, limit = 2)
		.let { it[0].trim() to it[1].trim() with 0 with 0 }
	this.contains(",") -> this.substring(1, this.length - 1).split(",", limit = 2)
		.let { it[0].trim() to it[1].trim() with this.getLeftRangeOffset() with this.getRightRangeOffset() }
	else -> this.notARange()
}

private fun String.getLeftRangeOffset() =
	if(this.startsWith("[")) 0 else if(this.startsWith("(")) 1 else this.notARange()

private fun String.getRightRangeOffset() =
	if(this.endsWith("]")) 0 else if(this.endsWith(")")) -1 else this.notARange()

private fun String.notARange():Nothing =
	throw IllegalArgumentException("String '$this' cannot be resolved as a range.")


/**将当前字符串转化为文件。*/
inline fun String.toFile():File = File(this)

/**将当前字符串转化为路径。*/
inline fun String.toPath():Path = Path.of(this)

/**将当前字符串转化为统一资源标识符。可能需要事先对查询参数进行适当的编码。*/
inline fun String.toUri():URI = URI.create(this)

/**将当前字符串转化为统一资源定位符。*/
@JvmOverloads
inline fun String.toUrl(content:URL? = null, handler:URLStreamHandler? = null):URL = URL(content, this, handler)

/**将当前字符串转化为字符集。*/
inline fun String.toCharset():Charset = Charset.forName(this)

/**将当前对象转化为类型。*/
inline fun String.toClass():Class<*> = Class.forName(this)


/**将当前字符串转化为日期。*/
@JvmOverloads
inline fun String.toDate(format:String = "yyyy-MM-dd HH:mm:ss"):Date = SimpleDateFormat(format).parse(this)

/**将当前字符串转化为本地日期。*/
@JvmOverloads
inline fun CharSequence.toLocalDate(formatter:DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE):LocalDate =
	LocalDate.parse(this, formatter)

/**将当前字符串转化为本地日期时间。*/
@JvmOverloads
inline fun CharSequence.toLocalDateTime(formatter:DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME):LocalDateTime =
	LocalDateTime.parse(this, formatter)

/**将当前字符串转化为本地时间。*/
@JvmOverloads
inline fun CharSequence.toLocalTime(formatter:DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME):LocalDateTime =
	LocalDateTime.parse(this, formatter)
//endregion
