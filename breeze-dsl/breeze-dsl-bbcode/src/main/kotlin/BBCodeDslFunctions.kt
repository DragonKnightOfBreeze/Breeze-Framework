package com.windea.breezeframework.dsl.bbcode

import com.windea.breezeframework.dsl.bbcode.BBCode.*
import com.windea.breezeframework.dsl.bbcode.BBCode.List

/**生成一段BBCode文本。*/
@BBCodeDsl
inline fun bbcode(block:Document.()->Unit) = Document().apply(block)
@BBCodeDsl
fun b(text:CharSequence) = BoldText(text)
@BBCodeDsl
fun i(text:CharSequence) = ItalicText(text)
@BBCodeDsl
fun u(text:CharSequence)  = UnderlinedText(text)
@BBCodeDsl
fun strike(text:CharSequence) = StrikeText(text)
@BBCodeDsl
fun spoiler(text:CharSequence) = SpoilerText(text)
@BBCodeDsl
fun noparse(text:CharSequence) = NoParseText(text)
@BBCodeDsl
fun left(text:CharSequence) = LeftText(text)
@BBCodeDsl
fun center(text:CharSequence) = CenterText(text)
@BBCodeDsl
fun right(text:CharSequence) = RightText(text)
@BBCodeDsl
fun code(text:String) = Code(text)
@BBCodeDsl
fun size(size:String,text:CharSequence) = StyledText(size,null,text)
@BBCodeDsl
fun color(color:String,text:CharSequence) = StyledText(null,color,text)
@BBCodeDsl
fun style(size:String,color:String,text:CharSequence) = StyledText(size,color,text)
@BBCodeDsl
fun youtube(text:String) = YoutubeVideo(text)
@BBCodeDsl
fun link(text:String) = Link(null,text)
@BBCodeDsl
fun link(url:String,text:String) = Link(url,text)
@BBCodeDsl
fun image(text:String) = Image(null,null,text)
@BBCodeDsl
fun image(width:String,height:String,text:String)=Image(width,height,text)
@BBCodeDsl
fun h1(text:CharSequence) = Heading1(text)
@BBCodeDsl
fun h2(text:CharSequence) = Heading2(text)
@BBCodeDsl
fun h3(text:CharSequence) = Heading3(text)
@BBCodeDsl
fun h4(text:CharSequence) = Heading4(text)
@BBCodeDsl
inline fun list(block:List.()->Unit) = List().apply(block)
@BBCodeDsl
fun List.ul(text:CharSequence) = UnorderedListNode(text).also { nodes += it }
@BBCodeDsl
fun List.ol(text:CharSequence) = OrderedListNode(text).also { nodes += it }
@BBCodeDsl
inline fun table(block:Table.()->Unit) = Table().apply(block)
@BBCodeDsl
inline fun Table.th(block:TableHeader.()->Unit) = TableHeader().apply(block).also { header = it }
@BBCodeDsl
inline fun Table.tr(block:TableRow.()->Unit) = TableRow().apply(block).also { rows += it }
@BBCodeDsl
fun TableHeader.td(text:CharSequence) = TableColumn(text).also { columns +=it }
@BBCodeDsl
fun TableRow.td(text:CharSequence) = TableColumn(text).also { columns +=it }
@BBCodeDsl
fun quote(text:CharSequence) = Quote(null).apply { this.inlineText = text }
@BBCodeDsl
fun quote(name:String,text:CharSequence) = Quote(name).apply { this.inlineText = text }
