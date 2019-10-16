@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.dsl.text

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.dsl.*

//REGION Dsl annotations

@DslMarker
private annotation class CriticMarkupDsl

//REGION Dsl & Dsl config & Dsl elements

@Reference("[Critic Markup](http://criticmarkup.com/users-guide.php)")
@CriticMarkupDsl
class CriticMarkup @PublishedApi internal constructor() : DslBuilder {
	lateinit var text: String
	
	override fun toString() = text
}


/**CriticMarkup Dsl的元素。*/
@CriticMarkupDsl
interface CriticMarkupDslElement : DslElement

/**CriticMarkup Dsl的内联元素。*/
@CriticMarkupDsl
interface CriticMarkupDslInlineElement : CriticMarkupDslElement


/**Critic Markup文本。*/
@CriticMarkupDsl
sealed class CriticMarkupText(
	protected val prefixMarkers: String,
	val text: String,
	protected val suffixMarkers: String
) : CriticMarkupDslInlineElement {
	override fun toString() = "$prefixMarkers $text $suffixMarkers"
}

/**Critic Markup添加文本。*/
@CriticMarkupDsl
class CriticMarkupAppendedText @PublishedApi internal constructor(
	text: String
) : CriticMarkupText("{++", text, "++}")

/**Critic Markup添加文本。*/
@CriticMarkupDsl
class CriticMarkupDeletedText @PublishedApi internal constructor(
	text: String
) : CriticMarkupText("{--", text, "--}")

/**Critic Markup替换文本。*/
@CriticMarkupDsl
class CriticMarkupReplacedText @PublishedApi internal constructor(
	text: String,
	val replacedText: String
) : CriticMarkupText("{--", text, "--}") {
	private val infixMarkers: String = "~>"
	
	override fun toString() = "$prefixMarkers $text $infixMarkers $replacedText $suffixMarkers"
}

/**Critic Markup注释文本。*/
@CriticMarkupDsl
class CriticMarkupCommentText @PublishedApi internal constructor(
	text: String
) : CriticMarkupText("{>>", text, "<<}")

/**Critic Markup高亮文本。*/
@CriticMarkupDsl
class CriticMarkupHighlightText @PublishedApi internal constructor(
	text: String
) : CriticMarkupText("{==", text, "==}")

//REGION Build extensions

@CriticMarkupDsl
object CriticMarkupInlineBuilder {
	@CriticMarkupDsl
	inline fun cmAppend(text: String) = CriticMarkupAppendedText(text)
	
	@CriticMarkupDsl
	inline fun cmDelete(text: String) = CriticMarkupDeletedText(text)
	
	@CriticMarkupDsl
	inline fun cmReplace(text: String, replacedText: String) = CriticMarkupReplacedText(text, replacedText)
	
	@CriticMarkupDsl
	inline fun cmComment(text: String) = CriticMarkupCommentText(text)
	
	@CriticMarkupDsl
	inline fun cmHighlight(text: String) = CriticMarkupHighlightText(text)
}


@CriticMarkupDsl
inline fun criticMarkup(builder: CriticMarkup.() -> String) = CriticMarkup().also { it.text = it.builder() }

