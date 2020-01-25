package com.windea.breezeframework.core.enums.text

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
		arrayOf(".", "^", "$", "[", "{", "(", "|", "-", "*", "?", "+"), //可能不适用于所有情况
		arrayOf("\\.", "\\^", "\\$", "\\[", "\\{", "\\(", "\\|", "-", "\\*", "\\?", "\\+")
	),
	/**Xml转义。*/
	Xml(
		arrayOf("<", ">", "&", "'", "\""),
		arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "quot;")
	)
}
