@file:Suppress("unused")

package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markdown.Markdown.*
import com.windea.breezeframework.dsl.markdown.Markdown.List
import com.windea.breezeframework.dsl.markdown.MarkdownConfig.emptyColumnText

@TopDslFunction
@MarkdownDsl
inline fun markdown(block:Document.() -> Unit) = Document().apply(block)

@TopDslFunction
@MarkdownDsl
inline fun markdownConfig(block:MarkdownConfig.() -> Unit) = MarkdownConfig.apply(block)


@InlineDslFunction
@MarkdownDsl
fun InlineDslEntry.b(text:CharSequence) = BoldText(text)

@InlineDslFunction
@MarkdownDsl
fun InlineDslEntry.i(text:CharSequence) = ItalicText(text)

@InlineDslFunction
@MarkdownDsl
fun InlineDslEntry.s(text:CharSequence) = StrokedText(text)

@InlineDslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.u(text:CharSequence) = UnderlinedText(text)

@InlineDslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.em(text:CharSequence) = HighlightText(text)

@InlineDslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.sup(text:CharSequence) = SuperscriptText(text)

@InlineDslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.sub(text:CharSequence) = SubscriptText(text)

@InlineDslFunction
@MarkdownDsl
fun InlineDslEntry.icon(name:String) = Icon(name)

@InlineDslFunction
@MarkdownDsl
fun InlineDslEntry.footNote(reference:String) = FootNote(reference)


@InlineDslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.autoLink(url:String) = AutoLink(url)

@InlineDslFunction
@MarkdownDsl
fun InlineDslEntry.link(name:String, url:String, title:String? = null) = InlineLink(name, url, title)

@InlineDslFunction
@MarkdownDsl
fun InlineDslEntry.image(name:String = "", url:String, title:String? = null) = InlineImageLink(name, url, title)

@InlineDslFunction
@MarkdownDsl
fun InlineDslEntry.refLink(reference:String, name:String? = null) = ReferenceLink(reference, name).toString()

@InlineDslFunction
@MarkdownDsl
fun InlineDslEntry.refImage(reference:String, name:String? = null) = ReferenceImageLink(reference, name).toString()

@InlineDslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.wikiLink(name:String, url:String) = WikiLink(name, url)

@InlineDslFunction
@MarkdownDsl
fun InlineDslEntry.code(text:String) = InlineCode(text)

@InlineDslFunction
@MarkdownDsl
fun InlineDslEntry.math(text:String) = InlineMath(text)


@DslFunction
@MarkdownDsl
@MarkdownExtendedFeature
inline fun Document.frontMatter(lazyText:() -> String) =
	FrontMatter(lazyText()).also { frontMatter = it }

@DslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun Document.toc() =
	Toc().also { toc = it }

@DslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun Document.abbr(reference:String, text:String) =
	Abbreviation(reference, text).also { references += it }

@DslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun Document.footNoteRef(reference:String, text:String) =
	FootNoteReference(reference, text).also { references += it }

@DslFunction
@MarkdownDsl
fun Document.linkRef(reference:String, url:String, title:String? = null) =
	LinkReference(reference, url, title).also { references += it }

@DslFunction
@MarkdownDsl
inline fun IDslEntry.textBlock(lazyText:() -> String) =
	TextBlock(lazyText()).also { content += it }

@DslFunction
@MarkdownDsl
fun IDslEntry.mainHeading(text:String) =
	MainHeading(text).also { content += it }

@DslFunction
@MarkdownDsl
fun IDslEntry.subHeading(text:String) =
	SubHeading(text).also { content += it }

@DslFunction
@MarkdownDsl
fun IDslEntry.h1(text:String) =
	Heading1(text).also { content += it }

@DslFunction
@MarkdownDsl
fun IDslEntry.h2(text:String) =
	Heading2(text).also { content += it }

@DslFunction
@MarkdownDsl
fun IDslEntry.h3(text:String) =
	Heading3(text).also { content += it }

@DslFunction
@MarkdownDsl
fun IDslEntry.h4(text:String) =
	Heading4(text).also { content += it }

@DslFunction
@MarkdownDsl
fun IDslEntry.h5(text:String) =
	Heading5(text).also { content += it }

@DslFunction
@MarkdownDsl
fun IDslEntry.h6(text:String) =
	Heading6(text).also { content += it }

@DslFunction
@MarkdownDsl
fun IDslEntry.hr() =
	HorizontalLine.also { content += it }

@DslFunction
@MarkdownDsl
inline fun IDslEntry.list(block:Markdown.List.() -> Unit) =
	List().apply(block).also { content += it }

@DslFunction
@MarkdownDsl
@MarkdownExtendedFeature
inline fun IDslEntry.def(title:String, block:Definition.() -> Unit) =
	Definition(title).apply(block).also { content += it }

@DslFunction
@MarkdownDsl
inline fun IDslEntry.table(block:Table.() -> Unit) =
	Table().apply(block).also { content += it }

@DslFunction
@MarkdownDsl
inline fun IDslEntry.blockQueue(block:BlockQuote.() -> Unit) =
	BlockQuote().apply(block).also { content += it }

@DslFunction
@MarkdownDsl
@MarkdownExtendedFeature
inline fun IDslEntry.indentedBlock(block:IndentedBlock.() -> Unit) =
	IndentedBlock().apply(block).also { content += it }

@DslFunction
@MarkdownDsl
@MarkdownExtendedFeature
inline fun IDslEntry.sideBlock(block:SideBlock.() -> Unit) =
	SideBlock().apply(block).also { content += it }

@DslFunction
@MarkdownDsl
inline fun IDslEntry.codeFence(language:String, lazyText:() -> String) =
	CodeFence(language, lazyText()).also { content += it }

@DslFunction
@MarkdownDsl
inline fun IDslEntry.multilineMath(lazyText:() -> String) =
	MultilineMath(lazyText()).also { content += it }

@DslFunction
@MarkdownDsl
@MarkdownExtendedFeature
inline fun IDslEntry.admonition(qualifier:AdmonitionQualifier, title:String = "",
	type:AdmonitionType = AdmonitionType.Normal, block:Admonition.() -> Unit) =
	Admonition(qualifier, title, type).apply(block).also { content += it }

@DslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun IDslEntry.import(url:String) =
	Import(url).also { content += it }

@DslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun IDslEntry.macros(name:String) =
	Macros(name).also { content += it }

@DslFunction
@MarkdownDsl
@MarkdownExtendedFeature
inline fun IDslEntry.macrosSnippet(name:String, block:MacrosSnippet.() -> Unit) =
	MacrosSnippet(name).apply(block).also { content += it }

@DslFunction
@MarkdownDsl
@MarkdownExtendedFeature
infix fun <T : WithAttributes> T.with(attributes:AttributeGroup) =
	apply { this.attributes = attributes }

@DslFunction
@MarkdownDsl
inline fun Markdown.List.ol(order:String, text:String, block:OrderedListNode.() -> Unit = {}) =
	OrderedListNode(order, text).apply(block).also { nodes += it }

@DslFunction
@MarkdownDsl
inline fun Markdown.List.ul(text:String, block:UnorderedListNode.() -> Unit = {}) =
	UnorderedListNode(text).apply(block).also { nodes += it }

@DslFunction
@MarkdownDsl
inline fun Markdown.List.task(status:Boolean, text:String, block:TaskListNode.() -> Unit = {}) =
	TaskListNode(status, text).apply(block).also { nodes += it }

@DslFunction
@MarkdownDsl
inline fun ListNode.ol(order:String, text:String, block:OrderedListNode.() -> Unit = {}) =
	OrderedListNode(order, text).apply(block).also { nodes += it }

@DslFunction
@MarkdownDsl
inline fun ListNode.ul(text:String, block:UnorderedListNode.() -> Unit = {}) =
	UnorderedListNode(text).apply(block).also { nodes += it }

@DslFunction
@MarkdownDsl
inline fun ListNode.task(status:Boolean, text:String, block:TaskListNode.() -> Unit = {}) =
	TaskListNode(status, text).apply(block).also { nodes += it }

@DslFunction
@MarkdownDsl
@MarkdownExtendedFeature
inline fun Definition.node(title:String, block:DefinitionNode.() -> Unit) =
	DefinitionNode(title).apply(block).also { nodes += it }

@DslFunction
@MarkdownDsl
inline fun Table.header(block:TableHeader.() -> Unit) =
	TableHeader().apply(block).also { header = it }

@DslFunction
@MarkdownDsl
inline fun Table.row(block:TableRow.() -> Unit) =
	TableRow().apply(block).also { rows += it }

@DslFunction
@MarkdownDsl
infix fun Table.columnSize(size:Int) =
	apply { columnSize = size }

@DslFunction
@MarkdownDsl
fun TableHeader.column(text:String = emptyColumnText) =
	TableColumn(text).also { columns += it }

@DslFunction
@MarkdownDsl
fun TableRow.column(text:String = emptyColumnText) =
	TableColumn(text).also { columns += it }


@InlineDslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun TableRow.rowSpan() = column(">")

@InlineDslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun TableRow.colSpan() = column("^")


@InlineDslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.attributes(vararg attributes:Attribute) = AttributeGroup(attributes.toSet())

@InlineDslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.id(name:String) = IdAttribute(name)

@InlineDslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.`class`(name:String) = ClassAttribute(name)

@InlineDslFunction
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.prop(name:String, value:String) = PropertyAttribute(name to value)
