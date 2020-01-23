package com.windea.breezeframework.core.enums.core

/**转义类型。*/
enum class EscapeType(
	val escapeStrings: Array<String>, //ignore "\"
	val escapedStrings: Array<String>,
	val escapeBackslash: Boolean = true
) {
	/**Kotlin转义。*/
	Kotlin(
		arrayOf("\t", "\b", "\n", "\r", "\'", "\"", "\$"),
		arrayOf("\\t", "\\b", "\\n", "\\r", "\\'", "\\\"", "\\\$")
	),
	/**Java转义。*/
	Java(
		arrayOf("\t", "\b", "\n", "\r", "\'", "\""),
		arrayOf("\\t", "\\b", "\\n", "\\r", "\\'", "\\\"")
	),
	/**正则表达式转义。*/
	Regex(
		arrayOf(".", "^", "$", "[", "{", "(", "|", "?", "+"), //not for every scope
		arrayOf("\\.", "\\^", "\\$", "\\[", "\\{", "\\(", "\\|", "\\?", "\\+")
	),
	/**Xml转义。*/
	Xml(
		arrayOf("<", ">", "&", "'", "\""), //only for basic escape
		arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "quot;"),
		false
	)
}
