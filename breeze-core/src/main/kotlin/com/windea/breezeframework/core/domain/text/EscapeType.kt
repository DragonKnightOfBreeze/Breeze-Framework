package com.windea.breezeframework.core.domain.text

/**转义类型。*/
enum class EscapeType(
	override val escapeStrings: Array<String>,
	override val escapedStrings: Array<String>
) : EscapeStrategy {
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
	//com.google.common.xml.XmlEscapers
	/**Xml转义。*/
	Xml(
		arrayOf("<", ">", "&", "'", "\""),
		arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "&quot;")
	),

	/**Xml特性转义。*/
	XmlAttribute(
		arrayOf("<", ">", "&", "'", "\"", "\t", "\n", "\r"),
		arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "&quot;", "&#x9", "&#xA", "&#xD")
	),
	//com.google.common.html.HtmlEscapers
	/**Html转义。*/
	Html(
		arrayOf("<", ">", "&", "'", "\""),
		arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "&quot;")
	)
}
