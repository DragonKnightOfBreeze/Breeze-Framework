// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

import com.windea.breezeframework.core.annotation.*

/**
 * 转义器。
 *
 * 转义器用于基于指定的（语言）规则，转义和反转义字符串。
 *
 * 注意：不考虑转义特殊的Unicode字符。
 */
@BreezeComponent
interface Escaper {
	/**
	 * 转义指定的字符串。
	 */
	fun escape(value: String): String

	/**
	 * 反转义指定的字符串。
	 */
	fun unescape(value: String): String

	//region Default Escapers
	abstract class AbstractEscaper : Escaper {
		protected abstract val escapeChars: CharArray
		protected abstract val escapedStrings: Array<String>

		override fun escape(value: String): String {
			//这里直接遍历字符数组以提高性能
			return buildString {
				val chars = value.toCharArray()
				for(char in chars) {
					val index = escapeChars.indexOf(char)
					if(index == -1) append(char) else append(escapedStrings[index])
				}
			}
		}

		@NotOptimized
		override fun unescape(value: String): String {
			var result = value
			val size = escapeChars.size
			for(i in 0 until size) {
				result = result.replace(escapedStrings[i], escapeChars[i].toString())
			}
			return result
		}
	}

	/**
	 * Kotlin字符串的转义器。
	 */
	object KotlinEscaper : AbstractEscaper() {
		override val escapeChars = charArrayOf('\n', '\r', '\t', '\b', '\'', '\"', '\$')
		override val escapedStrings = arrayOf("\\n", "\\r", "\\t", "\\b", "\\'", "\\\"", "\\\$")
	}

	/**
	 * Java的转义器。
	 */
	object JavaEscaper : AbstractEscaper() {
		override val escapeChars = charArrayOf('\n', '\r', '\t', '\b', '\'', '\"')
		override val escapedStrings = arrayOf("\\n", "\\r", "\\t", "\\b", "\\'", "\\\"")
	}

	/**
	 * JavaScript的转义器。
	 */
	object JavaScriptEscaper : AbstractEscaper() {
		override val escapeChars = charArrayOf('\n', '\r', '\t', '\b', '\'', '\"')
		override val escapedStrings = arrayOf("\\n", "\\r", "\\t", "\b", "\\'", "\\\"")
	}

	/**
	 * Json的转义器。
	 */
	object JsonEscaper : AbstractEscaper() {
		override val escapeChars = charArrayOf('\t', '\b', '\n', '\r', '\"')
		override val escapedStrings = arrayOf("\\t", "\\b", "\\n", "\\r", "\\\"")
	}

	/**
	 * Xml的转义器。
	 */
	object XmlEscaper : AbstractEscaper() {
		override val escapeChars = charArrayOf('<', '>', '&', '\'', '\"')
		override val escapedStrings = arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "&quot;")
	}

	/**
	 * Xml属性的转义器。
	 */
	object XmlAttributeEscaper : AbstractEscaper() {
		override val escapeChars = charArrayOf('<', '>', '&', '\'', '\"', '\n', '\r', '\t')
		override val escapedStrings = arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "&quot;", "&#xA;", "&#xD", "&#x9")
	}

	/**
	 * Xml内容的转义器。
	 */
	object XmlContentEscaper : AbstractEscaper() {
		override val escapeChars = charArrayOf('<', '>', '&')
		override val escapedStrings = arrayOf("&lt;", "&gt;", "amp;")
	}

	/**
	 * Html的转义器。
	 */
	object HtmlEscaper : AbstractEscaper() {
		override val escapeChars = charArrayOf('<', '>', '&', '\'', '\"')
		override val escapedStrings = arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "&quot;")
	}
	//endregion

	//region Line Break Escapers
	/**
	 * 换行转义器。
	 *
	 * 转义换行符`\n`为`\\n`。
	 */
	object LineBreakEscaper : Escaper {
		override fun escape(value: String) = value.replace("\n", "\\n")
		override fun unescape(value: String) = value.replace("\\n", "\n")
	}

	/**
	 * Html换行转义器。
	 *
	 * 转义换行符`\n`为`<br>`。
	 */
	object HtmlLineBreakEscaper : Escaper {
		private const val escapedTag = "<br>"
		override fun escape(value: String) = value.replace("\n", escapedTag)
		override fun unescape(value: String) = value.replace(escapedTag, "\n", true)
	}
	//endregion

	companion object {
		private val escapers = mutableListOf<Escaper>()

		/**
		 * 得到已注册的转义器列表。
		 */
		@JvmStatic fun values(): List<Escaper> {
			return escapers
		}

		/**
		 * 注册指定的转义器。
		 */
		@JvmStatic fun register(escaper: Escaper) {
			escapers.add(escaper)
		}

		init {
			registerDefaultEscapers()
			registerLineBreakEscapers()
		}

		private fun registerDefaultEscapers() {
			register(KotlinEscaper)
			register(JavaEscaper)
			register(JavaScriptEscaper)
			register(JsonEscaper)
			register(XmlEscaper)
			register(XmlAttributeEscaper)
			register(XmlContentEscaper)
		}

		private fun registerLineBreakEscapers() {
			register(LineBreakEscaper)
			register(HtmlLineBreakEscaper)
		}
	}
}
