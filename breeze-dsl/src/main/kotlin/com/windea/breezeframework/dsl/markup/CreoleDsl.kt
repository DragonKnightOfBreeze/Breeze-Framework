@file:Suppress("DuplicatedCode", "NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markup.CreoleConfig.emptyColumnText
import com.windea.breezeframework.dsl.markup.CreoleConfig.repeatableMarkerCount

//REGION Dsl annotations

@DslMarker
internal annotation class CreoleDsl

@DslMarker
internal annotation class InlineCreoleDsl

//REGION Dsl & Dsl config & Dsl elements

@CreoleDsl
inline fun creole(builder: Creole.() -> Unit) = Creole().also { it.builder() }

@Reference("[Creole](http://plantuml.com/zh/creole)")
@CreoleDsl
class Creole @PublishedApi internal constructor() : DslBuilder, InlineCreoleDslEntry, CreoleDslEntry {
	override val content: MutableList<CreoleDslTopElement> = mutableListOf()
	
	override fun toString(): String = content.joinToString("\n\n")
}

/**Creole配置。*/
@CreoleDsl
object CreoleConfig : DslConfig {
	/**缩进长度。*/
	var indentSize = 2
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**可重复标记的个数。*/
	var repeatableMarkerCount = 4
		set(value) = run { field = value.coerceIn(2, 4) }
	var truncated = "..."
	var preferDoubleQuote: Boolean = true
	var emptyColumnSize: Int = 4
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(preferDoubleQuote) "\"" else "'"
	@PublishedApi internal val emptyColumnText get() = " " * emptyColumnSize
}


interface InlineCreoleDslEntry

@InlineCreoleDsl
inline fun InlineCreoleDslEntry.text(text: String) = CreoleText(text)

@InlineCreoleDsl
inline fun InlineCreoleDslEntry.escaped(text: String) = CreoleEscapedText(text)

@InlineCreoleDsl
inline fun InlineCreoleDslEntry.icon(name: String) = CreoleIcon(name)

@InlineCreoleDsl
inline fun InlineCreoleDslEntry.unicode(number: Int) = CreoleUnicode(number)

@InlineCreoleDsl
inline fun InlineCreoleDslEntry.b(text: String) = CreoleBoldText(text)

@InlineCreoleDsl
inline fun InlineCreoleDslEntry.i(text: String) = CreoleItalicText(text)

@InlineCreoleDsl
inline fun InlineCreoleDslEntry.m(text: String) = CreoleMonospacedText(text)

@InlineCreoleDsl
inline fun InlineCreoleDslEntry.s(text: String) = CreoleStrokedText(text)

@InlineCreoleDsl
inline fun InlineCreoleDslEntry.u(text: String) = CreoleUnderlinedText(text)

@InlineCreoleDsl
inline fun InlineCreoleDslEntry.w(text: String) = CreoleWavedText(text)


interface CreoleDslEntry : TextContent<CreoleTextBlock>, InlineContent<CreoleTextBlock> {
	val content: MutableList<CreoleDslTopElement>
	
	@CreoleDsl
	override fun String.unaryPlus() = textBlock { this }
	
	@CreoleDsl
	override fun String.not() = textBlock { this@CreoleDslEntry.content.clear();this }
}

@CreoleDsl
inline fun CreoleDslEntry.textBlock(lazyText: () -> String) =
	CreoleTextBlock(lazyText()).also { content += it }

@CreoleDsl
inline fun CreoleDslEntry.list(builder: CreoleList.() -> Unit) =
	CreoleList().also { it.builder() }.also { content += it }

@CreoleDsl
inline fun CreoleDslEntry.hr(type: CreoleHorizontalLineType = CreoleHorizontalLineType.Normal) =
	CreoleHorizontalLine(type).also { content += it }

@CreoleDsl
inline fun CreoleDslEntry.title(text: String, type: CreoleHorizontalLineType = CreoleHorizontalLineType.Normal) =
	CreoleTitle(text, type).also { content += it }

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

inline fun CreoleDslEntry.tree(title: String, builder: CreoleTree.() -> Unit) =
	CreoleTree(title).also { it.builder() }.also { content += it }

@CreoleDsl
inline fun CreoleDslEntry.table(builder: CreoleTable.() -> Unit) =
	CreoleTable().also { it.builder() }.also { content += it }


@InlineCreoleDsl
interface InlineCreoleDslElement : InlineDslElement

@CreoleDsl
interface CreoleDslElement : DslElement, InlineCreoleDslEntry

@CreoleDsl
interface CreoleDslTopElement : CreoleDslElement


/**Creole文本。*/
@InlineCreoleDsl
class CreoleText @PublishedApi internal constructor(
	val text: String
) : InlineCreoleDslElement {
	override fun toString() = text
}

/**Creole转义文本。*/
@InlineCreoleDsl
class CreoleEscapedText @PublishedApi internal constructor(
	val text: String
) : InlineCreoleDslElement {
	override fun toString() = "~$text"
}

/**Creole图标。可以使用open iconic图标。*/
@Reference("[OpenIconic](https://useiconic.com/open/)")
@InlineCreoleDsl
class CreoleIcon @PublishedApi internal constructor(
	val name: String
) : InlineCreoleDslElement {
	override fun toString() = "<&$name>"
}

/**Creole Unicode字符。*/
@InlineCreoleDsl
class CreoleUnicode @PublishedApi internal constructor(
	val number: Int
) : InlineCreoleDslElement {
	override fun toString() = "<U+$number>"
}


/**Creole富文本。*/
@InlineCreoleDsl
sealed class CreoleRichText(
	val markers: String,
	val text: String
) : InlineCreoleDslElement {
	override fun toString() = "$markers$text$markers"
}

/**Creole加粗文本。*/
@InlineCreoleDsl
class CreoleBoldText @PublishedApi internal constructor(text: String) : CreoleRichText("**", text)

/**Creole斜体文本。*/
@InlineCreoleDsl
class CreoleItalicText @PublishedApi internal constructor(text: String) : CreoleRichText("//", text)

/**Creole代码文本。*/
@InlineCreoleDsl
class CreoleMonospacedText @PublishedApi internal constructor(text: String) : CreoleRichText("\"\"", text)

/**Creole删除文本。*/
@InlineCreoleDsl
class CreoleStrokedText @PublishedApi internal constructor(text: String) : CreoleRichText("--", text)

/**Creole下划线文本。*/
@InlineCreoleDsl
class CreoleUnderlinedText @PublishedApi internal constructor(text: String) : CreoleRichText("__", text)

/**Creole波浪下划线文本。*/
@InlineCreoleDsl
class CreoleWavedText @PublishedApi internal constructor(text: String) : CreoleRichText("~~", text)


/**Creole文本块。*/
@CreoleDsl
class CreoleTextBlock @PublishedApi internal constructor(
	val text: String
) : CreoleDslTopElement {
	override fun toString() = text
}


/**Creole水平分割线。*/
@CreoleDsl
class CreoleHorizontalLine @PublishedApi internal constructor(
	val type: CreoleHorizontalLineType = CreoleHorizontalLineType.Normal
) : CreoleDslTopElement {
	override fun toString() = type.text * repeatableMarkerCount
}

/**Creole水平分割标题。*/
@CreoleDsl
class CreoleTitle @PublishedApi internal constructor(
	val text: String,
	val type: CreoleHorizontalLineType = CreoleHorizontalLineType.Normal
) : CreoleDslTopElement {
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
class CreoleHeading1 @PublishedApi internal constructor(text: String) : CreoleHeading(1, text)

/**Creole二级标题。*/
@CreoleDsl
class CreoleHeading2 @PublishedApi internal constructor(text: String) : CreoleHeading(2, text)

/**Creole三级标题。*/
@CreoleDsl
class CreoleHeading3 @PublishedApi internal constructor(text: String) : CreoleHeading(3, text)

/**Creole四级标题。*/
@CreoleDsl
class CreoleHeading4 @PublishedApi internal constructor(text: String) : CreoleHeading(4, text)


//NOTE Do not provide dsl for Legacy HTML


/**Creole列表。*/
@CreoleDsl
class CreoleList @PublishedApi internal constructor() : CreoleDslTopElement {
	val nodes: MutableList<CreoleListNode> = mutableListOf()
	
	override fun toString() = nodes.joinToString("\n")
	
	
	@CreoleDsl
	inline fun ol(text: String) =
		CreoleOrderedListNode(text).also { nodes += it }
	
	@CreoleDsl
	inline fun ol(text: String, builder: CreoleOrderedListNode.() -> Unit) =
		CreoleOrderedListNode(text).also { it.builder() }.also { nodes += it }
	
	@CreoleDsl
	inline fun ul(text: String) =
		CreoleUnorderedListNode(text).also { nodes += it }
	
	@CreoleDsl
	inline fun ul(text: String, builder: CreoleUnorderedListNode.() -> Unit) =
		CreoleUnorderedListNode(text).also { it.builder() }.also { nodes += it }
}

/**Creole列表节点。*/
@CreoleDsl
sealed class CreoleListNode(
	protected val prefixMarker: String,
	val text: String
) : CreoleDslElement {
	val nodes: MutableList<CreoleListNode> = mutableListOf()
	
	override fun toString(): String {
		val nodesSnippet = if(nodes.isEmpty()) "" else nodes.joinToString("\n", "\n") { "${it.prefixMarker}$it" }
		return "$prefixMarker $text$nodesSnippet"
	}
	
	
	@CreoleDsl
	inline fun ol(text: String) =
		CreoleOrderedListNode(text).also { nodes += it }
	
	@CreoleDsl
	inline fun ol(text: String, builder: CreoleOrderedListNode.() -> Unit) =
		CreoleOrderedListNode(text).also { it.builder() }.also { nodes += it }
	
	@CreoleDsl
	inline fun ul(text: String) =
		CreoleUnorderedListNode(text).also { nodes += it }
	
	@CreoleDsl
	inline fun ul(text: String, builder: CreoleUnorderedListNode.() -> Unit) =
		CreoleUnorderedListNode(text).also { it.builder() }.also { nodes += it }
}

/**Creole有序列表节点。*/
@CreoleDsl
class CreoleOrderedListNode @PublishedApi internal constructor(text: String) : CreoleListNode("#", text)

/**Creole无序列表节点。*/
@CreoleDsl
class CreoleUnorderedListNode @PublishedApi internal constructor(text: String) : CreoleListNode("*", text)


/**Creole树。*/
@CreoleDsl
class CreoleTree @PublishedApi internal constructor(
	val title: String
) : CreoleDslTopElement, BlockContent<CreoleTreeNode> {
	val nodes: MutableList<CreoleTreeNode> = mutableListOf()
	
	override fun toString(): String {
		val nodesSnippet = if(nodes.isEmpty()) "" else nodes.joinToString("\n", "\n")
		return "$title$nodesSnippet"
	}
	
	
	@CreoleDsl
	inline fun node(text: String) =
		CreoleTreeNode(text).also { nodes += it }
	
	@CreoleDsl
	inline fun node(text: String, builder: CreoleTreeNode.() -> Unit) =
		CreoleTreeNode(text).also { it.builder() }.also { nodes += it }
	
	@CreoleDsl
	override fun String.invoke() = node(this)
	
	@CreoleDsl
	override fun String.invoke(builder: CreoleTreeNode.() -> Unit) = node(this, builder)
}

/**Creole树节点。*/
@CreoleDsl
class CreoleTreeNode @PublishedApi internal constructor(
	val text: String
) : CreoleDslElement, BlockContent<CreoleTreeNode> {
	val nodes: MutableList<CreoleTreeNode> = mutableListOf()
	
	override fun toString(): String {
		//include prefix "|_", add it to first line, add spaces to other lines
		val textSnippet = "|_ $text"
		val nodesSnippet = if(nodes.isEmpty()) "" else nodes.joinToString("\n", "\n") { it.toString().prependIndent("   ") }
		return "$textSnippet$nodesSnippet"
	}
	
	
	@CreoleDsl
	inline fun node(text: String) =
		CreoleTreeNode(text).also { nodes += it }
	
	@CreoleDsl
	inline fun node(text: String, builder: CreoleTreeNode.() -> Unit) =
		CreoleTreeNode(text).also { it.builder() }.also { nodes += it }
	
	@CreoleDsl
	override fun String.invoke() = node(this)
	
	@CreoleDsl
	override fun String.invoke(builder: CreoleTreeNode.() -> Unit) = node(this, builder)
}


//TODO pretty format
/**Creole表格。*/
@CreoleDsl
class CreoleTable @PublishedApi internal constructor() : CreoleDslTopElement {
	var header: CreoleTableHeader = CreoleTableHeader()
	val rows: MutableList<CreoleTableRow> = mutableListOf()
	var columnSize: Int? = null
	
	override fun toString(): String {
		require(rows.isNotEmpty()) { "Table row size must be greater than 0." }
		
		//NOTE actual column size may not equal to columns.size, and can be user defined
		val actualColumnSize = columnSize ?: maxOf(header.columns.size, rows.map { it.columns.size }.max()!!)
		header.columnSize = actualColumnSize
		rows.forEach { it.columnSize = actualColumnSize }
		
		val headerRowSnippet = header.toString()
		val rowsSnippet = rows.joinToString("\n")
		return "$headerRowSnippet\n$rowsSnippet"
	}
	
	
	@CreoleDsl
	inline fun header(builder: CreoleTableHeader.() -> Unit) =
		CreoleTableHeader().also { it.builder() }.also { header = it }
	
	@CreoleDsl
	inline fun row(builder: CreoleTableRow.() -> Unit) =
		CreoleTableRow().also { it.builder() }.also { rows += it }
	
	@CreoleDsl
	inline infix fun columnSize(size: Int) =
		this.also { this.columnSize = size }
}

/**Creole表格头部。*/
@CreoleDsl
class CreoleTableHeader @PublishedApi internal constructor() : CreoleDslElement, TextContent<CreoleTableColumn> {
	val columns: MutableList<CreoleTableColumn> = mutableListOf()
	var columnSize: Int? = null
	
	override fun toString(): String {
		require(columns.isNotEmpty()) { "Table row column size must be greater than 0." }
		
		//NOTE actual column size may not equal to columns.size
		return when {
			columnSize == null || columnSize == columns.size -> columns.map { it.toStringInHeader() }
			columnSize!! < columns.size -> columns.subList(0, columnSize!!).map { it.toStringInHeader() }
			else -> columns.map { it.toStringInHeader() }.toMutableList().fillToSize(emptyColumnText, columnSize!!)
		}.joinToString("|", "|", "|")
	}
	
	
	@CreoleDsl
	inline fun column(text: String) =
		CreoleTableColumn(text).also { columns += it }
	
	@CreoleDsl
	inline infix fun CreoleTableColumn.align(alignment: CreoleTableAlignment) =
		this.also { it.alignment = alignment }
	
	@CreoleDsl
	override fun String.unaryPlus() = column(this)
}

/**Creole表格行。*/
@CreoleDsl
open class CreoleTableRow @PublishedApi internal constructor() : CreoleDslElement, TextContent<CreoleTableColumn> {
	val columns: MutableList<CreoleTableColumn> = mutableListOf()
	var columnSize: Int? = null
	
	override fun toString(): String {
		require(columns.isNotEmpty()) { "Table row column size must be greater than 0." }
		
		//NOTE actual column size may not equal to columns.size
		return when {
			columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
			columnSize!! < columns.size -> columns.subList(0, columnSize!!).map { it.toString() }
			else -> columns.map { it.toString() }.toMutableList().fillToSize(emptyColumnText, columnSize!!)
		}.joinToString(" | ", "| ", " |")
	}
	
	
	@CreoleDsl
	inline fun column(text: String = emptyColumnText) =
		CreoleTableColumn(text).also { columns += it }
	
	@CreoleDsl
	override fun String.unaryPlus() = column(this)
}

/**Creole表格列。*/
@CreoleDsl
open class CreoleTableColumn @PublishedApi internal constructor(
	val text: String = emptyColumnText
) : CreoleDslElement {
	var color: String? = null
	var alignment: CreoleTableAlignment = CreoleTableAlignment.None //only for columns in table header
	
	override fun toString(): String {
		val colorSnippet = color?.let { "<$color> " } ?: ""
		return "$colorSnippet$text"
	}
	
	fun toStringInHeader(): String {
		val colorSnippet = color?.let { "<$color> " } ?: ""
		val (l, r) = alignment.textPair
		return "$l $colorSnippet$text $r"
	}
	
	
	@CreoleDsl
	inline infix fun color(color: String) =
		this.also { it.color = color }
}

//REGION Enumerations and constants

/**Creole水平线类型。*/
@CreoleDsl
enum class CreoleHorizontalLineType(
	val text: String
) {
	Normal("-"), Double("="), Strong("_"), Dotted(".")
}

/**Creole表格的对齐方式。*/
@CreoleDsl
enum class CreoleTableAlignment(
	val textPair: Pair<String, String>
) {
	None("" to ""), Left("=" to ""), Center("=" to "="), Right("" to "=")
}
