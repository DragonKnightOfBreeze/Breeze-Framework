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
}
