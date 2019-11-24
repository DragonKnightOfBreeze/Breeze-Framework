@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "unused")

package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.core.enums.core.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markup.XmlConfig.autoCloseTag
import com.windea.breezeframework.dsl.markup.XmlConfig.defaultRootName
import com.windea.breezeframework.dsl.markup.XmlConfig.indent
import com.windea.breezeframework.dsl.markup.XmlConfig.quote

//region top annotations and interfaces
/**Xml的Dsl。*/
@DslMarker
private annotation class XmlDsl

/**Xml。*/
@XmlDsl
class Xml @PublishedApi internal constructor() : DslBuilder,
	WithComment<XmlComment>, WithBlock<XmlElement> {
	val statements: MutableList<XmlStatement> = mutableListOf()
	val comments: MutableList<XmlComment> = mutableListOf()
	var rootElement: XmlElement = XmlElement(defaultRootName)
	
	override fun toString(): String {
		return arrayOf(
			statements.joinToStringOrEmpty("\n"),
			comments.joinToStringOrEmpty("\n"),
			rootElement.toString()
		).filterNotEmpty().joinToStringOrEmpty("\n")
	}
	
	@GenericDsl
	override fun String.unaryMinus() = comment(this)
	
	@GenericDsl
	override fun String.invoke(block: XmlElement.() -> Unit) = element(this, block = block)
	
	@GenericDsl
	operator fun String.invoke(vararg args: Pair<String, Any?>, block: XmlElement.() -> Unit = {}) =
		element(this, *args, block = block)
}

/**Xml的配置。*/
@XmlDsl
object XmlConfig : DslConfig {
	private val indentSizeRange = -2..8
	
	var indentSize: Int = 2
		set(value) = run { if(value in indentSizeRange) field = value }
	var preferDoubleQuote: Boolean = true
	var defaultRootName: String = "root"
		set(value) = run { if(value.isNotBlank()) field = value }
	var autoCloseTag: Boolean = false
	
	@PublishedApi internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	@PublishedApi internal val quote get() = if(preferDoubleQuote) '"' else '\''
}
//endregion

//region dsl interfaces
/**Xml Dsl的元素。*/
@XmlDsl
interface XmlDslElement : DslElement
//endregion

//region dsl elements
/**Xml声明。*/
@XmlDsl
class XmlStatement @PublishedApi internal constructor(
	val name: String,
	val attributes: Map<String, String> = mapOf()
) : XmlDslElement {
	override fun toString(): String {
		val attributesSnippet = attributes.joinToStringOrEmpty(" ", " ") { (k, v) -> "$k=${v.wrapQuote(quote)}" }
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
		return text.escapeBy(EscapeType.Xml)
	}
}

/**Xml注释。*/
@XmlDsl
class XmlComment @PublishedApi internal constructor(
	val text: String
) : XmlNode(), CanWrapContent, CanIndentContent {
	override var wrapContent: Boolean = false
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val textSnippet = text.escapeBy(EscapeType.Xml)._applyIndent(indent, wrapContent)
			.let { if(wrapContent) "\n$it\n" else it }
		return "<!--$textSnippet-->"
	}
}

/**Xml元素。*/
@XmlDsl
class XmlElement @PublishedApi internal constructor(
	val name: String,
	val attributes: Map<String, String> = mapOf()
) : XmlNode(), CanWrapContent, CanIndentContent,
	WithText<XmlText>, WithComment<XmlComment>, WithBlock<XmlElement>, WithName {
	val nodes: MutableList<XmlNode> = mutableListOf()
	
	override var wrapContent: Boolean = true
	override var indentContent: Boolean = true
	
	override val _name: String get() = name
	
	override fun toString(): String {
		val attributesSnippet = attributes.joinToStringOrEmpty(" ", " ") { (k, v) -> "$k=${v.wrapQuote(quote)}" }
		val nodesSnippet = nodes.joinToStringOrEmpty(_wrap)._applyIndent(indent, wrapContent)
			.let { if(wrapContent) "\n$it\n" else it }
		val prefixSnippet = "<$name$attributesSnippet>"
		val suffixSnippet = if(nodes.isEmpty() && autoCloseTag) "/>" else "</$name>"
		return "$prefixSnippet$nodesSnippet$suffixSnippet"
	}
	
	@GenericDsl
	override fun String.unaryPlus() = text(this)
	
	@GenericDsl
	override fun String.unaryMinus() = comment(this)
	
	@GenericDsl
	override fun String.invoke(block: XmlElement.() -> Unit) = element(this, block = block)
	
	@GenericDsl
	operator fun String.invoke(vararg args: Pair<String, Any?>, block: XmlElement.() -> Unit = {}) =
		element(this, *args, block = block)
}
//endregion

//region build extensions
@XmlDsl
inline fun xml(block: Xml.() -> Unit) = Xml().also { it.block() }

@XmlDsl
inline fun Xml.statement(name: String, vararg attributes: Pair<String, Any?>) =
	XmlStatement(name, attributes.toMap().toStringValueMap()).also { statements += it }

@XmlDsl
inline fun Xml.comment(text: String) =
	XmlComment(text).also { comments += it }

@XmlDsl
inline fun Xml.element(name: String, vararg attributes: Pair<String, Any?>, block: XmlElement.() -> Unit = {}) =
	XmlElement(name, attributes.toMap().toStringValueMap()).also { it.block() }.also { rootElement = it }

@XmlDsl
inline fun XmlElement.text(text: String) =
	XmlText(text).also { nodes += it }

@XmlDsl
inline fun XmlElement.comment(text: String) =
	XmlComment(text).also { nodes += it }

@XmlDsl
inline fun XmlElement.element(name: String, vararg attributes: Pair<String, Any?>, block: XmlElement.() -> Unit = {}) =
	XmlElement(name, attributes.toMap().toStringValueMap()).also { it.block() }.also { nodes += it }
//endregion
