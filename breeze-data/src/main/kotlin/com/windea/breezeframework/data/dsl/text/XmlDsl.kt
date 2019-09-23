@file:Suppress("NOTHING_TO_INLINE", "RemoveRedundantQualifierName", "UNCHECKED_CAST", "SimpleRedundantLet", "CanBePrimaryConstructorProperty")

package com.windea.breezeframework.data.dsl.text

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.dsl.*
import com.windea.breezeframework.data.dsl.text.XmlConfig.autoCloseTag
import com.windea.breezeframework.data.dsl.text.XmlConfig.defaultRootName
import com.windea.breezeframework.data.dsl.text.XmlConfig.indent
import com.windea.breezeframework.data.dsl.text.XmlConfig.indentSize
import com.windea.breezeframework.data.dsl.text.XmlConfig.quote

//REGION Dsl marker annotations & Dsl element interfaces

@DslMarker
internal annotation class XmlDsl

/**Xml Dsl元素。*/
@XmlDsl
interface XmlDslElement

/**Xml结点。*/
@XmlDsl
interface XmlNode : XmlDslElement

//REGION Dsl elements & Build functions

/**构建Xml。*/
@XmlDsl
fun xml(builder: Xml.() -> Unit) = Xml().also { it.builder() }

/**Xml文件。*/
@XmlDsl
class Xml @PublishedApi internal constructor() : XmlDslElement, CanWrapContent, DslBuilder {
	/**声明列表。*/
	val statements: MutableList<XmlStatement> = mutableListOf(
		XmlStatement("xml", "version" to "1.0", "encoding" to "UTF-8")
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
	
	
	/**添加声明。*/
	inline fun statement(name: String, vararg attributes: Pair<String, Any?>) =
		XmlStatement(name, *attributes).also { statements += it }
	
	/**添加注释。*/
	@XmlDsl
	inline fun comment(comment: String) = XmlComment(comment).also { comments += it }
	
	/**添加元素。*/
	@XmlDsl
	inline fun element(name: String, vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit = {}) =
		XmlElement(name, *attributes).also { it.builder() }.also { rootElement = it }
	
	/**@see Xml.comment*/
	@XmlDsl
	inline operator fun String.unaryMinus() = comment(this)
	
	/**@see Xml.element*/
	@XmlDsl
	inline operator fun String.invoke(vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit = {}) =
		element(this, *attributes) { builder() }
}

/**Xml声明。*/
class XmlStatement @PublishedApi internal constructor(
	name: String,
	vararg attributes: Pair<String, Any?>
) : XmlDslElement {
	/**名字。*/
	val name: String = name //NOTE do not ensure argument is valid
	/**属性。*/
	val attributes: Map<String, String> = attributes.toMap().toStringValueMap() //NOTE do not ensure argument is valid
	
	override fun toString(): String {
		val attributesSnippet = when {
			attributes.isEmpty() -> ""
			else -> attributes.joinToString(" ", " ") { (k, v) -> "$k=${v.wrapQuote(quote)}" }
		}
		return "<?$name$attributesSnippet?>"
	}
}

/**Xml元素。*/
@XmlDsl
class XmlElement @PublishedApi internal constructor(
	name: String,
	vararg attributes: Pair<String, Any?>
) : XmlNode, CanWrapContent, CanIndentContent {
	/**名字。*/
	val name = name //NOTE do not ensure argument is valid
	/**属性。*/
	val attributes: Map<String, String> = attributes.toMap().toStringValueMap() //NOTE do not ensure argument is valid
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
	
	
	/**添加文本。*/
	@XmlDsl
	inline fun text(text: String) = XmlText(text).also { nodes += it }
	
	/**添加注释。*/
	@XmlDsl
	inline fun comment(comment: String) = XmlComment(comment).also { nodes += it }
	
	/**添加元素。*/
	@XmlDsl
	inline fun element(name: String, vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit = {}) =
		XmlElement(name, *attributes).also { it.builder() }.also { nodes += it }
	
	/**@see XmlElement.text*/
	@XmlDsl
	inline operator fun String.unaryPlus() = text(this)
	
	/**@see XmlElement.comment*/
	@XmlDsl
	inline operator fun String.unaryMinus() = comment(this)
	
	/**@see XmlElement.element*/
	@XmlDsl
	inline operator fun String.invoke(vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit = {}) =
		element(this, *attributes) { builder() }
}

/**Xml文本。*/
@XmlDsl
class XmlText @PublishedApi internal constructor(
	text: String
) : XmlNode {
	/**文本。*/
	var text: String = text.escapeXml() //NOTE do not ensure argument is valid
	
	
	override fun toString(): String {
		return text
	}
}

/**Xml注释。*/
@XmlDsl
class XmlComment @PublishedApi internal constructor(
	comment: String
) : XmlNode, CanWrapContent, CanIndentContent {
	/**注释。*/
	var comment: String = comment.escapeXml() //NOTE do not ensure argument is valid
	
	override var wrapContent: Boolean = false
	override var indentContent: Boolean = false
	
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
}

//REGION Config object

/**Xml配置。*/
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
