@file:Suppress("unused")

package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.markdown.Markdown.*
import com.windea.breezeframework.dsl.markdown.Markdown.Companion.config
import com.windea.breezeframework.dsl.markdown.Markdown.List
@MarkdownDsl
inline fun markdown(block:Document.() -> Unit) = Document().apply(block)
@MarkdownDsl
inline fun markdownConfig(block:Config.() -> Unit) = config.apply(block)
@MarkdownDsl
fun InlineDslEntry.b(text:CharSequence) = BoldText(text)
@MarkdownDsl
fun InlineDslEntry.i(text:CharSequence) = ItalicText(text)
@MarkdownDsl
fun InlineDslEntry.s(text:CharSequence) = StrokedText(text)
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.u(text:CharSequence) = UnderlinedText(text)
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.em(text:CharSequence) = HighlightText(text)
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.sup(text:CharSequence) = SuperscriptText(text)
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.sub(text:CharSequence) = SubscriptText(text)
@MarkdownDsl
fun InlineDslEntry.icon(name:String) = Icon(name)
@MarkdownDsl
fun InlineDslEntry.footNote(reference:String) = FootNote(reference)
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.autoLink(url:String) = AutoLink(url)
@MarkdownDsl
fun InlineDslEntry.link(name:String, url:String, title:String? = null) = InlineLink(name, url, title)
@MarkdownDsl
fun InlineDslEntry.image(name:String = "", url:String, title:String? = null) = InlineImageLink(name, url, title)
@MarkdownDsl
fun InlineDslEntry.refLink(reference:String, name:String? = null) = ReferenceLink(reference, name).toString()
@MarkdownDsl
fun InlineDslEntry.refImage(reference:String, name:String? = null) = ReferenceImageLink(reference, name).toString()
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.wikiLink(name:String, url:String) = WikiLink(name, url)
@MarkdownDsl
fun InlineDslEntry.code(text:String) = InlineCode(text)
@MarkdownDsl
fun InlineDslEntry.math(text:String) = InlineMath(text)
@MarkdownDsl
@MarkdownExtendedFeature
inline fun Document.frontMatter(lazyText:() -> String) =
	FrontMatter(lazyText()).also { frontMatter = it }
@MarkdownDsl
@MarkdownExtendedFeature
fun Document.toc() =
	Toc().also { toc = it }
@MarkdownDsl
@MarkdownExtendedFeature
fun Document.abbr(reference:String, text:String) =
	Abbreviation(reference, text).also { references += it }
@MarkdownDsl
@MarkdownExtendedFeature
fun Document.footNoteRef(reference:String, text:String) =
	FootNoteReference(reference, text).also { references += it }
@MarkdownDsl
fun Document.linkRef(reference:String, url:String, title:String? = null) =
	LinkReference(reference, url, title).also { references += it }
@MarkdownDsl
inline fun IDslEntry.textBlock(lazyText:() -> String) =
	TextBlock(lazyText()).also { content += it }
@MarkdownDsl
fun IDslEntry.mainHeading(text:String) =
	MainHeading(text).also { content += it }
@MarkdownDsl
fun IDslEntry.subHeading(text:String) =
	SubHeading(text).also { content += it }
@MarkdownDsl
fun IDslEntry.h1(text:String) =
	Heading1(text).also { content += it }
@MarkdownDsl
fun IDslEntry.h2(text:String) =
	Heading2(text).also { content += it }
@MarkdownDsl
fun IDslEntry.h3(text:String) =
	Heading3(text).also { content += it }
@MarkdownDsl
fun IDslEntry.h4(text:String) =
	Heading4(text).also { content += it }
@MarkdownDsl
fun IDslEntry.h5(text:String) =
	Heading5(text).also { content += it }
@MarkdownDsl
fun IDslEntry.h6(text:String) =
	Heading6(text).also { content += it }
@MarkdownDsl
fun IDslEntry.hr() =
	HorizontalLine.also { content += it }
@MarkdownDsl
inline fun IDslEntry.list(block:List.() -> Unit) =
	List().apply(block).also { content += it }
@MarkdownDsl
@MarkdownExtendedFeature
inline fun IDslEntry.def(title:String, block:Definition.() -> Unit) =
	Definition(title).apply(block).also { content += it }
@MarkdownDsl
inline fun IDslEntry.table(block:Table.() -> Unit) =
	Table().apply(block).also { content += it }
@MarkdownDsl
inline fun IDslEntry.blockQueue(block:BlockQuote.() -> Unit) =
	BlockQuote().apply(block).also { content += it }
@MarkdownDsl
@MarkdownExtendedFeature
inline fun IDslEntry.indentedBlock(block:IndentedBlock.() -> Unit) =
	IndentedBlock().apply(block).also { content += it }
@MarkdownDsl
@MarkdownExtendedFeature
inline fun IDslEntry.sideBlock(block:SideBlock.() -> Unit) =
	SideBlock().apply(block).also { content += it }
@MarkdownDsl
inline fun IDslEntry.codeFence(language:String, lazyText:() -> String) =
	CodeFence(language, lazyText()).also { content += it }
@MarkdownDsl
inline fun IDslEntry.multilineMath(lazyText:() -> String) =
	MultilineMath(lazyText()).also { content += it }
@MarkdownDsl
@MarkdownExtendedFeature
inline fun IDslEntry.admonition(qualifier:AdmonitionQualifier, title:String = "",
	type:AdmonitionType = AdmonitionType.Normal, block:Admonition.() -> Unit) =
	Admonition(qualifier, title, type).apply(block).also { content += it }
@MarkdownDsl
@MarkdownExtendedFeature
fun IDslEntry.import(url:String) =
	Import(url).also { content += it }
@MarkdownDsl
@MarkdownExtendedFeature
fun IDslEntry.macros(name:String) =
	Macros(name).also { content += it }
@MarkdownDsl
@MarkdownExtendedFeature
inline fun IDslEntry.macrosSnippet(name:String, block:MacrosSnippet.() -> Unit) =
	MacrosSnippet(name).apply(block).also { content += it }
@MarkdownDsl
@MarkdownExtendedFeature
infix fun <T : WithAttributes> T.with(attributes:AttributeGroup) =
	apply { this.attributes = attributes }
@MarkdownDsl
inline fun List.ol(order:String, text:String, block:OrderedListNode.() -> Unit = {}) =
	OrderedListNode(order, text).apply(block).also { nodes += it }
@MarkdownDsl
inline fun List.ul(text:String, block:UnorderedListNode.() -> Unit = {}) =
	UnorderedListNode(text).apply(block).also { nodes += it }
@MarkdownDsl
inline fun List.task(status:Boolean, text:String, block:TaskListNode.() -> Unit = {}) =
	TaskListNode(status, text).apply(block).also { nodes += it }
@MarkdownDsl
inline fun ListNode.ol(order:String, text:String, block:OrderedListNode.() -> Unit = {}) =
	OrderedListNode(order, text).apply(block).also { nodes += it }
@MarkdownDsl
inline fun ListNode.ul(text:String, block:UnorderedListNode.() -> Unit = {}) =
	UnorderedListNode(text).apply(block).also { nodes += it }
@MarkdownDsl
inline fun ListNode.task(status:Boolean, text:String, block:TaskListNode.() -> Unit = {}) =
	TaskListNode(status, text).apply(block).also { nodes += it }
@MarkdownDsl
@MarkdownExtendedFeature
inline fun Definition.node(title:String, block:DefinitionNode.() -> Unit) =
	DefinitionNode(title).apply(block).also { nodes += it }
@MarkdownDsl
inline fun Table.header(block:TableHeader.() -> Unit) =
	TableHeader().apply(block).also { header = it }
@MarkdownDsl
inline fun Table.row(block:TableRow.() -> Unit) =
	TableRow().apply(block).also { rows += it }
@MarkdownDsl
infix fun Table.columnSize(size:Int) =
	apply { columnSize = size }
@MarkdownDsl
fun TableHeader.column(text:String = config.emptyColumnText) =
	TableColumn(text).also { columns += it }
@MarkdownDsl
fun TableRow.column(text:String = config.emptyColumnText) =
	TableColumn(text).also { columns += it }
@MarkdownDsl
@MarkdownExtendedFeature
fun TableRow.rowSpan() = column(">")
@MarkdownDsl
@MarkdownExtendedFeature
fun TableRow.colSpan() = column("^")
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.attributes(vararg attributes:Attribute) = AttributeGroup(attributes.toSet())
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.id(name:String) = IdAttribute(name)
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.`class`(name:String) = ClassAttribute(name)
@MarkdownDsl
@MarkdownExtendedFeature
fun InlineDslEntry.prop(name:String, value:String) = PropertyAttribute(name to value)
