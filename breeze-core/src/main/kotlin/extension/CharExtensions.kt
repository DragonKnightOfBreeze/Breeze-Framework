// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("CharExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extension

/**
 * 重复当前字符到指定次数。
 * @see com.windea.breezeframework.core.extension.repeat
 */
operator fun Char.times(n: Int): String {
	return this.repeat(n)
}


/**重复当前字符到指定次数。*/
fun Char.repeat(n: Int): String {
	return String(CharArray(n) { this })
}
