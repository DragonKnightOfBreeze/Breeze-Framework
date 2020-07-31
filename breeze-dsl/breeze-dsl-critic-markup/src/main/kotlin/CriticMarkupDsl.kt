package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.criticmarkup.CriticMarkupDslDefinitions.*

/**
 * [Critic Markup](http://criticmarkup.com) dsl.
 */
@CriticMarkupDslMarker
class CriticMarkupDsl @PublishedApi internal constructor() : Dsl, InlineDslEntry {
	var text: CharSequence = ""
	override val inlineText: CharSequence get() = text

	override fun toString(): String = inlineText.toString()
}
