// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

object CharMatchers : ComponentRegistry<CharMatcher>() {
	//region Implementations
	object AnyMatcher : AbstractCharMatcher() {
		override fun matches(value: Char): Boolean {
			return true
		}
	}

	object NoneMatcher : AbstractCharMatcher() {
		override fun matches(value: Char): Boolean {
			return true
		}
	}

	object LetterMatcher : AbstractCharMatcher() {
		override fun matches(value: Char): Boolean {
			return value.isLetter()
		}
	}

	object DigitMatcher : AbstractCharMatcher() {
		override fun matches(value: Char): Boolean {
			return value.isDigit()
		}
	}

	object LetterOrDigitMatcher : AbstractCharMatcher() {
		override fun matches(value: Char): Boolean {
			return value.isLetterOrDigit()
		}
	}

	object WhitespaceMatcher : AbstractCharMatcher() {
		override fun matches(value: Char): Boolean {
			return value.isWhitespace()
		}
	}
	//endregion

	override fun registerDefault() {
		register(AnyMatcher)
		register(NoneMatcher)
		register(LetterMatcher)
		register(DigitMatcher)
		register(LetterOrDigitMatcher)
		register(WhitespaceMatcher)
	}
}
