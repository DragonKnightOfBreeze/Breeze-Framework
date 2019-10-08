@file:Suppress("NOTHING_TO_INLINE", "DuplicatedCode", "unused")

package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markup.MarkdownConfig.emptyColumnSize
import com.windea.breezeframework.dsl.markup.MarkdownConfig.emptyColumnText
import com.windea.breezeframework.dsl.markup.MarkdownConfig.indent
import com.windea.breezeframework.dsl.markup.MarkdownConfig.initialMarker
import com.windea.breezeframework.dsl.markup.MarkdownConfig.quote
import com.windea.breezeframework.dsl.markup.MarkdownConfig.repeatableMarkerCount
import com.windea.breezeframework.dsl.markup.MarkdownConfig.truncated
import org.intellij.lang.annotations.*

//TODO list prefix marker can also be "+"

//REGION Dsl annotations

@DslMarker
private annotation class MarkdownDsl

@DslMarker
private annotation class InlineMarkdownDsl

@MustBeDocumented
private annotation class MarkdownDslExtendedFeature

//REGION Dsl & Dsl config & Dsl elements

/**构建Markdown。*/
@MarkdownDsl
inline fun markdown(builder: Markdown.() -> Unit) = Markdown().also { it.builder() }

/**Markdown。*/
@MarkdownDsl
class Markdown @PublishedApi internal constructor() : DslBuilder, InlineMarkdownDslEntry, MarkdownDslEntry {
	var frontMatter: MarkdownFrontMatter? = null
	var toc: MarkdownToc? = null
	override val content: MutableList<MarkdownDslTopElement> = mutableListOf()
	val references: MutableSet<MarkdownReference> = mutableSetOf()
	
	override fun toString(): String {
		return arrayOf(
			frontMatter?.toString() ?: "",
			toc?.toString() ?: "",
			toContentString(),
			references.joinToString("\n")
		).filterNotEmpty().joinToString("\n\n")
	}
	
	
	@MarkdownDsl
	@MarkdownDslExtendedFeature
	inline fun frontMatter(lazyText: () -> String) =
		MarkdownFrontMatter(lazyText()).also { frontMatter = it }
	
	@MarkdownDsl
	@MarkdownDslExtendedFeature
	inline fun toc() =
		MarkdownToc().also { toc = it }
	
	@MarkdownDsl
	@MarkdownDslExtendedFeature
	inline fun abbr(reference: String, text: String) =
		MarkdownAbbreviation(reference, text).also { references += it }
	
	@MarkdownDsl
	@MarkdownDslExtendedFeature
	inline fun footNoteRef(reference: String, text: String) =
		MarkdownFootNoteReference(reference, text).also { references += it }
	
	@MarkdownDsl
	inline fun linkRef(reference: String, url: String, title: String? = null) =
		MarkdownLinkReference(reference, url, title).also { references += it }
}


/**Markdown配置。*/
@MarkdownDsl
object MarkdownConfig : DslConfig {
	/**缩进长度。*/
	var indentSize = 4
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**可重复标记的个数。*/
	var repeatableMarkerCount = 3
		set(value) = run { field = value.coerceIn(3, 6) }
	var truncated = "..."
	var preferDoubleQuote: Boolean = true
	var preferAsteriskMaker: Boolean = true
	var emptyColumnSize: Int = 4
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(preferDoubleQuote) "\"" else "'"
	internal val initialMarker get() = if(preferAsteriskMaker) "*" else "-"
	@PublishedApi internal val emptyColumnText get() = " " * emptyColumnSize
}


interface InlineMarkdownDslEntry

@InlineMarkdownDsl
inline fun InlineMarkdownDslEntry.text(text: String) = MarkdownText(text)

@InlineMarkdownDsl
inline fun InlineMarkdownDslEntry.icon(text: String) = MarkdownIcon(text)

@InlineMarkdownDsl
inline fun InlineMarkdownDslEntry.footNote(reference: String) = MarkdownFootNote(reference)

@InlineMarkdownDsl
inline fun InlineMarkdownDslEntry.b(text: String) = MarkdownBoldText(text)

@InlineMarkdownDsl
inline fun InlineMarkdownDslEntry.i(text: String) = MarkdownItalicText(text)

@InlineMarkdownDsl
inline fun InlineMarkdownDslEntry.s(text: String) = MarkdownStrokedText(text)

@InlineMarkdownDsl
@MarkdownDslExtendedFeature
inline fun InlineMarkdownDslEntry.u(text: String) = MarkdownUnderlinedText(text)

@InlineMarkdownDsl
@MarkdownDslExtendedFeature
inline fun InlineMarkdownDslEntry.h(text: String) = MarkdownHighlightText(text)

@InlineMarkdownDsl
@MarkdownDslExtendedFeature
inline fun InlineMarkdownDslEntry.sup(text: String) = MarkdownSuperscriptText(text)

@InlineMarkdownDsl
@MarkdownDslExtendedFeature
inline fun InlineMarkdownDslEntry.sub(text: String) = MarkdownSubscriptText(text)

@InlineMarkdownDsl
@MarkdownDslExtendedFeature
inline fun InlineMarkdownDslEntry.cmAppend(text: String) = CriticMarkupAppendedText(text)

@InlineMarkdownDsl
@MarkdownDslExtendedFeature
inline fun InlineMarkdownDslEntry.cmDelete(text: String) = CriticMarkupDeletedText(text)

@InlineMarkdownDsl
@MarkdownDslExtendedFeature
inline fun InlineMarkdownDslEntry.cmReplace(text: String, replacedText: String) = CriticMarkupReplacedText(text, replacedText)

@InlineMarkdownDsl
@MarkdownDslExtendedFeature
inline fun InlineMarkdownDslEntry.cmComment(text: String) = CriticMarkupCommentText(text)

@InlineMarkdownDsl
@MarkdownDslExtendedFeature
inline fun InlineMarkdownDslEntry.cmHighlight(text: String) = CriticMarkupHighlightText(text)

@InlineMarkdownDsl
@MarkdownDslExtendedFeature
inline fun InlineMarkdownDslEntry.autoLink(url: String) = MarkdownAutoLink(url)

@InlineMarkdownDsl
inline fun InlineMarkdownDslEntry.link(name: String, url: String, title: String? = null) = MarkdownInlineLink(name, url, title)

@InlineMarkdownDsl
inline fun InlineMarkdownDslEntry.image(name: String, url: String, title: String? = null) = MarkdownInlineImageLink(name, url, title)

@InlineMarkdownDsl
inline fun InlineMarkdownDslEntry.refLink(reference: String, name: String? = null) = MarkdownReferenceLink(reference, name)

@InlineMarkdownDsl
inline fun InlineMarkdownDslEntry.refImage(reference: String, name: String? = null) = MarkdownReferenceImageLink(reference, name)

@InlineMarkdownDsl
@MarkdownDslExtendedFeature
inline fun InlineMarkdownDslEntry.wikiLink(name: String, url: String) = MarkdownWikiLink(name, url)

@InlineMarkdownDsl
inline fun InlineMarkdownDslEntry.code(text: String) = MarkdownInlineCode(text)

@InlineMarkdownDsl
inline fun InlineMarkdownDslEntry.math(text: String) = MarkdownInlineMath(text)


interface MarkdownDslEntry : TextContent<MarkdownTextBlock>, InlineContent<MarkdownTextBlock> {
	val content: MutableList<MarkdownDslTopElement>
	
	fun toContentString() = content.joinToString("\n\n")
	
	@MarkdownDsl
	override fun String.unaryPlus() = textBlock { this@unaryPlus }
	
	@MarkdownDsl
	override fun String.not() = textBlock { this@MarkdownDslEntry.content.clear();this@not }
}

@MarkdownDsl
inline fun MarkdownDslEntry.textBlock(lazyText: () -> String) =
	MarkdownTextBlock(lazyText()).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.mainHeading(text: String) =
	MarkdownMainHeading(text).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.subHeading(text: String) =
	MarkdownSubHeading(text).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.h1(text: String) =
	MarkdownHeading1(text).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.h2(text: String) =
	MarkdownHeading2(text).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.h3(text: String) =
	MarkdownHeading3(text).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.h4(text: String) =
	MarkdownHeading4(text).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.h5(text: String) =
	MarkdownHeading5(text).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.h6(text: String) =
	MarkdownHeading6(text).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.hr() =
	MarkdownHorizontalLine().also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.list(builder: MarkdownList.() -> Unit) =
	MarkdownList().also { it.builder() }.also { content += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslEntry.def(title: String, builder: MarkdownDefinition.() -> Unit) =
	MarkdownDefinition(title).also { it.builder() }.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.table(builder: MarkdownTable.() -> Unit) =
	MarkdownTable().also { it.builder() }.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.blockQueue(builder: MarkdownBlockQuote.() -> Unit) =
	MarkdownBlockQuote().also { it.builder() }.also { content += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslEntry.indentedBlock(builder: MarkdownIndentedBlock.() -> Unit) =
	MarkdownIndentedBlock().also { it.builder() }.also { content += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslEntry.sideBlock(builder: MarkdownSideBlock.() -> Unit) =
	MarkdownSideBlock().also { it.builder() }.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.codeFence(language: String, lazyText: () -> String) =
	MarkdownCodeFence(language, lazyText()).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.multilineMath(lazyText: () -> String) =
	MarkdownMultilineMath(lazyText()).also { content += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslEntry.alertBox(qualifier: MarkdownAlertBoxQualifier, title: String = "", type: MarkdownAlertBoxType = MarkdownAlertBoxType.Normal) =
	MarkdownAlertBox(qualifier, title, type).also { content += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslEntry.import(url: String) =
	MarkdownImport(url).also { content += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslEntry.macros(name: String) =
	MarkdownMacros(name).also { content += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslEntry.macrosSnippet(name: String, builder: MarkdownMacrosSnippet.() -> Unit) =
	MarkdownMacrosSnippet(name).also { it.builder() }.also { content += it }


/**内联的Markdown Dsl的元素。*/
@InlineMarkdownDsl
interface InlineMarkdownElement : InlineDslElement

/**Markdown Dsl的元素。*/
@MarkdownDsl
interface MarkdownDslElement : DslElement, InlineMarkdownDslEntry

/**Markdown Dsl的顶级元素。*/
@MarkdownDsl
interface MarkdownDslTopElement : MarkdownDslElement


/**Markdown文本。*/
@InlineMarkdownDsl
class MarkdownText @PublishedApi internal constructor(
	val text: String
) : InlineMarkdownElement {
	override fun toString() = text
}

/**Markdown图标。可以使用font awesome和emoji图标。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownIcon @PublishedApi internal constructor(
	val name: String
) : InlineMarkdownElement {
	override fun toString() = ":$name:"
}

/**Markdown脚注。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownFootNote @PublishedApi internal constructor(
	val reference: String
) : InlineMarkdownElement {
	override fun toString() = "[^$reference]"
}


/**Markdown富文本。*/
@InlineMarkdownDsl
sealed class MarkdownRichText(
	protected val markers: String,
	val text: String
) : InlineMarkdownElement {
	override fun toString() = "$markers$text$markers"
}

/**Markdown加粗文本。*/
@InlineMarkdownDsl
class MarkdownBoldText @PublishedApi internal constructor(text: String) : MarkdownRichText("**", text)

/**Markdown斜体文本。*/
@InlineMarkdownDsl
class MarkdownItalicText @PublishedApi internal constructor(text: String) : MarkdownRichText("*", text)

/**Markdown删除文本。*/
@InlineMarkdownDsl
class MarkdownStrokedText @PublishedApi internal constructor(text: String) : MarkdownRichText("~~", text)

/**Markdown下划线文本。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownUnderlinedText @PublishedApi internal constructor(text: String) : MarkdownRichText("++", text)

/**Markdown高亮文本。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownHighlightText @PublishedApi internal constructor(text: String) : MarkdownRichText("==", text)

/**Markdown上标文本。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownSuperscriptText @PublishedApi internal constructor(text: String) : MarkdownRichText("^", text)

/**Markdown下标文本。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownSubscriptText @PublishedApi internal constructor(text: String) : MarkdownRichText(text, "~")


//TODO remove out as "CriticMarkupDsl"
/**Critic Markup文本。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
sealed class CriticMarkupText(
	protected val prefixMarkers: String,
	val text: String,
	protected val suffixMarkers: String
) : InlineMarkdownElement {
	override fun toString() = "$prefixMarkers $text $suffixMarkers"
}

/**Critic Markup添加文本。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
class CriticMarkupAppendedText @PublishedApi internal constructor(text: String) : CriticMarkupText("{++", text, "++}")

/**Critic Markup添加文本。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
class CriticMarkupDeletedText @PublishedApi internal constructor(text: String) : CriticMarkupText("{--", text, "--}")

/**Critic Markup替换文本。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
class CriticMarkupReplacedText @PublishedApi internal constructor(
	text: String,
	val replacedText: String
) : CriticMarkupText("{--", text, "--}") {
	private val infixMarkers: String = "~>"
	
	override fun toString() = "$prefixMarkers $text $infixMarkers $replacedText $suffixMarkers"
}

/**Critic Markup注释文本。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
class CriticMarkupCommentText @PublishedApi internal constructor(text: String) : CriticMarkupText("{>>", text, "<<}")

/**Critic Markup高亮文本。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
class CriticMarkupHighlightText @PublishedApi internal constructor(text: String) : CriticMarkupText("{==", text, "==}")


/**Markdown链接。*/
@InlineMarkdownDsl
sealed class MarkdownLink(
	val url: String
) : InlineMarkdownElement

/**Markdown自动链接。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownAutoLink @PublishedApi internal constructor(url: String) : MarkdownLink(url) {
	override fun toString() = "<$url>"
}

/**Markdown行内链接。*/
@InlineMarkdownDsl
open class MarkdownInlineLink @PublishedApi internal constructor(
	val name: String,
	url: String,
	val title: String? = null
) : MarkdownLink(url) {
	override fun toString(): String {
		val titleSnippet = title?.let { " ${it.wrapQuote(quote)}" } ?: ""
		return "[$name]($url$titleSnippet)"
	}
}

/**Markdown行内图片链接。*/
@InlineMarkdownDsl
class MarkdownInlineImageLink @PublishedApi internal constructor(
	name: String, url: String, title: String? = null
) : MarkdownInlineLink(name, url, title) {
	override fun toString() = "!${super.toString()}"
}

/**Markdown引用连接。*/
@InlineMarkdownDsl
open class MarkdownReferenceLink @PublishedApi internal constructor(
	val reference: String,
	val name: String? = null
) : MarkdownLink(truncated) {
	override fun toString(): String {
		val nameSnippet = name?.let { "[$name]" } ?: ""
		return "$nameSnippet[$reference]"
	}
}

/**Markdown引用图片连接。*/
@InlineMarkdownDsl
class MarkdownReferenceImageLink @PublishedApi internal constructor(
	reference: String, name: String? = null
) : MarkdownReferenceLink(reference, name) {
	override fun toString() = "!${super.toString()}"
}

/**Markdown维基链接。采用Github风格，标题在前，地址在后。*/
@InlineMarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownWikiLink @PublishedApi internal constructor(
	val name: String,
	url: String
) : MarkdownLink(url) {
	override fun toString() = "[[$name|$url]]"
}


/**Markdown文本块。*/
@MarkdownDsl
class MarkdownTextBlock @PublishedApi internal constructor(
	val text: String
) : MarkdownDslTopElement {
	override fun toString() = text
}


/**Markdown标题。*/
@MarkdownDsl
sealed class MarkdownHeading(
	val headingLevel: Int,
	val text: String
) : MarkdownDslTopElement

/**Setext风格的Markdown标题。*/
@MarkdownDsl
sealed class MarkdownSetextHeading(headingLevel: Int, text: String) : MarkdownHeading(headingLevel, text) {
	override fun toString(): String {
		val suffixMarkers = (if(headingLevel == 1) "=" else "-") * repeatableMarkerCount
		return "$text\n$suffixMarkers"
	}
}

/**Markdown主标题。*/
@MarkdownDsl
class MarkdownMainHeading @PublishedApi internal constructor(text: String) : MarkdownSetextHeading(1, text)

/**Markdown副标题。*/
@MarkdownDsl
class MarkdownSubHeading @PublishedApi internal constructor(text: String) : MarkdownSetextHeading(2, text)

/**Atx风格的Markdown标题。*/
@MarkdownDsl
sealed class MarkdownAtxHeading(headingLevel: Int, text: String) : MarkdownHeading(headingLevel, text) {
	override fun toString(): String {
		val prefixMarkers = "#" * headingLevel
		return "$prefixMarkers $text"
	}
}

/**Markdown一级标题。*/
@MarkdownDsl
class MarkdownHeading1 @PublishedApi internal constructor(text: String) : MarkdownAtxHeading(1, text)

/**Markdown二级标题。*/
@MarkdownDsl
class MarkdownHeading2 @PublishedApi internal constructor(text: String) : MarkdownAtxHeading(2, text)

/**Markdown三级标题。*/
@MarkdownDsl
class MarkdownHeading3 @PublishedApi internal constructor(text: String) : MarkdownAtxHeading(3, text)

/**Markdown四级标题。*/
@MarkdownDsl
class MarkdownHeading4 @PublishedApi internal constructor(text: String) : MarkdownAtxHeading(4, text)

/**Markdown五级标题。*/
@MarkdownDsl
class MarkdownHeading5 @PublishedApi internal constructor(text: String) : MarkdownAtxHeading(5, text)

/**Markdown六级标题。*/
@MarkdownDsl
class MarkdownHeading6 @PublishedApi internal constructor(text: String) : MarkdownAtxHeading(6, text)

/**Markdown水平分割线。*/
@MarkdownDsl
class MarkdownHorizontalLine @PublishedApi internal constructor() : MarkdownDslTopElement {
	override fun toString() = initialMarker * repeatableMarkerCount
}


/**Markdown列表。*/
@MarkdownDsl
class MarkdownList @PublishedApi internal constructor(
	val nodes: MutableList<MarkdownListNode> = mutableListOf()
) : MarkdownDslTopElement {
	override fun toString() = nodes.joinToString("\n")
	
	
	@MarkdownDsl
	inline fun ol(order: String, text: String) =
		MarkdownOrderedListNode(order, text).also { nodes += it }
	
	@MarkdownDsl
	inline fun ol(order: String, text: String, builder: MarkdownOrderedListNode.() -> Unit) =
		MarkdownOrderedListNode(order, text).also { it.builder() }.also { nodes += it }
	
	@MarkdownDsl
	inline fun ul(text: String) =
		MarkdownUnorderedListNode(text).also { nodes += it }
	
	@MarkdownDsl
	inline fun ul(text: String, builder: MarkdownUnorderedListNode.() -> Unit) =
		MarkdownUnorderedListNode(text).also { it.builder() }.also { nodes += it }
	
	@MarkdownDsl
	inline fun task(status: Boolean, text: String) =
		MarkdownTaskListNode(status, text).also { nodes += it }
	
	@MarkdownDsl
	inline fun task(status: Boolean, text: String, builder: MarkdownTaskListNode.() -> Unit) =
		MarkdownTaskListNode(status, text).also { it.builder() }.also { nodes += it }
}

/**Markdown列表节点。*/
@MarkdownDsl
sealed class MarkdownListNode(
	protected val prefixMarker: String,
	val text: String
) : MarkdownDslElement {
	val nodes: MutableList<MarkdownListNode> = mutableListOf()
	
	override fun toString(): String {
		val nodesSnippet = if(nodes.isEmpty()) "" else nodes.joinToString("\n", "\n") { it.toString().prependIndent(indent) }
		return "$prefixMarker $text$nodesSnippet"
	}
	
	
	@MarkdownDsl
	inline fun ol(order: String, text: String) =
		MarkdownOrderedListNode(order, text).also { nodes += it }
	
	@MarkdownDsl
	inline fun ol(order: String, text: String, builder: MarkdownOrderedListNode.() -> Unit) =
		MarkdownOrderedListNode(order, text).also { it.builder() }.also { nodes += it }
	
	@MarkdownDsl
	inline fun ul(text: String) =
		MarkdownUnorderedListNode(text).also { nodes += it }
	
	@MarkdownDsl
	inline fun ul(text: String, builder: MarkdownUnorderedListNode.() -> Unit) =
		MarkdownUnorderedListNode(text).also { it.builder() }.also { nodes += it }
	
	@MarkdownDsl
	inline fun task(status: Boolean, text: String) =
		MarkdownTaskListNode(status, text).also { nodes += it }
	
	@MarkdownDsl
	inline fun task(status: Boolean, text: String, builder: MarkdownTaskListNode.() -> Unit) =
		MarkdownTaskListNode(status, text).also { it.builder() }.also { nodes += it }
}

/**Markdown有序列表节点。*/
@MarkdownDsl
class MarkdownOrderedListNode @PublishedApi internal constructor(
	val order: String, text: String
) : MarkdownListNode("$order.", text)

/**Markdown无序列表节点。*/
@MarkdownDsl
class MarkdownUnorderedListNode @PublishedApi internal constructor(text: String) : MarkdownListNode(initialMarker, text)

/**Markdown任务列表节点。*/
@MarkdownDsl
class MarkdownTaskListNode @PublishedApi internal constructor(
	val completeStatus: Boolean, text: String
) : MarkdownListNode("$initialMarker [${if(completeStatus) "X" else " "}]", text)


/**Markdown定义列表。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownDefinition @PublishedApi internal constructor(
	val title: String
) : MarkdownDslTopElement {
	val nodes: MutableList<MarkdownDefinitionNode> = mutableListOf()
	
	override fun toString(): String {
		require(nodes.isNotEmpty()) { "Definition node size must be greater than 0." }
		
		val nodesSnippet = nodes.joinToString("\n")
		return "$title\n$nodesSnippet"
	}
	
	
	@MarkdownDsl
	@MarkdownDslExtendedFeature
	inline fun node(title: String, builder: MarkdownDefinitionNode.() -> Unit) =
		MarkdownDefinitionNode(title).also { it.builder() }.also { nodes += it }
}

/**Markdown定义列表节点。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownDefinitionNode @PublishedApi internal constructor(
	val text: String
) : MarkdownDslElement {
	override fun toString(): String {
		return ":" + text.prependIndent(indent).drop(1)
	}
}


//TODO pretty format
/**Markdown表格。*/
@MarkdownDsl
class MarkdownTable @PublishedApi internal constructor() : MarkdownDslTopElement {
	var header: MarkdownTableHeader = MarkdownTableHeader()
	val rows: MutableList<MarkdownTableRow> = mutableListOf()
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
	
	
	@MarkdownDsl
	inline fun header(builder: MarkdownTableHeader.() -> Unit) =
		MarkdownTableHeader().also { it.builder() }.also { header = it }
	
	@MarkdownDsl
	inline fun row(builder: MarkdownTableRow.() -> Unit) =
		MarkdownTableRow().also { it.builder() }.also { rows += it }
	
	@MarkdownDsl
	inline infix fun columnSize(size: Int) =
		this.also { this.columnSize = size }
}

/**Markdown表格头部。*/
@MarkdownDsl
class MarkdownTableHeader @PublishedApi internal constructor() : MarkdownDslElement, TextContent<MarkdownTableColumn> {
	val columns: MutableList<MarkdownTableColumn> = mutableListOf()
	var columnSize: Int? = null
	
	override fun toString(): String {
		require(columns.isNotEmpty()) { "Table row column size must be greater than 0." }
		
		//NOTE actual column size may not equal to columns.size
		val columnsSnippet = when {
			columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
			columnSize!! < columns.size -> columns.subList(0, columnSize!!).map { it.toString() }
			else -> columns.map { it.toString() }.toMutableList().fillToSize(emptyColumnText, columnSize!!)
		}.joinToString(" | ", "| ", " |")
		val delimitersSnippet = when {
			columnSize == null || columnSize == columns.size -> columns.map { it.toDelimiterString() }
			columnSize!! < columns.size -> columns.subList(0, columnSize!!).map { it.toDelimiterString() }
			else -> columns.map { it.toDelimiterString() }.toMutableList().fillToSize("-" * emptyColumnSize, columnSize!!)
		}.joinToString(" | ", "| ", " |")
		return "$columnsSnippet\n$delimitersSnippet"
	}
	
	
	@MarkdownDsl
	inline fun column(text: String = emptyColumnText) =
		MarkdownTableColumn(text).also { columns += it }
	
	@MarkdownDsl
	inline infix fun MarkdownTableColumn.align(alignment: MarkdownTableAlignment) =
		this.also { it.alignment = alignment }
	
	@MarkdownDsl
	override fun String.unaryPlus() = column(this)
}

/**Markdown表格行。*/
@MarkdownDsl
open class MarkdownTableRow @PublishedApi internal constructor() : MarkdownDslElement, TextContent<MarkdownTableColumn> {
	val columns: MutableList<MarkdownTableColumn> = mutableListOf()
	var columnSize: Int? = null
	
	override fun toString(): String {
		require(columns.isNotEmpty()) { "Table row column size must be greater than 0." }
		
		//NOTE actual column size may not equal to columns.size
		return when {
			columnSize == null || columnSize == columns.size -> columns
			columnSize!! < columns.size -> columns.subList(0, columnSize!!)
			else -> columns.map { it.toString() }.toMutableList().fillToSize(emptyColumnText, columnSize!!)
		}.joinToString(" | ", "| ", " |")
	}
	
	
	@MarkdownDsl
	inline fun column(text: String = emptyColumnText) =
		MarkdownTableColumn(text).also { columns += it }
	
	@MarkdownDsl
	@MarkdownDslExtendedFeature
	inline fun rowSpan() = column(">")
	
	@MarkdownDsl
	@MarkdownDslExtendedFeature
	inline fun colSpan() = column("^")
	
	@MarkdownDsl
	override fun String.unaryPlus() = column(this)
}

/**Markdown表格列。*/
@MarkdownDsl
class MarkdownTableColumn @PublishedApi internal constructor(
	val text: String = emptyColumnText
) : MarkdownDslElement {
	var alignment: MarkdownTableAlignment = MarkdownTableAlignment.None //only for columns in table header
	
	override fun toString() = text
	
	fun toDelimiterString(): String {
		val (l, r) = alignment.textPair
		return "$l${" " * (emptyColumnSize - 2)}$r"
	}
}


/**Markdown引文。*/
@MarkdownDsl
sealed class MarkdownQuote(
	val prefixMarker: String
) : MarkdownDslTopElement, MarkdownDslEntry {
	override val content: MutableList<MarkdownDslTopElement> = mutableListOf()
	
	override fun toString(): String {
		return toContentString().prependIndent("$prefixMarker ")
	}
}

/**Markdown引文块。*/
@MarkdownDsl
class MarkdownBlockQuote @PublishedApi internal constructor() : MarkdownQuote(">")

/**Markdown缩进块。*/
@MarkdownDsl
class MarkdownIndentedBlock @PublishedApi internal constructor() : MarkdownQuote(" ")

/**Markdown侧边块。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownSideBlock @PublishedApi internal constructor() : MarkdownQuote("|")


/**Markdown代码。*/
@MarkdownDsl
interface MarkdownCode : MarkdownDslElement {
	val text: String
}

/**Markdown行内代码。*/
@MarkdownDsl
class MarkdownInlineCode @PublishedApi internal constructor(text: String) : MarkdownRichText("`", text)

/**Markdown代码块。*/
@MarkdownDsl
class MarkdownCodeFence @PublishedApi internal constructor(
	val language: String,
	override val text: String
	//TODO extended classes and properties
) : MarkdownDslTopElement, MarkdownCode {
	override fun toString() = "```$language\n$text\n```"
}


/**Markdown数学表达式。*/
@MarkdownDsl
interface MarkdownMath : MarkdownDslElement {
	val text: String
}

/**Markdown行内数学表达式。*/
@MarkdownDsl
class MarkdownInlineMath @PublishedApi internal constructor(
	override val text: String
) : InlineMarkdownElement, MarkdownMath {
	override fun toString() = "$$text$"
}

/**Markdown多行数学表达式。*/
@MarkdownDsl
class MarkdownMultilineMath @PublishedApi internal constructor(
	override val text: String
) : MarkdownDslTopElement, MarkdownMath {
	override fun toString() = "$$\n$text\n$$"
}

/**Markdown警告框。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownAlertBox @PublishedApi internal constructor(
	val qualifier: MarkdownAlertBoxQualifier,
	val title: String = "",
	val type: MarkdownAlertBoxType = MarkdownAlertBoxType.Normal
) : MarkdownDslTopElement, MarkdownDslEntry {
	override val content: MutableList<MarkdownDslTopElement> = mutableListOf()
	
	override fun toString(): String {
		require(content.isNotEmpty()) { "Alert box content must not be empty." }
		
		val titleSnippet = title.wrapQuote(quote)
		val contentSnippet = toContentString().prependIndent(indent)
		return "${type.text} $qualifier $titleSnippet\n$contentSnippet"
	}
}

@MarkdownDsl
@MarkdownDslExtendedFeature
enum class MarkdownAlertBoxType(
	val text: String
) {
	Normal("!!!"), Collapsed("???"), Opened("!!!+")
}

/**Front Matter。只能位于Markdown文档顶部。用于配置当前的Markdown文档。使用Yaml格式。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownFrontMatter @PublishedApi internal constructor(
	@Language("Yaml")
	val text: String
) : MarkdownDslElement {
	override fun toString() = "---\n$text\n---"
}

/**Markdown目录。只能位于文档顶部。用于生成当前文档的目录。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownToc @PublishedApi internal constructor() : MarkdownDslElement, CanGenerateContent {
	override var generateContent: Boolean = false //TODO
	
	override fun toString() = "[TOC]"
}

/**Markdown导入。用于导入相对路径的图片或文本。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownImport @PublishedApi internal constructor(
	val url: String
	//TODO extended classes and properties
) : MarkdownDslTopElement, CanGenerateContent {
	override var generateContent: Boolean = false //TODO
	
	override fun toString(): String {
		val urlSnippet = url.wrapQuote(quote)
		return "@import $urlSnippet"
	}
}

/**Markdown宏。用于重复利用任意Markdown片段。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownMacros @PublishedApi internal constructor(
	val name: String
) : MarkdownDslTopElement, CanGenerateContent {
	override var generateContent: Boolean = false //TODO
	
	override fun toString() = "<<< $name >>>"
}

/**Markdown宏片段。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownMacrosSnippet @PublishedApi internal constructor(
	val name: String
) : MarkdownDslTopElement, MarkdownDslEntry {
	override val content: MutableList<MarkdownDslTopElement> = mutableListOf()
	
	override fun toString(): String {
		val contentSnippet = toContentString()
		return ">>> $name\n$contentSnippet\n<<<"
	}
}


/**Markdown引用。*/
@MarkdownDsl
sealed class MarkdownReference(
	val reference: String
) : MarkdownDslElement {
	override fun equals(other: Any?) = other === this || (other is MarkdownReference && other.reference == reference)
	
	override fun hashCode() = reference.hashCode()
}

/**Markdown脚注的引用。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownFootNoteReference @PublishedApi internal constructor(
	reference: String,
	val text: String
) : MarkdownReference(reference) {
	override fun toString() = "[^$reference]: $text"
}

/**Markdown缩写。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownAbbreviation @PublishedApi internal constructor(
	reference: String,
	val text: String
) : MarkdownReference(reference) {
	override fun toString() = "*[$reference]: $text"
}

/**Markdown链接的引用。*/
@MarkdownDsl
class MarkdownLinkReference @PublishedApi internal constructor(
	reference: String,
	val url: String,
	val title: String? = null
) : MarkdownReference(reference) {
	override fun toString(): String {
		val titleSnippet = title?.let { " ${it.wrapQuote(quote)}" } ?: ""
		return "[$reference]: $url$titleSnippet"
	}
}

//REGION Enumerations and constants

/**Markdown表格的对齐方式。*/
@MarkdownDslExtendedFeature
enum class MarkdownTableAlignment(
	val textPair: Pair<String, String>
) {
	None("-" to "-"), Left(":" to "-"), Center(":" to ":"), Right("-" to ":")
}

/**Markdown警告框的限定符。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
enum class MarkdownAlertBoxQualifier(
	val style: String, val text: String
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
