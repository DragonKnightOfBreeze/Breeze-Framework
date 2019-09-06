@file:Suppress("NOTHING_TO_INLINE", "RemoveRedundantQualifierName", "UNCHECKED_CAST")

package com.windea.breezeframework.data.dsl

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.dsl.XmlConfig.autoCloseTag
import com.windea.breezeframework.data.dsl.XmlConfig.defaultRootName
import com.windea.breezeframework.data.dsl.XmlConfig.indent
import com.windea.breezeframework.data.dsl.XmlConfig.indentSize
import com.windea.breezeframework.data.dsl.XmlConfig.quote

//////////Portal extensions

/**开始生成Xml。*/
fun Dsl.Companion.xml(builder: Xml.() -> Unit) = Xml().builder()

/**开始配置Xml。*/
fun DslConfig.Companion.xml(builder: XmlConfig.() -> Unit) = XmlConfig.builder()

//////////Main class & Config object

/**Xml文件。*/
class Xml @PublishedApi internal constructor(
	/**注释列表。*/
	val comments: MutableList<XmlComment> = mutableListOf(),
	/**根元素。*/
	var rootElement: XmlElement = XmlElement(defaultRootName),
	override var wrapContent: Boolean = true
) : Dsl, XmlDslElement, CanWrapContent {
	override fun toString(): String {
		val commentsSnippet = if(wrapContent) comments.joinToString("\n") else comments.joinToString("")
		val wrapSnippet = if(wrapContent) "\n" else ""
		return "$commentsSnippet$wrapSnippet$rootElement"
	}
	
	/**添加注释。*/
	inline fun comment(comment: String) = XmlComment.create(comment).also { comments += it }
	
	/**添加元素。*/
	inline fun element(name: String, vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
		XmlElement.create(name, *attributes, builder = builder).also { rootElement = it }
	
	/**添加注释。*/
	inline operator fun String.unaryMinus() = comment(this)
	
	/**添加元素。*/
	inline operator fun String.invoke(vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
		element(this, *attributes, builder = builder)
}

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

///////////////Dsl marker annotations & Dsl element interfaces

@DslMarker
internal annotation class XmlDsl

/**Xml Dsl元素。*/
@XmlDsl
interface XmlDslElement

/**Xml结点。*/
interface XmlNode : XmlDslElement

////////////Dsl elements & build functions

/**Xml元素。*/
class XmlElement @PublishedApi internal constructor(
	/**名字。*/
	val name: String = "element",
	/**属性。*/
	val attributes: MutableMap<String, Any?> = mutableMapOf(),
	/**子结点列表。*/
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
		@PublishedApi
		internal inline fun create(name: String, vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
			XmlElement(name.fixXmlElementName(), attributes.toMap().fixXmlElementAttributes().toMutableMap()).also(builder)
	}
	
	/**添加文本。*/
	inline fun text(text: String) = XmlText.create(text).also { nodes += it }
	
	/**添加注释。*/
	inline fun comment(comment: String) = XmlComment.create(comment).also { nodes += it }
	
	/**添加元素。*/
	inline fun element(name: String, vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
		XmlElement.create(name, *attributes, builder = builder).also { nodes += it }
	
	/**设置属性。*/
	inline fun attribute(key: String, value: String) = run { this.attributes[key] = value }
	
	/**添加文本。*/
	inline operator fun String.unaryPlus() = text(this)
	
	/**添加注释。*/
	inline operator fun String.unaryMinus() = comment(this)
	
	/**添加元素。*/
	inline operator fun String.invoke(vararg attributes: Pair<String, Any?>, builder: XmlElement.() -> Unit) =
		element(this, *attributes, builder = builder)
	
	/**设置属性。*/
	inline operator fun String.invoke(value: String) = attribute(this, value)
}

/**Xml文本。*/
class XmlText @PublishedApi internal constructor(
	/**文本。*/
	var text: String = ""
) : XmlNode {
	override fun toString(): String {
		return text
	}
	
	companion object {
		@PublishedApi
		internal inline fun create(text: String) = XmlText(text.fixXmlText())
	}
}

/**Xml注释。*/
class XmlComment @PublishedApi internal constructor(
	/**注释。*/
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
		@PublishedApi
		internal inline fun create(comment: String) = XmlComment(comment.fixXmlText())
	}
}

//////////TODO Param handler extensions

@PublishedApi
internal fun String.fixXmlElementName() = this.trim()

@PublishedApi
internal fun Map<String, Any?>.fixXmlElementAttributes() = this.mapKeys { (k, _) -> k.trim() }

@PublishedApi
internal fun String.fixXmlText() = this.replace("<", "&lt;").replace(">", "&gt;")
