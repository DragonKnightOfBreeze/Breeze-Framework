@file:Suppress("UNUSED_PARAMETER", "NOTHING_TO_INLINE", "DuplicatedCode")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.enums.core.*
import java.io.*
import java.net.*
import java.nio.file.*
import java.text.*
import java.time.*
import java.time.format.*
import java.util.*

//region operator overrides
/**@see kotlin.text.slice*/
inline operator fun String.get(indexRange: IntRange): String = this.slice(indexRange)

/**@see com.windea.breezeframework.core.extensions.remove*/
inline operator fun String.minus(other: Any?): String = if(other == null) this else this.remove(other.toString())

/**@see kotlin.text.repeat*/
inline operator fun String.times(n: Int): String = this.repeat(n)

/**@see kotlin.text.chunked*/
inline operator fun String.div(n: Int): List<String> = this.chunked(n)
//endregion

//region common functions
/**逐行连接两个字符串。返回的字符串的长度为两者长度中的较大值。*/
infix fun String.lineConcat(other: String): String {
	val lines = this.lines()
	val otherLines = other.lines()
	return when {
		lines.size <= otherLines.size -> lines.fillEnd(otherLines.size, "") zip otherLines
		else -> lines zip otherLines.fillEnd(lines.size, "")
	}.joinToString("\n") { (a, b) -> "$a$b" }
}

/**判断字符串是否相等。忽略大小写。*/
infix fun String?.equalsIc(other: String?): Boolean {
	return this.equals(other, true)
}

/**判断字符串是否相等。忽略显示格式[LetterCase]。*/
infix fun String?.equalsIlc(other: String?): Boolean {
	if(this == other) return true
	return this != null && other != null && this.switchCase(this.letterCase, other.letterCase) == other
}


/**判断当前字符串中的任意字符是否被另一字符串包含。*/
infix fun String.anyIn(other: String): Boolean = this.any { it in other }


/**判断当前字符串是否以指定前缀开头。*/
inline infix fun CharSequence.startsWith(prefix: CharSequence): Boolean {
	return this.startsWith(prefix, false)
}

/**判断当前字符串是否以任意指定前缀开头。*/
inline infix fun CharSequence.startsWith(prefixArray: Array<out CharSequence>): Boolean {
	return prefixArray.any { this.startsWith(it, false) }
}

/**判断当前字符串是否以指定后缀结尾。*/
inline infix fun CharSequence.endsWith(suffix: CharSequence): Boolean {
	return this.endsWith(suffix, false)
}

/**判断当前字符串是否以任意指定后缀结尾。*/
inline infix fun CharSequence.endsWith(suffixArray: Array<out CharSequence>): Boolean {
	return suffixArray.any { this.endsWith(it, false) }
}

/**判断当前字符串是否以指定前缀开头。忽略大小写。*/
inline infix fun CharSequence.startsWithIc(prefix: CharSequence): Boolean {
	return this.startsWith(prefix, true)
}

/**判断当前字符串是否以任意指定前缀开头。忽略大小写。*/
inline infix fun CharSequence.startsWithIc(prefixArray: Array<out CharSequence>): Boolean {
	return prefixArray.any { this.startsWith(it, true) }
}

/**判断当前字符串是否以指定后缀结尾。忽略大小写。*/
inline infix fun CharSequence.endsWithIc(suffix: CharSequence): Boolean {
	return this.endsWith(suffix, true)
}

/**判断当前字符串是否以指定后缀结尾。忽略大小写。*/
inline infix fun CharSequence.endsWithIc(suffixArray: Array<out CharSequence>): Boolean =
	suffixArray.any { this.endsWith(it, true) }


/**判断当前字符串是否仅包含字母，且不为空/空白字符串。*/
fun CharSequence.isAlphabetic() = this matches "[a-zA-Z]+".toRegex()

/**判断当前字符串是否仅包含数字，且不为空/空白字符串。*/
fun CharSequence.isNumeric() = this matches "[1-9]+".toRegex()

/**判断当前字符串是否仅包含字母、数字和下划线，且不为空/空白字符串。*/
fun CharSequence.isAlphanumeric() = this matches "[1-9a-zA-Z_]+".toRegex()


/**如果当前字符串不为空，则返回转换后的值。*/
inline fun <C : CharSequence> C.ifNotEmpty(transform: (C) -> C): C {
	return if(this.isEmpty()) this else transform(this)
}

/**如果当前字符串不为空白，则返回转换后的值。*/
inline fun <C : CharSequence> C.ifNotBlank(transform: (C) -> C): C {
	return if(this.isBlank()) this else transform(this)
}


/**分别平滑重复当前字符串中的字符到指定次数。*/
fun String.flatRepeat(n: Int): String {
	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }
	
	return this.map { it.toString().repeat(n) }.joinToString("")
}


/**根据一组字符元组，将字符串中的对应字符替换成对应的替换后字符。*/
@JvmName("replaceAllByChar")
fun String.replaceAll(vararg charPairs: Pair<Char, Char>, ignoreCase: Boolean = false): String {
	return charPairs.fold(this) { str, (oldChar, newChar) -> str.replace(oldChar, newChar, ignoreCase) }
}

/**根据一组字符元组，将字符串中的对应字符替换成对应的替换后字符。*/
@JvmName("replaceAllByChar")
fun String.replaceAll(charPairs: List<Pair<Char, Char>>, ignoreCase: Boolean = false): String {
	return charPairs.fold(this) { str, (oldChar, newChar) -> str.replace(oldChar, newChar, ignoreCase) }
}

/**根据一组字符元组，将字符串中的对应字符串替换成对应的替换后字符串。*/
@JvmName("replaceAllByString")
fun String.replaceAll(vararg valuePairs: Pair<String, String>, ignoreCase: Boolean = false): String {
	return valuePairs.fold(this) { str, (oldValue, newValue) -> str.replace(oldValue, newValue, ignoreCase) }
}

/**根据一组字符元组，将字符串中的对应字符串替换成对应的替换后字符串。*/
@JvmName("replaceAllByString")
fun String.replaceAll(valuePairs: List<Pair<String, String>>, ignoreCase: Boolean = false): String {
	return valuePairs.fold(this) { str, (oldValue, newValue) -> str.replace(oldValue, newValue, ignoreCase) }
}

/**根据一组正则表达式-替换字符串元组，将字符串中的对应字符串替换成对应的替换后字符串。*/
@JvmName("replaceAllByRegex")
fun String.replaceAll(vararg regexReplacementPairs: Pair<Regex, String>): String {
	return regexReplacementPairs.fold(this) { str, (regex, replacement) -> str.replace(regex, replacement) }
}

/**根据一组正则表达式-替换字符串元组，将字符串中的对应字符串替换成对应的替换后字符串。*/
@JvmName("replaceAllByRegex")
fun String.replaceAll(regexReplacementPairs: List<Pair<Regex, String>>): String {
	return regexReplacementPairs.fold(this) { str, (regex, replacement) -> str.replace(regex, replacement) }
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
fun String.messageFormat(vararg args: Any?): String {
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
fun String.customFormat(placeholder: String, vararg args: Any?): String {
	return when {
		"index" in placeholder -> {
			val (prefix, suffix) = placeholder.split("index", limit = 2).map { it.escape(EscapeType.Regex) }
			this.replace("""$prefix(\d+)$suffix""".toRegex()) { r ->
				args.getOrNull(r.groupValues[1].toInt())?.toString().orEmpty()
			}
		}
		"name" in placeholder -> {
			val argPairs = args.map { it as Pair<*, *> }.toMap()
			val (prefix, suffix) = placeholder.split("name", limit = 2).map { it.escape(EscapeType.Regex) }
			this.replace("""$prefix([a-zA-Z_$]+)$suffix""".toRegex()) { r ->
				argPairs[r.groupValues[1]]?.toString().orEmpty()
			}
		}
		else -> this.replaceIndexed(placeholder) { args.getOrNull(it)?.toString().orEmpty() }
	}
}


/**添加指定的前缀。当已存在时不添加。可指定是否忽略空字符串，默认为true。*/
fun String.addPrefix(prefix: CharSequence, ignoreEmpty: Boolean = true): String {
	if(this.isEmpty() && ignoreEmpty) return this
	else if(this.startsWith(prefix)) return this
	return "$prefix$this"
}

/**添加指定的后缀。当已存在时不添加。可指定是否忽略空字符串，默认为true。*/
fun String.addSuffix(suffix: CharSequence, ignoreEmpty: Boolean = true): String {
	if(this.isEmpty() && ignoreEmpty) return this
	else if(this.endsWith(suffix)) return this
	return "$this$suffix"
}

/**添加指定的前缀和后缀。当已存在时不添加。可指定是否忽略空字符串，默认为true。*/
fun String.addSurrounding(prefix: CharSequence, suffix: CharSequence, ignoreEmpty: Boolean = true): String {
	if(this.isEmpty() && ignoreEmpty) return this
	else if(this.startsWith(prefix) && this.endsWith(suffix)) return this
	return "$prefix$this$suffix"
}

/**添加指定的前后缀。当已存在时不添加。可指定是否忽略空字符串，默认为true。*/
fun String.addSurrounding(delimiter: CharSequence, ignoreEmpty: Boolean): String {
	return this.addSurrounding(delimiter, delimiter, ignoreEmpty)
}


/**去除指定字符。*/
inline fun String.remove(oldChar: Char, ignoreCase: Boolean = false): String {
	return this.replace(oldChar.toString(), "", ignoreCase)
}

/**去除指定字符串。*/
inline fun String.remove(oldValue: String, ignoreCase: Boolean = false): String {
	return this.replace(oldValue, "", ignoreCase)
}

/**去除指定正则表达式的字符串。*/
inline fun String.remove(regex: Regex): String {
	return this.replace(regex, "")
}

/**去除所有空格。*/
inline fun String.removeWhiteSpace(): String {
	return this.replace(" ", "")
}

/**去除所有空白。*/
inline fun String.removeBlank(): String {
	return this.replace("""\s+""".toRegex(), "")
}


/**将第一个字符转为大写。*/
fun String.firstCharToUpperCase(): String {
	return if(this.isNotBlank()) this[0].toUpperCase() + this.substring(1) else this
}

/**将第一个字符转为小写。*/
fun String.firstCharToLowerCase(): String {
	return if(this.isNotBlank()) this[0].toLowerCase() + this.substring(1) else this
}


/**将当前字符串分割为单词列表，基于任意长度的指定的分割符，默认为空格。允许位于首尾的分隔符。*/
fun String.splitToWordList(delimiter: Char = ' '): List<String> {
	return this.split(delimiter).filterNotEmpty()
}

/**将当前字符串转化为以空格分割的单词组成的字符串，基于大小写边界。允许全大写的单词。*/
fun String.toWords(): String {
	return this.replace("""\B([A-Z][a-z])""".toRegex(), " $1")
}


/**根据指定的正则表达式，得到当前字符串的匹配结果分组对应的字符串列表。不包含索引0的分组，列表可能为空。*/
fun CharSequence.substrings(regex: Regex): List<String> {
	return regex.matchEntire(this)?.groupValues?.drop(1) ?: listOf()
}

/**
 * 根据以null隔离的从前往后和从后往前的分隔符，按顺序分割字符串。
 * 不包含分隔符时，加入基于索引和剩余字符串得到的默认值列表中的对应索引的值。
 */
fun String.substrings(vararg delimiters: String?, defaultValue: (Int, String) -> List<String>): List<String> =
	substringsOrElse(*delimiters) { index, str -> defaultValue(index, str).getOrEmpty(index) }

/**
 * 根据以null隔离的从前往后和从后往前的分隔符，按顺序分割字符串。
 * 不包含分隔符时，加入基于索引和剩余字符串得到的默认值。
 */
fun String.substringsOrElse(vararg delimiters: String?, defaultValue: (Int, String) -> String): List<String> {
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


/**为当前字符串的每行添加缩进，并为第一行提供一个指定的前缀。*/
fun String.prependIndent(indent: String = "    ", prefix: String): String {
	return prefix + this.prependIndent(indent).drop(prefix.length)
}
//endregion

//region specific extensions
private val quoteChars = charArrayOf('\"', '\'', '`')

/**使用指定的引号包围当前字符串。同时转义其中的对应引号。默认使用双引号。*/
fun String.wrapQuote(quote: Char): String {
	require(quote in quoteChars) { "Invalid quote char: $quote." }
	
	return "$quote${this.ifNotEmpty { it.replace(quote.toString(), "\\$quote") }}$quote"
}

/**尝试去除当前字符串两侧的引号。同时反转义其中的对应引号。*/
fun String.unwrapQuote(): String {
	val quote = this.first()
	if(quote !in quoteChars) return this
	return this.removeSurrounding(quote.toString()).ifNotEmpty { it.replace("\\$quote", quote.toString()) }
}


/**根据指定的转义类型，转义当前字符串。默认采用Kotlin的转义。*/
fun String.escape(type: EscapeType = EscapeType.Kotlin): String {
	//"\" should be escaped first
	val tempString = if(type.escapeBackslash) this.replace("\\", "\\\\") else this
	return tempString.replaceAll(type.escapeStrings zip type.escapedStrings)
}

/**根据指定的转义类型，反转义当前字符串。默认采用Kotlin的转义。*/
fun String.unescape(type: EscapeType = EscapeType.Kotlin): String {
	//"\" should be unescaped last
	val tempString = this.replaceAll(type.escapedStrings zip type.escapeStrings)
	return if(type.escapeBackslash) tempString.replace("\\\\", "\\") else this
}


/**得到当前字母的字母显示格式。*/
val String.letterCase: LetterCase
	get() = enumValues<LetterCase>().first { this matches it.regex }

/**得到当前字符串的引用显示格式。*/
val String.referenceCase: ReferenceCase
	get() = enumValues<ReferenceCase>().first { this matches it.regex }

/**切换当前字符串的显示格式。*/
fun String.switchCase(fromCase: FormatCase, toCase: FormatCase): String {
	return this.splitBy(fromCase).joinBy(toCase)
}

/**根据显示格式分割当前字符串。*/
fun String.splitBy(case: FormatCase): List<String> {
	return case.splitFunction(this)
}

/**根据显示格式连接当前字符串列表，组成完整字符串。*/
fun List<String>.joinBy(case: FormatCase): String {
	return case.joinFunction(this)
}

/**切换当前字符串的显示格式。根据目标显示格式的类型推导当前的显示格式。某些显示格式需要显式指定。*/
fun String.switchTo(case: FormatCase): String {
	return when(case) {
		is LetterCase -> this.splitBy(this.letterCase)
		is ReferenceCase -> this.splitBy(this.referenceCase)
		else -> throw IllegalArgumentException("Target format case do not provide an actual way to get from a string.")
	}.joinBy(case)
}


/**逐行向左对齐当前字符串，并保证每行长度一致，用指定字符填充。默认为空格。*/
fun String.alignLeft(padChar: Char = ' '): String {
	val lines = this.lines()
	if(lines.size <= 1) return this
	val maxLength = lines.map { it.length }.max()!!
	return lines.joinToString("\n") { it.trimStart().padEnd(maxLength, padChar) }
}

/**逐行中心对齐当前字符串，并保证每行长度一致，用指定字符填充。默认为空格。*/
fun String.alignCenter(padChar: Char = ' '): String {
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

/**逐行向右对齐当前字符串，并保证每行长度一致，用指定字符填充。默认为空格。*/
fun String.alignRight(padChar: Char = ' '): String {
	val lines = this.lines()
	if(lines.size <= 1) return this
	val maxLength = lines.map { it.length }.max()!!
	return lines.joinToString("\n") { it.trimEnd().padStart(maxLength, padChar) }
}
//endregion

//region convert extensions
/**@see com.windea.breezeframework.core.extensions.toInlineText*/
inline val String.inline get() = this.toInlineText()

/**@see kotlin.text.trimIndent*/
inline val String.multiline get() = this.trimIndent()

/**
 * 将当前字符串转为单行文本。
 *
 * 去除所有换行符以及每行的首尾空白。
 */
fun String.toInlineText(): String {
	return this.remove("""\s*\n\s*""".toRegex())
}

/**
 * 将当前字符串转化为多行文本。可选相对缩进，默认为0。
 *
 * 去除首尾空白行，然后基于尾随空白行的缩进，尝试去除每一行的缩进。
 **/
fun String.toMultilineText(relativeIndentSize: Int = 0): String {
	require(relativeIndentSize in -2..8) { "Relative indent size is not in range -2..8." }
	
	val lines = this.lines()
	val additionalIndent = if(relativeIndentSize > 0) " " * relativeIndentSize else "\t" * relativeIndentSize
	val trimmedIndent = lines.last().ifNotBlank { "" } + additionalIndent
	return if(trimmedIndent.isEmpty()) this.trimIndent()
	else lines.dropBlank().dropLastBlank().joinToString("\n") { it.removePrefix(trimmedIndent) }
}


/**转化为指定的数字类型。*/
inline fun <reified T : Number> String.to(): T {
	//performance note: approach to 1/5
	return when(val typeName = T::class.java.name) {
		"java.lang.Integer" -> this.toInt() as T
		"java.lang.Long" -> this.toLong() as T
		"java.lang.Float" -> this.toFloat() as T
		"java.lang.Double" -> this.toDouble() as T
		"java.lang.Byte" -> this.toByte() as T
		"java.lang.Short" -> this.toShort() as T
		"java.math.BigInteger" -> this.toBigInteger() as T
		"java.math.BigDecimal" -> this.toBigDecimal() as T
		else -> throw UnsupportedOperationException("Unsupported reified type parameter '$typeName'.")
	}
}


/**将当前字符串转化为对应的枚举值。如果转化失败，则抛出异常。*/
inline fun <reified T : Enum<T>> String.toEnumValue(ignoreCase: Boolean = false): T {
	return enumValues<T>().first { it.name.equals(this, ignoreCase) }
}

/**将当前字符串转化为对应的枚举值。如果转化失败，则返回null。*/
inline fun <reified T : Enum<T>> String.toEnumValueOrNull(ignoreCase: Boolean = false): T? {
	return enumValues<T>().firstOrNull { it.name.equals(this, ignoreCase) }
}

/**将当前字符串转化为对应的枚举值。如果转化失败，则抛出异常。*/

@Deprecated("使用具象化泛型。", ReplaceWith("this.toEnumValue<T>(ignoreCase)"))
@Suppress("DEPRECATION")
fun <T> String.toEnumValue(type: Class<T>, ignoreCase: Boolean = false): T {
	requireNotNull(type.isEnum) { "$type is not an enum class." }
	
	return type.enumConstants.first { it.toString().equals(this, ignoreCase) }
}

/**将当前字符串转化为对应的枚举值。如果转化失败，则转化为null。*/
@Deprecated("使用具象化泛型。", ReplaceWith("this.toEnumValueOrNull<T>(ignoreCase)"))
fun <T> String.toEnumValueOrNull(type: Class<T>, ignoreCase: Boolean = false): T? {
	requireNotNull(type.isEnum) { "$type is not an enum class." }
	
	return type.enumConstants.firstOrNull { it.toString().equals(this, ignoreCase) }
}


/**将当前字符串转化为文件。*/
inline fun String.toFile(): File = File(this)

/**将当前字符串转化为路径。*/
inline fun String.toPath(): Path = Path.of(this)

/**将当前字符串转化为统一资源标识符。可能需要事先对查询参数进行适当的编码。*/
inline fun String.toUri(): URI = URI.create(this)

/**将当前字符串转化为统一资源定位符。*/
inline fun String.toUrl(content: URL? = null, handler: URLStreamHandler? = null): URL = URL(content, this, handler)


/**将当前字符串转化为日期。*/
inline fun String.toDate(format: String = "yyyy-MM-dd HH:mm:ss"): Date = SimpleDateFormat(format).parse(this)

/**将当前字符串转化为本地日期。*/
inline fun CharSequence.toLocalDate(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE): LocalDate =
	LocalDate.parse(this, formatter)

/**将当前字符串转化为本地日期时间。*/
inline fun CharSequence.toLocalDateTime(
	formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME): LocalDateTime =
	LocalDateTime.parse(this, formatter)

/**将当前字符串转化为本地时间。*/
inline fun CharSequence.toLocalTime(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME): LocalDateTime =
	LocalDateTime.parse(this, formatter)
//endregion
