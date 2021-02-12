// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("CreoleDslExtensions")

package com.windea.breezeframework.dsl.creole

import com.windea.breezeframework.dsl.creole.CreoleDsl.*
import com.windea.breezeframework.dsl.creole.CreoleDsl.List

/**
 * 开始构建[CreoleDsl]。
 */
@CreoleDslMarker
inline fun creoleDsl(block: DslDocument.() -> Unit): DslDocument {
	return DslDocument().apply(block)
}

/**
 * 配置[CreoleDsl]。
 */
@CreoleDslMarker
inline fun creoleDslConfig(block: DslConfig.() -> Unit) {
	DslConfig.block()
}

/**
 * 创建一个[CreoleDsl.UnicodeText]。
 */
@CreoleDslMarker
fun InlineDslEntry.unicode(number: Int): UnicodeText {
	return UnicodeText(number.toString())
}

/**
 * 创建一个[CreoleDsl.BoldText]。
 */
@CreoleDslMarker
fun InlineDslEntry.b(text: CharSequence): BoldText {
	return BoldText(text)
}

/**
 * 创建一个[CreoleDsl.ItalicText]。
 */
@CreoleDslMarker
fun InlineDslEntry.i(text: CharSequence): ItalicText {
	return ItalicText(text)
}

/**
 * 创建一个[CreoleDsl.MonospacedText]。
 */
@CreoleDslMarker
fun InlineDslEntry.m(text: String): MonospacedText {
	return MonospacedText(text)
}

/**
 * 创建一个[CreoleDsl.StrokedText]。
 */
@CreoleDslMarker
fun InlineDslEntry.s(text: CharSequence): StrokedText {
	return StrokedText(text)
}

/**
 * 创建一个[CreoleDsl.UnderlineText]。
 */
@CreoleDslMarker
fun InlineDslEntry.u(text: CharSequence): UnderlineText {
	return UnderlineText(text)
}

/**
 * 创建一个[CreoleDsl.WavedText]。
 */
@CreoleDslMarker
fun InlineDslEntry.w(text: CharSequence): WavedText {
	return WavedText(text)
}

/**
 * 创建一个[CreoleDsl.EscapedText]。
 */
@CreoleDslMarker
fun InlineDslEntry.escaped(text: CharSequence): EscapedText {
	return EscapedText(text)
}

/**
 * 创建一个[CreoleDsl.Icon]。
 */
@CreoleDslMarker
fun InlineDslEntry.icon(name: String): Icon {
	return Icon(name)
}

/**
 * 创建一个[CreoleDsl.TextBlock]并注册。
 */
@CreoleDslMarker
inline fun DslEntry.textBlock(lazyText: () -> String): TextBlock {
	return TextBlock(lazyText()).also { content += it }
}

/**
 * 创建一个[CreoleDsl.Heading1]并注册。
 */
@CreoleDslMarker
fun DslEntry.h1(text: String): Heading1 {
	return Heading1(text).also { content += it }
}

/**
 * 创建一个[CreoleDsl.Heading2]并注册。
 */
@CreoleDslMarker
fun DslEntry.h2(text: String): Heading2 {
	return Heading2(text).also { content += it }
}

/**
 * 创建一个[CreoleDsl.Heading3]并注册。
 */
@CreoleDslMarker
fun DslEntry.h3(text: String): Heading3 {
	return Heading3(text).also { content += it }
}

/**
 * 创建一个[CreoleDsl.Heading4]并注册。
 */
@CreoleDslMarker
fun DslEntry.h4(text: String): Heading4 {
	return Heading4(text).also { content += it }
}

/**
 * 创建一个[CreoleDsl.HorizontalLine]并注册。
 */
@CreoleDslMarker
fun DslEntry.hr(type: HorizontalLineType = HorizontalLineType.Normal): HorizontalLine {
	return HorizontalLine(type).also { content += it }
}

/**
 * 创建一个[CreoleDsl.HorizontalTitle]并注册。
 */
@CreoleDslMarker
fun DslEntry.title(text: String, type: HorizontalLineType = HorizontalLineType.Normal): HorizontalTitle {
	return HorizontalTitle(text, type).also { content += it }
}

/**
 * 创建一个[CreoleDsl.List]并注册。
 */
@CreoleDslMarker
inline fun DslEntry.list(block: List.() -> Unit): List {
	return List().apply(block).also { content += it }
}

/**
 * 创建一个[CreoleDsl.Tree]并注册。
 */
@CreoleDslMarker
inline fun DslEntry.tree(title: String, block: Tree.() -> Unit): Tree {
	return Tree(title).apply(block).also { content += it }
}

/**
 * 创建一个[CreoleDsl.Table]并注册。
 */
@CreoleDslMarker
inline fun DslEntry.table(block: Table.() -> Unit): Table {
	return Table().apply(block).also { content += it }
}

/**
 * 创建一个[CreoleDsl.OrderedListNode]并注册。
 */
@CreoleDslMarker
inline fun List.ol(text: String, block: OrderedListNode.() -> Unit = {}): OrderedListNode {
	return OrderedListNode(text).apply(block).also { nodes += it }
}

/**
 * 创建一个[CreoleDsl.UnorderedListNode]并注册。
 */
@CreoleDslMarker
inline fun List.ul(text: String, block: UnorderedListNode.() -> Unit = {}): UnorderedListNode {
	return UnorderedListNode(text).apply(block).also { nodes += it }
}

/**
 * 创建一个[CreoleDsl.TextBlock]并注册。
 */
@CreoleDslMarker
inline fun ListNode.ol(text: String, block: OrderedListNode.() -> Unit = {}): OrderedListNode {
	return OrderedListNode(text).apply(block).also { nodes += it }
}

/**
 * 创建一个[CreoleDsl.UnorderedListNode]并注册。
 */
@CreoleDslMarker
inline fun ListNode.ul(text: String, block: UnorderedListNode.() -> Unit = {}): UnorderedListNode {
	return UnorderedListNode(text).apply(block).also { nodes += it }
}

/**
 * 创建一个[CreoleDsl.TreeNode]并注册。
 */
@CreoleDslMarker
inline fun Tree.node(text: String, block: TreeNode.() -> Unit = {}): TreeNode {
	return TreeNode(text).apply(block).also { nodes += it }
}

/**
 * 创建一个[CreoleDsl.TreeNode]并注册。
 */
@CreoleDslMarker
inline fun TreeNode.node(text: String, block: TreeNode.() -> Unit = {}): TreeNode {
	return TreeNode(text).apply(block).also { nodes += it }
}

/**
 * 创建一个[CreoleDsl.TableHeader]并注册。
 */
@CreoleDslMarker
inline fun Table.header(block: TableHeader.() -> Unit): TableHeader {
	return TableHeader().apply(block).also { header = it }
}

/**
 * 创建一个[CreoleDsl.TableRow]并注册。
 */
@CreoleDslMarker
inline fun Table.row(block: TableRow.() -> Unit): TableRow {
	return TableRow().apply(block).also { rows += it }
}

/**
 * 配置[CreoleDsl.Table]的列的数量。
 */
@CreoleDslMarker
infix fun Table.columnSize(size: Int): Table {
	return apply { this.columnSize = size }
}

/**
 * 创建一个[CreoleDsl.TableColumn]并注册。
 */
@CreoleDslMarker
fun TableHeader.column(text: String = DslConfig.emptyColumnText): TableColumn {
	return TableColumn(text).also { columns += it }
}

/**
 * 创建一个[CreoleDsl.TableColumn]并注册。
 */
@CreoleDslMarker
fun TableRow.column(text: String = DslConfig.emptyColumnText): TableColumn {
	return TableColumn(text).also { columns += it }
}

/**
 * 配置[CreoleDsl.TableColumn]的颜色。
 */
@CreoleDslMarker
infix fun TableColumn.color(color: String): TableColumn {
	return apply { this.color = color }
}
