@file:Suppress("unused", "NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.xml

import com.windea.breezeframework.core.domain.text.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.xml.Xml.Config.autoCloseTag
import com.windea.breezeframework.dsl.xml.Xml.Config.defaultRootName
import com.windea.breezeframework.dsl.xml.Xml.Config.indent
import com.windea.breezeframework.dsl.xml.Xml.Config.quote

//region dsl top declarations
/**Xml的Dsl。*/
@DslMarker
@MustBeDocumented
annotation class XmlDsl

/**Xml。*/
@XmlDsl
class Xml @PublishedApi internal constructor() : DslDocument, WithComment<XmlComment>, WithBlock<XmlElement> {
	val statements: MutableList<XmlStatement> = mutableListOf()
	val comments: MutableList<XmlComment> = mutableListOf()
	var rootElement: XmlElement = XmlElement(defaultRootName)

	override fun toString(): String {
		return listOfNotNull(
			statements.orNull()?.joinToString("\n"),
			comments.orNull()?.joinToString("\n"),
			rootElement.toString()
		).joinToString("\n")
	}

	@XmlDsl
	override fun String.unaryMinus() = comment(this)

	@XmlDsl
	override fun String.invoke(block: XmlElement.() -> Unit) = element(this, block = block)

	@XmlDsl
	operator fun String.invoke(vararg args: Pair<String, Any?>, block: XmlElement.() -> Unit = {}) =
		element(this, *args, block = block)

	/**Xml的配置。*/
	@XmlDsl
	object Config {
		var indent: String = "  "
		var preferDoubleQuote: Boolean = true
		var autoCloseTag: Boolean = false
		var defaultRootName: String = "root"
			set(value) = run { if(value.isNotBlank()) field = value }
		val quote get() = if(preferDoubleQuote) '"' else '\''
	}
}


/**Xml声明。*/
@XmlDsl
class XmlStatement @PublishedApi internal constructor(
	val name: String, val attributes: Map<String, String> = mapOf()
) : DslElement {
	override fun toString(): String {
		val attributesSnippet = attributes.orNull()?.joinToString(" ", " ") { (k, v) -> "$k=${v.quote(quote)}" }.orEmpty()
		return "<?$name$attributesSnippet?>"
	}
}

/**Xml结点。*/
@XmlDsl
interface XmlNode : DslElement

/**Xml文本。*/
@XmlDsl
inline class XmlText constructor(val text: String) : XmlNode {
	override fun toString() = text.escapeBy(EscapeType.Xml) //必要的转义
}

/**Xml注释。*/
@XmlDsl
class XmlComment @PublishedApi internal constructor(val text: String) : XmlNode, CanWrapLine, CanIndent {
	override var wrapContent = false
	override var indentContent = false

	override fun toString(): String {
		val textSnippet = text.escapeBy(EscapeType.Xml).doIndent(indent, wrapContent)
			.let { if(wrapContent) "\n$it\n" else it }
		return "<!--$textSnippet-->"
	}
}

/**Xml元素。*/
@XmlDsl
class XmlElement @PublishedApi internal constructor(
	val name: String, val attributes: Map<String, String> = mapOf()
) : XmlNode, CanWrapLine, CanIndent, WithText<XmlText>, WithComment<XmlComment>, WithBlock<XmlElement>, WithId {
	val nodes: MutableList<XmlNode> = mutableListOf()

	override var wrapContent = true
	override var indentContent = true
	override val id get() = name

	override fun toString(): String {
		val attributesSnippet = attributes.orNull()?.joinToString(" ", " ") { (k, v) -> "$k=${v.quote(quote)}" }.orEmpty()
		val nodesSnippet = nodes.joinToString(wrapSeparator).doIndent(indent, wrapContent)
			.let { if(wrapContent) "\n$it\n" else it }
		val prefixSnippet = "<$name$attributesSnippet>"
		val suffixSnippet = if(nodes.isEmpty() && autoCloseTag) "/>" else "</$name>"
		return "$prefixSnippet$nodesSnippet$suffixSnippet"
	}

	@XmlDsl
	override fun String.unaryPlus() = text(this)

	@XmlDsl
	override fun String.unaryMinus() = comment(this)

	@XmlDsl
	override fun String.invoke(block: XmlElement.() -> Unit) = element(this, block = block)

	@XmlDsl
	operator fun String.invoke(vararg args: Pair<String, Any?>, block: XmlElement.() -> Unit = {}) =
		element(this, *args, block = block)
}
//endregion

//region dsl build extensions
@XmlDsl
inline fun xml(block: Xml.() -> Unit) = Xml().apply(block)

@XmlDsl
inline fun Xml.statement(name: String, vararg attributes: Pair<String, Any?>) =
	XmlStatement(name, attributes.toMap().toStringValueMap()).also { statements += it }

@XmlDsl
inline fun Xml.comment(text: String) =
	XmlComment(text).also { comments += it }

@XmlDsl
inline fun Xml.element(name: String, vararg attributes: Pair<String, Any?>, block: XmlElement.() -> Unit = {}) =
	XmlElement(name, attributes.toMap().toStringValueMap()).apply(block).also { rootElement = it }

@XmlDsl
inline fun XmlElement.text(text: String) =
	XmlText(text).also { nodes += it }

@XmlDsl
inline fun XmlElement.comment(text: String) =
	XmlComment(text).also { nodes += it }

@XmlDsl
inline fun XmlElement.element(name: String, vararg attributes: Pair<String, Any?>, block: XmlElement.() -> Unit = {}) =
	XmlElement(name, attributes.toMap().toStringValueMap()).apply(block).also { nodes += it }
//endregion
