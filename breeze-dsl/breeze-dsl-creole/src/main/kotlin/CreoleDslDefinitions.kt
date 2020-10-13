// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.creole

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslConstants.ls
import com.windea.breezeframework.dsl.creole.CreoleDslConfig.emptyColumnText
import com.windea.breezeframework.dsl.creole.CreoleDslConfig.indent
import com.windea.breezeframework.dsl.creole.CreoleDslConfig.markerCount

/**
 * Dsl definitions of [CreoleDsl].
 */
@CreoleDslMarker
interface CreoleDslDefinitions {
	companion object {
		internal fun heading(text: String, headingLevel: Int) = "${"#".repeat(headingLevel)} $text"
	}

	/**Creole领域特定语言的入口。*/
	@CreoleDslMarker
	interface IDslEntry : DslEntry {
		val content: MutableList<TopDslElement>

		override fun toContentString() = content.joinToString("$ls$ls")

		operator fun String.unaryPlus() = textBlock { this }
	}

	/**Creole领域特定语言的内联入口。*/
	@CreoleDslMarker
	interface InlineDslEntry : DslEntry

	/**Creole领域特定语言的元素。*/
	@CreoleDslMarker
	interface IDslElement : DslElement, InlineDslEntry

	/**Creole领域特定语言的内联元素。*/
	@CreoleDslMarker
	interface InlineDslElement : IDslElement, Inlineable

	/**Creole领域特定语言的顶级元素。*/
	@CreoleDslMarker
	interface TopDslElement : IDslElement

	/**Creole富文本。*/
	@CreoleDslMarker
	interface RichText : InlineDslElement {
		val text: CharSequence
		override val inlineText get() = text
	}

	/**CreoleUnicode文本。*/
	@CreoleDslMarker
	class UnicodeText @PublishedApi internal constructor(override val text: String) : RichText {
		override fun toString() = "<U+$text>"
	}

	/**Creole加粗文本。*/
	@CreoleDslMarker
	class BoldText @PublishedApi internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = "**$text**"
	}

	/**Creole斜体文本。*/
	@CreoleDslMarker
	class ItalicText @PublishedApi internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = "//$text//"
	}

	/**Creole代码文本。*/
	@CreoleDslMarker
	class MonospacedText @PublishedApi internal constructor(override val text: String) : RichText {
		override fun toString() = "--$text--"
	}

	/**Creole删除线文本。*/
	@CreoleDslMarker
	class StrokedText @PublishedApi internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = "--$text--"
	}

	/**Creole下划线文本。*/
	@CreoleDslMarker
	class UnderlineText @PublishedApi internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = "__${text}__"
	}

	/**Creole波浪线文本。*/
	@CreoleDslMarker
	class WavedText @PublishedApi internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = "~~$text~~"
	}

	/**Creole转义文本。*/
	@CreoleDslMarker
	class EscapedText @PublishedApi internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = "~$text"
	}

	/**
	 * Creole的图标。
	 * 参见：[OpenIconic](https://useiconic.com/open/)
	 */
	@CreoleDslMarker
	class Icon @PublishedApi internal constructor(val name: String) : InlineDslElement {
		override val inlineText get() = name
		override fun toString() = "<&$name>"
	}

	/**Creole文本块。*/
	@CreoleDslMarker
	class TextBlock @PublishedApi internal constructor(
		val text: String,
	) : TopDslElement {
		override fun toString() = text
	}

	//DELAY HtmlBlock

	/**Creole标题。*/
	@CreoleDslMarker
	interface Heading : TopDslElement {
		val text: String
	}

	/**Creole一级标题。*/
	@CreoleDslMarker
	class Heading1 @PublishedApi internal constructor(
		override val text: String,
	) : Heading {
		override fun toString() = heading(text, 1)
	}

	/**Creole二级标题。*/
	@CreoleDslMarker
	class Heading2 @PublishedApi internal constructor(
		override val text: String,
	) : Heading {
		override fun toString() = heading(text, 2)
	}

	/**Creole三级标题。*/
	@CreoleDslMarker
	class Heading3 @PublishedApi internal constructor(
		override val text: String,
	) : Heading {
		override fun toString() = heading(text, 3)
	}

	/**Creole四级标题。*/
	@CreoleDslMarker
	class Heading4 @PublishedApi internal constructor(
		override val text: String,
	) : Heading {
		override fun toString() = heading(text, 4)
	}

	/**Creole水平分割线。*/
	@CreoleDslMarker
	class HorizontalLine @PublishedApi internal constructor(
		val type: HorizontalLineType = HorizontalLineType.Normal,
	) : TopDslElement {
		override fun toString() = type.marker.repeat(markerCount)
	}

	/**Creole水平标题。*/
	@CreoleDslMarker
	class HorizontalTitle @PublishedApi internal constructor(
		val text: String, val type: HorizontalLineType = HorizontalLineType.Normal,
	) : TopDslElement {
		override fun toString() = type.marker.repeat(markerCount).let { "$it$text$it" }
	}

	/**Creole列表。*/
	@CreoleDslMarker
	class List @PublishedApi internal constructor() : TopDslElement {
		val nodes: MutableList<ListNode> = mutableListOf()

		override fun toString() = nodes.joinToString(ls)
	}

	/**Creole列表节点。*/
	@CreoleDslMarker
	abstract class ListNode(
		internal val prefixMarkers: String, val text: String,
	) : IDslElement {
		val nodes: MutableList<ListNode> = mutableListOf()

		override fun toString() = "$prefixMarkers $text${nodes.joinToText(ls, ls)}"
	}

	/**Creole有序列表节点。*/
	@CreoleDslMarker
	class OrderedListNode @PublishedApi internal constructor(text: String) : ListNode("#", text)

	/**Creole无序列表节点。*/
	@CreoleDslMarker
	class UnorderedListNode @PublishedApi internal constructor(text: String) : ListNode("*", text)

	/**Creole树。*/
	@CreoleDslMarker
	class Tree @PublishedApi internal constructor(
		val title: String,
	) : TopDslElement {
		val nodes: MutableList<TreeNode> = mutableListOf()

		override fun toString() = "$title${nodes.joinToText(ls, ls)}"
	}

	/**Creole树节点。*/
	@CreoleDslMarker
	class TreeNode @PublishedApi internal constructor(
		val text: String,
	) : IDslElement {
		val nodes: MutableList<TreeNode> = mutableListOf()

		override fun toString() = "|_ $text${nodes.joinToText(ls, ls).prependIndent(indent)}"
	}

	//DELAY pretty format
	/**Creole表格。*/
	@CreoleDslMarker
	class Table @PublishedApi internal constructor() : TopDslElement {
		var header: TableHeader = TableHeader()
		val rows: MutableList<TableRow> = mutableListOf()
		var columnSize: Int? = null

		override fun toString(): String {
			require(rows.isNotEmpty()) { "Table row size must be positive." }

			//actual column size may not equal to columns.size, and can be user defined
			val actualColumnSize = columnSize ?: maxOf(header.columns.size, rows.map { it.columns.size }.max() ?: 0)
			header.columnSize = actualColumnSize
			rows.forEach { it.columnSize = actualColumnSize }
			val headerRowSnippet = header.toString()
			val rowsSnippet = rows.joinToString(ls)
			return "$headerRowSnippet$ls$rowsSnippet"
		}
	}

	/**Creole表格头部。*/
	@CreoleDslMarker
	class TableHeader @PublishedApi internal constructor() : IDslElement {
		val columns: MutableList<TableColumn> = mutableListOf()
		var columnSize: Int? = null

		override fun toString(): String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			//actual column size may not equal to columns.size
			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toStringInHeader() }
				else -> columns.map { it.toStringInHeader() }.fillEnd(columnSize!!, emptyColumnText)
			}.joinToString("|", "|", "|")
		}

		operator fun String.unaryPlus() = column(this)

		@CreoleDslMarker
		infix fun TableColumn.align(alignment: TableAlignment) = apply { this.alignment = alignment }
	}

	/**Creole表格行。*/
	@CreoleDslMarker
	open class TableRow @PublishedApi internal constructor() : IDslElement {
		val columns: MutableList<TableColumn> = mutableListOf()
		var columnSize: Int? = null

		override fun toString(): String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			//actual column size may not equal to columns.size
			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
				else -> columns.map { it.toString() }.fillEnd(columnSize!!, emptyColumnText)
			}.joinToString(" | ", "| ", " |")
		}

		operator fun String.unaryPlus() = column(this)
	}

	/**Creole表格列。*/
	@CreoleDslMarker
	open class TableColumn @PublishedApi internal constructor(
		val text: String = emptyColumnText,
	) : IDslElement {
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

	/**Creole水平线的类型。*/
	@CreoleDslMarker
	enum class HorizontalLineType(
		internal val marker: Char,
	) {
		Normal('-'), Double('='), Strong('_'), Dotted('.')
	}

	/**Creole表格的对齐方式。*/
	@CreoleDslMarker
	enum class TableAlignment(
		internal val textPair: Pair<String, String>,
	) {
		None("" to ""), Left("=" to ""), Center("=" to "="), Right("" to "=")
	}
}
