// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.creole

import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.core.model.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslDocument as IDslDocument
import com.windea.breezeframework.dsl.DslConfig as IDslConfig
import com.windea.breezeframework.dsl.DslEntry as IDslEntry
import com.windea.breezeframework.dsl.DslElement as IDslElement

@CreoleDslMarker
interface CreoleDsl {
	@CreoleDslMarker
	class DslDocument @PublishedApi internal constructor() : IDslDocument, DslEntry, InlineDslEntry {
		override val content: MutableList<TopDslElement> = mutableListOf()

		override fun toString() = toContentString()
	}

	@CreoleDslMarker
	object DslConfig : IDslConfig {
		var indent: String = "  "
		var truncated: String = "..."
		var doubleQuoted: Boolean = true
		var markerCount: Int = 3
		var emptyColumnLength: Int = 3

		val quote get() = if(doubleQuoted) '\"' else '\''
		val emptyColumnText: String get() = " ".repeat(emptyColumnLength)
	}

	@CreoleDslMarker
	interface DslEntry:IDslEntry {
		val content: MutableList<TopDslElement>

		override fun toContentString() = content.joinToString("\n\n")

		operator fun String.unaryPlus() = textBlock { this }
	}

	@CreoleDslMarker
	interface InlineDslEntry:IDslEntry

	@CreoleDslMarker
	interface DslElement : IDslElement, InlineDslEntry

	@CreoleDslMarker
	interface InlineDslElement : DslElement, Inlineable

	@CreoleDslMarker
	interface TopDslElement : DslElement

	@CreoleDslMarker
	abstract class RichText : InlineDslElement {
		abstract val text: CharSequence
		override val inlineText get() = text
	}

	@CreoleDslMarker
	class UnicodeText @PublishedApi internal constructor(override val text: String) : RichText() {
		override fun toString() = "<U+$text>"
	}

	@CreoleDslMarker
	class BoldText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override fun toString() = "**$text**"
	}

	@CreoleDslMarker
	class ItalicText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override fun toString() = "//$text//"
	}

	@CreoleDslMarker
	class MonospacedText @PublishedApi internal constructor(override val text: String) : RichText() {
		override fun toString() = "--$text--"
	}

	@CreoleDslMarker
	class StrokedText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override fun toString() = "--$text--"
	}

	@CreoleDslMarker
	class UnderlineText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override fun toString() = "__${text}__"
	}

	@CreoleDslMarker
	class WavedText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override fun toString() = "~~$text~~"
	}

	@CreoleDslMarker
	class EscapedText @PublishedApi internal constructor(override val text: CharSequence) : RichText() {
		override fun toString() = "~$text"
	}

	//https://useiconic.com/open/
	@CreoleDslMarker
	class Icon @PublishedApi internal constructor(val name: String) : InlineDslElement {
		override val inlineText get() = name
		override fun toString() = "<&$name>"
	}

	@CreoleDslMarker
	class TextBlock @PublishedApi internal constructor(val text: String) : TopDslElement {
		override fun toString() = text
	}

	//DELAY HtmlBlock

	@CreoleDslMarker
	abstract class Heading(val headingLevel: Int) : TopDslElement {
		abstract val text: String
		override fun toString() = "${"#".repeat(headingLevel)} $text"
	}

	@CreoleDslMarker
	class Heading1 @PublishedApi internal constructor(override val text: String) : Heading(1)

	@CreoleDslMarker
	class Heading2 @PublishedApi internal constructor(override val text: String) : Heading(2)

	@CreoleDslMarker
	class Heading3 @PublishedApi internal constructor(override val text: String) : Heading(3)

	@CreoleDslMarker
	class Heading4 @PublishedApi internal constructor(override val text: String) : Heading(4)

	@CreoleDslMarker
	class HorizontalLine @PublishedApi internal constructor(
		val type: HorizontalLineType = HorizontalLineType.Normal,
	) : TopDslElement {
		override fun toString(): String {
			return type.marker.repeat(DslConfig.markerCount)
		}
	}

	@CreoleDslMarker
	class HorizontalTitle @PublishedApi internal constructor(
		val text: String, val type: HorizontalLineType = HorizontalLineType.Normal,
	) : TopDslElement {
		override fun toString(): String {
			return type.marker.repeat(DslConfig.markerCount).let { "$it$text$it" }
		}
	}

	@CreoleDslMarker
	class List @PublishedApi internal constructor() : TopDslElement {
		val nodes: MutableList<ListNode> = mutableListOf()

		override fun toString() = nodes.joinToString("\n")
	}

	@CreoleDslMarker
	abstract class ListNode(
		internal val prefixMarkers: String,
	) : DslElement {
		abstract val text: String
		val nodes: MutableList<ListNode> = mutableListOf()

		override fun toString() = "$prefixMarkers $text${nodes.joinToText("\n", "\n")}"
	}

	@CreoleDslMarker
	class OrderedListNode @PublishedApi internal constructor(override val text: String) : ListNode("#")

	@CreoleDslMarker
	class UnorderedListNode @PublishedApi internal constructor(override val text: String) : ListNode("*")

	@CreoleDslMarker
	class Tree @PublishedApi internal constructor(
		val title: String,
	) : TopDslElement {
		val nodes: MutableList<TreeNode> = mutableListOf()

		override fun toString() = "$title${nodes.joinToText("\n", "\n")}"
	}

	@CreoleDslMarker
	class TreeNode @PublishedApi internal constructor(
		val text: String,
	) : DslElement {
		val nodes: MutableList<TreeNode> = mutableListOf()

		override fun toString() = "|_ $text${nodes.joinToText("\n", "\n").prependIndent(DslConfig.indent)}"
	}

	//DELAY pretty format
	@CreoleDslMarker
	class Table @PublishedApi internal constructor() : TopDslElement {
		var header: TableHeader = TableHeader()
		val rows: MutableList<TableRow> = mutableListOf()
		var columnSize: Int? = null

		override fun toString(): String {
			require(rows.isNotEmpty()) { "Table row size must be positive." }

			//actual column size may not equal to columns.size, and can be user defined
			val actualColumnSize = columnSize ?: maxOf(header.columns.size, rows.map { it.columns.size }.maxOrNull() ?: 0)
			header.columnSize = actualColumnSize
			rows.forEach { it.columnSize = actualColumnSize }
			val headerRowSnippet = header.toString()
			val rowsSnippet = rows.joinToString("\n")
			return "$headerRowSnippet\n$rowsSnippet"
		}
	}

	@CreoleDslMarker
	class TableHeader @PublishedApi internal constructor() : DslElement {
		val columns: MutableList<TableColumn> = mutableListOf()
		var columnSize: Int? = null

		override fun toString(): String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			//actual column size may not equal to columns.size
			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toStringInHeader() }
				else -> columns.map { it.toStringInHeader() }.fillEnd(columnSize!!, DslConfig.emptyColumnText)
			}.joinToString("|", "|", "|")
		}

		operator fun String.unaryPlus() = column(this)

		@CreoleDslMarker
		infix fun TableColumn.align(alignment: TableAlignment) = apply { this.alignment = alignment }
	}

	@CreoleDslMarker
	open class TableRow @PublishedApi internal constructor() : DslElement {
		val columns: MutableList<TableColumn> = mutableListOf()
		var columnSize: Int? = null

		override fun toString(): String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			//actual column size may not equal to columns.size
			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
				else -> columns.map { it.toString() }.fillEnd(columnSize!!, DslConfig.emptyColumnText)
			}.joinToString(" | ", "| ", " |")
		}

		operator fun String.unaryPlus() = column(this)
	}

	@CreoleDslMarker
	open class TableColumn @PublishedApi internal constructor(
		val text: String = DslConfig.emptyColumnText,
	) : DslElement {
		var color: String? = null
		var alignment: TableAlignment = TableAlignment.None //only for columns in table header

		override fun toString(): String {
			val colorSnippet = color?.let { "<$color> " }.orEmpty()
			return "$colorSnippet$text"
		}

		fun toStringInHeader(): String {
			val colorSnippet = color?.let { "<$color> " }.orEmpty()
			val (l, r) = alignment.textPair
			return "$l $colorSnippet$text $r"
		}
	}

	@CreoleDslMarker
	enum class HorizontalLineType(val marker: Char) {
		Normal('-'), Double('='), Strong('_'), Dotted('.')
	}

	@CreoleDslMarker
	enum class TableAlignment(val textPair: Pair<String, String>) {
		None("" to ""), Left("=" to ""), Center("=" to "="), Right("" to "=")
	}
}
