// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.dsl.*

/**
 * Dsl definitions of [CriticMarkupDsl].
 */
interface CriticMarkupDslDefinitions {
	/**
	 * Inline dsl element of [CriticMarkupDsl].
	 */
	@CriticMarkupDslMarker
	interface InlineDslElement : DslElement, Inlineable

	/**
	 * Inline dsl entry of [CriticMarkupDsl].
	 */
	@CriticMarkupDslMarker
	interface InlineDslEntry : DslEntry, Inlineable

	/**
	 * Mark of [CriticMarkupDsl].
	 */
	interface Mark : InlineDslElement {
		val text: CharSequence
		override val inlineText get() = text
	}

	/**
	 * Addition of [CriticMarkupDsl].
	 */
	class Addition @PublishedApi internal constructor(override val text: CharSequence) : Mark {
		override fun toString() = "{++$text++}"
	}

	/**
	 * Deletion of [CriticMarkupDsl].
	 */
	class Deletion @PublishedApi internal constructor(override val text: CharSequence) : Mark {
		override fun toString() = "{--$text--}"
	}

	/**
	 * Substitution of [CriticMarkupDsl].
	 */
	class Substitution @PublishedApi internal constructor(
		override val text: CharSequence, val newText: CharSequence
	) : Mark {
		override fun toString() = "{~~$text~>$newText~~}"
	}

	/**
	 * Comment of [CriticMarkupDsl].
	 */
	class Comment @PublishedApi internal constructor(override val text: CharSequence) : Mark {
		override fun toString() = "{>>$text<<}"
	}

	/**
	 * Highlight of [CriticMarkupDsl].
	 */
	class Highlight @PublishedApi internal constructor(override val text: CharSequence) : Mark {
		override fun toString() = "{==$text==}"
	}
}
