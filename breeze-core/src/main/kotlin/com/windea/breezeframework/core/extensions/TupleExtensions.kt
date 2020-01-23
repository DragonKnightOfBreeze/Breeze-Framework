@file:JvmName("TupleExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.domain.core.*
import com.windea.breezeframework.core.domain.math.*

//region build extensions
/**从二元素元组构造三元素元组。*/
infix fun <A, B, C> Pair<A, B>.fromTo(that: C): Triple<A, B, C> = Triple(first, second, that)

/**从三元素元组构造四元素元组。*/
infix fun <A, B, C, D> Triple<A, B, C>.thenTo(that: D): Quadruple<A, B, C, D> = Quadruple(first, second, third, that)
//endregion

//region common extensions
/**映射单一元素类型的二元素元组的元素。*/
inline fun <T, R> Pair<T, T>.map(transform: (T) -> R): Pair<R, R> =
	Pair(transform(first), transform(second))

/**映射单一元素类型的三元素元组的元素。*/
inline fun <T, R> Triple<T, T, T>.map(transform: (T) -> R): Triple<R, R, R> =
	Triple(transform(first), transform(second), transform(third))

/**映射单一元素类型的三元素元组的元素。*/
inline fun <T, R> Quadruple<T, T, T, T>.map(transform: (T) -> R): Quadruple<R, R, R, R> =
	Quadruple(transform(first), transform(second), transform(third), transform(fourth))


/**根据当前二元素元组和另一个二元素元组，映射单一元素类型的二元素元组的元素。*/
inline fun <T, R, V> Pair<T, T>.zip(other: Pair<R, R>, transform: (T, R) -> V): Pair<V, V> =
	Pair(transform(first, other.first), transform(second, other.second))

/**根据当前三元素元组和另一个三元素元组，映射单一元素类型的三元素元组的元素。*/
inline fun <T, R, V> Triple<T, T, T>.zip(other: Triple<R, R, R>, transform: (T, R) -> V): Triple<V, V, V> =
	Triple(transform(first, other.first), transform(second, other.second), transform(third, other.third))

/**根据当前四元素元组和另一个三元素元组，映射单一元素类型的四元素元组的元素。*/
inline fun <T, R, V> Quadruple<T, T, T, T>.zip(other: Quadruple<R, R, R, R>, transform: (T, R) -> V): Quadruple<V, V, V, V> =
	Quadruple(transform(first, other.first), transform(second, other.second), transform(third, other.third), transform(fourth, other.fourth))
//endregion

//region convert extensions
/**将字符类型的二元素元组转化为字符范围。*/
inline fun Pair<Char, Char>.toRange(): CharRange = first..second

/**将整数类型的二元素元组转化为整数范围。*/
inline fun Pair<Int, Int>.toRange(): IntRange = first..second

/**将长整数类型的二元素元组转化为长整数范围。*/
inline fun Pair<Long, Long>.toRange(): LongRange = first..second

/**将单一元素类型的二元素元组转化为范围。*/
inline fun <T : Comparable<T>> Pair<T, T>.toRange(): ClosedRange<T> = first..second


/**将浮点数二元素元组转化为二维向量。*/
inline fun Pair<Float, Float>.toVector2(): Vector2 = Vector2(first, second)

/**将浮点数三元素元组转化为三维向量。*/
inline fun Triple<Float, Float, Float>.toVector3(): Vector3 = Vector3(first, second, third)
//endregion
