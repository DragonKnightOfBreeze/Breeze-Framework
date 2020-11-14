// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("CanBeParameter")

package com.windea.breezeframework.dsl.bbcode

import com.windea.breezeframework.core.model.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.api.*
import com.windea.breezeframework.dsl.bbcode.BBCodeDslConfig.indent

/**
 * DslDocument definitions of [BBCodeDsl].
 */
interface BBCodeDslDefinitions {
	interface IDslElement : DslElement, Inlineable {
		override val inlineText: CharSequence
	}

	interface InlineDslElement : IDslElement {
		override val inlineText: CharSequence
	}

	interface CrosslineDslElement : IDslElement {
		override val inlineText: CharSequence
	}

	interface BlockDslElement : IDslElement, Indentable {
		val contentText: String
		override val inlineText: CharSequence get() = contentText.ifNotEmpty { '\n' + it.doIndent(indent) + '\n' }
	}


	abstract class Element @PublishedApi internal constructor(val tag: String) : IDslElement {
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

	class Quote @PublishedApi internal constructor(
		val name: String?,
	) : OneArgElement("quote", name), BlockDslElement {
		override var inlineText: CharSequence = ""
		override val contentText: String get() = inlineText.toString()
		override var indentContent: Boolean = true
	}
}
