// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

import java.io.*

/**
 * Represents a tuple of four values.
 *
 * @see Pair
 * @see Triple
 * @see Quadruple
 * @see Quintuple
 */
data class Quadruple<out A, out B, out C, out D>(
	val first: A,
	val second: B,
	val third: C,
	val fourth: D,
) : Serializable {
	override fun toString(): String = "($first, $second, $third, $fourth)"
}
