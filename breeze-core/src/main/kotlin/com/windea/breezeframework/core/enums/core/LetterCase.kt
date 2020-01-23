@file:Suppress("EnumEntryName", "RemoveRedundantBackticks")

package com.windea.breezeframework.core.enums.core

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*

/**字母的显示格式。*/
enum class LetterCase(
	override val regex: Regex,
	override val splitFunction: (String) -> List<String>,
	override val joinFunction: (List<String>) -> String
) : FormatCase {
	/**全小写的单词。*/
	`lowercase`(
		//allow: only lower word
		"""[a-z]+""".toRegex(),
		{ listOf(it) },
		{ it.joinToString("").toLowerCase() }
	),
	/**全大写的单词。*/
	`UPPERCASE`(
		//allow: only upper word
		"""[A-Z]+""".toRegex(),
		{ listOf(it) },
		{ it.joinToString("").toUpperCase() }
	),
	/**首字母大写的单词。*/
	`Capitalized`(
		//allow: only capitalized word
		"""[A-Z][a-z]+""".toRegex(),
		{ listOf(it) },
		{ it.joinToString("").firstCharToUpperCase() }
	),
	/**全小写的单词组。*/
	`lower case words`(
		//allow: lower words, blank, `
		"""[a-z']+(?:\s+[a-z']+)+""".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ").toLowerCase() }
	),
	/**全大写的单词组。*/
	`UPPER CASE WORDS`(
		//allow: lower words, blank, `
		"""[A-Z']+(?:\s+[A-Z']+)+""".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ").toUpperCase() }
	),
	/**首个单词的首字母大写的单词组。*/
	`First word capitalized`(
		//allow: first word capitalized, blank, '
		"""[A-Z][a-z']*(?:\s+[a-z']+)+""".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ").firstCharToUpperCase() }
	),
	/**首字母大写的单词组。*/
	`Capitalized Words`(
		//allow: capitalized words, blank, '
		"""[A-Z][a-z]*(?:\s+[a-z]+)+""".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ") { s -> s.firstCharToUpperCase() } }
	),
	/**单词组。*/
	`Generic Words`(
		//allow: just include blank
		"""\S+(?:\s+\S+)+""".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ") }
	),
	/**以点分割的格式。*/
	`DotCase`(
		//allow: words, $, _, ., {, }, [, ]
		"""[a-zA-Z_{}\[\]$]+(?:\.[a-zA-Z_{}\[\]$]+)+""".toRegex(),
		{ it.split(".") },
		{ it.joinToString(".") }
	),
	/**以单词边界分割，首个单词全小写，后续单词首字母大写的格式。*/
	`camelCase`(
		//allow: lower word first, capitalized/upper words remain , $ surrounds a word, numbers
		"""\$?[a-z]+(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex(),
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { S -> S.firstCharToUpperCase() }.firstCharToLowerCase() }
	),
	/**以单词边界分割，所有单词首字母大写的格式。*/
	`PascalCase`(
		//allow: capitalized/upper words , $ surrounds a word, numbers
		"""\$?(?:[A-Z][a-z]+|[A-Z]+)(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex(),
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { s -> s.firstCharToUpperCase() } }
	),
	/**以下划线分割，所有单词全小写的格式。*/
	`snake_case`(
		//allow: lower words, $ surrounds a word, _, numbers
		"""\$?[a-z]+(?:_(?:\$?[a-z]+\$?|\d+))+""".toRegex(),
		{ it.split("_") },
		{ it.joinToString("_") { s -> s.toLowerCase() } }
	),
	/**以下划线分割，所有单词全大写的格式。*/
	`SCREAMING_SNAKE_CASE`(
		//allow: upper words, $ surrounds a word, _, numbers
		"""\$?[A-Z]+(?:_(?:\$?[A-Z]+|\d+))+""".toRegex(),
		{ it.split("_") },
		{ it.joinToString("_") { s -> s.toUpperCase() } }
	),
	/**以下划线分割的格式。*/
	`underscore_Words`(
		//allow: words, $, _ may repeat and may at begin, numbers
		"""_*[a-zA-Z$]+(?:_+(?:[a-zA-Z$]+|\d+))+""".toRegex(),
		{ it.splitToWordList('_') },
		{ it.joinToString("_") }
	),
	/**以连接线分割，所有单词全小写的格式。*/
	`kebab-case`(
		//allow: lower words, -, numbers
		"""[a-z]+(?:-(?:[a-z]+|\d+))+""".toRegex(),
		{ it.split("-") },
		{ it.joinToString("-") { s -> s.toLowerCase() } }
	),
	/**以连接线分割，所有单词全大写的格式。*/
	`KEBAB-UPPERCASE`(
		//allow: upper words, -, numbers
		"""[A-Z]+(?:-(?:[A-Z]+|\d+))+""".toRegex(),
		{ it.split("-") },
		{ it.joinToString("-") { s -> s.toUpperCase() } }
	),
	/**以连接线分割的格式。*/
	`hyphen-Words`(
		//allow: words, - may repeat and may at begin, numbers
		"""-*[a-zA-Z]+(?:-+(?:[a-zA-Z]+|\d+))+""".toRegex(),
		{ it.splitToWordList('-') },
		{ it.joinToString("-") }
	),
	/**以单词边界或者下划线分割，首个单词全小写，后续单词首字母大写的格式。*/
	@ExplicitUsageApi
	`camelCase_AllowUnderscore`(
		//allow: lower word first, capitalized/upper words remain, $ surrounds a word, numbers, _ may repeat & at begin
		"""_*\$?[a-z]+(?:_*(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+))+""".toRegex(),
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { S -> S.firstCharToUpperCase() }.firstCharToLowerCase() }
	),
	/**以单词边界或者下划线分割，所有单词首字母大写的格式。*/
	@ExplicitUsageApi
	`PascalCase_AllowUnderscore`(
		//allow: lower word first, capitalized/upper words remain, $ surrounds a word, numbers, _ may repeat & at begin
		"""_*\$?(?:[A-Z][a-z]+|[A-Z]+)(?:_*(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+))+""".toRegex(),
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { S -> S.firstCharToUpperCase() }.firstCharToLowerCase() }
	),
	/**未知格式。*/
	Unknown(""".*""".toRegex(), { listOf(it) }, { it.joinToString("") });
}
