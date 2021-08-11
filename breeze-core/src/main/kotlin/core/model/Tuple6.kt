// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

import java.io.*

/**
 * 代表了一个六元素元组。
 *
 * Represents a tuple of six values.
 */
data class Tuple6<out A, out B, out C, out D, out E, out F>(
	val first: A,
	val second: B,
	val third: C,
	val fourth: D,
	val fifth: E,
	val sixth: F
) : Serializable {
	override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth)"
}
