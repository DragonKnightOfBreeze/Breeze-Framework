/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

@file:Suppress("unused")

package com.windea.breezeframework.dsl.creole

import com.windea.breezeframework.dsl.creole.CreoleDslDefinitions.*
import com.windea.breezeframework.dsl.creole.CreoleDslDefinitions.List

@CreoleDslMarker
inline fun creoleDsl(block: CreoleDsl.() -> Unit) = CreoleDsl().apply(block)

@CreoleDslMarker
inline fun creoleDslConfig(block: CreoleDslConfig.() -> Unit) = CreoleDslConfig.block()


@CreoleDslMarker
fun InlineDslEntry.unicode(number: Int) = UnicodeText(number.toString())

@CreoleDslMarker
fun InlineDslEntry.b(text: CharSequence) = BoldText(text)

@CreoleDslMarker
fun InlineDslEntry.i(text: CharSequence) = ItalicText(text)

@CreoleDslMarker
fun InlineDslEntry.m(text: String) = MonospacedText(text)

@CreoleDslMarker
fun InlineDslEntry.s(text: CharSequence) = StrokedText(text)

@CreoleDslMarker
fun InlineDslEntry.u(text: CharSequence) = UnderlineText(text)

@CreoleDslMarker
fun InlineDslEntry.w(text: CharSequence) = WavedText(text)

@CreoleDslMarker
fun InlineDslEntry.escaped(text: CharSequence) = EscapedText(text)

@CreoleDslMarker
fun InlineDslEntry.icon(name: String) = Icon(name)

@CreoleDslMarker
inline fun IDslEntry.textBlock(lazyText: () -> String) = TextBlock(lazyText()).also { content += it }

@CreoleDslMarker
fun IDslEntry.h1(text: String) = Heading1(text).also { content += it }

@CreoleDslMarker
fun IDslEntry.h2(text: String) = Heading2(text).also { content += it }

@CreoleDslMarker
fun IDslEntry.h3(text: String) = Heading3(text).also { content += it }

@CreoleDslMarker
fun IDslEntry.h4(text: String) = Heading4(text).also { content += it }

@CreoleDslMarker
fun IDslEntry.hr(type: HorizontalLineType = HorizontalLineType.Normal) = HorizontalLine(type).also { content += it }

@CreoleDslMarker
fun IDslEntry.title(text: String, type: HorizontalLineType = HorizontalLineType.Normal) = HorizontalTitle(text, type).also { content += it }

@CreoleDslMarker
inline fun IDslEntry.list(block: List.() -> Unit) = List().apply(block).also { content += it }

@CreoleDslMarker
inline fun IDslEntry.tree(title: String, block: Tree.() -> Unit) = Tree(title).apply(block).also { content += it }

@CreoleDslMarker
inline fun IDslEntry.table(block: Table.() -> Unit) = Table().apply(block).also { content += it }

@CreoleDslMarker
inline fun List.ol(text: String, block: OrderedListNode.() -> Unit = {}) = OrderedListNode(text).apply(block).also { nodes += it }

@CreoleDslMarker
inline fun List.ul(text: String, block: UnorderedListNode.() -> Unit = {}) = UnorderedListNode(text).apply(block).also { nodes += it }

@CreoleDslMarker
inline fun ListNode.ol(text: String, block: OrderedListNode.() -> Unit = {}) = OrderedListNode(text).apply(block).also { nodes += it }

@CreoleDslMarker
inline fun ListNode.ul(text: String, block: UnorderedListNode.() -> Unit = {}) = UnorderedListNode(text).apply(block).also { nodes += it }

@CreoleDslMarker
inline fun Tree.node(text: String, block: TreeNode.() -> Unit = {}) = TreeNode(text).apply(block).also { nodes += it }

@CreoleDslMarker
inline fun TreeNode.node(text: String, block: TreeNode.() -> Unit = {}) = TreeNode(text).apply(block).also { nodes += it }

@CreoleDslMarker
inline fun Table.header(block: TableHeader.() -> Unit) = TableHeader().apply(block).also { header = it }

@CreoleDslMarker
inline fun Table.row(block: TableRow.() -> Unit) = TableRow().apply(block).also { rows += it }

@CreoleDslMarker
infix fun Table.columnSize(size: Int) = apply { this.columnSize = size }

@CreoleDslMarker
fun TableHeader.column(text: String = CreoleDslConfig.emptyColumnText) = TableColumn(text).also { columns += it }

@CreoleDslMarker
fun TableRow.column(text: String = CreoleDslConfig.emptyColumnText) = TableColumn(text).also { columns += it }

@CreoleDslMarker
infix fun TableColumn.color(color: String) = apply { this.color = color }
