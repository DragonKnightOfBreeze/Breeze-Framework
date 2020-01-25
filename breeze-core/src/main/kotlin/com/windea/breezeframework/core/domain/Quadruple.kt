package com.windea.breezeframework.core.domain

import java.io.*

/**四元数元组。*/
data class Quadruple<out A, out B, out C, out D>(
	val first: A,
	val second: B,
	val third: C,
	val fourth: D
) : Serializable {
	override fun toString(): String = "($first, $second, $third, $fourth)"
}

/**将单一元素类型的四元素元组转化成列表。*/
fun <T> Quadruple<T, T, T, T>.toList() = listOf(first, second, third, fourth)
