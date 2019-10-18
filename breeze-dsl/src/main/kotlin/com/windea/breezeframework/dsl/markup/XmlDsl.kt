@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST", "unused")

package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.core.enums.core.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markup.XmlConfig.autoCloseTag
import com.windea.breezeframework.dsl.markup.XmlConfig.defaultRootName
import com.windea.breezeframework.dsl.markup.XmlConfig.indent
import com.windea.breezeframework.dsl.markup.XmlConfig.quote

//REGION Dsl annotations

@DslMarker
private annotation class XmlDsl

//REGION Dsl & Dsl config & Dsl elements

/**Xml。*/
@XmlDsl
class Xml @PublishedApi internal constructor() : DslBuilder, WithComment<XmlComment>, WithBlock<XmlElement> {
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

/**Xml的配置。*/
@XmlDsl
object XmlConfig : DslConfig {
	/**默认根元素名。*/
	var defaultRootName: String = "root"
		set(value) = run { field = value.ifBlank { "root" } }
	/**缩进长度。*/
	var indentSize: Int = 2
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**是否使用双引号。*/
	var preferDoubleQuote: Boolean = true
	/**是否自关闭标签。*/
	var autoCloseTag: Boolean = false
	
	@PublishedApi internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	@PublishedApi internal val quote get() = if(preferDoubleQuote) '"' else '\''
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
		return text.escape(EscapeType.InXml)
	}
}

/**Xml注释。*/
@XmlDsl
class XmlComment @PublishedApi internal constructor(
	val text: String
) : XmlNode(), WrapContent, IndentContent {
	override var wrapContent: Boolean = false
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val textSnippet = text.escape(EscapeType.InXml)
			.let { if(wrapContent && indentContent) it.prependIndent(indent) else it }
			.let { if(wrapContent) "\n$it\n" else it }
		return "<!--$textSnippet-->"
	}
}

/**Xml元素。*/
@XmlDsl
class XmlElement @PublishedApi internal constructor(
	val name: String,
	val attributes: Map<String, String> = mapOf()
) : XmlNode(), WrapContent, IndentContent, WithText<XmlText>, WithComment<XmlComment>, WithBlock<XmlElement> {
	val nodes: MutableList<XmlNode> = mutableListOf()
	
	override var wrapContent: Boolean = true
	override var indentContent: Boolean = true
	
	override fun toString(): String {
		val attributesSnippet = attributes.joinToStringOrEmpty(" ", " ") { (k, v) -> "$k=${v.wrapQuote(quote)}" }
		val nodesSnippet = nodes.joinToStringOrEmpty(if(wrapContent) "\n" else "")
			.let { if(wrapContent && indentContent) it.prependIndent(indent) else it }
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
	override fun String.invoke() = element(this)
	
	@XmlDsl
	override fun String.invoke(builder: XmlElement.() -> Unit) = element(this, builder = builder)
	
	@XmlDsl
	operator fun String.invoke(vararg args: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
		element(this, *args, builder = builder)
}

//REGION Build extensions

@XmlDsl
object XmlInlineBuilder {
	@XmlDsl
	inline fun element(name: String, vararg attributes: Pair<String, Any?>) =
		XmlElement(name, attributes.toMap().toStringValueMap())
	
	@XmlDsl
	inline fun element(name: String, vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
		XmlElement(name, attributes.toMap().toStringValueMap()).also { it.builder() }
	
	@XmlDsl
	operator fun String.invoke(vararg args: Pair<String, Any?>) = element(this, *args)
	
	@XmlDsl
	operator fun String.invoke(vararg args: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
		element(this, *args, builder = builder)
}


@XmlDsl
inline fun xml(builder: Xml.() -> Unit) =
	Xml().also { it.builder() }

@XmlDsl
inline fun Xml.statement(name: String, vararg attributes: Pair<String, Any?>) =
	XmlStatement(name, attributes.toMap().toStringValueMap()).also { statements += it }

@XmlDsl
inline fun Xml.defaultStatement() = statement("xml", "version" to "1.0", "encoding" to "UTF-8")

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
