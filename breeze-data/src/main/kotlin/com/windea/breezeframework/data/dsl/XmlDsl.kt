@file:Suppress("NOTHING_TO_INLINE", "RemoveRedundantQualifierName", "UNCHECKED_CAST")

package com.windea.breezeframework.data.dsl

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.dsl.XmlConfig.autoCloseTag
import com.windea.breezeframework.data.dsl.XmlConfig.defaultRootName
import com.windea.breezeframework.data.dsl.XmlConfig.indent
import com.windea.breezeframework.data.dsl.XmlConfig.indentSize
import com.windea.breezeframework.data.dsl.XmlConfig.quote

//////////Portal extensions

fun Dsl.Companion.xml(builder: Xml.() -> Unit) = Xml.create(builder)

fun DslConfig.Companion.xml(builder: XmlConfig.() -> Unit) = XmlConfig.builder()

//////////Config object

object XmlConfig : DslConfig {
	var defaultRootName: String = "root"
		set(value) = run { field = value.ifBlank { "root" } }
	var indentSize = 2
		set(value) = run { field = value.coerceIn(-2, 8) }
	var useDoubleQuote: Boolean = true
	var autoCloseTag: Boolean = false
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(useDoubleQuote) "\"" else "'"
}

///////////////Dsl marker annotations & Dsl element interfaces

@DslMarker
annotation class XmlDsl

@XmlDsl
interface XmlDslElement

interface XmlNode : XmlDslElement

////////////Dsl elements & build functions

class Xml @PublishedApi internal constructor(
	val comments: MutableList<XmlComment> = mutableListOf(),
	var rootElement: XmlElement = XmlElement(defaultRootName),
	override var wrapContent: Boolean = true
) : Dsl, XmlDslElement, CanWrapContent {
	override fun toString(): String {
		val commentsSnippet = if(wrapContent) comments.joinToString("\n") else comments.joinToString("")
		val wrapSnippet = if(wrapContent) "\n" else ""
		return "$commentsSnippet$wrapSnippet$rootElement"
	}
	
	companion object {
		inline fun create(builder: Xml.() -> Unit) = Xml().also(builder)
	}
	
	inline fun comment(comment: String) = XmlComment.create(comment).also { comments += it }
	
	inline fun element(name: String, vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
		XmlElement.create(name, *attributes, builder = builder).also { rootElement = it }
	
	inline operator fun String.unaryMinus() = comment(this)
	
	inline operator fun String.invoke(vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
		element(this, *attributes, builder = builder)
}

class XmlElement @PublishedApi internal constructor(
	val name: String = "element",
	val attributes: Map<String, Any?> = mapOf(),
	val nodes: MutableList<XmlNode> = mutableListOf(),
	override var wrapContent: Boolean = true,
	override var indentContent: Boolean = true
) : XmlNode, CanWrapContent, CanIndentContent {
	override fun toString(): String {
		val attributesSnippet = attributes.joinToString(" ") { (k, v) -> "$k=$quote$v$quote" }.ifNotEmpty { " $it" }
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
	
	companion object {
		inline fun create(name: String, vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
			XmlElement(name.fixXmlElementName(), attributes.toMap().fixXmlElementAttributes()).also(builder)
	}
	
	inline fun text(text: String) = XmlText.create(text).also { nodes += it }
	
	inline fun comment(comment: String) = XmlComment.create(comment).also { nodes += it }
	
	inline fun element(name: String, vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
		XmlElement.create(name, *attributes, builder = builder).also { nodes += it }
	
	inline operator fun String.unaryPlus() = text(this)
	
	inline operator fun String.unaryMinus() = comment(this)
	
	inline operator fun String.invoke(vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
		element(this, *attributes, builder = builder)
}

class XmlText @PublishedApi internal constructor(
	var text: String = ""
) : XmlNode {
	override fun toString(): String {
		return text
	}
	
	companion object {
		inline fun create(text: String) = XmlText(text.fixXmlText())
	}
}

class XmlComment @PublishedApi internal constructor(
	var comment: String = "",
	override var wrapContent: Boolean = false,
	override var indentContent: Boolean = false
) : XmlNode, CanWrapContent, CanIndentContent {
	override fun toString(): String {
		val indentedCommentSnippet = if(indentContent) comment.prependIndent(indent) else comment
		val wrappedCommentSnippet = when {
			wrapContent -> "\n$indentedCommentSnippet\n"
			//when no wrap with indent, trim first indent string.
			!wrapContent && indentContent -> indentedCommentSnippet.drop(indentSize)
			else -> indentedCommentSnippet
		}
		return "<!--$wrappedCommentSnippet-->"
	}
	
	companion object {
		inline fun create(comment: String) = XmlComment(comment.fixXmlText())
	}
}

//////////Param handler extensions

@PublishedApi
internal fun String.fixXmlElementName() = this.trim()

@PublishedApi
internal fun Map<String, Any?>.fixXmlElementAttributes() = this.mapKeys { (k, _) -> k.trim() }

@PublishedApi
internal fun String.fixXmlText() = this.replace("<", "&lt;").replace(">", "&gt;")
