@file:Suppress("EnumEntryName", "RemoveRedundantBackticks")

package com.windea.breezeframework.core.enums

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*

/**字母的显示格式。*/
@NotTested
enum class LetterCase(
	override val regex: Regex,
	override val splitFunction: (String) -> List<String>,
	override val joinFunction: (List<String>) -> String
) : FormatCase {
	`lowercase`(
		//allow: only lower words
		"^([a-z]+)$".toRegex(),
		{ listOf(it) },
		{ it.joinToString("").toLowerCase() }
	),
	`UPPERCASE`(
		//allow: only upper words
		"^([A-Z]+)$".toRegex(),
		{ listOf(it) },
		{ it.joinToString("").toUpperCase() }
	),
	`Capitalized`(
		//allow: only capitalized word
		"^([A-Z][a-z]*)$".toRegex(),
		{ listOf(it) },
		{ it.joinToString("").capitalize() }
	),
	`lower case words`(
		//allow: lower words, blank, `
		"^([a-z']+)(?:\\s+([a-z']+))+$".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ") { s -> s.toLowerCase() } }
	),
	`UPPER CASE WORDS`(
		//allow: lower words, blank, `
		"^([A-Z']+)(?:\\s+([A-Z']+))+$".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ") { s -> s.toUpperCase() } }
	),
	`Capitalized words`(
		//allow: capitalized words, blank, `
		"^([A-Z][a-z]*)(?:\\s+([a-z]+))+$".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ") { s -> s.toLowerCase() }.capitalize() }
	),
	`Generic Words`(
		//allow: just include blank
		"^(\\S+)(?:\\s+(\\S+))+$".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ") }
	),
	`DotCase`(
		//allow: words, $, -, _, ., {, }, [, ]
		"^([a-zA-Z$\\-_{}\\[]]+}?)(?:\\.([a-zA-Z$\\-_]+))+$".toRegex(),
		{ it.split(".") },
		{ it.joinToString(".") }
	),
	`camelCase`(
		//allow: words, $
		"^(\\$?[a-z]+)(\\$?[A-Z][a-z]*)*$".toRegex(),
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { S -> S.firstCharToUpperCaseOnly() }.firstCharToLowerCase() }
	),
	`PascalCase`(
		//allow: words, $
		"^(\\$?[A-Z][a-z]*)*$".toRegex(),
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { s -> s.firstCharToUpperCaseOnly() } }
	),
	`snake_case`(
		//allow: words, $
		"^(\\$?[a-z]+)(?:_(\\$?[a-z]+))*$".toRegex(),
		{ it.split("_") },
		{ it.joinToString("_") { s -> s.toLowerCase() } }
	),
	`SCREAMING_SNAKE_CASE`(
		//allow: words, $
		"^(\\$?[A-Z]+)(?:_(\\$?[A-Z]+))*$".toRegex(),
		{ it.split("_") },
		{ it.joinToString("_") { s -> s.toUpperCase() } }
	),
	`kebab-case`(
		//allow: words
		"^([a-z]+)(?:-([a-z]+))*$".toRegex(),
		{ it.split("-") },
		{ it.joinToString("-") { s -> s.toLowerCase() } }
	),
	`KEBAB-UPPERCASE`(
		//allow: words
		"^([A-Z]+)(?:-([A-Z]+))*$".toRegex(),
		{ it.split("-") },
		{ it.joinToString("-") { s -> s.toUpperCase() } }
	),
	Unknown("^(.*)$".toRegex(), { listOf(it) }, { it.joinToString("") });
}
