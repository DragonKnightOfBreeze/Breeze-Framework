// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.criticmarkup

import com.windea.breezeframework.core.model.*
import com.windea.breezeframework.dsl.*

interface CriticMarkupDsl {
	@CriticMarkupDslMarker
	class Document @PublishedApi internal constructor() : DslDocument, InlineDslEntry {
		var text: CharSequence = ""
		override val inlineText: CharSequence get() = text

		override fun toString(): String = inlineText.toString()
	}

	@CriticMarkupDslMarker
	interface InlineDslElement : DslElement, Inlineable

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

	class Substitution @PublishedApi internal constructor(
		override val text: CharSequence, val newText: CharSequence,
	) : Mark() {
		override fun toString() = "{~~$text~>$newText~~}"
	}

	class Comment @PublishedApi internal constructor(override val text: CharSequence) : Mark() {
		override fun toString() = "{>>$text<<}"
	}

	class Highlight @PublishedApi internal constructor(override val text: CharSequence) : Mark() {
		override fun toString() = "{==$text==}"
	}
}
