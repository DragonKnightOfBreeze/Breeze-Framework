@file:Suppress("NOTHING_TO_INLINE", "DuplicatedCode")

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
internal annotation class MarkdownDsl

@MustBeDocumented
internal annotation class MarkdownDslExtendedFeature

//REGION Dsl & Dsl elements & Dsl config

/**构建Markdown。*/
@MarkdownDsl
inline fun markdown(builder: Markdown.() -> Unit) = Markdown().also { it.builder() }

/**Markdown。*/
@MarkdownDsl
class Markdown @PublishedApi internal constructor() : Dsl, MarkdownDslEntry {
	var frontMatter: MarkdownFrontMatter? = null
	var toc: MarkdownToc? = null
	override val content: MutableList<MarkdownTopElement> = mutableListOf()
	val references: MutableSet<MarkdownReferenceElement> = mutableSetOf()
	
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
	inline fun MarkdownDslInlineEntry.footNote(reference: String, text: String = truncated) =
		MarkdownFootNote(reference, text).also { inlineContent += it }.also { this@Markdown.references += it }
	
	@MarkdownDsl
	inline fun MarkdownDslInlineEntry.abbr(reference: String, text: String = truncated) =
		MarkdownAbbreviation(reference, text).also { inlineContent += it }.also { this@Markdown.references += it }
	
	@MarkdownDsl
	inline fun MarkdownDslInlineEntry.refLink(name: String? = null, reference: String, url: String = truncated, title: String? = null) =
		MarkdownReferenceLink(name, reference, url, title).also { inlineContent += it }.also { this@Markdown.references += it }
	
	@MarkdownDsl
	inline fun MarkdownDslInlineEntry.refImage(name: String? = null, reference: String, url: String = truncated, title: String? = null) =
		MarkdownReferenceImageLink(name, reference, url, title).also { inlineContent += it }.also { this@Markdown.references += it }
	
	@MarkdownDsl
	inline fun MarkdownDslInlineEntry.rLink(reference: String, url: String = truncated, title: String? = null) =
		MarkdownReferenceLink(null, reference, url, title).also { inlineContent += it }.also { this@Markdown.references += it }
	
	@MarkdownDsl
	inline fun MarkdownDslInlineEntry.rImage(reference: String, url: String = truncated, title: String? = null) =
		MarkdownReferenceImageLink(null, reference, url, title).also { inlineContent += it }.also { this@Markdown.references += it }
}

/**Markdown Dsl的入口。*/
@MarkdownDsl
interface MarkdownDslEntry : InlineContent<MarkdownTextBlock> {
	val content: MutableList<MarkdownTopElement>
	
	fun toContentString() = content.joinToString("\n\n")
	
	@MarkdownDsl
	override fun String.unaryPlus() = textBlock { text(this@unaryPlus) }
	
	@MarkdownDsl
	override fun String.unaryMinus() = textBlock { inlineContent.clear();text(this@unaryMinus) }
}

@MarkdownDsl
inline fun MarkdownDslEntry.textBlock(builder: MarkdownTextBlock.() -> Unit) =
	MarkdownTextBlock().also { it.builder() }.also { content += it }


@MarkdownDsl
inline fun MarkdownDslEntry.mainHeading(builder: MarkdownMainHeading.() -> Unit) =
	MarkdownMainHeading().also { it.builder() }.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.subHeading(builder: MarkdownSubHeading.() -> Unit) =
	MarkdownSubHeading().also { it.builder() }.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.h1(builder: MarkdownHeading1.() -> Unit) =
	MarkdownHeading1().also { it.builder() }.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.h2(builder: MarkdownHeading2.() -> Unit) =
	MarkdownHeading2().also { it.builder() }.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.h3(builder: MarkdownHeading3.() -> Unit) =
	MarkdownHeading3().also { it.builder() }.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.h4(builder: MarkdownHeading4.() -> Unit) =
	MarkdownHeading4().also { it.builder() }.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.h5(builder: MarkdownHeading5.() -> Unit) =
	MarkdownHeading5().also { it.builder() }.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.h6(builder: MarkdownHeading6.() -> Unit) =
	MarkdownHeading6().also { it.builder() }.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.hr(builder: MarkdownHorizontalLine.() -> Unit) =
	MarkdownHorizontalLine().also { it.builder() }.also { content += it }


@MarkdownDsl
inline fun MarkdownDslEntry.list(builder: MarkdownList.() -> Unit) =
	MarkdownList().also { it.builder() }.also { content += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslEntry.def(builder: MarkdownDefinition.() -> Unit) =
	MarkdownDefinition().also { it.builder() }.also { content += it }

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

/**Markdown Dsl的内联入口。*/
@MarkdownDsl
interface MarkdownDslInlineEntry : InlineContent<MarkdownText> {
	val inlineContent: MutableList<MarkdownInlineElement>
	
	//return truncated string if empty
	fun toInlineContentString() = if(inlineContent.isEmpty()) truncated else inlineContent.joinToString("")
	
	@MarkdownDsl
	override fun String.unaryPlus() = text(this)
	
	@MarkdownDsl
	override fun String.unaryMinus() = run { inlineContent.clear();text(this) }
}

@MarkdownDsl
inline fun MarkdownDslInlineEntry.text(text: String) =
	MarkdownText(text).also { inlineContent += it }

@MarkdownDsl
inline fun MarkdownDslInlineEntry.icon(text: String) =
	MarkdownIcon(text).also { inlineContent += it }


@MarkdownDsl
inline fun MarkdownDslInlineEntry.b(text: String) =
	MarkdownBoldText(text).also { inlineContent += it }

@MarkdownDsl
inline fun MarkdownDslInlineEntry.i(text: String) =
	MarkdownItalicText(text).also { inlineContent += it }

@MarkdownDsl
inline fun MarkdownDslInlineEntry.s(text: String) =
	MarkdownStrokedText(text).also { inlineContent += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslInlineEntry.u(text: String) =
	MarkdownUnderlinedText(text).also { inlineContent += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslInlineEntry.h(text: String) =
	MarkdownHighlightText(text).also { inlineContent += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslInlineEntry.sup(text: String) =
	MarkdownSuperscriptText(text).also { inlineContent += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslInlineEntry.sub(text: String) =
	MarkdownSubscriptText(text).also { inlineContent += it }


@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslInlineEntry.cmAppend(text: String) =
	CriticMarkupAppendedText(text).also { inlineContent += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslInlineEntry.cmDelete(text: String) =
	CriticMarkupDeletedText(text).also { inlineContent += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslInlineEntry.cmReplace(text: String, replacedText: String) =
	CriticMarkupReplacedText(text, replacedText).also { inlineContent += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslInlineEntry.cmComment(text: String) =
	CriticMarkupCommentText(text).also { inlineContent += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslInlineEntry.cmHighlight(text: String) =
	CriticMarkupHighlightText(text).also { inlineContent += it }


@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslInlineEntry.autoLink(url: String) =
	MarkdownAutoLink(url).also { inlineContent += it }

@MarkdownDsl
inline fun MarkdownDslInlineEntry.link(name: String, url: String, title: String? = null) =
	MarkdownInlineLink(name, url, title).also { inlineContent += it }

@MarkdownDsl
inline fun MarkdownDslInlineEntry.image(name: String, url: String, title: String? = null) =
	MarkdownInlineImageLink(name, url, title).also { inlineContent += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslInlineEntry.wikiLink(name: String, url: String) =
	MarkdownWikiLink(name, url).also { inlineContent += it }


@MarkdownDsl
inline fun MarkdownDslInlineEntry.code(text: String) =
	MarkdownInlineCode(text).also { inlineContent += it }

@MarkdownDsl
inline fun MarkdownDslInlineEntry.math(text: String) =
	MarkdownInlineMath(text).also { inlineContent += it }


/**Markdown Dsl的元素。*/
@MarkdownDsl
interface MarkdownDslElement : DslElement

/**Markdown顶级元素。*/
@MarkdownDsl
interface MarkdownTopElement : MarkdownDslElement

/**Markdown内联元素。*/
@MarkdownDsl
interface MarkdownInlineElement : MarkdownDslElement

/**Markdown引用元素。*/
@MarkdownDsl
interface MarkdownReferenceElement : MarkdownDslElement, UniqueDslElement {
	fun getReferenceString(): String
}


/**Markdown文本块。*/
@MarkdownDsl
class MarkdownTextBlock @PublishedApi internal constructor() : MarkdownTopElement, MarkdownDslInlineEntry {
	override val inlineContent: MutableList<MarkdownInlineElement> = mutableListOf()
	
	override fun toString() = toInlineContentString()
}

/**Markdown文本。*/
@MarkdownDsl
class MarkdownText @PublishedApi internal constructor(
	val text: String
) : MarkdownInlineElement {
	override fun toString() = text
}

/**Markdown图标。可以使用font awesome和emoji图标。*/
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
	override fun equals(other: Any?) = other === this || (other is MarkdownFootNote && other.reference == reference)
	
	override fun hashCode() = reference.hashCode()
	
	override fun toString() = "[^$reference]"
	
	override fun getReferenceString() = "[^$reference]: $text"
}

/**Markdown缩写。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownAbbreviation @PublishedApi internal constructor(
	val reference: String,
	val text: String
) : MarkdownInlineElement, MarkdownReferenceElement {
	override fun equals(other: Any?) = other === this || (other is MarkdownAbbreviation && other.reference == reference)
	
	override fun hashCode() = reference.hashCode()
	
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

/**Markdown斜体文本。*/
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
	override fun equals(other: Any?) = other === this || (other is MarkdownReferenceLink && other.reference == reference)
	
	override fun hashCode() = reference.hashCode()
	
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
sealed class MarkdownHeading(
	val headingLevel: Int
) : MarkdownTopElement, MarkdownDslInlineEntry {
	override val inlineContent: MutableList<MarkdownInlineElement> = mutableListOf()
}

/**Setext风格的Markdown标题。*/
@MarkdownDsl
sealed class MarkdownSetextHeading(headingLevel: Int) : MarkdownHeading(headingLevel) {
	override fun toString(): String {
		val contentSnippet = toInlineContentString()
		val suffixMarkers = (if(headingLevel == 1) "=" else "-") * repeatableMarkerCount
		return "$contentSnippet\n$suffixMarkers"
	}
}

/**Markdown主标题。*/
@MarkdownDsl
class MarkdownMainHeading @PublishedApi internal constructor() : MarkdownSetextHeading(1)

/**Markdown副标题。*/
@MarkdownDsl
class MarkdownSubHeading @PublishedApi internal constructor() : MarkdownSetextHeading(2)

/**Atx风格的Markdown标题。*/
@MarkdownDsl
sealed class MarkdownAtxHeading(headingLevel: Int) : MarkdownHeading(headingLevel) {
	override fun toString(): String {
		val contentSnippet = toInlineContentString()
		val prefixMarkers = "#" * headingLevel
		return "$prefixMarkers $contentSnippet"
	}
}

/**Markdown一级标题。*/
@MarkdownDsl
class MarkdownHeading1 : MarkdownAtxHeading(1)

/**Markdown二级标题。*/
@MarkdownDsl
class MarkdownHeading2 : MarkdownAtxHeading(2)

/**Markdown三级标题。*/
@MarkdownDsl
class MarkdownHeading3 : MarkdownAtxHeading(3)

/**Markdown四级标题。*/
@MarkdownDsl
class MarkdownHeading4 : MarkdownAtxHeading(4)

/**Markdown五级标题。*/
@MarkdownDsl
class MarkdownHeading5 : MarkdownAtxHeading(5)

/**Markdown六级标题。*/
@MarkdownDsl
class MarkdownHeading6 : MarkdownAtxHeading(6)

/**Markdown水平分割线。*/
@MarkdownDsl
class MarkdownHorizontalLine @PublishedApi internal constructor() : MarkdownTopElement, MarkdownDslInlineEntry {
	override val inlineContent: MutableList<MarkdownInlineElement> = mutableListOf()
	
	override fun toString() = initialMarker * repeatableMarkerCount
}


/**Markdown列表。*/
@MarkdownDsl
class MarkdownList @PublishedApi internal constructor(
	val nodes: MutableList<MarkdownListNode> = mutableListOf()
) : MarkdownTopElement {
	override fun toString() = nodes.joinToString("\n")
	
	
	@MarkdownDsl
	inline fun ol(order: String, builder: MarkdownOrderedListNode.() -> Unit) =
		MarkdownOrderedListNode(order).also { it.builder() }.also { nodes += it }
	
	@MarkdownDsl
	inline fun ul(builder: MarkdownUnorderedListNode.() -> Unit) =
		MarkdownUnorderedListNode().also { it.builder() }.also { nodes += it }
	
	@MarkdownDsl
	inline fun task(status: Boolean, builder: MarkdownTaskListNode.() -> Unit) =
		MarkdownTaskListNode(status).also { it.builder() }.also { nodes += it }
}

/**Markdown列表节点。*/
@MarkdownDsl
sealed class MarkdownListNode(
	protected val prefixMarker: String
) : MarkdownDslInlineEntry {
	override val inlineContent: MutableList<MarkdownInlineElement> = mutableListOf()
	val nodes: MutableList<MarkdownListNode> = mutableListOf()
	
	override fun toString(): String {
		val contentSnippet = "$prefixMarker ${toInlineContentString()}"
		val nodesSnippet = if(nodes.isEmpty()) "" else nodes.joinToString("\n", "\n") { it.toString().prependIndent(indent) }
		return "$contentSnippet$nodesSnippet"
	}
	
	
	@MarkdownDsl
	inline fun ol(order: String, builder: MarkdownOrderedListNode.() -> Unit) =
		MarkdownOrderedListNode(order).also { it.builder() }.also { nodes += it }
	
	@MarkdownDsl
	inline fun ul(builder: MarkdownUnorderedListNode.() -> Unit) =
		MarkdownUnorderedListNode().also { it.builder() }.also { nodes += it }
	
	@MarkdownDsl
	inline fun task(status: Boolean, builder: MarkdownTaskListNode.() -> Unit) =
		MarkdownTaskListNode(status).also { it.builder() }.also { nodes += it }
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
@MarkdownDslExtendedFeature
class MarkdownDefinition @PublishedApi internal constructor() : MarkdownTopElement, MarkdownDslInlineEntry {
	override val inlineContent: MutableList<MarkdownInlineElement> = mutableListOf()
	val nodes: MutableList<MarkdownDefinitionNode> = mutableListOf()
	
	override fun toString(): String {
		require(nodes.isNotEmpty()) { "Definition node size must be greater than 0." }
		
		val contentSnippet = toInlineContentString()
		val nodesSnippet = nodes.joinToString("\n")
		return "$contentSnippet\n$nodesSnippet"
	}
	
	
	@MarkdownDsl
	@MarkdownDslExtendedFeature
	inline fun node(builder: MarkdownDefinitionNode.() -> Unit) =
		MarkdownDefinitionNode().also { it.builder() }.also { nodes += it }
}

//MarkdownDefinitionTerm?

/**Markdown定义列表节点。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownDefinitionNode @PublishedApi internal constructor() : MarkdownDslInlineEntry {
	override val inlineContent: MutableList<MarkdownInlineElement> = mutableListOf()
	
	override fun toString(): String {
		val contentSnippet = toInlineContentString().prependIndent(indent)
		return ":" + contentSnippet.drop(1)
	}
}


//TODO pretty format
/**Markdown表格。*/
@MarkdownDsl
class MarkdownTable @PublishedApi internal constructor() : MarkdownTopElement {
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
class MarkdownTableHeader @PublishedApi internal constructor() : MarkdownDslElement {
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
	inline fun column(builder: MarkdownTableColumn.() -> Unit) =
		MarkdownTableColumn().also { it.builder() }.also { columns += it }
	
	@MarkdownDsl
	inline infix fun MarkdownTableColumn.align(alignment: MarkdownTableAlignment) =
		this.also { it.alignment = alignment }
}

/**Markdown表格行。*/
@MarkdownDsl
open class MarkdownTableRow @PublishedApi internal constructor() : MarkdownDslElement {
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
	inline fun column(builder: MarkdownTableColumn.() -> Unit) =
		MarkdownTableColumn().also { it.builder() }.also { columns += it }
	
	@MarkdownDsl
	inline fun column() =
		MarkdownTableColumn().also { columns += it }
	
	@MarkdownDsl
	@MarkdownDslExtendedFeature
	inline fun rowSpan() =
		MarkdownTableColumn().also { it.inlineContent += MarkdownText(">") }.also { columns += it }
	
	@MarkdownDsl
	@MarkdownDslExtendedFeature
	inline fun colSpan() =
		MarkdownTableColumn().also { it.inlineContent += MarkdownText("^") }.also { columns += it }
}

/**Markdown表格列。*/
@MarkdownDsl
class MarkdownTableColumn @PublishedApi internal constructor() : MarkdownDslInlineEntry {
	override val inlineContent: MutableList<MarkdownInlineElement> = mutableListOf()
	
	var alignment: MarkdownTableAlignment = MarkdownTableAlignment.None //only for columns in table header
	
	override fun toInlineContentString() = if(inlineContent.isEmpty()) emptyColumnText else inlineContent.joinToString("")
	
	override fun toString() = toInlineContentString()
	
	fun toDelimiterString(): String {
		val (l, r) = alignment.textPair
		return "$l${" " * (emptyColumnSize - 2)}$r"
	}
}


/**Markdown引文。*/
@MarkdownDsl
sealed class MarkdownQuote(
	val prefixMarker: String
) : MarkdownTopElement, MarkdownDslEntry {
	override val content: MutableList<MarkdownTopElement> = mutableListOf()
	
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
	val qualifier: MarkdownAlertBoxQualifier,
	val title: String = "",
	val type: MarkdownAlertBoxType = MarkdownAlertBoxType.Normal
) : MarkdownTopElement, MarkdownDslEntry {
	override val content: MutableList<MarkdownTopElement> = mutableListOf()
	
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
) : MarkdownTopElement, CanGenerateContent {
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
) : MarkdownTopElement, CanGenerateContent {
	override var generateContent: Boolean = false //TODO
	
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
		val contentSnippet = toContentString()
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
	var repeatableMarkerCount = 3
		set(value) = run { field = value.coerceIn(3, 6) }
	var truncated = "..."
	var preferDoubleQuote: Boolean = true
	var preferAsteriskMaker: Boolean = true
	var emptyColumnSize: Int = 4
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val quote get() = if(preferDoubleQuote) "\"" else "'"
	internal val initialMarker get() = if(preferAsteriskMaker) "*" else "-"
	internal val emptyColumnText get() = " " * emptyColumnSize
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

//REGION Build extensions












