@file:Suppress("unused", "NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.criticmarkup.CriticMarkup.*

//http://criticmarkup.com/users-guide.php

/**
 * CriticMarkup文本的Dsl。
 *
 * 参见：[Critic Markup](http://criticmarkup.com/users-guide.php)
 */
@DslMarker
@MustBeDocumented
annotation class CriticMarkupDsl

/**
 * CriticMarkup文本。
 *
 * 参见：[Critic Markup](http://criticmarkup.com/users-guide.php)
 * */
@CriticMarkupDsl
class CriticMarkup @PublishedApi internal constructor() : DslDocument, CriticMarkupInlineEntry {
	@PublishedApi internal lateinit var text: CharSequence

	override fun toString() = text.toString()


	/**CriticMarkup富文本。*/
	@CriticMarkupDsl
	interface RichText : DslElement, CharSequence {
		val text: CharSequence
		override val length get() = text.length
		override fun get(index: Int) = text[index]
		override fun subSequence(startIndex: Int, endIndex: Int) = text.subSequence(startIndex, endIndex)
	}

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

	/**CriticMarkup替换文本。*/
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

/**CriticMarkup标记语法的内联入口。*/
@CriticMarkupDsl
interface CriticMarkupInlineEntry : DslEntry


@TopDslFunction
@CriticMarkupDsl
inline fun criticMarkup(block: CriticMarkup.() -> CharSequence) = CriticMarkup().apply { text = block() }

@InlineDslFunction
@CriticMarkupDsl
fun CriticMarkupInlineEntry.append(text: CharSequence) = AppendText(text)

@InlineDslFunction
@CriticMarkupDsl
fun CriticMarkupInlineEntry.delete(text: CharSequence) = DeleteText(text)

@InlineDslFunction
@CriticMarkupDsl
fun CriticMarkupInlineEntry.replace(text: CharSequence, replacedText: CharSequence) = ReplaceText(text, replacedText)

@InlineDslFunction
@CriticMarkupDsl
fun CriticMarkupInlineEntry.comment(text: CharSequence) = CommentText(text)

@InlineDslFunction
@CriticMarkupDsl
fun CriticMarkupInlineEntry.highlight(text: CharSequence) = HighlightText(text)
