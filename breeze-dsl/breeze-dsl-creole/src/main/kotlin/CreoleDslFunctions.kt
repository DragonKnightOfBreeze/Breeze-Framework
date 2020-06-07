@file:Suppress("unused")

package com.windea.breezeframework.dsl.creole

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.creole.Creole.*
import com.windea.breezeframework.dsl.creole.Creole.Companion.config
import com.windea.breezeframework.dsl.creole.Creole.List
@CreoleDsl
inline fun creole(block:Document.() -> Unit) = Document().apply(block)
@CreoleDsl
inline fun creoleConfig(block:Config.() -> Unit) = config.block()
@CreoleDsl
fun InlineDslEntry.unicode(number:Int) = UnicodeText(number.toString())
@CreoleDsl
fun InlineDslEntry.b(text:CharSequence) = BoldText(text)
@CreoleDsl
fun InlineDslEntry.i(text:CharSequence) = ItalicText(text)
@CreoleDsl
fun InlineDslEntry.m(text:String) = MonospacedText(text)
@CreoleDsl
fun InlineDslEntry.s(text:CharSequence) = StrokedText(text)
@CreoleDsl
fun InlineDslEntry.u(text:CharSequence) = UnderlineText(text)
@CreoleDsl
fun InlineDslEntry.w(text:CharSequence) = WavedText(text)
@CreoleDsl
fun InlineDslEntry.escaped(text:CharSequence) = EscapedText(text)
@CreoleDsl
fun InlineDslEntry.icon(name:String) = Icon(name)
@CreoleDsl
inline fun IDslEntry.textBlock(lazyText:() -> String) =
	TextBlock(lazyText()).also { content += it }
@CreoleDsl
fun IDslEntry.h1(text:String) =
	Heading1(text).also { content += it }
@CreoleDsl
fun IDslEntry.h2(text:String) =
	Heading2(text).also { content += it }
@CreoleDsl
fun IDslEntry.h3(text:String) =
	Heading3(text).also { content += it }
@CreoleDsl
fun IDslEntry.h4(text:String) =
	Heading4(text).also { content += it }
@CreoleDsl
fun IDslEntry.hr(type:HorizontalLineType = HorizontalLineType.Normal) =
	HorizontalLine(type).also { content += it }
@CreoleDsl
fun IDslEntry.title(text:String, type:HorizontalLineType = HorizontalLineType.Normal) =
	HorizontalTitle(text, type).also { content += it }
@CreoleDsl
inline fun IDslEntry.list(block:List.() -> Unit) =
	List().apply(block).also { content += it }
@CreoleDsl
inline fun IDslEntry.tree(title:String, block:Tree.() -> Unit) =
	Tree(title).apply(block).also { content += it }
@CreoleDsl
inline fun IDslEntry.table(block:Table.() -> Unit) =
	Table().apply(block).also { content += it }
@CreoleDsl
inline fun List.ol(text:String, block:OrderedListNode.() -> Unit = {}) =
	OrderedListNode(text).apply(block).also { nodes += it }
@CreoleDsl
inline fun List.ul(text:String, block:UnorderedListNode.() -> Unit = {}) =
	UnorderedListNode(text).apply(block).also { nodes += it }
@CreoleDsl
inline fun ListNode.ol(text:String, block:OrderedListNode.() -> Unit = {}) =
	OrderedListNode(text).apply(block).also { nodes += it }
@CreoleDsl
inline fun ListNode.ul(text:String, block:UnorderedListNode.() -> Unit = {}) =
	UnorderedListNode(text).apply(block).also { nodes += it }
@CreoleDsl
inline fun Tree.node(text:String, block:TreeNode.() -> Unit = {}) =
	TreeNode(text).apply(block).also { nodes += it }
@CreoleDsl
inline fun TreeNode.node(text:String, block:TreeNode.() -> Unit = {}) =
	TreeNode(text).apply(block).also { nodes += it }
@CreoleDsl
inline fun Table.header(block:TableHeader.() -> Unit) =
	TableHeader().apply(block).also { header = it }
@CreoleDsl
inline fun Table.row(block:TableRow.() -> Unit) =
	TableRow().apply(block).also { rows += it }
@CreoleDsl
infix fun Table.columnSize(size:Int) =
	apply { this.columnSize = size }
@CreoleDsl
fun TableHeader.column(text:String = config.emptyColumnText) =
	TableColumn(text).also { columns += it }
@CreoleDsl
fun TableRow.column(text:String = config.emptyColumnText) =
	TableColumn(text).also { columns += it }
@CreoleDsl
infix fun TableColumn.color(color:String) =
	apply { this.color = color }
