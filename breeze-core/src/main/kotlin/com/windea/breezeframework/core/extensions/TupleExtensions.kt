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

//region convert extensions
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

/**将浮点数二元素元组转化为二维向量。*/
inline fun Pair<Float, Float>.toVector2(): Vector2 = Vector2(this.first, this.second)

/**将浮点数三元素元组转化为三维向量。*/
inline fun Triple<Float, Float, Float>.toVector3(): Vector3 = Vector3(this.first, this.second, this.third)
//endregion
