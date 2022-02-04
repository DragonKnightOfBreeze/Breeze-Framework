// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.annotation.TrickyApi
import icu.windea.breezeframework.core.annotation.UselessCallOnNotNullType
import java.lang.reflect.Type
import java.util.LinkedHashMap

//region common extensions
/**
 * 得到当前序列的最为适配的键类型。
 */
@TrickyApi
@Deprecated("Tricky implementation.", level = DeprecationLevel.HIDDEN)
inline val <reified T> Sequence<T>.elementType: Type get() = javaTypeOf<T>()

/**
 * 判断当前序列中的所有元素是否被另一序列包含。
 */
infix fun <T> Sequence<T>.allIn(other: Sequence<T>): Boolean {
	return this.all { it in other }
}

/**
 * 判断当前序列中的任意元素是否被另一序列包含。
 */
infix fun <T> Sequence<T>.anyIn(other: Sequence<T>): Boolean {
	return this.any { it in other }
}

/**
 * 判断当前序列是否以指定元素开始。
 */
@Deprecated("Unnecessary extension.", level = DeprecationLevel.HIDDEN)
inline infix fun <T> Sequence<T>.startsWith(element: T): Boolean = this.firstOrNull() == element

/**
 * 判断当前序列是否以任意指定元素开始。
 */
@Deprecated("Unnecessary extension.", level = DeprecationLevel.HIDDEN)
inline infix fun <T> Sequence<T>.startsWith(elements: Array<out T>): Boolean = this.firstOrNull() in elements


/**
 * 根据指定的条件，内连接当前序列和另一个序列中的元素，以当前数组长度为准，过滤不满足的情况。
 */
inline fun <T, R> Sequence<T>.innerJoin(other: Sequence<R>, crossinline predicate: (T, R) -> Boolean): Sequence<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}


/**
 * 根据指定的深度递归平滑化当前序列。
 * 如果指定了深度，则会递归映射到该深度，或者直到找不到集合类型的元素为止。默认不指定深度。
 *
 * 支持[Array]、[Iterable]和[Sequence]。
 */
@JvmOverloads
fun <T> Sequence<*>.deepFlatten(depth: Int = -1): List<T> {
	return this.doDeepFlatten(depth)
}


/**
 * 将当前键值对序列转化为新的可变映射。
 */
fun <K, V> Sequence<Pair<K, V>>.toMutableMap(): MutableMap<K, V> {
	return this.toMap(LinkedHashMap())
}


/**
 * 将当前序列转化为新的以键为值的映射。
 */
fun <T> Sequence<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associateBy({ it.index.toString() }, { it.value })
}
//endregion

//region specific extensions
/**
 * 去除起始的空字符串。
 */
inline fun <T : CharSequence> Sequence<T>.dropEmpty(): Sequence<T> {
	return this.dropWhile { it.isEmpty() }
}

/**
 * 去除起始的空白字符串。
 */
inline fun <T : CharSequence> Sequence<T>.dropBlank(): Sequence<T> {
	return this.dropWhile { it.isBlank() }
}


/**
 * 过滤当前序列中为空字符串的元素。
 */
inline fun <T : CharSequence> Sequence<T>.filterNotEmpty(): Sequence<T> {
	return this.filter { it.isNotEmpty() }
}

/**
 * 过滤当前序列中为空白字符串的元素。
 */
inline fun <T : CharSequence> Sequence<T>.filterNotBlank(): Sequence<T> {
	return this.filter { it.isNotBlank() }
}

/**
 * 过滤当前序列中为null或空字符串的元素。
 */
@UselessCallOnNotNullType
inline fun <T : CharSequence> Sequence<T?>.filterNotNullOrEmpty(): Sequence<T> {
	return this.filter { it.isNotNullOrEmpty() } as Sequence<T>
}

/**
 * 过滤当前序列中为null或空白字符串的元素。
 */
@UselessCallOnNotNullType
inline fun <T : CharSequence> Sequence<T?>.filterNotNullOrBlank(): Sequence<T> {
	return this.filter { it.isNotNullOrBlank() } as Sequence<T>
}
//endregion
