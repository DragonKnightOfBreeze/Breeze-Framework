@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "unused")

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
private annotation class XmlDsl

//REGION Dsl & Dsl config & Dsl elements

/**Xml。*/
@XmlDsl
class Xml @PublishedApi internal constructor() : DslBuilder, WrapContent, WithComment<XmlComment>, WithBlock<XmlElement> {
	val statements: MutableList<XmlStatement> = mutableListOf(
		XmlStatement("xml", mapOf("version" to "1.0", "encoding" to "UTF-8"))
	)
	val comments: MutableList<XmlComment> = mutableListOf()
	var rootElement: XmlElement = XmlElement(defaultRootName)
	
	override var wrapContent: Boolean = true
	
	override fun toString(): String {
		return if(wrapContent) {
			arrayOf(statements.joinToString("\n"), comments.joinToString("\n"), rootElement.toString())
				.filterNotEmpty().joinToString("\n")
		} else {
			arrayOf(statements.joinToString(""), comments.joinToString(""), rootElement.toString())
				.filterNotEmpty().joinToString("")
		}
	}
	
	@XmlDsl
	override fun String.unaryMinus() = comment(this)
	
	@XmlDsl
	override fun String.invoke(): XmlElement = element(this)
	
	@XmlDsl
	override fun String.invoke(builder: XmlElement.() -> Unit) = element(this, builder = builder)
	
	@XmlDsl
	operator fun String.invoke(vararg args: Pair<String, Any?>) = element(this, *args)
	
	@XmlDsl
	operator fun String.invoke(vararg args: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
		element(this, *args, builder = builder)
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
	
	@PublishedApi internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	@PublishedApi internal val quote get() = if(useDoubleQuote) "\"" else "'"
}


/**Xml Dsl的元素。*/
@XmlDsl
interface XmlDslElement : DslElement


/**Xml声明。*/
@XmlDsl
class XmlStatement @PublishedApi internal constructor(
	val name: String,
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

/**Xml文本。*/
@XmlDsl
class XmlText @PublishedApi internal constructor(
	val text: String
) : XmlNode() {
	override fun toString(): String {
		return text.escapeXml()
	}
}

/**Xml注释。*/
@XmlDsl
class XmlComment @PublishedApi internal constructor(
	val text: String
) : XmlNode(), WrapContent, IndentContent {
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

/**Xml元素。*/
@XmlDsl
class XmlElement @PublishedApi internal constructor(
	val name: String,
	val attributes: Map<String, String> = mapOf()
) : XmlNode(), WrapContent, IndentContent, InlineContent, WithText<XmlText>, WithComment<XmlComment>, WithBlock<XmlElement> {
	val nodes: MutableList<XmlNode> = mutableListOf()
	
	override var wrapContent: Boolean = true
	override var indentContent: Boolean = true
	override var inlineContent: Boolean = false
	
	override fun toString(): String {
		val attributesSnippet = when {
			attributes.isEmpty() -> ""
			else -> attributes.joinToString(" ", " ") { (k, v) -> "$k=${v.wrapQuote(quote)}" }
		}
		val contentSnippet = when {
			inlineContent -> nodes.last().toString()
			wrapContent -> nodes.joinToString("\n")
			else -> nodes.joinToString("")
		}
		val indentedContentSnippet = when {
			indentContent -> contentSnippet.prependIndent(indent)
			else -> contentSnippet
		}
		val wrappedContentSnippet = when {
			wrapContent -> "\n$indentedContentSnippet\n"
			//when no wrap with indent, trim first indent string.
			!wrapContent && indentContent -> indentedContentSnippet.drop(indentSize)
			else -> indentedContentSnippet
		}
		val prefixSnippet = "<$name$attributesSnippet>"
		val suffixSnippet = if(nodes.isEmpty() && autoCloseTag) "/>" else "</$name>"
		return "$prefixSnippet$wrappedContentSnippet$suffixSnippet"
	}
	
	
	@XmlDsl
	override fun String.unaryPlus() = text(this)
	
	@XmlDsl
	override fun String.unaryMinus() = comment(this)
	
	@XmlDsl
	override fun String.invoke() = element(this)
	
	@XmlDsl
	override fun String.invoke(builder: XmlElement.() -> Unit) = element(this, builder = builder)
	
	@XmlDsl
	operator fun String.invoke(vararg args: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
		element(this, *args, builder = builder)
}

//REGION Build extensions

@XmlDsl
inline fun xml(builder: Xml.() -> Unit) =
	Xml().also { it.builder() }


@XmlDsl
inline fun Xml.statement(name: String, vararg attributes: Pair<String, Any?>) =
	XmlStatement(name, attributes.toMap().toStringValueMap()).also { statements += it }

@XmlDsl
inline fun Xml.comment(text: String) =
	XmlComment(text).also { comments += it }

@XmlDsl
inline fun Xml.element(name: String, vararg attributes: Pair<String, Any?>) =
	XmlElement(name, attributes.toMap().toStringValueMap()).also { rootElement = it }

@XmlDsl
inline fun Xml.element(name: String, vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
	XmlElement(name, attributes.toMap().toStringValueMap()).also { it.builder() }.also { rootElement = it }


@XmlDsl
inline fun XmlElement.text(text: String) =
	XmlText(text).also { nodes += it }

@XmlDsl
inline fun XmlElement.comment(text: String) =
	XmlComment(text).also { nodes += it }

@XmlDsl
inline fun XmlElement.element(name: String, vararg attributes: Pair<String, Any?>) =
	XmlElement(name, attributes.toMap().toStringValueMap()).also { nodes += it }

@XmlDsl
inline fun XmlElement.element(name: String, vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
	XmlElement(name, attributes.toMap().toStringValueMap()).also { it.builder() }.also { nodes += it }
