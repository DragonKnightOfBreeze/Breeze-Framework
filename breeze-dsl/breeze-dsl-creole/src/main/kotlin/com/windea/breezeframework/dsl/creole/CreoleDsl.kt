@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.creole

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.creole.Creole.*
import com.windea.breezeframework.dsl.creole.Creole.Companion.config
import com.windea.breezeframework.dsl.creole.Creole.List

/**
 * Creole的Dsl。
 * 参见：[Creole](http://plantuml.com/zh/creole)
 */
@DslMarker
@MustBeDocumented
annotation class CreoleDsl

/**Creole的内联入口。*/
@CreoleDsl
interface CreoleInlineEntry : DslEntry {
	@InlineDslFunction
	@CreoleDsl
	fun CreoleInlineEntry.escaped(text:CharSequence) = EscapedText(text)

	@InlineDslFunction
	@CreoleDsl
	fun CreoleInlineEntry.icon(name:String) = Icon(name)

	@InlineDslFunction
	@CreoleDsl
	fun CreoleInlineEntry.unicode(number:Int) = UnicodeText(number.toString())

	@InlineDslFunction
	@CreoleDsl
	fun CreoleInlineEntry.b(text:CharSequence) = BoldText(text)

	@InlineDslFunction
	@CreoleDsl
	fun CreoleInlineEntry.i(text:CharSequence) = ItalicText(text)

	@InlineDslFunction
	@CreoleDsl
	fun CreoleInlineEntry.m(text:String) = MonospacedText(text)

	@InlineDslFunction
	@CreoleDsl
	fun CreoleInlineEntry.s(text:CharSequence) = StrokedText(text)

	@InlineDslFunction
	@CreoleDsl
	fun CreoleInlineEntry.u(text:CharSequence) = UnderlineText(text)

	@InlineDslFunction
	@CreoleDsl
	fun CreoleInlineEntry.w(text:CharSequence) = WavedText(text)
}

/**Creole的入口。*/
@CreoleDsl
interface CreoleEntry : DslEntry, WithText<TextBlock> {
	val content:MutableList<CreoleTopElement>

	override val contentString get() = content.joinToString("\n\n")

	@DslFunction
	@CreoleDsl
	override fun String.unaryPlus() = TextBlock(this).also { content += it }
}

/**Creole的内联元素。*/
@CreoleDsl
interface CreoleInlineElement : DslElement, CreoleElement

/**Creole的元素。*/
@CreoleDsl
interface CreoleElement : DslElement, CreoleInlineEntry

/**Creole的顶级元素。*/
@CreoleDsl
interface CreoleTopElement : CreoleElement

/**Creole。*/
@CreoleDsl
interface Creole {
	/**Creole的文档。*/
	class Document @PublishedApi internal constructor() : CreoleEntry, CreoleInlineEntry {
		override val content:MutableList<CreoleTopElement> = mutableListOf()

		override fun toString() = contentString
	}

	/**Creole的富文本。*/
	@CreoleDsl
	interface RichText : CreoleInlineElement, HandledCharSequence

	/**Creole的转义文本。*/
	@CreoleDsl
	inline class EscapedText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = "~$text"
	}

	/**
	 * Creole的图标。
	 * 参见：[OpenIconic](https://useiconic.com/open/)
	 * */
	@CreoleDsl
	inline class Icon @PublishedApi internal constructor(
		override val text:String
	) : RichText {
		override fun toString() = "<&$text>"
	}

	/**Creole的Unicode文本。*/
	@CreoleDsl
	inline class UnicodeText @PublishedApi internal constructor(
		override val text:String
	) : RichText {
		override fun toString() = "<U+$text>"
	}

	/**Creole的加粗文本。*/
	@CreoleDsl
	inline class BoldText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = "**$text**"
	}

	/**Creole的斜体文本。*/
	@CreoleDsl
	inline class ItalicText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = "//$text//"
	}

	/**Creole的代码文本。*/
	@CreoleDsl
	inline class MonospacedText @PublishedApi internal constructor(
		override val text:String
	) : RichText {
		override fun toString() = "--$text--"
	}

	/**Creole的删除线文本。*/
	@CreoleDsl
	inline class StrokedText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = "--$text--"
	}

	/**Creole的下划线文本。*/
	@CreoleDsl
	inline class UnderlineText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = "__${text}__"
	}

	/**Creole的波浪线文本。*/
	@CreoleDsl
	inline class WavedText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = "~~$text~~"
	}

	/**Creole的文本块。*/
	@CreoleDsl
	inline class TextBlock @PublishedApi internal constructor(
		val text:String
	) : CreoleTopElement {
		override fun toString() = text
	}

	//DELAY HtmlBlock

	/**Creole的水平分割线。*/
	@CreoleDsl
	inline class HorizontalLine @PublishedApi internal constructor(
		val type:HorizontalLineType = HorizontalLineType.Normal
	) : CreoleTopElement {
		override fun toString() = type.marker.repeat(config.markerCount)
	}

	/**Creole的水平标题。*/
	@CreoleDsl
	class HorizontalTitle @PublishedApi internal constructor(
		val text:String, val type:HorizontalLineType = HorizontalLineType.Normal
	) : CreoleTopElement {
		override fun toString() = type.marker.repeat(config.markerCount).let { "$it$text$it" }
	}

	/**Creole的标题。*/
	@CreoleDsl
	interface Heading : CreoleTopElement {
		val text:String
	}

	/**Creole的一级标题。*/
	@CreoleDsl
	inline class Heading1 @PublishedApi internal constructor(
		override val text:String
	) : Heading {
		override fun toString() = heading(text, 1)
	}

	/**Creole的二级标题。*/
	@CreoleDsl
	inline class Heading2 @PublishedApi internal constructor(
		override val text:String
	) : Heading {
		override fun toString() = heading(text, 2)
	}

	/**Creole的三级标题。*/
	@CreoleDsl
	inline class Heading3 @PublishedApi internal constructor(
		override val text:String
	) : Heading {
		override fun toString() = heading(text, 3)
	}

	/**Creole的四级标题。*/
	@CreoleDsl
	inline class Heading4 @PublishedApi internal constructor(
		override val text:String
	) : Heading {
		override fun toString() = heading(text, 4)
	}

	/**Creole的列表。*/
	@CreoleDsl
	class List @PublishedApi internal constructor() : CreoleTopElement {
		val nodes:MutableList<ListNode> = mutableListOf()

		override fun toString() = nodes.joinToString("\n")
	}

	/**Creole列表节点。*/
	@CreoleDsl
	abstract class ListNode(
		val prefixMarkers:String, val text:String
	) : CreoleElement {
		val nodes:MutableList<ListNode> = mutableListOf()

		override fun toString() = "$prefixMarkers $text${nodes.typingAll("\n", "\n")}"
	}

	/**Creole的有序列表节点。*/
	@CreoleDsl
	class OrderedListNode @PublishedApi internal constructor(text:String) : ListNode("#", text)

	/**Creole的无序列表节点。*/
	@CreoleDsl
	class UnorderedListNode @PublishedApi internal constructor(text:String) : ListNode("*", text)

	/**Creole的树。*/
	@CreoleDsl
	class Tree @PublishedApi internal constructor(
		val title:String
	) : CreoleTopElement {
		val nodes:MutableList<TreeNode> = mutableListOf()

		override fun toString() = "$title${nodes.typingAll("\n", "\n")}"
	}

	/**Creole的树节点。*/
	@CreoleDsl
	class TreeNode @PublishedApi internal constructor(
		val text:String
	) : CreoleElement {
		val nodes:MutableList<TreeNode> = mutableListOf()

		override fun toString() = "|_ $text${nodes.typingAll("\n", "\n").prependIndent(config.indent)}"
	}

	//DELAY pretty format
	/**Creole的表格。*/
	@CreoleDsl
	class Table @PublishedApi internal constructor() : CreoleTopElement {
		var header:TableHeader = TableHeader()
		val rows:MutableList<TableRow> = mutableListOf()
		var columnSize:Int? = null

		override fun toString():String {
			require(rows.isNotEmpty()) { "Table row size must be positive." }

			//actual column size may not equal to columns.size, and can be user defined
			val actualColumnSize = columnSize ?: maxOf(header.columns.size, rows.map { it.columns.size }.max() ?: 0)
			header.columnSize = actualColumnSize
			rows.forEach { it.columnSize = actualColumnSize }
			val headerRowSnippet = header.toString()
			val rowsSnippet = rows.joinToString("\n")
			return "$headerRowSnippet\n$rowsSnippet"
		}
	}

	/**Creole表格头部。*/
	@CreoleDsl
	class TableHeader @PublishedApi internal constructor() : CreoleElement, WithText<TableColumn> {
		val columns:MutableList<TableColumn> = mutableListOf()
		var columnSize:Int? = null

		override fun toString():String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			//actual column size may not equal to columns.size
			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toStringInHeader() }
				else -> columns.map { it.toStringInHeader() }.fillEnd(columnSize!!, config.emptyColumnText)
			}.joinToString("|", "|", "|")
		}

		@DslFunction
		@CreoleDsl
		override fun String.unaryPlus() = column(this)

		@DslFunction
		@CreoleDsl
		infix fun TableColumn.align(alignment:TableAlignment) = apply { this.alignment = alignment }
	}

	/**Creole表格行。*/
	@CreoleDsl
	open class TableRow @PublishedApi internal constructor() : CreoleElement, WithText<TableColumn> {
		val columns:MutableList<TableColumn> = mutableListOf()
		var columnSize:Int? = null

		override fun toString():String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			//actual column size may not equal to columns.size
			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
				else -> columns.map { it.toString() }.fillEnd(columnSize!!, config.emptyColumnText)
			}.joinToString(" | ", "| ", " |")
		}

		@DslFunction
		@CreoleDsl
		override fun String.unaryPlus() = column(this)
	}

	/**Creole表格列。*/
	@CreoleDsl
	open class TableColumn @PublishedApi internal constructor(
		val text:String = config.emptyColumnText
	) : CreoleElement {
		var color:String? = null
		var alignment:TableAlignment = TableAlignment.None //only for columns in table header

		override fun toString():String {
			val colorSnippet = color?.let { "<$color> " }.orEmpty()
			return "$colorSnippet$text"
		}

		fun toStringInHeader():String {
			val colorSnippet = color?.let { "<$color> " }.orEmpty()
			val (l, r) = alignment.textPair
			return "$l $colorSnippet$text $r"
		}
	}

	/**Creole水平线的类型。*/
	@CreoleDsl
	enum class HorizontalLineType(
		internal val marker:Char
	) {
		Normal('-'), Double('='), Strong('_'), Dotted('.')
	}

	/**Creole表格的对齐方式。*/
	@CreoleDsl
	enum class TableAlignment(
		internal val textPair:Pair<String, String>
	) {
		None("" to ""), Left("=" to ""), Center("=" to "="), Right("" to "=")
	}

	/**
	 * Creole的配置。
	 * @property indent 文本的缩进。
	 * @property emptyColumnText 表格的空单元格的文本。
	 * @property truncated 省略字符串。
	 * @property doubleQuoted 是否偏向使用双引号。
	 * @property markerCount 可重复标记的个数。
	 */
	@CreoleDsl
	data class Config(
		var indent:String = "  ",
		var emptyColumnText:String = "  ",
		var truncated:String = "...",
		var doubleQuoted:Boolean = true,
		var markerCount:Int = 3
	) {
		@PublishedApi internal val quote get() = if(doubleQuoted) '\"' else '\''
		@PublishedApi internal fun repeat(marker:Char) = marker.repeat(markerCount)
	}

	companion object {
		@PublishedApi internal val config = Config()

		@PublishedApi internal fun heading(text:String, headingLevel:Int) = "${"#".repeat(headingLevel)} $text"
	}
}


@TopDslFunction
@CreoleDsl
inline fun creole(block:Document.() -> Unit) = Document().apply(block)

@TopDslFunction
@CreoleDsl
inline fun creoleConfig(block:Config.() -> Unit) = config.block()

@DslFunction
@CreoleDsl
inline fun CreoleEntry.textBlock(lazyText:() -> String) =
	TextBlock(lazyText()).also { content += it }

@DslFunction
@CreoleDsl
inline fun CreoleEntry.list(block:List.() -> Unit) =
	List().apply(block).also { content += it }

@DslFunction
@CreoleDsl
fun CreoleEntry.hr(type:HorizontalLineType = HorizontalLineType.Normal) =
	HorizontalLine(type).also { content += it }

@DslFunction
@CreoleDsl
fun CreoleEntry.title(text:String, type:HorizontalLineType = HorizontalLineType.Normal) =
	HorizontalTitle(text, type).also { content += it }

@DslFunction
@CreoleDsl
fun CreoleEntry.h1(text:String) =
	Heading1(text).also { content += it }

@DslFunction
@CreoleDsl
fun CreoleEntry.h2(text:String) =
	Heading2(text).also { content += it }

@DslFunction
@CreoleDsl
fun CreoleEntry.h3(text:String) =
	Heading3(text).also { content += it }

@DslFunction
@CreoleDsl
fun CreoleEntry.h4(text:String) =
	Heading4(text).also { content += it }

@DslFunction
@CreoleDsl
inline fun CreoleEntry.tree(title:String, block:Tree.() -> Unit) =
	Tree(title).apply(block).also { content += it }

@DslFunction
@CreoleDsl
inline fun CreoleEntry.table(block:Table.() -> Unit) =
	Table().apply(block).also { content += it }

@DslFunction
@CreoleDsl
inline fun List.ol(text:String, block:OrderedListNode.() -> Unit = {}) =
	OrderedListNode(text).apply(block).also { nodes += it }

@DslFunction
@CreoleDsl
inline fun List.ul(text:String, block:UnorderedListNode.() -> Unit = {}) =
	UnorderedListNode(text).apply(block).also { nodes += it }

@DslFunction
@CreoleDsl
inline fun ListNode.ol(text:String, block:OrderedListNode.() -> Unit = {}) =
	OrderedListNode(text).apply(block).also { nodes += it }

@DslFunction
@CreoleDsl
inline fun ListNode.ul(text:String, block:UnorderedListNode.() -> Unit = {}) =
	UnorderedListNode(text).apply(block).also { nodes += it }

@DslFunction
@CreoleDsl
inline fun Tree.node(text:String, block:TreeNode.() -> Unit = {}) =
	TreeNode(text).apply(block).also { nodes += it }

@DslFunction
@CreoleDsl
inline fun TreeNode.node(text:String, block:TreeNode.() -> Unit = {}) =
	TreeNode(text).apply(block).also { nodes += it }

@DslFunction
@CreoleDsl
inline fun Table.header(block:TableHeader.() -> Unit) =
	TableHeader().apply(block).also { header = it }

@DslFunction
@CreoleDsl
inline fun Table.row(block:TableRow.() -> Unit) =
	TableRow().apply(block).also { rows += it }

@DslFunction
@CreoleDsl
infix fun Table.columnSize(size:Int) =
	apply { this.columnSize = size }

@DslFunction
@CreoleDsl
fun TableHeader.column(text:String = config.emptyColumnText) =
	TableColumn(text).also { columns += it }

@DslFunction
@CreoleDsl
fun TableRow.column(text:String = config.emptyColumnText) =
	TableColumn(text).also { columns += it }

@DslFunction
@CreoleDsl
infix fun TableColumn.color(color:String) =
	apply { this.color = color }
