@file:Suppress("UNUSED_PARAMETER")

package com.windea.breezeframework.core.extensions

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
infix fun String?.equalsIc(other: String?): Boolean {
	return this.equals(other, true)
}

/**判断字符串是否相等。忽略显示格式[LetterCase]。*/
infix fun String?.equalsIsc(other: String?): Boolean {
	if(this == other) return true
	return this != null && other != null && this.switchCase(this.getCase(), other.getCase()) == other
}


/**判断当前字符串中的任意字符是否被另一字符串包含。*/
infix fun String.anyIn(other: String): Boolean = this.any { it in other }

/**判断当前字符串是否与另一字符串相像。即，判断是否存在共同的以空格分隔的单词。*/
infix fun String.like(other: String): Boolean = this.splitByCase(LetterCase.WhiteSpaceCase) anyIn other.splitByCase(LetterCase.WhiteSpaceCase)


/**判断当前字符串是否以指定前缀开头。*/
infix fun CharSequence.startsWith(prefix: CharSequence): Boolean {
	return this.startsWith(prefix, false)
}

/**判断当前字符串是否以任意指定前缀开头。*/
infix fun CharSequence.startsWith(prefixArray: Array<out CharSequence>): Boolean {
	return prefixArray.any { this.startsWith(it, false) }
}

/**判断当前字符串是否以指定后缀结尾。*/
infix fun CharSequence.endsWith(suffix: CharSequence): Boolean {
	return this.endsWith(suffix, false)
}

/**判断当前字符串是否以任意指定后缀结尾。*/
infix fun CharSequence.endsWith(suffixArray: Array<out CharSequence>): Boolean {
	return suffixArray.any { this.endsWith(it, false) }
}

/**判断当前字符串是否以指定前缀开头。忽略大小写。*/
infix fun CharSequence.startsWithIc(prefix: CharSequence): Boolean {
	return this.startsWith(prefix, true)
}

/**判断当前字符串是否以任意指定前缀开头。忽略大小写。*/
infix fun CharSequence.startsWithIc(prefixArray: Array<out CharSequence>): Boolean {
	return prefixArray.any { this.startsWith(it, true) }
}

/**判断当前字符串是否以指定后缀结尾。忽略大小写。*/
infix fun CharSequence.endsWithIc(suffix: CharSequence): Boolean {
	return this.endsWith(suffix, true)
}

/**判断当前字符串是否以指定后缀结尾。忽略大小写。*/
infix fun CharSequence.endsWithIc(suffixArray: Array<out CharSequence>): Boolean =
	suffixArray.any { this.endsWith(it, true) }


private var enableCrossLine = false
private var prepareCrossLineSurroundingWith = false

/**执行跨行操作。*/
fun <R> List<String>.crossLine(block: (List<String>) -> R): R {
	enableCrossLine = true
	return this.let(block).also {
		enableCrossLine = false
		prepareCrossLineSurroundingWith = false
	}
}

/**执行跨行操作。*/
fun <R> Sequence<String>.crossLine(block: (Sequence<String>) -> R): R {
	enableCrossLine = true
	return this.let(block).also {
		enableCrossLine = false
		prepareCrossLineSurroundingWith = false
	}
}

/**判断当前行是否在指定的跨行前后缀之间。在[crossLine]之中调用这个方法。*/
fun String.crossLineSurroundsWith(prefix: String, suffix: String, ignoreCase: Boolean = false): Boolean {
	check(enableCrossLine) { "[ERROR] Cross line operations are not enabled. They are enabled in crossLine { ... } block." }
	
	val isBeginBound = this.contains(prefix, ignoreCase)
	val isEndBound = this.contains(suffix, ignoreCase)
	if(isBeginBound && !prepareCrossLineSurroundingWith) prepareCrossLineSurroundingWith = true
	if(isEndBound) prepareCrossLineSurroundingWith = false
	return !isBeginBound && prepareCrossLineSurroundingWith
}

/**判断当前行是否在指定的跨行前后缀之间。在[crossLine]之中调用这个方法。*/
fun String.crossLineSurroundsWith(delimiter: String, ignoreCase: Boolean = false): Boolean =
	this.crossLineSurroundsWith(delimiter, delimiter, ignoreCase)


/**如果当前字符串不为空，则返回转换后的值。推荐仅用于长链式方法调用。*/
inline fun <C : CharSequence> C.ifNotEmpty(transform: (C) -> C): C {
	return if(this.isEmpty()) this else transform(this)
}

/**如果当前字符串不为空白，则返回转换后的值。推荐仅用于长链式方法调用。*/
inline fun <C : CharSequence> C.ifNotBlank(transform: (C) -> C): C {
	return if(this.isBlank()) this else transform(this)
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
 * 占位符形如：`{0}`, `{1,number}`, `{2,date,shot}`。
 */
fun String.messageFormat(vararg args: Any): String {
	return MessageFormat.format(this.replace("'", "''"), *args)
}

/**
 * 基于指定的占位符格式化当前字符串。
 *
 * 占位符形如：`{}`, `{index}`, `${}`, `${index}`。
 */
fun String.customFormat(placeholder: String, vararg args: Any): String {
	return when {
		"index" in placeholder -> {
			val (prefix, suffix) = placeholder.split("index")
			this.splitToSequence(prefix, suffix).map { s ->
				s.replace("^(\\d+)$".toRegex()) { r ->
					args.getOrNull(r.groupValues[1].toInt())?.toString() ?: r.groupValues[0]
				}
			}.joinToString("")
		}
		else -> this.replaceIndexed(placeholder) { args.getOrNull(it)?.toString() ?: "" }
	}
}


/**根据指定的前缀[prefix]和后缀[suffix]，包围字符串，可指定是否忽略空字符串[ignoreEmpty]，默认为true。*/
fun String.surrounding(prefix: String, suffix: String, ignoreEmpty: Boolean = true): String {
	return if(ignoreEmpty && this.isEmpty()) "" else prefix + this + suffix
}

/**根据指定的前后缀[delimiter]，包围字符串，可指定是否忽略空字符串[ignoreEmpty]，默认为true。*/
fun String.surrounding(delimiter: String, ignoreEmpty: Boolean = true): String =
	surrounding(delimiter, delimiter, ignoreEmpty)


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


private val quotes = arrayOf("\"", "'", "`")

/**使用双引号/单引号/反引号包围当前字符串。默认使用双引号。*/
fun String.wrapQuote(quote: String = "\""): String {
	if(quote !in quotes) return this
	return this.replace(quote, "\\$quote").surrounding(quote, false)
}

/**去除当前字符串两侧的双引号/单引号/反引号。*/
fun String.unwrapQuote(): String {
	val quote = this.first().toString()
	if(quote !in quotes) return this
	return this.removeSurrounding(quote).replace("\\$quote", quote)
}


/**将第一个字符转为大写。*/
fun String.firstCharToUpperCase(): String {
	return this[0].toUpperCase() + this.substring(1, this.length)
}

/**将第一个字符转为大写，将其他字符皆转为小写。*/
fun String.firstCharToUpperCaseOnly(): String {
	return this[0].toUpperCase() + this.substring(1, this.length).toLowerCase()
}

/**将第一个字符转为小写。*/
fun String.firstCharToLowerCase(): String {
	return this[0].toLowerCase() + this.substring(1, this.length)
}

/**将第一个字符转为小写，将其他字符皆转为小写。*/
fun String.firstCharToLowerCaseOnly(): String {
	return this[0].toLowerCase() + this.substring(1, this.length).toUpperCase()
}


/**得到当前字母的显示格式。*/
fun String.getCase(): LetterCase {
	return when {
		this matches LetterCase.CamelCase.regex -> LetterCase.CamelCase
		this matches LetterCase.PascalCase.regex -> LetterCase.PascalCase
		this matches LetterCase.SnakeCase.regex -> LetterCase.SnakeCase
		this matches LetterCase.ScreamingSnakeCase.regex -> LetterCase.ScreamingSnakeCase
		this matches LetterCase.KebabCase.regex -> LetterCase.KebabCase
		this matches LetterCase.KebabUpperCase.regex -> LetterCase.KebabUpperCase
		this matches LetterCase.DotCase.regex -> LetterCase.DotCase
		this matches LetterCase.WhiteSpaceCase.regex -> LetterCase.WhiteSpaceCase
		else -> LetterCase.Unknown
	}
}

/**切换当前字母的显示格式。*/
fun String.switchCase(fromCase: LetterCase, toCase: LetterCase): String {
	return this.splitByCase(fromCase).joinByCase(toCase)
}

/**根据显示格式分割当前字符串。*/
fun String.splitByCase(case: LetterCase): List<String> {
	return when(case) {
		LetterCase.Unknown -> listOf(this)
		LetterCase.CamelCase -> this.firstCharToUpperCase().splitWordByWhiteSpace().split(" ")
		LetterCase.PascalCase -> this.splitWordByWhiteSpace().split(" ")
		LetterCase.SnakeCase -> this.split("_")
		LetterCase.ScreamingSnakeCase -> this.split("_")
		LetterCase.KebabCase -> this.split("-")
		LetterCase.KebabUpperCase -> this.split("-")
		LetterCase.DotCase -> this.split(".")
		LetterCase.WhiteSpaceCase -> this.split("\\s+".toRegex())
	}
}

/**根据显示格式连接当前字符串列表。*/
fun List<String>.joinByCase(case: LetterCase): String {
	return when(case) {
		LetterCase.Unknown -> this.first()
		LetterCase.CamelCase -> this.joinToString("") { it.firstCharToUpperCaseOnly() }.firstCharToLowerCase()
		LetterCase.PascalCase -> this.joinToString("") { it.firstCharToUpperCaseOnly() }
		LetterCase.SnakeCase -> this.joinToString("_") { it.toLowerCase() }
		LetterCase.ScreamingSnakeCase -> this.joinToString("_") { it.toUpperCase() }
		LetterCase.KebabCase -> this.joinToString("-") { it.toLowerCase() }
		LetterCase.KebabUpperCase -> this.joinToString("-") { it.toUpperCase() }
		LetterCase.DotCase -> this.joinToString(".")
		LetterCase.WhiteSpaceCase -> this.joinToString(" ")
	}
}

private fun String.splitWordByWhiteSpace(): String {
	return this.replace("\\B([A-Z][a-z_$])".toRegex(), " $1")
}


/**得到当前字符串的路径显示格式。*/
fun String.getPathCase(): PathCase {
	return when {
		this matches PathCase.WindowsPath.regex -> PathCase.WindowsPath
		this matches PathCase.UnixPath.regex -> PathCase.UnixPath
		this matches PathCase.ReferencePath.regex -> PathCase.ReferencePath
		this matches PathCase.JsonPath.regex -> PathCase.JsonPath
		else -> PathCase.Unknown
	}
}

/**切换当前字符串的路径显示格式。*/
fun String.switchPathCase(fromCase: PathCase, toCase: PathCase): String {
	return this.splitByPathCase(fromCase).joinByPathCase(toCase)
}

/**根据路径显示格式分割当前字符串，组成完整路径。*/
fun String.splitByPathCase(case: PathCase): List<String> {
	val fixedPath = this.removePrefix(".").removePrefix(".").removePrefix("#")
	return when(case) {
		PathCase.Unknown -> listOf(this)
		PathCase.WindowsPath -> fixedPath.split("\\").filterNotEmpty()
		PathCase.UnixPath -> fixedPath.split("/").filterNotEmpty()
		PathCase.ReferencePath -> fixedPath.split("[", "]", ".").filterNotEmpty()
		PathCase.JsonPath -> fixedPath.split("/").filterNotEmpty()
	}
}

/**根据路径显示格式连接当前字符串列表，组成完整路径。可指定是否为绝对路径，默认为true。*/
fun List<String>.joinByPathCase(case: PathCase, isAbsolutePath: Boolean = true): String {
	return when(case) {
		PathCase.Unknown -> this.first()
		PathCase.WindowsPath -> this.joinToString("\\").let { if(isAbsolutePath) "\\$it" else it }
		PathCase.UnixPath -> this.joinToString("/").let { if(isAbsolutePath) "/$it" else it }
		PathCase.ReferencePath -> this.joinToString(".").replace("^(\\d+)$|\\.?(\\d+)\\.?".toRegex(), "[$1$2]")
		PathCase.JsonPath -> this.joinToString("/", "/")
	}
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


/**将当前字符串转为折行文本。（去除所有换行符以及每行的首尾空白。）*/
fun String.toBreakLineText(): String {
	return this.remove("\\s*\\n\\s*".toRegex())
}

/**将当前字符串转化为多行文本。（去除首尾空白行，然后基于尾随空白行的缩进，尝试去除每一行的缩进。）*/
fun String.toMultilineText(): String {
	val lines = this.lines()
	val trimmedIndent = lines.last().ifBlank { "" }
	if(trimmedIndent.isEmpty()) return this.trimIndent()
	return lines.dropBlank().dropLastBlank().joinToString("\n") { it.removePrefix(trimmedIndent) }
}


/**去空白后，将当前字符串转化为对应的整数，发生异常则转化为默认值[defaultValue]，默认为0。*/
fun String.toIntOrDefault(defaultValue: Int = 0): Int = this.toIntOrDefault(10, defaultValue)

/**去空白后，将当前字符串转化为对应的整数，发生异常则转化为默认值[defaultValue]，默认为0。可指定进制[radix]，默认为十进制。*/
fun String.toIntOrDefault(radix: Int = 10, defaultValue: Int = 0): Int = this.toIntOrNull(radix) ?: defaultValue

/**去空白后，将当前字符串转化为对应的长整数，发生异常则转化为默认值[defaultValue]，默认为0。*/
fun String.toLongOrDefault(defaultValue: Long = 0): Long = this.toLongOrDefault(10, defaultValue)

/**去空白后，将当前字符串转化为对应的长整数，发生异常则转化为默认值[defaultValue]，默认为0。可指定进制[radix]，默认为十进制。*/
fun String.toLongOrDefault(radix: Int = 10, defaultValue: Long = 0): Long = this.toLongOrNull(radix) ?: defaultValue

/**去空白后，将当前字符串转化为对应的单精度浮点数，发生异常则转化为默认值[defaultValue]，默认为0.0f。*/
fun String.toFloatOrDefault(defaultValue: Float = 0.0f): Float = this.toFloatOrNull() ?: defaultValue

/**去空白后，将当前字符串转化为对应的双精度浮点数，发生异常则转化为默认值[defaultValue]，默认为0.0。*/
fun String.toDoubleOrDefault(defaultValue: Double = 0.0): Double = this.toDoubleOrNull() ?: defaultValue


/**将当前字符串转化为对应的枚举常量。*/
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
fun String.toFile(): File = File(this)

/**将当前字符串转化为路径。*/
fun String.toPath(): Path = Path.of(this)

/**将当前字符串转化为地址。*/
fun String.toUrl(content: URL? = null, handler: URLStreamHandler? = null): URL = URL(content, this.trim(), handler)

/**将当前字符串转化为统一资源定位符。*/
fun String.toUri(): URI = URI.create(this)


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
	val map = when {
		this.isEmpty() -> mapOf()
		else -> this.split("&").map { s -> s.split("=") }.groupBy({ it[0] }, { it[1] })
			.mapValues { (_, v) -> if(v.size == 1) v[0] else v }
	}
	return QueryParamMap(map)
}


/**将当前字符串转化为日期。*/
fun String.toDate(format: String): Date = SimpleDateFormat(format).parse(this)

/**将当前字符串转化为本地日期。*/
fun CharSequence.toLocalDate(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE): LocalDate =
	LocalDate.parse(this, formatter)

/**将当前字符串转化为本地日期时间。*/
fun CharSequence.toLocalDateTime(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME): LocalDateTime =
	LocalDateTime.parse(this, formatter)

/**将当前字符串转化为本地时间。*/
fun CharSequence.toLocalTime(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME): LocalDateTime =
	LocalDateTime.parse(this, formatter)


/**将当前字符串转化为颜色。*/
fun String.toColor(): Color {
	return when {
		//#333
		this startsWith "#" && this.length == 4 -> Color(this.substring(1).mapPerCharRepeat(2).toInt(16))
		//#3333
		this startsWith "#" && this.length == 5 -> Color(this.substring(1).mapPerCharRepeat(2).toInt(16), true)
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

private fun String.mapPerCharRepeat(n: Int): String {
	return this.chunked(1).joinToString("") { it.repeat(n) }
}


/**@see kotlin.text.repeat*/
operator fun String.times(n: Int): String = this.repeat(n)

/**@see kotlin.text.chunked*/
operator fun String.div(n: Int): List<String> = this.chunked(n)

/**@see kotlin.text.substring*/
operator fun String.get(indexRange: IntRange): String = this.substring(indexRange)
