@file:Suppress("UNUSED_PARAMETER", "NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.domain.text.*
import com.windea.breezeframework.core.enums.*
import mu.*
import java.awt.*
import java.io.*
import java.net.*
import java.nio.file.*
import java.text.*
import java.time.*
import java.time.format.*
import java.util.*

private val logger = KotlinLogging.logger { }


/**判断字符串是否相等。忽略大小写。*/
@OutlookImplementationApi
infix fun String?.equalsIc(other: String?): Boolean {
	return this.equals(other, true)
}

/**判断字符串是否相等。忽略显示格式[LetterCase]。*/
@OutlookImplementationApi
infix fun String?.equalsIlc(other: String?): Boolean {
	if(this == other) return true
	return this != null && other != null && this.switchTo(this.letterCase, other.letterCase) == other
}


/**判断当前字符串中的任意字符是否被另一字符串包含。*/
@OutlookImplementationApi
infix fun String.anyIn(other: String): Boolean = this.any { it in other }

/**判断当前字符串是否与另一字符串相像。即，判断是否存在共同的以空格分隔的单词。*/
infix fun String.like(other: String): Boolean = this.splitToWordList() anyIn other.splitToWordList()


/**判断当前字符串是否以指定前缀开头。*/
@OutlookImplementationApi
infix fun CharSequence.startsWith(prefix: CharSequence): Boolean {
	return this.startsWith(prefix, false)
}

/**判断当前字符串是否以任意指定前缀开头。*/
@OutlookImplementationApi
infix fun CharSequence.startsWith(prefixArray: Array<out CharSequence>): Boolean {
	return prefixArray.any { this.startsWith(it, false) }
}

/**判断当前字符串是否以指定后缀结尾。*/
@OutlookImplementationApi
infix fun CharSequence.endsWith(suffix: CharSequence): Boolean {
	return this.endsWith(suffix, false)
}

/**判断当前字符串是否以任意指定后缀结尾。*/
@OutlookImplementationApi
infix fun CharSequence.endsWith(suffixArray: Array<out CharSequence>): Boolean {
	return suffixArray.any { this.endsWith(it, false) }
}

/**判断当前字符串是否以指定前缀开头。忽略大小写。*/
@OutlookImplementationApi
infix fun CharSequence.startsWithIc(prefix: CharSequence): Boolean {
	return this.startsWith(prefix, true)
}

/**判断当前字符串是否以任意指定前缀开头。忽略大小写。*/
@OutlookImplementationApi
infix fun CharSequence.startsWithIc(prefixArray: Array<out CharSequence>): Boolean {
	return prefixArray.any { this.startsWith(it, true) }
}

/**判断当前字符串是否以指定后缀结尾。忽略大小写。*/
@OutlookImplementationApi
infix fun CharSequence.endsWithIc(suffix: CharSequence): Boolean {
	return this.endsWith(suffix, true)
}

/**判断当前字符串是否以指定后缀结尾。忽略大小写。*/
@OutlookImplementationApi
infix fun CharSequence.endsWithIc(suffixArray: Array<out CharSequence>): Boolean =
	suffixArray.any { this.endsWith(it, true) }


/**如果当前字符串不为空，则返回转换后的值。*/
@OutlookImplementationApi
inline fun <C : CharSequence> C.ifNotEmpty(transform: (C) -> C): C {
	return if(this.isEmpty()) this else transform(this)
}

/**如果当前字符串不为空白，则返回转换后的值。*/
@OutlookImplementationApi
inline fun <C : CharSequence> C.ifNotBlank(transform: (C) -> C): C {
	return if(this.isBlank()) this else transform(this)
}


/**分别平滑重复当前字符串中的字符到指定次数。*/
fun String.flatRepeat(n: Int): String {
	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }
	
	return this.map { it.toString().repeat(n) }.joinToString("")
}


/**将字符串中的指定字符替换成根据索引得到的字符。*/
fun String.replaceIndexed(oldChar: Char, ignoreCase: Boolean = false, newChar: (Int) -> Char): String {
	return buildString {
		val splitStrings = this@replaceIndexed.splitToSequence(oldChar, ignoreCase = ignoreCase)
		for((i, s) in splitStrings.withIndex()) {
			this.append(s)
			if(i < splitStrings.count() - 1) this.append(newChar(i))
		}
	}
}

/**将字符串中的指定值替换成根据索引得到的字符串。*/
fun String.replaceIndexed(oldValue: String, ignoreCase: Boolean = false, newValue: (Int) -> String): String {
	return buildString {
		val splitStrings = this@replaceIndexed.splitToSequence(oldValue, ignoreCase = ignoreCase)
		for((i, s) in splitStrings.withIndex()) {
			this.append(s)
			if(i < splitStrings.count() - 1) this.append(newValue(i))
		}
	}
}


/**
 * 基于[MessageFormat]格式化当前字符串。自动转义单引号。
 *
 * * 占位符格式：`{0}`, `{1,number}`, `{2,date,shot}`
 * * 示例：`"123{0}123{1}123".messageFormat("a","b")`
 */
fun String.messageFormat(vararg args: Any): String {
	return MessageFormat.format(this.replace("'", "''"), *args)
}

/**
 * 基于指定的占位符格式化当前字符串。
 *
 * * 占位符格式：`{}`, `{index}`, `${}`, `${index}`, `${name}`
 * * 示例：`"1{}2{}3".customFormat("{}","a","b")`
 * * 示例：`"1{0}2{1}3".customFormat("{index}","a","b")`
 * * 示例：`"1{a}2{b}3".customFormat("{name}","a" to "a", "b" to "b")`
 */
fun String.customFormat(placeholder: String, vararg args: Any): String {
	return when {
		"index" in placeholder -> {
			val (prefix, suffix) = placeholder.split("index", limit = 2).map { it.unescapeRegex() }
			this.replace("$prefix(\\d+)$suffix".toRegex()) { r ->
				args.getOrNull(r.groupValues[1].toInt())?.toString() ?: ""
			}
		}
		"name" in placeholder -> {
			val argPairs = args.map { it as Pair<*, *> }.toMap()
			val (prefix, suffix) = placeholder.split("name", limit = 2).map { it.unescapeRegex() }
			this.replace("$prefix(.*?)$suffix".toRegex()) { r ->
				argPairs[r.groupValues[1]]?.toString() ?: ""
			}
		}
		else -> this.replaceIndexed(placeholder) { args.getOrNull(it)?.toString() ?: "" }
	}
}


/**添加指定的前缀。可指定是否忽略空字符串，默认为true。*/
@OutlookImplementationApi
fun String.addPrefix(prefix: String, ignoreEmpty: Boolean = true): String {
	if(this.isEmpty() && ignoreEmpty) return this
	else if(this.startsWith(prefix)) return this
	return "$prefix$this"
}

/**添加指定的后缀。可指定是否忽略空字符串，默认为true。*/
@OutlookImplementationApi
fun String.addSuffix(suffix: String, ignoreEmpty: Boolean = true): String {
	if(this.isEmpty() && ignoreEmpty) return this
	else if(this.endsWith(suffix)) return this
	return "$this$suffix"
}

/**添加指定的前缀和后缀。可指定是否忽略空字符串，默认为true。*/
@OutlookImplementationApi
fun String.addSurrounding(prefix: String, suffix: String, ignoreEmpty: Boolean = true): String {
	if(this.isEmpty() && ignoreEmpty) return this
	else if(this.startsWith(prefix) && this.endsWith(suffix)) return this
	return "$prefix$this$suffix"
}


/**去除指定字符。*/
fun String.remove(oldChar: Char, ignoreCase: Boolean = false): String {
	return this.replace(oldChar.toString(), "", ignoreCase)
}

/**去除指定字符串。*/
fun String.remove(oldValue: String, ignoreCase: Boolean = false): String {
	return this.replace(oldValue, "", ignoreCase)
}

/**去除指定正则表达式的字符串。*/
fun String.remove(regex: Regex): String {
	return this.replace(regex, "")
}

/**去除所有空格。*/
fun String.removeWhiteSpace(): String {
	return this.replace(" ", "")
}

/**去除所有空白。*/
fun String.removeBlank(): String {
	return this.replace("\\s+".toRegex(), "")
}


private val escapes = arrayOf("\n", "\r", "\t", "\'", "\"")

private val unescapes = arrayOf("\\n", "\\r", "\\t", "\\'", "\\\"")

//Not for every scope
private val escapesInRegex = arrayOf(".", "^", "$", "[", "{", "(", "|", "?", "+")

/**转义当前字符串。例如，将`\\n`转换为`\n`。*/
fun String.escape(): String {
	return (escapes zip unescapes).fold(this) { str, (escape, unescape) ->
		str.replace(unescape, escape)
	}
}

/**反转义当前字符串。例如，将`\n`转换为`\\n`。*/
fun String.unescape(): String {
	return (escapes zip unescapes).fold(this) { str, (escape, unescape) ->
		str.replace(escape, unescape)
	}
}

private fun String.unescapeRegex(): String {
	return escapesInRegex.fold(this) { str, escape ->
		str.replace(escape, "\\$escape")
	}
}

private val quotes = arrayOf("\"", "'", "`")

/**使用双引号/单引号/反引号包围当前字符串。默认使用双引号。*/
fun String.wrapQuote(quote: String = "\""): String {
	if(quote !in quotes) return this
	return this.replace(quote, "\\$quote").addSurrounding(quote, quote, false)
}

/**去除当前字符串两侧的双引号/单引号/反引号。*/
fun String.unwrapQuote(): String {
	val quote = this.first().toString()
	if(quote !in quotes) return this
	return this.removeSurrounding(quote).replace("\\$quote", quote)
}


/**将第一个字符转为大写。*/
fun String.firstCharToUpperCase(): String {
	return if(this.isNotBlank()) this[0].toUpperCase() + this.substring(1) else this
}

/**将第一个字符转为大写，将其他字符皆转为小写。*/
fun String.firstCharToUpperCaseOnly(): String {
	return if(this.isNotBlank()) this[0].toUpperCase() + this.substring(1).toLowerCase() else this
}

/**将第一个字符转为小写。*/
fun String.firstCharToLowerCase(): String {
	return if(this.isNotBlank()) this[0].toLowerCase() + this.substring(1) else this
}

/**将第一个字符转为小写，将其他字符皆转为大写。*/
fun String.firstCharToLowerCaseOnly(): String {
	return if(this.isNotBlank()) this[0].toLowerCase() + this.substring(1).toUpperCase() else this
}


/**将当前字符串分割为单词列表，基于任意长度的指定的分割符，默认为空格。允许位于首尾的分隔符。*/
//TESTED
fun String.splitToWordList(delimiter: Char = ' '): List<String> {
	return this.split(delimiter).filterNotEmpty()
}

/**将当前字符串转化为以空格分割的单词组成的字符串，基于大小写边界。允许全大写的单词。*/
fun String.toWords(): String {
	return this.replace("\\B([A-Z][a-z_$])".toRegex(), " $1")
}


/**得到当前字母的字母显示格式。*/
val String.letterCase: LetterCase
	get() = enumValues<LetterCase>().first { this matches it.regex }

/**得到当前字符串的引用显示格式。*/
val String.referenceCase: ReferenceCase
	get() = enumValues<ReferenceCase>().first { this matches it.regex }

/**根据显示格式分割当前字符串。*/
fun String.splitBy(case: FormatCase): List<String> {
	return case.splitFunction(this)
}

/**根据显示格式连接当前字符串列表，组成完整字符串。*/
fun List<String>.joinBy(case: FormatCase): String {
	return case.joinFunction(this)
}

/**切换当前字符串的显示格式。*/
fun String.switchTo(fromCase: FormatCase, toCase: FormatCase): String {
	return this.splitBy(fromCase).joinBy(toCase)
}

/**转化当前字符串的显示格式。*/
fun String.to(case: FormatCase): String {
	return when(case) {
		is LetterCase -> this.splitBy(this.letterCase)
		is ReferenceCase -> this.splitBy(this.referenceCase)
		else -> throw IllegalArgumentException("Target case enum do not provide a actual way to get case from string.")
	}.joinBy(case)
}


/**根据指定的正则表达式，得到当前字符串的匹配结果分组对应的字符串列表。不包含索引0的分组，列表可能为空。*/
fun CharSequence.substrings(regex: Regex): List<String> {
	return regex.matchEntire(this)?.groupValues?.drop(1) ?: listOf()
}

/**根据以null分割的前置和后置的分隔符，按顺序分割字符串。不包含分隔符时，加入基于索引和剩余字符串得到的默认值列表中的对应索引的值。*/
fun String.substrings(vararg delimiters: String?, defaultValue: (Int, String) -> List<String>): List<String> =
	substringsOrElse(*delimiters) { index, str -> defaultValue(index, str).getOrEmpty(index) }

/**根据以null隔离的从前往后和从后往前的分隔符，按顺序分割字符串。不包含分隔符时，加入基于索引和剩余字符串得到的默认值。*/
inline fun String.substringsOrElse(vararg delimiters: String?, defaultValue: (Int, String) -> String): List<String> {
	require(delimiters.count { it == null } <= 1) { "[ERROR] There should be at most one null value as separator in delimiters." }
	
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

/**根据以null隔离的从前往后和从后往前的分隔符，按顺序分割字符串。不包含分隔符时，加入空字符串。*/
fun String.substringsOrEmpty(vararg delimiters: String?): List<String> =
	this.substringsOrElse(*delimiters) { _, _ -> "" }

/**根据以null隔离的从前往后和从后往前的分隔符，按顺序分割字符串。不包含分隔符时，加入剩余字符串。*/
fun String.substringsOrRemain(vararg delimiters: String?): List<String> =
	this.substringsOrElse(*delimiters) { _, str -> str }

////////////Convert operations

/**
 * 将当前字符串转为折行文本。
 * （去除所有换行符以及每行的首尾空白。）
 */
fun String.toBreakLineText(): String {
	return this.remove("\\s*\\n\\s*".toRegex())
}

/**
 * 将当前字符串转化为多行文本。可选相对于三引号的缩进，默认为0，-1为制表符。
 * （去除首尾空白行，然后基于尾随空白行的缩进，尝试去除每一行的缩进。）
 **/
fun String.toMultilineText(additionalIndentSize: Int = 0): String {
	val lines = this.lines()
	val additionalIndent = if(additionalIndentSize < 0) "\t" else " ".repeat(additionalIndentSize)
	val trimmedIndent = lines.last().ifNotBlank { "" } + additionalIndent
	if(trimmedIndent.isEmpty()) return this.trimIndent()
	return lines.dropBlank().dropLastBlank().joinToString("\n") { it.removePrefix(trimmedIndent) }
}


/**去空白后，将当前字符串转化为对应的整数，发生异常则转化为默认值[defaultValue]，默认为0。*/
@OutlookImplementationApi
fun String.toIntOrDefault(defaultValue: Int = 0): Int = this.toIntOrDefault(10, defaultValue)

/**去空白后，将当前字符串转化为对应的整数，发生异常则转化为默认值[defaultValue]，默认为0。可指定进制[radix]，默认为十进制。*/
@OutlookImplementationApi
fun String.toIntOrDefault(radix: Int = 10, defaultValue: Int = 0): Int = this.toIntOrNull(radix) ?: defaultValue

/**去空白后，将当前字符串转化为对应的长整数，发生异常则转化为默认值[defaultValue]，默认为0。*/
@OutlookImplementationApi
fun String.toLongOrDefault(defaultValue: Long = 0): Long = this.toLongOrDefault(10, defaultValue)

/**去空白后，将当前字符串转化为对应的长整数，发生异常则转化为默认值[defaultValue]，默认为0。可指定进制[radix]，默认为十进制。*/
@OutlookImplementationApi
fun String.toLongOrDefault(radix: Int = 10, defaultValue: Long = 0): Long = this.toLongOrNull(radix) ?: defaultValue

/**去空白后，将当前字符串转化为对应的单精度浮点数，发生异常则转化为默认值[defaultValue]，默认为0.0f。*/
@OutlookImplementationApi
fun String.toFloatOrDefault(defaultValue: Float = 0.0f): Float = this.toFloatOrNull() ?: defaultValue

/**去空白后，将当前字符串转化为对应的双精度浮点数，发生异常则转化为默认值[defaultValue]，默认为0.0。*/
@OutlookImplementationApi
fun String.toDoubleOrDefault(defaultValue: Double = 0.0): Double = this.toDoubleOrNull() ?: defaultValue


/**将当前字符串转化为对应的枚举常量。*/
@OutlookImplementationApi
inline fun <reified T : Enum<T>> String.toEnumValue(): T = enumValueOf(this)

/**将当前字符串转化为对应的枚举常量。*/
fun String.toEnumValue(type: Class<*>): Any {
	requireNotNull(type.isEnum) { "[ERROR] $type is not an enum class!" }
	return try {
		type.enumConstants.first { it.toString() == this }
	} catch(e: Exception) {
		logger.warn("No matched enum const found. Convert to default.")
		type.enumConstants.first()
	}
}


/**将当前字符串转化为文件。*/
inline fun String.toFile(): File = File(this)

/**将当前字符串转化为路径。*/
inline fun String.toPath(): Path = Path.of(this)

/**将当前字符串转化为地址。*/
inline fun String.toUrl(content: URL? = null, handler: URLStreamHandler? = null): URL = URL(content, this.trim(), handler)

/**将当前字符串转化为统一资源定位符。*/
inline fun String.toUri(): URI = URI.create(this)


/**将当前字符串转化为路径信息。*/
fun String.toPathInfo(): PathInfo {
	val path = this.replace("/", "\\")
	val rootPath = path.substringBefore("\\")
	val (fileDirectory, fileName) = path.substrings(null, "\\") { _, s -> listOf("", s) }
	val (fileShotName, fileExtension) = fileName.substrings(null, ".") { _, s -> listOf(s, "") }
	return PathInfo(path, rootPath, fileDirectory, fileName, fileShotName, fileExtension)
}

/**将当前字符串转化为地址信息。*/
fun String.toUrlInfo(): UrlInfo {
	val url = this
	val (fullPath, query) = url.substrings("?") { _, s -> listOf(s, "") }
	val (protocol, hostAndPort, path) = fullPath.substrings("://", "/") { _, s -> listOf("http", "", s) }
	val (host, port) = hostAndPort.substrings(":") { _, s -> listOf(s, "") }
	return UrlInfo(url, fullPath, protocol, host, port, path, query)
}

/**将当前字符串转化为查询参数映射。*/
internal fun String.toQueryParamMap(): QueryParamMap {
	//show warns here!!!
	val map = when {
		this.isEmpty() -> mapOf()
		else -> this.split("&").map { s -> s.split("=") }.groupBy({ it[0] }, { it[1] })
			.mapValues { (_, v) -> if(v.size == 1) v[0] else v }
	}
	return QueryParamMap(map)
}


/**将当前字符串转化为日期。*/
inline fun String.toDate(format: String): Date = SimpleDateFormat(format).parse(this)

/**将当前字符串转化为本地日期。*/
inline fun CharSequence.toLocalDate(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE): LocalDate =
	LocalDate.parse(this, formatter)

/**将当前字符串转化为本地日期时间。*/
inline fun CharSequence.toLocalDateTime(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME): LocalDateTime =
	LocalDateTime.parse(this, formatter)

/**将当前字符串转化为本地时间。*/
inline fun CharSequence.toLocalTime(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME): LocalDateTime =
	LocalDateTime.parse(this, formatter)


/**将当前字符串转化为颜色。*/
fun String.toColor(): Color {
	return when {
		//#333
		this startsWith "#" && this.length == 4 -> Color(this.substring(1).flatRepeat(2).toInt(16))
		//#3333
		this startsWith "#" && this.length == 5 -> Color(this.substring(1).flatRepeat(2).toInt(16), true)
		//#333333
		this startsWith "#" && this.length == 7 -> Color(this.substring(1).toInt(16))
		//#33333333
		this startsWith "#" && this.length == 9 -> Color(this.substring(1).toInt(16), true)
		//rgb(0,0,0)
		this startsWith "rgb(" -> {
			val (r, g, b) = this.substring(4, this.length - 1).split(",").map { it.trim().toInt(16) }
			Color(r, g, b)
		}
		//rgba(0,0,0,255)
		this startsWith "rgba(" -> {
			val (r, g, b, a) = this.substring(5, this.length - 1).split(",").map { it.trim().toInt(16) }
			Color(r, g, b, a)
		}
		//white || EXCEPTION
		else -> Color.getColor(this)
	}
}

/////////////Operator overrides

/**@see kotlin.text.repeat*/
@OutlookImplementationApi
inline operator fun String.times(n: Int): String = this.repeat(n)

/**@see kotlin.text.chunked*/
@OutlookImplementationApi
inline operator fun String.div(n: Int): List<String> = this.chunked(n)

/**@see kotlin.text.substring*/
@OutlookImplementationApi
inline operator fun String.get(indexRange: IntRange): String = this.substring(indexRange)
