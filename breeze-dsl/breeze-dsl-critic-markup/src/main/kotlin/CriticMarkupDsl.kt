// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl.criticmarkup

import icu.windea.breezeframework.core.model.*
import icu.windea.breezeframework.dsl.DslDocument as IDslDocument
import icu.windea.breezeframework.dsl.DslElement as IDslElement

interface CriticMarkupDsl {
	@CriticMarkupDslMarker
	class DslDocument @PublishedApi internal constructor() : IDslDocument, InlineDslEntry {
		var text: CharSequence = ""
		override val inlineText: CharSequence get() = text

		override fun toString(): String = inlineText.toString()
	}

	@CriticMarkupDslMarker
	interface InlineDslElement : IDslElement, Inlineable

	@CriticMarkupDslMarker
	interface InlineDslEntry : Inlineable

	abstract class Mark : InlineDslElement {
		abstract val text: CharSequence
		override val inlineText get() = text
	}

	class Addition @PublishedApi internal constructor(override val text: CharSequence) : Mark() {
		override fun toString() = "{++$text++}"
	}

	class Deletion @PublishedApi internal constructor(override val text: CharSequence) : Mark() {
		override fun toString() = "{--$text--}"
	}

	class Substitution @PublishedApi internal constructor(override val text: CharSequence, val newText: CharSequence) : Mark() {
		override fun toString() = "{~~$text~>$newText~~}"
	}

	class Comment @PublishedApi internal constructor(override val text: CharSequence) : Mark() {
		override fun toString() = "{>>$text<<}"
	}

	class Highlight @PublishedApi internal constructor(override val text: CharSequence) : Mark() {
		override fun toString() = "{==$text==}"
	}
}
