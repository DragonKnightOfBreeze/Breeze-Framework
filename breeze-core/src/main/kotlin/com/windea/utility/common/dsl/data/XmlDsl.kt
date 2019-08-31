@file:Suppress("UNUSED_PARAMETER")

package com.windea.utility.common.dsl.data

import com.windea.utility.common.dsl.*
import com.windea.utility.common.dsl.data.XmlDslConfig.defaultName
import com.windea.utility.common.dsl.data.XmlDslConfig.defaultRootName
import com.windea.utility.common.dsl.data.XmlDslConfig.indent
import com.windea.utility.common.dsl.data.XmlDslConfig.preferAutoClosedTag
import com.windea.utility.common.dsl.data.XmlDslConfig.quote
import com.windea.utility.common.extensions.*
import java.lang.annotation.*

/**Xml Dsl。*/
data class XmlDsl @PublishedApi internal constructor(
	override val name: String,
	val comments: MutableList<XmlComment> = mutableListOf(),
	var root: XmlElement = XmlElement(defaultRootName)
) : Dsl, XmlDslElement {
	override fun toString(): String {
		val commentsSnippet = this.comments.joinToString("\n")
		return "$commentsSnippet\n$root"
	}
}

/**Xml Dsl的配置。*/
object XmlDslConfig : DslConfig {
	const val defaultName: String = "xml"
	const val defaultRootName: String = "root"
	var indentSize: Int = 2
		set(value) {
			field = value.coerceIn(2, 8)
		}
	var preferDoubleQuote: Boolean = true
	var preferAutoClosedTag: Boolean = false
	
	internal val indent get() = " ".repeat(indentSize)
	internal val quote get() = if(preferDoubleQuote) "\"" else "'"
}


/**Xml Dsl标记。*/
@DslMarker
internal annotation class XmlDslMarker

/**Xml Dsl的扩展功能。*/
@MustBeDocumented
@Inherited
internal annotation class ExtendedXmlFeature


/**Xml Dsl的元素。*/
@XmlDslMarker
interface XmlDslElement

/**Xml Dsl的可换行元素。*/
interface XmlDslNewLineElement : XmlDslElement {
	var newLine: Boolean
}

/**Xml Dsl的可以空行分割内容的元素。*/
interface XmlDslBlankLineElement : XmlDslElement {
	var blankLineSize: Int
}


/**Xml注释。*/
data class XmlComment @PublishedApi internal constructor(
	val text: String,
	override var newLine: Boolean = false
) : XmlDslNewLineElement {
	override fun toString(): String {
		val textSnippet = if(newLine) "\n${text.prependIndent(indent)}\n" else text
		return "<!--$textSnippet-->"
	}
}

/**Xml元素。*/
data class XmlElement @PublishedApi internal constructor(
	val name: String,
	val attributes: Map<String, Any?> = mapOf(),
	val text: String? = null,
	val content: MutableList<XmlDslElement> = mutableListOf(),
	override var newLine: Boolean = true,
	override var blankLineSize: Int = 0
) : XmlDslNewLineElement, XmlDslBlankLineElement {
	override fun toString(): String {
		val attributesSnippet = when {
			attributes.isEmpty() -> ""
			else -> attributes.joinToString(" ", " ") { (k, v) -> "$k=$quote$v$quote" }
		}
		val textSnippet = text?.let { if(newLine) "\n${it.prependIndent(indent)}\n" else text } ?: ""
		val innerTextSnippet = when {
			textSnippet.isEmpty() -> when {
				newLine -> "\n${content.joinToString("\n" + "\n".repeat(blankLineSize)).prependIndent(indent)}\n"
				else -> content.joinToString("")
			}
			textSnippet.isEmpty() && content.isEmpty() -> ""
			else -> textSnippet
		}
		val prefixMarkers = "<$name$attributesSnippet>"
		val suffixMarkers = if(innerTextSnippet.isEmpty() && preferAutoClosedTag) "/>" else "</$name>"
		return "$prefixMarkers$innerTextSnippet$suffixMarkers"
	}
	
	operator fun String.unaryPlus() = this@XmlElement.text(this)
	
	operator fun String.unaryMinus() = this@XmlElement.text(this, true)
	
	operator fun XmlDslElement.plus(text: String) = this@XmlElement.text(text)
	
	operator fun XmlDslElement.plus(element: XmlDslElement) = element
}

/**Xml文本。*/
data class XmlText @PublishedApi internal constructor(
	val text: String
) : XmlDslElement {
	override fun toString(): String {
		return text
	}
}


/**构建Xml Dsl。*/
inline fun Dsl.Companion.xml(name: String = defaultName, content: XmlDsl.() -> Unit) =
	XmlDsl(name).also { it.content() }

/**配置Xml Dsl。*/
inline fun DslConfig.Companion.xml(config: XmlDslConfig.() -> Unit) = XmlDslConfig.config()


/**创建Xml注释。*/
fun XmlDsl.comment(comment: String) = XmlComment(comment).also { this.comments += it }

/**创建Xml元素。*/
fun XmlDsl.element(name: String, vararg attributes: Pair<String, Any?>) =
	XmlElement(name, attributes.toMap(), newLine = false).also { this.root = it }

/**创建Xml元素。默认缩进子元素。*/
inline fun XmlDsl.element(name: String, vararg attributes: Pair<String, Any?>, content: XmlElement.() -> Unit) =
	XmlElement(name, attributes.toMap()).also { it.content() }.also { this.root = it }


/**创建Xml注释。*/
fun XmlElement.comment(comment: String) = XmlComment(comment).also { this.content += it }

/**创建Xml元素。*/
fun XmlElement.element(name: String, vararg attributes: Pair<String, Any?>) =
	XmlElement(name, attributes.toMap(), newLine = false).also { this.content += it }

/**创建Xml元素。默认缩进子元素。*/
inline fun XmlElement.element(name: String, vararg attributes: Pair<String, Any?>, content: XmlElement.() -> Unit) =
	XmlElement(name, attributes.toMap()).also { it.content() }.also { this.content += it }

/**创建Xml文本。*/
fun XmlElement.text(text: String, clearContent: Boolean = false) =
	XmlText(text).also { if(clearContent) this.content.clear() }.also { this.content += it }


/**配置当前元素的换行。默认换行。*/
fun <T : XmlDslNewLineElement> T.n(newLine: Boolean = true) = this.also { it.newLine = newLine }

/**配置当前元素的内容间空行数量。默认为1。*/
fun <T : XmlDslBlankLineElement> T.bn(blankLineSize: Int = 1) = this.also { it.blankLineSize = blankLineSize }
