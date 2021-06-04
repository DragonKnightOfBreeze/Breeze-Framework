// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

import java.io.*

/**
 * 代表了一个四元素元组。
 *
 * Represents a tuple of four values.
 */
data class Tuple4<out A, out B, out C, out D>(
	val first: A,
	val second: B,
	val third: C,
	val fourth: D
) : Serializable {
	override fun toString(): String = "($first, $second, $third, $fourth)"
}
