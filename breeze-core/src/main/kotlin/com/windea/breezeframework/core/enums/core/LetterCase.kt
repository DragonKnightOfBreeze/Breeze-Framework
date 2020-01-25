@file:Suppress("EnumEntryName", "RemoveRedundantBackticks")

package com.windea.breezeframework.core.enums.core

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*

/**字母的显示格式。*/
enum class LetterCase(
	override val splitter: (CharSequence) -> Sequence<String> = { sequenceOf(it.toString()) },
	override val joiner: (Iterable<CharSequence>) -> String = { it.joinToString("") },
	override val arrayJoiner: (Array<out CharSequence>) -> String = { it.joinToString("") },
	override val regex: Regex? = null,
	override val predicate: (String) -> Boolean = { regex == null || it matches regex }
) : FormatCase {
	/**全小写的单词。*/
	`lowercase`(
		{ sequenceOf(it.toString()) },
		{ it.joinToString("").toLowerCase() },
		{ it.joinToString("").toLowerCase() },
		"""[a-z]+""".toRegex()
	),
	/**全大写的单词。*/
	`UPPERCASE`(
		{ sequenceOf(it.toString()) },
		{ it.joinToString("").toUpperCase() },
		{ it.joinToString("").toUpperCase() },
		"""[A-Z]+""".toRegex()
	),
	/**首字母大写的单词。*/
	`Capitalized`(
		{ sequenceOf(it.toString()) },
		{ it.joinToString("").firstCharToUpperCase() },
		{ it.joinToString("").firstCharToUpperCase() },
		"""[A-Z][a-z]+""".toRegex()
	),
	/**全小写的单词组。*/
	`lower case words`(
		{ it.splitToWordSequence(' ') },
		{ it.joinToString(" ").toLowerCase() },
		{ it.joinToString(" ").toLowerCase() },
		"""[a-z']+(?:\s+[a-z']+)+""".toRegex()
	),
	/**全大写的单词组。*/
	`UPPER CASE WORDS`(
		{ it.splitToWordSequence(' ') },
		{ it.joinToString(" ").toUpperCase() },
		{ it.joinToString(" ").toUpperCase() },
		"""[A-Z']+(?:\s+[A-Z']+)+""".toRegex()
	),
	/**首个单词的首字母大写的单词组。*/
	`First word capitalized`(
		{ it.splitToWordSequence(' ') },
		{ it.joinToString(" ").firstCharToUpperCase() },
		{ it.joinToString(" ").firstCharToUpperCase() },
		"""[A-Z][a-z']*(?:\s+[a-z']+)+""".toRegex()
	),
	/**首字母大写的单词组。*/
	`Capitalized Words`(
		{ it.splitToWordSequence(' ') },
		{ it.joinToString(" ") { s -> s.firstCharToUpperCase() } },
		{ it.joinToString(" ") { s -> s.firstCharToUpperCase() } },
		"""[A-Z][a-z]*(?:\s+[a-z]+)+""".toRegex()
	),
	/**单词组。*/
	`Generic Words`(
		{ it.splitToWordSequence(' ') },
		{ it.joinToString(" ") },
		{ it.joinToString(" ") },
		"""\S+(?:\s+\S+)+""".toRegex()
	),
	/**以单个点分割的格式。*/
	`DotCase`(
		{ it.splitToSequence('.') },
		{ it.joinToString(".") },
		{ it.joinToString(".") },
		"""[a-zA-Z_{}\[\]$]+(?:\.[a-zA-Z_{}\[\]$]+)+""".toRegex()
	),
	/**以单词边界分割，首个单词全小写，后续单词首字母大写的格式。*/
	`camelCase`(
		{ it.splitToWords().splitToSequence(' ') },
		{ it.joinToString("") { S -> S.firstCharToUpperCase() }.firstCharToLowerCase() },
		{ it.joinToString("") { S -> S.firstCharToUpperCase() }.firstCharToLowerCase() },
		"""\$?[a-z]+(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex()
	),
	/**以单词边界分割，所有单词首字母大写的格式。*/
	`PascalCase`(
		{ it.splitToWords().splitToSequence(' ') },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() } },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() } },
		"""\$?(?:[A-Z][a-z]+|[A-Z]+)(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex()
	),
	/**以单个下划线分割，所有单词全小写的格式。*/
	`snake_case`(
		{ it.splitToSequence('_') },
		{ it.joinToString("_") { s -> s.toString().toLowerCase() } },
		{ it.joinToString("_") { s -> s.toString().toLowerCase() } },
		"""\$?[a-z]+(?:_(?:\$?[a-z]+\$?|\d+))+""".toRegex()
	),
	/**以单个下划线分割，所有单词全大写的格式。*/
	`SCREAMING_SNAKE_CASE`(
		{ it.splitToSequence('_') },
		{ it.joinToString("_") { s -> s.toString().toUpperCase() } },
		{ it.joinToString("_") { s -> s.toString().toUpperCase() } },
		"""\$?[A-Z]+(?:_(?:\$?[A-Z]+|\d+))+""".toRegex()
	),
	/**以数个下划线分割的格式。*/
	`underscore_Words`(
		{ it.splitToWordSequence('_') },
		{ it.joinToString("_") },
		{ it.joinToString("_") },
		"""_*[a-zA-Z$]+(?:_+(?:[a-zA-Z$]+|\d+))+""".toRegex()
	),
	/**以单个连接线分割，所有单词全小写的格式。*/
	`kebab-case`(
		{ it.splitToSequence('-') },
		{ it.joinToString("-") { s -> s.toString().toLowerCase() } },
		{ it.joinToString("-") { s -> s.toString().toLowerCase() } },
		"""[a-z]+(?:-(?:[a-z]+|\d+))+""".toRegex()
	),
	/**以单个连接线分割，所有单词全大写的格式。*/
	`KEBAB-UPPERCASE`(
		{ it.splitToSequence('-') },
		{ it.joinToString("-") { s -> s.toString().toUpperCase() } },
		{ it.joinToString("-") { s -> s.toString().toUpperCase() } },
		"""[A-Z]+(?:-(?:[A-Z]+|\d+))+""".toRegex()
	),
	/**以数个连接线分割的格式。*/
	`hyphen-Words`(
		{ it.splitToWordSequence('-') },
		{ it.joinToString("-") },
		{ it.joinToString("-") },
		"""-*[a-zA-Z]+(?:-+(?:[a-zA-Z]+|\d+))+""".toRegex()
	),
	/**以单词边界或者数个下划线分割，首个单词全小写，后续单词首字母大写的格式。*/
	@ExplicitUsageApi
	`camelCase_AllowUnderscore`(
		{ it.splitToWords().splitToWordSequence(' ', '_') },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() }.firstCharToLowerCase() },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() }.firstCharToLowerCase() },
		"""_*\$?[a-z]+(?:_*(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+))+""".toRegex()
	),
	/**以单词边界或者数个下划线分割，所有单词首字母大写的格式。*/
	@ExplicitUsageApi
	`PascalCase_AllowUnderscore`(
		{ it.splitToWords().splitToWordSequence(' ', '_') },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() }.firstCharToLowerCase() },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() }.firstCharToLowerCase() },
		"""_*\$?(?:[A-Z][a-z]+|[A-Z]+)(?:_*(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+))+""".toRegex()
	),
	/**未知格式。*/
	Unknown;
}


/**将第一个字符转为大写。*/
private fun CharSequence.firstCharToUpperCase(): String {
	return this[0].toUpperCase() + this.substring(1)
}

/**将第一个字符转为小写。*/
private fun CharSequence.firstCharToLowerCase(): String {
	return this[0].toLowerCase() + this.substring(1)
}

/**将当前字符串分割为单词序列，基于任意长度的指定的分割符。允许位于首尾的分隔符。*/
private fun CharSequence.splitToWordSequence(vararg delimiters: Char): Sequence<String> {
	return this.splitToSequence(*delimiters).filterNotEmpty()
}

/**基于小写字母与大写字母的边界，将当前字符串转化为以空格分割的单词组成的字符串。允许全大写的单词。*/
private fun CharSequence.splitToWords(): String {
	return this.replace("""\B([A-Z][a-z])""".toRegex(), " $1")
}
