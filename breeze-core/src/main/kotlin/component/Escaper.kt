// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 转义器。
 *
 * 转义器用于基于指定的（语言）规则，转义和反转义字符串。
 *
 * 注意：不考虑转义特殊的Unicode字符。
 */
interface Escaper : Component {
	/**
	 * 转义指定的字符串。
	 */
	fun escape(value: String): String

	/**
	 * 反转义指定的字符串。
	 */
	fun unescape(value: String): String
}

