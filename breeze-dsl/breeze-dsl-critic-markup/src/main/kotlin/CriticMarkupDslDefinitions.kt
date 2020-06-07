package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.dsl.*

/**
 * [Critic Markup Dsl](http://criticmarkup.com).
 */
@CriticMarkupDslMarker
class CriticMarkupDsl @PublishedApi internal constructor() : Dsl, CriticMarkupDslEntry {
	var text: CharSequence = ""
	override val inlineText: CharSequence get() = text

	override fun toString(): String = inlineText.toString()
}

/**
 * [Critic Markup Dsl](http://criticmarkup.com) Element.
 */
@CriticMarkupDslMarker
interface CriticMarkupDslElement : DslElement, Inlineable

/**
 * [Critic Markup Dsl](http://criticmarkup.com) Entry.
 */
@CriticMarkupDslMarker
interface CriticMarkupDslEntry : DslEntry, Inlineable

/**
 * Critic Markup Mark.
 */
sealed class Mark : CriticMarkupDslElement {
	override fun toString(): String = inlineText.toString()
}

/**
 * Critic Markup Addition.
 */
class Addition @PublishedApi internal constructor(val text: CharSequence) : Mark() {
	override val inlineText: CharSequence get() = "{++$text++}"
}

/**
 * Critic Markup Deletion.
 */
class Deletion @PublishedApi internal constructor(val text: CharSequence) : Mark() {
	override val inlineText: CharSequence get() = "{--$text--}"
}

/**
 * Critic Markup Substitution.
 */
class Substitution @PublishedApi internal constructor(val text: CharSequence, val newText: CharSequence) : Mark() {
	override val inlineText: CharSequence get() = "{~~$text~>$newText~~}"
}

/**
 * Critic Markup Comment.
 */
class Comment @PublishedApi internal constructor(val text: CharSequence) : Mark() {
	override val inlineText: CharSequence get() = "{>>$text<<}"
}

/**
 * Critic Markup Highlight.
 */
class Highlight @PublishedApi internal constructor(val text: CharSequence) : Mark() {
	override val inlineText: CharSequence get() = "{==$text==}"
}
