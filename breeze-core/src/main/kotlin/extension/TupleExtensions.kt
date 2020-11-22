// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("TupleExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.type.*

//对于元组的需求：
//元组的元素类型可以不同，并且元素个数应当是有限的（这里最多提供五元素元组）
//元组应当是data class，实现自定义的toString方法，并且实现toList扩展方法。
//应当提供一系列用于创建元组的中缀扩展。
//提供其他必要的扩展方法。

//region Build extensions
/**从二元素元组构造三元素元组。*/
infix fun <A, B, C> Tuple2<A, B>.fromTo(that: C): Tuple3<A, B, C> = Tuple3(first, second, that)

/**从三元素元组构造四元素元组。*/
infix fun <A, B, C, D> Tuple3<A, B, C>.fromTo(that: D): Tuple4<A, B, C, D> = Tuple4(first, second, third, that)

/**从四元素元组构造五元素元组。*/
infix fun <A, B, C, D, E> Tuple4<A, B, C, D>.fromTo(that: E): Tuple5<A, B, C, D, E> = Tuple5(first, second, third, fourth, that)
//endregion

//region Common Extensions
/**映射单一元素类型的二元素元组的元素。*/
inline fun <T, R> TypedTuple2<T>.map(transform: (T) -> R): TypedTuple2<R> =
	TypedTuple2(transform(first), transform(second))

/**映射单一元素类型的三元素元组的元素。*/
inline fun <T, R> TypedTuple3<T>.map(transform: (T) -> R): TypedTuple3<R> =
	TypedTuple3(transform(first), transform(second), transform(third))

/**映射单一元素类型的四元素元组的元素。*/
inline fun <T, R> TypedTuple4<T>.map(transform: (T) -> R): TypedTuple4<R> =
	TypedTuple4(transform(first), transform(second), transform(third), transform(fourth))

/**映射单一元素类型的五元素元组的元素。*/
inline fun <T, R> TypedTuple5<T>.map(transform: (T) -> R): TypedTuple5<R> =
	TypedTuple5(transform(first), transform(second), transform(third), transform(fourth), transform(fifth))
//endregion

//region Convert Extensions
/**将单一元素类型的三元素元组转化为列表。*/
fun <T> TypedTuple3<T>.toList() = listOf(first, second, third)

/**将单一元素类型的四元素元组转化为列表。*/
fun <T> TypedTuple4<T>.toList() = listOf(first, second, third, fourth)

/**将单一元素类型的五元素元组转化为列表。*/
fun <T> TypedTuple5<T>.toList() = listOf(first, second, third, fourth, fifth)


/**将字符类型的二元素元组转化为字符范围。*/
inline fun TypedTuple2<Char>.toRange(): CharRange = first..second

/**将整数类型的二元素元组转化为整数范围。*/
inline fun TypedTuple2<Int>.toRange(): IntRange = first..second

/**将长整数类型的二元素元组转化为长整数范围。*/
inline fun TypedTuple2<Long>.toRange(): LongRange = first..second

/**将单一元素类型的二元素元组转化为范围。*/
inline fun <T : Comparable<T>> TypedTuple2<T>.toRange(): ClosedRange<T> = first..second
//endregion
