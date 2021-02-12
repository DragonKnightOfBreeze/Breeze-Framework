// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("MarkdownDslExtensions")

package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.dsl.markdown.MarkdownDsl.*
import com.windea.breezeframework.dsl.markdown.MarkdownDsl.List

/**
 * 开始构建[MarkdownDsl]。
 */
@MarkdownDslMarker
inline fun markdownDsl(block: DslDocument.() -> Unit): DslDocument {
	return DslDocument().apply(block)
}

/**
 * 配置[MarkdownDsl]。
 */
@MarkdownDslMarker
inline fun markdownDslConfig(block: DslConfig.() -> Unit): DslConfig {
	return DslConfig.apply(block)
}

/**
 * 创建一个[MarkdownDsl.BoldText]。
 */
@MarkdownDslMarker
fun InlineDslEntry.b(text: CharSequence): BoldText {
	return BoldText(text)
}

/**
 * 创建一个[MarkdownDsl.ItalicText]。
 */
@MarkdownDslMarker
fun InlineDslEntry.i(text: CharSequence): ItalicText {
	return ItalicText(text)
}

/**
 * 创建一个[MarkdownDsl.StrokedText]。
 */
@MarkdownDslMarker
fun InlineDslEntry.s(text: CharSequence): StrokedText {
	return StrokedText(text)
}

/**
 * 创建一个[MarkdownDsl.UnderlinedText]。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.u(text: CharSequence): UnderlinedText {
	return UnderlinedText(text)
}

/**
 * 创建一个[MarkdownDsl.HighlightText]。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.em(text: CharSequence): HighlightText {
	return HighlightText(text)
}

/**
 * 创建一个[MarkdownDsl.SuperscriptText]。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.sup(text: CharSequence): SuperscriptText {
	return SuperscriptText(text)
}

/**
 * 创建一个[MarkdownDsl.SubscriptText]。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.sub(text: CharSequence): SubscriptText {
	return SubscriptText(text)
}

/**
 * 创建一个[MarkdownDsl.Icon]。
 */
@MarkdownDslMarker
fun InlineDslEntry.icon(name: String): Icon {
	return Icon(name)
}

/**
 * 创建一个[MarkdownDsl.FootNote]。
 */
@MarkdownDslMarker
fun InlineDslEntry.footNote(reference: String): FootNote {
	return FootNote(reference)
}

/**
 * 创建一个[MarkdownDsl.AutoLink]。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.autoLink(url: String): AutoLink {
	return AutoLink(url)
}

/**
 * 创建一个[MarkdownDsl.InlineLink]。
 */
@MarkdownDslMarker
fun InlineDslEntry.link(name: String, url: String, title: String? = null): InlineLink {
	return InlineLink(name, url, title)
}

/**
 * 创建一个[MarkdownDsl.InlineImageLink]。
 */
@MarkdownDslMarker
fun InlineDslEntry.image(name: String = "", url: String, title: String? = null): InlineImageLink {
	return InlineImageLink(name, url, title)
}

/**
 * 创建一个[MarkdownDsl.ReferenceLink]。
 */
@MarkdownDslMarker
fun InlineDslEntry.refLink(reference: String, name: String? = null): ReferenceLink {
	return ReferenceLink(reference, name)
}

/**
 * 创建一个[MarkdownDsl.ReferenceImageLink]。
 */
@MarkdownDslMarker
fun InlineDslEntry.refImage(reference: String, name: String? = null): ReferenceImageLink {
	return ReferenceImageLink(reference, name)
}

/**
 * 创建一个[MarkdownDsl.WikiLink]。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.wikiLink(name: String, url: String): WikiLink {
	return WikiLink(name, url)
}

/**
 * 创建一个[MarkdownDsl.InlineCode]。
 */
@MarkdownDslMarker
fun InlineDslEntry.code(text: String): InlineCode {
	return InlineCode(text)
}

/**
 * 创建一个[MarkdownDsl.InlineMath]。
 */
@MarkdownDslMarker
fun InlineDslEntry.math(text: String): InlineMath {
	return InlineMath(text)
}

/**
 * 创建一个[MarkdownDsl.FrontMatter]并注册。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslDocument.frontMatter(lazyText: () -> String): FrontMatter {
	return FrontMatter(lazyText()).also { frontMatter = it }
}

/**
 * 创建一个[MarkdownDsl.Toc]并注册。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun DslDocument.toc(): Toc {
	return Toc().also { toc = it }
}

/**
 * 创建一个[MarkdownDsl.Abbreviation]并注册。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun DslDocument.abbr(reference: String, text: String): Abbreviation {
	return Abbreviation(reference, text).also { references += it }
}

/**
 * 创建一个[MarkdownDsl.FootNoteReference]并注册。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun DslDocument.footNoteRef(reference: String, text: String): FootNoteReference {
	return FootNoteReference(reference, text).also { references += it }
}


/**
 * 创建一个[MarkdownDsl.LinkReference]并注册。
 */
@MarkdownDslMarker
fun DslDocument.linkRef(reference: String, url: String, title: String? = null): LinkReference {
	return LinkReference(reference, url, title).also { references += it }
}

/**
 * 创建一个[MarkdownDsl.TextBlock]并注册。
 */
@MarkdownDslMarker
inline fun DslEntry.textBlock(lazyText: () -> String): TextBlock {
	return TextBlock(lazyText()).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.MainHeading]并注册。
 */
@MarkdownDslMarker
fun DslEntry.mainHeading(text: String): MainHeading {
	return MainHeading(text).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.SubHeading]并注册。
 */
@MarkdownDslMarker
fun DslEntry.subHeading(text: String): SubHeading {
	return SubHeading(text).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.Heading1]并注册。
 */
@MarkdownDslMarker
fun DslEntry.h1(text: String): Heading1 {
	return Heading1(text).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.Heading2]并注册。
 */
@MarkdownDslMarker
fun DslEntry.h2(text: String): Heading2 {
	return Heading2(text).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.Heading3]并注册。
 */
@MarkdownDslMarker
fun DslEntry.h3(text: String): Heading3 {
	return Heading3(text).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.Heading4]并注册。
 */
@MarkdownDslMarker
fun DslEntry.h4(text: String): Heading4 {
	return Heading4(text).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.Heading5]并注册。
 */
@MarkdownDslMarker
fun DslEntry.h5(text: String): Heading5 {
	return Heading5(text).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.Heading6]并注册。
 */
@MarkdownDslMarker
fun DslEntry.h6(text: String): Heading6 {
	return Heading6(text).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.HorizontalLine]并注册。
 */
@MarkdownDslMarker
fun DslEntry.hr(): HorizontalLine {
	return HorizontalLine.also { content += it }
}

/**
 * 创建一个[MarkdownDsl.List]并注册。
 */
@MarkdownDslMarker
inline fun DslEntry.list(block: MarkdownDsl.List.() -> Unit): MarkdownDsl.List {
	return List().apply(block).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.Definition]并注册。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.def(title: String, block: Definition.() -> Unit): Definition {
	return Definition(title).apply(block).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.Table]并注册。
 */
@MarkdownDslMarker
inline fun DslEntry.table(block: Table.() -> Unit): Table {
	return Table().apply(block).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.BlockQuote]并注册。
 */
@MarkdownDslMarker
inline fun DslEntry.blockQueue(block: BlockQuote.() -> Unit): BlockQuote {
	return BlockQuote().apply(block).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.IndentedBlock]并注册。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.indentedBlock(block: IndentedBlock.() -> Unit): IndentedBlock {
	return IndentedBlock().apply(block).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.SideBlock]并注册。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.sideBlock(block: SideBlock.() -> Unit): SideBlock {
	return SideBlock().apply(block).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.CodeFence]并注册。
 */
@MarkdownDslMarker
inline fun DslEntry.codeFence(language: String, lazyText: () -> String): CodeFence {
	return CodeFence(language, lazyText()).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.MultilineMath]并注册。
 */
@MarkdownDslMarker
inline fun DslEntry.multilineMath(lazyText: () -> String): MultilineMath {
	return MultilineMath(lazyText()).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.Admonition]并注册。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.admonition(qualifier: AdmonitionQualifier, title: String = "", type: AdmonitionType = AdmonitionType.Normal, block: Admonition.() -> Unit): Admonition {
	return Admonition(qualifier, title, type).apply(block).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.Import]并注册。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun DslEntry.import(url: String): Import {
	return Import(url).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.Macros]并注册。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun DslEntry.macros(name: String): Macros {
	return Macros(name).also { content += it }
}

/**
 * 创建一个[MarkdownDsl.MacrosSnippet]并注册。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.macrosSnippet(name: String, block: MacrosSnippet.() -> Unit): MacrosSnippet {
	return MacrosSnippet(name).apply(block).also { content += it }
}

/**
 * 绑定[MarkdownDsl.AttributeGroup]。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
infix fun <T : WithAttributes> T.with(attributes: AttributeGroup): T {
	return apply { this.attributes = attributes }
}

/**
 * 创建一个[MarkdownDsl.OrderedListNode]并注册。
 */
@MarkdownDslMarker
inline fun MarkdownDsl.List.ol(order: String, text: String, block: OrderedListNode.() -> Unit = {}): OrderedListNode {
	return OrderedListNode(order, text).apply(block).also { nodes += it }
}

/**
 * 创建一个[MarkdownDsl.UnorderedListNode]并注册。
 */
@MarkdownDslMarker
inline fun MarkdownDsl.List.ul(text: String, block: UnorderedListNode.() -> Unit = {}): UnorderedListNode {
	return UnorderedListNode(text).apply(block).also { nodes += it }
}

/**
 * 创建一个[MarkdownDsl.TaskListNode]并注册。
 */
@MarkdownDslMarker
inline fun MarkdownDsl.List.task(status: Boolean, text: String, block: TaskListNode.() -> Unit = {}): TaskListNode {
	return TaskListNode(status, text).apply(block).also { nodes += it }
}

/**
 * 创建一个[MarkdownDsl.OrderedListNode]并注册。
 */
@MarkdownDslMarker
inline fun ListNode.ol(order: String, text: String, block: OrderedListNode.() -> Unit = {}): OrderedListNode {
	return OrderedListNode(order, text).apply(block).also { nodes += it }
}

/**
 * 创建一个[MarkdownDsl.UnorderedListNode]并注册。
 */
@MarkdownDslMarker
inline fun ListNode.ul(text: String, block: UnorderedListNode.() -> Unit = {}): UnorderedListNode {
	return UnorderedListNode(text).apply(block).also { nodes += it }
}

/**
 * 创建一个[MarkdownDsl.TaskListNode]并注册。
 */
@MarkdownDslMarker
inline fun ListNode.task(status: Boolean, text: String, block: TaskListNode.() -> Unit = {}): TaskListNode {
	return TaskListNode(status, text).apply(block).also { nodes += it }
}

/**
 * 创建一个[MarkdownDsl.DefinitionNode]并注册。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun Definition.node(title: String, block: DefinitionNode.() -> Unit): DefinitionNode {
	return DefinitionNode(title).apply(block).also { nodes += it }
}

/**
 * 创建一个[MarkdownDsl.TableHeader]并注册。
 */
@MarkdownDslMarker
inline fun Table.header(block: TableHeader.() -> Unit): TableHeader {
	return TableHeader().apply(block).also { header = it }
}

/**
 * 创建一个[MarkdownDsl.TableRow]并注册。
 */
@MarkdownDslMarker
inline fun Table.row(block: TableRow.() -> Unit): TableRow {
	return TableRow().apply(block).also { rows += it }
}

/**
 * 配置[MarkdownDsl.Table]的列数。
 */
@MarkdownDslMarker
infix fun Table.columnSize(size: Int): Table {
	return apply { columnSize = size }
}

/**
 * 创建一个[MarkdownDsl.TableColumn]并注册。
 */
@MarkdownDslMarker
fun TableHeader.column(text: String = DslConfig.emptyColumnText): TableColumn {
	return TableColumn(text).also { columns += it }
}

/**
 * 创建一个[MarkdownDsl.TableColumn]并注册。
 */
@MarkdownDslMarker
fun TableRow.column(text: String = DslConfig.emptyColumnText): TableColumn {
	return TableColumn(text).also { columns += it }
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun TableRow.rowSpan(): TableColumn {
	return column(">")
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun TableRow.colSpan(): TableColumn {
	return column("^")
}

/**
 * 创建一个[MarkdownDsl.AttributeGroup]。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.attributes(vararg attributes: Attribute): AttributeGroup {
	return AttributeGroup(attributes.toSet())
}

/**
 * 创建一个[MarkdownDsl.IdAttribute]。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.id(name: String): IdAttribute {
	return IdAttribute(name)
}

/**
 * 创建一个[MarkdownDsl.ClassAttribute]。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.`class`(name: String): ClassAttribute {
	return ClassAttribute(name)
}

/**
 * 创建一个[MarkdownDsl.PropertyAttribute]。
 */
@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.prop(name: String, value: String): PropertyAttribute {
	return PropertyAttribute(name to value)
}
