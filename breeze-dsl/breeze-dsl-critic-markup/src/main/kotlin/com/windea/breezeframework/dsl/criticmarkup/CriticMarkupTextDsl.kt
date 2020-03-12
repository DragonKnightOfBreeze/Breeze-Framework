@file:Suppress("unused")

package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.dsl.*

/**
 * CriticMarkup富文本的Dsl。
 *
 * 参见：[Critic Markup](http://criticmarkup.com/users-guide.php)
 */
@DslMarker
@MustBeDocumented
internal annotation class CriticMarkupTextDsl

/**
 * CriticMarkup富文本。
 *
 * 参见：[Critic Markup](http://criticmarkup.com/users-guide.php)
 * */
@CriticMarkupTextDsl
class CriticMarkupText @PublishedApi internal constructor() : DslDocument, CriticMarkupTextInlineEntry {
	@PublishedApi internal lateinit var text: String

	override fun toString(): String {
		return text
	}
}

/**CriticMarkup富文本的内联入口。*/
@CriticMarkupTextDsl
interface CriticMarkupTextInlineEntry : DslEntry


@CriticMarkupTextDsl
inline fun criticMarkupText(block: CriticMarkupText.() -> String) = CriticMarkupText().also { it.text = it.block() }

@InlineDslFunction
@CriticMarkupTextDsl
fun CriticMarkupTextInlineEntry.append(text: String): String {
	return "{++ $text ++}"
}

@InlineDslFunction
@CriticMarkupTextDsl
fun CriticMarkupTextInlineEntry.delete(text: String): String {
	return "{-- $text --}"
}

@InlineDslFunction
@CriticMarkupTextDsl
fun CriticMarkupTextInlineEntry.replace(text: String, replacedText: String): String {
	return "{~~ $text ~> $replacedText ~~}"
}

@InlineDslFunction
@CriticMarkupTextDsl
fun CriticMarkupTextInlineEntry.comment(text: String): String {
	return "{>> $text <<}"
}

@InlineDslFunction
@CriticMarkupTextDsl
fun CriticMarkupTextInlineEntry.highlight(text: String): String {
	return "{== $text ==}"
}
