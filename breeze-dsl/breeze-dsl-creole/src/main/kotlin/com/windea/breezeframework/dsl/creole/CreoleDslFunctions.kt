@file:Suppress("unused")

package com.windea.breezeframework.dsl.creole

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.creole.Creole.*
import com.windea.breezeframework.dsl.creole.Creole.Companion.config
import com.windea.breezeframework.dsl.creole.Creole.List

@TopDslFunction
@CreoleDsl
inline fun creole(block:Document.() -> Unit) = Document().apply(block)

@TopDslFunction
@CreoleDsl
inline fun creoleConfig(block:Config.() -> Unit) = config.block()


@InlineDslFunction
@CreoleDsl
fun CreoleDslInlineEntry.unicode(number:Int) = UnicodeText(number.toString())

@InlineDslFunction
@CreoleDsl
fun CreoleDslInlineEntry.b(text:CharSequence) = BoldText(text)

@InlineDslFunction
@CreoleDsl
fun CreoleDslInlineEntry.i(text:CharSequence) = ItalicText(text)

@InlineDslFunction
@CreoleDsl
fun CreoleDslInlineEntry.m(text:String) = MonospacedText(text)

@InlineDslFunction
@CreoleDsl
fun CreoleDslInlineEntry.s(text:CharSequence) = StrokedText(text)

@InlineDslFunction
@CreoleDsl
fun CreoleDslInlineEntry.u(text:CharSequence) = UnderlineText(text)

@InlineDslFunction
@CreoleDsl
fun CreoleDslInlineEntry.w(text:CharSequence) = WavedText(text)

@InlineDslFunction
@CreoleDsl
fun CreoleDslInlineEntry.escaped(text:CharSequence) = EscapedText(text)

@InlineDslFunction
@CreoleDsl
fun CreoleDslInlineEntry.icon(name:String) = Icon(name)


@DslFunction
@CreoleDsl
inline fun CreoleDslEntry.textBlock(lazyText:() -> String) =
	TextBlock(lazyText()).also { content += it }

@DslFunction
@CreoleDsl
fun CreoleDslEntry.h1(text:String) =
	Heading1(text).also { content += it }

@DslFunction
@CreoleDsl
fun CreoleDslEntry.h2(text:String) =
	Heading2(text).also { content += it }

@DslFunction
@CreoleDsl
fun CreoleDslEntry.h3(text:String) =
	Heading3(text).also { content += it }

@DslFunction
@CreoleDsl
fun CreoleDslEntry.h4(text:String) =
	Heading4(text).also { content += it }

@DslFunction
@CreoleDsl
fun CreoleDslEntry.hr(type:HorizontalLineType = HorizontalLineType.Normal) =
	HorizontalLine(type).also { content += it }

@DslFunction
@CreoleDsl
fun CreoleDslEntry.title(text:String, type:HorizontalLineType = HorizontalLineType.Normal) =
	HorizontalTitle(text, type).also { content += it }

@DslFunction
@CreoleDsl
inline fun CreoleDslEntry.list(block:Creole.List.() -> Unit) =
	List().apply(block).also { content += it }

@DslFunction
@CreoleDsl
inline fun CreoleDslEntry.tree(title:String, block:Tree.() -> Unit) =
	Tree(title).apply(block).also { content += it }

@DslFunction
@CreoleDsl
inline fun CreoleDslEntry.table(block:Table.() -> Unit) =
	Table().apply(block).also { content += it }

@DslFunction
@CreoleDsl
inline fun Creole.List.ol(text:String, block:OrderedListNode.() -> Unit = {}) =
	OrderedListNode(text).apply(block).also { nodes += it }

@DslFunction
@CreoleDsl
inline fun Creole.List.ul(text:String, block:UnorderedListNode.() -> Unit = {}) =
	UnorderedListNode(text).apply(block).also { nodes += it }

@DslFunction
@CreoleDsl
inline fun ListNode.ol(text:String, block:OrderedListNode.() -> Unit = {}) =
	OrderedListNode(text).apply(block).also { nodes += it }

@DslFunction
@CreoleDsl
inline fun ListNode.ul(text:String, block:UnorderedListNode.() -> Unit = {}) =
	UnorderedListNode(text).apply(block).also { nodes += it }

@DslFunction
@CreoleDsl
inline fun Tree.node(text:String, block:TreeNode.() -> Unit = {}) =
	TreeNode(text).apply(block).also { nodes += it }

@DslFunction
@CreoleDsl
inline fun TreeNode.node(text:String, block:TreeNode.() -> Unit = {}) =
	TreeNode(text).apply(block).also { nodes += it }

@DslFunction
@CreoleDsl
inline fun Table.header(block:TableHeader.() -> Unit) =
	TableHeader().apply(block).also { header = it }

@DslFunction
@CreoleDsl
inline fun Table.row(block:TableRow.() -> Unit) =
	TableRow().apply(block).also { rows += it }

@DslFunction
@CreoleDsl
infix fun Table.columnSize(size:Int) =
	apply { this.columnSize = size }

@DslFunction
@CreoleDsl
fun TableHeader.column(text:String = config.emptyColumnText) =
	TableColumn(text).also { columns += it }

@DslFunction
@CreoleDsl
fun TableRow.column(text:String = config.emptyColumnText) =
	TableColumn(text).also { columns += it }

@DslFunction
@CreoleDsl
infix fun TableColumn.color(color:String) =
	apply { this.color = color }
