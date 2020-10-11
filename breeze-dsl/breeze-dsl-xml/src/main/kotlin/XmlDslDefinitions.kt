// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.xml

import com.windea.breezeframework.core.domain.text.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.core.types.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslConstants.ls
import com.windea.breezeframework.dsl.xml.XmlDslConfig.autoCloseTag
import com.windea.breezeframework.dsl.xml.XmlDslConfig.indent
import com.windea.breezeframework.dsl.xml.XmlDslConfig.quote

/**
 * Dsl definitions of [XmlDsl].
 */
@XmlDslMarker
interface XmlDslDefinitions {
	/**
	 * Dsl element of [XmlDsl].
	 */
	@XmlDslMarker
	interface IDslElement : DslElement

	/**
	 * Statement of [XmlDsl].
	 */
	@XmlDslMarker
	class Statement @PublishedApi internal constructor(
		val attributes: Map<String, Any?> = mapOf()
	) : IDslElement {
		override fun toString(): String {
			val attributesSnippet = attributes.joinToText(" ", " ") { (k, v) ->
				"$k=${v.toString().escapeBy(EscapeType.XmlAttribute).quote(quote)}"
			}
			return "<?xmlDsl$attributesSnippet?>"
		}
	}

	/**
	 * Node of [XmlDsl].
	 */
	@XmlDslMarker
	interface Node : IDslElement

	/**
	 * Text of [XmlDsl].
	 */
	@XmlDslMarker
	class Text @PublishedApi internal constructor(val text: String) : Node {
		override fun toString(): String {
			return text.escapeBy(EscapeType.Xml)
		}
	}

	/**
	 * CData text of [XmlDsl].
	 */
	@XmlDslMarker
	class CData @PublishedApi internal constructor(val text:String) : Node, Wrappable, Indentable {
		override var wrapContent = true
		override var indentContent = true

		override fun toString():String {
			val textSnippet = text.doIndent(indent, wrapContent).doWrap { "$ls$ls$ls" }
			return "![CDATA[$textSnippet]]>"
		}
	}

	/**
	 * Comment of [XmlDsl].
	 */
	@XmlDslMarker
	class Comment @PublishedApi internal constructor(val text: String) : Node, Wrappable, Indentable {
		override var wrapContent = false
		override var indentContent = true

		override fun toString(): String {
			val textSnippet = text.escapeBy(EscapeType.Xml).doIndent(indent, wrapContent).doWrap { "$ls$it$ls" }
			return "<!--$textSnippet-->"
		}
	}

	/**
	 * Element of [XmlDsl].
	 */
	@XmlDslMarker
	class Element @PublishedApi internal constructor(
		val name: String,
		val attributes: Map<String, Any?> = mapOf()
	) : Node, Wrappable, Indentable, WithId {
		val nodes: MutableList<Node> = mutableListOf()
		override var wrapContent = true
		override var indentContent = true
		override val id get() = name

		operator fun String.unaryPlus() = text(this)
		operator fun String.unaryMinus() = comment(this)
		operator fun String.invoke(block:Block<Element>) = element(this, block = block)
		operator fun String.invoke(vararg args:Arg, block:Block<Element>) = element(this, *args, block = block)

		override fun toString():String {
			val nodesSnippet = nodes.joinToText(ls).doIndent(indent, wrapContent).doWrap { "$ls$it$ls" }
			val attributesSnippet = attributes.joinToText(" ", " ") { (k, v) ->
				"$k=${v.toString().escapeBy(EscapeType.XmlAttribute).quote(quote)}"
			}
			val prefixSnippet = "<$name$attributesSnippet>"
			val suffixSnippet = if(autoCloseTag && nodes.isEmpty()) "/>" else "</$name>"
			return "$prefixSnippet$nodesSnippet$suffixSnippet"
		}
	}
}

