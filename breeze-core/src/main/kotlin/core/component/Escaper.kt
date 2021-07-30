// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.annotation.*
import icu.windea.breezeframework.core.extension.*

/**
 * 转义器。
 *
 * 转义器用于基于指定的（语言）规则，转义和反转义字符串。
 *
 * 注意：不考虑转义特殊的Unicode字符。
 */
interface Escaper : Component {
	/**
	 * 转义指定的字符串。
	 */
	fun escape(value: String): String

	/**
	 * 反转义指定的字符串。
	 */
	fun unescape(value: String): String
}

abstract class AbstractEscaper : Escaper {
	override fun equals(other: Any?) = componentEquals(this, other)

	override fun hashCode() = componentHashcode(this)

	override fun toString() = componentToString(this)
}

@NotOptimized
object Escapers : ComponentRegistry<Escaper>() {
	//region Implementations
	/**
	 * 特定语言的转义器。
	 */
	abstract class LanguageEscaper : AbstractEscaper() {
		protected abstract val escapeChars: CharArray
		protected abstract val escapedStrings: Array<String>
		protected val escapeBackSlash = true

		override fun escape(value: String): String {
			//这里直接遍历字符数组以提高性能
			return buildString {
				val chars = value.toCharArray()
				for(char in chars) {
					val index = escapeChars.indexOf(char)
					when {
						index != -1 -> append(escapedStrings[index])
						escapeBackSlash && char == '\\' -> append("\\\\")
						else -> append(char)
					}
				}
			}
		}

		override fun unescape(value: String): String {
			var result = value
			if(escapeBackSlash) result = result.replace("\\\\", "\\")
			val size = escapeChars.size
			//TODO 需要顺序替换
			for(i in 0 until size) {
				result = result.replace(escapedStrings[i], escapeChars[i].toString())
			}
			return result
		}
	}

	/**
	 * Kotlin字符串的转义器。
	 */
	object KotlinEscaper : LanguageEscaper() {
		override val escapeChars = charArrayOf('\n', '\r', '\t', '\b', '\'', '\"', '\$')
		override val escapedStrings = arrayOf("\\n", "\\r", "\\t", "\\b", "\\'", "\\\"", "\\\$")
	}

	/**
	 * Java的转义器。
	 */
	object JavaEscaper : LanguageEscaper() {
		override val escapeChars = charArrayOf('\n', '\r', '\t', '\b', '\'', '\"')
		override val escapedStrings = arrayOf("\\n", "\\r", "\\t", "\\b", "\\'", "\\\"")
	}

	/**
	 * JavaScript的转义器。
	 */
	object JavaScriptEscaper : LanguageEscaper() {
		override val escapeChars = charArrayOf('\n', '\r', '\t', '\b', '\'', '\"')
		override val escapedStrings = arrayOf("\\n", "\\r", "\\t", "\b", "\\'", "\\\"")
	}

	/**
	 * Json的转义器。
	 */
	object JsonEscaper : LanguageEscaper() {
		override val escapeChars = charArrayOf('\t', '\b', '\n', '\r', '\"')
		override val escapedStrings = arrayOf("\\t", "\\b", "\\n", "\\r", "\\\"")
	}

	/**
	 * Xml的转义器。
	 */
	object XmlEscaper : LanguageEscaper() {
		override val escapeChars = charArrayOf('<', '>', '&', '\'', '\"')
		override val escapedStrings = arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "&quot;")
	}

	/**
	 * Xml属性的转义器。
	 */
	object XmlAttributeEscaper : LanguageEscaper() {
		override val escapeChars = charArrayOf('<', '>', '&', '\'', '\"', '\n', '\r', '\t')
		override val escapedStrings = arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "&quot;", "&#xA;", "&#xD", "&#x9")
	}

	/**
	 * Xml内容的转义器。
	 */
	object XmlContentEscaper : LanguageEscaper() {
		override val escapeChars = charArrayOf('<', '>', '&')
		override val escapedStrings = arrayOf("&lt;", "&gt;", "amp;")
	}

	/**
	 * Html的转义器。
	 */
	object HtmlEscaper : LanguageEscaper() {
		override val escapeChars = charArrayOf('<', '>', '&', '\'', '\"')
		override val escapedStrings = arrayOf("&lt;", "&gt;", "&amp;", "&apos;", "&quot;")
	}

	/**
	 * 换行转义器。
	 */
	abstract class LineBreakEscaper : AbstractEscaper()

	/**
	 * 默认换行转义器。
	 *
	 * 转义换行符`\n`为`\\n`。
	 */
	object DefaultLineBreakEscaper : LineBreakEscaper() {
		override fun escape(value: String) = value.replace("\n", "\\n")
		override fun unescape(value: String) = value.replace("\\n", "\n")
	}

	/**
	 * Html换行转义器。
	 *
	 * 转义换行符`\n`为`<br>`。
	 */
	object HtmlLineBreakEscaper : LineBreakEscaper() {
		private const val escapedTag = "<br>"
		override fun escape(value: String) = value.replace("\n", escapedTag)
		override fun unescape(value: String) = value.replace(escapedTag, "\n", true)
	}
	//endregion

	override fun registerDefault() {
		register(KotlinEscaper)
		register(JavaEscaper)
		register(JavaScriptEscaper)
		register(JsonEscaper)
		register(XmlEscaper)
		register(XmlAttributeEscaper)
		register(XmlContentEscaper)
		register(HtmlEscaper)

		register(DefaultLineBreakEscaper)
		register(HtmlLineBreakEscaper)
	}
}
