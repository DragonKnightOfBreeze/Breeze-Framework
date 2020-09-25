/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.dsl.bbcode

import com.windea.breezeframework.dsl.bbcode.BBCodeDslDefinitions.*
import com.windea.breezeframework.dsl.bbcode.BBCodeDslDefinitions.List

@BBCodeDslMarker
inline fun bbcode(block: BBCodeDsl.() -> Unit) = BBCodeDsl().apply(block)

@BBCodeDslMarker
fun b(text: CharSequence) = BoldText(text)

@BBCodeDslMarker
fun i(text: CharSequence) = ItalicText(text)

@BBCodeDslMarker
fun u(text: CharSequence) = UnderlinedText(text)

@BBCodeDslMarker
fun strike(text: CharSequence) = StrikeText(text)

@BBCodeDslMarker
fun spoiler(text: CharSequence) = SpoilerText(text)

@BBCodeDslMarker
fun noparse(text: CharSequence) = NoParseText(text)

@BBCodeDslMarker
fun left(text: CharSequence) = LeftText(text)

@BBCodeDslMarker
fun center(text: CharSequence) = CenterText(text)

@BBCodeDslMarker
fun right(text: CharSequence) = RightText(text)

@BBCodeDslMarker
fun code(text: String) = Code(text)

@BBCodeDslMarker
fun size(size: String, text: CharSequence) = StyledText(size, null, text)

@BBCodeDslMarker
fun color(color: String, text: CharSequence) = StyledText(null, color, text)

@BBCodeDslMarker
fun style(size: String, color: String, text: CharSequence) = StyledText(size, color, text)

@BBCodeDslMarker
fun youtube(text: String) = YoutubeVideo(text)

@BBCodeDslMarker
fun link(text: String) = Link(null, text)

@BBCodeDslMarker
fun link(url: String, text: String) = Link(url, text)

@BBCodeDslMarker
fun image(text: String) = Image(null, null, text)

@BBCodeDslMarker
fun image(width: String, height: String, text: String) = Image(width, height, text)

@BBCodeDslMarker
fun h1(text: CharSequence) = Heading1(text)

@BBCodeDslMarker
fun h2(text: CharSequence) = Heading2(text)

@BBCodeDslMarker
fun h3(text: CharSequence) = Heading3(text)

@BBCodeDslMarker
fun h4(text: CharSequence) = Heading4(text)

@BBCodeDslMarker
inline fun list(block: List.() -> Unit) = List().apply(block)

@BBCodeDslMarker
fun List.ul(text: CharSequence) = UnorderedListNode(text).also { nodes += it }

@BBCodeDslMarker
fun List.ol(text: CharSequence) = OrderedListNode(text).also { nodes += it }

@BBCodeDslMarker
inline fun table(block: Table.() -> Unit) = Table().apply(block)

@BBCodeDslMarker
inline fun Table.th(block: TableHeader.() -> Unit) = TableHeader().apply(block).also { header = it }

@BBCodeDslMarker
inline fun Table.tr(block: TableRow.() -> Unit) = TableRow().apply(block).also { rows += it }

@BBCodeDslMarker
fun TableHeader.td(text: CharSequence) = TableColumn(text).also { columns += it }

@BBCodeDslMarker
fun TableRow.td(text: CharSequence) = TableColumn(text).also { columns += it }

@BBCodeDslMarker
fun quote(text: CharSequence) = Quote(null).apply { this.inlineText = text }

@BBCodeDslMarker
fun quote(name: String, text: CharSequence) = Quote(name).apply { this.inlineText = text }
