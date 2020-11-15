// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core

import java.io.*

/**
 * Represents a tuple of five values.
 *
 * @see Pair
 * @see Triple
 * @see Quadruple
 * @see Quintuple
 *
 */
data class Quintuple<out A, out B, out C, out D, out E>(
	val first: A,
	val second: B,
	val third: C,
	val fourth: D,
	val fifth: E,
) : Serializable {
	override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"
}
