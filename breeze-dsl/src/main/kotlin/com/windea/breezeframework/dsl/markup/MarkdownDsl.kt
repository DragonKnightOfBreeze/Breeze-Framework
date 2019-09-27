@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markup.MarkdownConfig.indent
import com.windea.breezeframework.dsl.markup.MarkdownConfig.indentSize
import com.windea.breezeframework.dsl.markup.MarkdownConfig.initialMarker
import com.windea.breezeframework.dsl.markup.MarkdownConfig.quote
import com.windea.breezeframework.dsl.markup.MarkdownConfig.repeatableMarkerCount
import com.windea.breezeframework.dsl.markup.MarkdownConfig.truncated
import org.intellij.lang.annotations.*

//TODO 太复杂了……

//REGION Dsl annotations

@DslMarker
internal annotation class MarkdownDsl

@MustBeDocumented
internal annotation class MarkdownDslExtendedFeature

//REGION Dsl & Dsl elements & Dsl config

/**Markdown。*/
@MarkdownDsl
class Markdown @PublishedApi internal constructor() : Dsl, MarkdownDslEntry {
	var frontMatter: MarkdownFrontMatter? = null
	var toc: MarkdownToc? = null
	override val content: MutableList<MarkdownTopElement> = mutableListOf()
	val references: MutableList<String> = mutableListOf()
	
	override fun toString(): String {
		TODO("not implemented")
	}
}

/**Markdown Dsl的入口。*/
interface MarkdownDslEntry {
	val content: MutableList<MarkdownTopElement>
}

/**Markdown Dsl的元素。*/
@MarkdownDsl
interface MarkdownDslElement : DslElement

/**Markdown内联元素。*/
@MarkdownDsl
interface MarkdownInlineElement : MarkdownDslElement

/**Markdown单行元素。*/
@MarkdownDsl
interface MarkdownLineElement : MarkdownTopElement, InlineContent<MarkdownText> {
	val inlineContent: MutableList<MarkdownInlineElement>
	
	@MarkdownDsl
	override fun String.unaryPlus() = TODO()
	
	@MarkdownDsl
	override fun String.unaryMinus() = TODO()
}

/**Markdown顶级元素。*/
@MarkdownDsl
interface MarkdownTopElement : MarkdownDslElement

/**Markdown引用元素。*/
@MarkdownDsl
interface MarkdownReferenceElement : MarkdownDslElement {
	fun getReferenceString(): String
}


/**Markdown文本块。*/
@MarkdownDsl
class MarkdownTextBlock @PublishedApi internal constructor() : MarkdownLineElement {
	override val inlineContent: MutableList<MarkdownInlineElement> = mutableListOf()
	
	override fun toString() = inlineContent.joinToString("")
}

/**Markdown文本。*/
@MarkdownDsl
class MarkdownText @PublishedApi internal constructor(
	val text: String
) : MarkdownInlineElement {
	override fun toString() = text
}

/**Markdown图标。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownIcon @PublishedApi internal constructor(
	val name: String
) : MarkdownInlineElement {
	override fun toString() = ":$name:"
}

/**Markdown脚注。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownFootNote @PublishedApi internal constructor(
	val reference: String,
	val text: String = truncated
) : MarkdownInlineElement, MarkdownReferenceElement {
	override fun toString() = "[^$reference]"
	
	override fun getReferenceString() = "[^$reference]: $text"
}

/**Markdown缩写。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownAbbreviation @PublishedApi internal constructor(
	val reference: String,
	val text: String = truncated
) : MarkdownInlineElement, MarkdownReferenceElement {
	override fun toString() = " $reference " //should add spaces surrounding abbr word
	
	override fun getReferenceString() = "*[$reference]: $text"
}


/**Markdown富文本。*/
@MarkdownDsl
sealed class MarkdownRichText(
	protected val markers: String,
	val text: String
) : MarkdownInlineElement {
	override fun toString() = "$markers$text$markers"
}

/**Markdown加粗文本。*/
@MarkdownDsl
class MarkdownBoldText @PublishedApi internal constructor(text: String) : MarkdownRichText("**", text)

/**Markdown加粗文本。*/
@MarkdownDsl
class MarkdownItalicText @PublishedApi internal constructor(text: String) : MarkdownRichText("*", text)

/**Markdown删除文本。*/
@MarkdownDsl
class MarkdownStrokedText @PublishedApi internal constructor(text: String) : MarkdownRichText("~~", text)

/**Markdown下划线文本。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownUnderlinedText @PublishedApi internal constructor(text: String) : MarkdownRichText("++", text)

/**Markdown高亮文本。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownHighlightText @PublishedApi internal constructor(text: String) : MarkdownRichText("==", text)

/**Markdown上标文本。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownSuperscriptText @PublishedApi internal constructor(text: String) : MarkdownRichText("^", text)

/**Markdown下标文本。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownSubscriptText @PublishedApi internal constructor(text: String) : MarkdownRichText(text, "~")


/**Critic Markup文本。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
sealed class CriticMarkupText(
	protected val prefixMarkers: String,
	val text: String,
	protected val suffixMarkers: String
) : MarkdownInlineElement {
	override fun toString() = "$prefixMarkers $text $suffixMarkers"
}

/**Critic Markup添加文本。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class CriticMarkupAppendedText @PublishedApi internal constructor(text: String) : CriticMarkupText("{++", text, "++}")

/**Critic Markup添加文本。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class CriticMarkupDeletedText @PublishedApi internal constructor(text: String) : CriticMarkupText("{--", text, "--}")

/**Critic Markup替换文本。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class CriticMarkupReplacedText @PublishedApi internal constructor(
	text: String,
	val replacedText: String
) : CriticMarkupText("{--", text, "--}") {
	private val infixMarkers: String = "~>"
	
	override fun toString() = "$prefixMarkers $text $infixMarkers $replacedText $suffixMarkers"
}

/**Critic Markup注释文本。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class CriticMarkupCommentText @PublishedApi internal constructor(text: String) : CriticMarkupText("{>>", text, "<<}")

/**Critic Markup高亮文本。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class CriticMarkupHighlightText @PublishedApi internal constructor(text: String) : CriticMarkupText("{==", text, "==}")


/**Markdown链接。*/
@MarkdownDsl
sealed class MarkdownLink(
	val url: String
) : MarkdownInlineElement

/**Markdown自动链接。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownAutoLink @PublishedApi internal constructor(url: String) : MarkdownLink(url) {
	override fun toString() = "<$url>"
}

/**Markdown行内链接。*/
@MarkdownDsl
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
@MarkdownDsl
class MarkdownInlineImageLink @PublishedApi internal constructor(
	name: String, url: String, title: String? = null
) : MarkdownInlineLink(name, url, title) {
	override fun toString() = "!${super.toString()}"
}

/**Markdown引用连接。*/
@MarkdownDsl
open class MarkdownReferenceLink @PublishedApi internal constructor(
	val name: String? = null,
	val reference: String,
	url: String,
	val title: String? = null
) : MarkdownLink(url), MarkdownReferenceElement {
	override fun toString(): String {
		val nameSnippet = name?.let { "[$name]" } ?: ""
		return "$nameSnippet[$reference]"
	}
	
	override fun getReferenceString(): String {
		val titleSnippet = title?.let { " ${it.wrapQuote(quote)}" } ?: ""
		return "[$reference]: $url$titleSnippet"
	}
}

/**Markdown引用图片连接。*/
@MarkdownDsl
class MarkdownReferenceImageLink @PublishedApi internal constructor(
	name: String? = null, reference: String, url: String, title: String? = null
) : MarkdownReferenceLink(name, reference, url, title) {
	override fun toString() = "!${super.toString()}"
}

/**Markdown维基链接。采用Github风格，标题在前，地址在后。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownWikiLink @PublishedApi internal constructor(
	val name: String,
	url: String
) : MarkdownLink(url) {
	override fun toString() = "[[$name|$url]]"
}


/**Markdown标题。*/
@MarkdownDsl
sealed class MarkdownHeader(
	protected val headerLevel: Int
) : MarkdownLineElement {
	override val inlineContent: MutableList<MarkdownInlineElement> = mutableListOf()
}

/**Setext风格的Markdown标题。*/
@MarkdownDsl
sealed class MarkdownSetextHeader(headerLevel: Int) : MarkdownHeader(headerLevel) {
	override fun toString(): String {
		val contentSnippet = inlineContent.joinToString("")
		val suffixMarkers = (if(headerLevel == 1) "=" else "-") * repeatableMarkerCount
		return "$contentSnippet\n$suffixMarkers"
	}
}

/**Markdown主标题。*/
@MarkdownDsl
class MarkdownMainHeader @PublishedApi internal constructor() : MarkdownSetextHeader(1)

/**Markdown副标题。*/
@MarkdownDsl
class MarkdownSubHeader @PublishedApi internal constructor() : MarkdownSetextHeader(2)

/**Atx风格的Markdown标题。*/
@MarkdownDsl
sealed class MarkdownAtxHeader(headerLevel: Int) : MarkdownHeader(headerLevel) {
	override fun toString(): String {
		val contentSnippet = inlineContent.joinToString("")
		val prefixMarkers = "#" * headerLevel
		return "$prefixMarkers $contentSnippet"
	}
}

/**Markdown一级标题。*/
@MarkdownDsl
class MarkdownHeader1 : MarkdownAtxHeader(1)

/**Markdown二级标题。*/
@MarkdownDsl
class MarkdownHeader2 : MarkdownAtxHeader(2)

/**Markdown三级标题。*/
@MarkdownDsl
class MarkdownHeader3 : MarkdownAtxHeader(3)

/**Markdown四级标题。*/
@MarkdownDsl
class MarkdownHeader4 : MarkdownAtxHeader(4)

/**Markdown五级标题。*/
@MarkdownDsl
class MarkdownHeader5 : MarkdownAtxHeader(5)

/**Markdown六级标题。*/
@MarkdownDsl
class MarkdownHeader6 : MarkdownAtxHeader(6)

/**Markdown水平分割线。*/
@MarkdownDsl
class MarkdownHorizontalLine @PublishedApi internal constructor() : MarkdownLineElement {
	override val inlineContent: MutableList<MarkdownInlineElement> = mutableListOf()
	
	override fun toString() = initialMarker * repeatableMarkerCount
}


/**Markdown列表。*/
@MarkdownDsl
class MarkdownList @PublishedApi internal constructor(
	val nodes: MutableList<MarkdownListNode> = mutableListOf()
) : MarkdownTopElement {
	override fun toString() = nodes.joinToString("\n")
}

/**Markdown列表节点。*/
@MarkdownDsl
sealed class MarkdownListNode(
	protected val prefixMarker: String
) : MarkdownLineElement {
	override val inlineContent: MutableList<MarkdownInlineElement> = mutableListOf()
	val nodes: MutableList<MarkdownListNode> = mutableListOf()
	
	override fun toString(): String {
		val contentSnippet = inlineContent.joinToString("")
		val lineSnippet = "$prefixMarker $contentSnippet"
		return when {
			nodes.isEmpty() -> lineSnippet
			else -> "$lineSnippet\n${nodes.toString().prependIndent(indent)}"
		}
	}
}

/**Markdown有序列表节点。*/
@MarkdownDsl
class MarkdownOrderedListNode @PublishedApi internal constructor(
	val order: String
) : MarkdownListNode("$order.")

/**Markdown无序列表节点。*/
@MarkdownDsl
class MarkdownUnorderedListNode @PublishedApi internal constructor() : MarkdownListNode(initialMarker)

/**Markdown任务列表节点。*/
@MarkdownDsl
class MarkdownTaskListNode @PublishedApi internal constructor(
	val completeStatus: Boolean
) : MarkdownListNode("$initialMarker [${if(completeStatus) "X" else " "}]")


/**Markdown定义列表。*/
@MarkdownDsl
class MarkdownDefinition @PublishedApi internal constructor(
	val title: String
) : MarkdownTopElement {
	val nodes: MutableList<MarkdownDefinitionNode> = mutableListOf()
	
	override fun toString(): String {
		require(nodes.isNotEmpty()) { "Definition node size must be greater than 0." }
		
		return "$title\n${nodes.joinToString("\n")}"
	}
}

//MarkdownDefinitionTerm?

/**Markdown定义列表节点。*/
@MarkdownDsl
class MarkdownDefinitionNode @PublishedApi internal constructor() : MarkdownLineElement {
	override val inlineContent: MutableList<MarkdownInlineElement> = mutableListOf()
	
	override fun toString(): String {
		val contentSnippet = inlineContent.joinToString("")
		val indentedContentSnippet = contentSnippet.prependIndent(" " * indentSize)
		return ":" + indentedContentSnippet.drop(1)
	}
}


//TODO pretty format
/**Markdown表格。*/
@MarkdownDsl
class MarkdownTable @PublishedApi internal constructor() : MarkdownTopElement {
	var headerRow: MarkdownTableRow? = null
	val rows: MutableList<MarkdownTableRow> = mutableListOf()
	
	override fun toString(): String {
		require(rows.isNotEmpty()) { "Table row size must be greater than 0." }
		
		val columnSize = rows.first().columns.size
		val headerRowSnippet = headerRow?.toString() ?: (1..columnSize).joinToString(" | ", "| ", " |") { " " }
		val delimiterSnippet = (1..columnSize).joinToString("-|-", "|-", "-|") { "-" }
		val rowsSnippet = rows.joinToString("\n")
		return "$headerRowSnippet\n$delimiterSnippet\n$rowsSnippet"
	}
}

/**Markdown表格行。*/
@MarkdownDsl
class MarkdownTableRow @PublishedApi internal constructor() : MarkdownDslElement {
	val columns: MutableList<MarkdownTableColumn> = mutableListOf()
	
	override fun toString(): String {
		require(columns.isNotEmpty()) { "Table row column size must be greater than 0." }
		
		return columns.joinToString(" | ", "| ", " |")
	}
}

/**Markdown表格列。*/
@MarkdownDsl
class MarkdownTableColumn @PublishedApi internal constructor() : MarkdownLineElement {
	override val inlineContent: MutableList<MarkdownInlineElement> = mutableListOf()
	
	override fun toString() = inlineContent.joinToString("")
}


/**Markdown引文。*/
@MarkdownDsl
sealed class MarkdownQuote(
	val prefixMarker: String
) : MarkdownTopElement, MarkdownDslEntry {
	override val content: MutableList<MarkdownTopElement> = mutableListOf()
	
	override fun toString(): String {
		return content.joinToString("\n").prependIndent("$prefixMarker ")
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
) : MarkdownTopElement, MarkdownCode {
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
) : MarkdownInlineElement, MarkdownMath {
	override fun toString() = "$$text$"
}

/**Markdown多行数学表达式。*/
@MarkdownDsl
class MarkdownMultilineMath @PublishedApi internal constructor(
	override val text: String
) : MarkdownTopElement, MarkdownMath {
	override fun toString() = "$$\n$text\n$$"
}


/**Markdown警告框。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownAlertBox @PublishedApi internal constructor(
	val type: MarkdownAlertBoxType,
	val qualifier: String,
	val title: String,
	val content: MutableList<MarkdownTopElement> = mutableListOf()
) : MarkdownTopElement {
	override fun toString(): String {
		require(content.isNotEmpty()) { "Alert box content must not be empty." }
		
		val titleSnippet = title.wrapQuote(quote)
		val contentSnippet = content.joinToString("\n").prependIndent(indent)
		return "${type.prefixMarkers} $qualifier $titleSnippet\n$contentSnippet"
	}
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
class MarkdownToc @PublishedApi internal constructor() : MarkdownDslElement {
	override fun toString() = "[TOC]"
}

/**Markdown导入。用于导入相对路径的图片或文本。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownImport @PublishedApi internal constructor(
	val url: String
	//TODO extended classes and properties
) : MarkdownTopElement {
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
) : MarkdownTopElement {
	override fun toString() = "<<< $name >>>"
}

/**Markdown宏片段。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownMacrosSnippet @PublishedApi internal constructor(
	val name: String
) : MarkdownTopElement, MarkdownDslEntry {
	override val content: MutableList<MarkdownTopElement> = mutableListOf()
	
	override fun toString(): String {
		val contentSnippet = content.joinToString("\n")
		return ">>> $name\n$contentSnippet\n<<<"
	}
}


/**Markdown配置。*/
@MarkdownDsl
object MarkdownConfig : DslConfig {
	/**缩进长度。*/
	var indentSize = 4
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**可重复标记的个数。*/
	var repeatableMarkerCount = 6
		set(value) = run { field = value.coerceIn(3, 6) }
	var truncated = "..."
	var preferDoubleQuote: Boolean = true
	var preferAsteriskMaker: Boolean = true
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(preferDoubleQuote) "\"" else "'"
	internal val initialMarker get() = if(preferAsteriskMaker) "*" else "-"
	
}

//REGION Enumerations and constants

/**Markdown警告框的类型。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
enum class MarkdownAlertBoxType(
	val prefixMarkers: String
) {
	Normal("!!!"), Collapsed("???"), Opened("???+")
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

//TODO Build extensions

/**构建Markdown。*/
@MarkdownDsl
inline fun markdown(builder: Markdown.() -> Unit) = Markdown().also { it.builder() }


@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun Markdown.frontMatter(lazyText: () -> String) =
	MarkdownFrontMatter(lazyText()).also { frontMatter = it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun Markdown.toc() =
	MarkdownToc().also { toc = it }


@MarkdownDsl
inline fun MarkdownDslEntry.mainHeader(builder: MarkdownMainHeader.() -> Unit) =
	MarkdownMainHeader().also { it.builder() }.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.subHeader(builder: MarkdownSubHeader.() -> Unit) =
	MarkdownSubHeader().also { it.builder() }.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.textBlock(builder: MarkdownTextBlock.() -> Unit) =
	MarkdownTextBlock().also { it.builder() }.also { content += it }


@MarkdownDsl
inline fun MarkdownList.ol(order: String, builder: MarkdownOrderedListNode.() -> Unit) =
	MarkdownOrderedListNode(order).also { it.builder() }.also { nodes += it }

@MarkdownDsl
inline fun MarkdownList.ul(builder: MarkdownUnorderedListNode.() -> Unit) =
	MarkdownUnorderedListNode().also { it.builder() }.also { nodes += it }

@MarkdownDsl
inline fun MarkdownList.task(status: Boolean, builder: MarkdownTaskListNode.() -> Unit) =
	MarkdownTaskListNode(status).also { it.builder() }.also { nodes += it }

@MarkdownDsl
inline fun MarkdownListNode.ol(order: String, builder: MarkdownOrderedListNode.() -> Unit) =
	MarkdownOrderedListNode(order).also { it.builder() }.also { nodes += it }

@MarkdownDsl
inline fun MarkdownListNode.ul(builder: MarkdownUnorderedListNode.() -> Unit) =
	MarkdownUnorderedListNode().also { it.builder() }.also { nodes += it }

@MarkdownDsl
inline fun MarkdownListNode.task(status: Boolean, builder: MarkdownTaskListNode.() -> Unit) =
	MarkdownTaskListNode(status).also { it.builder() }.also { nodes += it }


@MarkdownDsl
inline fun MarkdownDefinition.node(builder: MarkdownDefinitionNode.() -> Unit) =
	MarkdownDefinitionNode().also { it.builder() }.also { nodes += it }


@MarkdownDsl
inline fun MarkdownTable.header(builder: MarkdownTableRow.() -> Unit) =
	MarkdownTableRow().also { it.builder() }.also { headerRow = it }

@MarkdownDsl
inline fun MarkdownTable.row(builder: MarkdownTableRow.() -> Unit) =
	MarkdownTableRow().also { it.builder() }.also { rows += it }

@MarkdownDsl
inline fun MarkdownTableRow.column(builder: MarkdownTableColumn.() -> Unit) =
	MarkdownTableColumn().also { it.builder() }.also { columns += it }
