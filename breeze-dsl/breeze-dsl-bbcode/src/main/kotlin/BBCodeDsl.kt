// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.bbcode

import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.core.model.*
import com.windea.breezeframework.dsl.api.*
import com.windea.breezeframework.dsl.bbcode.BBCodeDsl.DslConfig.indent
import com.windea.breezeframework.dsl.DslConfig as IDslConfig
import com.windea.breezeframework.dsl.DslDocument as IDslDocument
import com.windea.breezeframework.dsl.DslElement as IDslElement
import com.windea.breezeframework.dsl.DslEntry as IDslEntry

interface BBCodeDsl {
	class DslDocument @PublishedApi internal constructor() : IDslDocument, InlineDslEntry {
		var text: CharSequence = ""

		override fun toString(): String = text.toString()
	}

	object DslConfig : IDslConfig {
		var indent: String = "  "
	}

	interface InlineDslEntry : IDslEntry

	interface DslElement : IDslElement, Inlineable, InlineDslEntry {
		override val inlineText: CharSequence
	}

	interface InlineDslElement : DslElement {
		override val inlineText: CharSequence
	}

	interface CrosslineDslElement : DslElement {
		override val inlineText: CharSequence
	}

	interface BlockDslElement : DslElement, Indentable {
		val contentText: String
		override val inlineText: CharSequence get() = contentText.ifNotEmpty { "\n${it.let { if(indentContent) it.prependIndent(indent) else it }}\n" }
	}

	abstract class Element @PublishedApi internal constructor(val tag: String) : DslElement {
		override fun toString(): String = "[$tag]$inlineText[/$tag]"
	}

	abstract class OneArgElement @PublishedApi internal constructor(
		tag: String,
		val value: String?,
	) : Element(tag) {
		override fun toString(): String = "[$tag${value.toText { "=$it" }}]$inlineText[/$tag]"
	}

	abstract class MultiArgElement @PublishedApi internal constructor(
		tag: String,
		val args: Map<String, String?>,
	) : Element(tag) {
		override fun toString(): String = "[$tag${args.joinToText(" ", " ") { (k, v) -> "$k=$v" }}]$inlineText[/$tag]"
	}

	abstract class RichText @PublishedApi internal constructor(
		tag: String,
		override val inlineText: CharSequence,
	) : Element(tag), InlineDslElement

	class BoldText @PublishedApi internal constructor(override val inlineText: CharSequence) : Element("b")

	class ItalicText @PublishedApi internal constructor(override val inlineText: CharSequence) : Element("i")

	class UnderlinedText @PublishedApi internal constructor(override val inlineText: CharSequence) : Element("u")

	class StrikeText @PublishedApi internal constructor(override val inlineText: CharSequence) : Element("strike")

	class SpoilerText @PublishedApi internal constructor(override val inlineText: CharSequence) : Element("spoiler")

	class NoParseText @PublishedApi internal constructor(override val inlineText: CharSequence) : Element("noparse")

	class LeftText @PublishedApi internal constructor(override val inlineText: CharSequence) : Element("left")

	class CenterText @PublishedApi internal constructor(override val inlineText: CharSequence) : Element("center")

	class RightText @PublishedApi internal constructor(override val inlineText: CharSequence) : Element("right")

	class Code @PublishedApi internal constructor(override val inlineText: CharSequence) : Element("code")

	class StyledText @PublishedApi internal constructor(
		val size: String?,
		val color: String?,
		override val inlineText: CharSequence,
	) : MultiArgElement("style", mapOf("size" to size, "color" to color)), InlineDslElement

	class YoutubeVideo @PublishedApi internal constructor(override val inlineText: CharSequence) : Element("youtube"), InlineDslElement

	class Link @PublishedApi internal constructor(
		val url: String?,
		override val inlineText: CharSequence,
	) : OneArgElement("url", url), InlineDslElement

	class Image @PublishedApi internal constructor(
		val width: String?,
		val height: String?,
		override val inlineText: CharSequence,
	) : MultiArgElement("img", mapOf("width" to width, "height" to height)), InlineDslElement

	abstract class Heading @PublishedApi internal constructor(
		tag: String,
		override val inlineText: CharSequence,
	) : Element(tag), CrosslineDslElement

	class Heading1 @PublishedApi internal constructor(text: CharSequence) : Heading("h1", text)

	class Heading2 @PublishedApi internal constructor(text: CharSequence) : Heading("h1", text)

	class Heading3 @PublishedApi internal constructor(text: CharSequence) : Heading("h1", text)

	class Heading4 @PublishedApi internal constructor(text: CharSequence) : Heading("h1", text)

	class List @PublishedApi internal constructor() : Element("list"), BlockDslElement {
		val nodes: MutableList<ListNode> = mutableListOf()
		override val contentText: String get() = nodes.joinToText("\n")
		override var indentContent: Boolean = true
	}

	abstract class ListNode @PublishedApi internal constructor(tag: String, override val inlineText: CharSequence) : Element(tag)

	class UnorderedListNode @PublishedApi internal constructor(text: CharSequence) : ListNode("ul", text)

	class OrderedListNode @PublishedApi internal constructor(text: CharSequence) : ListNode("ol", text)

	class Table @PublishedApi internal constructor() : Element("table"), BlockDslElement {
		lateinit var header: TableHeader
		val rows: MutableList<TableRow> = mutableListOf()
		override val contentText: String get() = header.toString() + "\n" + rows.joinToText("\n")
		override var indentContent: Boolean = true
	}

	class TableHeader @PublishedApi internal constructor() : Element("th"), BlockDslElement {
		val columns: MutableList<TableColumn> = mutableListOf()
		override val contentText: String get() = columns.joinToText("\n")
		override var indentContent: Boolean = true
	}

	class TableRow @PublishedApi internal constructor() : Element("tr"), BlockDslElement {
		val columns: MutableList<TableColumn> = mutableListOf()
		override val contentText: String get() = columns.joinToText("\n")
		override var indentContent: Boolean = true
	}

	class TableColumn @PublishedApi internal constructor(override val inlineText: CharSequence) : Element("td")

	class Quote @PublishedApi internal constructor(val name: String?) : OneArgElement("quote", name), BlockDslElement {
		override var inlineText: CharSequence = ""
		override val contentText: String get() = inlineText.toString()
		override var indentContent: Boolean = true
	}
}
