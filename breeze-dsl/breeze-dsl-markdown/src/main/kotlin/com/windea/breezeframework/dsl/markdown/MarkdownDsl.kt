@file:Suppress("NOTHING_TO_INLINE", "DuplicatedCode", "unused", "CanBeParameter")

package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.criticmarkup.*
import com.windea.breezeframework.dsl.markdown.MarkdownConfig.addPrefixHeadingMarkers
import com.windea.breezeframework.dsl.markdown.MarkdownConfig.emptyColumnLength
import com.windea.breezeframework.dsl.markdown.MarkdownConfig.emptyColumnText
import com.windea.breezeframework.dsl.markdown.MarkdownConfig.horizontalLineMarkers
import com.windea.breezeframework.dsl.markdown.MarkdownConfig.indent
import com.windea.breezeframework.dsl.markdown.MarkdownConfig.listNodeMarker
import com.windea.breezeframework.dsl.markdown.MarkdownConfig.quote
import com.windea.breezeframework.dsl.markdown.MarkdownConfig.repeatableMarkerCount
import com.windea.breezeframework.dsl.markdown.MarkdownConfig.truncated
import com.windea.breezeframework.dsl.markdown.MarkdownConfig.wrapLength
import org.intellij.lang.annotations.*

//region dsl top declarations
/**Markdown的Dsl。*/
@DslMarker
@MustBeDocumented
internal annotation class MarkdownDsl

/**Markdown的扩展特性。*/
@MustBeDocumented
internal annotation class MarkdownDslExtendedFeature

/**Markdown。*/
@MarkdownDsl
class Markdown @PublishedApi internal constructor() : DslDocument, MarkdownDslEntry {
	var frontMatter: MarkdownFrontMatter? = null
	var toc: MarkdownToc? = null
	override val content: MutableList<MarkdownDslTopElement> = mutableListOf()
	val references: MutableSet<MarkdownReference> = mutableSetOf()

	override fun toString(): String {
		return listOfNotNull(
			frontMatter?.toString(),
			toc?.toString(),
			toContentString().orNull(),
			references.orNull()?.joinToString("\n")
		).joinToString("\n\n")
	}
}

/**Markdown的配置。*/
@MarkdownDsl
object MarkdownConfig : DslConfig {
	private val indentSizeRange = -2..8
	private val wrapLengthRange = 60..240
	private val repeatableMarkerCountRange = 3..12
	private val listNodeMarkerArray = charArrayOf('*', '-', '+')
	private val horizontalListMarkerArray = charArrayOf('*', '-', '_')
	private val codeFenceMarkerArray = charArrayOf('`', '~')

	var indentSize: Int = 4
		set(value) = run { if(value in indentSizeRange) field = value }
	var preferDoubleQuote: Boolean = true
	var truncated: String = "..."
	var wrapLength: Int = 120
		set(value) = run { if(value in wrapLengthRange) field = value }
	var addPrefixHeadingMarkers: Boolean = false
	var repeatableMarkerCount: Int = 3
		set(value) = run { if(value in repeatableMarkerCountRange) field = value }
	var listNodeMarker: Char = '*'
		set(value) = run { if(value in listNodeMarkerArray) field = value }
	var horizontalLineMarker: Char = '*'
		set(value) = run { if(value in horizontalListMarkerArray) field = value }
	var codeFenceMarker: Char = '`'
		set(value) = run { if(value in codeFenceMarkerArray) field = value }

	@PublishedApi internal val indent get() = if(indentSize <= -1) "\t" * indentSize else " " * indentSize
	@PublishedApi internal val quote get() = if(preferDoubleQuote) '\"' else '\''

	@PublishedApi internal val horizontalLineMarkers get() = horizontalLineMarker * repeatableMarkerCount
	@PublishedApi internal val codeFenceMarkers get() = codeFenceMarker * repeatableMarkerCount
	@PublishedApi internal val emptyColumnLength = 4
	@PublishedApi internal val emptyColumnText = " " * emptyColumnLength
}
//endregion

//region dsl declarations
/**Markdown Dsl的内联入口。*/
@MarkdownDsl
interface MarkdownInlineEntry : DslEntry, CriticMarkupInlineEntry

/**Markdown Dsl的入口。*/
@MarkdownDsl
interface MarkdownDslEntry : DslEntry, WithText<MarkdownTextBlock> {
	val content: MutableList<MarkdownDslTopElement>

	override fun toContentString(): String {
		return content.joinToString("\n\n")
	}

	@MarkdownDsl
	override fun String.unaryPlus() = MarkdownTextBlock(this).also { content += it }
}

/**Markdown Dsl的元素。*/
@MarkdownDsl
interface MarkdownDslElement : DslElement

/**Markdown Dsl的顶级元素。*/
@MarkdownDsl
interface MarkdownDslTopElement : MarkdownDslElement

@MarkdownDsl
interface WithMarkdownAttributes {
	var attributes: MarkdownAttributes?
}
//endregion

//region dsl elements
/**Markdown链接。*/
@MarkdownDsl
sealed class MarkdownLink(
	val url: String
) : MarkdownDslElement

/**Markdown自动链接。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownAutoLink @PublishedApi internal constructor(
	url: String
) : MarkdownLink(url) {
	override fun toString(): String {
		return "<$url>"
	}
}

/**Markdown行内链接。*/
@MarkdownDsl
open class MarkdownInlineLink @PublishedApi internal constructor(
	val name: String,
	url: String,
	val title: String? = null
) : MarkdownLink(url) {
	override fun toString(): String {
		val titleSnippet = title?.let { " ${it.quote(quote)}" }.orEmpty()
		return "[$name]($url$titleSnippet)"
	}
}

/**Markdown行内图片链接。*/
@MarkdownDsl
class MarkdownInlineImageLink @PublishedApi internal constructor(
	name: String,
	url: String,
	title: String? = null
) : MarkdownInlineLink(name, url, title) {
	override fun toString(): String {
		return "!${super.toString()}"
	}
}

/**Markdown引用连接。*/
@MarkdownDsl
open class MarkdownReferenceLink @PublishedApi internal constructor(
	val reference: String,
	val name: String? = null
) : MarkdownLink(truncated) {
	override fun toString(): String {
		val nameSnippet = name?.let { "[$name]" }.orEmpty()
		return "$nameSnippet[$reference]"
	}
}

/**Markdown引用图片连接。*/
@MarkdownDsl
class MarkdownReferenceImageLink @PublishedApi internal constructor(
	reference: String,
	name: String? = null
) : MarkdownReferenceLink(reference, name) {
	override fun toString(): String {
		return "!${super.toString()}"
	}
}

/**Markdown维基链接。采用Github风格，标题在前，地址在后。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownWikiLink @PublishedApi internal constructor(
	val name: String,
	url: String
) : MarkdownLink(url) {
	override fun toString(): String {
		return "[[$name|$url]]"
	}
}


/**Markdown文本块。*/
@MarkdownDsl
class MarkdownTextBlock @PublishedApi internal constructor(
	val text: String
) : MarkdownDslTopElement, CanWrap {
	override var wrapContent: Boolean = true

	override fun toString(): String {
		return if(text.length > wrapLength)
			text.let { if(wrapContent) it.chunked(wrapLength).joinToString("\n") else it }
		else text
	}
}

/**Markdown标题。*/
@MarkdownDsl
sealed class MarkdownHeading(
	val headingLevel: Int,
	val text: String
) : MarkdownDslTopElement, WithMarkdownAttributes, CanWrap {
	override var attributes: MarkdownAttributes? = null
	override var wrapContent: Boolean = true
}

/**Setext风格的Markdown标题。*/
@MarkdownDsl
sealed class MarkdownSetextHeading(
	headingLevel: Int,
	text: String
) : MarkdownHeading(headingLevel, text) {
	override fun toString(): String {
		val textSnippet = if(text.length > wrapLength)
			text.let { if(wrapContent) it.chunked(wrapLength).joinToString("\n") else it }
		else text
		val attributesSnippet = attributes?.let { " $it" }.orEmpty()
		val suffixMarkers = (if(headingLevel == 1) "=" else "-") * repeatableMarkerCount
		return "$textSnippet$attributesSnippet\n$suffixMarkers"
	}
}

/**Markdown主标题。*/
@MarkdownDsl
class MarkdownMainHeading @PublishedApi internal constructor(
	text: String
) : MarkdownSetextHeading(1, text)

/**Markdown副标题。*/
@MarkdownDsl
class MarkdownSubHeading @PublishedApi internal constructor(
	text: String
) : MarkdownSetextHeading(2, text)

/**Atx风格的Markdown标题。*/
@MarkdownDsl
sealed class MarkdownAtxHeading(
	headingLevel: Int,
	text: String
) : MarkdownHeading(headingLevel, text) {
	override fun toString(): String {
		val indent = " " * (headingLevel + 1)
		val prefixMarkers = "#" * headingLevel
		val textSnippet = if(text.length > wrapLength)
			text.let { if(wrapContent) it.chunked(wrapLength).joinToString("\n") else it }
				.prependIndent(indent).setPrefix(prefixMarkers)
		else text
		val attributesSnippet = attributes?.let { " $it" }.orEmpty()
		val suffixMarkers = if(addPrefixHeadingMarkers) " $prefixMarkers" else ""
		return "$textSnippet$attributesSnippet$suffixMarkers"
	}
}

/**Markdown一级标题。*/
@MarkdownDsl
class MarkdownHeading1 @PublishedApi internal constructor(
	text: String
) : MarkdownAtxHeading(1, text)

/**Markdown二级标题。*/
@MarkdownDsl
class MarkdownHeading2 @PublishedApi internal constructor(
	text: String
) : MarkdownAtxHeading(2, text)

/**Markdown三级标题。*/
@MarkdownDsl
class MarkdownHeading3 @PublishedApi internal constructor(
	text: String
) : MarkdownAtxHeading(3, text)

/**Markdown四级标题。*/
@MarkdownDsl
class MarkdownHeading4 @PublishedApi internal constructor(
	text: String
) : MarkdownAtxHeading(4, text)

/**Markdown五级标题。*/
@MarkdownDsl
class MarkdownHeading5 @PublishedApi internal constructor(
	text: String
) : MarkdownAtxHeading(5, text)

/**Markdown六级标题。*/
@MarkdownDsl
class MarkdownHeading6 @PublishedApi internal constructor(
	text: String
) : MarkdownAtxHeading(6, text)

/**Markdown水平分割线。*/
@MarkdownDsl
object MarkdownHorizontalLine : MarkdownDslTopElement {
	override fun toString(): String {
		return horizontalLineMarkers
	}
}

/**Markdown列表。*/
@MarkdownDsl
class MarkdownList @PublishedApi internal constructor(
	val nodes: MutableList<MarkdownListNode> = mutableListOf()
) : MarkdownDslTopElement {
	override fun toString(): String {
		return nodes.joinToString("\n")
	}
}

/**Markdown列表节点。*/
@MarkdownDsl
sealed class MarkdownListNode(
	protected val prefixMarkers: String,
	val text: String
) : MarkdownDslElement, CanWrap {
	val nodes: MutableList<MarkdownListNode> = mutableListOf()

	override var wrapContent: Boolean = true

	override fun toString(): String {
		val indent = " " * (prefixMarkers.length + 1)
		val textSnippet = if(text.length > wrapLength)
			text.let { if(wrapContent) it.chunked(wrapLength).joinToString("\n") else it }
				.prependIndent(indent).setPrefix(prefixMarkers)
		else text
		val nodesSnippet = nodes.orNull()?.joinToString("\n", "\n")?.prependIndent(indent).orEmpty()
		return "$textSnippet$nodesSnippet"
	}
}

/**Markdown有序列表节点。*/
@MarkdownDsl
class MarkdownOrderedListNode @PublishedApi internal constructor(
	val order: String,
	text: String
) : MarkdownListNode("$order.", text)

/**Markdown无序列表节点。*/
@MarkdownDsl
class MarkdownUnorderedListNode @PublishedApi internal constructor(
	text: String
) : MarkdownListNode(listNodeMarker.toString(), text)

/**Markdown任务列表节点。*/
@MarkdownDsl
class MarkdownTaskListNode @PublishedApi internal constructor(
	val isCompleted: Boolean,
	text: String
) : MarkdownListNode("$listNodeMarker [${if(isCompleted) "X" else " "}]", text)

/**Markdown定义列表。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownDefinition @PublishedApi internal constructor(
	val title: String
) : MarkdownDslTopElement, CanWrap {
	val nodes: MutableList<MarkdownDefinitionNode> = mutableListOf()

	override var wrapContent: Boolean = true

	override fun toString(): String {
		require(nodes.isNotEmpty()) { "Definition node size must be positive." }

		val titleSnippet = if(title.length > wrapLength)
			title.let { if(wrapContent) it.chunked(wrapLength).joinToString("\n") else it }
		else title
		val nodesSnippet = nodes.joinToString("\n")
		return "$titleSnippet\n$nodesSnippet"
	}
}

/**Markdown定义列表节点。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownDefinitionNode @PublishedApi internal constructor(
	val text: String
) : MarkdownDslElement, CanWrap {
	override var wrapContent: Boolean = true

	override fun toString(): String {
		return if(text.length > wrapLength)
			text.let { if(wrapContent) it.chunked(wrapLength).joinToString("\n") else it }
				.prependIndent(indent).setPrefix(":")
		else text
	}
}

//DELAY pretty format
/**Markdown表格。*/
@MarkdownDsl
class MarkdownTable @PublishedApi internal constructor() : MarkdownDslTopElement {
	var header: MarkdownTableHeader = MarkdownTableHeader()
	val rows: MutableList<MarkdownTableRow> = mutableListOf()
	var columnSize: Int? = null

	override fun toString(): String {
		require(rows.isNotEmpty()) { "Table row size must be positive." }

		//actual column size may not equal to columns.size, and can be user defined
		val actualColumnSize = columnSize ?: maxOf(header.columns.size, rows.map { it.columns.size }.max() ?: 0)
		//adjust column size
		header.columnSize = actualColumnSize
		rows.forEach { it.columnSize = actualColumnSize }

		val headerRowSnippet = header.toString()
		val delimitersSnippet = header.toDelimitersString()
		val rowsSnippet = rows.joinToString("\n")
		return "$headerRowSnippet\n$delimitersSnippet\n$rowsSnippet"
	}

	enum class Alignment(val textPair: Pair<String, String>) {
		None("-" to "-"), Left(":" to "-"), Center(":" to ":"), Right("-" to ":")
	}
}

/**Markdown表格头部。*/
@MarkdownDsl
class MarkdownTableHeader @PublishedApi internal constructor() : MarkdownDslElement, WithText<MarkdownTableColumn> {
	val columns: MutableList<MarkdownTableColumn> = mutableListOf()
	var columnSize: Int? = null

	override fun toString(): String {
		require(columns.isNotEmpty()) { "Table row column size must be positive." }

		//actual column size may not equal to columns.size
		return when {
			columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
			else -> columns.map { it.toString() }.fillEnd(columnSize!!, emptyColumnText)
		}.joinToString(" | ", "| ", " |")
	}

	fun toDelimitersString(): String {
		require(columns.isNotEmpty()) { "Table row column size must be positive." }

		return when {
			columnSize == null || columnSize == columns.size -> columns.map { it.toDelimitersString() }
			else -> columns.map { it.toDelimitersString() }.fillEnd(columnSize!!, "-" * emptyColumnLength)
		}.joinToString(" | ", "| ", " |")
	}

	@MarkdownDsl
	override fun String.unaryPlus() = column(this)

	@MarkdownDsl
	inline infix fun MarkdownTableColumn.align(alignment: MarkdownTable.Alignment) =
		this.also { it.alignment = alignment }
}

/**Markdown表格行。*/
@MarkdownDsl
open class MarkdownTableRow @PublishedApi internal constructor() : MarkdownDslElement, WithText<MarkdownTableColumn> {
	val columns: MutableList<MarkdownTableColumn> = mutableListOf()
	var columnSize: Int? = null

	override fun toString(): String {
		require(columns.isNotEmpty()) { "Table row column size must be positive." }

		//actual column size may not equal to columns.size
		return when {
			columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
			else -> columns.map { it.toString() }.fillEnd(columnSize!!, emptyColumnText)
		}.joinToString(" | ", "| ", " |")
	}

	@MarkdownDsl
	override fun String.unaryPlus() = column(this)
}

/**Markdown表格列。*/
@MarkdownDsl
class MarkdownTableColumn @PublishedApi internal constructor(
	val text: String = emptyColumnText
) : MarkdownDslElement {
	var alignment: MarkdownTable.Alignment = MarkdownTable.Alignment.None //only for columns in table header

	override fun toString(): String {
		return text
	}

	fun toDelimitersString(): String {
		val (l, r) = alignment.textPair
		return "$l${" " * (emptyColumnLength - 2)}$r"
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
interface MarkdownCode {
	val code: String
}

/**Markdown行内代码。*/
@MarkdownDsl
class MarkdownInlineCode @PublishedApi internal constructor(
	override val code: String
) : MarkdownCode {
	override fun toString(): String {
		return "`$code`"
	}
}

/**Markdown代码块。*/
@MarkdownDsl
class MarkdownCodeFence @PublishedApi internal constructor(
	val language: String,
	override val code: String
) : MarkdownDslTopElement, MarkdownCode, WithMarkdownAttributes {
	//DONE extended classes and properties
	override var attributes: MarkdownAttributes? = null

	override fun toString(): String {
		val markersSnippet = horizontalLineMarkers
		val attributesSnippet = attributes?.let { " $it" }.orEmpty()
		return "$markersSnippet$language$attributesSnippet\n$code\n$markersSnippet"
	}
}

/**Markdown数学表达式。*/
@MarkdownDsl
interface MarkdownMath {
	val code: String
}

/**Markdown行内数学表达式。*/
@MarkdownDsl
class MarkdownInlineMath @PublishedApi internal constructor(
	override val code: String
) : MarkdownMath {
	override fun toString(): String {
		return "$$code$"
	}
}

/**Markdown多行数学表达式。*/
@MarkdownDsl
class MarkdownMultilineMath @PublishedApi internal constructor(
	override val code: String
) : MarkdownDslTopElement, MarkdownMath {
	override fun toString(): String {
		return "$$\n$code\n$$"
	}
}

/**Markdown警告框。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownAdmonition @PublishedApi internal constructor(
	val qualifier: Qualifier,
	val title: String = "",
	val type: Type = Type.Normal
) : MarkdownDslTopElement, MarkdownDslEntry {
	override val content: MutableList<MarkdownDslTopElement> = mutableListOf()

	override fun toString(): String {
		require(content.isNotEmpty()) { "Alert box content must not be empty." }

		val titleSnippet = title.quote(quote)
		val contentSnippet = toContentString().prependIndent(indent)
		return "${type.text} ${qualifier.text} $titleSnippet\n$contentSnippet"
	}

	enum class Type(val text: String) {
		Normal("!!!"), Collapsed("???"), Opened("!!!+")
	}

	enum class Qualifier(val style: String, val text: String) {
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
}

/**Front Matter。只能位于Markdown文档顶部。用于配置当前的Markdown文档。使用Yaml格式。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownFrontMatter @PublishedApi internal constructor(
	@Language("Yaml")
	val text: String
) : MarkdownDslElement {
	override fun toString(): String {
		return "---\n$text\n---"
	}
}

/**Markdown目录。只能位于文档顶部。用于生成当前文档的目录。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownToc @PublishedApi internal constructor() : MarkdownDslElement, CanGenerate {
	override var generateContent: Boolean = false

	override fun toGeneratedString(): String {
		TODO("not implemented")
	}

	override fun toString(): String {
		if(generateContent) return toGeneratedString()
		return "[TOC]"
	}
}

/**Markdown导入。用于导入相对路径的图片或文本。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownImport @PublishedApi internal constructor(
	val url: String
) : MarkdownDslTopElement, CanGenerate, WithMarkdownAttributes {
	//DONE extended classes and properties
	override var attributes: MarkdownAttributes? = null
	override var generateContent: Boolean = false

	override fun toGeneratedString(): String {
		TODO("not implemented")
	}

	override fun toString(): String {
		if(generateContent) return toGeneratedString()
		val attributesSnippet = attributes?.let { " $it" }.orEmpty()
		val urlSnippet = url.quote(quote)
		return "@import $urlSnippet$attributesSnippet"
	}
}

/**Markdown宏。用于重复利用任意Markdown片段。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownMacros @PublishedApi internal constructor(
	val name: String
) : MarkdownDslTopElement, CanGenerate {
	override var generateContent: Boolean = false

	override fun toGeneratedString(): String {
		TODO("not implemented")
	}

	override fun toString(): String {
		if(generateContent) return toGeneratedString()
		return "<<< $name >>>"
	}
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
) : MarkdownDslElement, WithUniqueId {
	override val id: String get() = reference
}

/**Markdown脚注的引用。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownFootNoteReference @PublishedApi internal constructor(
	reference: String,
	val text: String
) : MarkdownReference(reference) {
	override fun equals(other: Any?) = equalsByOne(this, other) { id }

	override fun hashCode() = hashCodeByOne(this) { id }

	override fun toString(): String {
		return "[^$reference]: $text"
	}
}

/**Markdown缩写。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownAbbreviation @PublishedApi internal constructor(
	reference: String,
	val text: String
) : MarkdownReference(reference) {
	override fun equals(other: Any?) = equalsByOne(this, other) { id }

	override fun hashCode() = hashCodeByOne(this) { id }

	override fun toString(): String {
		return "*[$reference]: $text"
	}
}

/**Markdown链接的引用。*/
@MarkdownDsl
class MarkdownLinkReference @PublishedApi internal constructor(
	reference: String,
	val url: String,
	val title: String? = null
) : MarkdownReference(reference) {
	override fun equals(other: Any?) = equalsByOne(this, other) { id }

	override fun hashCode() = hashCodeByOne(this) { id }

	override fun toString(): String {
		val titleSnippet = title?.let { " ${it.quote(quote)}" }.orEmpty()
		return "[$reference]: $url$titleSnippet"
	}
}

/**Markdown特性组。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownAttributes @PublishedApi internal constructor(
	attributes: Set<MarkdownAttribute>
) : MarkdownDslElement, Set<MarkdownAttribute> by attributes {
	override fun toString(): String {
		return this.joinToString(" ", " {", "}")
	}
}

/**Markdown特性。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
sealed class MarkdownAttribute : MarkdownDslElement

/**Markdown css id特性。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownIdAttribute(
	val name: String
) : MarkdownAttribute() {
	override fun toString(): String {
		return "#$name"
	}
}

/**Markdown css class特性。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownClassAttribute(
	val name: String
) : MarkdownAttribute() {
	override fun toString(): String {
		return ".$name"
	}
}

/**Markdown属性特性。*/
@MarkdownDsl
@MarkdownDslExtendedFeature
class MarkdownPropertyAttribute(
	val name: String,
	val value: String
) : MarkdownAttribute() {
	override fun toString(): String {
		return "$name=${value.quote(quote)}"
	}
}
//endregion

//region dsl build extensions
@MarkdownDsl
inline fun markdown(block: Markdown.() -> Unit) = Markdown().apply(block)

@MarkdownDsl
inline fun markdownConfig(block: MarkdownConfig.() -> Unit) = MarkdownConfig.apply(block)

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun Markdown.frontMatter(lazyText: () -> String) =
	MarkdownFrontMatter(lazyText()).also { frontMatter = it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun Markdown.toc() =
	MarkdownToc().also { toc = it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun Markdown.abbr(reference: String, text: String) =
	MarkdownAbbreviation(reference, text).also { references += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun Markdown.footNoteRef(reference: String, text: String) =
	MarkdownFootNoteReference(reference, text).also { references += it }

@MarkdownDsl
inline fun Markdown.linkRef(reference: String, url: String, title: String? = null) =
	MarkdownLinkReference(reference, url, title).also { references += it }

@InlineDslFunction
@MarkdownDsl
inline fun MarkdownInlineEntry.text(text: String): String {
	return text
}

@InlineDslFunction
@MarkdownDsl
inline fun MarkdownInlineEntry.icon(name: String): String {
	return ":$name:"
}

@InlineDslFunction
@MarkdownDsl
inline fun MarkdownInlineEntry.footNote(reference: String): String {
	return "[^$reference]"
}

@InlineDslFunction
@MarkdownDsl
inline fun MarkdownInlineEntry.b(text: String): String { //bold
	return "**$text**"
}

@InlineDslFunction
@MarkdownDsl
inline fun MarkdownInlineEntry.i(text: String): String { //italic
	return "*$text*"
}

@InlineDslFunction
@MarkdownDsl
inline fun MarkdownInlineEntry.s(text: String): String { //stroked
	return "~~$text~~"
}

@InlineDslFunction
@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownInlineEntry.u(text: String): String { //underline
	return "++$text++"
}

@InlineDslFunction
@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownInlineEntry.em(text: String): String { //emphasis
	return "==$text=="
}

@InlineDslFunction
@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownInlineEntry.sup(text: String): String { //superscript
	return "^$text^"
}

@InlineDslFunction
@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownInlineEntry.sub(text: String): String { //subscript
	return "~$text~"
}

@InlineDslFunction
@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownInlineEntry.autoLink(url: String): String {
	return MarkdownAutoLink(url).toString()
}

@InlineDslFunction
@MarkdownDsl
inline fun MarkdownInlineEntry.link(name: String, url: String, title: String? = null): String {
	return MarkdownInlineLink(name, url, title).toString()
}

@InlineDslFunction
@MarkdownDsl
inline fun MarkdownInlineEntry.image(name: String, url: String, title: String? = null): String {
	return MarkdownInlineImageLink(name, url, title).toString()
}

@InlineDslFunction
@MarkdownDsl
inline fun MarkdownInlineEntry.refLink(reference: String, name: String? = null): String {
	return MarkdownReferenceLink(reference, name).toString()
}

@InlineDslFunction
@MarkdownDsl
inline fun MarkdownInlineEntry.refImage(reference: String, name: String? = null): String {
	return MarkdownReferenceImageLink(reference, name).toString()
}

@InlineDslFunction
@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownInlineEntry.wikiLink(name: String, url: String): String {
	return MarkdownWikiLink(name, url).toString()
}

@InlineDslFunction
@MarkdownDsl
fun MarkdownInlineEntry.code(text: String): String {
	return MarkdownInlineCode(text).toString()
}

@InlineDslFunction
@MarkdownDsl
fun MarkdownInlineEntry.math(text: String): String {
	return MarkdownInlineMath(text).toString()
}

@InlineDslFunction
@MarkdownDsl
@MarkdownDslExtendedFeature
fun MarkdownInlineEntry.attributes(vararg attributes: MarkdownAttribute) = MarkdownAttributes(attributes.toSet())

@InlineDslFunction
@MarkdownDsl
@MarkdownDslExtendedFeature
fun MarkdownInlineEntry.id(name: String) = MarkdownIdAttribute(name)

@InlineDslFunction
@MarkdownDsl
@MarkdownDslExtendedFeature
fun MarkdownInlineEntry.`class`(name: String) = MarkdownClassAttribute(name)

@InlineDslFunction
@MarkdownDsl
@MarkdownDslExtendedFeature
fun MarkdownInlineEntry.prop(name: String, value: String) = MarkdownPropertyAttribute(name, value)

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
	MarkdownHorizontalLine.also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.list(block: MarkdownList.() -> Unit) =
	MarkdownList().apply(block).also { content += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslEntry.def(title: String, block: MarkdownDefinition.() -> Unit) =
	MarkdownDefinition(title).apply(block).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.table(block: MarkdownTable.() -> Unit) =
	MarkdownTable().apply(block).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.blockQueue(block: MarkdownBlockQuote.() -> Unit) =
	MarkdownBlockQuote().apply(block).also { content += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslEntry.indentedBlock(block: MarkdownIndentedBlock.() -> Unit) =
	MarkdownIndentedBlock().apply(block).also { content += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslEntry.sideBlock(block: MarkdownSideBlock.() -> Unit) =
	MarkdownSideBlock().apply(block).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.codeFence(language: String, lazyText: () -> String) =
	MarkdownCodeFence(language, lazyText()).also { content += it }

@MarkdownDsl
inline fun MarkdownDslEntry.multilineMath(lazyText: () -> String) =
	MarkdownMultilineMath(lazyText()).also { content += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDslEntry.admonition(qualifier: MarkdownAdmonition.Qualifier, title: String = "",
	type: MarkdownAdmonition.Type = MarkdownAdmonition.Type.Normal, block: MarkdownAdmonition.() -> Unit) =
	MarkdownAdmonition(qualifier, title, type).apply(block).also { content += it }

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
inline fun MarkdownDslEntry.macrosSnippet(name: String, block: MarkdownMacrosSnippet.() -> Unit) =
	MarkdownMacrosSnippet(name).apply(block).also { content += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline infix fun <T : WithMarkdownAttributes> T.with(attributes: MarkdownAttributes) =
	this.also { it.attributes = attributes }

@MarkdownDsl
inline fun MarkdownList.ol(order: String, text: String, block: MarkdownOrderedListNode.() -> Unit = {}) =
	MarkdownOrderedListNode(order, text).apply(block).also { nodes += it }

@MarkdownDsl
inline fun MarkdownList.ul(text: String, block: MarkdownUnorderedListNode.() -> Unit = {}) =
	MarkdownUnorderedListNode(text).apply(block).also { nodes += it }

@MarkdownDsl
inline fun MarkdownList.task(status: Boolean, text: String, block: MarkdownTaskListNode.() -> Unit = {}) =
	MarkdownTaskListNode(status, text).apply(block).also { nodes += it }

@MarkdownDsl
inline fun MarkdownListNode.ol(order: String, text: String, block: MarkdownOrderedListNode.() -> Unit = {}) =
	MarkdownOrderedListNode(order, text).apply(block).also { nodes += it }

@MarkdownDsl
inline fun MarkdownListNode.ul(text: String, block: MarkdownUnorderedListNode.() -> Unit = {}) =
	MarkdownUnorderedListNode(text).apply(block).also { nodes += it }

@MarkdownDsl
inline fun MarkdownListNode.task(status: Boolean, text: String, block: MarkdownTaskListNode.() -> Unit = {}) =
	MarkdownTaskListNode(status, text).apply(block).also { nodes += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownDefinition.node(title: String, block: MarkdownDefinitionNode.() -> Unit) =
	MarkdownDefinitionNode(title).apply(block).also { nodes += it }

@MarkdownDsl
inline fun MarkdownTable.header(block: MarkdownTableHeader.() -> Unit) =
	MarkdownTableHeader().apply(block).also { header = it }

@MarkdownDsl
inline fun MarkdownTable.row(block: MarkdownTableRow.() -> Unit) =
	MarkdownTableRow().apply(block).also { rows += it }

@MarkdownDsl
inline infix fun MarkdownTable.columnSize(size: Int) =
	this.also { this.columnSize = size }

@MarkdownDsl
inline fun MarkdownTableHeader.column(text: String = emptyColumnText) =
	MarkdownTableColumn(text).also { columns += it }

@MarkdownDsl
inline fun MarkdownTableRow.column(text: String = emptyColumnText) =
	MarkdownTableColumn(text).also { columns += it }

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownTableRow.rowSpan() = column(">")

@MarkdownDsl
@MarkdownDslExtendedFeature
inline fun MarkdownTableRow.colSpan() = column("^")
//endregion
