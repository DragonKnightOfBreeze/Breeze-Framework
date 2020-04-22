package com.windea.breezeframework.dsl.bbcode

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.DslConstants.ls

@BBCodeDsl
interface BBCode {
	/**
	 * BBCode的文档。
	 * @property text 内容文本。
	 */
	@BBCodeDsl
	class Document @PublishedApi internal constructor() : DslDocument {
		var text:CharSequence = ""

		override fun toString():String = text.toString()
	}

	/**
	 * BBCode的配置。
	 * @property indent 文本缩进。
	 */
	@BBCodeDsl
	data class Config(
		var indent:String = "  "
	)


	@BBCodeDsl
	interface DslElement : com.windea.breezeframework.dsl.DslElement,Inlineable

	@BBCodeDsl
	abstract class Element @PublishedApi internal constructor(val tag:String):DslElement{
		abstract override val text:CharSequence

		override fun toString():String = "[$tag]$text[/$tag]"
	}

	@BBCodeDsl
	abstract class InlineElement @PublishedApi internal constructor(
		tag:String,
		override val text:CharSequence
	) :Element(tag)

	@BBCodeDsl
	abstract class OneArgInlineElement @PublishedApi internal constructor(
		tag:String,
		val value:String?,
		text:CharSequence
	) : InlineElement(tag, text) {
		override fun toString():String = "[$tag${value.typing { "=$it" }}]$text[/$tag]"
	}

	@BBCodeDsl
	abstract class MultiArgInlineElement @PublishedApi internal constructor(
		tag:String,
		val args:Map<String, String?>,
		text:CharSequence
	) : InlineElement(tag, text) {
		override fun toString():String = "[$tag${args.typingAll(" ", " ") { (k, v) -> "$k=$v" }}]$text[/$tag]"
	}

	@BBCodeDsl
	 class BoldText @PublishedApi internal constructor(override val text:CharSequence):InlineElement("b",text)

	@BBCodeDsl
	 class ItalicText @PublishedApi internal constructor(override val text:CharSequence):InlineElement("i",text)

	@BBCodeDsl
	 class UnderlinedText @PublishedApi internal constructor(override val text:CharSequence):InlineElement("u",text)

	@BBCodeDsl
	 class StrokedText @PublishedApi internal constructor(override val text:CharSequence):InlineElement("s",text) //strike

	@BBCodeDsl
	 class SpoilerText @PublishedApi internal constructor(override val text:CharSequence):InlineElement("spoiler",text)

	@BBCodeDsl
	 class NoParseText @PublishedApi internal constructor(override val text:CharSequence):InlineElement("noparse",text)

	@BBCodeDsl
	 class LeftText @PublishedApi internal constructor(override val text:CharSequence):InlineElement("left",text)

	@BBCodeDsl
	 class CenterText @PublishedApi internal constructor(override val text:CharSequence):InlineElement("center",text)

	@BBCodeDsl
	class RightText @PublishedApi internal constructor(override val text:CharSequence):InlineElement("right",text)

	@BBCodeDsl
	class Code @PublishedApi internal constructor(override val text:CharSequence):InlineElement("code",text)

	@BBCodeDsl
	class YoutubeVideo @PublishedApi internal constructor(override val text:CharSequence):InlineElement("youtube",text)

	@BBCodeDsl
	class StyledText @PublishedApi internal constructor(
		val size:String?,
		val color:String?,
		override val text:CharSequence
	):MultiArgInlineElement("style", mapOf("size" to size,"color" to color), text)

	@BBCodeDsl
	class Quote @PublishedApi internal constructor(
		val name:String?,
		override val text:CharSequence
	):OneArgInlineElement("quote",name,text)

	@BBCodeDsl
	class Link @PublishedApi internal constructor(
		val url:String?,
		override val text:CharSequence
	):OneArgInlineElement("url",url,text)

	@BBCodeDsl
	class Image @PublishedApi internal constructor(
		val width:String?,
		val height:String?,
		override val text:CharSequence
	):MultiArgInlineElement("img",mapOf("width" to width,"height" to height),text)

	@BBCodeDsl
	abstract class   CrosslineElement @PublishedApi internal constructor(
		tag:String,
		override val text:CharSequence
	):Element(tag){
		override fun toString():String = "[$tag]$text[/$tag]"
	}

	@BBCodeDsl
	abstract class Heading @PublishedApi internal constructor(
		tag:String,
		text:CharSequence
	):CrosslineElement(tag,text)

	@BBCodeDsl
	class Heading1 @PublishedApi internal constructor(text:CharSequence):Heading("h1",text)

	@BBCodeDsl
	class Heading2 @PublishedApi internal constructor(text:CharSequence):Heading("h1",text)

	@BBCodeDsl
	class Heading3 @PublishedApi internal constructor(text:CharSequence):Heading("h1",text)

	@BBCodeDsl
	class Heading4 @PublishedApi internal constructor(text:CharSequence):Heading("h1",text)

	@BBCodeDsl
	abstract class BlockElement @PublishedApi internal constructor(tag:String) :Element(tag),Indentable{
		abstract override val text:CharSequence
		override var indentContent:Boolean = true

		override fun toString():String = "[$tag]${text.ifNotEmpty { it.toString().doIndent(config.indent) }}[/$tag]"
	}

	@BBCodeDsl
	class List  @PublishedApi internal constructor():BlockElement("list"){
		val nodes:MutableList<ListNode> = mutableListOf()
		override val text:CharSequence get() = nodes.typingAll(ls)
	}

	abstract class ListNode @PublishedApi internal constructor(tag:String, override val text:CharSequence):Element(tag)

	@BBCodeDsl
	class UnorderedListNode  @PublishedApi internal constructor(text:CharSequence ):ListNode("ul",text)

	@BBCodeDsl
	class OrderedListNode  @PublishedApi internal constructor(text:CharSequence):ListNode("ol",text)

	@BBCodeDsl
	class Table  @PublishedApi internal constructor():BlockElement("table"){
		lateinit var header: TableHeader //不能为null
		val rows:MutableList<TableRow> = mutableListOf()
		override val text:CharSequence get() = header.toString() + ls + rows.typingAll(ls)
	}

	@BBCodeDsl
	class TableHeader  @PublishedApi internal constructor():Element("th"){
		val columns:MutableList<TableColumn> = mutableListOf()
		override val text:CharSequence get() = columns.typingAll(ls)
	}
	@BBCodeDsl
	class TableRow  @PublishedApi internal constructor():Element("tr"){
		val columns:MutableList<TableColumn> = mutableListOf()
		override val text:CharSequence get() = columns.typingAll(ls)
	}

	@BBCodeDsl
	class TableColumn  @PublishedApi internal constructor(override val text:CharSequence):Element("td")


	companion object {
		val config = Config()
	}
}
