// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("unused")

package com.windea.breezeframework.dsl.markdown

import com.windea.breezeframework.dsl.markdown.MarkdownDsl.*

@MarkdownDslMarker
inline fun markdownDsl(block: Document.() -> Unit): Document {
	return Document().apply(block)
}

@MarkdownDslMarker
inline fun markdownDslConfig(block: Config.() -> Unit): Config {
	return Config.apply(block)
}


@MarkdownDslMarker
fun InlineDslEntry.b(text: CharSequence): BoldText {
	return BoldText(text)
}

@MarkdownDslMarker
fun InlineDslEntry.i(text: CharSequence): ItalicText {
	return ItalicText(text)
}

@MarkdownDslMarker
fun InlineDslEntry.s(text: CharSequence): StrokedText {
	return StrokedText(text)
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.u(text: CharSequence): UnderlinedText {
	return UnderlinedText(text)
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.em(text: CharSequence): HighlightText {
	return HighlightText(text)
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.sup(text: CharSequence): SuperscriptText {
	return SuperscriptText(text)
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.sub(text: CharSequence): SubscriptText {
	return SubscriptText(text)
}

@MarkdownDslMarker
fun InlineDslEntry.icon(name: String): Icon {
	return Icon(name)
}

@MarkdownDslMarker
fun InlineDslEntry.footNote(reference: String): FootNote {
	return FootNote(reference)
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.autoLink(url: String): AutoLink {
	return AutoLink(url)
}

@MarkdownDslMarker
fun InlineDslEntry.link(name: String, url: String, title: String? = null): InlineLink {
	return InlineLink(name, url, title)
}

@MarkdownDslMarker
fun InlineDslEntry.image(name: String = "", url: String, title: String? = null): InlineImageLink {
	return InlineImageLink(name, url, title)
}

@MarkdownDslMarker
fun InlineDslEntry.refLink(reference: String, name: String? = null): String {
	return ReferenceLink(reference, name).toString()
}

@MarkdownDslMarker
fun InlineDslEntry.refImage(reference: String, name: String? = null): String {
	return ReferenceImageLink(reference, name).toString()
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.wikiLink(name: String, url: String): WikiLink {
	return WikiLink(name, url)
}

@MarkdownDslMarker
fun InlineDslEntry.code(text: String): InlineCode {
	return InlineCode(text)
}

@MarkdownDslMarker
fun InlineDslEntry.math(text: String): InlineMath {
	return InlineMath(text)
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun Document.frontMatter(lazyText: () -> String): FrontMatter {
	return FrontMatter(lazyText()).also { frontMatter = it }
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun Document.toc(): Toc {
	return Toc().also { toc = it }
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun Document.abbr(reference: String, text: String): Abbreviation {
	return Abbreviation(reference, text).also { references += it }
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun Document.footNoteRef(reference: String, text: String): FootNoteReference {
	return FootNoteReference(reference, text).also { references += it }
}

@MarkdownDslMarker
fun Document.linkRef(reference: String, url: String, title: String? = null): LinkReference {
	return LinkReference(reference, url, title).also { references += it }
}

@MarkdownDslMarker
inline fun DslEntry.textBlock(lazyText: () -> String): TextBlock {
	return TextBlock(lazyText()).also { content += it }
}

@MarkdownDslMarker
fun DslEntry.mainHeading(text: String): MainHeading {
	return MainHeading(text).also { content += it }
}

@MarkdownDslMarker
fun DslEntry.subHeading(text: String): SubHeading {
	return SubHeading(text).also { content += it }
}

@MarkdownDslMarker
fun DslEntry.h1(text: String): Heading1 {
	return Heading1(text).also { content += it }
}

@MarkdownDslMarker
fun DslEntry.h2(text: String): Heading2 {
	return Heading2(text).also { content += it }
}

@MarkdownDslMarker
fun DslEntry.h3(text: String): Heading3 {
	return Heading3(text).also { content += it }
}

@MarkdownDslMarker
fun DslEntry.h4(text: String): Heading4 {
	return Heading4(text).also { content += it }
}

@MarkdownDslMarker
fun DslEntry.h5(text: String): Heading5 {
	return Heading5(text).also { content += it }
}

@MarkdownDslMarker
fun DslEntry.h6(text: String): Heading6 {
	return Heading6(text).also { content += it }
}

@MarkdownDslMarker
fun DslEntry.hr(): HorizontalLine {
	return HorizontalLine.also { content += it }
}

@MarkdownDslMarker
inline fun DslEntry.list(block: MarkdownDsl.List.() -> Unit): MarkdownDsl.List {
	return List().apply(block).also { content += it }
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.def(title: String, block: Definition.() -> Unit): Definition {
	return Definition(title).apply(block).also { content += it }
}

@MarkdownDslMarker
inline fun DslEntry.table(block: Table.() -> Unit): Table {
	return Table().apply(block).also { content += it }
}

@MarkdownDslMarker
inline fun DslEntry.blockQueue(block: BlockQuote.() -> Unit): BlockQuote {
	return BlockQuote().apply(block).also { content += it }
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.indentedBlock(block: IndentedBlock.() -> Unit): IndentedBlock {
	return IndentedBlock().apply(block).also { content += it }
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.sideBlock(block: SideBlock.() -> Unit): SideBlock {
	return SideBlock().apply(block).also { content += it }
}

@MarkdownDslMarker
inline fun DslEntry.codeFence(language: String, lazyText: () -> String): CodeFence {
	return CodeFence(language, lazyText()).also { content += it }
}

@MarkdownDslMarker
inline fun DslEntry.multilineMath(lazyText: () -> String): MultilineMath {
	return MultilineMath(lazyText()).also { content += it }
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.admonition(qualifier: AdmonitionQualifier, title: String = "", type: AdmonitionType = AdmonitionType.Normal, block: Admonition.() -> Unit): Admonition {
	return Admonition(qualifier, title, type).apply(block).also { content += it }
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun DslEntry.import(url: String): Import {
	return Import(url).also { content += it }
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun DslEntry.macros(name: String): Macros {
	return Macros(name).also { content += it }
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.macrosSnippet(name: String, block: MacrosSnippet.() -> Unit): MacrosSnippet {
	return MacrosSnippet(name).apply(block).also { content += it }
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
infix fun <T : WithAttributes> T.with(attributes: AttributeGroup): T {
	return apply { this.attributes = attributes }
}

@MarkdownDslMarker
inline fun MarkdownDsl.List.ol(order: String, text: String, block: OrderedListNode.() -> Unit = {}): OrderedListNode {
	return OrderedListNode(order, text).apply(block).also { nodes += it }
}

@MarkdownDslMarker
inline fun MarkdownDsl.List.ul(text: String, block: UnorderedListNode.() -> Unit = {}): UnorderedListNode {
	return UnorderedListNode(text).apply(block).also { nodes += it }
}

@MarkdownDslMarker
inline fun MarkdownDsl.List.task(status: Boolean, text: String, block: TaskListNode.() -> Unit = {}): TaskListNode {
	return TaskListNode(status, text).apply(block).also { nodes += it }
}

@MarkdownDslMarker
inline fun ListNode.ol(order: String, text: String, block: OrderedListNode.() -> Unit = {}): OrderedListNode {
	return OrderedListNode(order, text).apply(block).also { nodes += it }
}

@MarkdownDslMarker
inline fun ListNode.ul(text: String, block: UnorderedListNode.() -> Unit = {}): UnorderedListNode {
	return UnorderedListNode(text).apply(block).also { nodes += it }
}

@MarkdownDslMarker
inline fun ListNode.task(status: Boolean, text: String, block: TaskListNode.() -> Unit = {}): TaskListNode {
	return TaskListNode(status, text).apply(block).also { nodes += it }
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun Definition.node(title: String, block: DefinitionNode.() -> Unit): DefinitionNode {
	return DefinitionNode(title).apply(block).also { nodes += it }
}

@MarkdownDslMarker
inline fun Table.header(block: TableHeader.() -> Unit): TableHeader {
	return TableHeader().apply(block).also { header = it }
}

@MarkdownDslMarker
inline fun Table.row(block: TableRow.() -> Unit): TableRow {
	return TableRow().apply(block).also { rows += it }
}

@MarkdownDslMarker
infix fun Table.columnSize(size: Int): Table {
	return apply { columnSize = size }
}

@MarkdownDslMarker
fun TableHeader.column(text: String = Config.emptyColumnText): TableColumn {
	return TableColumn(text).also { columns += it }
}

@MarkdownDslMarker
fun TableRow.column(text: String = Config.emptyColumnText): TableColumn {
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

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.attributes(vararg attributes: Attribute): AttributeGroup {
	return AttributeGroup(attributes.toSet())
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.id(name: String): IdAttribute {
	return IdAttribute(name)
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.`class`(name: String): ClassAttribute {
	return ClassAttribute(name)
}

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.prop(name: String, value: String): PropertyAttribute {
	return PropertyAttribute(name to value)
}
