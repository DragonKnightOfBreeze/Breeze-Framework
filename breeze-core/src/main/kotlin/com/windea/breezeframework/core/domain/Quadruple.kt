package com.windea.breezeframework.core.domain

import java.io.*

/**四元素元组。*/
data class Quadruple<out A, out B, out C, out D>(
	val first: A,
	val second: B,
	val third: C,
	val fourth: D
) : Serializable {
	override fun toString(): String = "($first, $second, $third, $fourth)"
}
