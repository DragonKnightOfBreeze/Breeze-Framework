// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("CollectionExtensions")
@file:Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE", "IMPLICIT_CAST_TO_ANY",
	"BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER", "UPPER_BOUND_CANNOT_BE_ARRAY", "ReplaceIsEmptyWithIfEmpty")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.annotation.*
import java.lang.reflect.*
import java.util.*
import java.util.concurrent.*
import kotlin.contracts.*

//region Entry Extensions
/**构建一个映射并事先过滤值为空的键值对。*/
fun <K, V : Any> mapOfValuesNotNull(pair: Pair<K, V?>): Map<K, V?> {
	return if(pair.second != null) mapOf(pair) else emptyMap()
}

/**构建一个映射并事先过滤值为空的键值对。*/
fun <K, V : Any> mapOfValuesNotNull(vararg pairs: Pair<K, V?>): Map<K, V> {
	return LinkedHashMap<K, V>().apply { for((key, value) in pairs) if(value != null) put(key, value) }
}


/**构建一个空的枚举集。*/
inline fun <reified T : Enum<T>> enumSetOf(): EnumSet<T> {
	return EnumSet.noneOf(T::class.java)
}

/**构建一个包含所有枚举值的枚举集。*/
inline fun <reified T : Enum<T>> enumSetOfAll(): EnumSet<T> {
	return EnumSet.allOf(T::class.java)
}

/**构建一个枚举集。*/
fun <T : Enum<T>> enumSetOf(first: T, vararg elements: T): EnumSet<T> {
	return EnumSet.of(first, *elements)
}

/**构建一个空的枚举映射。*/
inline fun <reified K : Enum<K>, V> enumMapOf(): EnumMap<K, V> {
	return EnumMap(K::class.java)
}

/**构建一个枚举映射。*/
fun <K : Enum<K>, V> enumMapOf(vararg pairs: Pair<K, V>): EnumMap<K, V> {
	return EnumMap(pairs.toMap())
}


/**构建一个空的线程安全的并发列表。*/
fun <T> concurrentListOf(): CopyOnWriteArrayList<T> {
	return CopyOnWriteArrayList()
}

/**构建一个线程安全的并发列表。*/
fun <T> concurrentListOf(vararg elements: T): CopyOnWriteArrayList<T> {
	return CopyOnWriteArrayList(elements)
}

/**构建一个空的线程安全的并发集。*/
fun <T> concurrentSetOf(): CopyOnWriteArraySet<T> {
	return CopyOnWriteArraySet()
}

/**构建一个线程安全的并发集。*/
fun <T> concurrentSetOf(vararg elements: T): CopyOnWriteArraySet<T> {
	return CopyOnWriteArraySet(elements.toSet())
}

/**构建一个空的线程安全的并发映射。*/
fun <K, V> concurrentMapOf(): ConcurrentHashMap<K, V> {
	return ConcurrentHashMap()
}

/**构建一个线程安全的并发映射。*/
fun <K, V> concurrentMapOf(vararg pairs: Pair<K, V>): ConcurrentHashMap<K, V> {
	return ConcurrentHashMap(pairs.toMap())
}
//endregion

//region Operator Extensions
/**
 * 重复当前列表中的元素到指定次数。
 *
 * @see icu.windea.breezeframework.core.extension.repeat
 */
operator fun <T> List<T>.times(n: Int): List<T> {
	return this.repeat(n)
}

/**
 * 切分当前集合中的元素到指定个数。
 *
 * @see kotlin.collections.chunked
 **/
operator fun <T> Iterable<T>.div(n: Int): List<List<T>> {
	return this.chunked(n)
}

/**
 * 得到索引指定范围内的子列表。
 *
 * @see kotlin.collections.slice
 */
operator fun <T> List<T>.get(indices: IntRange): List<T> {
	return this.slice(indices)
}

/**
 * 得到索引指定范围内的子列表。
 *
 * @see kotlin.collections.List.subList
 */
operator fun <T> List<T>.get(startIndex: Int, endIndex: Int): List<T> {
	return this.subList(startIndex, endIndex)
}
//endregion

//region Common Extensions
/**
 * 得到当前数组的最为适配的元素类型。
 */
inline val Array<*>.elementType: Type get() = this::class.java.componentType

/**
 * 得到当前集合的最为适配的元素类型。
 */
@TrickApi
@Deprecated("Tricky implementation.", level = DeprecationLevel.HIDDEN)
inline val <reified T> Iterable<T>.elementType: Type
	get() = javaTypeOf<T>()

/**
 * 得到当前映射的最为适配的键类型。
 */
@TrickApi
@Deprecated("Tricky implementation.", level = DeprecationLevel.HIDDEN)
inline val <reified K> Map<K, *>.keyType: Type
	get() = javaTypeOf<K>()

/**
 * 得到当前映射的最为适配的值类型。
 */
@TrickApi
@Deprecated("Tricky implementation.", level = DeprecationLevel.HIDDEN)
inline val <reified V> Map<*, V>.valueType: Type
	get() = javaTypeOf<V>()

/**
 * 得到当前序列的最为适配的键类型。
 */
@TrickApi
@Deprecated("Tricky implementation.", level = DeprecationLevel.HIDDEN)
inline val <reified T> Sequence<T>.elementType: Type
	get() = javaTypeOf<T>()


/**
 * 判断当前数组是否不为null，且不为空。
 */
@UselessCallOnNotNullType
@JvmSynthetic
@InlineOnly
inline fun Array<*>?.isNotNullOrEmpty(): Boolean {
	contract {
		returns(true) implies (this@isNotNullOrEmpty != null)
	}
	return this != null && this.isNotEmpty()
}

/**
 * 判断当前集合是否不为null，且不为空。
 */
@UselessCallOnNotNullType
@JvmSynthetic
@InlineOnly
inline fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean {
	contract {
		returns(true) implies (this@isNotNullOrEmpty != null)
	}
	return this != null && this.isNotEmpty()
}

/**
 * 判断当前映射是否不为null，且不为空。
 */
@UselessCallOnNotNullType
@JvmSynthetic
@InlineOnly
inline fun <K, V> Map<out K, V>?.isNotNullOrEmpty(): Boolean {
	contract {
		returns(true) implies (this@isNotNullOrEmpty != null)
	}
	return this != null && this.isNotEmpty()
}

/**
 * 判断当前序列是否为空。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
@JvmSynthetic
@InlineOnly
inline fun <T> Sequence<T>.isEmpty(): Boolean {
	return !this.iterator().hasNext()
}

/**
 * 判断当前序列是否不为空。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
@JvmSynthetic
@InlineOnly
inline fun <T> Sequence<T>.isNotEmpty(): Boolean {
	return this.iterator().hasNext()
}


/**
 * 如果当前数组不为空，则返回本身，否则返回null。
 */
@JvmSynthetic
@InlineOnly
inline fun <T> Array<out T>.orNull(): Array<out T>? {
	return if(this.isEmpty()) null else this
}

/**
 * 如果当前集合不为空，则返回本身，否则返回null。
 */
@JvmSynthetic
@InlineOnly
inline fun <T> Collection<T>.orNull(): Collection<T>? {
	return if(this.isEmpty()) null else this
}

/**
 * 如果当前映射不为空，则返回本身，否则返回null。
 */
@JvmSynthetic
@InlineOnly
inline fun <K, V> Map<K, V>.orNull(): Map<K, V>? {
	return if(this.isEmpty()) null else this
}


/**
 * 如果当前数组不为空，则返回转化后的值，否则返回本身。
 */
@JvmSynthetic
@InlineOnly
inline fun <C, R> C.ifNotEmpty(transform: (C) -> R): R where C : Array<*>, C : R {
	return if(this.isEmpty()) this else transform(this)
}

/**
 * 如果当前集合不为空，则返回转化后的值，否则返回本身。
 */
@JvmSynthetic
@InlineOnly
inline fun <C, R> C.ifNotEmpty(transform: (C) -> R): R where C : Collection<*>, C : R {
	return if(this.isEmpty()) this else transform(this)
}

/**
 * 如果当前映射不为空，则返回转化后的值，否则返回本身。
 */
@JvmSynthetic
@InlineOnly
inline fun <M, R> M.ifNotEmpty(transform: (M) -> R): R where M : Map<*, *>, M : R {
	return if(this.isEmpty()) this else transform(this)
}

/**
 * 判断两个列表的结构是否相等。即，判断长度、元素、元素顺序是否相等。
 */
infix fun <T> List<T>.contentEquals(other: List<T>): Boolean {
	//注意：某些具体的实现类的equals方法与这个方法是等效的
	return this == other || this.size == other.size && (this zip other).all { (a, b) -> a == b }
}

/**
 * 判断两个列表的结构是否递归相等。即，判断长度、元素、元素顺序是否递归相等。
 */
infix fun <T> List<T>.contentDeepEquals(other: List<T>): Boolean {
	//注意：某些具体的实现类的equals方法与这个方法是等效的
	return this == other || this.size == other.size && (this zip other).all { (a, b) ->
		when {
			a is Array<*> && b is Array<*> -> a contentDeepEquals b
			a is List<*> && b is List<*> -> a contentDeepEquals b
			else -> a == b
		}
	}
}


/**
 * 判断当前数组中的所有元素是否被另一数组包含。
 */
infix fun <T> Array<out T>.allIn(other: Array<out T>): Boolean {
	return this.all { it in other }
}

/**
 * 判断当前数组中的所有元素是否被另一集合包含。
 */
infix fun <T> Array<out T>.allIn(other: Iterable<T>): Boolean {
	return this.all { it in other }
}

/**
 * 判断当前集合中的所有元素是否被另一数组包含。
 */
infix fun <T> Iterable<T>.allIn(other: Array<out T>): Boolean {
	return this.all { it in other }
}

/**
 * 判断当前集合中的所有元素是否被另一集合包含。
 */
infix fun <T> Iterable<T>.allIn(other: Iterable<T>): Boolean {
	return this.all { it in other }
}

/**
 * 判断当前序列中的所有元素是否被另一序列包含。
 */
infix fun <T> Sequence<T>.allIn(other: Sequence<T>): Boolean {
	return this.all { it in other }
}

/**
 * 判断当前数组中的任意元素是否被另一数组包含。
 */
infix fun <T> Array<out T>.anyIn(other: Array<out T>): Boolean {
	return this.any { it in other }
}

/**
 * 判断当前数组中的任意元素是否被另一集合包含。
 */
infix fun <T> Array<out T>.anyIn(other: Iterable<T>): Boolean {
	return this.any { it in other }
}

/**
 * 判断当前集合中的任意元素是否被另一数组包含。
 */
infix fun <T> Iterable<T>.anyIn(other: Array<out T>): Boolean {
	return this.any { it in other }
}

/**
 * 判断当前集合中的任意元素是否被另一集合包含。
 */
infix fun <T> Iterable<T>.anyIn(other: Iterable<T>): Boolean {
	return this.any { it in other }
}

/**
 * 判断当前序列中的任意元素是否被另一序列包含。
 */
infix fun <T> Sequence<T>.anyIn(other: Sequence<T>): Boolean {
	return this.any { it in other }
}


/**
 * 判断当前数组是否以指定元素开始。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
inline infix fun <T> Array<out T>.startsWith(element: T): Boolean = this.firstOrNull() == element

/**
 * 判断当前数组是否以任意指定元素开始。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
inline infix fun <T> Array<out T>.startsWith(elements: Array<out T>): Boolean = this.firstOrNull() in elements

/**
 * 判断当前集合是否以指定元素开始。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
inline infix fun <T> Iterable<T>.startsWith(element: T): Boolean = this.firstOrNull() == element

/**
 * 判断当前集合是否以任意指定元素开始。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
inline infix fun <T> Iterable<T>.startsWith(elements: Array<out T>): Boolean = this.firstOrNull() in elements

/**
 * 判断当前序列是否以指定元素开始。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
inline infix fun <T> Sequence<T>.startsWith(element: T): Boolean = this.firstOrNull() == element

/**
 * 判断当前序列是否以任意指定元素开始。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
inline infix fun <T> Sequence<T>.startsWith(elements: Array<out T>): Boolean = this.firstOrNull() in elements

/**
 * 判断当前数组是否以指定元素结束。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
inline infix fun <T> Array<out T>.endsWith(element: T): Boolean = this.lastOrNull() == element

/**
 * 判断当前数组是否以任意指定元素结束。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
inline infix fun <T> Array<out T>.endsWith(elements: Array<out T>): Boolean = this.lastOrNull() in elements

/**
 * 判断当前集合是否以指定元素结束。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
inline infix fun <T> Iterable<T>.endsWith(element: T): Boolean = this.lastOrNull() == element

/**
 * 判断当前集合是否以任意指定元素结束。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
inline infix fun <T> Iterable<T>.endsWith(elements: Array<out T>): Boolean = this.lastOrNull() in elements


/**
 * 得到指定索引的元素，发生异常则得到默认值。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
fun <T> Array<out T>.getOrDefault(index: Int, defaultValue: T): T {
	return this.getOrElse(index) { defaultValue }
}

/**
 * 得到指定索引的元素，发生异常则得到默认值。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
fun <T> List<T>.getOrDefault(index: Int, defaultValue: T): T {
	return this.getOrElse(index) { defaultValue }
}


/**
 * 将指定的键值对放入当前映射中。
 */
fun <K, V> MutableMap<K, V>.put(pair: Pair<K, V>): V? {
	return this.put(pair.first, pair.second)
}


/**
 * 交换当前数组中指定的两个索引对应的元素。
 */
fun <T> Array<T>.swap(index1: Int, index2: Int) {
	val temp = this[index1]
	this[index1] = this[index2]
	this[index2] = temp
}

/**
 * 交换当前列表中指定的两个索引对应的元素。
 */
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
	//不委托给java.util.Collections.swap，因为数组的对应方法是私有的
	val temp = this[index1]
	this[index1] = this[index2]
	this[index2] = temp
}


/**
 * 重复当前列表中的元素到指定次数。
 */
fun <T> List<T>.repeat(n: Int): List<T> {
	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }
	return ArrayList<T>(this.size * n).also { list -> repeat(n) { list += this } }
}

/**
 * 分别重复当前列表中的元素到指定次数。
 */
fun <T> List<T>.repeatOrdinal(n: Int): List<T> {
	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }
	return ArrayList<T>(this.size * n).also { list -> for(e in this) repeat(n) { list += e } }
}


/**
 * 填充指定索引范围内的元素到当前列表。如果索引超出当前列表的长度，或为负数，则忽略。
 */
fun <T> MutableList<T>.fillRange(indices: IntRange, value: T) {
	val fromIndex = indices.first.coerceIn(0, this.size)
	val toIndex = indices.last.coerceIn(fromIndex, this.size)
	for(index in fromIndex..toIndex) this[index] = value
}

/**
 * 填充指定元素到当前列表之前，直到指定长度。如果指定长度比当前长度小，则切割当前列表。返回填充后的列表。
 */
fun <T> List<T>.fillStart(size: Int, value: T): List<T> {
	require(size >= 0) { "Desired size must be non-negative, but was $size." }

	if(size <= this.size) return this.subList(0, size)
	return List(size - this.size) { value } + this
}

/**
 * 填充指定元素到当前列表之后，直到指定长度。如果指定长度比当前长度小，则切割当前列表。返回填充后的列表。
 */
fun <T> List<T>.fillEnd(size: Int, value: T): List<T> {
	require(size >= 0) { "Desired size must be non-negative, but was $size." }

	if(size <= this.size) return this.subList(0, size)
	return this + List(size - this.size) { value }
}


/**
 * 移除指定范围内的元素。
 */
fun <T> MutableList<T>.removeAllAt(indices: IntRange) {
	for(index in indices.reversed()) this.removeAt(index)
}


/**
 * 将指定索引的元素插入到另一索引处。注意后者为移动前的索引，而非移动后的索引。
 */
fun <T> MutableList<T>.moveAt(fromIndex: Int, toIndex: Int): T {
	val element = this[fromIndex]
	this.add(toIndex, element)
	return this.removeAt(fromIndex)
}

/**
 * 将指定索引范围内的元素插入到以另一索引为起点处。注意后者为移动前的索引，而非移动后的索引。
 */
fun <T> MutableList<T>.moveAllAt(fromIndices: IntRange, toIndex: Int) {
	val elements = this.slice(fromIndices)
	this.addAll(toIndex, elements)
	this.removeAllAt(fromIndices)
}


/**
 * Appends the string from all the entries separated using [separator] and using the given [prefix] and [postfix] if supplied.
 *
 * If the map could be huge, you can specify a non-negative value of [limit], in which case only the first [limit]
 * elements will be appended, followed by the [truncated] string (which defaults to "...").
 *
 * The default entry format is `$key=$value`.
 */
fun <K, V, A : Appendable> Map<K, V>.joinTo(
	buffer: A, separator: CharSequence = ", ", prefix: CharSequence = "", postfix: CharSequence = "",
	limit: Int = -1, truncated: CharSequence = "...", transform: ((Map.Entry<K, V>) -> CharSequence)? = null
): A {
	return this.entries.joinTo(buffer, separator, prefix, postfix, limit, truncated, transform)
}

/**
 * Creates a string from all the entries separated using [separator] and using the given [prefix] and [postfix] if supplied.
 *
 * If the map could be huge, you can specify a non-negative value of [limit], in which case only the first [limit]
 * elements will be appended, followed by the [truncated] string (which defaults to "...").
 *
 * The default entry format is `$key=$value`.
 */
fun <K, V> Map<K, V>.joinToString(
	separator: CharSequence = ", ", prefix: CharSequence = "", postfix: CharSequence = "",
	limit: Int = -1, truncated: CharSequence = "...", transform: ((Map.Entry<K, V>) -> CharSequence)? = null
): String {
	return this.joinTo(StringBuilder(), separator, prefix, postfix, limit, truncated, transform).toString()
}


/**
 * Returns a array containing the results of applying the given [transform] function
 * to each element in the original list.
 */
inline fun <T, reified R> List<T>.mapToArray(transform: (T) -> R): Array<R> {
	return Array(size) { transform(this[it]) }
}

/**
 * Returns a array containing the results of applying the given [transform] function
 * to each element in the original set.
 */
inline fun <T, reified R> Set<T>.mapToArray(transform: (T) -> R): Array<R> {
	val list = this.toList()
	return Array(size) { transform(list[it]) }
}

/**
 * 映射当前映射中的值，并过滤转化后为null的值。
 */
inline fun <K, V, R : Any> Map<out K, V>.mapValuesNotNull(transform: (V) -> R?): Map<K, R> {
	return mapValuesNotNullTo(LinkedHashMap(), transform)
}

/**
 * 映射当前映射中的值，并过滤转化后为null的值，然后加入指定的映射。
 */
inline fun <K, V, R : Any, M : MutableMap<in K, in R>> Map<K, V>.mapValuesNotNullTo(destination: M, transform: (V) -> R?): M {
	for((key, value) in this) transform(value)?.let { destination[key] = it }
	return destination
}


/**
 * 过滤当前映射中值为null的键值对。
 */
fun <K, V : Any> Map<out K, V?>.filterValuesNotNull(): Map<K, V> {
	return filterValuesNotNullTo(LinkedHashMap())
}

/**
 * 过滤当前映射中值为null的键值对，然后加入指定的映射。
 */
fun <K, V : Any, M : MutableMap<in K, in V>> Map<out K, V?>.filterValuesNotNullTo(destination: M): M {
	for((key, value) in this) if(value != null) destination[key] = value
	return destination
}


/**
 * 根据指定的条件，绑定当前数组和另一个数组中的元素，以当前数组长度为准，过滤不满足的情况。
 */
inline fun <T, R> Array<out T>.bind(other: Array<out R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**
 * 根据指定的条件，绑定当前数组和另一个集合中的元素，以当前数组长度为准，过滤不满足的情况。
 */
inline fun <T, R : Any> Array<out T>.bind(other: Iterable<R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**
 * 根据指定的条件，绑定当前集合和另一个数组中的元素，以当前数组长度为准，过滤不满足的情况。
 */
inline fun <T, R> Iterable<T>.bind(other: Array<out R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**
 * 根据指定的条件，绑定当前集合和另一个集合中的元素，以当前数组长度为准，过滤不满足的情况。
 */
inline fun <T, R> Iterable<T>.bind(other: Iterable<R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**
 * 根据指定的条件，绑定当前序列和另一个序列中的元素，以当前数组长度为准，过滤不满足的情况。
 */
inline fun <T, R> Sequence<T>.bind(other: Sequence<R>, crossinline predicate: (T, R) -> Boolean): Sequence<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}


/**
 * 根据指定的级别选择器，按级别从低到高向下折叠集合中的元素，返回包含完整的级别从低到高的元素组的列表。
 * 级别从1开始。
 */
@UnstableApi
fun <T, L : Comparable<L>> List<T>.collapse(levelSelector: (T) -> L): List<List<T>> {
	val elementAndLevels = this.map { it to levelSelector(it) }
	val result = mutableListOf<List<T>>()
	val parents = mutableListOf<T>()
	val parentLevels = mutableListOf<L>()
	elementAndLevels.forEachIndexed { index, (element, level) ->
		if(index == this.lastIndex) {
			result += parents + element
		} else {
			val (_, nextLevel) = elementAndLevels[index + 1]
			if(nextLevel <= level) {
				result += parents + element
				repeat(parentLevels.count { it >= nextLevel }) {
					parents.removeLast()
					parentLevels.removeLast()
				}
			} else {
				parents += element
				parentLevels += level
			}
		}
	}
	return result
}

/**
 * 根据指定的操作，遍历当前列表，递归展开并收集遍历过程中的每一个元素，直到结果为空为止，返回收集到的元素的列表。
 */
@UnstableApi
fun <T> List<T>.expand(operation: (T) -> Iterable<T>): List<T> {
	val result = mutableListOf<T>()
	var nextResult = this
	//递归进行操作，直到结果中不再有数据
	while(nextResult.any()) {
		result += nextResult
		nextResult = nextResult.flatMap { e ->
			val r = operation(e)
			//如果进行操作后得到的结果只有1个且与原元素相等，则跳过
			if(r.singleOrNull() != e) r else emptyList()
		}
	}
	return result
}

/**
 * 根据指定的预测，将当前集合中的符合条件的元素，依次固定到指定的索引处。默认固定到列表最前面。
 */
@UnstableApi
inline fun <T> Iterable<T>.pin(index: Int = 0, predicate: (T) -> Boolean): List<T> {
	val result = mutableListOf<T>()
	var i = index
	for(e in this) {
		if(predicate(e)) result.add(i++, e) else result.add(e)
	}
	return result
}

/**
 * 根据指定的一组键以及键选择器，选择当前集合中的元素，返回选择后的元素的列表。保持原有顺序，并覆盖先选择的元素。
 */
@UnstableApi
inline fun <T, K> Iterable<T>.select(vararg keys: K, keySelector: (T) -> K): List<T> {
	val result = ArrayList<T>(keys.size)
	for(e in this) {
		val keyIndex = keys.indexOf(keySelector(e))
		if(keyIndex != -1) result[keyIndex] = e
	}
	return result
}


/**
 * 根据指定的深度递归平滑化当前数组。
 * 如果指定了深度，则会递归映射到该深度，或者直到找不到集合类型的元素为止。默认不指定深度。
 *
 * 支持[Array]、[Iterable]和[Sequence]。
 */
@JvmOverloads
fun <T> Array<*>.deepFlatten(depth: Int = -1): List<T> {
	return this.doDeepFlatten(depth)
}

/**
 * 根据指定的深度递归平滑化当前集合。
 * 如果指定了深度，则会递归映射到该深度，或者直到找不到集合类型的元素为止。默认不指定深度。
 *
 * 支持[Array]、[Iterable]和[Sequence]。
 */
@JvmOverloads
fun <T> Iterable<*>.deepFlatten(depth: Int = -1): List<T> {
	return this.doDeepFlatten(depth)
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

private fun <T> Any?.doDeepFlatten(depth: Int): List<T> {
	require(depth == -1 || depth > 0) { "Flatten depth '$depth' cannot be non-positive." }
	var values = listOf(this)
	var currentDepth = depth
	while(currentDepth != 0) {
		//用来判断这次循环中是否找到集合类型的数据，以判断是否需要进行下一次循环
		var hasNoneCollectionElement = true
		values = values.flatMap { value ->
			when(value) {
				is Array<*> -> {
					hasNoneCollectionElement = false
					value.asIterable()
				}
				is Iterable<*> -> {
					hasNoneCollectionElement = false
					value
				}
				is Sequence<*> -> {
					hasNoneCollectionElement = false
					value.asIterable()
				}
				else -> listOf(value)
			}
		}
		if(hasNoneCollectionElement) break
		currentDepth--
	}
	return values as List<T>
}
//endregion

//region Convert Extensions
/**
 * 将当前列表转化为新的并发列表。
 */
fun <T> List<T>.asConcurrent(): CopyOnWriteArrayList<T> {
	return CopyOnWriteArrayList(this)
}

/**
 * 将当前集转化为新的并发集。
 */
fun <T> Set<T>.asConcurrent(): CopyOnWriteArraySet<T> {
	return CopyOnWriteArraySet(this)
}

/**
 * 将当前映射转化为新的并发映射。
 */
fun <K, V> Map<K, V>.asConcurrent(): ConcurrentMap<K, V> {
	return ConcurrentHashMap(this)
}

/**
 * 将当前列表转化为可变列表。如果当前列表本身就是可变列表，则直接返回。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
fun <T> List<T>.asMutable(): MutableList<T> {
	return when {
		this is MutableList<*> -> this as MutableList<T>
		else -> this.toMutableList()
	}
}

/**
 * 将当前列表转化为不可变列表。如果当前列表的元素数量大于1，则直接返回。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
fun <T> MutableList<T>.asImmutable(): List<T> {
	return when(size) {
		0 -> emptyList()
		1 -> listOf(this.get(0))
		else -> this
	}
}

/**
 * 将当前集转化为可变集。如果当前集本身就是可变集，则直接返回。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
fun <T> Set<T>.asMutable(): MutableSet<T> {
	return when {
		this is MutableSet<*> -> this as MutableSet<T>
		else -> this.toMutableSet()
	}
}

/**
 * 将当前集转化为不可变集。如果当前集的元素数量大于1，则直接返回。
 */
@Deprecated("Duplicate extension.", level = DeprecationLevel.HIDDEN)
fun <T> MutableSet<T>.asImmutable(): Set<T> {
	return when(size) {
		0 -> emptySet()
		1 -> setOf(iterator().next())
		else -> this
	}
}


/**
 * 将当前对象转化为单例列表。
 *
 * 如果当前对象为`null`且参数[orEmpty]为`true`，则会返回空列表。
 */
fun <T> T.toSingletonList(orEmpty: Boolean = false): List<T> {
	return if(orEmpty && this == null) Collections.emptyList() else Collections.singletonList(this)
}

/**
 * 将当前对象转化为单例集。
 *
 * 如果当前对象为`null`且参数[orEmpty]为`true`，则会返回空集。
 */
fun <T> T.toSingletonSet(orEmpty: Boolean = false): Set<T> {
	return if(orEmpty && this == null) Collections.emptySet() else Collections.singleton(this)
}

/**
 * 将当前二元素元组转化为单例映射。
 *
 * 如果当前二元素元组其中对应的键或值为`null`且参数[orEmpty]为`true`，则会返回空映射。
 */
fun <K, V> Pair<K, V>.toSingletonMap(orEmpty: Boolean = false): Map<K, V> {
	return if(orEmpty && (first == null || second == null)) Collections.emptyMap() else Collections.singletonMap(first, second)
}


/**
 * 将当前键值对数组转化为新的可变映射。
 */
fun <K, V> Array<out Pair<K, V>>.toMutableMap(): MutableMap<K, V> {
	return this.toMap(LinkedHashMap())
}

/**
 * 将当前键值对列表转化为新的可变映射。
 */
fun <K, V> Iterable<Pair<K, V>>.toMutableMap(): MutableMap<K, V> {
	return this.toMap(LinkedHashMap())
}

/**
 * 将当前键值对序列转化为新的可变映射。
 */
fun <K, V> Sequence<Pair<K, V>>.toMutableMap(): MutableMap<K, V> {
	return this.toMap(LinkedHashMap())
}


/**
 * 将当前数组转化为新的以键为值的映射。
 */
fun <T> Array<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associateBy({ it.index.toString() }, { it.value })
}

/**
 * 将当前集合转化为新的以键为值的映射。
 */
fun <T> Iterable<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associateBy({ it.index.toString() }, { it.value })
}

/**
 * 将当前映射转化为新的以字符串为键的映射。
 */
fun <K, V> Map<K, V>.toStringKeyMap(): Map<String, V> {
	return this.mapKeys { (k, _) -> k.toString() }
}

/**
 * 将当前序列转化为新的以键为值的映射。
 */
fun <T> Sequence<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associateBy({ it.index.toString() }, { it.value })
}
//endregion

//region Specific Extensions
/**
 * 得到指定索引的字符串，如果索引越界，则返回空字符串。
 */
inline fun Array<out String>.getOrEmpty(index: Int): String {
	return this.getOrNull(index) ?: ""
}

/**
 * 得到指定索引的字符串，如果索引越界，则返回空字符串。
 */
inline fun List<String>.getOrEmpty(index: Int): String {
	return this.getOrNull(index) ?: ""
}

/**
 * 得到指定键的字符串，如果值为null，则返回空字符串。
 */
inline fun <K> Map<K, String>.getOrEmpty(key: K): String {
	return this[key] ?: ""
}


/**
 * 去除起始的空字符串。
 */
inline fun <T : CharSequence> Array<out T>.dropEmpty(): List<T> {
	return this.dropWhile { it.isEmpty() }
}

/**
 * 去除起始的空字符串。
 */
inline fun <T : CharSequence> Iterable<T>.dropEmpty(): List<T> {
	return this.dropWhile { it.isEmpty() }
}

/**
 * 去除起始的空字符串。
 */
inline fun <T : CharSequence> Sequence<T>.dropEmpty(): Sequence<T> {
	return this.dropWhile { it.isEmpty() }
}

/**
 * 去除尾随的空字符串。
 */
inline fun <T : CharSequence> Array<out T>.dropLastEmpty(): List<T> {
	return this.dropLastWhile { it.isEmpty() }
}

/**
 * 去除尾随的空字符串。
 */
inline fun <T : CharSequence> List<T>.dropLastEmpty(): List<T> {
	return this.dropLastWhile { it.isEmpty() }
}


/**
 * 去除起始的空白字符串。
 */
inline fun <T : CharSequence> Array<out T>.dropBlank(): List<T> {
	return this.dropWhile { it.isBlank() }
}

/**
 * 去除起始的空白字符串。
 */
inline fun <T : CharSequence> Iterable<T>.dropBlank(): List<T> {
	return this.dropWhile { it.isBlank() }
}

/**
 * 去除起始的空白字符串。
 */
inline fun <T : CharSequence> Sequence<T>.dropBlank(): Sequence<T> {
	return this.dropWhile { it.isBlank() }
}

/**
 * 去除尾随的空白字符串。
 */
inline fun <T : CharSequence> Array<out T>.dropLastBlank(): List<T> {
	return this.dropLastWhile { it.isBlank() }
}

/**
 * 去除尾随的空白字符串。
 */
inline fun <T : CharSequence> List<T>.dropLastBlank(): List<T> {
	return this.dropLastWhile { it.isBlank() }
}


/**
 * 过滤当前数组中为空字符串的元素。
 */
inline fun <T : CharSequence> Array<out T>.filterNotEmpty(): List<T> {
	return this.filterNotEmptyTo(ArrayList())
}

/**
 * 过滤当前集合中为空字符串的元素。
 */
inline fun <T : CharSequence> Iterable<T>.filterNotEmpty(): List<T> {
	return this.filterNotEmptyTo(ArrayList())
}

/**
 * 过滤当前映射中值为空字符串的键值对。
 */
inline fun <K, V : CharSequence> Map<out K, V>.filterValuesNotEmpty(): Map<K, V> {
	val result = LinkedHashMap<K, V>()
	for((key, value) in this) if(value.isNotEmpty()) result[key] = value
	return result
}

/**
 * 过滤当前序列中为空字符串的元素。
 */
inline fun <T : CharSequence> Sequence<T>.filterNotEmpty(): Sequence<T> {
	return this.filter { it.isNotEmpty() }
}


/**
 * 过滤当前数组中为空字符串的元素，然后加入到指定的集合。
 */
inline fun <T : CharSequence, C : MutableCollection<in T>> Array<out T>.filterNotEmptyTo(destination: C): C {
	for(element in this) if(element.isNotEmpty()) destination += element
	return destination
}

/**
 * 过滤当前集合中为空字符串的元素，然后加入到指定的集合。
 */
inline fun <T : CharSequence, C : MutableCollection<in T>> Iterable<T>.filterNotEmptyTo(destination: C): C {
	for(element in this) if(element.isNotEmpty()) destination += element
	return destination
}


/**
 * 过滤当前数组中为空白字符串的元素。
 */
inline fun <T : CharSequence> Array<out T>.filterNotBlank(): List<T> {
	return this.filterNotBlankTo(ArrayList())
}

/**
 * 过滤当前集合中为空白字符串的元素。
 */
inline fun <T : CharSequence> Iterable<T>.filterNotBlank(): List<T> {
	return this.filterNotBlankTo(ArrayList())
}

/**
 * 过滤当前映射中值为空白字符串的键值对。
 */
inline fun <K, V : CharSequence> Map<out K, V>.filterValuesNotBlank(): Map<K, V> {
	val result = LinkedHashMap<K, V>()
	for((key, value) in this) if(value.isNotBlank()) result[key] = value
	return result
}

/**
 * 过滤当前序列中为空白字符串的元素。
 */
inline fun <T : CharSequence> Sequence<T>.filterNotBlank(): Sequence<T> {
	return this.filter { it.isNotBlank() }
}


/**
 * 过滤当前数组中为空白字符串的元素，然后加入到指定的集合。
 */
inline fun <T : CharSequence, C : MutableCollection<in T>> Array<out T>.filterNotBlankTo(destination: C): C {
	for(element in this) if(element.isNotBlank()) destination += element
	return destination
}

/**
 * 过滤当前集合中为空白字符串的元素，然后加入到指定的集合。
 */
inline fun <T : CharSequence, C : MutableCollection<in T>> Iterable<T>.filterNotBlankTo(destination: C): C {
	for(element in this) if(element.isNotBlank()) destination += element
	return destination
}


/**
 * 过滤当前数组中为null或空字符串的元素。
 */
@UselessCallOnNotNullType
inline fun <T : CharSequence> Array<out T?>.filterNotNullOrEmpty(): List<T> {
	return this.filterNotNullOrEmptyTo(ArrayList())
}

/**
 * 过滤当前集合中为null或空字符串的元素。
 */
@UselessCallOnNotNullType
inline fun <T : CharSequence> Iterable<T?>.filterNotNullOrEmpty(): List<T> {
	return this.filterNotNullOrEmptyTo(ArrayList())
}

/**
 * 过滤当前映射中值为null或空字符串的键值对。
 */
@UselessCallOnNotNullType
inline fun <K, V : CharSequence> Map<out K, V>.filterValuesNotNullOrEmpty(): Map<K, V> {
	val result = LinkedHashMap<K, V>()
	for((key, value) in this) if(value.isNotNullOrEmpty()) result[key] = value
	return result
}

/**
 * 过滤当前序列中为null或空字符串的元素。
 */
@UselessCallOnNotNullType
inline fun <T : CharSequence> Sequence<T?>.filterNotNullOrEmpty(): Sequence<T> {
	return this.filter { it.isNotNullOrEmpty() } as Sequence<T>
}


/**
 * 过滤当前数组中为null或空字符串的元素，然后加入到指定的集合。
 */
@UselessCallOnNotNullType
inline fun <T : CharSequence, C : MutableCollection<in T>> Array<out T?>.filterNotNullOrEmptyTo(destination: C): C {
	for(element in this) if(element.isNotNullOrEmpty()) destination += element
	return destination
}

/**
 * 过滤当前集合中为null或空字符串的元素，然后加入到指定的集合。
 */
@UselessCallOnNotNullType
inline fun <T : CharSequence, C : MutableCollection<in T>> Iterable<T?>.filterNotNullOrEmptyTo(destination: C): C {
	for(element in this) if(element.isNotNullOrEmpty()) destination += element
	return destination
}


/**
 * 过滤当前数组中为null或空白字符串的元素。
 */
@UselessCallOnNotNullType
inline fun <T : CharSequence> Array<out T?>.filterNotNullOrBlank(): List<T> {
	return this.filterNotNullOrBlankTo(ArrayList())
}

/**
 * 过滤当前集合中为null或空白字符串的元素。
 */
@UselessCallOnNotNullType
inline fun <T : CharSequence> Iterable<T?>.filterNotNullOrBlank(): List<T> {
	return this.filterNotNullOrBlankTo(ArrayList())
}

/**
 * 过滤当前映射中值为null或空白字符串的键值对。
 */
@UselessCallOnNotNullType
inline fun <K, V : CharSequence> Map<out K, V>.filterValuesNotNullOrBlank(): Map<K, V> {
	val result = LinkedHashMap<K, V>()
	for((key, value) in this) if(value.isNotNullOrBlank()) result[key] = value
	return result
}

/**
 * 过滤当前序列中为null或空白字符串的元素。
 */
@UselessCallOnNotNullType
inline fun <T : CharSequence> Sequence<T?>.filterNotNullOrBlank(): Sequence<T> {
	return this.filter { it.isNotNullOrBlank() } as Sequence<T>
}


/**
 * 过滤当前数组中为null或空白字符串的元素，然后加入到指定的集合。
 */
@UselessCallOnNotNullType
inline fun <T : CharSequence, C : MutableCollection<in T>> Array<out T?>.filterNotNullOrBlankTo(destination: C): C {
	for(element in this) if(element.isNotNullOrBlank()) destination += element
	return destination
}

/**
 * 过滤当前集合中为null或空白字符串的元素，然后加入到指定的集合。
 */
@UselessCallOnNotNullType
inline fun <T : CharSequence, C : MutableCollection<in T>> Iterable<T?>.filterNotNullOrBlankTo(destination: C): C {
	for(element in this) if(element.isNotNullOrBlank()) destination += element
	return destination
}
//endregion
