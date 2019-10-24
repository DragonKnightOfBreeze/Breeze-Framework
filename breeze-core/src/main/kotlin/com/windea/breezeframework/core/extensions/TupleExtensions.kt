@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import java.io.*

//REGION type alias

typealias Tuple2<A, B> = Pair<A, B>

typealias Tuple3<A, B, C> = Triple<A, B, C>

typealias Tuple4<A, B, C, D> = Quadruple<A, B, C, D>


typealias TypedTuple2<T> = Pair<T, T>

typealias TypedTuple3<T> = Triple<T, T, T>

typealias TypedTuple4<T> = Quadruple<T, T, T, T>

//REGION quadruple class and extensions (necessary for Quaternion)

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

//REGION build extensions

/**从二元素元组构造三元素元组。*/
infix fun <A, B, C> Pair<A, B>.fromTo(that: C): Triple<A, B, C> = Triple(first, second, that)

/**从三元素元组构造四元素元组。*/
infix fun <A, B, C, D> Triple<A, B, C>.thenTo(that: D): Quadruple<A, B, C, D> = Quadruple(first, second, third, that)

//REGION convert extensions

/**映射单一元素类型的二元素元组的元素。*/
inline fun <T, R> Pair<T, T>.map(transform: (T) -> R): Pair<R, R> =
	Pair(transform(first), transform(second))

/**映射单一元素类型的三元素元组的元素。*/
inline fun <T, R> Triple<T, T, T>.map(transform: (T) -> R): Triple<R, R, R> =
	Triple(transform(first), transform(second), transform(third))

/**映射单一元素类型的三元素元组的元素。*/
inline fun <T, R> Quadruple<T, T, T, T>.map(transform: (T) -> R): Quadruple<R, R, R, R> =
	Quadruple(transform(first), transform(second), transform(third), transform(fourth))


/**将单一元素类型的二元素元组转化为范围。*/
inline fun <T : Comparable<T>> Pair<T, T>.toRange(): ClosedRange<T> = first..second
