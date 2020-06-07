@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.xml

import com.windea.breezeframework.core.domain.text.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.core.types.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslConstants.ls

/**
 * Xml。
 */
@XmlDsl
interface Xml {
	/**
	 * Xml文档。
	 * @property declarations 声明一览。
	 * @property comments 注释一览。
	 * @property rootElement 根元素。
	 */
	@XmlDsl
	class Document @PublishedApi internal constructor() : Dsl {
		val declarations:MutableList<Statement> = mutableListOf()
		val comments:MutableList<Comment> = mutableListOf()
		var rootElement:Element? = null

		operator fun String.unaryMinus():Comment = comment(this)
		operator fun String.invoke(block:Block<Element>):Element = element(this, block = block)
		operator fun String.invoke(vararg args:Arg, block:Block<Element>):Element = element(this, *args, block = block)

		override fun toString():String {
			require(rootElement != null) { "Root element of Xml document cannot be null." }
			return arrayOf(declarations.typingAll(ls), comments.typingAll(ls), rootElement).typingAll(ls)
		}
	}


	/**
	 * Xml领域特定语言的元素。
	 */
	@XmlDsl
	interface IDslElement : DslElement

	/**
	 * Xml声明。
	 * @property attributes 特性一览。
	 */
	@XmlDsl
	class Statement @PublishedApi internal constructor(
		val attributes:Map<String, Any?> = mapOf()
	) : IDslElement {
		override fun toString():String {
			val attributesSnippet = attributes.typingAll(" ", " ") { (k, v) ->
				"$k=${v.toString().escapeBy(EscapeType.XmlAttribute).quote(config.quote)}"
			}
			return "<?xml$attributesSnippet?>"
		}
	}

	/**
	 * Xml结点。
	 */
	@XmlDsl
	interface Node : IDslElement

	/**
	 * Xml文本。
	 * @property text 内容文本。
	 */
	@XmlDsl
	inline class Text @PublishedApi internal constructor(val text:String) : Node {
		override fun toString():String {
			return text.escapeBy(EscapeType.Xml)
		}
	}

	/**
	 * Xml CDATA文本。
	 * @property text 内容文本。
	 */
	class CData @PublishedApi internal constructor(val text:String) : Node, Wrappable, Indentable {
		override var wrapContent = true
		override var indentContent = true

		override fun toString():String {
			val textSnippet = text.doIndent(config.indent, wrapContent).doWrap { "$ls$ls$ls" }
			return "![CDATA[$textSnippet]]>"
		}
	}

	/**
	 * Xml注释。
	 * @property text 内容文本。
	 */
	@XmlDsl
	class Comment @PublishedApi internal constructor(val text:String) : Node, Wrappable, Indentable {
		override var wrapContent = false
		override var indentContent = true

		override fun toString():String {
			val textSnippet = text.escapeBy(EscapeType.Xml).doIndent(config.indent, wrapContent).doWrap { "$ls$it$ls" }
			return "<!--$textSnippet-->"
		}
	}

	/**
	 * Xml元素。
	 * @property name 元素名。
	 * @property attributes 特性一览。
	 * @property nodes 子节点一览。
	 */
	@XmlDsl
	class Element @PublishedApi internal constructor(
		val name:String,
		val attributes:Map<String, Any?> = mapOf()
	) : Node, Wrappable, Indentable, WithId {
		val nodes:MutableList<Node> = mutableListOf()
		override var wrapContent = true
		override var indentContent = true
		override val id get() = name

		operator fun String.unaryPlus() = text(this)
		operator fun String.unaryMinus() = comment(this)
		operator fun String.invoke(block:Block<Element>) = element(this, block = block)
		operator fun String.invoke(vararg args:Arg, block:Block<Element>) = element(this, *args, block = block)

		override fun toString():String {
			val nodesSnippet = nodes.typingAll(ls).doIndent(config.indent, wrapContent).doWrap { "$ls$it$ls" }
			val attributesSnippet = attributes.typingAll(" ", " ") { (k, v) ->
				"$k=${v.toString().escapeBy(EscapeType.XmlAttribute).quote(config.quote)}"
			}
			val prefixSnippet = "<$name$attributesSnippet>"
			val suffixSnippet = if(config.autoCloseTag && nodes.isEmpty()) "/>" else "</$name>"
			return "$prefixSnippet$nodesSnippet$suffixSnippet"
		}
	}


	/**
	 * Xml配置。
	 * @property indent 文本缩进。默认为2个空格。
	 * @property doubleQuoted 是否偏向使用双引号。默认为是。
	 * @property autoCloseTag 是否自关闭标签。默认为否。
	 */
	@XmlDsl
	data class Config(
		var indent:String = "  ",
		var doubleQuoted:Boolean = true,
		var autoCloseTag:Boolean = false
	) {
		val quote get() = if(doubleQuoted) '"' else '\''
	}

	companion object {
		val config = Config()
	}
}
