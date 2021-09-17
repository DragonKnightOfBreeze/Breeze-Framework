// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("EscaperExtensions")

package icu.windea.breezeframework.core.component.extension

import icu.windea.breezeframework.core.component.Escaper

/**
 * 根据指定的转义器，转义当前字符串。
 *
 * @see Escaper
 */
fun String.escapeBy(escaper: Escaper): String {
	return escaper.escape(this)
}

/**
 * 根据指定的转义器，反转义当前字符串。
 *
 * @see Escaper
 */
fun String.unescapeBy(escaper: Escaper): String {
	return escaper.unescape(this)
}
