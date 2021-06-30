// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 字符串匹配器。
 *
 * 字符串匹配器用于表示字符串是否匹配某种格式。
 */
interface StringMatcher : Matcher<String> {
	companion object Registry : AbstractComponentRegistry<StringMatcher>() {
		override fun registerDefault() {
			register(AnyMatcher)
			register(NoneMatcher)
			register(BooleanMatcher)
			register(WildcardBooleanMatcher)
			register(IntMatcher)
			register(NumberMatcher)
			register(AlphaMatcher)
			register(NumericMatcher)
			register(AlphanumericMatcher)
			register(BlankMatcher)
		}
	}

	//region String Patterns
	object AnyMatcher:StringMatcher{
		override fun matches(value: String): Boolean {
			return true
		}
	}

	object NoneMatcher:StringMatcher{
		override fun matches(value: String): Boolean {
			return false
		}
	}

	object BooleanMatcher : StringMatcher {
		private val values = arrayOf("true", "false")

		override fun matches(value: String): Boolean {
			return value in values
		}
	}

	object WildcardBooleanMatcher : StringMatcher {
		private val values = arrayOf("true", "false", "yes", "no", "on", "off")

		override fun matches(value: String): Boolean {
			return value.lowercase() in values
		}
	}

	object IntMatcher : StringMatcher {
		private val signs = charArrayOf('+', '-')

		override fun matches(value: String): Boolean {
			val sign = value.firstOrNull()
			val noSignValue = if(sign == null) value else if(sign in signs) value.drop(1) else return false
			return noSignValue.any { it.isDigit() }
		}
	}

	object NumberMatcher : StringMatcher {
		private val signs = charArrayOf('+', '-')

		override fun matches(value: String): Boolean {
			val sign = value.firstOrNull()
			val noSignValue = if(sign == null) value else if(sign in signs) value.drop(1) else return false
			var hasDot = false
			return noSignValue.any {
				if(it == '.') {
					if(!hasDot) hasDot = true else return false
				}
				it.isDigit()
			}
		}
	}

	object AlphaMatcher : StringMatcher {
		override fun matches(value: String): Boolean {
			return value.isNotEmpty() && value.all { it.isLetter() }
		}
	}

	object NumericMatcher : StringMatcher {
		override fun matches(value: String): Boolean {
			return value.isNotEmpty() && value.all { it.isDigit() }
		}
	}

	object AlphanumericMatcher : StringMatcher {
		override fun matches(value: String): Boolean {
			return value.isNotEmpty() && value.all { it.isLetterOrDigit() }
		}
	}

	object BlankMatcher:StringMatcher{
		override fun matches(value: String): Boolean {
			return value.isBlank()
		}
	}
	//endregion
}

