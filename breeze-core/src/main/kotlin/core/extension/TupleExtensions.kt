// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("TupleExtensions")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.model.*
import icu.windea.breezeframework.core.type.*

//关于Kotlin元组：
//* 元组的元素类型可以不同，并且元素个数应当是有限的
//* 元组应当是data class，继承自Serializable，实现自定义的toString方法，并且实现toList扩展方法。

//region entry extensions
/**
 * 构建一个二元素元组。
 *
 * Construct a tuple of two values.
 */
fun <A, B> tupleOf(first: A, second: B): Tuple2<A, B> {
	return Tuple2(first, second)
}

/**
 * 构建一个三元素元组。
 *
 * Construct a tuple of three values.
 */
fun <A, B, C> tupleOf(first: A, second: B, third: C): Tuple3<A, B, C> {
	return Tuple3(first, second, third)
}

/**
 * 构建一个四元素元组。
 *
 * Construct a tuple of four values.
 */
fun <A, B, C, D> tupleOf(first: A, second: B, third: C, fourth: D): Tuple4<A, B, C, D> {
	return Tuple4(first, second, third, fourth)
}

/**
 * 构建一个五元素元组。
 *
 * Construct a tuple of five values.
 */
fun <A, B, C, D, E> tupleOf(first: A, second: B, third: C, fourth: D, fifth: E): Tuple5<A, B, C, D, E> {
	return Tuple5(first, second, third, fourth, fifth)
}

/**
 * 构建一个流元素元组。
 *
 * Construct a tuple of six values.
 */
fun <A, B, C, D, E, F> tupleOf(first: A, second: B, third: C, fourth: D, fifth: E, sixth: F): Tuple6<A, B, C, D, E, F> {
	return Tuple6(first, second, third, fourth, fifth, sixth)
}
//endregion

//region build extensions
/**从二元素元组构造三元素元组。*/
infix fun <A, B, C> Tuple2<A, B>.fromTo(that: C): Tuple3<A, B, C> {
	return Tuple3(first, second, that)
}

/**从三元素元组构造四元素元组。*/
infix fun <A, B, C, D> Tuple3<A, B, C>.fromTo(that: D): Tuple4<A, B, C, D> {
	return Tuple4(first, second, third, that)
}

/**从四元素元组构造五元素元组。*/
infix fun <A, B, C, D, E> Tuple4<A, B, C, D>.fromTo(that: E): Tuple5<A, B, C, D, E> {
	return Tuple5(first, second, third, fourth, that)
}

/**从五元素元组构造六元素元组。*/
infix fun <A, B, C, D, E, F> Tuple5<A, B, C, D, F>.fromTo(that: F): Tuple6<A, B, C, D, F, F> {
	return Tuple6(first, second, third, fourth, fifth, that)
}
//endregion

//region common extensions
/**映射单一元素类型的二元素元组的元素。*/
inline fun <T, R> TypedTuple2<T>.map(transform: (T) -> R): TypedTuple2<R> {
	return TypedTuple2(transform(first), transform(second))
}

/**映射单一元素类型的三元素元组的元素。*/
inline fun <T, R> TypedTuple3<T>.map(transform: (T) -> R): TypedTuple3<R> {
	return TypedTuple3(transform(first), transform(second), transform(third))
}

/**映射单一元素类型的四元素元组的元素。*/
inline fun <T, R> TypedTuple4<T>.map(transform: (T) -> R): TypedTuple4<R> {
	return TypedTuple4(transform(first), transform(second), transform(third), transform(fourth))
}

/**映射单一元素类型的五元素元组的元素。*/
inline fun <T, R> TypedTuple5<T>.map(transform: (T) -> R): TypedTuple5<R> {
	return TypedTuple5(transform(first), transform(second), transform(third), transform(fourth), transform(fifth))
}

/**映射单一元素类型的六元素元组的元素。*/
inline fun <T, R> TypedTuple6<T>.map(transform: (T) -> R): TypedTuple6<R> {
	return TypedTuple6(transform(first), transform(second), transform(third), transform(fourth), transform(fifth), transform(sixth))
}
//endregion

//region convert extensions
/**将单一元素类型的三元素元组转化为列表。*/
fun <T> TypedTuple3<T>.toList() = listOf(first, second, third)

/**将单一元素类型的四元素元组转化为列表。*/
fun <T> TypedTuple4<T>.toList() = listOf(first, second, third, fourth)

/**将单一元素类型的五元素元组转化为列表。*/
fun <T> TypedTuple5<T>.toList() = listOf(first, second, third, fourth, fifth)

/**将单一元素类型的六元素元组转化为列表。*/
fun <T> TypedTuple6<T>.toList() = listOf(first, second, third, fourth, fifth, sixth)


/**将字符类型的二元素元组转化为字符范围。*/
fun TypedTuple2<Char>.toRange(): CharRange = first..second

/**将整数类型的二元素元组转化为整数范围。*/
fun TypedTuple2<Int>.toRange(): IntRange = first..second

/**将长整数类型的二元素元组转化为长整数范围。*/
fun TypedTuple2<Long>.toRange(): LongRange = first..second

/**将单一元素类型的二元素元组转化为范围。*/
fun <T : Comparable<T>> TypedTuple2<T>.toRange(): ClosedRange<T> = first..second
//endregion
