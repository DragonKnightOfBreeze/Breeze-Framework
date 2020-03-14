@file:Suppress("unused", "NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.xml

import com.windea.breezeframework.core.domain.text.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.xml.Xml.Config.autoCloseTag
import com.windea.breezeframework.dsl.xml.Xml.Config.defaultRootName
import com.windea.breezeframework.dsl.xml.Xml.Config.indent
import com.windea.breezeframework.dsl.xml.Xml.Config.quote

/**Xml。*/
@XmlDsl
interface Xml {
	/**Xml文档。*/
	@XmlDsl
	class Document @PublishedApi internal constructor() : DslDocument, XmlDslEntry, WithComment<Comment>, WithBlock<Element> {
		val statements:MutableList<Statement> = mutableListOf()
		val comments:MutableList<Comment> = mutableListOf()
		var rootElement:Element = Element(defaultRootName)

		override fun toString():String {
			return listOfNotNull(
				statements.orNull()?.joinToString("\n"),
				comments.orNull()?.joinToString("\n"),
				rootElement.toString()
			).joinToString("\n")
		}

		override fun String.unaryMinus() = comment(this)

		override fun String.invoke(block:Element.() -> Unit) = element(this, block = block)

		operator fun String.invoke(vararg args:Pair<String, Any?>, block:Element.() -> Unit = {}) =
			element(this, *args, block = block)
	}

	/**Xml声明。*/
	@XmlDsl
	class Statement @PublishedApi internal constructor(
		val name:String, val attributes:Map<String, String> = mapOf()
	) : XmlDslElement {
		override fun toString():String {
			val attributesSnippet = attributes.orNull()?.joinToString(" ", " ") { (k, v) -> "$k=${v.quote(quote)}" }.orEmpty()
			return "<?$name$attributesSnippet?>"
		}
	}

	/**Xml结点。*/
	@XmlDsl
	interface Node : XmlDslElement

	/**Xml文本。*/
	@XmlDsl
	inline class Text constructor(val text:String) : Node {
		override fun toString() = text.escapeBy(EscapeType.Xml) //必要的转义
	}

	/**Xml注释。*/
	@XmlDsl
	class Comment @PublishedApi internal constructor(val text:String) : Node, CanWrapLine, CanIndent {
		override var wrapContent = false
		override var indentContent = false

		override fun toString():String {
			val textSnippet = text.escapeBy(EscapeType.Xml).doIndent(indent, wrapContent)
				.let { if(wrapContent) "\n$it\n" else it }
			return "<!--$textSnippet-->"
		}
	}

	/**Xml元素。*/
	@XmlDsl
	class Element @PublishedApi internal constructor(
		val name:String, val attributes:Map<String, String> = mapOf()
	) : Node, CanWrapLine, CanIndent, WithText<Text>, WithComment<Comment>, WithBlock<Element>, WithId {
		val nodes:MutableList<Node> = mutableListOf()

		override var wrapContent = true
		override var indentContent = true
		override val id get() = name

		override fun toString():String {
			val attributesSnippet = attributes.orNull()?.joinToString(" ", " ") { (k, v) -> "$k=${v.quote(quote)}" }.orEmpty()
			val nodesSnippet = nodes.joinToString(wrapSeparator).doIndent(indent, wrapContent)
				.let { if(wrapContent) "\n$it\n" else it }
			val prefixSnippet = "<$name$attributesSnippet>"
			val suffixSnippet = if(nodes.isEmpty() && autoCloseTag) "/>" else "</$name>"
			return "$prefixSnippet$nodesSnippet$suffixSnippet"
		}

		override fun String.unaryPlus() = text(this)

		override fun String.unaryMinus() = comment(this)

		override fun String.invoke(block:Element.() -> Unit) = element(this, block = block)

		operator fun String.invoke(vararg args:Pair<String, Any?>, block:Element.() -> Unit = {}) =
			element(this, *args, block = block)
	}

	/**Xml配置。*/
	@XmlDsl
	object Config {
		var indent:String = "  "
		var preferDoubleQuote:Boolean = true
		var autoCloseTag:Boolean = false
		var defaultRootName:String = "root"
			set(value) = run { if(value.isNotBlank()) field = value }
		val quote get() = if(preferDoubleQuote) '"' else '\''
	}
}
