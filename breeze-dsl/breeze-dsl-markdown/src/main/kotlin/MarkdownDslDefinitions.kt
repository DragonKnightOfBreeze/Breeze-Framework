// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslConstants.ls
import com.windea.breezeframework.dsl.markdown.MarkdownDslConfig.addPrefixHeadingMarkers
import com.windea.breezeframework.dsl.markdown.MarkdownDslConfig.emptyColumnLength
import com.windea.breezeframework.dsl.markdown.MarkdownDslConfig.emptyColumnText
import com.windea.breezeframework.dsl.markdown.MarkdownDslConfig.horizontalLineMarkers
import com.windea.breezeframework.dsl.markdown.MarkdownDslConfig.indent
import com.windea.breezeframework.dsl.markdown.MarkdownDslConfig.listNodeMarker
import com.windea.breezeframework.dsl.markdown.MarkdownDslConfig.markerCount
import com.windea.breezeframework.dsl.markdown.MarkdownDslConfig.quote
import com.windea.breezeframework.dsl.markdown.MarkdownDslConfig.wrapLength
import org.intellij.lang.annotations.*

/**
 * Dsl definitions of [MarkdownDsl].
 */
@MarkdownDslMarker
interface MarkdownDslDefinitions {
	companion object {
		internal fun heading(text: String, headingLevel: Int) = "${"#".repeat(headingLevel)} $text"
	}


	/**Markdown领域特定语言的入口。*/
	@MarkdownDslMarker
	interface IDslEntry : DslEntry {
		val content: MutableList<TopDslElement>

		override fun toContentString(): String = content.typingAll("$ls$ls")

		operator fun String.unaryPlus() = TextBlock(this).also { content += it }
	}

	/**Markdown领域特定语言的内联入口。*/
	@MarkdownDslMarker
	interface InlineDslEntry : DslEntry

	/**Markdown领域特定语言的元素。*/
	@MarkdownDslMarker
	interface IDslElement : DslElement, InlineDslEntry

	/**Markdown领域特定语言的内联元素。*/
	@MarkdownDslMarker
	interface InlineDslElement : IDslElement, Inlineable

	/**Markdown领域特定语言的顶级元素。*/
	@MarkdownDslMarker
	interface TopDslElement : IDslElement

	/**带有Markdown的特性。*/
	@MarkdownDslMarker
	interface WithAttributes {
		@MarkdownDslExtendedFeature
		var attributes: AttributeGroup?
	}


	/**Markdown富文本。*/
	@MarkdownDslMarker
	interface RichText : InlineDslElement {
		val text: CharSequence
		override val inlineText get() = text
	}

	/**Markdown加粗文本。*/
	@MarkdownDslMarker
	inline class BoldText(override val text: CharSequence) : RichText {
		override fun toString() = "**$text**"
	}

	/**Markdown斜体文本。*/
	@MarkdownDslMarker
	inline class ItalicText(override val text: CharSequence) : RichText {
		override fun toString() = "*$text*"
	}

	/**Markdown删除线文本。*/
	@MarkdownDslMarker
	inline class StrokedText(override val text: CharSequence) : RichText {
		override fun toString() = "~~$text~~"
	}

	/**Markdown下划线文本。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	inline class UnderlinedText(override val text: CharSequence) : RichText {
		override fun toString() = "++$text++"
	}

	/**Markdown强调文本。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	inline class HighlightText(override val text: CharSequence) : RichText {
		override fun toString() = "==$text=="
	}

	/**Markdown上标文本。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	inline class SuperscriptText(override val text: CharSequence) : RichText {
		override fun toString() = "^$text^"
	}

	/**Markdown下标文本。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	inline class SubscriptText(override val text: CharSequence) : RichText {
		override fun toString() = "~$text~"
	}

	/**Markdown图标。*/
	@MarkdownDslMarker
	inline class Icon(val name: String) : InlineDslElement {
		override val inlineText get() = name
		override fun toString() = ":$name:"
	}

	/**Markdown尾注。*/
	@MarkdownDslMarker
	inline class FootNote(val reference: String) : InlineDslElement {
		override val inlineText: String get() = reference
		override fun toString() = ":$reference:"
	}

	/**Markdown链接。*/
	@MarkdownDslMarker
	abstract class Link(
		val name: String? = null, val url: String? = null
	) : InlineDslElement {
		override fun toString(): String = inlineText.toString()
	}

	/**Markdown自动链接。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class AutoLink @PublishedApi internal constructor(
		url: String
	) : Link(null, url) {
		override val inlineText: CharSequence get() = "<$url>"
	}

	/**Markdown内联链接。*/
	@MarkdownDslMarker
	open class InlineLink @PublishedApi internal constructor(
		name: String, url: String, val title: String? = null
	) : Link(name, url) {
		override val inlineText: CharSequence get() = "[$name]($url${title?.let { " ${it.quote(quote)}" }.orEmpty()})"
	}

	/**Markdown内联图片链接。*/
	@MarkdownDslMarker
	class InlineImageLink @PublishedApi internal constructor(
		name: String = "", url: String, title: String? = null
	) : InlineLink(name, url, title) {
		override val inlineText: CharSequence get() = "!${super.toString()}"
	}

	/** Markdown的维基链接。采用Github风格，标题在前，地址在后。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class WikiLink @PublishedApi internal constructor(
		name: String, url: String
	) : Link(name, url) {
		override val inlineText: CharSequence get() = "[[$name|$url]]"
	}

	/**Markdown引用连接。*/
	@MarkdownDslMarker
	open class ReferenceLink @PublishedApi internal constructor(
		val reference: String, name: String? = null
	) : Link(name, null) {
		override val inlineText: CharSequence get() = "${name?.let { "[$name]" }.orEmpty()}[$reference]"
	}

	/**Markdown引用图片连接。*/
	@MarkdownDslMarker
	class ReferenceImageLink @PublishedApi internal constructor(
		reference: String, name: String? = null
	) : ReferenceLink(reference, name) {
		override val inlineText: CharSequence get() = "!${super.toString()}"
	}

	/**Markdown文本块。*/
	@MarkdownDslMarker
	class TextBlock @PublishedApi internal constructor(
		val text: String
	) : TopDslElement, Wrappable {
		override var wrapContent: Boolean = true

		override fun toString(): String {
			return if(text.length > wrapLength)
				text.let { if(wrapContent) it.chunked(wrapLength).joinToString(ls) else it }
			else text
		}
	}

	/**Markdown标题。*/
	@MarkdownDslMarker
	abstract class Heading(
		val headingLevel: Int, val text: String
	) : TopDslElement, WithAttributes, Wrappable {
		@MarkdownDslExtendedFeature
		override var attributes: AttributeGroup? = null
		override var wrapContent: Boolean = true
	}

	/**MarkdownSetext风格的标题。*/
	@MarkdownDslMarker
	abstract class SetextHeading(
		headingLevel: Int, text: String
	) : Heading(headingLevel, text) {
		@MarkdownDslExtendedFeature
		override fun toString(): String {
			val textSnippet = when {
				text.length > wrapLength -> text.let { if(wrapContent) it.chunked(wrapLength).typingAll(ls) else it }
				else -> text
			}
			val attributesSnippet = attributes?.let { " $it" }.orEmpty()
			val suffixMarkers = (if(headingLevel == 1) "=" else "-").repeat(markerCount)
			return "$textSnippet$attributesSnippet$ls$suffixMarkers"
		}
	}

	/**Markdown主标题。*/
	@MarkdownDslMarker
	class MainHeading @PublishedApi internal constructor(
		text: String
	) : SetextHeading(1, text)

	/**Markdown副标题。*/
	@MarkdownDslMarker
	class SubHeading @PublishedApi internal constructor(
		text: String
	) : SetextHeading(2, text)

	/**MarkdownSetext风格的标题。*/
	@MarkdownDslMarker
	abstract class AtxHeading(
		headingLevel: Int, text: String
	) : Heading(headingLevel, text) {
		@MarkdownDslExtendedFeature
		override fun toString(): String {
			val indent = " " * (headingLevel + 1)
			val prefixMarkers = "#" * headingLevel
			val textSnippet = if(text.length > wrapLength)
				text.let { if(wrapContent) it.chunked(wrapLength).typingAll(ls) else it }
					.prependIndent(indent).setPrefix(prefixMarkers)
			else text
			val attributesSnippet = attributes?.let { " $it" }.orEmpty()
			val suffixMarkers = if(addPrefixHeadingMarkers) " $prefixMarkers" else ""
			return "$textSnippet$attributesSnippet$suffixMarkers"
		}
	}

	/**Markdown一级标题。*/
	@MarkdownDslMarker
	class Heading1 @PublishedApi internal constructor(
		text: String
	) : AtxHeading(1, text)

	/**Markdown二级标题。*/
	@MarkdownDslMarker
	class Heading2 @PublishedApi internal constructor(
		text: String
	) : AtxHeading(2, text)

	/**Markdown三级标题。*/
	@MarkdownDslMarker
	class Heading3 @PublishedApi internal constructor(
		text: String
	) : AtxHeading(3, text)

	/**Markdown四级标题。*/
	@MarkdownDslMarker
	class Heading4 @PublishedApi internal constructor(
		text: String
	) : AtxHeading(4, text)

	/**Markdown五级标题。*/
	@MarkdownDslMarker
	class Heading5 @PublishedApi internal constructor(
		text: String
	) : AtxHeading(5, text)

	/**Markdown六级标题。*/
	@MarkdownDslMarker
	class Heading6 @PublishedApi internal constructor(
		text: String
	) : AtxHeading(6, text)

	/**Markdown水平分割线。*/
	@MarkdownDslMarker
	object HorizontalLine : TopDslElement {
		override fun toString() = horizontalLineMarkers
	}

	/**Markdown列表。*/
	@MarkdownDslMarker
	class List @PublishedApi internal constructor(
		val nodes: MutableList<ListNode> = mutableListOf()
	) : TopDslElement {
		override fun toString() = nodes.typingAll(ls)
	}

	/**Markdown列表节点。*/
	@MarkdownDslMarker
	abstract class ListNode(
		internal val prefixMarkers: String, val text: String
	) : IDslElement, Wrappable {
		val nodes: MutableList<ListNode> = mutableListOf()

		override var wrapContent: Boolean = true

		override fun toString(): String {
			val indent = " " * (prefixMarkers.length + 1)
			val textSnippet = when {
				text.length > wrapLength -> text.let {
					if(wrapContent) it.chunked(wrapLength).typingAll(ls) else it
				}.prependIndent(indent).setPrefix(prefixMarkers)
				else -> text
			}
			val nodesSnippet = nodes.typingAll(ls, ls).ifNotEmpty { it.prependIndent(indent) }
			return "$textSnippet$nodesSnippet"
		}
	}

	/**Markdown有序列表节点。*/
	@Suppress("CanBeParameter")
	@MarkdownDslMarker
	class OrderedListNode @PublishedApi internal constructor(
		val order:String, text:String
	) : ListNode("$order.", text)

	/**Markdown无序列表节点。*/
	@MarkdownDslMarker
	class UnorderedListNode @PublishedApi internal constructor(
		text: String
	) : ListNode(listNodeMarker.toString(), text)

	/**Markdown任务列表节点。*/
	@MarkdownDslMarker
	class TaskListNode @PublishedApi internal constructor(
		val isCompleted: Boolean, text: String
	) : ListNode("$listNodeMarker [${if(isCompleted) "X" else " "}]", text)

	/**Markdown定义列表。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class Definition @PublishedApi internal constructor(
		val title: String
	) : TopDslElement, Wrappable {
		val nodes: MutableList<DefinitionNode> = mutableListOf()

		override var wrapContent: Boolean = true

		override fun toString(): String {
			require(nodes.isNotEmpty()) { "Definition node size must be positive." }

			val titleSnippet = if(title.length > wrapLength)
				title.let { if(wrapContent) it.chunked(wrapLength).typingAll(ls) else it }
			else title
			val nodesSnippet = nodes.typingAll(ls)
			return "$titleSnippet$ls$nodesSnippet"
		}
	}

	/**Markdown定义列表节点。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class DefinitionNode @PublishedApi internal constructor(
		val text: String
	) : IDslElement, Wrappable {
		override var wrapContent: Boolean = true

		override fun toString(): String {
			return if(text.length > wrapLength)
				text.let { if(wrapContent) it.chunked(wrapLength).typingAll(ls) else it }
					.prependIndent(indent).setPrefix(":")
			else text
		}
	}

	//DELAY pretty format
	/**Markdown表格。*/
	@MarkdownDslMarker
	class Table @PublishedApi internal constructor() : TopDslElement {
		var header: TableHeader = TableHeader()
		val rows: MutableList<TableRow> = mutableListOf()
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
			val rowsSnippet = rows.typingAll(ls)
			return "$headerRowSnippet$ls$delimitersSnippet$ls$rowsSnippet"
		}
	}

	/**Markdown表格的头部。*/
	@MarkdownDslMarker
	class TableHeader @PublishedApi internal constructor() : IDslElement {
		val columns: MutableList<TableColumn> = mutableListOf()
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
				else -> columns.map { it.toDelimitersString() }.fillEnd(columnSize!!, "---")
			}.joinToString(" | ", "| ", " |")
		}

		operator fun String.unaryPlus() = column(this)

		@MarkdownDslMarker
		infix fun TableColumn.align(alignment: TableAlignment) = apply { this.alignment = alignment }
	}

	/**Markdown表格的行。*/
	@MarkdownDslMarker
	open class TableRow @PublishedApi internal constructor() : IDslElement {
		val columns: MutableList<TableColumn> = mutableListOf()
		var columnSize: Int? = null

		override fun toString(): String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			//actual column size may not equal to columns.size
			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
				else -> columns.map { it.toString() }.fillEnd(columnSize!!, emptyColumnText)
			}.joinToString(" | ", "| ", " |")
		}

		operator fun String.unaryPlus() = column(this)
	}

	/**Markdown表格的列。*/
	@MarkdownDslMarker
	class TableColumn @PublishedApi internal constructor(
		val text: String = emptyColumnText
	) : IDslElement {
		var alignment: TableAlignment = TableAlignment.None //only for columns in table header

		override fun toString(): String {
			return text
		}

		fun toDelimitersString(): String {
			val (l, r) = alignment.textPair
			return "$l${" " * (emptyColumnLength - 2)}$r"
		}
	}

	/**Markdown引文。*/
	@MarkdownDslMarker
	abstract class Quote(
		val prefixMarker: String
	) : TopDslElement, IDslEntry {
		override val content: MutableList<TopDslElement> = mutableListOf()

		override fun toString(): String {
			return toContentString().prependIndent("$prefixMarker ")
		}
	}

	/**Markdown引文块。*/
	@MarkdownDslMarker
	class BlockQuote @PublishedApi internal constructor() : Quote(">")

	/**Markdown缩进块。*/
	@MarkdownDslMarker
	class IndentedBlock @PublishedApi internal constructor() : Quote(" ")

	/**Markdown侧边块。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class SideBlock @PublishedApi internal constructor() : Quote("|")

	/**Markdown代码。*/
	@MarkdownDslMarker
	interface Code {
		val code: String
	}

	/**Markdown内联代码。*/
	@MarkdownDslMarker
	class InlineCode @PublishedApi internal constructor(
		override val code: String
	) : InlineDslElement, Code {
		override val inlineText: String get() = code
		override fun toString() = "`$code`"
	}

	/**Markdown代码块。*/
	@MarkdownDslMarker
	class CodeFence @PublishedApi internal constructor(
		val language: String,
		override val code: String
	) : TopDslElement, Code, WithAttributes {
		//DONE extended classes and properties
		@MarkdownDslExtendedFeature
		override var attributes: AttributeGroup? = null

		@MarkdownDslExtendedFeature
		override fun toString(): String {
			val markersSnippet = horizontalLineMarkers
			val attributesSnippet = attributes?.let { " $it" }.orEmpty()
			return "$markersSnippet$language$attributesSnippet$ls$code$ls$markersSnippet"
		}
	}

	/**Markdown数学表达式。*/
	@MarkdownDslMarker
	interface Math {
		val code: String
	}

	/**Markdown内联数学表达式。*/
	@MarkdownDslMarker
	class InlineMath @PublishedApi internal constructor(
		override val code: String
	) : InlineDslElement, Math {
		override val inlineText get() = code
		override fun toString() = "$$code$"
	}

	/**Markdown多行数学表达式。*/
	@MarkdownDslMarker
	class MultilineMath @PublishedApi internal constructor(
		override val code: String
	) : TopDslElement, Math {
		override fun toString(): String {
			return "$$$ls$code$ls$$"
		}
	}

	/**Markdown警告框。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class Admonition @PublishedApi internal constructor(
		val qualifier: AdmonitionQualifier, val title: String = "", val type: AdmonitionType = AdmonitionType.Normal
	) : TopDslElement, IDslEntry {
		override val content: MutableList<TopDslElement> = mutableListOf()

		override fun toString(): String {
			require(content.isNotEmpty()) { "Alert box content must not be empty." }

			val titleSnippet = title.quote(quote)
			val contentSnippet = toContentString().prependIndent(indent)
			return "${type.text} ${qualifier.text} $titleSnippet$ls$contentSnippet"
		}
	}

	/**Front Matter。只能位于Markdown文档顶部。用于配置当前的Markdown文档。使用Yaml格式。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class FrontMatter @PublishedApi internal constructor(
		@Language("Yaml") val text: String
	) : IDslElement {
		override fun toString(): String {
			return "---$ls$text$ls---"
		}
	}

	/**Markdown目录。只能位于文档顶部。用于生成当前文档的目录。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class Toc @PublishedApi internal constructor() : IDslElement, Generatable {
		override var generateContent: Boolean = false

		override fun toString(): String {
			return "[TOC]"
		}
	}

	/**Markdown导入命令。用于导入相对路径的图片或文本。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class Import @PublishedApi internal constructor(
		val url: String
	) : TopDslElement, Generatable, WithAttributes {
		//DONE extended classes and properties
		override var attributes: AttributeGroup? = null
		override var generateContent: Boolean = false

		override fun toString(): String {
			val attributesSnippet = attributes?.let { " $it" }.orEmpty()
			val urlSnippet = url.quote(quote)
			return "@import $urlSnippet$attributesSnippet"
		}
	}

	/**Markdown宏。用于重复利用任意Markdown片段。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class Macros @PublishedApi internal constructor(
		val name: String
	) : TopDslElement, Generatable {
		override var generateContent: Boolean = false

		override fun toString(): String {
			return "<<< $name >>>"
		}
	}

	/**Markdown宏片段。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class MacrosSnippet @PublishedApi internal constructor(
		val name: String
	) : TopDslElement, IDslEntry {
		override val content: MutableList<TopDslElement> = mutableListOf()

		override fun toString(): String {
			val contentSnippet = toContentString()
			return ">>> $name$ls$contentSnippet$ls<<<"
		}
	}

	/**Markdown引用。*/
	@MarkdownDslMarker
	abstract class Reference(
		val reference: String
	) : IDslElement, WithId {
		override val id: String get() = reference
	}

	/**Markdown脚注的引用。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class FootNoteReference @PublishedApi internal constructor(
		reference: String, val text: String
	) : Reference(reference) {
		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString(): String {
			return "[^$reference]: $text"
		}
	}

	/**Markdown缩写。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class Abbreviation @PublishedApi internal constructor(
		reference: String, val text: String
	) : Reference(reference) {
		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString(): String {
			return "*[$reference]: $text"
		}
	}

	/**Markdown链接的引用。*/
	@MarkdownDslMarker
	class LinkReference @PublishedApi internal constructor(
		reference: String, val url: String, val title: String? = null
	) : Reference(reference) {
		override fun equals(other: Any?) = equalsBy(this, other) { arrayOf(id) }

		override fun hashCode() = hashCodeBy(this) { arrayOf(id) }

		override fun toString(): String {
			val titleSnippet = title?.let { " ${it.quote(quote)}" }.orEmpty()
			return "[$reference]: $url$titleSnippet"
		}
	}

	/**Markdown特性组。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	class AttributeGroup @PublishedApi internal constructor(
		attributes: Set<Attribute>
	) : InlineDslElement, Inlineable, Set<Attribute> by attributes {
		override val inlineText: String get() = joinToString(" ", " {", "}")
		override fun toString() = inlineText
	}

	/**Markdown特性。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	interface Attribute : InlineDslElement, Inlineable

	/**Markdown css id特性。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	inline class IdAttribute(val name: String) : Attribute {
		override val inlineText: String get() = "#$name"
		override fun toString() = inlineText
	}

	/**Markdown css class特性。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	inline class ClassAttribute(val name: String) : Attribute {
		override val inlineText: String get() = ".$name"
		override fun toString() = inlineText
	}

	/**Markdown属性特性。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	inline class PropertyAttribute(val pair: Pair<String, String>) : Attribute {
		override val inlineText: String get() = "${pair.first}=${pair.second.quote(quote)}"
		override fun toString() = inlineText
	}

	/**Markdown表格的对齐方式。*/
	@MarkdownDslMarker
	enum class TableAlignment(
		internal val textPair: Pair<String, String>
	) {
		None("-" to "-"), Left(":" to "-"), Center(":" to ":"), Right("-" to ":")
	}

	/**Markdown警告框的类型。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	enum class AdmonitionType(
		internal val text: String
	) {
		Normal("!!!"), Collapsed("???"), Opened("!!!+")
	}

	/**Markdown警告框的限定符。*/
	@MarkdownDslMarker
	@MarkdownDslExtendedFeature
	enum class AdmonitionQualifier(
		internal val style: String, internal val text: String
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
}
