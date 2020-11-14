// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.core.model.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.api.*
import org.intellij.lang.annotations.*

@MarkdownDslMarker
interface MarkdownDsl {
	@MarkdownDslMarker
	class Document @PublishedApi internal constructor() : DslDocument, DslEntry {
		@MarkdownDslExtendedFeature
		var frontMatter: FrontMatter? = null
		@MarkdownDslExtendedFeature
		var toc: Toc? = null
		override val content: MutableList<TopDslElement> = mutableListOf()
		val references: MutableSet<Reference> = mutableSetOf()

		@MarkdownDslExtendedFeature
		override fun toString(): String {
			return arrayOf(
				frontMatter,
				toc,
				toContentString(),
				references.joinToText("\n")
			).joinToText("\n\n")
		}
	}

	@MarkdownDslMarker
	object Config : DslConfig {
		var indent: String = "  "
		var truncated: String = "..."
		var listNodeMarker: Char = '*'
		var horizontalLineMarker: Char = '*'
		var codeFenceMarker: Char = '`'
		var doubleQuoted: Boolean = true
		var addPrefixHeadingMarkers: Boolean = false
		var markerCount: Int = 3
		var emptyColumnLength: Int = 3
		var wrapLength: Int = 120

		val quote get() = if(doubleQuoted) '\"' else '\''
		val horizontalLineMarkers get() = horizontalLineMarker.repeat(markerCount)
		val codeFenceMarkers get() = codeFenceMarker.repeat(markerCount)
		val emptyColumnText: String get() = " ".repeat(emptyColumnLength)
		val emptyColumnSeparatorText: String = "-".repeat(emptyColumnLength)
	}

	companion object {
		internal fun heading(text: String, headingLevel: Int) = "${"#".repeat(headingLevel)} $text"
	}

	@MarkdownDslMarker
	interface DslElement : com.windea.breezeframework.dsl.DslElement, InlineDslEntry

	@MarkdownDslMarker
	interface InlineDslElement : DslElement, Inlineable

	@MarkdownDslMarker
	interface TopDslElement : DslElement

	@MarkdownDslMarker
	interface DslEntry {
		val content: MutableList<TopDslElement>

		fun toContentString(): String {
			return content.joinToText("\n\n")
		}

		operator fun String.unaryPlus() = TextBlock(this).also { content += it }
	}

	@MarkdownDslMarker
	interface InlineDslEntry

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	interface WithAttributes {
		var attributes: AttributeGroup?
	}

	@MarkdownDslMarker
	abstract class RichText : InlineDslElement {
		abstract val text: CharSequence
		override val inlineText get() = text
	}

	@MarkdownDslMarker
	class BoldText(override val text: CharSequence) : RichText() {
		override fun toString() = "**$text**"
	}

	@MarkdownDslMarker
	class ItalicText(override val text: CharSequence) : RichText() {
		override fun toString() = "*$text*"
	}

	@MarkdownDslMarker
	class StrokedText(override val text: CharSequence) : RichText() {
		override fun toString() = "~~$text~~"
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class UnderlinedText(override val text: CharSequence) : RichText() {
		override fun toString() = "++$text++"
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class HighlightText(override val text: CharSequence) : RichText() {
		override fun toString() = "==$text=="
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	 class SuperscriptText(override val text: CharSequence) : RichText() {
		override fun toString() = "^$text^"
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class SubscriptText(override val text: CharSequence) : RichText() {
		override fun toString() = "~$text~"
	}

	@MarkdownDslMarker
	 class Icon(val name: String) : InlineDslElement {
		override val inlineText get() = name
		override fun toString() = ":$name:"
	}

	@MarkdownDslMarker
	 class FootNote(val reference: String) : InlineDslElement {
		override val inlineText: String get() = reference
		override fun toString() = ":$reference:"
	}

	@MarkdownDslMarker
	abstract class Link(
		val name: String? = null, val url: String? = null,
	) : InlineDslElement {
		override fun toString(): String = inlineText.toString()
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class AutoLink @PublishedApi internal constructor(url: String) : Link(null, url) {
		override val inlineText: CharSequence get() = "<$url>"
	}

	@MarkdownDslMarker
	open class InlineLink @PublishedApi internal constructor(
		name: String, url: String, val title: String? = null,
	) : Link(name, url) {
		override val inlineText: CharSequence get() = "[$name]($url${title?.let { " ${it.quote(Config.quote)}" }.orEmpty()})"
	}

	@MarkdownDslMarker
	class InlineImageLink @PublishedApi internal constructor(
		name: String = "", url: String, title: String? = null,
	) : InlineLink(name, url, title) {
		override val inlineText: CharSequence get() = "!${super.toString()}"
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class WikiLink @PublishedApi internal constructor(
		name: String, url: String,
	) : Link(name, url) {
		override val inlineText: CharSequence get() = "[[$name|$url]]"
	}

	@MarkdownDslMarker
	open class ReferenceLink @PublishedApi internal constructor(
		val reference: String, name: String? = null,
	) : Link(name, null) {
		override val inlineText: CharSequence get() = "${name?.let { "[$name]" }.orEmpty()}[$reference]"
	}

	@MarkdownDslMarker
	class ReferenceImageLink @PublishedApi internal constructor(
		reference: String, name: String? = null,
	) : ReferenceLink(reference, name) {
		override val inlineText: CharSequence get() = "!${super.toString()}"
	}

	@MarkdownDslMarker
	class TextBlock @PublishedApi internal constructor(
		val text: String,
	) : TopDslElement, Wrappable {
		override var wrapContent: Boolean = true

		override fun toString(): String {
			return when {
				text.length > Config.wrapLength -> text.let { if(wrapContent) it.chunked(Config.wrapLength).joinToString("\n") else it }
				else -> text
			}
		}
	}

	@MarkdownDslMarker
	abstract class Heading: TopDslElement, WithAttributes, Wrappable {
		abstract val headingLevel: Int
		abstract val text: String
		@MarkdownDslExtendedFeature
		override var attributes: AttributeGroup? = null

		override var wrapContent: Boolean = true
	}

	@MarkdownDslMarker
	abstract class SetextHeading(override val headingLevel: Int) : Heading() {
		@MarkdownDslExtendedFeature
		override fun toString(): String {
			val textSnippet = when {
				text.length > Config.wrapLength -> text.let { if(wrapContent) it.chunked(Config.wrapLength).joinToText("\n") else it }
				else -> text
			}
			val attributesSnippet = attributes?.let { " $it" }.orEmpty()
			val suffixMarkers = (if(headingLevel == 1) "=" else "-").repeat(Config.markerCount)
			return "$textSnippet$attributesSnippet\n$suffixMarkers"
		}
	}

	@MarkdownDslMarker
	class MainHeading @PublishedApi internal constructor(override val text: String) : SetextHeading(1)

	@MarkdownDslMarker
	class SubHeading @PublishedApi internal constructor(override val text: String, ) : SetextHeading(2)

	@MarkdownDslMarker
	abstract class AtxHeading(override val headingLevel: Int) : Heading() {
		override fun toString(): String {
			val indent = " " * (headingLevel + 1)
			val prefixMarkers = "#" * headingLevel
			val textSnippet = if(text.length > Config.wrapLength)
				text.let { if(wrapContent) it.chunked(Config.wrapLength).joinToText("\n") else it }
					.prependIndent(indent).setPrefix(prefixMarkers)
			else text
			val attributesSnippet = attributes?.let { " $it" }.orEmpty()
			val suffixMarkers = if(Config.addPrefixHeadingMarkers) " $prefixMarkers" else ""
			return "$textSnippet$attributesSnippet$suffixMarkers"
		}
	}

	@MarkdownDslMarker
	class Heading1 @PublishedApi internal constructor(override val text: String) : AtxHeading(1)

	@MarkdownDslMarker
	class Heading2 @PublishedApi internal constructor(override val text: String) : AtxHeading(2)

	@MarkdownDslMarker
	class Heading3 @PublishedApi internal constructor(override val text: String) : AtxHeading(3)

	@MarkdownDslMarker
	class Heading4 @PublishedApi internal constructor(override val text: String) : AtxHeading(4)

	@MarkdownDslMarker
	class Heading5 @PublishedApi internal constructor(override val text: String) : AtxHeading(5)

	@MarkdownDslMarker
	class Heading6 @PublishedApi internal constructor(override val text: String) : AtxHeading(6)

	@MarkdownDslMarker
	object HorizontalLine : TopDslElement {
		override fun toString() = Config.horizontalLineMarkers
	}

	@MarkdownDslMarker
	class List @PublishedApi internal constructor(
		val nodes: MutableList<ListNode> = mutableListOf(),
	) : TopDslElement {
		override fun toString() = nodes.joinToText("\n")
	}

	@MarkdownDslMarker
	abstract class ListNode(
		internal val prefixMarkers: String, val text: String,
	) : DslElement, Wrappable {
		val nodes: MutableList<ListNode> = mutableListOf()

		override var wrapContent: Boolean = true

		override fun toString(): String {
			val indent = " " * (prefixMarkers.length + 1)
			val textSnippet = when {
				text.length > Config.wrapLength -> text.let {
					if(wrapContent) it.chunked(Config.wrapLength).joinToText("\n") else it
				}.prependIndent(indent).setPrefix(prefixMarkers)
				else -> text
			}
			val nodesSnippet = nodes.joinToText("\n", "\n").ifNotEmpty { it.prependIndent(indent) }
			return "$textSnippet$nodesSnippet"
		}
	}

	@MarkdownDslMarker
	class OrderedListNode @PublishedApi internal constructor(
		val order: String, text: String,
	) : ListNode("$order.", text)

	@MarkdownDslMarker
	class UnorderedListNode @PublishedApi internal constructor(
		text: String,
	) : ListNode(Config.listNodeMarker.toString(), text)

	@MarkdownDslMarker
	class TaskListNode @PublishedApi internal constructor(
		val isCompleted: Boolean, text: String,
	) : ListNode("${Config.listNodeMarker} [${if(isCompleted) "X" else " "}]", text)

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class Definition @PublishedApi internal constructor(
		val title: String,
	) : TopDslElement, Wrappable {
		val nodes: MutableList<DefinitionNode> = mutableListOf()

		override var wrapContent: Boolean = true

		override fun toString(): String {
			require(nodes.isNotEmpty()) { "Definition node size must be positive." }

			val titleSnippet = if(title.length > Config.wrapLength)
				title.let { if(wrapContent) it.chunked(Config.wrapLength).joinToText("\n") else it }
			else title
			val nodesSnippet = nodes.joinToText("\n")
			return "$titleSnippet\n$nodesSnippet"
		}
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class DefinitionNode @PublishedApi internal constructor(
		val text: String,
	) : DslElement, Wrappable {
		override var wrapContent: Boolean = true

		override fun toString(): String {
			return if(text.length > Config.wrapLength)
				text.let { if(wrapContent) it.chunked(Config.wrapLength).joinToText("\n") else it }
					.prependIndent(Config.indent).setPrefix(":")
			else text
		}
	}

	//DELAY pretty format
	@MarkdownDslMarker
	class Table @PublishedApi internal constructor() : TopDslElement {
		var header: TableHeader = TableHeader()
		val rows: MutableList<TableRow> = mutableListOf()
		var columnSize: Int? = null

		override fun toString(): String {
			require(rows.isNotEmpty()) { "Table row size must be positive." }

			//actual column size may not equal to columns.size, and can be user defined
			val actualColumnSize = columnSize ?: maxOf(header.columns.size, rows.map { it.columns.size }.max() ?: 0)
			//adjust column size
			header.columnSize = actualColumnSize
			rows.forEach { it.columnSize = actualColumnSize }

			val headerRowSnippet = header.toString()
			val delimitersSnippet = header.toDelimitersString()
			val rowsSnippet = rows.joinToText("\n")
			return "$headerRowSnippet\n$delimitersSnippet\n$rowsSnippet"
		}
	}

	@MarkdownDslMarker
	class TableHeader @PublishedApi internal constructor() : DslElement {
		val columns: MutableList<TableColumn> = mutableListOf()
		var columnSize: Int? = null

		override fun toString(): String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			//actual column size may not equal to columns.size
			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
				else -> columns.map { it.toString() }.fillEnd(columnSize!!, Config.emptyColumnText)
			}.joinToString(" | ", "| ", " |")
		}

		fun toDelimitersString(): String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toDelimitersString() }
				else -> columns.map { it.toDelimitersString() }.fillEnd(columnSize!!, "---")
			}.joinToString(" | ", "| ", " |")
		}

		operator fun String.unaryPlus() = column(this)

		@MarkdownDslMarker
		infix fun TableColumn.align(alignment: TableAlignment) = apply { this.alignment = alignment }
	}

	@MarkdownDslMarker
	open class TableRow @PublishedApi internal constructor() : DslElement {
		val columns: MutableList<TableColumn> = mutableListOf()
		var columnSize: Int? = null

		override fun toString(): String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			//actual column size may not equal to columns.size
			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
				else -> columns.map { it.toString() }.fillEnd(columnSize!!, Config.emptyColumnText)
			}.joinToString(" | ", "| ", " |")
		}

		operator fun String.unaryPlus() = column(this)
	}

	@MarkdownDslMarker
	class TableColumn @PublishedApi internal constructor(
		val text: String = Config.emptyColumnText,
	) : DslElement {
		var alignment: TableAlignment = TableAlignment.None //only for columns in table header

		override fun toString(): String {
			return text
		}

		fun toDelimitersString(): String {
			val (l, r) = alignment.textPair
			return "$l${" " * (Config.emptyColumnLength - 2)}$r"
		}
	}

	@MarkdownDslMarker
	abstract class Quote(
		val prefixMarker: String,
	) : TopDslElement, DslEntry {
		override val content: MutableList<TopDslElement> = mutableListOf()

		override fun toString(): String {
			return toContentString().prependIndent("$prefixMarker ")
		}
	}

	@MarkdownDslMarker
	class BlockQuote @PublishedApi internal constructor() : Quote(">")

	@MarkdownDslMarker
	class IndentedBlock @PublishedApi internal constructor() : Quote(" ")

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class SideBlock @PublishedApi internal constructor() : Quote("|")

	@MarkdownDslMarker
	interface Code {
		val code: String
	}

	@MarkdownDslMarker
	class InlineCode @PublishedApi internal constructor(
		override val code: String,
	) : InlineDslElement, Code {
		override val inlineText: String get() = code
		override fun toString() = "`$code`"
	}

	@MarkdownDslMarker
	class CodeFence @PublishedApi internal constructor(
		val language: String,
		override val code: String,
	) : TopDslElement, Code, WithAttributes {
		//DONE extended classes and properties
		@MarkdownDslExtendedFeature
		override var attributes: AttributeGroup? = null

		@MarkdownDslExtendedFeature
		override fun toString(): String {
			val markersSnippet = Config.horizontalLineMarkers
			val attributesSnippet = attributes?.let { " $it" }.orEmpty()
			return "$markersSnippet$language$attributesSnippet\n$code\n$markersSnippet"
		}
	}

	@MarkdownDslMarker
	interface Math {
		val code: String
	}

	@MarkdownDslMarker
	class InlineMath @PublishedApi internal constructor(
		override val code: String,
	) : InlineDslElement, Math {
		override val inlineText get() = code
		override fun toString() = "$$code$"
	}

	@MarkdownDslMarker
	class MultilineMath @PublishedApi internal constructor(
		override val code: String,
	) : TopDslElement, Math {
		override fun toString(): String {
			return "$$\n$code\n$$"
		}
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class Admonition @PublishedApi internal constructor(
		val qualifier: AdmonitionQualifier, val title: String = "", val type: AdmonitionType = AdmonitionType.Normal,
	) : TopDslElement, DslEntry {
		override val content: MutableList<TopDslElement> = mutableListOf()

		override fun toString(): String {
			require(content.isNotEmpty()) { "Alert box content must not be empty." }

			val titleSnippet = title.quote(Config.quote)
			val contentSnippet = toContentString().prependIndent(Config.indent)
			return "${type.text} ${qualifier.text} $titleSnippet\n$contentSnippet"
		}
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class FrontMatter @PublishedApi internal constructor(
		@Language("Yaml") val text: String,
	) : DslElement {
		override fun toString(): String {
			return "---\n$text\n---"
		}
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class Toc @PublishedApi internal constructor() : DslElement, Generatable {
		override var generateContent: Boolean = false

		override fun toString(): String {
			return "[TOC]"
		}
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class Import @PublishedApi internal constructor(
		val url: String,
	) : TopDslElement, Generatable, WithAttributes {
		//DONE extended classes and properties
		override var attributes: AttributeGroup? = null
		override var generateContent: Boolean = false

		override fun toString(): String {
			val attributesSnippet = attributes?.let { " $it" }.orEmpty()
			val urlSnippet = url.quote(Config.quote)
			return "@import $urlSnippet$attributesSnippet"
		}
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class Macros @PublishedApi internal constructor(
		val name: String,
	) : TopDslElement, Generatable {
		override var generateContent: Boolean = false

		override fun toString(): String {
			return "<<< $name >>>"
		}
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class MacrosSnippet @PublishedApi internal constructor(
		val name: String,
	) : TopDslElement, DslEntry {
		override val content: MutableList<TopDslElement> = mutableListOf()

		override fun toString(): String {
			val contentSnippet = toContentString()
			return ">>> $name\n$contentSnippet\n<<<"
		}
	}

	@MarkdownDslMarker
	abstract class Reference(
		val reference: String,
	) : DslElement {
		val id: String get() = reference
	}

	/**Markdown脚注的引用。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class FootNoteReference @PublishedApi internal constructor(
		reference: String, val text: String,
	) : Reference(reference) {
		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString(): String {
			return "[^$reference]: $text"
		}
	}

	/**Markdown缩写。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class Abbreviation @PublishedApi internal constructor(
		reference: String, val text: String,
	) : Reference(reference) {
		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString(): String {
			return "*[$reference]: $text"
		}
	}

	/**Markdown链接的引用。*/
	@MarkdownDslMarker
	class LinkReference @PublishedApi internal constructor(
		reference: String, val url: String, val title: String? = null,
	) : Reference(reference) {
		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString(): String {
			val titleSnippet = title?.let { " ${it.quote(Config.quote)}" }.orEmpty()
			return "[$reference]: $url$titleSnippet"
		}
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class AttributeGroup @PublishedApi internal constructor(
		attributes: Set<Attribute>,
	) : InlineDslElement, Inlineable, Set<Attribute> by attributes {
		override val inlineText: String get() = joinToString(" ", " {", "}")
		override fun toString() = inlineText
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	interface Attribute : InlineDslElement, Inlineable

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class IdAttribute(val name: String) : Attribute {
		override val inlineText: String get() = "#$name"
		override fun toString() = inlineText
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	 class ClassAttribute(val name: String) : Attribute {
		override val inlineText: String get() = ".$name"
		override fun toString() = inlineText
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	 class PropertyAttribute(val pair: Pair<String, String>) : Attribute {
		override val inlineText: String get() = "${pair.first}=${pair.second.quote(Config.quote)}"
		override fun toString() = inlineText
	}

	@MarkdownDslMarker
	enum class TableAlignment(
		internal val textPair: Pair<String, String>,
	) {
		None("-" to "-"), Left(":" to "-"), Center(":" to ":"), Right("-" to ":")
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	enum class AdmonitionType(
		internal val text: String,
	) {
		Normal("!!!"), Collapsed("???"), Opened("!!!+")
	}

	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	enum class AdmonitionQualifier(
		internal val style: String, internal val text: String,
	) {
		Abstract("abstract", "abstract"), Summary("abstract", "summary"), Tldr("abstract", "tldr"),
		Bug("bug", "bug"),
		Danger("danger", "danger"), Error("danger", "error"),
		Example("example", "example"), Snippet("example", "snippet"),
		Fail("fail", "fail"), Failure("fail", "failure"), Missing("fail", "missing"),
		Question("fag", "question"), Help("fag", "help"), Fag("fag", "fag"),
		Info("info", "info"), Todo("info", "todo"),
		Note("note", "note"), SeeAlso("note", "seealso"),
		Quote("quote", "quote"), Cite("quote", "cite"),
		Success("success", "success"), Check("success", "check"), Done("success", "done"),
		Tip("tip", "tip"), Hint("tip", "hint"), Important("tip", "important"),
		Warning("warning", "warning"), Caution("warning", "caution"), Attention("warning", "attention")
	}
}
