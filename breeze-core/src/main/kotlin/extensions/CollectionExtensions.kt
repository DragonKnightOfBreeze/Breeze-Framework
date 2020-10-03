// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("CollectionExtensions")
@file:Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE", "IMPLICIT_CAST_TO_ANY", "unused")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.domain.text.*
import java.lang.reflect.*
import java.util.*
import java.util.concurrent.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap
import kotlin.contracts.*

//注意：可以通过添加注解 @Suppress("CANNOT_CHECK_FOR_ERASED") 检查数组的泛型如 array is Array<String>
//注意：可以通过添加注解 @Suppress("UNSUPPORTED") 启用字面量数组如 [1, 2, 3]
//注意：某些情况下，如果直接参照标准库的写法编写扩展方法，会报编译器错误

//region operator extensions
/**
 * 重复当前列表中的元素到指定次数。
 * @see com.windea.breezeframework.core.extensions.repeat
 */
operator fun <T> List<T>.times(n: Int): List<T> = this.repeat(n)

/**
 * 切分当前集合中的元素到指定个数。
 * @see kotlin.collections.chunked
 **/
operator fun <T> Iterable<T>.div(n: Int): List<List<T>> = this.chunked(n)

/**
 * 得到索引指定范围内的子列表。
 * @see kotlin.collections.slice
 */
operator fun <T> List<T>.get(indices: IntRange): List<T> = this.slice(indices)

/**
 * 得到索引指定范围内的子列表。
 * @see kotlin.collections.List.subList
 */
operator fun <T> List<T>.get(startIndex: Int, endIndex: Int): List<T> = this.subList(startIndex, endIndex)
//endregion

//region common extensions
//虽然不知道为什么，但是的确可以这样做 ヾ(*´▽‘*)ﾉ

/**得到当前数组的最为适配的元素类型。*/
inline val Array<*>.elementType: Type get() = this::class.java.componentType

/**得到当前集合的最为适配的元素类型。*/
inline val <reified T> Iterable<T>.elementType: Type get() = javaTypeOf<T>()

/**得到当前映射的最为适配的键类型。*/
inline val <reified K> Map<K, *>.keyType: Type get() = javaTypeOf<K>()

/**得到当前映射的最为适配的值类型。*/
inline val <reified V> Map<*, V>.valueType: Type get() = javaTypeOf<V>()

/**得到当前序列的最为适配的键类型*/
inline val <reified T> Sequence<T>.elementType: Type get() = javaTypeOf<T>()


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


/**判断当前数组是否不为null，且不为空。*/
@UselessCallOnNotNullType
@JvmSynthetic
inline fun Array<*>?.isNotNullOrEmpty(): Boolean {
	contract {
		returns(true) implies (this@isNotNullOrEmpty != null)
	}
	return this != null && this.isNotEmpty()
}

/**判断当前集合是否不为null，且不为空。*/
@UselessCallOnNotNullType
@JvmSynthetic
inline fun <T> Collection<T>?.isNotNullOrEmpty(): Boolean {
	contract {
		returns(true) implies (this@isNotNullOrEmpty != null)
	}
	return this != null && this.isNotEmpty()
}

/**判断当前映射是否不为null，且不为空。*/
@UselessCallOnNotNullType
@JvmSynthetic
inline fun <K, V> Map<out K, V>?.isNotNullOrEmpty(): Boolean {
	contract {
		returns(true) implies (this@isNotNullOrEmpty != null)
	}
	return this != null && this.isNotEmpty()
}

/**判断当前序列是否为空。*/
inline fun <T> Sequence<T>.isEmpty() = !this.iterator().hasNext()

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
inline fun <C, R> C.ifNotEmpty(transform: (C) -> R): R where C : Array<*>, C : R {
	return if(this.isEmpty()) this else transform(this)
}

/**如果当前集合不为空，则返回转化后的值，否则返回本身。*/
@JvmSynthetic
@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER")
inline fun <C, R> C.ifNotEmpty(transform: (C) -> R): R where C : Collection<*>, C : R {
	return if(this.isEmpty()) this else transform(this)
}

/**如果当前映射不为空，则返回转化后的值，否则返回本身。*/
@JvmSynthetic
@Suppress("BOUNDS_NOT_ALLOWED_IF_BOUNDED_BY_TYPE_PARAMETER")
inline fun <M, R> M.ifNotEmpty(transform: (M) -> R): R where M : Map<*, *>, M : R {
	return if(this.isEmpty()) this else transform(this)
}


/**得到指定索引的元素，发生异常则得到默认值。*/
fun <T> Array<out T>.getOrDefault(index: Int, defaultValue: T): T {
	return this.getOrElse(index) { defaultValue }
}

/**得到指定索引的元素，发生异常则得到默认值。*/
fun <T> List<T>.getOrDefault(index: Int, defaultValue: T): T {
	return this.getOrElse(index) { defaultValue }
}


/**将指定的键值对放入当前映射中。*/
fun <K, V> MutableMap<K, V>.put(pair: Pair<K, V>) = this.put(pair.first, pair.second)


/**
 * 根据指定路径查询当前数组，返回匹配的路径-值映射。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意返回映射的值的类型应当与指定的泛型类型一致，否则会发生异常。
 */
fun <T> Array<*>.query(path: String): Map<String, T> = this.query0(path)

/**
 * 根据指定路径查询当前集合，返回匹配的路径-值映射。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意返回映射的值的类型应当与指定的泛型类型一致，否则会发生异常。
 */
fun <T> Iterable<*>.query(path: String): Map<String, T> = this.query0(path)

/**
 * 根据指定路径查询当前映射，返回匹配的路径-值映射。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意返回映射的值的类型应当与指定的泛型类型一致，否则会发生异常。
 */
fun <T> Map<*, *>.query(path: String): Map<String, T> = this.query0(path)

private fun <T> Any?.query0(path: String): Map<String, T> {
	return try {
		when {
			path.isPathOfMapLike() -> this.toQueryMap()
			path.isPathOfListLike() -> this.toQueryMap()
			path.isPathOfRegex() -> this.collectionSlice(path.substring(3).toRegex()).toQueryMap()
			path.isPathOfIndices() -> this.collectionSlice(path.toIntRange()).toQueryMap()
			else -> this.collectionGetOrNull(path).toSingletonQueryMap(path)
		}
	} catch(e: Exception) {
		this.collectionGetOrNull(path).toSingletonQueryMap(path)
	} as Map<String, T>
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


/**
 * Appends the string from all the entries separated using [separator] and using the given [prefix] and [postfix] if supplied.
 *
 * If the map could be huge, you can specify a non-negative value of [limit], in which case only the first [limit]
 * elements will be appended, followed by the [truncated] string (which defaults to "...").
 *
 * The default entry format is `$key=$value`.
 */
fun <K, V, A : Appendable> Map<K, V>.joinTo(buffer: A, separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	transform: ((Map.Entry<K, V>) -> CharSequence)? = null): A {
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
fun <K, V> Map<K, V>.joinToString(separator: CharSequence = ", ", prefix: CharSequence = "", postfix: CharSequence = "",
	limit: Int = -1, truncated: CharSequence = "...", transform: ((Map.Entry<K, V>) -> CharSequence)? = null): String {
	return this.joinTo(StringBuilder(), separator, prefix, postfix, limit, truncated, transform).toString()
}


/**
 * Returns a array containing the results of applying the given [transform] function
 * to each element in the original array.
 */
inline fun <T, reified R> Array<out T>.mapArray(transform: (T) -> R): Array<R> {
	return Array(size) { transform(this[it]) }
}

/**
 * Returns a array containing the results of applying the given [transform] function
 * to each element in the original list.
 */
inline fun <T, reified R> List<T>.mapArray(transform: (T) -> R): Array<R> {
	return Array(size) { transform(this[it]) }
}


/**映射当前映射中的值，并过滤转化后为null的值。*/
inline fun <K, V, R : Any> Map<out K, V>.mapValuesNotNull(transform: (V) -> R?): Map<K, R> {
	return this.mapValuesNotNullTo(LinkedHashMap(), transform)
}

/**映射当前映射中的值，并过滤转化后为null的值，然后加入指定的映射。*/
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


/**根据指定的条件，绑定当前数组和另一个数组中的元素，以当前数组长度为准，过滤不满足的情况。*/
inline fun <T, R> Array<out T>.bind(other: Array<out R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**根据指定的条件，绑定当前数组和另一个集合中的元素，以当前数组长度为准，过滤不满足的情况。*/
inline fun <T, R : Any> Array<out T>.bind(other: Iterable<R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**根据指定的条件，绑定当前集合和另一个数组中的元素，以当前数组长度为准，过滤不满足的情况。*/
inline fun <T, R> Iterable<T>.bind(other: Array<out R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**根据指定的条件，绑定当前集合和另一个集合中的元素，以当前数组长度为准，过滤不满足的情况。*/
inline fun <T, R> Iterable<T>.bind(other: Iterable<R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**根据指定的条件，绑定当前序列和另一个序列中的元素，以当前数组长度为准，过滤不满足的情况。*/
inline fun <T, R> Sequence<T>.bind(other: Sequence<R>, crossinline predicate: (T, R) -> Boolean): Sequence<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}


/**根据指定的级别选择器，按级别从低到高向下折叠集合中的元素，返回包含完整的级别从低到高的元素组的列表。级别从1开始。*/
fun <T:Any, L : Comparable<L>> List<T>.collapse(levelSelector:(T)->L):List<List<T>>{
	val elementAndLevels = this.map{ it to levelSelector(it) }
	val result = mutableListOf<List<T>>()
	val parents = mutableListOf<T>()
	val parentLevels = mutableListOf<L>()
	elementAndLevels.forEachIndexed { index,(element,level)->
		if(index == this.lastIndex ) {
			result += parents +element
		}else{
			val (_,nextLevel ) = elementAndLevels[index + 1]
			if(nextLevel <= level){
				result += parents +element
				repeat(parentLevels.count{ it >= nextLevel }){
					parents.removeLast()
					parentLevels.removeLast()
				}
			}else{
				parents += element
				parentLevels += level
			}
		}
	}
	return result
}

/**根据指定的操作，以初始值为起点，递归展开并收集遍历过程中的每一个元素，直到结果为空为止，返回收集到的元素的列表。*/
@UnstableApi
inline fun <T> T.expand(operation: (T) -> Iterable<T>): List<T> {
	var nextResult = operation(this)
	//如果经过一次操作后没有发生变化，则直接返回
	if(nextResult.singleOrNull() == this) return nextResult.toList()
	//否则递归进行操作，直到结果中不再有数据
	val result = mutableListOf<T>()
	while(nextResult.any()) {
		result += nextResult
		nextResult = nextResult.flatMap { operation(it) }
	}
	return result
}
//endregion

//region deep operation extensions
/**
 * 根据指定路径得到当前数组中的元素。
 * 可指定路径的格式，默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意返回值的类型应当与指定的泛型类型一致，否则会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Array<*>.deepGet(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference): T {
	return this.deepGet0(path, pathCase)
}

/**
 * 根据指定路径得到当前列表中的元素。
 * 可指定路径的格式，默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意返回值的类型应当与指定的泛型类型一致，否则会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> List<*>.deepGet(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference): T {
	return this.deepGet0(path, pathCase)
}

/**
 * 根据指定路径得到当前映射中的元素。
 * 可指定路径的格式，默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意返回值的类型应当与指定的泛型类型一致，否则会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Map<*, *>.deepGet(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference): T {
	return this.deepGet0(path, pathCase)
}

private fun <T> Any?.deepGet0(path: String, pathCase: ReferenceCase): T {
	val pathList = path.splitBy(pathCase)
	require(pathList.isNotEmpty()) { "Path '$path' cannot be empty." }
	var currentValue = this
	for(p in pathList) {
		currentValue = currentValue.collectionGet(p)
	}
	return currentValue as T
}


/**
 * 根据指定路径得到当前映射中的元素，如果发生异常，则返回null。
 * 可指定路径的格式，默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意返回值的类型应当与指定的泛型类型一致，否则会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Array<*>.deepGetOrNull(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference): T? {
	return this.deepGetOrNull0(path, pathCase)
}

/**
 * 根据指定路径得到当前映射中的元素，如果发生异常，则返回null。
 * 可指定路径的格式，默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意返回值的类型应当与指定的泛型类型一致，否则会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> List<*>.deepGetOrNull(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference): T? {
	return this.deepGetOrNull0(path, pathCase)
}

/**
 * 根据指定路径得到当前映射中的元素，如果发生异常，则返回null。
 * 可指定路径的格式，默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意返回值的类型应当与指定的泛型类型一致，否则会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Map<*, *>.deepGetOrNull(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference): T? {
	return this.deepGetOrNull0(path, pathCase)
}

private fun <T> Any?.deepGetOrNull0(path: String, pathCase: ReferenceCase): T? {
	val splitPaths = path.splitBy(pathCase)
	require(splitPaths.isNotEmpty()) { "Path '$path' cannot be empty." }
	var currentValue = this
	for(p in splitPaths) {
		currentValue = currentValue.collectionGetOrNull(p)
	}
	return currentValue as T
}


/**
 * 根据指定路径得到当前映射中的元素，如果发生异常，则返回默认值。
 * 可指定路径的格式，默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意返回值的类型应当与指定的泛型类型一致，否则会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Array<*>.deepGetOrElse(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference, defaultValue: () -> T): T {
	return this.deepGetOrNull0(path, pathCase) ?: defaultValue()
}

/**
 * 根据指定路径得到当前映射中的元素，如果发生异常，则返回默认值。
 * 可指定路径的格式，默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意返回值的类型应当与指定的泛型类型一致，否则会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> List<*>.deepGetOrElse(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference, defaultValue: () -> T): T {
	return this.deepGetOrNull0(path, pathCase) ?: defaultValue()
}

/**
 * 根据指定路径得到当前映射中的元素，如果发生异常，则返回默认值。
 * 可指定路径的格式，默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意返回值的类型应当与指定的泛型类型一致，否则会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Map<*, *>.deepGetOrElse(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference, defaultValue: () -> T): T {
	return this.deepGetOrNull0(path, pathCase) ?: defaultValue()
}


/**
 * 根据指定路径设置当前数组中的元素。
 * 指定路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[MutableList]和[MutableMap]。
 * 向下定位元素时支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意指定的值的类型应当与对应集合的泛型类型一致，否则可能会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Array<*>.deepSet(path: String, value: T, pathCase: ReferenceCase = ReferenceCase.PathReference) {
	this.deepSet0(path, value, pathCase)
}

/**
 * 根据指定路径设置当前列表中的元素。
 * 指定路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[MutableList]和[MutableMap]。
 * 向下定位元素时支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意指定的值的类型应当与对应集合的泛型类型一致，否则可能会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> MutableList<*>.deepSet(path: String, value: T, pathCase: ReferenceCase = ReferenceCase.PathReference) {
	this.deepSet0(path, value, pathCase)
}

/**
 * 根据指定路径设置当前映射中的元素。
 * 指定路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[MutableList]和[MutableMap]。
 * 向下定位元素时支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意指定路径不能为空，否则会抛出异常。
 * 注意指定的值的类型应当与对应集合的泛型类型一致，否则可能会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> MutableMap<*, *>.deepSet(path: String, value: T, pathCase: ReferenceCase = ReferenceCase.PathReference) {
	this.deepSet0(path, value, pathCase)
}

private fun <T> Any?.deepSet0(path: String, value: T, pathCase: ReferenceCase) {
	val splitPaths = path.splitBy(pathCase)
	require(splitPaths.isNotEmpty()) { "Path '$path' cannot be empty." }
	var currentValue = this
	for(p in splitPaths.dropLast(1)) {
		currentValue = currentValue.collectionGet(p)
	}
	currentValue.collectionSet(splitPaths.last(), value)
}


/**
 * 根据指定路径递归查询当前数组，返回匹配的路径-值映射。
 * 指定路径的格式默认为路径引用。返回值中路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意返回映射的值的类型应当与指定的泛型类型一致，否则会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Array<*>.deepQuery(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference,
	returnPathCase: ReferenceCase = ReferenceCase.PathReference): Map<String, T> {
	return this.deepQuery0(path, pathCase, returnPathCase)
}

/**
 * 根据指定路径递归查询当前集合，返回匹配的路径-值映射。
 * 指定路径的格式默认为路径引用。返回值中路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意返回映射的值的类型应当与指定的泛型类型一致，否则会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Iterable<*>.deepQuery(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference,
	returnPathCase: ReferenceCase = ReferenceCase.PathReference): Map<String, T> {
	return this.deepQuery0(path, pathCase, returnPathCase)
}

/**
 * 根据指定路径递归查询当前映射，返回匹配的路径-值映射。
 * 指定路径的格式默认为路径引用。返回值中路径的格式默认为路径引用。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 注意返回映射的值的类型应当与指定的泛型类型一致，否则会发生异常。
 * @see ReferenceCase.PathReference
 */
@JvmOverloads
fun <T> Map<*, *>.deepQuery(path: String, pathCase: ReferenceCase = ReferenceCase.PathReference,
	returnPathCase: ReferenceCase = ReferenceCase.PathReference): Map<String, T> {
	return this.deepQuery0(path, pathCase, returnPathCase)
}

private fun <T> Any?.deepQuery0(path: String, pathCase: ReferenceCase, returnPathCase: ReferenceCase): Map<String, T> {
	val splitPaths = path.splitBy(pathCase)
	var pathValuePairs = listOf(arrayOf<String>() to this)
	for(p in splitPaths) {
		pathValuePairs = pathValuePairs.flatMap { (key, value) ->
			try {
				when {
					p.isPathOfMapLike() -> value.toDeepQueryPairList(key)
					p.isPathOfListLike() -> value.toDeepQueryPairList(key)
					p.isPathOfRegex() -> value.collectionSlice(p.substring(3).toRegex()).toDeepQueryPairList(key)
					p.isPathOfIndices() -> value.collectionSlice(p.toIntRange()).toDeepQueryPairList(key)
					else -> value.collectionGetOrNull(p).toSingletonDeepQueryPairList(key, p)
				}
			} catch(e: Exception) {
				value.collectionGetOrNull(p).toSingletonDeepQueryPairList(key, p)
			}
		}
	}
	return pathValuePairs.toDeepQueryMap(returnPathCase) as Map<String, T>
}


/**
 * 根据深度递归平滑化当前数组，返回匹配的值列表。
 * 默认递归映射直到找不到集合类型的元素为止。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 如果指定了递归的深度，则会递归映射到该深度，或者直到找不到集合类型的元素为止。
 */
@JvmOverloads
fun <T> Array<*>.deepFlatten(depth: Int = -1): List<T> {
	return this.deepFlatten0(depth)
}

/**
 * 根据深度递归平滑化当前集合，返回匹配的值列表。
 * 默认递归映射直到找不到集合类型的元素为止。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 如果指定了递归的深度，则会递归映射到该深度，或者直到找不到集合类型的元素为止。
 */
@JvmOverloads
fun <T> Iterable<*>.deepFlatten(depth: Int = -1): List<T> {
	return this.deepFlatten0(depth)
}

/**
 * 根据深度递归平滑化当前映射，返回匹配的值列表。
 * 默认递归映射直到找不到集合类型的元素为止。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 如果指定了递归的深度，则会递归映射到该深度，或者直到找不到集合类型的元素为止。
 */
@JvmOverloads
fun <T> Map<*, *>.deepFlatten(depth: Int = -1): List<T> {
	return this.deepFlatten0(depth)
}

/**
 * 根据深度递归平滑化当前序列，返回匹配的值列表。
 * 默认递归映射直到找不到集合类型的元素为止。
 * 支持的集合类型包括：[Array]、[Iterable]、[Map]和[Sequence]。
 * 如果指定了递归的深度，则会递归映射到该深度，或者直到找不到集合类型的元素为止。
 */
@JvmOverloads
fun <T> Sequence<*>.deepFlatten(depth: Int = -1): List<T> {
	return this.deepFlatten0(depth)
}

private fun <T> Any?.deepFlatten0(depth: Int): List<T> {
	require(depth == -1 || depth > 0) { "Depth '$depth' cannot be non-positive except -1." }
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
				is Map<*, *> -> {
					hasNoneCollectionElement = false
					value.values
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


private fun Any?.collectionGet(indexOrKey: String) = when(this) {
	is Array<*> -> this[indexOrKey.toInt()]
	is Iterable<*> -> this.elementAt(indexOrKey.toInt())
	is Map<*, *> -> this[indexOrKey]
	is Sequence<*> -> this.elementAt(indexOrKey.toInt())
	else -> throw IllegalArgumentException("Invalid type of receiver (Allow: Array, Iterable, Map or Sequence).")
}

private fun Any?.collectionGetOrNull(indexOrKey: String) = when(this) {
	is Array<*> -> this.getOrNull(indexOrKey.toInt())
	is Iterable<*> -> this.elementAtOrNull(indexOrKey.toInt())
	is Map<*, *> -> this[indexOrKey]
	is Sequence<*> -> this.elementAtOrNull(indexOrKey.toInt())
	else -> throw IllegalArgumentException("Invalid type of receiver (Allow: Array, Iterable, Map or Sequence).")
}

private fun Any?.collectionSet(indexOrKey: String, value: Any?) = when(this) {
	is Array<*> -> (this as Array<Any?>)[indexOrKey.toInt()] = value //这里可能会发生ArrayStoreException
	is MutableList<*> -> (this as MutableList<Any?>)[indexOrKey.toInt()] = value
	is MutableMap<*, *> -> (this as MutableMap<String, Any?>)[indexOrKey] = value
	else -> throw IllegalArgumentException("Invalid type of receiver (Allow: Array, MutableList or MutableMap).")
}

private fun Any?.collectionSlice(indices: IntRange) = when(this) {
	is Array<*> -> this.sliceArray(indices)
	is List<*> -> this.slice(indices)
	else -> throw IllegalArgumentException("Invalid type of receiver (Allow: Array, List).")
}

private fun Any?.collectionSlice(regex: Regex) = when(this) {
	is Array<*> -> this.filterIndexed { i, _ -> i.toString() matches regex }
	is Iterable<*> -> this.filterIndexed { i, _ -> i.toString() matches regex }
	is Map<*, *> -> this.filterKeys { it.toString() matches regex }
	is Sequence<*> -> this.filterIndexed { i, _ -> i.toString() matches regex }
	else -> throw IllegalArgumentException("Invalid type of receiver (Allow: Array, Iterable, Map or Sequence).")
}

private fun String.isPathOfMapLike() = this == "-" || this == "{}" || this.surroundsWith("{", "}")

private fun String.isPathOfListLike() = this == "-" || this == "[]" || this.surroundsWith("[", "]")

private fun String.isPathOfRegex() = this.startsWith("re:")

private fun String.isPathOfIndices() = this.contains("..") || this.contains("-") || this.contains("~")

private fun Any?.toQueryMap() = when(this) {
	is Array<*> -> this.toIndexKeyMap()
	is Iterable<*> -> this.toIndexKeyMap()
	is Map<*, *> -> this.toStringKeyMap()
	is Sequence<*> -> this.toIndexKeyMap()
	else -> throw IllegalArgumentException("Invalid type of receiver (Allow: Array, Iterable, Map or Sequence).")
}

private fun Any?.toSingletonQueryMap(path: String) = if(this == null) listOf() else listOf(path to this)

private fun Any?.toDeepQueryPairList(oldPaths: Array<String>) = when(this) {
	is Array<*> -> this.withIndex().map { (i, e) -> oldPaths + i.toString() to e }
	is Iterable<*> -> this.withIndex().map { (i, e) -> oldPaths + i.toString() to e }
	is Map<*, *> -> this.map { (k, v) -> oldPaths + k.toString() to v }
	is Sequence<*> -> this.withIndex().map { (i, e) -> oldPaths + i.toString() to e }.toList()
	else -> throw IllegalArgumentException("Invalid type of receiver (Allow: Array, Iterable, Map or Sequence).")
}

private fun Any?.toSingletonDeepQueryPairList(oldPaths: Array<String>, newPath: String) =
	if(this == null) listOf() else listOf(oldPaths + newPath to this)

private fun List<Pair<Array<String>, Any?>>.toDeepQueryMap(returnPathCase: ReferenceCase) =
	this.toMap().mapKeys { (k, _) -> k.joinToStringBy(returnPathCase) }
//endregion

//region convert extensions
/**将当前列表转化为新的并发列表。*/
fun <T> List<T>.asConcurrent(): CopyOnWriteArrayList<T> = CopyOnWriteArrayList(this)

/**将当前集转化为新的并发集。*/
fun <T> Set<T>.asConcurrent(): CopyOnWriteArraySet<T> = CopyOnWriteArraySet(this)

/**将当前映射转化为新的并发映射。*/
fun <K, V> Map<K, V>.asConcurrent(): ConcurrentMap<K, V> = ConcurrentHashMap(this)


/**
 * 将当前对象转换为单例集合。
 *
 * 支持的类型参数：[Collection] [MutableCollection] [Set] [MutableSet] [List] [MutableList]。
 */
inline fun <T, reified R> T.toSingleton(): R {
	return when(val type = R::class) {
		Collection::class -> Collections.singleton(this)
		MutableCollection::class -> Collections.singleton(this)
		Set::class -> Collections.singleton(this)
		MutableSet::class -> Collections.singleton(this)
		List::class -> Collections.singletonList(this)
		MutableList::class -> Collections.singletonList(this)
		else -> throw UnsupportedOperationException("Unsupported singleton collection type '${type.simpleName}'")
	} as R
}

/**
 * 如果当前对象不为null，则将当前对象转换为单例集合，否则转化为空集合。
 *
 * 支持的类型参数：[Collection] [MutableCollection] [Set] [MutableSet] [List] [MutableList]。
 */
inline fun <T, reified R> T.toSingletonOrEmpty(): R {
	return when(val type = R::class) {
		Collection::class -> if(this == null) Collections.emptySet() else Collections.singleton(this)
		MutableCollection::class -> if(this == null) Collections.emptySet() else Collections.singleton(this)
		Set::class -> if(this == null) Collections.emptySet() else Collections.singleton(this)
		MutableSet::class -> if(this == null) Collections.emptySet() else Collections.singleton(this)
		List::class -> if(this == null) Collections.emptyList() else Collections.singletonList(this)
		MutableList::class -> if(this == null) Collections.emptyList() else Collections.singletonList(this)
		else -> throw UnsupportedOperationException("Unsupported singleton collection type '${type.simpleName}'")
	} as R
}


/**将当前键值对数组转化为新的可变映射。*/
fun <K, V> Array<out Pair<K, V>>.toMutableMap(): MutableMap<K, V> = this.toMap(LinkedHashMap())

/**将当前键值对列表转化为新的可变映射。*/
fun <K, V> Iterable<Pair<K, V>>.toMutableMap(): MutableMap<K, V> = this.toMap(LinkedHashMap())

/**将当前键值对序列转化为新的可变映射。*/
fun <K, V> Sequence<Pair<K, V>>.toMutableMap(): MutableMap<K, V> = this.toMap(LinkedHashMap())


/**将当前数组转化为新的以键为值的映射。*/
inline fun <T> Array<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associateBy({ it.index.toString() }, { it.value })
}

/**将当前集合转化为新的以键为值的映射。*/
inline fun <T> Iterable<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associateBy({ it.index.toString() }, { it.value })
}

/**将当前序列转化为新的以键为值的映射。*/
inline fun <T> Sequence<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associateBy({ it.index.toString() }, { it.value })
}

/**将当前映射转化为新的以字符串为键的映射。*/
inline fun <K, V> Map<K, V>.toStringKeyMap(): Map<String, V> {
	return this.mapKeys { (k, _) -> k.toString() }
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
inline fun <T : CharSequence> Sequence<T>.filterNotEmpty(): Sequence<T> = this.filter { it.isNotEmpty() }


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
inline fun <T : CharSequence> Sequence<T>.filterNotBlank(): Sequence<T> = this.filter { it.isNotBlank() }


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
