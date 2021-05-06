// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 字符模式。
 *
 * 字符模式用于表示字符符合某种格式或规则。
 */
interface CharPattern: Component {
	/**
	 * 判断指定的字符是否匹配。
	 */
	fun matches(value:Char):Boolean

	companion object Registry: AbstractComponentRegistry<CharPattern>(){
		override fun registerDefault() {
			register(LetterPattern)
			register(DigitPattern)
			register(LetterOrDigitPattern)
			register(WhitespacePattern)
		}
	}

	//region Char Patterns
	object LetterPattern:CharPattern{
		override fun matches(value: Char): Boolean {
			return value.isLetter()
		}
	}

	object DigitPattern:CharPattern{
		override fun matches(value: Char): Boolean {
			return value.isDigit()
		}
	}

	object LetterOrDigitPattern:CharPattern{
		override fun matches(value: Char): Boolean {
			return value.isLetterOrDigit()
		}
	}

	object WhitespacePattern:CharPattern{
		override fun matches(value: Char): Boolean {
			return value.isWhitespace()
		}
	}
	//endregion
}
