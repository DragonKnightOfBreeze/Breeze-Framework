@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.dsl.*

//region dsl top declarations
/**CriticMarkup富文本的Dsl。*/
@Reference("[Critic Markup](http://criticmarkup.com/users-guide.php)")
@DslMarker
@MustBeDocumented
internal annotation class CriticMarkupTextDsl

/**CriticMarkup富文本。*/
@CriticMarkupTextDsl
class CriticMarkupText @PublishedApi internal constructor() : DslDocument, CriticMarkupTextDslInlineEntry {
	@PublishedApi internal lateinit var text: String

	override fun toString(): String {
		return text
	}
}
//endregion

//region dsl declarations
/**CriticMarkup Dsl的内联入口。*/
@CriticMarkupTextDsl
interface CriticMarkupTextDslInlineEntry : DslEntry
//endregion

//region dsl build extensions
@CriticMarkupTextDsl
inline fun criticMarkupText(block: CriticMarkupText.() -> String) = CriticMarkupText().also { it.text = it.block() }

@InlineDslFunction
@CriticMarkupTextDsl
inline fun CriticMarkupTextDslInlineEntry.append(text: String): String {
	return "{++ $text ++}"
}

@InlineDslFunction
@CriticMarkupTextDsl
inline fun CriticMarkupTextDslInlineEntry.delete(text: String): String {
	return "{-- $text --}"
}

@InlineDslFunction
@CriticMarkupTextDsl
inline fun CriticMarkupTextDslInlineEntry.replace(text: String, replacedText: String): String {
	return "{~~ $text ~> $replacedText ~~}"
}

@InlineDslFunction
@CriticMarkupTextDsl
inline fun CriticMarkupTextDslInlineEntry.comment(text: String): String {
	return "{>> $text <<}"
}

@InlineDslFunction
@CriticMarkupTextDsl
inline fun CriticMarkupTextDslInlineEntry.highlight(text: String): String {
	return "{== $text ==}"
}
//endregion
