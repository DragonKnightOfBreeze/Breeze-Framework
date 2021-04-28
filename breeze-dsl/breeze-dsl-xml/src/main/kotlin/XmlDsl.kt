// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl.xml

import icu.windea.breezeframework.core.component.*
import icu.windea.breezeframework.core.extension.*
import icu.windea.breezeframework.core.type.*
import icu.windea.breezeframework.dsl.api.*
import icu.windea.breezeframework.dsl.DslConfig as IDslConfig
import icu.windea.breezeframework.dsl.DslDocument as IDslDocument
import icu.windea.breezeframework.dsl.DslElement as IDslElement


@XmlDslMarker
interface XmlDsl {
	@XmlDslMarker
	class DslDocument @PublishedApi internal constructor() : IDslDocument {
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
	object DslConfig : IDslConfig {
		var indent: String = "  "
		var doubleQuoted: Boolean = true
		var autoCloseTag: Boolean = false

		val quote get() = if(doubleQuoted) '"' else '\''
	}

	@XmlDslMarker
	interface DslElement : IDslElement

	@XmlDslMarker
	class Statement @PublishedApi internal constructor(
		val attributes: Map<String, Any?> = mapOf(),
	) : DslElement {
		override fun toString(): String {
			val attributesSnippet = attributes.joinToText(" ", " ") { (k, v) ->
				"$k=${v.toString().escapeBy(Escaper.XmlAttributeEscaper).quote(DslConfig.quote)}"
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
		override var wrapContent = text.isMultiline()
		override var indentContent = true

		override fun toString(): String {
			return when {
				wrapContent && indentContent -> "![CDATA[\n${text.prependIndent(DslConfig.indent)}\n]]>"
				wrapContent -> "![CDATA[\n$text\n]]>"
				else -> "![CDATA[${text.prependIndent(DslConfig.indent)}]]>"
			}
		}
	}

	@XmlDslMarker
	class Comment @PublishedApi internal constructor(
		val text: String
	) : Node, Wrappable, Indentable {
		override var wrapContent = text.isMultiline()
		override var indentContent = true

		override fun toString(): String {
			val textSnippet = text.escapeBy(Escaper.XmlContentEscaper)
			return when {
				wrapContent && indentContent -> "<!--\n${textSnippet.prependIndent(DslConfig.indent)}\n-->"
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
				"$k=${v.toString().escapeBy(Escaper.XmlAttributeEscaper).quote(DslConfig.quote)}"
			}
			val prefixSnippet = "<$name$attributesSnippet>"
			val suffixSnippet = if(DslConfig.autoCloseTag && nodes.isEmpty()) "/>" else "</$name>"
			return when {
				wrapContent && indentContent -> "$prefixSnippet\n${nodesSnippet.prependIndent(DslConfig.indent)}\n$suffixSnippet"
				wrapContent -> "$prefixSnippet\n$nodesSnippet\n$suffixSnippet"
				else -> "$prefixSnippet$nodesSnippet$suffixSnippet"
			}
		}
	}
}

