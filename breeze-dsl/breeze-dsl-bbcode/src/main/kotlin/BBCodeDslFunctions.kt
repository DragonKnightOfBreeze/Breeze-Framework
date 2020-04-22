package com.windea.breezeframework.dsl.bbcode

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.bbcode.BBCode.*
import com.windea.breezeframework.dsl.bbcode.BBCode.List

/**生成一段BBCode文本。*/
@TopDslFunction
@BBCodeDsl
inline fun bbcode(block:Document.()->Unit) = Document().apply(block)


@InlineDslFunction
@BBCodeDsl
fun b(text:CharSequence) = BoldText(text)

@InlineDslFunction
@BBCodeDsl
fun i(text:CharSequence) = ItalicText(text)

@InlineDslFunction
@BBCodeDsl
fun u(text:CharSequence)  = UnderlinedText(text)

@InlineDslFunction
@BBCodeDsl
fun strike(text:CharSequence) = StrikeText(text)

@InlineDslFunction
@BBCodeDsl
fun spoiler(text:CharSequence) = SpoilerText(text)

@InlineDslFunction
@BBCodeDsl
fun noparse(text:CharSequence) = NoParseText(text)

@InlineDslFunction
@BBCodeDsl
fun left(text:CharSequence) = LeftText(text)

@InlineDslFunction
@BBCodeDsl
fun center(text:CharSequence) = CenterText(text)

@InlineDslFunction
@BBCodeDsl
fun right(text:CharSequence) = RightText(text)

@InlineDslFunction
@BBCodeDsl
fun code(text:String) = Code(text)

@InlineDslFunction
@BBCodeDsl
fun size(size:String,text:CharSequence) = StyledText(size,null,text)

@InlineDslFunction
@BBCodeDsl
fun color(color:String,text:CharSequence) = StyledText(null,color,text)

@InlineDslFunction
@BBCodeDsl
fun style(size:String,color:String,text:CharSequence) = StyledText(size,color,text)

@InlineDslFunction
@BBCodeDsl
fun youtube(text:String) = YoutubeVideo(text)

@InlineDslFunction
@BBCodeDsl
fun link(text:String) = Link(null,text)

@InlineDslFunction
@BBCodeDsl
fun link(url:String,text:String) = Link(url,text)

@InlineDslFunction
@BBCodeDsl
fun image(text:String) = Image(null,null,text)

@InlineDslFunction
@BBCodeDsl
fun image(width:String,height:String,text:String)=Image(width,height,text)

@InlineDslFunction
@BBCodeDsl
fun h1(text:CharSequence) = Heading1(text)

@InlineDslFunction
@BBCodeDsl
fun h2(text:CharSequence) = Heading2(text)

@InlineDslFunction
@BBCodeDsl
fun h3(text:CharSequence) = Heading3(text)

@InlineDslFunction
@BBCodeDsl
fun h4(text:CharSequence) = Heading4(text)

@InlineDslFunction
@BBCodeDsl
inline fun list(block:List.()->Unit) = List().apply(block)

@DslFunction
@BBCodeDsl
fun List.ul(text:CharSequence) = UnorderedListNode(text).also { nodes += it }

@DslFunction
@BBCodeDsl
fun List.ol(text:CharSequence) = OrderedListNode(text).also { nodes += it }

@InlineDslFunction
@BBCodeDsl
inline fun table(block:Table.()->Unit) = Table().apply(block)

@DslFunction
@BBCodeDsl
inline fun Table.th(block:TableHeader.()->Unit) = TableHeader().apply(block).also { header = it }

@DslFunction
@BBCodeDsl
inline fun Table.tr(block:TableRow.()->Unit) = TableRow().apply(block).also { rows += it }

@DslFunction
@BBCodeDsl
fun TableHeader.td(text:CharSequence) = TableColumn(text).also { columns +=it }

@DslFunction
@BBCodeDsl
fun TableRow.td(text:CharSequence) = TableColumn(text).also { columns +=it }

@InlineDslFunction
@BBCodeDsl
fun quote(text:CharSequence) = Quote(null).apply { this.text = text }

@InlineDslFunction
@BBCodeDsl
fun quote(name:String,text:CharSequence) = Quote(name).apply { this.text = text }
