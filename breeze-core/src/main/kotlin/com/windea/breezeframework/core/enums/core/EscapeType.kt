package com.windea.breezeframework.core.enums.core

/**转义类型。*/
enum class EscapeType(
	/**转义的字符串组。*/
	val escapeStrings: Array<String>,
	/**转义后的字符串组。*/
	val escapedStrings: Array<String>
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
	/**正则表达式转义。注意：不是将原始字符串用`\Q`和`\E`包围，而是转义其中必要的字符。*/
	Regex(
		arrayOf(".", "^", "$", "[", "{", "(", "|", "-", "*", "?", "+"), //not for every scope
		arrayOf("\\.", "\\^", "\\$", "\\[", "\\{", "\\(", "\\|", "-", "\\*", "\\?", "\\+")
	),
	/**Xml转义。*/
	Xml(
		arrayOf("<", ">", "&", "'", "\""), //only for basic escape
		arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "quot;")
	)
}
