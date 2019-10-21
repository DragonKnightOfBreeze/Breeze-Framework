package com.windea.breezeframework.core.enums.core

/**转义类型。*/
enum class EscapeType(
	val escapeStrings: Array<String>, //ignore "\"
	val escapedStrings: Array<String>,
	val escapeBackslash: Boolean = true
) {
	InKotlin(
		arrayOf("\t", "\b", "\n", "\r", "\'", "\"", "\$"),
		arrayOf("\\t", "\\b", "\\n", "\\r", "\\'", "\\\"", "\\\$")
	),
	InJava(
		arrayOf("\t", "\b", "\n", "\r", "\'", "\""),
		arrayOf("\\t", "\\b", "\\n", "\\r", "\\'", "\\\"")
	),
	InRegex(
		arrayOf(".", "^", "$", "[", "{", "(", "|", "?", "+"), //not for every scope
		arrayOf("\\.", "\\^", "\\$", "\\[", "\\{", "\\(", "\\|", "\\?", "\\+")
	),
	InXml(
		arrayOf("<", ">", "&", "'", "\""), //only for basic escape
		arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "quot;"),
		false
	)
}
