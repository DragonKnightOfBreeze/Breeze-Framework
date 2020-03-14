@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markdown.MarkdownConfig.indent
import com.windea.breezeframework.dsl.markdown.MarkdownConfig.quote
import org.intellij.lang.annotations.*

/**Markdown。*/
@MarkdownDsl
interface Markdown {
	@MarkdownDsl
	class Document @PublishedApi internal constructor() : DslDocument, MarkdownDslEntry {
		@MarkdownExtendedFeature var frontMatter:FrontMatter? = null
		@MarkdownExtendedFeature var toc:Toc? = null
		val references:MutableSet<Reference> = mutableSetOf()
		override val content:MutableList<MarkdownDslTopElement> = mutableListOf()

		@MarkdownExtendedFeature
		override fun toString():String {
			return listOfNotNull(
				frontMatter?.toString(),
				toc?.toString(),
				contentString().orNull(),
				references.orNull()?.joinToString("\n")
			).joinToString("\n\n")
		}
	}

	/**带有Markdown的特性。*/
	@MarkdownDsl
	interface WithAttributes {
		@MarkdownExtendedFeature var attributes:AttributeGroup?
	}

	/**Markdown富文本。*/
	@MarkdownDsl
	interface RichText : MarkdownDslInlineElement, HandledCharSequence

	/**Markdown加粗文本。*/
	@MarkdownDsl
	inline class BoldText(override val text:CharSequence) : RichText {
		override fun toString() = "**$text**"
	}

	/**Markdown斜体文本。*/
	@MarkdownDsl
	inline class ItalicText(override val text:CharSequence) : RichText {
		override fun toString() = "*$text*"
	}

	/**Markdown删除线文本。*/
	@MarkdownDsl
	inline class StrokedText(override val text:CharSequence) : RichText {
		override fun toString() = "~~$text~~"
	}

	/**Markdown下划线文本。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	inline class UnderlinedText(override val text:CharSequence) : RichText {
		override fun toString() = "++$text++"
	}

	/**Markdown强调文本。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	inline class HighlightText(override val text:CharSequence) : RichText {
		override fun toString() = "==$text=="
	}

	/**Markdown上标文本。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	inline class SuperscriptText(override val text:CharSequence) : RichText {
		override fun toString() = "^$text^"
	}

	/**Markdown下标文本。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	inline class SubscriptText(override val text:CharSequence) : RichText {
		override fun toString() = "~$text~"
	}

	/**Markdown图标。*/
	@MarkdownDsl
	inline class Icon(override val text:String) : RichText {
		val name:String get() = text
		override fun toString() = ":$text:"
	}

	/**Markdown尾注。*/
	@MarkdownDsl
	inline class FootNote(override val text:String) : RichText {
		val reference:String get() = text
		override fun toString() = ":$text:"
	}

	/**Markdown链接。*/
	@MarkdownDsl
	abstract class Link(
		val name:String? = null, val url:String? = null
	) : MarkdownDslInlineElement, DelegatedCharSequence {
		override fun toString():String = text.toString()
	}

	/**Markdown自动链接。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class AutoLink @PublishedApi internal constructor(
		url:String
	) : Link(null, url) {
		override val text:CharSequence get() = "<$url>"
	}

	/**Markdown内联链接。*/
	@MarkdownDsl
	open class InlineLink @PublishedApi internal constructor(
		name:String, url:String, val title:String? = null
	) : Link(name, url) {
		override val text:CharSequence get() = "[$name]($url${title?.let { " ${it.quote(quote)}" }.orEmpty()})"
	}

	/**Markdown内联图片链接。*/
	@MarkdownDsl
	class InlineImageLink @PublishedApi internal constructor(
		name:String = "", url:String, title:String? = null
	) : InlineLink(name, url, title) {
		override val text:CharSequence get() = "!${super.toString()}"
	}

	/** Markdown的维基链接。采用Github风格，标题在前，地址在后。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class WikiLink @PublishedApi internal constructor(
		name:String, url:String
	) : Link(name, url) {
		override val text:CharSequence get() = "[[$name|$url]]"
	}

	/**Markdown引用连接。*/
	@MarkdownDsl
	open class ReferenceLink @PublishedApi internal constructor(
		val reference:String, name:String? = null
	) : Link(name, null) {
		override val text:CharSequence get() = "${name?.let { "[$name]" }.orEmpty()}[$reference]"
	}

	/**Markdown引用图片连接。*/
	@MarkdownDsl
	class ReferenceImageLink @PublishedApi internal constructor(
		reference:String, name:String? = null
	) : ReferenceLink(reference, name) {
		override val text:CharSequence get() = "!${super.toString()}"
	}

	/**Markdown文本块。*/
	@MarkdownDsl
	class TextBlock @PublishedApi internal constructor(
		val text:String
	) : MarkdownDslTopElement, CanWrapLine {
		override var wrapContent:Boolean = true

		override fun toString():String {
			return if(text.length > MarkdownConfig.wrapLength)
				text.let { if(wrapContent) it.chunked(MarkdownConfig.wrapLength).joinToString("\n") else it }
			else text
		}
	}

	/**Markdown标题。*/
	@MarkdownDsl
	abstract class Heading(
		val headingLevel:Int, val text:String
	) : MarkdownDslTopElement, WithAttributes, CanWrapLine {
		@MarkdownExtendedFeature override var attributes:AttributeGroup? = null
		override var wrapContent:Boolean = true
	}

	/**MarkdownSetext风格的标题。*/
	@MarkdownDsl
	abstract class SetextHeading(
		headingLevel:Int, text:String
	) : Heading(headingLevel, text) {
		@MarkdownExtendedFeature
		override fun toString():String {
			val textSnippet = if(text.length > MarkdownConfig.wrapLength)
				text.let { if(wrapContent) it.chunked(MarkdownConfig.wrapLength).joinToString("\n") else it }
			else text
			val attributesSnippet = attributes?.let { " $it" }.orEmpty()
			val suffixMarkers = (if(headingLevel == 1) "=" else "-") * MarkdownConfig.repeatableMarkerCount
			return "$textSnippet$attributesSnippet\n$suffixMarkers"
		}
	}

	/**Markdown主标题。*/
	@MarkdownDsl
	class MainHeading @PublishedApi internal constructor(
		text:String
	) : SetextHeading(1, text)

	/**Markdown副标题。*/
	@MarkdownDsl
	class SubHeading @PublishedApi internal constructor(
		text:String
	) : SetextHeading(2, text)

	/**MarkdownSetext风格的标题。*/
	@MarkdownDsl
	abstract class AtxHeading(
		headingLevel:Int, text:String
	) : Heading(headingLevel, text) {
		@MarkdownExtendedFeature
		override fun toString():String {
			val indent = " " * (headingLevel + 1)
			val prefixMarkers = "#" * headingLevel
			val textSnippet = if(text.length > MarkdownConfig.wrapLength)
				text.let { if(wrapContent) it.chunked(MarkdownConfig.wrapLength).joinToString("\n") else it }
					.prependIndent(indent).setPrefix(prefixMarkers)
			else text
			val attributesSnippet = attributes?.let { " $it" }.orEmpty()
			val suffixMarkers = if(MarkdownConfig.addPrefixHeadingMarkers) " $prefixMarkers" else ""
			return "$textSnippet$attributesSnippet$suffixMarkers"
		}
	}

	/**Markdown一级标题。*/
	@MarkdownDsl
	class Heading1 @PublishedApi internal constructor(
		text:String
	) : AtxHeading(1, text)

	/**Markdown二级标题。*/
	@MarkdownDsl
	class Heading2 @PublishedApi internal constructor(
		text:String
	) : AtxHeading(2, text)

	/**Markdown三级标题。*/
	@MarkdownDsl
	class Heading3 @PublishedApi internal constructor(
		text:String
	) : AtxHeading(3, text)

	/**Markdown四级标题。*/
	@MarkdownDsl
	class Heading4 @PublishedApi internal constructor(
		text:String
	) : AtxHeading(4, text)

	/**Markdown五级标题。*/
	@MarkdownDsl
	class Heading5 @PublishedApi internal constructor(
		text:String
	) : AtxHeading(5, text)

	/**Markdown六级标题。*/
	@MarkdownDsl
	class Heading6 @PublishedApi internal constructor(
		text:String
	) : AtxHeading(6, text)

	/**Markdown水平分割线。*/
	@MarkdownDsl
	object HorizontalLine : MarkdownDslTopElement {
		override fun toString() = MarkdownConfig.horizontalLineMarkers
	}

	/**Markdown列表。*/
	@MarkdownDsl
	class List @PublishedApi internal constructor(
		val nodes:MutableList<ListNode> = mutableListOf()
	) : MarkdownDslTopElement {
		override fun toString() = nodes.joinToString("\n")
	}

	/**Markdown列表节点。*/
	@MarkdownDsl
	abstract class ListNode(
		internal val prefixMarkers:String, val text:String
	) : MarkdownDslElement, CanWrapLine {
		val nodes:MutableList<ListNode> = mutableListOf()

		override var wrapContent:Boolean = true

		override fun toString():String {
			val indent = " " * (prefixMarkers.length + 1)
			val textSnippet = if(text.length > MarkdownConfig.wrapLength)
				text.let { if(wrapContent) it.chunked(MarkdownConfig.wrapLength).joinToString("\n") else it }
					.prependIndent(indent).setPrefix(prefixMarkers)
			else text
			val nodesSnippet = nodes.orNull()?.joinToString("\n", "\n")?.prependIndent(indent).orEmpty()
			return "$textSnippet$nodesSnippet"
		}
	}

	/**Markdown有序列表节点。*/
	@Suppress("CanBeParameter")
	@MarkdownDsl
	class OrderedListNode @PublishedApi internal constructor(
		val order:String, text:String
	) : ListNode("$order.", text)

	/**Markdown无序列表节点。*/
	@MarkdownDsl
	class UnorderedListNode @PublishedApi internal constructor(
		text:String
	) : ListNode(MarkdownConfig.listNodeMarker.toString(), text)

	/**Markdown任务列表节点。*/
	@MarkdownDsl
	class TaskListNode @PublishedApi internal constructor(
		val isCompleted:Boolean, text:String
	) : ListNode("${MarkdownConfig.listNodeMarker} [${if(isCompleted) "X" else " "}]", text)

	/**Markdown定义列表。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class Definition @PublishedApi internal constructor(
		val title:String
	) : MarkdownDslTopElement, CanWrapLine {
		val nodes:MutableList<DefinitionNode> = mutableListOf()

		override var wrapContent:Boolean = true

		override fun toString():String {
			require(nodes.isNotEmpty()) { "Definition node size must be positive." }

			val titleSnippet = if(title.length > MarkdownConfig.wrapLength)
				title.let { if(wrapContent) it.chunked(MarkdownConfig.wrapLength).joinToString("\n") else it }
			else title
			val nodesSnippet = nodes.joinToString("\n")
			return "$titleSnippet\n$nodesSnippet"
		}
	}

	/**Markdown定义列表节点。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class DefinitionNode @PublishedApi internal constructor(
		val text:String
	) : MarkdownDslElement, CanWrapLine {
		override var wrapContent:Boolean = true

		override fun toString():String {
			return if(text.length > MarkdownConfig.wrapLength)
				text.let { if(wrapContent) it.chunked(MarkdownConfig.wrapLength).joinToString("\n") else it }
					.prependIndent(indent).setPrefix(":")
			else text
		}
	}

	//DELAY pretty format
	/**Markdown表格。*/
	@MarkdownDsl
	class Table @PublishedApi internal constructor() : MarkdownDslTopElement {
		var header:TableHeader = TableHeader()
		val rows:MutableList<TableRow> = mutableListOf()
		var columnSize:Int? = null

		override fun toString():String {
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
	}

	/**Markdown表格的头部。*/
	@MarkdownDsl
	class TableHeader @PublishedApi internal constructor() : MarkdownDslElement, WithText<TableColumn> {
		val columns:MutableList<TableColumn> = mutableListOf()
		var columnSize:Int? = null

		override fun toString():String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			//actual column size may not equal to columns.size
			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
				else -> columns.map { it.toString() }.fillEnd(columnSize!!, MarkdownConfig.emptyColumnText)
			}.joinToString(" | ", "| ", " |")
		}

		fun toDelimitersString():String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toDelimitersString() }
				else -> columns.map { it.toDelimitersString() }.fillEnd(columnSize!!, "-" * MarkdownConfig.emptyColumnLength)
			}.joinToString(" | ", "| ", " |")
		}

		override fun String.unaryPlus() = column(this)

		@DslFunction
		@MarkdownDsl
		infix fun TableColumn.align(alignment:TableAlignment) = apply { this.alignment = alignment }
	}

	/**Markdown表格的行。*/
	@MarkdownDsl
	open class TableRow @PublishedApi internal constructor() : MarkdownDslElement, WithText<TableColumn> {
		val columns:MutableList<TableColumn> = mutableListOf()
		var columnSize:Int? = null

		override fun toString():String {
			require(columns.isNotEmpty()) { "Table row column size must be positive." }

			//actual column size may not equal to columns.size
			return when {
				columnSize == null || columnSize == columns.size -> columns.map { it.toString() }
				else -> columns.map { it.toString() }.fillEnd(columnSize!!, MarkdownConfig.emptyColumnText)
			}.joinToString(" | ", "| ", " |")
		}

		override fun String.unaryPlus() = column(this)
	}

	/**Markdown表格的列。*/
	@MarkdownDsl
	class TableColumn @PublishedApi internal constructor(
		val text:String = MarkdownConfig.emptyColumnText
	) : MarkdownDslElement {
		var alignment:TableAlignment = TableAlignment.None //only for columns in table header

		override fun toString():String {
			return text
		}

		fun toDelimitersString():String {
			val (l, r) = alignment.textPair
			return "$l${" " * (MarkdownConfig.emptyColumnLength - 2)}$r"
		}
	}

	/**Markdown引文。*/
	@MarkdownDsl
	abstract class Quote(
		val prefixMarker:String
	) : MarkdownDslTopElement, MarkdownDslEntry {
		override val content:MutableList<MarkdownDslTopElement> = mutableListOf()

		override fun toString():String {
			return contentString().prependIndent("$prefixMarker ")
		}
	}

	/**Markdown引文块。*/
	@MarkdownDsl
	class BlockQuote @PublishedApi internal constructor() : Quote(">")

	/**Markdown缩进块。*/
	@MarkdownDsl
	class IndentedBlock @PublishedApi internal constructor() : Quote(" ")

	/**Markdown侧边块。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class SideBlock @PublishedApi internal constructor() : Quote("|")

	/**Markdown代码。*/
	@MarkdownDsl
	interface Code {
		val code:String
	}

	/**Markdown内联代码。*/
	@MarkdownDsl
	class InlineCode @PublishedApi internal constructor(
		override val code:String
	) : MarkdownDslInlineElement, Code, HandledCharSequence {
		override val text:String get() = code
		override fun toString() = "`$text`"
	}

	/**Markdown代码块。*/
	@MarkdownDsl
	class CodeFence @PublishedApi internal constructor(
		val language:String,
		override val code:String
	) : MarkdownDslTopElement, Code, WithAttributes {
		//DONE extended classes and properties
		@MarkdownExtendedFeature
		override var attributes:AttributeGroup? = null

		@MarkdownExtendedFeature
		override fun toString():String {
			val markersSnippet = MarkdownConfig.horizontalLineMarkers
			val attributesSnippet = attributes?.let { " $it" }.orEmpty()
			return "$markersSnippet$language$attributesSnippet\n$code\n$markersSnippet"
		}
	}

	/**Markdown数学表达式。*/
	@MarkdownDsl
	interface Math {
		val code:String
	}

	/**Markdown内联数学表达式。*/
	@MarkdownDsl
	class InlineMath @PublishedApi internal constructor(
		override val code:String
	) : MarkdownDslInlineElement, Math, HandledCharSequence {
		override val text get() = code
		override fun toString() = "$$text$"
	}

	/**Markdown多行数学表达式。*/
	@MarkdownDsl
	class MultilineMath @PublishedApi internal constructor(
		override val code:String
	) : MarkdownDslTopElement, Math {
		override fun toString():String {
			return "$$\n$code\n$$"
		}
	}

	/**Markdown警告框。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class Admonition @PublishedApi internal constructor(
		val qualifier:AdmonitionQualifier, val title:String = "", val type:AdmonitionType = AdmonitionType.Normal
	) : MarkdownDslTopElement, MarkdownDslEntry {
		override val content:MutableList<MarkdownDslTopElement> = mutableListOf()

		override fun toString():String {
			require(content.isNotEmpty()) { "Alert box content must not be empty." }

			val titleSnippet = title.quote(quote)
			val contentSnippet = contentString().prependIndent(indent)
			return "${type.text} ${qualifier.text} $titleSnippet\n$contentSnippet"
		}
	}

	/**Front Matter。只能位于Markdown文档顶部。用于配置当前的Markdown文档。使用Yaml格式。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class FrontMatter @PublishedApi internal constructor(
		@Language("Yaml") val text:String
	) : MarkdownDslElement {
		override fun toString():String {
			return "---\n$text\n---"
		}
	}

	/**Markdown目录。只能位于文档顶部。用于生成当前文档的目录。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class Toc @PublishedApi internal constructor() : MarkdownDslElement, CanGenerate {
		override var generateContent:Boolean = false

		override fun doGenerate():String {
			TODO("not implemented")
		}

		override fun toString():String {
			if(generateContent) return doGenerate()
			return "[TOC]"
		}
	}

	/**Markdown导入命令。用于导入相对路径的图片或文本。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class Import @PublishedApi internal constructor(
		val url:String
	) : MarkdownDslTopElement, CanGenerate, WithAttributes {
		//DONE extended classes and properties
		override var attributes:AttributeGroup? = null
		override var generateContent:Boolean = false

		override fun doGenerate():String {
			TODO("not implemented")
		}

		override fun toString():String {
			if(generateContent) return doGenerate()
			val attributesSnippet = attributes?.let { " $it" }.orEmpty()
			val urlSnippet = url.quote(quote)
			return "@import $urlSnippet$attributesSnippet"
		}
	}

	/**Markdown宏。用于重复利用任意Markdown片段。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class Macros @PublishedApi internal constructor(
		val name:String
	) : MarkdownDslTopElement, CanGenerate {
		override var generateContent:Boolean = false

		override fun doGenerate():String {
			TODO("not implemented")
		}

		override fun toString():String {
			if(generateContent) return doGenerate()
			return "<<< $name >>>"
		}
	}

	/**Markdown宏片段。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class MacrosSnippet @PublishedApi internal constructor(
		val name:String
	) : MarkdownDslTopElement, MarkdownDslEntry {
		override val content:MutableList<MarkdownDslTopElement> = mutableListOf()

		override fun toString():String {
			val contentSnippet = contentString()
			return ">>> $name\n$contentSnippet\n<<<"
		}
	}

	/**Markdown引用。*/
	@MarkdownDsl
	abstract class Reference(
		val reference:String
	) : MarkdownDslElement, WithId {
		override val id:String get() = reference
	}

	/**Markdown脚注的引用。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class FootNoteReference @PublishedApi internal constructor(
		reference:String, val text:String
	) : Reference(reference) {
		override fun equals(other:Any?) = equalsByOne(this, other) { id }

		override fun hashCode() = hashCodeByOne(this) { id }

		override fun toString():String {
			return "[^$reference]: $text"
		}
	}

	/**Markdown缩写。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class Abbreviation @PublishedApi internal constructor(
		reference:String, val text:String
	) : Reference(reference) {
		override fun equals(other:Any?) = equalsByOne(this, other) { id }

		override fun hashCode() = hashCodeByOne(this) { id }

		override fun toString():String {
			return "*[$reference]: $text"
		}
	}

	/**Markdown链接的引用。*/
	@MarkdownDsl
	class LinkReference @PublishedApi internal constructor(
		reference:String, val url:String, val title:String? = null
	) : Reference(reference) {
		override fun equals(other:Any?) = equalsByOne(this, other) { id }

		override fun hashCode() = hashCodeByOne(this) { id }

		override fun toString():String {
			val titleSnippet = title?.let { " ${it.quote(quote)}" }.orEmpty()
			return "[$reference]: $url$titleSnippet"
		}
	}

	/**Markdown特性组。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	class AttributeGroup @PublishedApi internal constructor(
		attributes:Set<Attribute>
	) : MarkdownDslInlineElement, DelegatedCharSequence, Set<Attribute> by attributes {
		override val text:String get() = joinToString(" ", " {", "}")
		override fun toString() = text
	}

	/**Markdown特性。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	interface Attribute : MarkdownDslInlineElement, DelegatedCharSequence

	/**Markdown css id特性。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	inline class IdAttribute(val name:String) : Attribute {
		override val text:String get() = "#$name"
		override fun toString() = text
	}

	/**Markdown css class特性。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	inline class ClassAttribute(val name:String) : Attribute {
		override val text:String get() = ".$name"
		override fun toString() = text
	}

	/**Markdown属性特性。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	inline class PropertyAttribute(val pair:Pair<String, String>) : Attribute {
		override val text:String get() = "${pair.first}=${pair.second.quote(quote)}"
		override fun toString() = text
	}

	/**Markdown表格的对齐方式。*/
	@MarkdownDsl
	enum class TableAlignment(
		internal val textPair:Pair<String, String>
	) {
		None("-" to "-"), Left(":" to "-"), Center(":" to ":"), Right("-" to ":")
	}

	/**Markdown警告框的类型。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	enum class AdmonitionType(
		internal val text:String
	) {
		Normal("!!!"), Collapsed("???"), Opened("!!!+")
	}

	/**Markdown警告框的限定符。*/
	@MarkdownDsl
	@MarkdownExtendedFeature
	enum class AdmonitionQualifier(
		internal val style:String, internal val text:String
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

	/**
	 * Markdown的配置。
	 * @property indent 文本的缩进。
	 * @property emptyColumnText 表格的空单元格的文本。
	 * @property truncated 省略字符串。
	 * @property doubleQuoted 是否偏向使用双引号。
	 * @property markerCount 可重复标记的个数。
	 */
	@MarkdownDsl
	data class Config(
		var indent:String = "  ",
		var emptyColumnText:String = "  ",
		var truncated:String = "...",
		var doubleQuoted:Boolean = true,
		var markerCount:Int = 3
	) {
		@PublishedApi internal val quote get() = if(doubleQuoted) '\"' else '\''
		@PublishedApi
		internal fun repeat(marker:Char) = marker.repeat(markerCount)
	}

	companion object {
		@PublishedApi internal val config = Config()

		@PublishedApi
		internal fun heading(text:String, headingLevel:Int) = "${"#".repeat(headingLevel)} $text"
	}
}
