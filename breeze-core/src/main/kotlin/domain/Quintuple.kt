package com.windea.breezeframework.core.domain

import java.io.*

/**Represents a tuple of five values.*/
data class Quintuple<out A, out B, out C, out D, out E>(
	val first: A,
	val second: B,
	val third: C,
	val fourth: D,
	val fifth: E
) : Serializable {
	override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"
}
