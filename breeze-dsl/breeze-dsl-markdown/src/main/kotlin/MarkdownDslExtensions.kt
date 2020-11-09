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
fun InlineDslEntry.footNote(reference: String) = FootNote(reference)

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.autoLink(url: String) = AutoLink(url)

@MarkdownDslMarker
fun InlineDslEntry.link(name: String, url: String, title: String? = null) = InlineLink(name, url, title)

@MarkdownDslMarker
fun InlineDslEntry.image(name: String = "", url: String, title: String? = null) = InlineImageLink(name, url, title)

@MarkdownDslMarker
fun InlineDslEntry.refLink(reference: String, name: String? = null) = ReferenceLink(reference, name).toString()

@MarkdownDslMarker
fun InlineDslEntry.refImage(reference: String, name: String? = null) = ReferenceImageLink(reference, name).toString()

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.wikiLink(name: String, url: String) = WikiLink(name, url)

@MarkdownDslMarker
fun InlineDslEntry.code(text: String) = InlineCode(text)

@MarkdownDslMarker
fun InlineDslEntry.math(text: String) = InlineMath(text)

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun Document.frontMatter(lazyText: () -> String) = FrontMatter(lazyText()).also { frontMatter = it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun Document.toc() = Toc().also { toc = it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun Document.abbr(reference: String, text: String) = Abbreviation(reference, text).also { references += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun Document.footNoteRef(reference: String, text: String) = FootNoteReference(reference, text).also { references += it }

@MarkdownDslMarker
fun Document.linkRef(reference: String, url: String, title: String? = null) = LinkReference(reference, url, title).also { references += it }

@MarkdownDslMarker
inline fun DslEntry.textBlock(lazyText: () -> String) = TextBlock(lazyText()).also { content += it }

@MarkdownDslMarker
fun DslEntry.mainHeading(text: String) = MainHeading(text).also { content += it }

@MarkdownDslMarker
fun DslEntry.subHeading(text: String) = SubHeading(text).also { content += it }

@MarkdownDslMarker
fun DslEntry.h1(text: String) = Heading1(text).also { content += it }

@MarkdownDslMarker
fun DslEntry.h2(text: String) = Heading2(text).also { content += it }

@MarkdownDslMarker
fun DslEntry.h3(text: String) = Heading3(text).also { content += it }

@MarkdownDslMarker
fun DslEntry.h4(text: String) = Heading4(text).also { content += it }

@MarkdownDslMarker
fun DslEntry.h5(text: String) = Heading5(text).also { content += it }

@MarkdownDslMarker
fun DslEntry.h6(text: String) = Heading6(text).also { content += it }

@MarkdownDslMarker
fun DslEntry.hr() = HorizontalLine.also { content += it }

@MarkdownDslMarker
inline fun DslEntry.list(block: MarkdownDsl.List.() -> Unit) = List().apply(block).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.def(title: String, block: Definition.() -> Unit) = Definition(title).apply(block).also { content += it }

@MarkdownDslMarker
inline fun DslEntry.table(block: Table.() -> Unit) = Table().apply(block).also { content += it }

@MarkdownDslMarker
inline fun DslEntry.blockQueue(block: BlockQuote.() -> Unit) = BlockQuote().apply(block).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.indentedBlock(block: IndentedBlock.() -> Unit) = IndentedBlock().apply(block).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.sideBlock(block: SideBlock.() -> Unit) = SideBlock().apply(block).also { content += it }

@MarkdownDslMarker
inline fun DslEntry.codeFence(language: String, lazyText: () -> String) = CodeFence(language, lazyText()).also { content += it }

@MarkdownDslMarker
inline fun DslEntry.multilineMath(lazyText: () -> String) = MultilineMath(lazyText()).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.admonition(
	qualifier: AdmonitionQualifier,
	title: String = "",
	type: AdmonitionType = AdmonitionType.Normal,
	block: Admonition.() -> Unit,
) = Admonition(qualifier, title, type).apply(block).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun DslEntry.import(url: String) = Import(url).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun DslEntry.macros(name: String) = Macros(name).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun DslEntry.macrosSnippet(name: String, block: MacrosSnippet.() -> Unit) = MacrosSnippet(name).apply(block).also { content += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
infix fun <T : WithAttributes> T.with(attributes: AttributeGroup) = apply { this.attributes = attributes }

@MarkdownDslMarker
inline fun MarkdownDsl.List.ol(order: String, text: String, block: OrderedListNode.() -> Unit = {}) = OrderedListNode(order, text).apply(block).also { nodes += it }

@MarkdownDslMarker
inline fun MarkdownDsl.List.ul(text: String, block: UnorderedListNode.() -> Unit = {}) = UnorderedListNode(text).apply(block).also { nodes += it }

@MarkdownDslMarker
inline fun MarkdownDsl.List.task(status: Boolean, text: String, block: TaskListNode.() -> Unit = {}) = TaskListNode(status, text).apply(block).also { nodes += it }

@MarkdownDslMarker
inline fun ListNode.ol(order: String, text: String, block: OrderedListNode.() -> Unit = {}) = OrderedListNode(order, text).apply(block).also { nodes += it }

@MarkdownDslMarker
inline fun ListNode.ul(text: String, block: UnorderedListNode.() -> Unit = {}) = UnorderedListNode(text).apply(block).also { nodes += it }

@MarkdownDslMarker
inline fun ListNode.task(status: Boolean, text: String, block: TaskListNode.() -> Unit = {}) = TaskListNode(status, text).apply(block).also { nodes += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
inline fun Definition.node(title: String, block: DefinitionNode.() -> Unit) = DefinitionNode(title).apply(block).also { nodes += it }

@MarkdownDslMarker
inline fun Table.header(block: TableHeader.() -> Unit) = TableHeader().apply(block).also { header = it }

@MarkdownDslMarker
inline fun Table.row(block: TableRow.() -> Unit) = TableRow().apply(block).also { rows += it }

@MarkdownDslMarker
infix fun Table.columnSize(size: Int) = apply { columnSize = size }

@MarkdownDslMarker
fun TableHeader.column(text: String = Config.emptyColumnText) = TableColumn(text).also { columns += it }

@MarkdownDslMarker
fun TableRow.column(text: String = Config.emptyColumnText) = TableColumn(text).also { columns += it }

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun TableRow.rowSpan() = column(">")

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun TableRow.colSpan() = column("^")

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.attributes(vararg attributes: Attribute) = AttributeGroup(attributes.toSet())

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.id(name: String) = IdAttribute(name)

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.`class`(name: String) = ClassAttribute(name)

@MarkdownDslMarker
@MarkdownDslExtendedFeature
fun InlineDslEntry.prop(name: String, value: String) = PropertyAttribute(name to value)
