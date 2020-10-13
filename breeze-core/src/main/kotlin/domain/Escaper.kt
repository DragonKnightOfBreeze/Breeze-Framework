// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain

//com.google.common.escape.Escaper

/**
 * 转义器。
 *
 * 转义器用于基于指定的（语言）规则，转义和反转义字符串。
 * 注意：不考虑转义特殊的Unicode字符。
 */
interface Escaper {
	fun escape(value: String): String

	fun unescape(value: String): String

	companion object {
		//不进行注册操作：没有需要注册中心的相关方法
		//不验证escapeChars和escapedStrings长度是否一致：交由底层负责

		private fun escapeByPair(value: String, escapeChars: CharArray, escapedStrings: Array<String>): String {
			//这里直接遍历字符数组以提高性能
			return buildString {
				val chars = value.toCharArray()
				for(char in chars) {
					val index = escapeChars.indexOf(char)
					if(index == -1) append(char) else append(escapedStrings[index])
				}
			}
		}

		private fun unescapeByPair(value: String, escapeChars: CharArray, escapedStrings: Array<String>): String {
			var result = value
			val size = escapeChars.size
			for(i in 0 until size) {
				result = result.replace(escapedStrings[i], escapeChars[i].toString())
			}
			return result
		}
	}

	//region Default Escapers
	/**
	 * Kotlin字符串的转义器。
	 */
	object KotlinEscaper : Escaper {
		private val escapeChars = charArrayOf('\n', '\r', '\t', '\b', '\'', '\"', '\$')
		private val escapedStrings = arrayOf("\\n", "\\r", "\\t", "\\b", "\\'", "\\\"", "\\\$")
		override fun escape(value: String) = escapeByPair(value, escapeChars, escapedStrings)
		override fun unescape(value: String) = unescapeByPair(value, escapeChars, escapedStrings)
	}

	/**
	 * Java的转义器。
	 */
	object JavaEscaper : Escaper {
		private val escapeChars = charArrayOf('\n', '\r', '\t', '\b', '\'', '\"')
		private val escapedStrings = arrayOf("\\n", "\\r", "\\t", "\\b", "\\'", "\\\"")
		override fun escape(value: String) = escapeByPair(value, escapeChars, escapedStrings)
		override fun unescape(value: String) = unescapeByPair(value, escapeChars, escapedStrings)
	}

	/**
	 * JavaScript的转义器。
	 */
	object JavaScriptEscaper : Escaper {
		private val escapeChars = charArrayOf('\n', '\r', '\t', '\b', '\'', '\"')
		private val escapedStrings = arrayOf("\\n", "\\r", "\\t", "\b", "\\'", "\\\"")
		override fun escape(value: String) = escapeByPair(value, escapeChars, escapedStrings)
		override fun unescape(value: String) = unescapeByPair(value, escapeChars, escapedStrings)
	}

	/**
	 * Json的转义器。
	 */
	object JsonEscaper : Escaper {
		private val escapeChars = charArrayOf('\t', '\b', '\n', '\r', '\"')
		private val escapedStrings = arrayOf("\\t", "\\b", "\\n", "\\r", "\\\"")
		override fun escape(value: String) = escapeByPair(value, escapeChars, escapedStrings)
		override fun unescape(value: String) = unescapeByPair(value, escapeChars, escapedStrings)
	}

	/**
	 * Xml的转义器。
	 */
	object XmlEscaper : Escaper {
		private val escapeChars = charArrayOf('<', '>', '&', '\'', '\"')
		private val escapedStrings = arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "&quot;")
		override fun escape(value: String) = escapeByPair(value, escapeChars, escapedStrings)
		override fun unescape(value: String) = unescapeByPair(value, escapeChars, escapedStrings)
	}

	/**
	 * Xml属性的转义器。
	 */
	object XmlAttributeEscaper : Escaper {
		private val escapeChars = charArrayOf('<', '>', '&', '\'', '\"', '\n', '\r', '\t')
		private val escapedStrings = arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "&quot;", "&#xA;", "&#xD", "&#x9")
		override fun escape(value: String) = escapeByPair(value, escapeChars, escapedStrings)
		override fun unescape(value: String) = unescapeByPair(value, escapeChars, escapedStrings)
	}

	/**
	 * Xml内容的转义器。
	 */
	object XmlContentEscaper : Escaper {
		private val escapeChars = charArrayOf('<', '>', '&')
		private val escapedStrings = arrayOf("&lt;", "&gt;", "amp;")
		override fun escape(value: String) = escapeByPair(value, escapeChars, escapedStrings)
		override fun unescape(value: String) = unescapeByPair(value, escapeChars, escapedStrings)
	}

	/**
	 * Html的转义器。
	 */
	object HtmlEscaper : Escaper {
		private val escapeChars = charArrayOf('<', '>', '&', '\'', '\"')
		private val escapedStrings = arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "&quot;")
		override fun escape(value: String) = escapeByPair(value, escapeChars, escapedStrings)
		override fun unescape(value: String) = unescapeByPair(value, escapeChars, escapedStrings)
	}

	/**
	 * 正则表达式的转义器。
	 *
	 * 注意：使用`Regex.escape(value)`进行转义，不支持反转义。
	 */
	object RegexEscaper : Escaper {
		override fun escape(value: String) = Regex.escape(value)
		override fun unescape(value: String) =
			throw UnsupportedOperationException("Unescape regex is not supported by regex escaper.")
	}
	//endregion

	//region Line Break Escapers
	/**
	 * 换行转义器。
	 *
	 * 将换行符`\n`转义。
	 */
	object LineBreakEscaper : Escaper {
		override fun escape(value: String) = value.replace("\n", "\\n")
		override fun unescape(value: String) = value.replace("\\n", "\n")
	}

	/**
	 * Html换行转义器。
	 *
	 * 将换行符`\n`转义为`<br>`。
	 */
	object HtmlLineBreakEscaper : Escaper {
		var escapedTag = "<br>"
		override fun escape(value: String) = value.replace("\n", escapedTag)
		override fun unescape(value: String) = value.replace(escapedTag, "\n", true)
	}
	//endregion
}
