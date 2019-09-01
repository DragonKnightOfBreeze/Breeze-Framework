package com.windea.breezeframework.core.enums

/**字母的显示格式。*/
enum class LetterCase(
	val regex: Regex
) {
	Unknown("^$".toRegex()),
	/**camelCase。*/
	CamelCase("^([a-z]+)([A-Z][a-z]*)*$".toRegex()),
	/**PascalCase。*/
	PascalCase("^([A-Z][a-z]*)*$".toRegex()),
	/**snake_case。*/
	SnakeCase("^([a-z]+)(?:_([a-z]+))*$".toRegex()),
	/**SCREAMING_SNAKE_CASE。*/
	ScreamingSnakeCase("^([A-Z]+)(_(?:[A-Z]+))*$".toRegex()),
	/**kebab-case。*/
	KebabCase("^([a-z]+)(?:-([a-z]+))*$".toRegex()),
	/**KEBAB-UPPERCASE*/
	KebabUpperCase("^([A-Z]+)(?:-([A-Z]+))*$".toRegex()),
	/**dot.case。*/
	DotCase("^([a-zA-Z_]+)(?:\\.([a-zA-Z_]+))*$".toRegex()),
	/**whiteSpace case。*/
	WhiteSpaceCase("^([a-zA-Z]+)(?:(\\s[a-zA-Z]+))*$".toRegex())
}
