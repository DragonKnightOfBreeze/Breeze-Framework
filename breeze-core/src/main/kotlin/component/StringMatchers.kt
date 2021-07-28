// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

object StringMatchers : ComponentRegistry<StringMatcher>() {
	//region Implementations
	object AnyMatcher : AbstractStringMatcher() {
		override fun matches(value: String): Boolean {
			return true
		}
	}

	object NoneMatcher : AbstractStringMatcher() {
		override fun matches(value: String): Boolean {
			return false
		}
	}

	object BooleanMatcher : AbstractStringMatcher() {
		private val values = arrayOf("true", "false")

		override fun matches(value: String): Boolean {
			return value in values
		}
	}

	object WildcardBooleanMatcher : AbstractStringMatcher() {
		private val values = arrayOf("true", "false", "yes", "no", "on", "off")

		override fun matches(value: String): Boolean {
			return value.lowercase() in values
		}
	}

	object IntMatcher : AbstractStringMatcher() {
		private val signs = charArrayOf('+', '-')

		override fun matches(value: String): Boolean {
			val sign = value.firstOrNull()
			val noSignValue = if(sign == null) value else if(sign in signs) value.drop(1) else return false
			return noSignValue.any { it.isDigit() }
		}
	}

	object NumberMatcher : AbstractStringMatcher() {
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

	object AlphaMatcher : AbstractStringMatcher() {
		override fun matches(value: String): Boolean {
			return value.isNotEmpty() && value.all { it.isLetter() }
		}
	}

	object NumericMatcher : AbstractStringMatcher() {
		override fun matches(value: String): Boolean {
			return value.isNotEmpty() && value.all { it.isDigit() }
		}
	}

	object AlphanumericMatcher : AbstractStringMatcher() {
		override fun matches(value: String): Boolean {
			return value.isNotEmpty() && value.all { it.isLetterOrDigit() }
		}
	}

	object BlankMatcher : AbstractStringMatcher() {
		override fun matches(value: String): Boolean {
			return value.isBlank()
		}
	}
	//endregion

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
