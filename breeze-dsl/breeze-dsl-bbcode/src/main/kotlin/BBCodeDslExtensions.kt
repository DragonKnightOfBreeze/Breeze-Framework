// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("BBCodeDslExtensions")

package com.windea.breezeframework.dsl.bbcode

import com.windea.breezeframework.dsl.bbcode.BBCodeDsl.*
import com.windea.breezeframework.dsl.bbcode.BBCodeDsl.List

/**
 * 开始构建[BBCodeDsl]。
 */
@BBCodeDslMarker
inline fun bbcode(block: DslDocument.() -> Unit) = DslDocument().apply(block)

/**
 * 创建一个[BBCodeDsl.BoldText]。
 */
@BBCodeDslMarker
fun InlineDslEntry.b(text: CharSequence) = BoldText(text)

/**
 * 创建一个[BBCodeDsl.ItalicText]。
 */
@BBCodeDslMarker
fun InlineDslEntry.i(text: CharSequence) = ItalicText(text)

/**
 * 创建一个[BBCodeDsl.UnderlinedText]。
 */
@BBCodeDslMarker
fun InlineDslEntry.u(text: CharSequence) = UnderlinedText(text)

/**
 * 创建一个[BBCodeDsl.StrikeText]。
 */
@BBCodeDslMarker
fun InlineDslEntry.strike(text: CharSequence) = StrikeText(text)

/**
 * 创建一个[BBCodeDsl.SpoilerText]。
 */
@BBCodeDslMarker
fun InlineDslEntry.spoiler(text: CharSequence) = SpoilerText(text)

/**
 * 创建一个[BBCodeDsl.NoParseText]。
 */
@BBCodeDslMarker
fun InlineDslEntry.noparse(text: CharSequence) = NoParseText(text)

/**
 * 创建一个[BBCodeDsl.LeftText]。
 */
@BBCodeDslMarker
fun InlineDslEntry.left(text: CharSequence) = LeftText(text)

/**
 * 创建一个[BBCodeDsl.CenterText]。
 */
@BBCodeDslMarker
fun InlineDslEntry.center(text: CharSequence) = CenterText(text)

/**
 * 创建一个[BBCodeDsl.RightText]。
 */
@BBCodeDslMarker
fun InlineDslEntry.right(text: CharSequence) = RightText(text)

/**
 * 创建一个[BBCodeDsl.Code]。
 */
@BBCodeDslMarker
fun InlineDslEntry.code(text: String) = Code(text)

/**
 * 创建一个[BBCodeDsl.StyledText]。
 */
@BBCodeDslMarker
fun InlineDslEntry.size(size: String, text: CharSequence) = StyledText(size, null, text)

/**
 * 创建一个[BBCodeDsl.StyledText]。
 */
@BBCodeDslMarker
fun InlineDslEntry.color(color: String, text: CharSequence) = StyledText(null, color, text)

/**
 * 创建一个[BBCodeDsl.BoldText]。
 */
@BBCodeDslMarker
fun InlineDslEntry.style(size: String, color: String, text: CharSequence) = StyledText(size, color, text)

/**
 * 创建一个[BBCodeDsl.BoldText]。
 */
@BBCodeDslMarker
fun InlineDslEntry.youtube(text: String) = YoutubeVideo(text)

/**
 * 创建一个[BBCodeDsl.Link]。
 */
@BBCodeDslMarker
fun InlineDslEntry.link(text: String) = Link(null, text)

/**
 * 创建一个[BBCodeDsl.Link]。
 */
@BBCodeDslMarker
fun InlineDslEntry.link(url: String, text: String) = Link(url, text)

/**
 * 创建一个[BBCodeDsl.Image]。
 */
@BBCodeDslMarker
fun InlineDslEntry.image(text: String) = Image(null, null, text)

/**
 * 创建一个[BBCodeDsl.Image]。
 */
@BBCodeDslMarker
fun InlineDslEntry.image(width: String, height: String, text: String) = Image(width, height, text)

/**
 * 创建一个[BBCodeDsl.Heading1]。
 */
@BBCodeDslMarker
fun InlineDslEntry.h1(text: CharSequence) = Heading1(text)

/**
 * 创建一个[BBCodeDsl.Heading2]。
 */
@BBCodeDslMarker
fun InlineDslEntry.h2(text: CharSequence) = Heading2(text)

/**
 * 创建一个[BBCodeDsl.Heading3]。
 */
@BBCodeDslMarker
fun InlineDslEntry.h3(text: CharSequence) = Heading3(text)

/**
 * 创建一个[BBCodeDsl.Heading4]。
 */
@BBCodeDslMarker
fun InlineDslEntry.h4(text: CharSequence) = Heading4(text)

/**
 * 创建一个[BBCodeDsl.List]。
 */
@BBCodeDslMarker
inline fun InlineDslEntry.list(block: List.() -> Unit) = List().apply(block)

/**
 * 创建一个[BBCodeDsl.UnorderedListNode]并注册。
 */
@BBCodeDslMarker
fun List.ul(text: CharSequence) = UnorderedListNode(text).also { nodes += it }

/**
 * 创建一个[BBCodeDsl.OrderedListNode]并注册。
 */
@BBCodeDslMarker
fun List.ol(text: CharSequence) = OrderedListNode(text).also { nodes += it }

/**
 * 创建一个[BBCodeDsl.Table]。
 */
@BBCodeDslMarker
inline fun InlineDslEntry.table(block: Table.() -> Unit) = Table().apply(block)

/**
 * 创建一个[BBCodeDsl.TableHeader]并注册。
 */
@BBCodeDslMarker
inline fun Table.th(block: TableHeader.() -> Unit) = TableHeader().apply(block).also { header = it }

/**
 * 创建一个[BBCodeDsl.TableRow]并注册。
 */
@BBCodeDslMarker
inline fun Table.tr(block: TableRow.() -> Unit) = TableRow().apply(block).also { rows += it }

/**
 * 创建一个[BBCodeDsl.TableColumn]并注册。
 */
@BBCodeDslMarker
fun TableHeader.td(text: CharSequence) = TableColumn(text).also { columns += it }

/**
 * 创建一个[BBCodeDsl.TableColumn]并注册。
 */
@BBCodeDslMarker
fun TableRow.td(text: CharSequence) = TableColumn(text).also { columns += it }

/**
 * 创建一个[BBCodeDsl.Quote]。
 */
@BBCodeDslMarker
fun InlineDslEntry.quote(text: CharSequence) = Quote(null).apply { this.inlineText = text }

/**
 * 创建一个[BBCodeDsl.Quote]。
 */
@BBCodeDslMarker
fun InlineDslEntry.quote(name: String, text: CharSequence) = Quote(name).apply { this.inlineText = text }
