@file:Suppress("DuplicatedCode", "NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markup.CreoleConfig.emptyColumnText
import com.windea.breezeframework.dsl.markup.CreoleConfig.repeatableMarkerCount

//REGION top annotations and interfaces

@DslMarker
private annotation class CreoleDsl

/**Creole。*/
@ReferenceApi("[Creole](http://plantuml.com/zh/creole)")
@CreoleDsl
class Creole @PublishedApi internal constructor() : DslBuilder, CreoleDslEntry {
	override val content: MutableList<CreoleDslTopElement> = mutableListOf()
	
	override fun toString() = content.joinToStringOrEmpty("\n\n")
}

//REGION dsl elements

@CreoleDsl
interface CreoleDslEntry : WithText<CreoleTextBlock> {
	val content: MutableList<CreoleDslTopElement>
	
	@CreoleDsl
	override fun String.unaryPlus() = textBlock(this)
}

/**Creole Dsl的元素。*/
@CreoleDsl
interface CreoleDslElement : DslElement

/**Creole Dsl的内联元素。*/
@CreoleDsl
interface CreoleDslInlineElement : CreoleDslElement

/**Creole Dsl的顶级元素。*/
@CreoleDsl
interface CreoleDslTopElement : CreoleDslElement


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
@ReferenceApi("[OpenIconic](https://useiconic.com/open/)")
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
class CreoleBoldText @PublishedApi internal constructor(text: String) : CreoleRichText("**", text)

/**Creole斜体文本。*/
@CreoleDsl
class CreoleItalicText @PublishedApi internal constructor(text: String) : CreoleRichText("//", text)

/**Creole代码文本。*/
@CreoleDsl
class CreoleMonospacedText @PublishedApi internal constructor(text: String) : CreoleRichText("\"\"", text)

/**Creole删除文本。*/
@CreoleDsl
class CreoleStrokedText @PublishedApi internal constructor(text: String) : CreoleRichText("--", text)

/**Creole下划线文本。*/
@CreoleDsl
class CreoleUnderlinedText @PublishedApi internal constructor(text: String) : CreoleRichText("__", text)

/**Creole波浪下划线文本。*/
@CreoleDsl
class CreoleWavedText @PublishedApi internal constructor(text: String) : CreoleRichText("~~", text)


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
class CreoleHorizontalLine @PublishedApi internal constructor(
	val type: CreoleHorizontalLineType = CreoleHorizontalLineType.Normal
) : CreoleDslTopElement {
	override fun toString(): String {
		return type.text * repeatableMarkerCount
	}
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
	
	override fun toString(): String {
		return nodes.joinToStringOrEmpty("\n")
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
		val nodesSnippet = nodes.joinToStringOrEmpty("\n", "\n") { "${it.prefixMarker}$it" }
		return "$prefixMarker $text$nodesSnippet"
	}
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
) : CreoleDslTopElement {
	val nodes: MutableList<CreoleTreeNode> = mutableListOf()
	
	override fun toString(): String {
		val nodesSnippet = nodes.joinToStringOrEmpty("\n", "\n")
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
		val nodesSnippet = nodes.joinToStringOrEmpty("\n", "\n") {
			it.toString().prependIndent("   ")
		}
		return "$textSnippet$nodesSnippet"
	}
}

//TODO pretty format
/**Creole表格。*/
@CreoleDsl
class CreoleTable @PublishedApi internal constructor() : CreoleDslTopElement {
	var header: CreoleTableHeader = CreoleTableHeader()
	val rows: MutableList<CreoleTableRow> = mutableListOf()
	var columnSize: Int? = null
	
	override fun toString(): String {
		require(rows.isNotEmpty()) { "Table row size must be positive." }
		
		//NOTE actual column size may not equal to columns.size, and can be user defined
		val actualColumnSize = columnSize ?: maxOf(header.columns.size, rows.map { it.columns.size }.max() ?: 0)
		header.columnSize = actualColumnSize
		rows.forEach { it.columnSize = actualColumnSize }
		
		val headerRowSnippet = header.toString()
		val rowsSnippet = rows.joinToStringOrEmpty("\n")
		return "$headerRowSnippet\n$rowsSnippet"
	}
}

/**Creole表格头部。*/
@CreoleDsl
class CreoleTableHeader @PublishedApi internal constructor() : CreoleDslElement, WithText<CreoleTableColumn> {
	val columns: MutableList<CreoleTableColumn> = mutableListOf()
	var columnSize: Int? = null
	
	override fun toString(): String {
		require(columns.isNotEmpty()) { "Table row column size must be positive." }
		
		//NOTE actual column size may not equal to columns.size
		return when {
			columnSize == null || columnSize == columns.size -> columns.map { it.toStringInHeader() }
			else -> columns.map { it.toStringInHeader() }.fillEnd(columnSize!!, emptyColumnText)
		}.joinToStringOrEmpty("|", "|", "|")
	}
	
	@CreoleDsl
	override fun String.unaryPlus() = column(this)
}

/**Creole表格行。*/
@CreoleDsl
open class CreoleTableRow @PublishedApi internal constructor() : CreoleDslElement, WithText<CreoleTableColumn> {
	val columns: MutableList<CreoleTableColumn> = mutableListOf()
	var columnSize: Int? = null
	
	override fun toString(): String {
		require(columns.isNotEmpty()) { "Table row column size must be positive." }
		
		//NOTE actual column size may not equal to columns.size
		return when {
			columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
			else -> columns.map { it.toString() }.fillEnd(columnSize!!, emptyColumnText)
		}.joinToStringOrEmpty(" | ", "| ", " |")
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
	var alignment: CreoleTableAlignment = CreoleTableAlignment.None //only for columns in table header
	
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

//REGION enumerations and constants

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

//REGION build extensions

@CreoleDsl
object CreoleInlineBuilder {
	@CreoleDsl
	inline fun text(text: String) = CreoleText(text)
	
	@CreoleDsl
	inline fun escaped(text: String) = CreoleEscapedText(text)
	
	@CreoleDsl
	inline fun icon(name: String) = CreoleIcon(name)
	
	@CreoleDsl
	inline fun unicode(number: Int) = CreoleUnicode(number)
	
	@CreoleDsl
	inline fun b(text: String) = CreoleBoldText(text)
	
	@CreoleDsl
	inline fun i(text: String) = CreoleItalicText(text)
	
	@CreoleDsl
	inline fun m(text: String) = CreoleMonospacedText(text)
	
	@CreoleDsl
	inline fun s(text: String) = CreoleStrokedText(text)
	
	@CreoleDsl
	inline fun u(text: String) = CreoleUnderlinedText(text)
	
	@CreoleDsl
	inline fun w(text: String) = CreoleWavedText(text)
}

@CreoleDsl
inline fun creole(builder: Creole.() -> Unit) =
	Creole().also { it.builder() }


@CreoleDsl
inline fun CreoleDslEntry.textBlock(text: String) =
	CreoleTextBlock(text).also { content += it }

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

@CreoleDsl
inline fun CreoleList.ol(text: String) =
	CreoleOrderedListNode(text).also { nodes += it }

@CreoleDsl
inline fun CreoleList.ol(text: String, builder: CreoleOrderedListNode.() -> Unit) =
	CreoleOrderedListNode(text).also { it.builder() }.also { nodes += it }

@CreoleDsl
inline fun CreoleList.ul(text: String) =
	CreoleUnorderedListNode(text).also { nodes += it }

@CreoleDsl
inline fun CreoleList.ul(text: String, builder: CreoleUnorderedListNode.() -> Unit) =
	CreoleUnorderedListNode(text).also { it.builder() }.also { nodes += it }

@CreoleDsl
inline fun CreoleListNode.ol(text: String) =
	CreoleOrderedListNode(text).also { nodes += it }

@CreoleDsl
inline fun CreoleListNode.ol(text: String, builder: CreoleOrderedListNode.() -> Unit) =
	CreoleOrderedListNode(text).also { it.builder() }.also { nodes += it }

@CreoleDsl
inline fun CreoleListNode.ul(text: String) =
	CreoleUnorderedListNode(text).also { nodes += it }

@CreoleDsl
inline fun CreoleListNode.ul(text: String, builder: CreoleUnorderedListNode.() -> Unit) =
	CreoleUnorderedListNode(text).also { it.builder() }.also { nodes += it }

@CreoleDsl
inline fun CreoleTree.node(text: String) =
	CreoleTreeNode(text).also { nodes += it }

@CreoleDsl
inline fun CreoleTree.node(text: String, builder: CreoleTreeNode.() -> Unit) =
	CreoleTreeNode(text).also { it.builder() }.also { nodes += it }

@CreoleDsl
inline fun CreoleTreeNode.node(text: String) =
	CreoleTreeNode(text).also { nodes += it }

@CreoleDsl
inline fun CreoleTreeNode.node(text: String, builder: CreoleTreeNode.() -> Unit) =
	CreoleTreeNode(text).also { it.builder() }.also { nodes += it }

@CreoleDsl
inline fun CreoleTable.header(builder: CreoleTableHeader.() -> Unit) =
	CreoleTableHeader().also { it.builder() }.also { header = it }

@CreoleDsl
inline fun CreoleTable.row(builder: CreoleTableRow.() -> Unit) =
	CreoleTableRow().also { it.builder() }.also { rows += it }

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

/**Only for table header.*/
@CreoleDsl
inline infix fun CreoleTableColumn.align(alignment: CreoleTableAlignment) =
	this.also { it.alignment = alignment }

//REGION dsl config

/**Creole的配置。*/
@ReferenceApi("[Creole](http://plantuml.com/zh/creole)")
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
