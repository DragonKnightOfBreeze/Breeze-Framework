// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

/**
 * 字符串模式。
 *
 * 字符串模式用于表示字符串符合某种格式或规则。
 */
interface StringPattern : Component {
	/**
	 * 判断指定的字符串是否匹配指定的字符串模式。
	 */
	fun matches(value: String): Boolean

	companion object Registry : AbstractComponentRegistry<StringPattern>() {
		init {
			registerDefaultStringPatterns()
		}

		private fun registerDefaultStringPatterns() {
			register(BooleanPattern)
			register(WildcardBooleanPattern)
			register(IntegerPattern)
			register(NumberPattern)
			register(NumericPattern)
			register(AlphaPattern)
			register(AlphanumericPattern)
		}
	}

	//region Default String Patterns
	object BooleanPattern : StringPattern {
		private val values = arrayOf("true", "false")

		override fun matches(value: String): Boolean {
			return value in values
		}
	}

	object WildcardBooleanPattern : StringPattern {
		private val values = arrayOf("true", "false", "yes", "no", "on", "off")

		override fun matches(value: String): Boolean {
			return value in values
		}
	}

	object IntegerPattern : StringPattern {
		private val signs = charArrayOf('+', '-')

		override fun matches(value: String): Boolean {
			val sign = value.firstOrNull()
			val noSignValue = if(sign == null) value else if(sign in signs) value.drop(1) else return false
			return noSignValue.any { it.isDigit() }
		}
	}

	object NumberPattern : StringPattern {
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

	object NumericPattern : StringPattern {
		override fun matches(value: String): Boolean {
			return value.isNotEmpty() && value.all { it.isLetter() }
		}
	}

	object AlphaPattern : StringPattern {
		override fun matches(value: String): Boolean {
			return value.isNotEmpty() && value.all { it.isDigit() }
		}
	}

	object AlphanumericPattern : StringPattern {
		override fun matches(value: String): Boolean {
			return value.isNotEmpty() && value.all { it.isLetterOrDigit() }
		}
	}
	//endregion
}

