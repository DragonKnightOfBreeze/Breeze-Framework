@file:JvmName("CollectionExtensions")
@file:Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.enums.text.*
import java.util.*
import java.util.concurrent.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap
import kotlin.collections.LinkedHashSet
import kotlin.random.Random

//注意：可以通过添加注解 @Suppress("CANNOT_CHECK_FOR_ERASED") 检查数组的泛型如 array is Array<String>
//注意：可以通过添加注解 @Suppress("UNSUPPORTED") 启用字面量数组如 [1, 2, 3]
//注意：某些情况下，如果直接参照标准库的写法编写扩展方法，会报编译器错误

//region portal extensions
/**构建一个集并事先过滤空的元素。*/
fun <T : Any> setOfNotNull(element: T?) = if(element != null) listOf(element) else emptyList()

/**构建一个集并事先过滤空的元素。*/
fun <T : Any> setOfNotNull(vararg elements: T?): Set<T> =
	LinkedHashSet<T>().apply { for(element in elements) if(element != null) add(element) }

/**构建一个映射并事先过滤值为空的键值对。*/
fun <K, V : Any> mapOfValuesNotNull(pair: Pair<K, V?>) = if(pair.second != null) mapOf(pair) else emptyMap()

/**构建一个映射并事先过滤值为空的键值对。*/
fun <K, V : Any> mapOfValuesNotNull(vararg pairs: Pair<K, V?>) =
	LinkedHashMap<K, V>().apply { for((key, value) in pairs) if(value != null) put(key, value) }


/**构建一个空的枚举集。*/
inline fun <reified T : Enum<T>> enumSetOf(): EnumSet<T> = EnumSet.noneOf(T::class.java)

/**构建一个枚举集。*/
fun <T : Enum<T>> enumSetOf(first: T, vararg elements: T): EnumSet<T> = EnumSet.of(first, *elements)

/**构建一个包含所有枚举值的枚举集。*/
inline fun <reified T : Enum<T>> enumSetOfAll(): EnumSet<T> = EnumSet.allOf(T::class.java)

/**构建一个空的枚举映射。*/
inline fun <reified K : Enum<K>, V> enumMapOf(): EnumMap<K, V> = EnumMap(K::class.java)

/**构建一个枚举映射。*/
fun <K : Enum<K>, V> enumMapOf(vararg pairs: Pair<K, V>): EnumMap<K, V> = EnumMap(pairs.toMap())


/**构建一个空的线程安全的并发列表。*/
inline fun <T> concurrentListOf(): CopyOnWriteArrayList<T> = CopyOnWriteArrayList()

/**构建一个线程安全的并发列表。*/
fun <T> concurrentListOf(vararg elements: T): CopyOnWriteArrayList<T> = CopyOnWriteArrayList(elements)

/**构建一个空的线程安全的并发集。*/
inline fun <T> concurrentSetOf(): CopyOnWriteArraySet<T> = CopyOnWriteArraySet()

/**构建一个线程安全的并发集。*/
fun <T> concurrentSetOf(vararg elements: T): CopyOnWriteArraySet<T> = CopyOnWriteArraySet(elements.toSet())

/**构建一个空的线程安全的并发映射。*/
inline fun <K, V> concurrentMapOf(): ConcurrentHashMap<K, V> = ConcurrentHashMap()

/**构建一个线程安全的并发映射。*/
fun <K, V> concurrentMapOf(vararg pairs: Pair<K, V>): ConcurrentHashMap<K, V> = ConcurrentHashMap(pairs.toMap())
//endregion

//region operator override extensions
/**@see kotlin.collections.slice*/
operator fun <T> Array<out T>.get(indices: IntRange): List<T> = this.slice(indices)

/**@see kotlin.collections.slice*/
operator fun <T> List<T>.get(indices: IntRange): List<T> = this.slice(indices)

/**@see com.windea.breezeframework.core.extensions.repeat*/
operator fun <T> Array<out T>.times(n: Int): List<T> = this.toList().repeat(n)

/**@see com.windea.breezeframework.core.extensions.repeat*/
operator fun <T> List<T>.times(n: Int): List<T> = this.repeat(n)

/**@see kotlin.collections.chunked*/
operator fun <T> Array<out T>.div(n: Int): List<List<T>> = this.toList().chunked(n)

/**@see kotlin.collections.chunked*/
operator fun <T> Iterable<T>.div(n: Int): List<List<T>> = this.chunked(n)
//endregion

//region common extensions
/**判断两个列表的结构是否相等。即，判断长度、元素、元素顺序是否相等。*/
infix fun <T> List<T>.contentEquals(other: List<T>): Boolean {
	//NOTE 某些具体的实现类的equals方法与这个方法应是等效的
	return this == other || this.size == other.size && (this zip other).all { (a, b) -> a == b }
}

/**判断两个列表的结构是否递归相等。即，判断长度、元素、元素顺序是否递归相等。*/
infix fun <T> List<T>.contentDeepEquals(other: List<T>): Boolean {
	return this == other || this.size == other.size && (this zip other).all { (a, b) ->
		when {
			a is Array<*> && b is Array<*> -> a contentDeepEquals b
			a is List<*> && b is List<*> -> a contentDeepEquals b
			else -> a == b
		}
	}
}


/**判断当前数组中的所有元素是否被另一数组包含。*/
infix fun <T> Array<out T>.allIn(other: Array<out T>): Boolean = this.all { it in other }

/**判断当前数组中的所有元素是否被另一集合包含。*/
infix fun <T> Array<out T>.allIn(other: Iterable<T>): Boolean = this.all { it in other }

/**判断当前集合中的所有元素是否被另一数组包含。*/
infix fun <T> Iterable<T>.allIn(other: Array<out T>): Boolean = this.all { it in other }

/**判断当前集合中的所有元素是否被另一集合包含。*/
infix fun <T> Iterable<T>.allIn(other: Iterable<T>): Boolean = this.all { it in other }

/**判断当前序列中的所有元素是否被另一序列包含。*/
infix fun <T> Sequence<T>.allIn(other: Sequence<T>): Boolean = this.all { it in other }

/**判断当前数组中的任意元素是否被另一数组包含。*/
infix fun <T> Array<out T>.anyIn(other: Array<out T>): Boolean = this.any { it in other }

/**判断当前数组中的任意元素是否被另一集合包含。*/
infix fun <T> Array<out T>.anyIn(other: Iterable<T>): Boolean = this.any { it in other }

/**判断当前集合中的任意元素是否被另一数组包含。*/
infix fun <T> Iterable<T>.anyIn(other: Array<out T>): Boolean = this.any { it in other }

/**判断当前集合中的任意元素是否被另一集合包含。*/
infix fun <T> Iterable<T>.anyIn(other: Iterable<T>): Boolean = this.any { it in other }

/**判断当前序列中的任意元素是否被另一序列包含。*/
infix fun <T> Sequence<T>.anyIn(other: Sequence<T>): Boolean = this.any { it in other }


/**判断当前数组是否以指定元素开始。*/
inline infix fun <T> Array<out T>.startsWith(element: T): Boolean = this.firstOrNull() == element

/**判断当前数组是否以任意指定元素开始。*/
inline infix fun <T> Array<out T>.startsWith(elements: Array<out T>): Boolean = this.firstOrNull() in elements

/**判断当前集合是否以指定元素开始。*/
inline infix fun <T> Iterable<T>.startsWith(element: T): Boolean = this.firstOrNull() == element

/**判断当前集合是否以任意指定元素开始。*/
inline infix fun <T> Iterable<T>.startsWith(elements: Array<out T>): Boolean = this.firstOrNull() in elements

/**判断当前序列是否以指定元素开始。*/
inline infix fun <T> Sequence<T>.startsWith(element: T): Boolean = this.firstOrNull() == element

/**判断当前序列是否以任意指定元素开始。*/
inline infix fun <T> Sequence<T>.startsWith(elements: Array<out T>): Boolean = this.firstOrNull() in elements

/**判断当前数组是否以指定元素结束。*/
inline infix fun <T> Array<out T>.endsWith(element: T): Boolean = this.lastOrNull() == element

/**判断当前数组是否以任意指定元素结束。*/
inline infix fun <T> Array<out T>.endsWith(elements: Array<out T>): Boolean = this.lastOrNull() in elements

/**判断当前集合是否以指定元素结束。*/
inline infix fun <T> Iterable<T>.endsWith(element: T): Boolean = this.lastOrNull() == element

/**判断当前集合是否以任意指定元素结束。*/
inline infix fun <T> Iterable<T>.endsWith(elements: Array<out T>): Boolean = this.lastOrNull() in elements


/**判断当前序列是否为空。*/
inline fun <T> Sequence<T>.isEmpty() = !this.isNotEmpty()

/**判断当前序列是否不为空。*/
inline fun <T> Sequence<T>.isNotEmpty() = this.iterator().hasNext()


/**如果当前数组不为空，则返回本身，否则返回null。*/
@JvmSynthetic
inline fun <T> Array<out T>.orNull(): Array<out T>? = if(this.isEmpty()) null else this

/**如果当前集合不为空，则返回本身，否则返回null。*/
@JvmSynthetic
inline fun <T> Collection<T>.orNull(): Collection<T>? = if(this.isEmpty()) null else this

/**如果当前列表不为空，则返回本身，否则返回null。*/
@JvmSynthetic
inline fun <T> List<T>.orNull(): List<T>? = if(this.isEmpty()) null else this

/**如果当前集不为空，则返回本身，否则返回null。*/
@JvmSynthetic
inline fun <T> Set<T>.orNull(): Set<T>? = if(this.isEmpty()) null else this

/**如果当前映射不为空，则返回本身，否则返回null。*/
@JvmSynthetic
inline fun <K, V> Map<K, V>.orNull(): Map<K, V>? = if(this.isEmpty()) null else this

/**如果当前数组不为空，则返回转化后的值，否则返回本身。*/
@JvmSynthetic
@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER", "UPPER_BOUND_CANNOT_BE_ARRAY")
inline fun <C, R> C.ifNotEmpty(transform: (C) -> R): R where C : Array<*>, C : R =
	if(this.isEmpty()) this else transform(this)

/**如果当前集合不为空，则返回转化后的值，否则返回本身。*/
@JvmSynthetic
@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER")
inline fun <C, R> C.ifNotEmpty(transform: (C) -> R): R where C : Collection<*>, C : R =
	if(this.isEmpty()) this else transform(this)

/**如果当前映射不为空，则返回转化后的值，否则返回本身。*/
@JvmSynthetic
@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER")
inline fun <M, R> M.ifNotEmpty(transform: (M) -> R): R where M : Map<*, *>, M : R =
	if(this.isEmpty()) this else transform(this)


/**得到指定索引的元素，发生异常则得到默认值。*/
fun <T> Array<out T>.getOrDefault(index: Int, defaultValue: T): T {
	return this.getOrElse(index) { defaultValue }
}

/**得到指定索引的元素，发生异常则得到默认值。*/
fun <T> List<T>.getOrDefault(index: Int, defaultValue: T): T {
	return this.getOrElse(index) { defaultValue }
}


/**交换当前数组中指定的两个索引对应的元素。*/
fun <T> Array<T>.swap(index1: Int, index2: Int) {
	val temp = this[index1]
	this[index1] = this[index2]
	this[index2] = temp
}

/**交换当前列表中指定的两个索引对应的元素。*/
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
	//不委托给java.util.Collections.swap，因为数组的对应方法是私有的
	val temp = this[index1]
	this[index1] = this[index2]
	this[index2] = temp
}


/**得到随机元素。如果数组为空，则返回null。*/
inline fun <T> Array<out T>.randomOrNull(): T? = this.randomOrNull(Random)

/**得到随机元素。如果数组为空，则返回null。*/
fun <T> Array<out T>.randomOrNull(random: Random): T? {
	if(this.isEmpty()) return null
	return this[random.nextInt(size)]
}

/**得到随机元素。如果集合为空，则返回null。*/
inline fun <T> Collection<T>.randomOrNull(): T? = this.randomOrNull(Random)

/**得到随机元素。如果集合为空，则返回null。*/
fun <T> Collection<T>.randomOrNull(random: Random): T? {
	if(this.isEmpty()) return null
	return this.elementAt(random.nextInt(size))
}


/**重复当前列表中的元素到指定次数。*/
fun <T> List<T>.repeat(n: Int): List<T> {
	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }

	return ArrayList<T>(this.size * n).also { list -> repeat(n) { list += this } }
}

/**分别重复当前列表中的元素到指定次数。*/
fun <T> List<T>.repeatOrdinal(n: Int): List<T> {
	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }
	return ArrayList<T>(this.size * n).also { list -> for(e in this) repeat(n) { list += e } }
}

///**分别重复当前列表中的元素到指定次数，并映射为子列表。*/
//fun <T> List<T>.repeatChunked(n: Int): List<List<T>> {
//	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }
//	return ArrayList<List<T>>(this.size).also { list -> for(e in this) list += List(n) { e } }
//}


/**填充指定索引范围内的元素到当前列表。如果索引超出当前列表的长度，或为负数，则忽略。*/
fun <T> MutableList<T>.fillRange(indices: IntRange, value: T) {
	val fromIndex = indices.first.coerceIn(0, this.size)
	val toIndex = indices.last.coerceIn(fromIndex, this.size)
	for(index in fromIndex..toIndex) this[index] = value
}

/**填充指定元素到当前列表之前，直到指定长度。如果指定长度比当前长度小，则切割当前列表。返回填充后的列表。*/
fun <T> List<T>.fillStart(size: Int, value: T): List<T> {
	require(size >= 0) { "Desired size must be non-negative, but was $size." }

	if(size <= this.size) return this.subList(0, size)
	return List(size - this.size) { value } + this
}

/**填充指定元素到当前列表之后，直到指定长度。如果指定长度比当前长度小，则切割当前列表。返回填充后的列表。*/
fun <T> List<T>.fillEnd(size: Int, value: T): List<T> {
	require(size >= 0) { "Desired size must be non-negative, but was $size." }

	if(size <= this.size) return this.subList(0, size)
	return this + List(size - this.size) { value }
}


/**移除指定范围内的元素。*/
fun <T> MutableList<T>.removeAllAt(indices: IntRange) {
	for(index in indices.reversed()) this.removeAt(index)
}

/**将指定索引的元素插入到另一索引处。注意后者为移动前的索引，而非移动后的索引。*/
fun <T> MutableList<T>.moveAt(fromIndex: Int, toIndex: Int): T {
	val element = this[fromIndex]
	this.add(toIndex, element)
	return this.removeAt(fromIndex)
}

/**将指定索引范围内的元素插入到以另一索引为起点处。注意后者为移动前的索引，而非移动后的索引。*/
fun <T> MutableList<T>.moveAllAt(fromIndices: IntRange, toIndex: Int) {
	val elements = this.slice(fromIndices)
	this.addAll(toIndex, elements)
	this.removeAllAt(fromIndices)
}


///**将当前数组作为值的部分，与另一个数组作为键的部分组成映射。*/
//fun <K, V> Array<out V>.withKeys(other: Array<out K>): Map<K, V> = (other zip this).toMap()
//
///**将当前数组作为值的部分，与另一个列表作为键的部分组成映射。*/
//fun <K, V> Array<out V>.withKeys(other: List<K>): Map<K, V> = (other zip this).toMap()
//
///**将当前列表作为值的部分，与另一个数组作为键的部分组成映射。*/
//fun <K, V> List<V>.withKeys(other: Array<out K>): Map<K, V> = (other zip this).toMap()
//
///**将当前列表作为值的部分，与另一个列表作为键的部分组成映射。*/
//fun <K, V> List<V>.withKeys(other: List<K>): Map<K, V> = (other zip this).toMap()


/**根据指定的转化操作，将当前映射中的键与值加入到指定的容器。默认的转化操作是`$k=$v`。*/
fun <K, V, A : Appendable> Map<K, V>.joinTo(buffer: A, separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	transform: ((Map.Entry<K, V>) -> CharSequence)? = null): A {
	return this.entries.joinTo(buffer, separator, prefix, postfix, limit, truncated, transform)
}

/**根据指定的转化操作，将当前映射中的键与值加入到字符串。默认的转化操作是`$k=$v`。*/
fun <K, V> Map<K, V>.joinToString(separator: CharSequence = ", ", prefix: CharSequence = "", postfix: CharSequence = "",
	limit: Int = -1, truncated: CharSequence = "...", transform: ((Map.Entry<K, V>) -> CharSequence)? = null): String {
	return this.joinTo(StringBuilder(), separator, prefix, postfix, limit, truncated, transform).toString()
}


/**根据指定的转化操作，将当前数组中的元素加入到字符串。当数组为空时，直接返回空字符串且忽略前后缀。*/
@Deprecated("Redundant extension method: Please consider checking nullability instead.", ReplaceWith(joinToStringSnippet))
fun <T> Array<out T>.joinToStringOrEmpty(separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	transform: ((T) -> CharSequence)? = null): String {
	return if(this.isEmpty()) "" else this.joinToString(separator, prefix, postfix, limit, truncated, transform)
}

/**根据指定的转化操作，将当前集合中的元素加入到字符串。当集合为空时，直接返回空字符串且忽略前后缀。*/
@Deprecated("Redundant extension method: Please consider checking nullability instead.", ReplaceWith(joinToStringSnippet))
fun <T> Iterable<T>.joinToStringOrEmpty(separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	transform: ((T) -> CharSequence)? = null): String {
	return if(this.none()) "" else this.joinToString(separator, prefix, postfix, limit, truncated, transform)
}

/**根据指定的转化操作，将当前映射中的元素加入到字符串。当映射为空时，直接返回空字符串且忽略前后缀。*/
@Deprecated("Redundant extension method: Please consider checking nullability instead.", ReplaceWith(joinToStringSnippet))
fun <K, V> Map<K, V>.joinToStringOrEmpty(separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	transform: ((Map.Entry<K, V>) -> CharSequence)? = null): String {
	return if(this.isEmpty()) "" else this.joinToString(separator, prefix, postfix, limit, truncated, transform)
}

private const val joinToStringSnippet = "joinToString(separator,prefix,postfix,limit,truncated,transform)"


/**映射当前映射中的值，并过滤转换后为null的值。*/
inline fun <K, V, R : Any> Map<out K, V>.mapValuesNotNull(transform: (V) -> R?): Map<K, R> {
	return this.mapValuesNotNullTo(LinkedHashMap(), transform)
}

/**映射当前映射中的值，并过滤转换后为null的值，然后置入之地你个的映射。*/
inline fun <K, V, R : Any, C : MutableMap<in K, in R>> Map<K, V>.mapValuesNotNullTo(destination: C, transform: (V) -> R?): C {
	for((key, value) in this) transform(value)?.let { destination.put(key, it) }
	return destination
}


/**过滤当前映射中值为null的键值对。*/
fun <K, V : Any> Map<out K, V?>.filterValuesNotNull(): Map<K, V> {
	val result = LinkedHashMap<K, V>()
	for((key, value) in this) if(value != null) result[key] = value
	return result
}

/**按照类型以及附加条件过滤当前数组。*/
@Deprecated("Redundant extension method: Please consider checking nullability instead.")
@Suppress("DEPRECATION")
inline fun <reified R> Array<*>.filterIsInstance(predicate: (R) -> Boolean): List<R> {
	return this.filterIsInstanceTo<R, MutableList<R>>(ArrayList(), predicate)
}

/**按照类型以及附加条件过滤当前数组，然后置入指定的集合。*/
@Deprecated("Redundant extension method: Please consider checking nullability instead.")
inline fun <reified R, C : MutableCollection<in R>> Array<*>.filterIsInstanceTo(destination: C,
	predicate: (R) -> Boolean): C {
	for(element in this) if(element is R && predicate(element)) destination.add(element)
	return destination
}

/**按照类型以及附加条件过滤当前列表。*/
@Deprecated("Redundant extension method: Please consider checking nullability instead.")
@Suppress("DEPRECATION")
inline fun <reified R> List<*>.filterIsInstance(predicate: (R) -> Boolean): List<R> {
	return this.filterIsInstanceTo<R, MutableList<R>>(ArrayList(), predicate)
}

/**按照类型以及附加条件过滤当前列表，然后置入指定的集合。*/
@Deprecated("Redundant extension method: Please consider checking nullability instead.")
inline fun <reified R, C : MutableCollection<in R>> List<*>.filterIsInstanceTo(destination: C,
	predicate: (R) -> Boolean): C {
	for(element in this) if(element is R && predicate(element)) destination.add(element)
	return destination
}

/**按照类型以及附加条件过滤当前集。*/
@Deprecated("Redundant extension method: Please consider checking nullability instead.")
@Suppress("DEPRECATION")
inline fun <reified R> Set<*>.filterIsInstance(predicate: (R) -> Boolean): List<R> {
	return this.filterIsInstanceTo<R, MutableList<R>>(ArrayList(), predicate)
}

/**按照类型以及附加条件过滤当前集，然后置入指定的集合。*/
@Deprecated("Redundant extension method: Please consider checking nullability instead.")
inline fun <reified R, C : MutableCollection<in R>> Set<*>.filterIsInstanceTo(destination: C,
	predicate: (R) -> Boolean): C {
	for(element in this) if(element is R && predicate(element)) destination.add(element)
	return destination
}


/**根据指定的条件，内连接当前数组和另一个数组。即，绑定满足该条件的各自元素，忽略不满足的情况。*/
inline fun <T, R : Any> Array<out T>.innerJoin(other: Array<out R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**根据指定的条件，内连接当前数组和另一个集合。即，绑定满足该条件的各自元素，忽略不满足的情况。*/
inline fun <T, R : Any> Array<out T>.innerJoin(other: Iterable<R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**根据指定的条件，内连接当前集合和另一个数组。即，绑定满足该条件的各自元素，忽略不满足的情况。*/
inline fun <T, R : Any> Iterable<T>.innerJoin(other: Array<out R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**根据指定的条件，内连接当前集合和另一个集合。即，绑定满足该条件的各自元素，忽略不满足的情况。*/
inline fun <T, R : Any> Iterable<T>.innerJoin(other: Iterable<R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**根据指定的条件，内连接当前序列和另一个序列。即，绑定满足该条件的各自元素，忽略不满足的情况。*/
inline fun <T, R : Any> Sequence<T>.innerJoin(other: Sequence<R>, crossinline predicate: (T, R) -> Boolean): Sequence<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}
//endregion

//region deep operation extensions
/**
 * 根据指定路径得到当前数组中的元素。可指定路径的格式，默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意返回值的类型应当与指定的泛型类型一致，否则会发生异常。
 *
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Array<*>.deepGet(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference): T =
	this.deepGet0(path, pathCase)

/**
 * 根据指定路径得到当前列表中的元素。可指定路径的格式，默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意返回值的类型应当与指定的泛型类型一致，否则会发生异常。
 *
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> List<*>.deepGet(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference): T =
	this.deepGet0(path, pathCase)

/**
 * 根据指定路径得到当前映射中的元素。可指定路径的格式，默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意返回值的类型应当与指定的泛型类型一致，否则会发生异常。
 *
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Map<*, *>.deepGet(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference): T =
	this.deepGet0(path, pathCase)

private fun <T> Any?.deepGet0(path: String, pathCase: ReferenceCase): T {
	val subPaths = path.splitBy(pathCase)
	if(subPaths.isEmpty()) throw IllegalArgumentException("Target path '$path' cannot be empty.")
	var currentValue = this
	for(subPath in subPaths) {
		currentValue = currentValue.collectionGet(subPath)
	}
	return currentValue as T
}


/**
 * 根据指定路径设置当前数组中的元素。指定路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[MutableList]和[MutableMap]。
 * 向下定位元素时支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意指定的值的类型应当与对应集合的泛型类型一致，否则可能会发生异常。
 *
 * @see ReferenceCase.PathReference
 */
@TrickImplementationApi("Cannot check actual generic type of a collection in Java.")
@JvmOverloads
fun <T> Array<*>.deepSet(path: String, value: T, pathCase: ReferenceCase = ReferenceCase.PathReference) =
	this.deepSet0(path, value, pathCase)

/**
 * 根据指定路径设置当前列表中的元素。指定路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[MutableList]和[MutableMap]。
 * 向下定位元素时支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意指定的值的类型应当与对应集合的泛型类型一致，否则可能会发生异常。
 *
 * @see ReferenceCase.JavaReference
 */
@TrickImplementationApi("Cannot check actual generic type of a collection in Java.")
@JvmOverloads
fun <T> MutableList<*>.deepSet(path: String, value: T, pathCase: ReferenceCase = ReferenceCase.PathReference) =
	this.deepSet0(path, value, pathCase)

/**
 * 根据指定路径设置当前映射中的元素。指定路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[MutableList]和[MutableMap]。
 * 向下定位元素时支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意指定的值的类型应当与对应集合的泛型类型一致，否则可能会发生异常。
 *
 * @see ReferenceCase.PathReference
 */
@TrickImplementationApi("Cannot check actual generic type of a collection in Java.")
@JvmOverloads
fun <T> MutableMap<*, *>.deepSet(path: String, value: T, pathCase: ReferenceCase = ReferenceCase.PathReference) =
	this.deepSet0(path, value, pathCase)

private fun <T> Any?.deepSet0(path: String, value: T, pathCase: ReferenceCase) {
	val subPaths = path.splitBy(pathCase)
	if(subPaths.isEmpty()) throw IllegalArgumentException("Target path '$path' cannot be empty.")
	var currentValue = this
	for(subPath in subPaths.dropLast(1)) {
		currentValue = currentValue.collectionGet(subPath)
	}
	currentValue.collectionSet(subPaths.last(), value)
}


/**
 * 根据指定路径递归查询当前数组，返回匹配的路径-值映射。指定路径的格式默认为路径引用。返回值中路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意返回映射的值的类型应当与指定的泛型类型一致，否则会发生异常。
 *
 * @see ReferenceCase.PathReference
 * @see ReferenceCase.JavaReference
 */
@JvmOverloads
fun <T> Array<*>.deepQuery(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference,
	returnPathCase: ReferenceCase = ReferenceCase.PathReference): Map<String, T> =
	this.deepQuery0(path, pathCase, returnPathCase)

/**
 * 根据指定路径递归查询当前集合，返回匹配的路径-值映射。指定路径的格式默认为路径引用。返回值中路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意返回映射的值的类型应当与指定的泛型类型一致，否则会发生异常。
 *
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Iterable<*>.deepQuery(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference,
	returnPathCase: ReferenceCase = ReferenceCase.PathReference): Map<String, T> =
	this.deepQuery0(path, pathCase, returnPathCase)

/**
 * 根据指定路径递归查询当前映射，返回匹配的路径-值映射。指定路径的格式默认为路径引用。返回值中路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意返回映射的值的类型应当与指定的泛型类型一致，否则会发生异常。
 *
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Map<*, *>.deepQuery(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference,
	returnPathCase: ReferenceCase = ReferenceCase.PathReference): Map<String, T> =
	this.deepQuery0(path, pathCase, returnPathCase)

/**
 * 根据指定路径递归查询当前序列，返回匹配的路径-值映射。指定路径的格式默认为路径引用。返回值中路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意返回映射的值的类型应当与指定的泛型类型一致，否则会发生异常。
 *
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Sequence<*>.deepQuery(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference,
	returnPathCase: ReferenceCase = ReferenceCase.PathReference): Map<String, T> =
	this.deepQuery0(path, pathCase, returnPathCase)

private fun <T> Any?.deepQuery0(path: String, pathCase: ReferenceCase, returnPathCase: ReferenceCase): Map<String, T> {
	val subPaths = path.splitBy(pathCase)
	var pathValuePairs = listOf(arrayOf<String>() to this)
	for(subPath in subPaths) {
		pathValuePairs = pathValuePairs.flatMap { (key, value) ->
			when(value) {
				is Array<*> -> when {
					subPath == "[]" || subPath == "-" || subPath.surroundsWith("[", "]") -> {
						value.withIndex().map { (i, e) -> (key + i.toString()) to e }
					}
					subPath.contains("..") || subPath.contains("-") -> {
						val indices = subPath.toIntRange()
						value.slice(indices).withIndex().map { (i, e) -> (key + i.toString()) to e }
					}
					else -> value.getOrNull(subPath.toIntOrThrow()).toPairList(key + subPath)
				}
				is Iterable<*> -> when {
					subPath == "[]" || subPath == "-" || subPath.surroundsWith("[", "]") -> {
						value.withIndex().map { (i, e) -> (key + i.toString()) to e }
					}
					subPath.contains("..") || subPath.contains("-") -> {
						val indices = subPath.toIntRange()
						if(value is List<*>) {
							value.slice(indices).withIndex().map { (i, e) -> (key + i.toString()) to e }
						} else {
							value.withIndex().filter { (i, _) -> i in indices }.map { (i, e) -> (key + i.toString()) to e }
						}
					}
					else -> value.elementAtOrNull(subPath.toIntOrThrow()).toPairList(key + subPath)
				}
				is Map<*, *> -> when {
					subPath == "{}" || subPath == "-" || subPath.surroundsWith("{", "}") -> {
						value.map { (k, v) -> (key + k.toString()) to v }
					}
					subPath.startsWith("re:") -> {
						val regex = subPath.substring(3).toRegex()
						value.filterKeys { it.toString() matches regex }.map { (k, v) -> (key + k.toString()) to v }
					}
					else -> value[subPath].toPairList(key + subPath)
				}
				is Sequence<*> -> when {
					subPath == "[]" || subPath == "-" || subPath.surroundsWith("[", "]") -> {
						value.withIndex().map { (i, e) -> (key + i.toString()) to e }.asIterable()
					}
					subPath.contains("..") || subPath.contains("-") -> {
						val indices = subPath.toIntRange()
						value.withIndex().filter { (i, _) -> i in indices }.map { (i, e) -> (key + i.toString()) to e }.asIterable()
					}
					else -> value.elementAtOrNull(subPath.toIntOrThrow()).toPairList(key + subPath)
				}
				//当尝试查询非集合类型的数据时，要求抛出异常
				else -> notAValidCollection(this)
			}
		}
	}
	return pathValuePairs.toPairValueMap(returnPathCase) as Map<String, T>
}


/**
 * 根据深度递归平滑映射当前数组，返回匹配的值列表。默认递归映射直到到找不到集合类型的元素为止。返回值中路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 如果指定了递归的深度，则会递归映射到该深度，或者直到找不到集合类型的元素为止。
 *
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun Array<*>.deepFlatten(depth: Int = -1): List<Any?> =
	this.deepFlatten0(depth)

/**
 * 根据深度递归平滑映射当前集合，返回匹配的值列表。默认递归映射直到到找不到集合类型的元素为止。返回值中路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 如果指定了递归的深度，则会递归映射到该深度，或者直到找不到集合类型的元素为止。
 *
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun Iterable<*>.deepFlatten(depth: Int = -1): List<Any?> =
	this.deepFlatten0(depth)

/**
 * 根据深度递归平滑映射当前映射，返回匹配的值列表。默认递归映射直到到找不到集合类型的元素为止。返回值中路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 如果指定了递归的深度，则会递归映射到该深度，或者直到找不到集合类型的元素为止。
 *
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun Map<*, *>.deepFlatten(depth: Int = -1): List<Any?> =
	this.deepFlatten0(depth)

/**
 * 根据深度递归平滑映射当前序列，返回匹配的值列表。默认递归映射直到到找不到集合类型的元素为止。返回值中路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 如果指定了递归的深度，则会递归映射到该深度，或者直到找不到集合类型的元素为止。
 *
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun Sequence<*>.deepFlatten(depth: Int = -1): List<Any?> =
	this.deepFlatten0(depth)

private fun Any?.deepFlatten0(depth: Int = -1): List<Any?> {
	var values = listOf(this)
	var currentDepth = depth
	while(currentDepth != 0) {
		//用来判断这次循环中是否找到集合类型的数据，以判断是否需要进行下一次循环
		var hasNoneCollectionElement = true
		values = values.flatMap { value ->
			hasNoneCollectionElement = false
			when(value) {
				is Array<*> -> value.asIterable()
				is Iterable<*> -> value
				is Map<*, *> -> value.values
				is Sequence<*> -> value.asIterable()
				else -> {
					hasNoneCollectionElement = true
					listOf(value)
				}
			}
		}
		if(hasNoneCollectionElement) return values
		currentDepth--
	}
	return values
}


private fun Any?.collectionGet(indexOrKey: String): Any? {
	return when(this) {
		is Array<*> -> this[indexOrKey.toIntOrThrow()]
		is Iterable<*> -> this.elementAt(indexOrKey.toIntOrThrow())
		is Map<*, *> -> this[indexOrKey]
		is Sequence<*> -> this.elementAt(indexOrKey.toIntOrThrow())
		else -> notAValidCollection(this)
	}
}

private fun Any?.collectionSet(indexOrKey: String, value: Any?) {
	//除了数组以外，无法进行类型检查
	when(this) {
		is Array<*> -> try {
			(this as Array<Any?>)[indexOrKey.toIntOrThrow()] = value
		} catch(e: ArrayStoreException) {
			typeMismatched(value)
		}
		is MutableList<*> -> (this as MutableList<Any?>)[indexOrKey.toIntOrThrow()] = value
		is MutableMap<*, *> -> (this as MutableMap<String, Any?>)[indexOrKey] = value
		else -> notAValidMutableCollection(this)
	}
}

private fun String.toIntOrThrow(): Int {
	return this.toIntOrNull() ?: notAnIndex(this)
}

private fun Any?.toPairList(path: Array<String>): List<Pair<Array<String>, Any?>> {
	return if(this == null) listOf() else listOf(path to this)
}

private fun notAnIndex(path: String): Nothing {
	throw IllegalArgumentException("Path '$path' is not a valid index.")
}

private fun notAValidCollection(collection: Any?): Nothing {
	throw IllegalArgumentException("'${collection.smartToString()}' is not a valid collection (Array, Iterable, Map or Sequence).")
}

private fun notAValidMutableCollection(collection: Any?): Nothing {
	throw IllegalArgumentException("'${collection.smartToString()}' is not a valid collection (Array, MutableList or MutableMap).")
}

private fun notAStringKeyMap(collection: Any?): Nothing {
	throw IllegalArgumentException("'${collection}' is a map but type of it's key is not String.")
}

private fun typeMismatched(value: Any?): Nothing {
	throw IllegalArgumentException("Value '$value' is type mismatched (Incompatible generic type).")
}

private fun List<Pair<Array<String>, Any?>>.toPairValueMap(returnPathCase: ReferenceCase): Map<String, Any?> {
	return this.map { (p, v) -> p.joinToStringBy(returnPathCase) to v }.toMap()
}
//endregion

//region convert extensions
/**将当前列表转化为新的并发列表。*/
fun <T> List<T>.asConcurrent(): CopyOnWriteArrayList<T> = CopyOnWriteArrayList(this)

/**将当前集转化为新的并发集。*/
fun <T> Set<T>.asConcurrent(): CopyOnWriteArraySet<T> = CopyOnWriteArraySet(this)

/**将当前映射转化为新的并发映射。*/
fun <K, V> Map<K, V>.asConcurrent(): ConcurrentHashMap<K, V> = ConcurrentHashMap(this)


/**将当前键值对数组转化为可变映射。*/
fun <K, V> Array<out Pair<K, V>>.toMutableMap(): MutableMap<K, V> = this.toMap(LinkedHashMap())

/**将当前键值对列表转化为可变映射。*/
fun <K, V> Iterable<Pair<K, V>>.toMutableMap(): MutableMap<K, V> = this.toMap(LinkedHashMap())

/**将当前键值对序列转化为可变映射。*/
fun <K, V> Sequence<Pair<K, V>>.toMutableMap(): MutableMap<K, V> = this.toMap(LinkedHashMap())


/**将当前数组转化为以键为值的映射。*/
inline fun <T> Array<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associateBy({ it.index.toString() }, { it.value })
}

/**将当前集合转化为以键为值的映射。*/
inline fun <T> Iterable<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associateBy({ it.index.toString() }, { it.value })
}

/**将当前序列转化为以键为值的映射。*/
inline fun <T> Sequence<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associateBy({ it.index.toString() }, { it.value })
}

/**将当前映射转化为新的以字符串为键的映射。*/
@NotOptimized
inline fun <K, V> Map<K, V>.toStringKeyMap(): Map<String, V> {
	return this.mapKeys { (k, _) -> k.toString() }
}

/**将当前映射转化为新的以字符串为键且以字符串为键的映射。*/
@NotOptimized
inline fun <V> Map<String, V>.toStringValueMap(): Map<String, String> {
	return this.mapValues { (_, v) -> v.toString() }
}
//endregion

//region unsafe extensions
/**尝试检查当前集合的泛型，但不保证正确性。默认遍历10个元素，判断是否全部兼容指定的类型。当限定个数为-1时遍历所有元素。*/
@TrickImplementationApi("Cannot check actual generic type of a collection in Java.")
@WeakDeprecated("Cannot check actual generic type of a collection in Java.")
inline fun <reified T : Any> Iterable<*>.isIterableOf(limit: Int = 10): Boolean {
	return when(limit) {
		0 -> throw IllegalArgumentException("Type check limit cannot be zero.")
		-1 -> this.all { it is T }
		else -> this.take(limit).all { it is T }
	}
}

/**尝试检查当前映射的泛型，但不保证正确性。默认遍历10个键值对，判断是否全部兼容指定的类型。当限定个数为-1时遍历所有元素。*/
@TrickImplementationApi("Cannot check actual generic type of a collection in Java.")
@WeakDeprecated("Cannot check actual generic type of a collection in Java.")
inline fun <reified K : Any, reified V : Any> Map<*, *>.isMapOf(limit: Int = 10): Boolean {
	return when(limit) {
		0 -> throw IllegalArgumentException("Type check limit cannot be zero.")
		-1 -> this.entries.all { (k, v) -> k is K && v is V }
		else -> this.entries.take(limit).all { it.key is K && it.value is V }
	}
}

/**尝试检查当前映射的泛型，但不保证正确性。默认遍历10个元素，判断是否全部兼容指定的类型。当限定个数为-1时遍历所有元素。*/
@TrickImplementationApi("Cannot check actual generic type of a collection in Java.")
@WeakDeprecated("Cannot check actual generic type of a collection in Java.")
inline fun <reified T : Any> Sequence<*>.isSequenceOf(limit: Int = 10): Boolean {
	return when(limit) {
		0 -> throw IllegalArgumentException("Type check limit cannot be zero.")
		-1 -> this.all { it is T }
		else -> this.take(limit).all { it is T }
	}
}
//endregion

//region specific operations
/**得到指定索引的字符串，如果索引越界，则返回空字符串。*/
inline fun Array<out String>.getOrEmpty(index: Int): String = this.getOrDefault(index, "")

/**得到指定索引的字符串，如果索引越界，则返回空字符串。*/
inline fun List<String>.getOrEmpty(index: Int): String = this.getOrDefault(index, "")

/**得到指定键的字符串，如果值为null，则返回空字符串。*/
inline fun <K> Map<K, String>.getOrEmpty(key: K): String = this[key] ?: ""


/**去除起始的空字符串。*/
inline fun <T : CharSequence> Array<out T>.dropEmpty(): List<T> = this.dropWhile { it.isEmpty() }

/**去除起始的空字符串。*/
inline fun <T : CharSequence> Iterable<T>.dropEmpty(): List<T> = this.dropWhile { it.isEmpty() }

/**去除起始的空字符串。*/
inline fun <T : CharSequence> Sequence<T>.dropEmpty(): Sequence<T> = this.dropWhile { it.isEmpty() }

/**去除尾随的空字符串。*/
inline fun <T : CharSequence> Array<out T>.dropLastEmpty(): List<T> = this.dropLastWhile { it.isEmpty() }

/**去除尾随的空字符串。*/
inline fun <T : CharSequence> List<T>.dropLastEmpty(): List<T> = this.dropLastWhile { it.isEmpty() }


/**去除起始的空白字符串。*/
inline fun <T : CharSequence> Array<out T>.dropBlank(): List<T> = this.dropWhile { it.isBlank() }

/**去除起始的空白字符串。*/
inline fun <T : CharSequence> Iterable<T>.dropBlank(): List<T> = this.dropWhile { it.isBlank() }

/**去除起始的空白字符串。*/
inline fun <T : CharSequence> Sequence<T>.dropBlank(): Sequence<T> = this.dropWhile { it.isBlank() }

/**去除尾随的空白字符串。*/
inline fun <T : CharSequence> Array<out T>.dropLastBlank(): List<T> = this.dropLastWhile { it.isBlank() }

/**去除尾随的空白字符串。*/
inline fun <T : CharSequence> List<T>.dropLastBlank(): List<T> = this.dropLastWhile { it.isBlank() }


/**过滤当前数组中为空字符串的元素。*/
inline fun <T : CharSequence> Array<out T>.filterNotEmpty(): List<T> = this.filterNotEmptyTo(ArrayList())

/**过滤当前集合中为空字符串的元素。*/
inline fun <T : CharSequence> Iterable<T>.filterNotEmpty(): List<T> = this.filterNotEmptyTo(ArrayList())

/**过滤当前映射中值为空字符串的键值对。*/
inline fun <K, V : CharSequence> Map<out K, V>.filterValuesNotEmpty(): Map<K, V> {
	val result = LinkedHashMap<K, V>()
	for((key, value) in this) if(value.isNotEmpty()) result[key] = value
	return result
}

/**过滤当前序列中为空字符串的元素。*/
inline fun <T : CharSequence> Sequence<T>.filterNotEmpty(): Sequence<T> = this.filter { it.isEmpty() }


/**过滤当前数组中为空字符串的元素，然后加入到指定的集合。*/
inline fun <T : CharSequence, C : MutableCollection<in T>> Array<out T>.filterNotEmptyTo(destination: C): C {
	for(element in this) if(element.isNotEmpty()) destination += element
	return destination
}

/**过滤当前集合中为空字符串的元素，然后加入到指定的集合。*/
inline fun <T : CharSequence, C : MutableCollection<in T>> Iterable<T>.filterNotEmptyTo(destination: C): C {
	for(element in this) if(element.isNotEmpty()) destination += element
	return destination
}


/**过滤当前数组中为空白字符串的元素。*/
inline fun <T : CharSequence> Array<out T>.filterNotBlank(): List<T> = this.filterNotBlankTo(ArrayList())

/**过滤当前集合中为空白字符串的元素。*/
inline fun <T : CharSequence> Iterable<T>.filterNotBlank(): List<T> = this.filterNotBlankTo(ArrayList())

/**过滤当前映射中值为空白字符串的键值对。*/
inline fun <K, V : CharSequence> Map<out K, V>.filterValuesNotBlank(): Map<K, V> {
	val result = LinkedHashMap<K, V>()
	for((key, value) in this) if(value.isNotBlank()) result[key] = value
	return result
}

/**过滤当前序列中为空白字符串的元素。*/
inline fun <T : CharSequence> Sequence<T>.filterNotBlank(): Sequence<T> = this.filter { it.isBlank() }


/**过滤当前数组中为空白字符串的元素，然后加入到指定的集合。*/
inline fun <T : CharSequence, C : MutableCollection<in T>> Array<out T>.filterNotBlankTo(destination: C): C {
	for(element in this) if(element.isNotBlank()) destination += element
	return destination
}

/**过滤当前集合中为空白字符串的元素，然后加入到指定的集合。*/
inline fun <T : CharSequence, C : MutableCollection<in T>> Iterable<T>.filterNotBlankTo(destination: C): C {
	for(element in this) if(element.isNotBlank()) destination += element
	return destination
}


/**过滤当前数组中为null或空字符串的元素。*/
@UselessCallOnNotNullType
inline fun <T : CharSequence> Array<out T?>.filterNotNullOrEmpty(): List<T> = this.filterNotNullOrEmptyTo(ArrayList())

/**过滤当前集合中为null或空字符串的元素。*/
@UselessCallOnNotNullType
inline fun <T : CharSequence> Iterable<T?>.filterNotNullOrEmpty(): List<T> = this.filterNotNullOrEmptyTo(ArrayList())

/**过滤当前映射中值为null或空字符串的键值对。*/
@UselessCallOnNotNullType
inline fun <K, V : CharSequence> Map<out K, V>.filterValuesNotNullOrEmpty(): Map<K, V> {
	val result = LinkedHashMap<K, V>()
	for((key, value) in this) if(value.isNotNullOrEmpty()) result[key] = value
	return result
}

/**过滤当前序列中为null或空字符串的元素。*/
@UselessCallOnNotNullType
inline fun <T : CharSequence> Sequence<T?>.filterNotNullOrEmpty(): Sequence<T> = this.filter { it.isNotNullOrEmpty() } as Sequence<T>


/**过滤当前数组中为null或空字符串的元素，然后加入到指定的集合。*/
@UselessCallOnNotNullType
inline fun <T : CharSequence, C : MutableCollection<in T>> Array<out T?>.filterNotNullOrEmptyTo(destination: C): C {
	for(element in this) if(element.isNotNullOrEmpty()) destination += element
	return destination
}

/**过滤当前集合中为null或空字符串的元素，然后加入到指定的集合。*/
@UselessCallOnNotNullType
inline fun <T : CharSequence, C : MutableCollection<in T>> Iterable<T?>.filterNotNullOrEmptyTo(destination: C): C {
	for(element in this) if(element.isNotNullOrEmpty()) destination += element
	return destination
}


/**过滤当前数组中为null或空白字符串的元素。*/
@UselessCallOnNotNullType
inline fun <T : CharSequence> Array<out T?>.filterNotNullOrBlank(): List<T> = this.filterNotNullOrBlankTo(ArrayList())

/**过滤当前集合中为null或空白字符串的元素。*/
@UselessCallOnNotNullType
inline fun <T : CharSequence> Iterable<T?>.filterNotNullOrBlank(): List<T> = this.filterNotNullOrBlankTo(ArrayList())

/**过滤当前映射中值为null或空白字符串的键值对。*/
@UselessCallOnNotNullType
inline fun <K, V : CharSequence> Map<out K, V>.filterValuesNotNullOrBlank(): Map<K, V> {
	val result = LinkedHashMap<K, V>()
	for((key, value) in this) if(value.isNotNullOrBlank()) result[key] = value
	return result
}

/**过滤当前序列中为null或空白字符串的元素。*/
@UselessCallOnNotNullType
inline fun <T : CharSequence> Sequence<T?>.filterNotNullOrBlank(): Sequence<T> = this.filter { it.isNotNullOrBlank() } as Sequence<T>


/**过滤当前数组中为null或空白字符串的元素，然后加入到指定的集合。*/
@UselessCallOnNotNullType
inline fun <T : CharSequence, C : MutableCollection<in T>> Array<out T?>.filterNotNullOrBlankTo(destination: C): C {
	for(element in this) if(element.isNotNullOrBlank()) destination += element
	return destination
}

/**过滤当前集合中为null或空白字符串的元素，然后加入到指定的集合。*/
@UselessCallOnNotNullType
inline fun <T : CharSequence, C : MutableCollection<in T>> Iterable<T?>.filterNotNullOrBlankTo(destination: C): C {
	for(element in this) if(element.isNotNullOrBlank()) destination += element
	return destination
}
//endregion
