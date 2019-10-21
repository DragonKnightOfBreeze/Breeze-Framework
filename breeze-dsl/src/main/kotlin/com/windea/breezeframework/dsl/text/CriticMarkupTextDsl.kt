@file:Suppress("NOTHING_TO_INLINE", "unused")

package com.windea.breezeframework.dsl.text

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.dsl.*

//REGION top annotations and interfaces

@DslMarker
private annotation class CriticMarkupTextDsl

/**CriticMarkup富文本。*/
@ReferenceApi("[Critic Markup](http://criticmarkup.com/users-guide.php)")
@CriticMarkupTextDsl
class CriticMarkupText @PublishedApi internal constructor() : DslBuilder, CriticMarkupTextDslInlineEntry {
	lateinit var text: String
	
	override fun toString(): String {
		return text
	}
}

//REGION dsl interfaces

/**CriticMarkup Dsl的内联入口。*/
@CriticMarkupTextDsl
interface CriticMarkupTextDslInlineEntry : DslEntry

/**CriticMarkup Dsl的内联元素。*/
@CriticMarkupTextDsl
interface CriticMarkupTextDslInlineElement : DslElement

//REGION dsl elements

/**Critic Markup文本。*/
@CriticMarkupTextDsl
sealed class CriticMarkupRichText(
	protected val prefixMarkers: String,
	val text: String,
	protected val suffixMarkers: String
) : CriticMarkupTextDslInlineElement {
	override fun toString(): String {
		return "$prefixMarkers $text $suffixMarkers"
	}
}

/**Critic Markup添加文本。*/
@CriticMarkupTextDsl
class CriticMarkupAppendedText @PublishedApi internal constructor(
	text: String
) : CriticMarkupRichText("{++", text, "++}")

/**Critic Markup添加文本。*/
@CriticMarkupTextDsl
class CriticMarkupDeletedText @PublishedApi internal constructor(
	text: String
) : CriticMarkupRichText("{--", text, "--}")

/**Critic Markup替换文本。*/
@CriticMarkupTextDsl
class CriticMarkupReplacedText @PublishedApi internal constructor(
	text: String,
	val replacedText: String
) : CriticMarkupRichText("{--", text, "--}") {
	private val infixMarkers: String = "~>"
	
	override fun toString(): String {
		return "$prefixMarkers $text $infixMarkers $replacedText $suffixMarkers"
	}
}

/**Critic Markup注释文本。*/
@CriticMarkupTextDsl
class CriticMarkupCommentText @PublishedApi internal constructor(
	text: String
) : CriticMarkupRichText("{>>", text, "<<}")

/**Critic Markup高亮文本。*/
@CriticMarkupTextDsl
class CriticMarkupHighlightText @PublishedApi internal constructor(
	text: String
) : CriticMarkupRichText("{==", text, "==}")

//REGION build extensions

@CriticMarkupTextDsl
inline fun criticMarkupText(builder: CriticMarkupText.() -> String) =
	CriticMarkupText().also { it.text = it.builder() }

@CriticMarkupTextDsl
inline fun criticMarkup(builder: CriticMarkupText.() -> CriticMarkupRichText) =
	CriticMarkupText().also { it.text = it.builder().toString() }

@InlineDsl
@CriticMarkupTextDsl
inline fun CriticMarkupTextDslInlineEntry.append(text: String) = CriticMarkupAppendedText(text)

@InlineDsl
@CriticMarkupTextDsl
inline fun CriticMarkupTextDslInlineEntry.delete(text: String) = CriticMarkupDeletedText(text)

@InlineDsl
@CriticMarkupTextDsl
inline fun CriticMarkupTextDslInlineEntry.replace(text: String, replacedText: String) =
	CriticMarkupReplacedText(text, replacedText)

@InlineDsl
@CriticMarkupTextDsl
inline fun CriticMarkupTextDslInlineEntry.comment(text: String) = CriticMarkupCommentText(text)

@InlineDsl
@CriticMarkupTextDsl
inline fun CriticMarkupTextDslInlineEntry.highlight(text: String) = CriticMarkupHighlightText(text)