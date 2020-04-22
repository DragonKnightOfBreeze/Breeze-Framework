@file:Suppress("CanBeParameter")

package com.windea.breezeframework.dsl.bbcode

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslConstants.ls

interface BBCode {
	/**
	 * BBCode的文档。
	 * @property text 内容文本。
	 */
	class Document @PublishedApi internal constructor() : DslDocument {
		var text:CharSequence = ""

		override fun toString():String = text.toString()
	}

	/**
	 * BBCode的配置。
	 * @property indent 文本缩进。
	 */
	data class Config(
		var indent:String = "  "
	)


	interface DslElement : com.windea.breezeframework.dsl.DslElement, Inlineable {
		override val text:CharSequence
	}

	interface InlineDslElement : DslElement {
		override val text:CharSequence
	}

	interface CrosslineDslElement : DslElement {
		override val text:CharSequence
	}

	interface BlockDslElement : DslElement, Indentable {
		val contentText:String
		override val text:CharSequence get() = contentText.ifNotEmpty { ls + it.doIndent(config.indent) + ls }
	}

	abstract class Element @PublishedApi internal constructor(val tag:String) : DslElement {
		override fun toString():String = "[$tag]$text[/$tag]"
	}

	abstract class OneArgElement @PublishedApi internal constructor(
		tag:String,
		val value:String?
	) : Element(tag) {
		override fun toString():String = "[$tag${value.typing { "=$it" }}]$text[/$tag]"
	}

	abstract class MultiArgElement @PublishedApi internal constructor(
		tag:String,
		val args:Map<String, String?>
	) : Element(tag) {
		override fun toString():String = "[$tag${args.typingAll(" ", " ") { (k, v) -> "$k=$v" }}]$text[/$tag]"
	}

	abstract class RichText @PublishedApi internal constructor(
		tag:String,
		override val text:CharSequence
	) : Element(tag), InlineDslElement

	class BoldText @PublishedApi internal constructor(override val text:CharSequence) : Element("b"), InlineDslElement

	class ItalicText @PublishedApi internal constructor(override val text:CharSequence) : Element("i"), InlineDslElement

	class UnderlinedText @PublishedApi internal constructor(override val text:CharSequence) : Element("u"), InlineDslElement

	class StrikeText @PublishedApi internal constructor(override val text:CharSequence) : Element("strike"), InlineDslElement

	class SpoilerText @PublishedApi internal constructor(override val text:CharSequence) : Element("spoiler"), InlineDslElement

	class NoParseText @PublishedApi internal constructor(override val text:CharSequence) : Element("noparse"), InlineDslElement

	class LeftText @PublishedApi internal constructor(override val text:CharSequence) : Element("left"), InlineDslElement

	class CenterText @PublishedApi internal constructor(override val text:CharSequence) : Element("center"), InlineDslElement

	class RightText @PublishedApi internal constructor(override val text:CharSequence) : Element("right"), InlineDslElement

	class Code @PublishedApi internal constructor(override val text:CharSequence) : Element("code"), InlineDslElement

	class StyledText @PublishedApi internal constructor(
		val size:String?,
		val color:String?,
		override val text:CharSequence
	) : MultiArgElement("style", mapOf("size" to size, "color" to color)), InlineDslElement

	class YoutubeVideo @PublishedApi internal constructor(override val text:CharSequence) : Element("youtube"), InlineDslElement

	class Link @PublishedApi internal constructor(
		val url:String?,
		override val text:CharSequence
	) : OneArgElement("url", url), InlineDslElement

	class Image @PublishedApi internal constructor(
		val width:String?,
		val height:String?,
		override val text:CharSequence
	) : MultiArgElement("img", mapOf("width" to width, "height" to height)), InlineDslElement

	abstract class Heading @PublishedApi internal constructor(
		tag:String,
		override val text:CharSequence
	) : Element(tag), CrosslineDslElement

	class Heading1 @PublishedApi internal constructor(text:CharSequence) : Heading("h1", text)

	class Heading2 @PublishedApi internal constructor(text:CharSequence) : Heading("h1", text)

	class Heading3 @PublishedApi internal constructor(text:CharSequence) : Heading("h1", text)

	class Heading4 @PublishedApi internal constructor(text:CharSequence) : Heading("h1", text)

	class List @PublishedApi internal constructor() : Element("list"), BlockDslElement {
		val nodes:MutableList<ListNode> = mutableListOf()
		override val contentText:String get() = nodes.typingAll(ls)
		override var indentContent:Boolean = true
	}

	abstract class ListNode @PublishedApi internal constructor(tag:String, override val text:CharSequence) : Element(tag)

	class UnorderedListNode @PublishedApi internal constructor(text:CharSequence) : ListNode("ul", text)

	class OrderedListNode @PublishedApi internal constructor(text:CharSequence) : ListNode("ol", text)

	class Table @PublishedApi internal constructor() : Element("table"), BlockDslElement {
		@MustBeInitialized lateinit var header:TableHeader
		val rows:MutableList<TableRow> = mutableListOf()
		override val contentText:String get() = header.toString() + ls + rows.typingAll(ls)
		override var indentContent:Boolean = true
	}

	class TableHeader @PublishedApi internal constructor() : Element("th"), BlockDslElement {
		val columns:MutableList<TableColumn> = mutableListOf()
		override val contentText:String get() = columns.typingAll(ls)
		override var indentContent:Boolean = true
	}

	class TableRow @PublishedApi internal constructor() : Element("tr"), BlockDslElement {
		val columns:MutableList<TableColumn> = mutableListOf()
		override val contentText:String get() = columns.typingAll(ls)
		override var indentContent:Boolean = true
	}

	class TableColumn @PublishedApi internal constructor(override val text:CharSequence) : Element("td")

	class Quote @PublishedApi internal constructor(
		val name:String?
	) : OneArgElement("quote", name), BlockDslElement {
		override var text:CharSequence = ""
		override val contentText:String get() = text.toString()
		override var indentContent:Boolean = true
	}

	companion object {
		val config = Config()
	}
}
