// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("CharExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package icu.windea.breezeframework.core.extension

/**
 * 重复当前字符到指定次数。
 * @see icu.windea.breezeframework.core.extension.repeat
 */
operator fun Char.times(n: Int): String {
	return this.repeat(n)
}


/**重复当前字符到指定次数。*/
fun Char.repeat(n: Int): String {
	return String(CharArray(n) { this })
}

@ExperimentalUnsignedTypes
fun Char.toUInt():UInt{
	return toInt().toUInt()
}

@ExperimentalUnsignedTypes
fun Char.toULong():ULong{
	return toLong().toULong()
}
