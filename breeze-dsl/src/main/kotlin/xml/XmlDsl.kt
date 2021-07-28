// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl.xml

import icu.windea.breezeframework.core.component.*
import icu.windea.breezeframework.core.extension.*
import icu.windea.breezeframework.core.type.*
import icu.windea.breezeframework.dsl.*

@XmlDslMarker
interface XmlDsl {
	@XmlDslMarker
	class DslDocument @PublishedApi internal constructor() : IDslDocument, DslEntry {
		val statements: MutableList<Statement> = mutableListOf()
		override val nodes: MutableList<Node> = mutableListOf()

		override fun renderTo(builder: StringBuilder) = renderText(builder) {
			if(DslConfig.validateDocument && nodes.count { it is Tag } != 1) {
				throw IllegalArgumentException("There must be one root tag in Xml document.")
			}

			if(statements.isNotEmpty()) {
				appendJoinWith(statements, "\n") { it.renderTo(this) }
			}
			if(nodes.isNotEmpty()) {
				appendJoinWith(nodes, "\n") { it.renderTo(this) }
			}
		}
	}

	@XmlDslMarker
	object DslConfig : IDslConfig {
		var indent: String = "  "
		var doubleQuoted: Boolean = true
		val quote get() = if(doubleQuoted) '"' else '\''

		var wrapCData: Boolean = false
		var indentCData: Boolean = false
		var wrapComment: Boolean = false
		var indentComment: Boolean = false
		var wrapTag: Boolean = false
		var indentTag: Boolean = false
		var autoCloseTag: Boolean = false

		var validateDocument: Boolean = true
	}

	@XmlDslMarker
	interface DslElement : IDslElement

	interface DslEntry : IDslEntry {
		val nodes: MutableList<Node>

		@XmlDslMarker
		operator fun String.unaryPlus() = text(this)

		@XmlDslMarker
		operator fun String.unaryMinus() = comment(this)

		@XmlDslMarker
		operator fun String.invoke(block: Block<Tag>): Tag = element(this, block = block)

		@XmlDslMarker
		operator fun String.invoke(vararg args: Arg, block: Block<Tag>): Tag = element(this, *args, block = block)
	}

	@XmlDslMarker
	class Statement @PublishedApi internal constructor(
		val attributes: Map<String, Any?> = mapOf(),
	) : DslElement {
		override fun renderTo(builder: StringBuilder) = renderText(builder) {
			append("<?xml")
			appendJoinWith(attributes, " ", " ") { (k, v) ->
				append(k).append(v.toString().escapeBy(Escapers.XmlAttributeEscaper).quote(DslConfig.quote))
			}
			append("?>")
		}

		override fun toString() = super.toString()
	}

	@XmlDslMarker
	abstract class Node : DslElement {

	}

	@XmlDslMarker
	class Text @PublishedApi internal constructor(val text: String) : Node() {
		override fun renderTo(builder: StringBuilder) = renderText(builder) {
			append(text.escapeBy(Escapers.XmlContentEscaper))
		}
	}

	@XmlDslMarker
	class CData @PublishedApi internal constructor(val text: String) : Node() {
		override fun renderTo(builder: StringBuilder) = renderText(builder) {
			append("![CDATA[")
			if(text.isNotEmpty()) {
				appendLineIf(DslConfig.wrapCData)
				append(text.prependIndentIf(DslConfig.indentCData, DslConfig.wrapCData, DslConfig.indent))
				appendLineIf(DslConfig.wrapCData)
			}
			append("]]>")
		}
	}

	@XmlDslMarker
	class Comment @PublishedApi internal constructor(val text: String) : Node() {
		override fun renderTo(builder: StringBuilder) = renderText(builder) {
			append("<!--")
			if(text.isNotEmpty()) {
				appendLineIf(DslConfig.wrapComment)
				append(text.escapeBy(Escapers.XmlContentEscaper)
					.prependIndentIf(DslConfig.indentComment, DslConfig.wrapComment, DslConfig.indent))
				appendLineIf(DslConfig.wrapComment)
			}
			append("-->")
		}
	}

	@XmlDslMarker
	class Tag @PublishedApi internal constructor(
		val name: String,
		val attributes: Map<String, Any?> = mapOf()
	) : Node(), DslEntry {
		override val nodes: MutableList<Node> = mutableListOf()

		override fun renderTo(builder: StringBuilder) = renderText(builder) {
			append("<").append(name)
			if(attributes.isNotEmpty()) {
				appendJoinWith(attributes, " ", " ") { (k, v) ->
					append(k).append("=").append(v.toString().escapeBy(Escapers.XmlAttributeEscaper))
				}
			}
			append(">")
			if(nodes.isNotEmpty()) {
				appendLineIf(DslConfig.wrapTag)
				if(DslConfig.indentTag) {
					append(buildString { appendJoinWith(nodes, "\n") { it.renderTo(this) } }.prependIndent(DslConfig.indent))
				} else {
					appendJoinWith(nodes, "\n") { it.renderTo(this) }
				}
				appendLineIf(DslConfig.wrapTag)
			}
			if(DslConfig.autoCloseTag && nodes.isEmpty()) append("/>") else append("</").append(name).append(">")
		}
	}
}


