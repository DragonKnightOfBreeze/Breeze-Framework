@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.bbcode

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.*

interface BBCode {
	@BBCodeDsl
	class Document @PublishedApi internal constructor() : DslDocument {
		var text:CharSequence = ""

		override fun toString() = text.toString()
	}

	@BBCodeDsl
	interface DslElement : com.windea.breezeframework.dsl.DslElement

	@BBCodeDsl
	interface TopDslElement : DslElement

	@BBCodeDsl
	interface InlineDslElement : DslElement, Inlineable

	@BBCodeDsl
	abstract class Element(
		val tag:String,
		text:CharSequence
	) : InlineDslElement {
		override fun toString() = "[$tag]$text[/$tag]"
	}

	@BBCodeDsl
	abstract class OneArgumentElement(
		tag:String,
		val value:String?,
		text:CharSequence
	) : Element(tag, text) {
		override fun toString() = "[$tag${value.typing { "=$it" }}]$text[/$tag]"
	}

	@BBCodeDsl
	abstract class MultiArgumentElement(
		tag:String,
		val args:Map<String, String?>,
		text:CharSequence
	) : Element(tag, text) {
		override fun toString() = "[$tag${args.typingAll(" ", " ") { (k, v) -> "$k=$v" }}]$text[/$tag]"
	}

	@BBCodeDsl
	inline class BoldText @PublishedApi internal constructor(override val text:CharSequence):Element("b",text)

	@BBCodeDsl
	inline class ItalicText @PublishedApi internal constructor(override val text:CharSequence):Element("i",text)

	@BBCodeDsl
	inline class UnderlinedText @PublishedApi internal constructor(override val text:CharSequence):Element("u",text)

	@BBCodeDsl
	inline class StrokedText @PublishedApi internal constructor(override val text:CharSequence):Element("s",text) //strike

	@BBCodeDsl
	inline class SpoilerText @PublishedApi internal constructor(override val text:CharSequence):Element("spoiler",text)

	@BBCodeDsl
	inline class NoParseText @PublishedApi internal constructor(override val text:CharSequence):Element("noparse",text)

	@BBCodeDsl
	inline class LeftText @PublishedApi internal constructor(override val text:CharSequence):Element("left",text)

	@BBCodeDsl
	inline class CenterText @PublishedApi internal constructor(override val text:CharSequence):Element("center",text)

	@BBCodeDsl
	inline class RightText @PublishedApi internal constructor(override val text:CharSequence):Element("right",text)

	@BBCodeDsl
	inline class Code @PublishedApi internal constructor(override val text:CharSequence):Element("code",text)

	@BBCodeDsl
	inline class YoutubeVideo @PublishedApi internal constructor(override val text:CharSequence):Element("youtube",text)

	@BBCodeDsl
	class StyledText @PublishedApi internal constructor(
		val size:String?,
		val color:String?,
		override val text:CharSequence
	):MultiArgumentElement("style", mapOf("size" to size,"color" to color), text)

	@BBCodeDsl
	class Quote @PublishedApi internal constructor(
		val name:String?,
		override val text:CharSequence
	):OneArgumentElement("quote",name,text)

	@BBCodeDsl
	class Link @PublishedApi internal constructor(
		val url:String?,
		override val text:String
	):OneArgumentElement("url",url,text)

	@BBCodeDsl
	class Image @PublishedApi internal constructor(
		val width:String?,
		val height:String?,
		override val text:CharSequence
	):MultiArgumentElement("img",mapOf("width" to width,"height" to height),text)

	@BBCodeDsl
	class List

	@BBCodeDsl
	class Table
}
