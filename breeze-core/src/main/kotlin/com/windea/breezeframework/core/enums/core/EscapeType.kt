package com.windea.breezeframework.core.enums.core

/**转义类型。*/
enum class EscapeType(
	val escapeStrings: Array<String>, //ignore "\"
	val escapedStrings: Array<String>,
	val escapeBackslash: Boolean = true
) {
	Kotlin(
		arrayOf("\t", "\b", "\n", "\r", "\'", "\"", "\$"),
		arrayOf("\\t", "\\b", "\\n", "\\r", "\\'", "\\\"", "\\\$")
	),
	Java(
		arrayOf("\t", "\b", "\n", "\r", "\'", "\""),
		arrayOf("\\t", "\\b", "\\n", "\\r", "\\'", "\\\"")
	),
	Regex(
		arrayOf(".", "^", "$", "[", "{", "(", "|", "?", "+"), //not for every scope
		arrayOf("\\.", "\\^", "\\$", "\\[", "\\{", "\\(", "\\|", "\\?", "\\+")
	),
	Xml(
		arrayOf("<", ">", "&", "'", "\""), //only for basic escape
		arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "quot;"),
		false
	)
}
