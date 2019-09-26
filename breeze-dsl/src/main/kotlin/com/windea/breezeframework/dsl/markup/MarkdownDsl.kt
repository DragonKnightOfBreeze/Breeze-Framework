package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.deprecated.text.*
import com.windea.breezeframework.dsl.markup.MarkdownConfig.indent
import com.windea.breezeframework.dsl.markup.MarkdownConfig.indentSize
import com.windea.breezeframework.dsl.markup.MarkdownConfig.initialMarker
import com.windea.breezeframework.dsl.markup.MarkdownConfig.quote
import com.windea.breezeframework.dsl.markup.MarkdownConfig.repeatableMarkerCount
import com.windea.breezeframework.dsl.markup.MarkdownConfig.truncated

//TODO

//REGION Dsl annotations

@DslMarker
internal annotation class MarkdownDsl

@MustBeDocumented
internal annotation class MarkdownDslExtendedFeature

//REGION Dsl elements & Build functions

/**Markdown。*/
@MarkdownDsl
class Markdown @PublishedApi internal constructor() : MarkdownDslElement, Dsl {
	val content: MutableList<MarkdownDslElement> = mutableListOf()
	
	override fun toString(): String {
		TODO("not implemented")
	}
}


/**Markdown Dsl的元素。*/
@MarkdownDsl
interface MarkdownDslElement : DslElement

/**Markdown内联元素。*/
@MarkdownDsl
interface MarkdownInlineElement : MarkdownDslElement

/**Markdown单行元素。*/
@MarkdownDsl
interface MarkdownLineElement : MarkdownDslElement, InlineContent<MarkdownText> {
	val inlineContent: MutableList<MarkdownInlineElement>
	
	@MarkdownDsl
	override fun String.unaryPlus() = TODO()
	
	@MarkdownDsl
	override fun String.unaryMinus() = TODO()
}

/**Markdown块元素。*/
@MarkdownDsl
interface MarkdownBlockElement : MarkdownDslElement

/**Markdown引用元素。*/
@MarkdownDsl
interface MarkdownReferenceElement : MarkdownDslElement {
	fun getReferenceString(): String
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

/**Markdown代码文本。*/
@MarkdownDsl
class MarkdownMonospacedText @PublishedApi internal constructor(text: String) : MarkdownRichText("`", text)

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
	protected val headerLevel: Int,
	override val inlineContent: MutableList<MarkdownInlineElement>
) : MarkdownLineElement

/**Setext风格的Markdown标题。*/
@MarkdownDsl
sealed class MarkdownSetextHeader(
	headerLevel: Int, inlineContent: MutableList<MarkdownInlineElement>
) : MarkdownHeader(headerLevel, inlineContent) {
	override fun toString(): String {
		val contentSnippet = inlineContent.joinToString("")
		val suffixMarkers = (if(headerLevel == 1) "=" else "-") * repeatableMarkerCount
		return "$contentSnippet\n$suffixMarkers"
	}
}

/**Markdown主标题。*/
@MarkdownDsl
class MarkdownMainHeader @PublishedApi internal constructor(
	inlineContent: MutableList<MarkdownInlineElement>
) : MarkdownSetextHeader(1, inlineContent)

/**Markdown副标题。*/
@MarkdownDsl
class MarkdownSubHeader @PublishedApi internal constructor(
	inlineContent: MutableList<MarkdownInlineElement>
) : MarkdownSetextHeader(2, inlineContent)

/**Atx风格的Markdown标题。*/
@MarkdownDsl
sealed class MarkdownAtxHeader(
	headerLevel: Int, inlineContent: MutableList<MarkdownInlineElement>
) : MarkdownHeader(headerLevel, inlineContent) {
	override fun toString(): String {
		val contentSnippet = inlineContent.joinToString("")
		val prefixMarkers = "#" * headerLevel
		return "$prefixMarkers $contentSnippet"
	}
}

/**Markdown一级标题。*/
@MarkdownDsl
class MarkdownHeader1(inlineContent: MutableList<MarkdownInlineElement>) : MarkdownAtxHeader(1, inlineContent)

/**Markdown二级标题。*/
@MarkdownDsl
class MarkdownHeader2(inlineContent: MutableList<MarkdownInlineElement>) : MarkdownAtxHeader(2, inlineContent)

/**Markdown三级标题。*/
@MarkdownDsl
class MarkdownHeader3(inlineContent: MutableList<MarkdownInlineElement>) : MarkdownAtxHeader(3, inlineContent)

/**Markdown四级标题。*/
@MarkdownDsl
class MarkdownHeader4(inlineContent: MutableList<MarkdownInlineElement>) : MarkdownAtxHeader(4, inlineContent)

/**Markdown五级标题。*/
@MarkdownDsl
class MarkdownHeader5(inlineContent: MutableList<MarkdownInlineElement>) : MarkdownAtxHeader(5, inlineContent)

/**Markdown六级标题。*/
@MarkdownDsl
class MarkdownHeader6(inlineContent: MutableList<MarkdownInlineElement>) : MarkdownAtxHeader(6, inlineContent)

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
) : MarkdownDslBlockElement {
	override fun toString() = nodes.joinToString("\n")
}

/**Markdown列表节点。*/
@MarkdownDsl
sealed class MarkdownListNode(
	protected val prefixMarker: String,
	override val inlineContent: MutableList<MarkdownInlineElement>,
	val nodes: MutableList<MarkdownListNode>
) : MarkdownLineElement {
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
	val order: String,
	inlineContent: MutableList<MarkdownInlineElement>, nodes: MutableList<MarkdownListNode> = mutableListOf()
) : MarkdownListNode("$order.", inlineContent, nodes)

/**Markdown无序列表节点。*/
@MarkdownDsl
class MarkdownUnorderedListNode @PublishedApi internal constructor(
	inlineContent: MutableList<MarkdownInlineElement>, nodes: MutableList<MarkdownListNode> = mutableListOf()
) : MarkdownListNode(initialMarker, inlineContent, nodes)

/**Markdown任务列表节点。*/
@MarkdownDsl
class MarkdownTaskListNode @PublishedApi internal constructor(
	val completeStatus: Boolean,
	inlineContent: MutableList<MarkdownInlineElement>, nodes: MutableList<MarkdownListNode> = mutableListOf()
) : MarkdownListNode("$initialMarker [${if(completeStatus) "X" else " "}]", inlineContent, nodes)


/**Markdown定义列表。*/
@MarkdownDsl
class MarkdownDefinition @PublishedApi internal constructor(
	val title: String,
	val nodes: MutableList<MarkdownDefinitionNode> = mutableListOf()
) : MarkdownBlockElement {
	override fun toString(): String {
		val prefixMarkers = ": ${" " * indentSize - 1}"
		return when {
			nodes.isEmpty() -> "$title\n$prefixMarkers$truncated"
			else -> "$title\n${nodes.joinToString("\n")}"
		}
	}
}

//MarkdownDefinitionTerm?

/**Markdown定义列表节点。*/
@MarkdownDsl
class MarkdownDefinitionNode(
	override val inlineContent: MutableList<MarkdownInlineElement>
) : MarkdownLineElement {
	override fun toString(): String {
		val contentSnippet = inlineContent.joinToString("")
		val prefixMarkers = ": ${" " * indentSize - 1}"
		return "$prefixMarkers$contentSnippet"
	}
}


//TODO

class MarkdownTable

class MarkdownTableRow

class MarkdownTableColumn


sealed class MarkdownQuote

class MarkdownBlockQuote : MarkdownQuote()

class MarkdownIndentedBlock : MarkdownQuote()

class MarkdownSideBlock : MarkdownQuote()


class MarkdownCodeFence

//REGION Enumerations and constants

//REGION Config object

/**Markdown配置。*/
object MarkdownConfig : DslConfig {
	/**缩进长度。*/
	var indentSize = 4
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**代码块的缩进长度。*/
	var codeIndentSize = 4
		set(value) = run { field = value.coerceIn(-2, 8) }
	/**可重复标记的个数。*/
	var repeatableMarkerCount = 6
		set(value) = run { field = value.coerceIn(3, 6) }
	var truncated = "..."
	var preferDoubleQuote: Boolean = true
	var preferAsteriskMaker: Boolean = true
	
	internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	internal val codeIndent get() = if(indentSize <= -1) "\t" * codeIndentSize else " " * codeIndentSize
	internal val quote get() = if(preferDoubleQuote) "\"" else "'"
	internal val initialMarker get() = if(preferAsteriskMaker) "*" else "-"
	
}
