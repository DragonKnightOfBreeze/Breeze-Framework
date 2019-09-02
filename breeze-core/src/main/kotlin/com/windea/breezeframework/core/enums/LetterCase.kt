@file:Suppress("EnumEntryName", "RemoveRedundantBackticks")

package com.windea.breezeframework.core.enums

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*

/**字母的显示格式。*/
//DONE 允许非开始位置的数字，视为一个单词进行对待
//DONE 允许单词开始和结束位置的"$"
//DONE 允许重复的"_"
//TODO 允许开始位置的"_"，开始位置的重复的"-"
@NotTested
enum class LetterCase(
	override val regex: Regex,
	override val splitFunction: (String) -> List<String>,
	override val joinFunction: (List<String>) -> String
) : FormatCase {
	`lowercase`(
		//allow: only lower word
		"^([a-z]+)$".toRegex(),
		{ listOf(it) },
		{ it.joinToString("").toLowerCase() }
	),
	`UPPERCASE`(
		//allow: only upper word
		"^([A-Z]+)$".toRegex(),
		{ listOf(it) },
		{ it.joinToString("").toUpperCase() }
	),
	`Capitalized`(
		//allow: only capitalized word
		"^([A-Z][a-z]*)$".toRegex(),
		{ listOf(it) },
		{ it.joinToString("").firstCharToUpperCase() }
	),
	`lower case words`(
		//allow: lower words, blank, `
		"^([a-z']+)(?:\\s+([a-z']+))+$".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ").toLowerCase() }
	),
	`UPPER CASE WORDS`(
		//allow: lower words, blank, `
		"^([A-Z']+)(?:\\s+([A-Z']+))+$".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ").toUpperCase() }
	),
	`First word capitalized`(
		//allow: first word capitalized, blank, '
		"^([A-Z][a-z]*)(?:\\s+([a-z]+))+$".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ").firstCharToUpperCase() }
	),
	`Capitalized Words`(
		//allow: capitalized words, blank, '
		"^([A-Z][a-z]*)(?:\\s+([a-z]+))+$".toRegex(),
		{ it.splitToWordList() },
		{ it.joinToString(" ") { s -> s.firstCharToUpperCase() } }
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
		//allow: lower word first, capitalized/upper words remain , $ surrounding a word, numbers
		"^(\\$?[a-z]+)(?:(\\$?[A-Z][a-z]*)|(\\$?[A-Z]+)|(\\d+))*\\$?$".toRegex(),
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { S -> S.firstCharToUpperCaseOnly() }.firstCharToLowerCase() }
	),
	//`camelCase_AllowUnderscore`(),
	`PascalCase`(
		//allow: capitalized/upper words , $ surrounding a word, numbers
		"^(?:(\\$?[A-Z][a-z]*)|(\\$?[A-Z]+)|(\\d+))*$".toRegex(),
		{ it.toWords().splitToWordList() },
		{ it.joinToString("") { s -> s.firstCharToUpperCaseOnly() } }
	),
	//`PamelCase_AllowUnderscore`(),
	`snake_case`(
		//allow: lower words, $ surrounding a word, _ may repeat, numbers
		"^(\\$?[a-z]+)(?:_+(?:(\\$?[a-z]+)|(\\d+)))*$".toRegex(),
		{ it.split("_").map { s -> s.trim('_') } },
		{ it.joinToString("_") { s -> s.toLowerCase() } }
	),
	`SCREAMING_SNAKE_CASE`(
		//allow: upper words, $ surrounding a word, _ may repeat, numbers
		"^(\\$?[A-Z]+)(?:_+(?:(\\\$?[A-Z]+)|(\\d+)))*$".toRegex(),
		{ it.split("_").map { s -> s.trim('_') } },
		{ it.joinToString("_") { s -> s.toUpperCase() } }
	),
	`underscore_Words`(
		//allow: words, $ surrounding a word, _ may repeat, numbers
		"^(\\$?[a-zA-Z]+)(?:_+(?:(\\\$?[a-zA-Z]+)|(\\d+)))*$".toRegex(),
		{ it.split("_").map { s -> s.trim('_') } },
		{ it.joinToString("_") }
	),
	`kebab-case`(
		//allow: lower words, -, numbers
		"^([a-z]+)(?:-(?:([a-z]+)|(\\d+)))*$".toRegex(),
		{ it.split("-") },
		{ it.joinToString("-") { s -> s.toLowerCase() } }
	),
	`KEBAB-UPPERCASE`(
		//allow: upper words, _, numbers
		"^([A-Z]+)(?:-(?:([A-Z]+)|(\\d+)))*$".toRegex(),
		{ it.split("-") },
		{ it.joinToString("-") { s -> s.toUpperCase() } }
	),
	`hyphen-Words`(
		//allow: words, -, numbers
		"^([a-zA-Z]+)(?:-(?:([a-zA-Z]+)|(\\d+)))*$".toRegex(),
		{ it.split("-") },
		{ it.joinToString("-") }
	),
	Unknown("^(.*)$".toRegex(), { listOf(it) }, { it.joinToString("") });
}
