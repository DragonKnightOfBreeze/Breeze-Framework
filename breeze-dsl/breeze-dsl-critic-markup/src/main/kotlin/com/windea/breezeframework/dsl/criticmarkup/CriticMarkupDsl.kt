@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.criticmarkup.CriticMarkup.*

//http://criticmarkup.com/users-guide.php

/**
 * CriticMarkup文本的Dsl。
 * 参见：[Critic Markup](http://criticmarkup.com/users-guide.php)
 */
@DslMarker
@MustBeDocumented
annotation class CriticMarkupDsl

/**CriticMarkup文本的内联入口。*/
@CriticMarkupDsl
interface CriticMarkupInlineEntry : DslEntry {
	/**(No document.)*/
	@InlineDslFunction
	@CriticMarkupDsl
	fun append(text:CharSequence) = AppendText(text)

	/**(No document.)*/
	@InlineDslFunction
	@CriticMarkupDsl
	fun delete(text:CharSequence) = DeleteText(text)

	/**(No document.)*/
	@InlineDslFunction
	@CriticMarkupDsl
	fun replace(text:CharSequence, replacedText:CharSequence) = ReplaceText(text, replacedText)

	/**(No document.)*/
	@InlineDslFunction
	@CriticMarkupDsl
	fun comment(text:CharSequence) = CommentText(text)

	/**(No document.)*/
	@InlineDslFunction
	@CriticMarkupDsl
	fun highlight(text:CharSequence) = HighlightText(text)
}

/**CriticMarkup文本的内联元素。*/
@CriticMarkupDsl
interface CriticMarkupInlineElement : DslElement

/**CriticMarkup文本。*/
@CriticMarkupDsl
interface CriticMarkup {
	/**CriticMarkup文本的文档。*/
	class Document @PublishedApi internal constructor() : DslDocument, CriticMarkupInlineEntry {
		var text:CharSequence = ""

		override fun toString() = text.toString()
	}

	/**CriticMarkup的富文本。*/
	@CriticMarkupDsl
	interface RichText : CriticMarkupInlineElement, HandledCharSequence

	/**CriticMarkup的添加文本。*/
	@CriticMarkupDsl
	inline class AppendText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = "{++ $text ++}"
	}

	/**CriticMarkup的删除文本。*/
	@CriticMarkupDsl
	inline class DeleteText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = "{-- $text --}"
	}

	/**
	 * CriticMarkup的替换文本。
	 * @property replacedText 替换后的文本。
	 */
	@CriticMarkupDsl
	class ReplaceText @PublishedApi internal constructor(
		override val text:CharSequence, val replacedText:CharSequence
	) : RichText {
		override fun toString() = "{~~ $text ~> $replacedText ~~}"
	}

	/**CriticMarkup的注释文本。*/
	@CriticMarkupDsl
	inline class CommentText @PublishedApi internal constructor(
		override val text:CharSequence)
		: RichText {
		override fun toString() = "{>> $text <<}"
	}

	/**CriticMarkup的强调文本。*/
	@CriticMarkupDsl
	inline class HighlightText @PublishedApi internal constructor(
		override val text:CharSequence
	) : RichText {
		override fun toString() = "{== $text ==}"
	}
}


/**(No document.)*/
@TopDslFunction
@CriticMarkupDsl
inline fun criticMarkup(block:Document.() -> CharSequence) = Document().apply { text = block() }
