// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 字符匹配器。
 *
 * 字符匹配器用于表示字符是否匹配某种格式。
 */
interface CharMatcher : Matcher<Char> {
	fun remove(charSequence: CharSequence): String {
		return buildString {
			for(c in charSequence) if(!matches(c)) {
				append(c)
			}
		}
	}

	fun retain(charSequence: CharSequence): String {
		return buildString {
			for(c in charSequence) if(matches(c)) {
				append(c)
			}
		}
	}

	fun collapse(charSequence: CharSequence, char: Char): String {
		return buildString {
			var isMatched = false
			for(c in charSequence) {
				if(matches(c)) {
					if(!isMatched) {
						isMatched = true
						append(c)
					}
				} else {
					isMatched = false
				}
			}
		}
	}

	override fun componentCopy(componentParams: Map<String, Any?>): CharMatcher {
		throw UnsupportedOperationException("Cannot copy component of type: ${javaClass.name}.")
	}
}

abstract class AbstractCharMatcher : CharMatcher {
	override fun equals(other: Any?) = componentEquals(other)

	override fun hashCode() = componentHashcode()

	override fun toString() = componentToString()
}

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
