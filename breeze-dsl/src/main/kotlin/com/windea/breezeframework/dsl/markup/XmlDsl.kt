@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markup.XmlConfig.autoCloseTag
import com.windea.breezeframework.dsl.markup.XmlConfig.defaultRootName
import com.windea.breezeframework.dsl.markup.XmlConfig.indent
import com.windea.breezeframework.dsl.markup.XmlConfig.indentSize
import com.windea.breezeframework.dsl.markup.XmlConfig.quote

//REGION Dsl annotations

@DslMarker
internal annotation class XmlDsl

//REGION Dsl & Dsl elements & Dsl config

/**Xml。*/
@XmlDsl
class Xml @PublishedApi internal constructor() : Dsl, CanWrapContent, CommentContent<XmlComment>, BlockContent<XmlElement> {
	/**声明列表。*/
	val statements: MutableList<XmlStatement> = mutableListOf(
		XmlStatement("xml", mapOf("version" to "1.0", "encoding" to "UTF-8"))
	)
	/**注释列表。*/
	val comments: MutableList<XmlComment> = mutableListOf()
	/**根元素。*/
	var rootElement: XmlElement = XmlElement(defaultRootName)
	
	override var wrapContent: Boolean = true
	
	override fun toString(): String {
		val n = if(wrapContent) "\n" else ""
		return arrayOf(
			statements.joinToString(n),
			comments.joinToString(n),
			rootElement.toString()
		).filterNotEmpty().joinToString(n)
	}
	
	@XmlDsl
	override fun String.not() = comment(this)
	
	@XmlDsl
	override fun String.invoke(builder: XmlElement.() -> Unit) = element(this, builder = builder)
	
	@XmlDsl
	fun String.invoke(vararg args: Pair<String, Any?>, builder: XmlElement.() -> Unit) = element(this, *args, builder = builder)
}

/**Xml Dsl的元素。*/
@XmlDsl
interface XmlDslElement : DslElement

/**Xml声明。*/
@XmlDsl
class XmlStatement @PublishedApi internal constructor(
	/**名字。*/
	val name: String,
	/**属性。*/
	val attributes: Map<String, String> = mapOf()
) : XmlDslElement {
	override fun toString(): String {
		val attributesSnippet = when {
			attributes.isEmpty() -> ""
			else -> attributes.joinToString(" ", " ") { (k, v) -> "$k=${v.wrapQuote(quote)}" }
		}
		return "<?$name$attributesSnippet?>"
	}
}

/**Xml结点。*/
@XmlDsl
sealed class XmlNode : XmlDslElement

/**Xml元素。*/
@XmlDsl
class XmlElement @PublishedApi internal constructor(
	/**名字。*/
	val name: String,
	/**属性。*/
	val attributes: Map<String, String> = mapOf()
) : XmlNode(), CanWrapContent, CanIndentContent, InlineContent<XmlText>,
	CommentContent<XmlComment>, BlockContent<XmlElement> {
	/**子结点列表。*/
	val nodes: MutableList<XmlNode> = mutableListOf()
	
	override var wrapContent: Boolean = true
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val attributesSnippet = when {
			attributes.isEmpty() -> ""
			else -> attributes.joinToString(" ", " ") { (k, v) -> "$k=${v.wrapQuote(quote)}" }
		}
		val elementsSnippet = if(wrapContent) nodes.joinToString("\n") else nodes.joinToString("")
		val indentedElementsSnippet = if(indentContent) elementsSnippet.prependIndent(indent) else elementsSnippet
		val wrappedElementsSnippet = when {
			wrapContent -> "\n$indentedElementsSnippet\n"
			//when no wrap with indent, trim first indent string.
			!wrapContent && indentContent -> indentedElementsSnippet.drop(indentSize)
			else -> indentedElementsSnippet
		}
		val prefixSnippet = "<$name$attributesSnippet>"
		val suffixSnippet = if(nodes.isEmpty() && autoCloseTag) "/>" else "</$name>"
		return "$prefixSnippet$wrappedElementsSnippet$suffixSnippet"
	}
	
	@XmlDsl
	override fun String.unaryPlus() = text(this)
	
	@XmlDsl
	override fun String.unaryMinus() = run { nodes.clear();text(this) }
	
	@XmlDsl
	override fun String.not() = comment(this)
	
	@XmlDsl
	override fun String.invoke(builder: XmlElement.() -> Unit) = element(this, builder = builder)
	
	@XmlDsl
	fun String.invoke(vararg args: Pair<String, Any?>, builder: XmlElement.() -> Unit) = element(this, *args, builder = builder)
}

/**Xml注释。*/
@XmlDsl
class XmlComment @PublishedApi internal constructor(
	/**注释文本。*/
	val text: String
) : XmlNode(), CanWrapContent, CanIndentContent {
	override var wrapContent: Boolean = false
	override var indentContent: Boolean = false
	
	override fun toString(): String {
		val textSnippet = text.escapeXml()
		val indentedTextSnippet = if(indentContent) textSnippet.prependIndent(indent) else textSnippet
		val wrappedTextSnippet = when {
			wrapContent -> "\n$indentedTextSnippet\n"
			//when no wrap with indent, trim first indent string.
			!wrapContent && indentContent -> indentedTextSnippet.drop(indentSize)
			else -> indentedTextSnippet
		}
		return "<!--$wrappedTextSnippet-->"
	}
}

/**Xml文本。*/
@XmlDsl
class XmlText @PublishedApi internal constructor(
	/**文本。*/
	val text: String
) : XmlNode() {
	override fun toString(): String {
		return text.escapeXml()
	}
}

/**Xml配置。*/
@XmlDsl
object XmlConfig : DslConfig {
	/**默认根元素名。*/
	var defaultRootName: String = "root"
		set(value) = run { field = value.ifBlank { "root" } }
	
	/**缩进长度。*/
	var indentSize = 2
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**是否使用双引号。*/
	var useDoubleQuote: Boolean = true
	/**是否自关闭标签。*/
	var autoCloseTag: Boolean = false
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(useDoubleQuote) "\"" else "'"
}

//REGION Build extensions

/**构建Xml。*/
@XmlDsl
inline fun xml(builder: Xml.() -> Unit) = Xml().also { it.builder() }

/**添加声明。*/
@XmlDsl
inline fun Xml.statement(name: String, vararg attributes: Pair<String, Any?>) =
	XmlStatement(name, attributes.toMap().toStringValueMap()).also { statements += it }

/**添加注释。*/
@XmlDsl
inline fun Xml.comment(text: String) =
	XmlComment(text).also { comments += it }

/**添加元素。*/
@XmlDsl
inline fun Xml.element(name: String, vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
	XmlElement(name, attributes.toMap().toStringValueMap()).also { it.builder() }.also { rootElement = it }

/**添加文本。*/
@XmlDsl
inline fun XmlElement.text(text: String) =
	XmlText(text).also { nodes += it }

/**添加注释。*/
@XmlDsl
inline fun XmlElement.comment(text: String) =
	XmlComment(text).also { nodes += it }

/**添加元素。*/
@XmlDsl
inline fun XmlElement.element(name: String, vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
	XmlElement(name, attributes.toMap().toStringValueMap()).also { it.builder() }.also { nodes += it }
