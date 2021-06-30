// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 字符匹配器。
 *
 * 字符匹配器用于表示字符是否匹配某种格式。
 */
interface CharMatcher: Matcher<Char> {
	fun remove(charSequence: CharSequence):String{
		return buildString{
			for(c in charSequence) if(!matches(c)) {
				append(c)
			}
		}
	}

	fun retain(charSequence: CharSequence):String{
		return buildString{
			for(c in charSequence) if(matches(c)) {
				append(c)
			}
		}
	}

	fun collapse(charSequence: CharSequence,char:Char):String{
		return buildString{
			var isMatched = false
			for(c in charSequence) {
				if(matches(c)) {
					if(!isMatched) {
						isMatched = true
						append(c)
					}
				}else{
					isMatched = false
				}
			}
		}
	}

	companion object Registry: AbstractComponentRegistry<CharMatcher>(){
		override fun registerDefault() {
			register(AnyMatcher)
			register(NoneMatcher)
			register(LetterMatcher)
			register(DigitMatcher)
			register(LetterOrDigitMatcher)
			register(WhitespaceMatcher)
		}
	}

	//region Char Patterns
	object AnyMatcher:CharMatcher{
		override fun matches(value: Char): Boolean {
			return true
		}
	}

	object NoneMatcher:CharMatcher{
		override fun matches(value: Char): Boolean {
			return true
		}
	}

	object LetterMatcher:CharMatcher{
		override fun matches(value: Char): Boolean {
			return value.isLetter()
		}
	}

	object DigitMatcher:CharMatcher{
		override fun matches(value: Char): Boolean {
			return value.isDigit()
		}
	}

	object LetterOrDigitMatcher:CharMatcher{
		override fun matches(value: Char): Boolean {
			return value.isLetterOrDigit()
		}
	}

	object WhitespaceMatcher:CharMatcher{
		override fun matches(value: Char): Boolean {
			return value.isWhitespace()
		}
	}
	//endregion
}
