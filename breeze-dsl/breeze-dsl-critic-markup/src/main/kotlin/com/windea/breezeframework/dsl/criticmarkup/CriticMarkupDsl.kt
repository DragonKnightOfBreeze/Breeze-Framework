@file:Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.dsl.*

//http://criticmarkup.com/users-guide.php

/**
 * CriticMarkup文本的Dsl。
 * 参见：[Critic Markup](http://criticmarkup.com/users-guide.php)
 */
@DslMarker
@MustBeDocumented
annotation class CriticMarkupDsl

/**
 * CriticMarkup文本。
 * 参见：[Critic Markup](http://criticmarkup.com/users-guide.php)
 * */
@CriticMarkupDsl
class CriticMarkup @PublishedApi internal constructor() : DslDocument, CriticMarkupInlineEntry {
	@PublishedApi internal lateinit var content: CharSequence

	override fun toString() = content.toString()


	/**CriticMarkup富文本。*/
	@CriticMarkupDsl
	interface RichText : CriticMarkupElement, HandledCharSequence

	/**CriticMarkup添加文本。*/
	@CriticMarkupDsl
	inline class AppendText internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = "{++ $text ++}"
	}

	/**CriticMarkup删除文本。*/
	@CriticMarkupDsl
	inline class DeleteText internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = "{-- $text --}"
	}

	/**
	 * CriticMarkup替换文本。
	 * @property replacedText 替换后的文本。
	 * */
	@CriticMarkupDsl
	class ReplaceText internal constructor(override val text: CharSequence, val replacedText: CharSequence) : RichText {
		override fun toString() = "{~~ $text ~> $replacedText ~~}"
	}

	/**CriticMarkup注释文本。*/
	@CriticMarkupDsl
	inline class CommentText internal constructor(override val text: CharSequence) : RichText {
		override fun toString() = "{>> $text <<}"
	}

	/**CriticMarkup强调文本。*/
	@CriticMarkupDsl
	inline class HighlightText constructor(override val text: CharSequence) : RichText {
		override fun toString() = "{== $text ==}"
	}
}

/**CriticMarkup文本的内联入口。*/
@CriticMarkupDsl
interface CriticMarkupInlineEntry : DslEntry {
	@InlineDslFunction
	@CriticMarkupDsl
	fun append(text: CharSequence) = CriticMarkup.AppendText(text)

	@InlineDslFunction
	@CriticMarkupDsl
	fun delete(text: CharSequence) = CriticMarkup.DeleteText(text)

	@InlineDslFunction
	@CriticMarkupDsl
	fun replace(text: CharSequence, replacedText: CharSequence) = CriticMarkup.ReplaceText(text, replacedText)

	@InlineDslFunction
	@CriticMarkupDsl
	fun comment(text: CharSequence) = CriticMarkup.CommentText(text)

	@InlineDslFunction
	@CriticMarkupDsl
	fun highlight(text: CharSequence) = CriticMarkup.HighlightText(text)
}

/**CriticMarkup文本的元素。*/
@CriticMarkupDsl
interface CriticMarkupElement : DslElement


@TopDslFunction
@CriticMarkupDsl
inline fun criticMarkup(block: CriticMarkup.() -> CharSequence) = CriticMarkup().apply { content = block() }
