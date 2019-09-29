@file:Suppress("DuplicatedCode", "NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markup.CreoleConfig.emptyColumnText
import com.windea.breezeframework.dsl.markup.CreoleConfig.repeatableMarkerCount
import com.windea.breezeframework.dsl.markup.CreoleConfig.truncated

//REGION Dsl annotations

@DslMarker
internal annotation class CreoleDsl

//REGION Dsl & Dsl elements & Dsl config

@Reference("[Creole](http://plantuml.com/zh/creole)")
@CreoleDsl
class Creole @PublishedApi internal constructor() : Dsl, InlineContent<CreoleTextBlock> {
	val content: MutableList<CreoleTopElement> = mutableListOf()
	
	override fun toString(): String = content.joinToString("\n\n")
	
	
	@CreoleDsl
	inline fun textBlock(builder: CreoleTextBlock.() -> Unit) =
		CreoleTextBlock().also { it.builder() }.also { content += it }
	
	@CreoleDsl
	inline fun list(builder: CreoleList.() -> Unit) =
		CreoleList().also { it.builder() }.also { content += it }
	
	@CreoleDsl
	inline fun hr(type: CreoleHorizontalLineType = CreoleHorizontalLineType.Normal) =
		CreoleHorizontalLine(type).also { content += it }
	
	@CreoleDsl
	inline fun title(text: String, type: CreoleHorizontalLineType = CreoleHorizontalLineType.Normal) =
		CreoleTitle(text, type).also { content += it }
	
	@CreoleDsl
	inline fun h1(text: String) =
		CreoleHeading1(text).also { content += it }
	
	@CreoleDsl
	inline fun h2(text: String) =
		CreoleHeading2(text).also { content += it }
	
	@CreoleDsl
	inline fun h3(text: String) =
		CreoleHeading3(text).also { content += it }
	
	@CreoleDsl
	inline fun h4(text: String) =
		CreoleHeading4(text).also { content += it }
	
	@CreoleDsl
	inline fun tree(builder: CreoleTree.() -> Unit) =
		CreoleTree().also { it.builder() }.also { content += it }
	
	@CreoleDsl
	inline fun table(builder: CreoleTable.() -> Unit) =
		CreoleTable().also { it.builder() }.also { content += it }
	
	@CreoleDsl
	override fun String.unaryPlus(): CreoleTextBlock {
		TODO("not implemented")
	}
	
	@CreoleDsl
	override fun String.unaryMinus(): CreoleTextBlock {
		TODO("not implemented")
	}
}

@CreoleDsl
interface CreoleDslInlineEntry : InlineContent<CreoleText> {
	val inlineContent: MutableList<CreoleInlineElement>
	
	//return truncated string if empty
	fun toInlineContentString() = if(inlineContent.isEmpty()) truncated else inlineContent.joinToString("")
	
	override fun String.unaryPlus() = TODO("not implemented")
	
	override fun String.unaryMinus() = TODO("not implemented")
}

@CreoleDsl
inline fun CreoleDslInlineEntry.text(text: String) =
	CreoleText(text).also { inlineContent += it }

@CreoleDsl
inline fun CreoleDslInlineEntry.escaped(text: String) =
	CreoleEscapedText(text).also { inlineContent += it }

@CreoleDsl
inline fun CreoleDslInlineEntry.icon(name: String) =
	CreoleIcon(name).also { inlineContent += it }

@CreoleDsl
inline fun CreoleDslInlineEntry.unicode(number: Int) =
	CreoleUnicode(number).also { inlineContent += it }

@CreoleDsl
inline fun CreoleDslInlineEntry.b(text: String) =
	CreoleBoldText(text).also { inlineContent += it }

@CreoleDsl
inline fun CreoleDslInlineEntry.i(text: String) =
	CreoleItalicText(text).also { inlineContent += it }

@CreoleDsl
inline fun CreoleDslInlineEntry.m(text: String) =
	CreoleMonospacedText(text).also { inlineContent += it }

@CreoleDsl
inline fun CreoleDslInlineEntry.s(text: String) =
	CreoleStrokedText(text).also { inlineContent += it }

@CreoleDsl
inline fun CreoleDslInlineEntry.u(text: String) =
	CreoleUnderlinedText(text).also { inlineContent += it }

@CreoleDsl
inline fun CreoleDslInlineEntry.w(text: String) =
	CreoleWavedText(text).also { inlineContent += it }


@CreoleDsl
interface CreoleDslElement : DslElement

@CreoleDsl
interface CreoleTopElement : CreoleDslElement

@CreoleDsl
interface CreoleInlineElement : CreoleDslElement


/**Creole文本块。*/
@CreoleDsl
class CreoleTextBlock @PublishedApi internal constructor() : CreoleTopElement, CreoleDslInlineEntry {
	override val inlineContent: MutableList<CreoleInlineElement> = mutableListOf()
	
	override fun toString() = toInlineContentString()
}

/**Creole文本。*/
@CreoleDsl
class CreoleText @PublishedApi internal constructor(
	val text: String
) : CreoleInlineElement {
	override fun toString() = text
}

/**Creole转义文本。*/
@CreoleDsl
class CreoleEscapedText @PublishedApi internal constructor(
	val text: String
) : CreoleInlineElement {
	override fun toString() = "~$text"
}

/**Creole图标。可以使用open iconic图标。*/
@Reference("[OpenIconic](https://useiconic.com/open/)")
@CreoleDsl
class CreoleIcon @PublishedApi internal constructor(
	val name: String
) : CreoleInlineElement {
	override fun toString() = "<&$name>"
}

/**Creole Unicode字符。*/
@CreoleDsl
class CreoleUnicode @PublishedApi internal constructor(
	val number: Int
) : CreoleInlineElement {
	override fun toString() = "<U+$number>"
}


/**Creole富文本。*/
@CreoleDsl
sealed class CreoleRichText(
	val markers: String,
	val text: String
) : CreoleInlineElement {
	override fun toString() = "$markers$text$markers"
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


/**Creole水平分割线。*/
@CreoleDsl
class CreoleHorizontalLine @PublishedApi internal constructor(
	val type: CreoleHorizontalLineType = CreoleHorizontalLineType.Normal
) : CreoleTopElement {
	override fun toString() = type.text * repeatableMarkerCount
}

/**Creole水平分割标题。*/
@CreoleDsl
class CreoleTitle @PublishedApi internal constructor(
	val text: String,
	val type: CreoleHorizontalLineType = CreoleHorizontalLineType.Normal
) : CreoleTopElement {
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
) : CreoleTopElement {
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
class CreoleList @PublishedApi internal constructor() : CreoleTopElement {
	val nodes: MutableList<CreoleListNode> = mutableListOf()
	
	override fun toString() = nodes.joinToString("\n")
	
	
	@CreoleDsl
	inline fun ol(builder: CreoleOrderedListNode.() -> Unit) =
		CreoleOrderedListNode().also { it.builder() }.also { nodes += it }
	
	@CreoleDsl
	inline fun ul(builder: CreoleUnorderedListNode.() -> Unit) =
		CreoleUnorderedListNode().also { it.builder() }.also { nodes += it }
}

/**Creole列表节点。*/
@CreoleDsl
sealed class CreoleListNode(
	protected val prefixMarker: String
) : CreoleDslElement, CreoleDslInlineEntry {
	override val inlineContent: MutableList<CreoleInlineElement> = mutableListOf()
	
	val nodes: MutableList<CreoleListNode> = mutableListOf()
	
	override fun toString(): String {
		val contentSnippet = toInlineContentString()
		val nodesSnippet = if(nodes.isEmpty()) "" else nodes.joinToString("\n", "\n") { "${it.prefixMarker}$it" }
		return "$prefixMarker $contentSnippet$nodesSnippet"
	}
	
	
	@CreoleDsl
	inline fun ol(builder: CreoleOrderedListNode.() -> Unit) =
		CreoleOrderedListNode().also { it.builder() }.also { nodes += it }
	
	@CreoleDsl
	inline fun ul(builder: CreoleUnorderedListNode.() -> Unit) =
		CreoleUnorderedListNode().also { it.builder() }.also { nodes += it }
}

/**Creole有序列表节点。*/
@CreoleDsl
class CreoleOrderedListNode @PublishedApi internal constructor() : CreoleListNode("#")

/**Creole无序列表节点。*/
@CreoleDsl
class CreoleUnorderedListNode @PublishedApi internal constructor() : CreoleListNode("*")


/**Creole树。*/
@CreoleDsl
class CreoleTree @PublishedApi internal constructor() : CreoleTopElement, CreoleDslInlineEntry {
	override val inlineContent: MutableList<CreoleInlineElement> = mutableListOf()
	val nodes: MutableList<CreoleTreeNode> = mutableListOf()
	
	override fun toString(): String {
		val contentSnippet = toInlineContentString()
		val nodesSnippet = if(nodes.isEmpty()) "" else nodes.joinToString("\n", "\n")
		return "$contentSnippet$nodesSnippet"
	}
	
	
	@CreoleDsl
	inline fun node(builder: CreoleTreeNode.() -> Unit) =
		CreoleTreeNode().also { it.builder() }.also { nodes += it }
}

/**Creole树节点。*/
@CreoleDsl
class CreoleTreeNode @PublishedApi internal constructor() : CreoleDslElement, CreoleDslInlineEntry {
	override val inlineContent: MutableList<CreoleInlineElement> = mutableListOf()
	val nodes: MutableList<CreoleTreeNode> = mutableListOf()
	
	override fun toString(): String {
		//include prefix "|_", add it to first line, add spaces to other lines
		val contentSnippet = "|_ ${toInlineContentString()}"
		val nodesSnippet = if(nodes.isEmpty()) "" else nodes.joinToString("\n", "\n") { it.toString().prependIndent("   ") }
		return "$contentSnippet$nodesSnippet"
	}
}


//TODO pretty format
/**Creole表格。*/
@CreoleDsl
class CreoleTable @PublishedApi internal constructor() : CreoleTopElement {
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
class CreoleTableHeader @PublishedApi internal constructor() : CreoleDslElement {
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
	inline fun column(builder: CreoleTableColumn.() -> Unit) =
		CreoleTableColumn().also { it.builder() }.also { columns += it }
	
	@CreoleDsl
	inline infix fun CreoleTableColumn.align(alignment: CreoleTableAlignment) =
		this.also { it.alignment = alignment }
}

/**Creole表格行。*/
@CreoleDsl
open class CreoleTableRow @PublishedApi internal constructor() : CreoleDslElement {
	val columns: MutableList<CreoleTableColumn> = mutableListOf()
	var columnSize: Int? = null
	
	override fun toString(): String {
		require(columns.isNotEmpty()) { "Table row column size must be greater than 0." }
		
		//NOTE actual column size may not equal to columns.size
		return when {
			columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
			columnSize!! < columns.size -> columns.subList(0, columnSize!!).map { it.toString() }
			else -> columns.map { it.toString() }.toMutableList().fillToSize(emptyColumnText, columnSize!!)
		}.joinToString("|", "|", "|")
	}
	
	
	@CreoleDsl
	inline fun column(builder: CreoleTableColumn.() -> Unit) =
		CreoleTableColumn().also { it.builder() }.also { columns += it }
}

/**Creole表格列。*/
@CreoleDsl
open class CreoleTableColumn @PublishedApi internal constructor() : CreoleDslElement, CreoleDslInlineEntry {
	override val inlineContent: MutableList<CreoleInlineElement> = mutableListOf()
	
	var color: String? = null
	var alignment: CreoleTableAlignment = CreoleTableAlignment.None //only for columns in table header
	
	override fun toInlineContentString() = if(inlineContent.isEmpty()) emptyColumnText else inlineContent.joinToString("")
	
	override fun toString(): String {
		val contentSnippet = toInlineContentString()
		val colorSnippet = color?.let { "<$color> " } ?: ""
		return " $colorSnippet$contentSnippet "
	}
	
	fun toStringInHeader(): String {
		val contentSnippet = toInlineContentString()
		val colorSnippet = color?.let { "<$color> " } ?: ""
		val (l, r) = alignment.textPair
		return "$l $colorSnippet$contentSnippet $r"
	}
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
	internal val emptyColumnText get() = " " * emptyColumnSize
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
