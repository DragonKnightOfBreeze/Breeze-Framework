@file:Suppress("DuplicatedCode", "NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.dsl.creole

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.creole.CreoleConfig.emptyColumnText
import com.windea.breezeframework.dsl.creole.CreoleConfig.repeatableMarkerCount

//DELAY add dsl element: HtmlBlock

//region top annotations and interfaces
/**Creole的Dsl。*/
@Reference("[Creole](http://plantuml.com/zh/creole)")
@DslMarker
@MustBeDocumented
internal annotation class CreoleDsl

/**Creole。*/
@CreoleDsl
class Creole @PublishedApi internal constructor() : DslDocument, CreoleDslEntry, CreoleDslInlineEntry {
	override val content: MutableList<CreoleDslTopElement> = mutableListOf()

	override fun toString() = content.joinToString("\n\n")
}

/**Creole的配置。*/
@Reference("[Creole](http://plantuml.com/zh/creole)")
@CreoleDsl
object CreoleConfig : DslConfig {
	private val indentSizeRange = -2..8
	private val repeatableMarkerCountRange = 3..12

	var indentSize = 2
		set(value) = run { if(value in indentSizeRange) field = value }
	var repeatableMarkerCount = 4
		set(value) = run { if(value in repeatableMarkerCountRange) field = value }
	var truncated = "..."
	var preferDoubleQuote: Boolean = true
	var emptyColumnSize: Int = 4

	@PublishedApi internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	@PublishedApi internal val quote get() = if(preferDoubleQuote) '"' else '\''
	@PublishedApi internal val emptyColumnText get() = " " * emptyColumnSize
}
//endregion

//region dsl interfaces
/**Creole Dsl的内联入口。*/
@CreoleDsl
interface CreoleDslInlineEntry : DslEntry

/**Creole Dsl的入口。*/
@CreoleDsl
interface CreoleDslEntry : DslEntry, WithText<CreoleTextBlock> {
	val content: MutableList<CreoleDslTopElement>

	@CreoleDsl
	override fun String.unaryPlus() = CreoleTextBlock(this).also { content += it }
}

/**Creole Dsl的元素。*/
@CreoleDsl
interface CreoleDslElement : DslElement, CreoleDslInlineEntry

/**Creole Dsl的内联元素。*/
@CreoleDsl
interface CreoleDslInlineElement : CreoleDslElement

/**Creole Dsl的顶级元素。*/
@CreoleDsl
interface CreoleDslTopElement : CreoleDslElement
//endregion

//region dsl elements
/**Creole文本。*/
@CreoleDsl
class CreoleText @PublishedApi internal constructor(
	val text: String
) : CreoleDslInlineElement {
	override fun toString(): String {
		return text
	}
}

/**Creole转义文本。*/
@CreoleDsl
class CreoleEscapedText @PublishedApi internal constructor(
	val text: String
) : CreoleDslInlineElement {
	override fun toString(): String {
		return "~$text"
	}
}

/**Creole图标。可以使用open iconic图标。*/
@Reference("[OpenIconic](https://useiconic.com/open/)")
@CreoleDsl
class CreoleIcon @PublishedApi internal constructor(
	val name: String
) : CreoleDslInlineElement {
	override fun toString(): String {
		return "<&$name>"
	}
}

/**Creole Unicode字符。*/
@CreoleDsl
class CreoleUnicode @PublishedApi internal constructor(
	val number: Int
) : CreoleDslInlineElement {
	override fun toString(): String {
		return "<U+$number>"
	}
}

/**Creole富文本。*/
@CreoleDsl
sealed class CreoleRichText(
	val markers: String,
	val text: String
) : CreoleDslInlineElement {
	override fun toString(): String {
		return "$markers$text$markers"
	}
}

/**Creole加粗文本。*/
@CreoleDsl
class CreoleBoldText @PublishedApi internal constructor(
	text: String
) : CreoleRichText("**", text)

/**Creole斜体文本。*/
@CreoleDsl
class CreoleItalicText @PublishedApi internal constructor(
	text: String
) : CreoleRichText("//", text)

/**Creole代码文本。*/
@CreoleDsl
class CreoleMonospacedText @PublishedApi internal constructor(
	text: String
) : CreoleRichText("\"\"", text)

/**Creole删除文本。*/
@CreoleDsl
class CreoleStrokedText @PublishedApi internal constructor(
	text: String
) : CreoleRichText("--", text)

/**Creole下划线文本。*/
@CreoleDsl
class CreoleUnderlinedText @PublishedApi internal constructor(
	text: String
) : CreoleRichText("__", text)

/**Creole波浪下划线文本。*/
@CreoleDsl
class CreoleWavedText @PublishedApi internal constructor(
	text: String
) : CreoleRichText("~~", text)


/**Creole文本块。*/
@CreoleDsl
class CreoleTextBlock @PublishedApi internal constructor(
	val text: String
) : CreoleDslTopElement {
	override fun toString(): String {
		return text
	}
}

/**Creole水平分割线。*/
@CreoleDsl
open class CreoleHorizontalLine @PublishedApi internal constructor(
	val type: Type = Type.Normal
) : CreoleDslTopElement {
	override fun toString(): String {
		return type.text * repeatableMarkerCount
	}

	enum class Type(val text: String) {
		Normal("-"), Double("="), Strong("_"), Dotted(".")
	}
}

/**Creole水平标题。*/
@CreoleDsl
class CreoleHorizontalTitle @PublishedApi internal constructor(
	val text: String,
	type: Type = Type.Normal
) : CreoleHorizontalLine(type) {
	override fun toString(): String {
		val markers = type.text * 2
		return "$markers$text$markers"
	}
}

/**Creole标题。*/
@CreoleDsl
sealed class CreoleHeading(
	val headingLevel: Int,
	val text: String
) : CreoleDslTopElement {
	override fun toString(): String {
		val prefixMarkers = "#" * headingLevel
		return "$prefixMarkers $text"
	}
}

/**Creole一级标题。*/
@CreoleDsl
class CreoleHeading1 @PublishedApi internal constructor(
	text: String
) : CreoleHeading(1, text)

/**Creole二级标题。*/
@CreoleDsl
class CreoleHeading2 @PublishedApi internal constructor(
	text: String
) : CreoleHeading(2, text)

/**Creole三级标题。*/
@CreoleDsl
class CreoleHeading3 @PublishedApi internal constructor(
	text: String
) : CreoleHeading(3, text)

/**Creole四级标题。*/
@CreoleDsl
class CreoleHeading4 @PublishedApi internal constructor(
	text: String
) : CreoleHeading(4, text)

/**Creole列表。*/
@CreoleDsl
class CreoleList @PublishedApi internal constructor() : CreoleDslTopElement {
	val nodes: MutableList<CreoleListNode> = mutableListOf()

	override fun toString(): String {
		return nodes.joinToString("\n")
	}
}

/**Creole列表节点。*/
@CreoleDsl
sealed class CreoleListNode(
	protected val prefixMarker: String,
	val text: String
) : CreoleDslElement {
	val nodes: MutableList<CreoleListNode> = mutableListOf()

	override fun toString(): String {
		val nodesSnippet = nodes.orNull()?.joinToString("\n", "\n") { "${it.prefixMarker}$it" }.orEmpty()
		return "$prefixMarker $text$nodesSnippet"
	}
}

/**Creole有序列表节点。*/
@CreoleDsl
class CreoleOrderedListNode @PublishedApi internal constructor(
	text: String
) : CreoleListNode("#", text)

/**Creole无序列表节点。*/
@CreoleDsl
class CreoleUnorderedListNode @PublishedApi internal constructor(
	text: String
) : CreoleListNode("*", text)

/**Creole树。*/
@CreoleDsl
class CreoleTree @PublishedApi internal constructor(
	val title: String
) : CreoleDslTopElement {
	val nodes: MutableList<CreoleTreeNode> = mutableListOf()

	override fun toString(): String {
		val nodesSnippet = nodes.joinToString("\n", "\n")
		return "$title$nodesSnippet"
	}
}

/**Creole树节点。*/
@CreoleDsl
class CreoleTreeNode @PublishedApi internal constructor(
	val text: String
) : CreoleDslElement {
	val nodes: MutableList<CreoleTreeNode> = mutableListOf()

	override fun toString(): String {
		//include prefix "|_", add it to first line, add spaces to other lines
		val textSnippet = "|_ $text"
		val nodesSnippet = nodes.orNull()?.joinToString("\n", "\n") { it.toString().prependIndent("   ") }.orEmpty()
		return "$textSnippet$nodesSnippet"
	}
}

//DELAY pretty format
/**Creole表格。*/
@CreoleDsl
class CreoleTable @PublishedApi internal constructor() : CreoleDslTopElement {
	var header: CreoleTableHeader = CreoleTableHeader()
	val rows: MutableList<CreoleTableRow> = mutableListOf()
	var columnSize: Int? = null

	override fun toString(): String {
		require(rows.isNotEmpty()) { "Table row size must be positive." }

		//actual column size may not equal to columns.size, and can be user defined
		val actualColumnSize = columnSize ?: maxOf(header.columns.size, rows.map { it.columns.size }.max() ?: 0)
		header.columnSize = actualColumnSize
		rows.forEach { it.columnSize = actualColumnSize }

		val headerRowSnippet = header.toString()
		val rowsSnippet = rows.joinToString("\n")
		return "$headerRowSnippet\n$rowsSnippet"
	}

	enum class Alignment(val textPair: Pair<String, String>) {
		None("" to ""), Left("=" to ""), Center("=" to "="), Right("" to "=")
	}
}

/**Creole表格头部。*/
@CreoleDsl
class CreoleTableHeader @PublishedApi internal constructor() : CreoleDslElement, WithText<CreoleTableColumn> {
	val columns: MutableList<CreoleTableColumn> = mutableListOf()
	var columnSize: Int? = null

	override fun toString(): String {
		require(columns.isNotEmpty()) { "Table row column size must be positive." }

		//actual column size may not equal to columns.size
		return when {
			columnSize == null || columnSize == columns.size -> columns.map { it.toStringInHeader() }
			else -> columns.map { it.toStringInHeader() }.fillEnd(columnSize!!, emptyColumnText)
		}.joinToString("|", "|", "|")
	}

	@CreoleDsl
	override fun String.unaryPlus() = column(this)

	@CreoleDsl
	inline infix fun CreoleTableColumn.align(alignment: CreoleTable.Alignment) =
		this.also { it.alignment = alignment }
}

/**Creole表格行。*/
@CreoleDsl
open class CreoleTableRow @PublishedApi internal constructor() : CreoleDslElement, WithText<CreoleTableColumn> {
	val columns: MutableList<CreoleTableColumn> = mutableListOf()
	var columnSize: Int? = null

	override fun toString(): String {
		require(columns.isNotEmpty()) { "Table row column size must be positive." }

		//actual column size may not equal to columns.size
		return when {
			columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
			else -> columns.map { it.toString() }.fillEnd(columnSize!!, emptyColumnText)
		}.joinToString(" | ", "| ", " |")
	}

	@CreoleDsl
	override fun String.unaryPlus() = column(this)
}

/**Creole表格列。*/
@CreoleDsl
open class CreoleTableColumn @PublishedApi internal constructor(
	val text: String = emptyColumnText
) : CreoleDslElement {
	var color: String? = null
	var alignment: CreoleTable.Alignment = CreoleTable.Alignment.None //only for columns in table header

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
//endregion

//region build extensions
@CreoleDsl
inline fun creole(block: Creole.() -> Unit) = Creole().also { it.block() }

@InlineDsl
@CreoleDsl
inline fun CreoleDslInlineEntry.text(text: String) = CreoleText(text).toString()

@InlineDsl
@CreoleDsl
inline fun CreoleDslInlineEntry.escaped(text: String) = CreoleEscapedText(text).toString()

@InlineDsl
@CreoleDsl
inline fun CreoleDslInlineEntry.icon(name: String) = CreoleIcon(name).toString()

@InlineDsl
@CreoleDsl
inline fun CreoleDslInlineEntry.unicode(number: Int) = CreoleUnicode(number).toString()

@InlineDsl
@CreoleDsl
inline fun CreoleDslInlineEntry.b(text: String) = CreoleBoldText(text).toString()

@InlineDsl
@CreoleDsl
inline fun CreoleDslInlineEntry.i(text: String) = CreoleItalicText(text).toString()

@InlineDsl
@CreoleDsl
inline fun CreoleDslInlineEntry.m(text: String) = CreoleMonospacedText(text).toString()

@InlineDsl
@CreoleDsl
inline fun CreoleDslInlineEntry.s(text: String) = CreoleStrokedText(text).toString()

@InlineDsl
@CreoleDsl
inline fun CreoleDslInlineEntry.u(text: String) = CreoleUnderlinedText(text).toString()

@InlineDsl
@CreoleDsl
inline fun CreoleDslInlineEntry.w(text: String) = CreoleWavedText(text).toString()

@CreoleDsl
inline fun CreoleDslEntry.textBlock(lazyText: () -> String) =
	CreoleTextBlock(lazyText()).also { content += it }

@CreoleDsl
inline fun CreoleDslEntry.list(block: CreoleList.() -> Unit) =
	CreoleList().also { it.block() }.also { content += it }

@CreoleDsl
inline fun CreoleDslEntry.hr(type: CreoleHorizontalLine.Type = CreoleHorizontalLine.Type.Normal) =
	CreoleHorizontalLine(type).also { content += it }

@CreoleDsl
inline fun CreoleDslEntry.title(text: String, type: CreoleHorizontalLine.Type = CreoleHorizontalLine.Type.Normal) =
	CreoleHorizontalTitle(text, type).also { content += it }

@CreoleDsl
inline fun CreoleDslEntry.h1(text: String) =
	CreoleHeading1(text).also { content += it }

@CreoleDsl
inline fun CreoleDslEntry.h2(text: String) =
	CreoleHeading2(text).also { content += it }

@CreoleDsl
inline fun CreoleDslEntry.h3(text: String) =
	CreoleHeading3(text).also { content += it }

@CreoleDsl
inline fun CreoleDslEntry.h4(text: String) =
	CreoleHeading4(text).also { content += it }

inline fun CreoleDslEntry.tree(title: String, block: CreoleTree.() -> Unit) =
	CreoleTree(title).also { it.block() }.also { content += it }

@CreoleDsl
inline fun CreoleDslEntry.table(block: CreoleTable.() -> Unit) =
	CreoleTable().also { it.block() }.also { content += it }

@CreoleDsl
inline fun CreoleList.ol(text: String, block: CreoleOrderedListNode.() -> Unit = {}) =
	CreoleOrderedListNode(text).also { it.block() }.also { nodes += it }

@CreoleDsl
inline fun CreoleList.ul(text: String, block: CreoleUnorderedListNode.() -> Unit = {}) =
	CreoleUnorderedListNode(text).also { it.block() }.also { nodes += it }

@CreoleDsl
inline fun CreoleListNode.ol(text: String, block: CreoleOrderedListNode.() -> Unit = {}) =
	CreoleOrderedListNode(text).also { it.block() }.also { nodes += it }

@CreoleDsl
inline fun CreoleListNode.ul(text: String, block: CreoleUnorderedListNode.() -> Unit = {}) =
	CreoleUnorderedListNode(text).also { it.block() }.also { nodes += it }

@CreoleDsl
inline fun CreoleTree.node(text: String, block: CreoleTreeNode.() -> Unit = {}) =
	CreoleTreeNode(text).also { it.block() }.also { nodes += it }

@CreoleDsl
inline fun CreoleTreeNode.node(text: String, block: CreoleTreeNode.() -> Unit = {}) =
	CreoleTreeNode(text).also { it.block() }.also { nodes += it }

@CreoleDsl
inline fun CreoleTable.header(block: CreoleTableHeader.() -> Unit) =
	CreoleTableHeader().also { it.block() }.also { header = it }

@CreoleDsl
inline fun CreoleTable.row(block: CreoleTableRow.() -> Unit) =
	CreoleTableRow().also { it.block() }.also { rows += it }

@CreoleDsl
inline infix fun CreoleTable.columnSize(size: Int) =
	this.also { this.columnSize = size }

@CreoleDsl
inline fun CreoleTableHeader.column(text: String = emptyColumnText) =
	CreoleTableColumn(text).also { columns += it }

@CreoleDsl
inline fun CreoleTableRow.column(text: String = emptyColumnText) =
	CreoleTableColumn(text).also { columns += it }

@CreoleDsl
inline infix fun CreoleTableColumn.color(color: String) =
	this.also { it.color = color }
//endregion
