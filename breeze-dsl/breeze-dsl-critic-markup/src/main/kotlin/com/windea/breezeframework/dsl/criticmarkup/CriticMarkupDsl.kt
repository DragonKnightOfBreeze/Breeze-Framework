@file:Suppress("unused", "NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS", "INLINE_CLASS_NOT_TOP_LEVEL")

package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.criticmarkup.CriticMarkup.*

/**
 * CriticMarkup标记语法的Dsl。
 *
 * 参见：[Critic Markup](http://criticmarkup.com/users-guide.php)
 */
@DslMarker
@MustBeDocumented
internal annotation class CriticMarkupTextDsl

/**
 * CriticMarkup标记语法。
 *
 * 参见：[Critic Markup](http://criticmarkup.com/users-guide.php)
 * */
@CriticMarkupTextDsl
class CriticMarkup @PublishedApi internal constructor() : DslDocument, CriticMarkupInlineEntry {
	@PublishedApi internal lateinit var text: String

	override fun toString() = text


	/**CriticMarkup富文本。*/
	@CriticMarkupTextDsl
	interface RichText : DslElement {
		val text: String
	}

	/**CriticMarkup添加文本。*/
	@CriticMarkupTextDsl
	inline class AppendText internal constructor(override val text: String) : RichText {
		override fun toString() = "{++ $text ++}"
	}

	/**CriticMarkup删除文本。*/
	@CriticMarkupTextDsl
	inline class DeleteText internal constructor(override val text: String) : RichText {
		override fun toString() = "{-- $text --}"
	}

	/**CriticMarkup替换文本。*/
	@CriticMarkupTextDsl
	class ReplaceText internal constructor(
		override val text: String, val replacedText: String
	) : RichText {
		override fun toString() = "{~~ $text ~> $replacedText ~~}"
	}

	/**CriticMarkup注释文本。*/
	@CriticMarkupTextDsl
	inline class CommentText internal constructor(override val text: String) : RichText {
		override fun toString() = "{>> $text <<}"
	}

	/**CriticMarkup强调文本。*/
	@CriticMarkupTextDsl
	inline class HighlightText constructor(override val text: String) : RichText {
		override fun toString() = "{== $text ==}"
	}
}

/**CriticMarkup标记语法的内联入口。*/
@CriticMarkupTextDsl
interface CriticMarkupInlineEntry : DslEntry


@TopDslFunction
@CriticMarkupTextDsl
inline fun criticMarkup(block: CriticMarkup.() -> String) = CriticMarkup().apply { text = block() }

@InlineDslFunction
@CriticMarkupTextDsl
fun CriticMarkupInlineEntry.append(text: String) = AppendText(text)

@InlineDslFunction
@CriticMarkupTextDsl
fun CriticMarkupInlineEntry.delete(text: String) = DeleteText(text)

@InlineDslFunction
@CriticMarkupTextDsl
fun CriticMarkupInlineEntry.replace(text: String, replacedText: String) = ReplaceText(text, replacedText)

@InlineDslFunction
@CriticMarkupTextDsl
fun CriticMarkupInlineEntry.comment(text: String) = CommentText(text)

@InlineDslFunction
@CriticMarkupTextDsl
fun CriticMarkupInlineEntry.highlight(text: String) = HighlightText(text)
