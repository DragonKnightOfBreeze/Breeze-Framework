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
		//($abc)($Abc)*
		"^([a-z]+)([A-Z][a-z]*)*$".toRegex(),
		{ it.joinToString("") { S -> S.firstCharToUpperCaseOnly() }.firstCharToLowerCase() }
	),
	/**PascalCase*/
	PascalCase(
		//($Abc)+
		"^([A-Z][a-z]*)+$".toRegex(),
		{ it.joinToString("") { s -> s.firstCharToUpperCaseOnly() } }
	),
	/**snake_case*/
	SnakeCase(
		//($abc)(wrapper(delimiter,$abc))*
		"^([a-z]+)(?:_([a-z]+))*$".toRegex(),
		{ it.joinToString("_") { s -> s.toLowerCase() } }
	),
	/**SCREAMING_SNAKE_CASE*/
	ScreamingSnakeCase(
		//($ABC)(wrapper(delimiter,$ABC))*
		"^([A-Z]+)(?:_([A-Z]+))*$".toRegex(),
		{ it.joinToString("_") { s -> s.toUpperCase() } }
	),
	/**kebab-case*/
	KebabCase(
		//($abc)(wrapper(delimiter,$abc))*
		"^([a-z]+)(?:-([a-z]+))*$".toRegex(),
		{ it.joinToString("-") { s -> s.toLowerCase() } }
	),
	/**KEBAB-UPPERCASE*/
	KebabUpperCase(
		//($ABC)(wrapper(delimiter,$ABC))*
		"^([A-Z]+)(?:-([A-Z]+))*$".toRegex(),
		{ it.joinToString("-") { s -> s.toUpperCase() } }
	),
	/**dot.case*/
	DotCase(
		//($AbC)(wrapper(delimiter,$AbC))*
		"^([a-zA-Z_]+)(?:\\.([a-zA-Z_]+))*$".toRegex(),
		{ it.joinToString(".") }
	),
	/**lowercase*/
	LowerCase(
		//$abc
		"^([a-z]+)$".toRegex(),
		{ it.joinToString("") { s -> s.toLowerCase() } }
	),
	/**UPPERCASE*/
	UpperCase(
		//$ABC
		"^([A-Z]+)$".toRegex(),
		{ it.joinToString("") { s -> s.toUpperCase() } }
	),
	/**lower case words*/
	LowerCaseWords(
		//($abc)(wrapper(delimiter,$abc))*
		"^([a-z]+)(?:\\s+([a-z]+))*$".toRegex(),
		{ it.joinToString(" ") { s -> s.toLowerCase() } }
	),
	/**UPPER CASE WORDS*/
	UpperCaseWords(
		//($ABC)(wrapper(delimiter,$ABC))*
		"^([A-Z]+)(?:\\s+([A-Z]+))*$".toRegex(),
		{ it.joinToString(" ") { s -> s.toUpperCase() } }
	),
	/**Capitalized words*/
	CapitalizedWords(
		//($Abc)(wrapper(delimiter,$abc))*
		"^([A-Z][a-z]*)(?:(\\s+[a-z]+))*$".toRegex(),
		{ it.joinToString("") { s -> s.toLowerCase() }.capitalize() }
	)
}
