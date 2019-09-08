@file:Suppress("EnumEntryName", "RemoveRedundantBackticks")

package com.windea.breezeframework.core.enums

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*

/**字母的显示格式。*/
@NotTested("难以进行完整的测试。")
enum class LetterCase(
	override val regex: Regex,
	override val splitFunction: (String) -> List<String>,
	override val joinFunction: (List<String>) -> String
) : FormatCase {
	`lowercase`(
		//allow: only lower word
		"""[a-z]+""".toRegex(),
		{ listOf(it) },
		{ it.joinToString("").toLowerCase() }
	),
	`UPPERCASE`(
		//allow: only upper word
		"""[A-Z]+""".toRegex(),
		{ listOf(it) },
		{ it.joinToString("").toUpperCase() }
	),
	`Capitalized`(
		//allow: only capitalized word
		"""[A-Z][a-z]+""".toRegex(),
		{ listOf(it) },
		{ it.joinToString("").firstCharToUpperCase() }
	),
	`lower case words`(
		//allow: lower words, blank, `
		"""[a-z']+(?:\s+[a-z']+)+""".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ").toLowerCase() }
	),
	`UPPER CASE WORDS`(
		//allow: lower words, blank, `
		"""[A-Z']+(?:\s+[A-Z']+)+""".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ").toUpperCase() }
	),
	`First word capitalized`(
		//allow: first word capitalized, blank, '
		"""[A-Z][a-z']*(?:\s+[a-z']+)+""".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ").firstCharToUpperCase() }
	),
	`Capitalized Words`(
		//allow: capitalized words, blank, '
		"""[A-Z][a-z]*(?:\s+[a-z]+)+""".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ") { s -> s.firstCharToUpperCase() } }
	),
	`Generic Words`(
		//allow: just include blank
		"""\S+(?:\s+\S+)+""".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ") }
	),
	`DotCase`(
		//allow: words, $, _, ., {, }, [, ]
		"""[a-zA-Z_{}\[\]$]+(?:\.[a-zA-Z_{}\[\]$]+)+""".toRegex(),
		{ it.split(".") },
		{ it.joinToString(".") }
	),
	`camelCase`(
		//allow: lower word first, capitalized/upper words remain , $ surrounds a word, numbers
		"""\$?[a-z]+(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex(),
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { S -> S.firstCharToUpperCaseOnly() }.firstCharToLowerCase() }
	),
	`PascalCase`(
		//allow: capitalized/upper words , $ surrounds a word, numbers
		"""\$?(?:[A-Z][a-z]+|[A-Z]+)(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+)+""".toRegex(),
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { s -> s.firstCharToUpperCaseOnly() } }
	),
	`snake_case`(
		//allow: lower words, $ surrounds a word, _, numbers
		"""\$?[a-z]+(?:_(?:\$?[a-z]+\$?|\d+))+""".toRegex(),
		{ it.split("_") },
		{ it.joinToString("_") { s -> s.toLowerCase() } }
	),
	`SCREAMING_SNAKE_CASE`(
		//allow: upper words, $ surrounds a word, _, numbers
		"""\$?[A-Z]+(?:_(?:\$?[A-Z]+|\d+))+""".toRegex(),
		{ it.split("_") },
		{ it.joinToString("_") { s -> s.toUpperCase() } }
	),
	`underscore_Words`(
		//allow: words, $, _ may repeat and may at begin, numbers
		"""_*[a-zA-Z$]+(?:_+(?:[a-zA-Z$]+|\d+))+""".toRegex(),
		{ it.splitToWordList('_') },
		{ it.joinToString("_") }
	),
	`kebab-case`(
		//allow: lower words, -, numbers
		"""[a-z]+(?:-(?:[a-z]+|\d+))+""".toRegex(),
		{ it.split("-") },
		{ it.joinToString("-") { s -> s.toLowerCase() } }
	),
	`KEBAB-UPPERCASE`(
		//allow: upper words, -, numbers
		"""[A-Z]+(?:-(?:[A-Z]+|\d+))+""".toRegex(),
		{ it.split("-") },
		{ it.joinToString("-") { s -> s.toUpperCase() } }
	),
	`hyphen-Words`(
		//allow: words, - may repeat and may at begin, numbers
		"""-*[a-zA-Z]+(?:-+(?:[a-zA-Z]+|\d+))+""".toRegex(),
		{ it.splitToWordList('-') },
		{ it.joinToString("-") }
	),
	@ExplicitApi
	`camelCase_AllowUnderscore`(
		//allow: lower word first, capitalized/upper words remain , $ surrounds a word, numbers, _ may repeat & at begin
		"""_*\$?[a-z]+(?:_*(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+))+""".toRegex(),
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { S -> S.firstCharToUpperCaseOnly() }.firstCharToLowerCase() }
	),
	@ExplicitApi
	`PascalCase_AllowUnderscore`(
		//allow: lower word first, capitalized/upper words remain , $ surrounds a word, numbers, _ may repeat & at begin
		"""_*\$?(?:[A-Z][a-z]+|[A-Z]+)(?:_*(?:\$?[A-Z][a-z]+\$?|\$?[A-Z]+\$?|\d+))+""".toRegex(),
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { S -> S.firstCharToUpperCaseOnly() }.firstCharToLowerCase() }
	),
	Unknown(""".*""".toRegex(), { listOf(it) }, { it.joinToString("") });
}
