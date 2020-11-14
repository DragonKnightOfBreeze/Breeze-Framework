// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.xml

import com.windea.breezeframework.core.components.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.core.types.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.api.*

@XmlDslMarker
interface XmlDsl {
	@XmlDslMarker
	class Document @PublishedApi internal constructor() : DslDocument {
		val declarations: MutableList<Statement> = mutableListOf()
		val comments: MutableList<Comment> = mutableListOf()
		var rootElement: Element? = null

		operator fun String.unaryMinus(): Comment = comment(this)
		operator fun String.invoke(block: Block<Element>): Element = element(this, block = block)
		operator fun String.invoke(vararg args: Arg, block: Block<Element>): Element = element(this, *args, block = block)

		override fun toString(): String {
			require(rootElement != null) { "Root element of Xml document cannot be null." }
			return arrayOf(
				declarations.joinToText("\n"),
				comments.joinToText("\n"),
				rootElement
			).joinToText("\n")
		}
	}

	@XmlDslMarker
	object Config : DslConfig {
		var indent: String = "  "
		var doubleQuoted: Boolean = true
		var autoCloseTag: Boolean = false

		val quote get() = if(doubleQuoted) '"' else '\''
	}

	@XmlDslMarker
	interface DslElement : com.windea.breezeframework.dsl.DslElement

	@XmlDslMarker
	class Statement @PublishedApi internal constructor(
		val attributes: Map<String, Any?> = mapOf(),
	) : DslElement {
		override fun toString(): String {
			val attributesSnippet = attributes.joinToText(" ", " ") { (k, v) ->
				"$k=${v.toString().escapeBy(Escaper.XmlAttributeEscaper).quote(Config.quote)}"
			}
			return "<?xml$attributesSnippet?>"
		}
	}

	@XmlDslMarker
	interface Node : DslElement

	@XmlDslMarker
	class Text @PublishedApi internal constructor(
		val text: String,
	) : Node {
		override fun toString(): String {
			return text.escapeBy(Escaper.XmlContentEscaper)
		}
	}

	@XmlDslMarker
	class CData @PublishedApi internal constructor(
		val text: String,
	) : Node, Wrappable, Indentable {
		override var wrapContent   = text.isMultiline()
		override var indentContent = true

		override fun toString(): String {
			return when {
				wrapContent && indentContent -> "![CDATA[\n${text.prependIndent(Config.indent)}\n]]>"
				wrapContent -> "![CDATA[\n$text\n]]>"
				else -> "![CDATA[${text.prependIndent(Config.indent)}]]>"
			}
		}
	}

	@XmlDslMarker
	class Comment @PublishedApi internal constructor(
		val text: String
	) : Node, Wrappable, Indentable {
		override var wrapContent  = text.isMultiline()
		override var indentContent = true

		override fun toString(): String {
			val textSnippet = text.escapeBy(Escaper.XmlContentEscaper)
			return when{
				wrapContent && indentContent -> "<!--\n${textSnippet.prependIndent(Config.indent)}\n-->"
				wrapContent -> "<!--\n$textSnippet\n-->"
				else -> "<!--$textSnippet-->"
			}
		}
	}

	@XmlDslMarker
	class Element @PublishedApi internal constructor(
		val name: String,
		val attributes: Map<String, Any?> = mapOf(),
	) : Node, Wrappable, Indentable {
		val nodes: MutableList<Node> = mutableListOf()
		override var wrapContent = true
		override var indentContent = true

		operator fun String.unaryPlus() = text(this)
		operator fun String.unaryMinus() = comment(this)
		operator fun String.invoke(block: Block<Element>) = element(this, block = block)
		operator fun String.invoke(vararg args: Arg, block: Block<Element>) = element(this, *args, block = block)

		override fun toString(): String {
			val nodesSnippet = nodes.joinToText("\n")
			val attributesSnippet = attributes.joinToText(" ", " ") { (k, v) ->
				"$k=${v.toString().escapeBy(Escaper.XmlAttributeEscaper).quote(Config.quote)}"
			}
			val prefixSnippet = "<$name$attributesSnippet>"
			val suffixSnippet = if(Config.autoCloseTag && nodes.isEmpty()) "/>" else "</$name>"
			return when{
				wrapContent && indentContent -> "$prefixSnippet\n${nodesSnippet.prependIndent(Config.indent)}\n$suffixSnippet"
				wrapContent  -> "$prefixSnippet\n$nodesSnippet\n$suffixSnippet"
				else -> "$prefixSnippet$nodesSnippet$suffixSnippet"
			}
		}
	}
}

