package com.windea.breezeframework.core.enums

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*

/**字母的显示格式。*/
@NotTested
enum class LetterCase(
	val regex: Regex = "^$".toRegex(),
	val joinFunction: (List<String>) -> String = { it.first() }
) {
	Unknown,
	/**camelCase*/
	CamelCase(
		"^([a-z]+)([A-Z][a-z]*)*$".toRegex(),
		{ it.joinToString("") { S -> S.firstCharToUpperCaseOnly() }.firstCharToLowerCase() }
	),
	/**PascalCase*/
	PascalCase(
		"^([A-Z][a-z]*)*$".toRegex(),
		{ it.joinToString("") { s -> s.firstCharToUpperCaseOnly() } }
	),
	/**snake_case*/
	SnakeCase(
		"^([a-z]+)(?:_([a-z]+))*$".toRegex(),
		{ it.joinToString("_") { s -> s.toLowerCase() } }
	),
	/**SCREAMING_SNAKE_CASE*/
	ScreamingSnakeCase(
		"^([A-Z]+)(_(?:[A-Z]+))*$".toRegex(),
		{ it.joinToString("_") { s -> s.toUpperCase() } }
	),
	/**kebab-case*/
	KebabCase(
		"^([a-z]+)(?:-([a-z]+))*$".toRegex(),
		{ it.joinToString("-") { s -> s.toLowerCase() } }
	),
	/**KEBAB-UPPERCASE*/
	KebabUpperCase(
		"^([A-Z]+)(?:-([A-Z]+))*$".toRegex(),
		{ it.joinToString("-") { s -> s.toUpperCase() } }
	),
	/**dot.case*/
	DotCase(
		"^([a-zA-Z_]+)(?:\\.([a-zA-Z_]+))*$".toRegex(),
		{ it.joinToString(".") }
	),
	/**lowercase*/
	LowerCase(
		"^[a-z]+$".toRegex(),
		{ it.joinToString("") { s -> s.toLowerCase() } }
	),
	/**UPPERCASE*/
	UpperCase(
		"^[A-Z]+$".toRegex(),
		{ it.joinToString("") { s -> s.toUpperCase() } }
	),
	/**lower case words*/
	LowerCaseWords(
		"^([a-z]+)(?:(\\s[a-z]+))*\$".toRegex(),
		{ it.joinToString(" ") { s -> s.toLowerCase() } }
	),
	/**UPPER CASE WORDS*/
	UpperCaseWords(
		"^([a-z]+)(?:(\\s[a-z]+))*\$".toRegex(),
		{ it.joinToString(" ") { s -> s.toUpperCase() } }
	),
	/**Capitalized words*/
	CapitalizedWords(
		"^([A-Z][a-z]*)(?:(\\s[a-z]+))*\$".toRegex(),
		{ it.joinToString("") { s -> s.toLowerCase() }.capitalize() }
	)
}
