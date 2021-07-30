// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl.xml

import icu.windea.breezeframework.core.component.*
import icu.windea.breezeframework.core.extension.*
import icu.windea.breezeframework.core.type.*
import icu.windea.breezeframework.dsl.*

@DslMarker
@MustBeDocumented
annotation class XmlDslMarker

@XmlDslMarker
object XmlDslConfig : DslConfig {
	var indent: String = "  "
	var doubleQuoted: Boolean = true
	val quote get() = if(doubleQuoted) '"' else '\''

	var wrapCData: Boolean = true
	var indentCData: Boolean = true
	var wrapComment: Boolean = true
	var indentComment: Boolean = true
	var wrapTag: Boolean = true
	var indentTag: Boolean = true
	var autoCloseTag: Boolean = true

	var validateDocument: Boolean = true
}

@XmlDslMarker
class XmlDslDocument @PublishedApi internal constructor() : DslDocument, XmlDslEntry {
	val statements: MutableList<XmlStatement> = mutableListOf()
	override val nodes: MutableList<XmlNode> = mutableListOf()

	override fun renderTo(builder: StringBuilder) = renderText(builder) {
		if(XmlDslConfig.validateDocument && nodes.count { it is XmlTag } != 1) {
			throw IllegalArgumentException("There must be one root tag in Xml document.")
		}

		if(statements.isNotEmpty()) {
			appendJoinWith(statements, "\n") { it.renderTo(this) }
		}
		if(nodes.isNotEmpty()) {
			appendJoinWith(nodes, "\n") { it.renderTo(this) }
		}
	}

	override fun toString() = render()
}

@XmlDslMarker
interface XmlDslElement : DslElement

interface XmlDslEntry : DslContent {
	val nodes: MutableList<XmlNode>

	@XmlDslMarker
	operator fun String.unaryPlus() = text(this)

	@XmlDslMarker
	operator fun String.unaryMinus() = comment(this)

	@XmlDslMarker
	operator fun String.invoke(block: Block<XmlTag>): XmlTag = element(this, block = block)

	@XmlDslMarker
	operator fun String.invoke(vararg args: Arg, block: Block<XmlTag>): XmlTag = element(this, *args, block = block)
}

@XmlDslMarker
class XmlStatement @PublishedApi internal constructor(
	val attributes: Map<String, Any?> = mapOf(),
) : XmlDslElement {
	override fun renderTo(builder: StringBuilder) = renderText(builder) {
		append("<?xml")
		appendJoinWith(attributes, " ", " ") { (k, v) ->
			append(k).append(v.toString().escapeBy(Escapers.XmlAttributeEscaper).quote(XmlDslConfig.quote))
		}
		append("?>")
	}

	override fun toString() = render()
}

@XmlDslMarker
abstract class XmlNode : XmlDslElement{
	override fun toString() = render()
}

@XmlDslMarker
class XmlText @PublishedApi internal constructor(
	val text: String
) : XmlNode() {
	override fun renderTo(builder: StringBuilder) = renderText(builder) {
		append(text.escapeBy(Escapers.XmlContentEscaper))
	}
}

@XmlDslMarker
class XmlCData @PublishedApi internal constructor(
	val text: String
) : XmlNode() {
	override fun renderTo(builder: StringBuilder) = renderText(builder) {
		append("![CDATA[")
		if(text.isNotEmpty()) {
			appendLineIf(XmlDslConfig.wrapCData)
			append(text.prependIndentIf(XmlDslConfig.indentCData, XmlDslConfig.wrapCData, XmlDslConfig.indent))
			appendLineIf(XmlDslConfig.wrapCData)
		}
		append("]]>")
	}
}

@XmlDslMarker
class XmlComment @PublishedApi internal constructor(
	val text: String
) : XmlNode() {
	override fun renderTo(builder: StringBuilder) = renderText(builder) {
		append("<!--")
		if(text.isNotEmpty()) {
			appendLineIf(XmlDslConfig.wrapComment)
			append(text.escapeBy(Escapers.XmlContentEscaper)
				.prependIndentIf(XmlDslConfig.indentComment, XmlDslConfig.wrapComment, XmlDslConfig.indent))
			appendLineIf(XmlDslConfig.wrapComment)
		}
		append("-->")
	}
}

@XmlDslMarker
class XmlTag @PublishedApi internal constructor(
	val name: String,
	val attributes: Map<String, Any?> = mapOf()
) : XmlNode(), XmlDslEntry {
	override val nodes: MutableList<XmlNode> = mutableListOf()

	override fun renderTo(builder: StringBuilder) = renderText(builder) {
		append("<").append(name)
		if(attributes.isNotEmpty()) {
			appendJoinWith(attributes, " ", " ") { (k, v) ->
				append(k).append("=").append(v.toString().escapeBy(Escapers.XmlAttributeEscaper))
			}
		}
		append(">")
		if(nodes.isNotEmpty()) {
			appendLineIf(XmlDslConfig.wrapTag)
			if(XmlDslConfig.indentTag) {
				append(buildString { appendJoinWith(nodes, "\n") { it.renderTo(this) } }.prependIndent(XmlDslConfig.indent))
			} else {
				appendJoinWith(nodes, "\n") { it.renderTo(this) }
			}
			appendLineIf(XmlDslConfig.wrapTag)
		}
		if(XmlDslConfig.autoCloseTag && nodes.isEmpty()) append("/>") else append("</").append(name).append(">")
	}
}


