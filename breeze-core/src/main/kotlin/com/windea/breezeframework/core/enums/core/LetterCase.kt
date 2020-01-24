@file:Suppress("EnumEntryName", "RemoveRedundantBackticks")

package com.windea.breezeframework.core.enums.core

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*

/**字母的显示格式。*/
enum class LetterCase(
	override val splitFunction: (String) -> List<String> = { listOf(it) },
	override val joinFunction: (List<String>) -> String = { it.joinToString("") },
	override val regex: Regex? = null,
	override val predicate: (String) -> Boolean = { regex == null || it matches regex }
) : FormatCase {
	/**全小写的单词。*/
	`lowercase`(
		{ listOf(it) },
		{ it.joinToString("").toLowerCase() },
		"""[a-z]+""".toRegex()
	),
	/**全大写的单词。*/
	`UPPERCASE`(
		{ listOf(it) },
		{ it.joinToString("").toUpperCase() },
		"""[A-Z]+""".toRegex()
	),
	/**首字母大写的单词。*/
	`Capitalized`(
		{ listOf(it) },
		{ it.joinToString("").firstCharToUpperCase() },
		"""[A-Z][a-z]+""".toRegex()
	),
	/**全小写的单词组。*/
	`lower case words`(
		{ it.splitToWordList() },
		{ it.joinToString(" ").toLowerCase() },
		"""[a-z']+(?:\s+[a-z']+)+""".toRegex()
	),
	/**全大写的单词组。*/
	`UPPER CASE WORDS`(
		{ it.splitToWordList() },
		{ it.joinToString(" ").toUpperCase() },
		"""[A-Z']+(?:\s+[A-Z']+)+""".toRegex()
	),
	/**首个单词的首字母大写的单词组。*/
	`First word capitalized`(
		{ it.splitToWordList() },
		{ it.joinToString(" ").firstCharToUpperCase() },
		"""[A-Z][a-z']*(?:\s+[a-z']+)+""".toRegex()
	),
	/**首字母大写的单词组。*/
	`Capitalized Words`(
		{ it.splitToWordList() },
		{ it.joinToString(" ") { s -> s.firstCharToUpperCase() } },
		"""[A-Z][a-z]*(?:\s+[a-z]+)+""".toRegex()
	),
	/**单词组。*/
	`Generic Words`(
		{ it.splitToWordList() },
		{ it.joinToString(" ") },
		"""\S+(?:\s+\S+)+""".toRegex()
	),
	/**以点分割的格式。*/
	`DotCase`(
		{ it.split(".") },
		{ it.joinToString(".") },
		"""[a-zA-Z_{}\[\]$]+(?:\.[a-zA-Z_{}\[\]$]+)+""".toRegex()
	),
	/**以单词边界分割，首个单词全小写，后续单词首字母大写的格式。*/
	`camelCase`(
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { S -> S.firstCharToUpperCase() }.firstCharToLowerCase() },
		"""\$?[a-z]+(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex()
	),
	/**以单词边界分割，所有单词首字母大写的格式。*/
	`PascalCase`(
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() } },
		"""\$?(?:[A-Z][a-z]+|[A-Z]+)(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex()
	),
	/**以下划线分割，所有单词全小写的格式。*/
	`snake_case`(
		{ it.split("_") },
		{ it.joinToString("_") { s -> s.toLowerCase() } },
		"""\$?[a-z]+(?:_(?:\$?[a-z]+\$?|\d+))+""".toRegex()
	),
	/**以下划线分割，所有单词全大写的格式。*/
	`SCREAMING_SNAKE_CASE`(
		{ it.split("_") },
		{ it.joinToString("_") { s -> s.toUpperCase() } },
		"""\$?[A-Z]+(?:_(?:\$?[A-Z]+|\d+))+""".toRegex()
	),
	/**以数个下划线分割的格式。*/
	`underscore_Words`(
		{ it.splitToWordList('_') },
		{ it.joinToString("_") },
		"""_*[a-zA-Z$]+(?:_+(?:[a-zA-Z$]+|\d+))+""".toRegex()
	),
	/**以连接线分割，所有单词全小写的格式。*/
	`kebab-case`(
		{ it.split("-") },
		{ it.joinToString("-") { s -> s.toLowerCase() } },
		"""[a-z]+(?:-(?:[a-z]+|\d+))+""".toRegex()
	),
	/**以连接线分割，所有单词全大写的格式。*/
	`KEBAB-UPPERCASE`(
		{ it.split("-") },
		{ it.joinToString("-") { s -> s.toUpperCase() } },
		"""[A-Z]+(?:-(?:[A-Z]+|\d+))+""".toRegex()
	),
	/**以数个连接线分割的格式。*/
	`hyphen-Words`(
		{ it.splitToWordList('-') },
		{ it.joinToString("-") },
		"""-*[a-zA-Z]+(?:-+(?:[a-zA-Z]+|\d+))+""".toRegex()
	),
	/**以单词边界或者下划线分割，首个单词全小写，后续单词首字母大写的格式。*/
	@ExplicitUsageApi
	`camelCase_AllowUnderscore`(
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { S -> S.firstCharToUpperCase() }.firstCharToLowerCase() },
		"""_*\$?[a-z]+(?:_*(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+))+""".toRegex()
	),
	/**以单词边界或者下划线分割，所有单词首字母大写的格式。*/
	@ExplicitUsageApi
	`PascalCase_AllowUnderscore`(
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { S -> S.firstCharToUpperCase() }.firstCharToLowerCase() },
		"""_*\$?(?:[A-Z][a-z]+|[A-Z]+)(?:_*(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+))+""".toRegex()
	),
	/**未知格式。*/
	Unknown;
}
