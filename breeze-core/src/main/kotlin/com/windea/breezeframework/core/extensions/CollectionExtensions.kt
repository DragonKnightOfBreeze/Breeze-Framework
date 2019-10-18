@file:Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.enums.core.*
import com.windea.breezeframework.core.enums.core.ReferenceCase.*
import java.util.concurrent.*
import kotlin.random.*

//REGION Portal extensions

/**构建线程安全的并发列表。*/
inline fun <T> concurrentListOf(): CopyOnWriteArrayList<T> = CopyOnWriteArrayList()

/**构建线程安全的并发列表。*/
inline fun <T> concurrentListOf(vararg elements: T): CopyOnWriteArrayList<T> = CopyOnWriteArrayList(elements)

/**构建线程安全的并发集。*/
inline fun <T> concurrentSetOf(): CopyOnWriteArraySet<T> = CopyOnWriteArraySet()

/**构建线程安全的并发集。*/
inline fun <T> concurrentSetOf(vararg elements: T): CopyOnWriteArraySet<T> = CopyOnWriteArraySet(elements.toSet())

/**构建线程安全的并发映射。*/
inline fun <K, V> concurrentMapOf(): ConcurrentHashMap<K, V> = ConcurrentHashMap()

/**构建线程安全的并发映射。*/
inline fun <K, V> concurrentMapOf(vararg pairs: Pair<K, V>): ConcurrentHashMap<K, V> = ConcurrentHashMap(pairs.toMap())

//REGION Operator overrides

/**@see kotlin.collections.slice*/
inline operator fun <T> Array<out T>.get(indexRange: IntRange): List<T> = this.slice(indexRange)

/**@see com.windea.breezeframework.core.extensions.repeat*/
inline operator fun <T> Array<out T>.times(n: Int): List<T> = this.toList().repeat(n)

/**@see kotlin.collections.chunked*/
inline operator fun <T> Array<out T>.div(n: Int): List<List<T>> = this.toList().chunked(n)

/**@see kotlin.collections.slice*/
inline operator fun <T> List<T>.get(range: IntRange): List<T> = this.slice(range)

/**@see com.windea.breezeframework.core.extensions.repeat*/
inline operator fun <T> Iterable<T>.times(n: Int): List<T> = this.repeat(n)

/**@see kotlin.collections.chunked*/
inline operator fun <T> Iterable<T>.div(n: Int): List<List<T>> = this.chunked(n)

//REGION Common functions

/**判断两个列表的结构是否相等。即，判断长度、元素、元素顺序是否相等。*/
infix fun <T> List<T>.contentEquals(other: List<T>): Boolean {
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
inline infix fun <T> Array<out T>.allIn(other: Array<out T>): Boolean = this.all { it in other }

/**判断当前数组中的所有元素是否被另一集合包含。*/
inline infix fun <T> Array<out T>.allIn(other: Iterable<T>): Boolean = this.all { it in other }

/**判断当前集合中的所有元素是否被另一数组包含。*/
inline infix fun <T> Iterable<T>.allIn(other: Array<out T>): Boolean = this.all { it in other }

/**判断当前集合中的所有元素是否被另一集合包含。*/
inline infix fun <T> Iterable<T>.allIn(other: Iterable<T>): Boolean = this.all { it in other }

/**判断当前序列中的所有元素是否被另一序列包含。*/
inline infix fun <T> Sequence<T>.allIn(other: Sequence<T>): Boolean = this.all { it in other }

/**判断当前数组中的任意元素是否被另一数组包含。*/
inline infix fun <T> Array<out T>.anyIn(other: Array<out T>): Boolean = this.any { it in other }

/**判断当前数组中的任意元素是否被另一集合包含。*/
inline infix fun <T> Array<out T>.anyIn(other: Iterable<T>): Boolean = this.any { it in other }

/**判断当前集合中的任意元素是否被另一数组包含。*/
inline infix fun <T> Iterable<T>.anyIn(other: Array<out T>): Boolean = this.any { it in other }

/**判断当前集合中的任意元素是否被另一集合包含。*/
inline infix fun <T> Iterable<T>.anyIn(other: Iterable<T>): Boolean = this.any { it in other }

/**判断当前序列中的任意元素是否被另一序列包含。*/
inline infix fun <T> Sequence<T>.anyIn(other: Sequence<T>): Boolean = this.any { it in other }


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
inline infix fun <T> Array<out T>.endsWith(element: T): Boolean = this.firstOrNull() == element

/**判断当前数组是否以任意指定元素结束。*/
inline infix fun <T> Array<out T>.endsWith(elements: Array<out T>): Boolean = this.firstOrNull() in elements

/**判断当前集合是否以指定元素结束。*/
inline infix fun <T> Iterable<T>.endsWith(element: T): Boolean = this.firstOrNull() == element

/**判断当前集合是否以任意指定元素结束。*/
inline infix fun <T> Iterable<T>.endsWith(elements: Array<out T>): Boolean = this.firstOrNull() in elements


/**判断当前序列是否为空。*/
inline fun <T> Sequence<T>.isEmpty() = !this.isNotEmpty()

/**判断当前序列是否不为空。*/
inline fun <T> Sequence<T>.isNotEmpty() = this.iterator().hasNext()


/**如果当前数组不为空，则返回转换后的值。*/
@Suppress("UPPER_BOUND_CANNOT_BE_ARRAY")
inline fun <T : Array<*>> T.ifNotEmpty(transform: (T) -> T): T {
	return if(this.isEmpty()) this else transform(this)
}

/**如果当前集合不为空，则返回转换后的值。*/
inline fun <T : Collection<*>> T.ifNotEmpty(transform: (T) -> T): T {
	return if(this.isEmpty()) this else transform(this)
}

/**如果当前映射不为空，则返回转换后的值。*/
inline fun <T : Map<*, *>> T.ifNotEmpty(transform: (T) -> T): T {
	return if(this.isEmpty()) this else transform(this)
}


/**得到指定索引的元素，发生异常则得到默认值。*/
inline fun <T> Array<out T>.getOrDefault(index: Int, defaultValue: T): T {
	return this.getOrElse(index) { defaultValue }
}

/**得到指定索引的元素，发生异常则得到默认值。*/
inline fun <T> List<T>.getOrDefault(index: Int, defaultValue: T): T {
	return this.getOrElse(index) { defaultValue }
}


/**得到随机元素。如果数组为空，则返回null。*/
inline fun <T> Array<out T>.randomOrNull(): T? = this.randomOrNull(Random)

/**得到随机元素。如果数组为空，则返回null。*/
inline fun <T> Array<out T>.randomOrNull(random: Random): T? {
	if(this.isEmpty()) return null
	return this[random.nextInt(size)]
}

/**得到随机元素。如果集合为空，则返回null。*/
inline fun <T> Collection<T>.randomOrNull(): T? = this.randomOrNull(Random)

/**得到随机元素。如果集合为空，则返回null。*/
inline fun <T> Collection<T>.randomOrNull(random: Random): T? {
	if(this.isEmpty()) return null
	return this.elementAt(random.nextInt(size))
}


/**重复当前集合中的元素到指定次数。*/
fun <T> Iterable<T>.repeat(n: Int): List<T> {
	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }
	
	return mutableListOf<T>().also { list -> repeat(n) { list += this } }
}

/**分别重复当前集合中的元素到指定次数，并映射为子列表。*/
fun <T> Iterable<T>.repeatChunked(n: Int): List<List<T>> {
	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }
	
	return mutableListOf<List<T>>().also { list -> for(e in this) list += (mutableListOf<T>().also { l -> repeat(n) { l += e } }) }
}

/**分别平滑重复当前集合中的元素到指定次数。*/
fun <T> Iterable<T>.flatRepeat(n: Int): List<T> {
	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }
	
	return mutableListOf<T>().also { list -> for(e in this) repeat(n) { list += e } }
}


/**填充指定索引范围内的元素到当前列表。如果索引超出当前列表的长度，或为负数，则忽略。*/
fun <T> MutableList<T>.fillRange(value: T, indices: IntRange) {
	val fromIndex = indices.first.coerceIn(0, this.size)
	val toIndex = indices.last.coerceIn(fromIndex, this.size)
	for(index in fromIndex..toIndex) this[index] = value
}

/**填充指定元素到当前列表，直到指定长度。如果指定长度比当前长度小，则忽略。返回填充后的列表。*/
fun <T> List<T>.fillEnd(size: Int, value: T): List<T> {
	if(this.size <= size) return this
	return this.toMutableList().also { list -> repeat(size - this.size) { list += value } }
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


/**将当前数组作为值的部分，与另一个数组组成映射。*/
fun <K, V> Array<out V>.withKeys(other: Array<out K>): Map<K, V> = (other zip this).toMap()

/**将当前数组作为值的部分，与另一个列表组成映射。*/
fun <K, V> Array<out V>.withKeys(other: List<K>): Map<K, V> = (other zip this).toMap()

/**将当前列表作为值的部分，与另一个数组组成映射。*/
fun <K, V> List<V>.withKeys(other: Array<out K>): Map<K, V> = (other zip this).toMap()

/**将当前列表作为值的部分，与另一个列表组成映射。*/
fun <K, V> List<V>.withKeys(other: List<K>): Map<K, V> = (other zip this).toMap()


/**根据指定的转换操作，将映射中的键与值加入到指定的可添加对添加对象。默认的转换操作是`$k=$v`。*/
fun <K, V, A : Appendable> Map<K, V>.joinTo(buffer: A, separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	transform: ((Map.Entry<K, V>) -> CharSequence)? = null): A {
	return this.entries.joinTo(buffer, separator, prefix, postfix, limit, truncated, transform)
}

/**根据指定的转换操作，将映射中的键与值加入到字符串。默认的转换操作是`$k=$v`。*/
fun <K, V> Map<K, V>.joinToString(separator: CharSequence = ", ", prefix: CharSequence = "", postfix: CharSequence = "",
	limit: Int = -1, truncated: CharSequence = "...", transform: ((Map.Entry<K, V>) -> CharSequence)? = null): String {
	return this.joinTo(StringBuilder(), separator, prefix, postfix, limit, truncated, transform).toString()
}

/**根据指定的转化操作，将数组中的元素加入到字符串。当数组为空时，直接返回空字符串且忽略前后缀。*/
fun <T> Array<out T>.joinToStringOrEmpty(separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	transform: ((T) -> CharSequence)? = null): String {
	return if(this.isEmpty()) "" else this.joinToString(separator, prefix, postfix, limit, truncated, transform)
}

/**根据指定的转化操作，将集合中的元素加入到字符串。当集合为空时，直接返回空字符串且忽略前后缀。*/
fun <T> Iterable<T>.joinToStringOrEmpty(separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	transform: ((T) -> CharSequence)? = null): String {
	return if(this.none()) "" else this.joinToString(separator, prefix, postfix, limit, truncated, transform)
}

/**根据指定的转化操作，将映射中的元素加入到字符串。当映射为空时，直接返回空字符串且忽略前后缀。*/
fun <K, V> Map<K, V>.joinToStringOrEmpty(separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	transform: ((Map.Entry<K, V>) -> CharSequence)? = null): String {
	return if(this.isEmpty()) "" else this.joinToString(separator, prefix, postfix, limit, truncated, transform)
}


/**按照类型以及附加条件过滤数组。*/
inline fun <reified R> Array<*>.filterIsInstance(predicate: (R) -> Boolean): List<R> {
	return this.filterIsInstanceTo<R, MutableList<R>>(ArrayList(), predicate)
}

/**按照类型以及附加条件过滤数组，然后置入指定的集合。*/
inline fun <reified R, C : MutableCollection<in R>> Array<*>.filterIsInstanceTo(destination: C,
	predicate: (R) -> Boolean): C {
	for(element in this) if(element is R && predicate(element)) destination.add(element)
	return destination
}

/**按照类型以及附加条件过滤列表。*/
inline fun <reified R> List<*>.filterIsInstance(predicate: (R) -> Boolean): List<R> {
	return this.filterIsInstanceTo<R, MutableList<R>>(ArrayList(), predicate)
}

/**按照类型以及附加条件过滤列表，然后置入指定的集合。*/
inline fun <reified R, C : MutableCollection<in R>> List<*>.filterIsInstanceTo(destination: C,
	predicate: (R) -> Boolean): C {
	for(element in this) if(element is R && predicate(element)) destination.add(element)
	return destination
}

/**按照类型以及附加条件过滤集。*/
inline fun <reified R> Set<*>.filterIsInstance(predicate: (R) -> Boolean): List<R> {
	return this.filterIsInstanceTo<R, MutableList<R>>(ArrayList(), predicate)
}

/**按照类型以及附加条件过滤集，然后置入指定的集合。*/
inline fun <reified R, C : MutableCollection<in R>> Set<*>.filterIsInstanceTo(destination: C,
	predicate: (R) -> Boolean): C {
	for(element in this) if(element is R && predicate(element)) destination.add(element)
	return destination
}

/**按照类型以及附加条件过滤序列。*/
inline fun <reified R> Sequence<*>.filterIsInstance(noinline predicate: (R) -> Boolean): Sequence<R> {
	return this.filterIsInstance<R>().filter(predicate)
}

/**按照类型以及附加条件过滤序列，然后置入指定的集合。*/
inline fun <reified R, C : MutableCollection<in R>> Sequence<*>.filterIsInstanceTo(destination: C,
	predicate: (R) -> Boolean): C {
	for(element in this) if(element is R && predicate(element)) destination.add(element)
	return destination
}


/**根据指定的条件，内连接当前数组和另一个列表。即，绑定满足该条件的各自元素，忽略不满足的情况。*/
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

/**绑定当前序列中的元素以及另一个序列中满足指定预测的首个元素。过滤总是不满足的情况。*/
inline fun <T, R : Any> Sequence<T>.innerJoin(other: Sequence<R>,
	crossinline predicate: (T, R) -> Boolean): Sequence<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

//REGION Deep operation extensions

/**根据指定的[StandardReference]得到当前数组中的元素。*/
fun <T> Array<out T>.deepGet(path: String): Any? =
	this.toIndexKeyMap().privateDeepGet(path.splitBy(StandardReference))

/**根据指定的[StandardReference]得到当前列表中的元素。*/
fun <T> List<T>.deepGet(path: String): Any? =
	this.toIndexKeyMap().privateDeepGet(path.splitBy(StandardReference))

/**根据指定的[StandardReference]得到当前映射中的元素。*/
fun <K, V> Map<K, V>.deepGet(path: String): Any? =
	this.toStringKeyMap().privateDeepGet(path.splitBy(StandardReference))

private tailrec fun Map<String, Any?>.privateDeepGet(subPaths: List<String>): Any? {
	val currentSubPath = subPaths.first()
	val currentSubPaths = subPaths.drop(1)
	//如果已递归到最后一个子路径，则从映射中返回对应元素
	val value = this[currentSubPath]
	//否则检查递归遍历的值的类型，继续递归调用这个方法
	val fixedValue = when {
		currentSubPaths.isEmpty() -> return value
		value is Array<*> -> value.toIndexKeyMap()
		value is Iterable<*> -> value.toIndexKeyMap()
		value is Map<*, *> -> value.toStringKeyMap()
		value is Sequence<*> -> value.toIndexKeyMap()
		else -> throw IllegalArgumentException("There is not a value related to this reference path.")
	}
	return fixedValue.privateDeepGet(currentSubPaths)
}


/**递归平滑映射当前数组，返回引用-值映射。默认使用[StandardReference]。可以指定层级，默认为全部层级。*/
fun <T> Array<out T>.deepFlatten(depth: Int = -1, pathFormatCase: FormatCase = StandardReference): Map<String, Any?> =
	this.toIndexKeyMap().privateDeepFlatten(depth, listOf(), pathFormatCase)

/**递归平滑映射当前集合，返回引用-值映射。默认使用[StandardReference]。可以指定层级，默认为全部层级。*/
fun <T> Iterable<T>.deepFlatten(depth: Int = -1, pathFormatCase: FormatCase = StandardReference): Map<String, Any?> =
	this.toIndexKeyMap().privateDeepFlatten(depth, listOf(), pathFormatCase)

/**递归平滑映射当前映射，返回引用-值映射。默认使用[StandardReference]。可以指定层级，默认为全部层级。*/
fun <K, V> Map<K, V>.deepFlatten(depth: Int = -1, pathFormatCase: FormatCase = StandardReference): Map<String, Any?> =
	this.toStringKeyMap().privateDeepFlatten(depth, listOf(), pathFormatCase)

/**递归平滑映射当前序列，返回引用-值映射。默认使用[StandardReference]。可以指定层级，默认为全部层级。*/
fun <T> Sequence<T>.deepFlatten(depth: Int = -1, pathFormatCase: FormatCase = StandardReference): Map<String, Any?> =
	this.toIndexKeyMap().privateDeepFlatten(depth, listOf(), pathFormatCase)

private fun Map<String, Any?>.privateDeepFlatten(depth: Int = -1, preSubPaths: List<String>,
	pathFormatCase: FormatCase = StandardReference): Map<String, Any?> {
	return this.flatMap { (key, value) ->
		val currentHierarchy = if(depth <= 0) depth else depth - 1
		//每次递归需要创建新的子路径列表
		val currentPreSubPaths = preSubPaths + key
		//如果不是集合类型，则拼接成完整路径，与值一同返回
		val fixedValue = when {
			currentHierarchy == 0 -> return@flatMap listOf(currentPreSubPaths.joinBy(pathFormatCase) to value)
			value is Array<*> -> value.toIndexKeyMap()
			value is Iterable<*> -> value.toIndexKeyMap()
			value is Map<*, *> -> value.toStringKeyMap()
			value is Sequence<*> -> value.toIndexKeyMap()
			else -> return@flatMap listOf(currentPreSubPaths.joinBy(pathFormatCase) to value)
		}
		return@flatMap fixedValue.privateDeepFlatten(currentHierarchy, currentPreSubPaths, pathFormatCase).toList()
	}.toMap()
}


/**根据指定的[JsonSchemaReference]递归查询当前数组，返回匹配的引用-值映射，默认使用[StandardReference]。*/
fun <T> Array<out T>.deepQuery(path: String, referenceCase: ReferenceCase = StandardReference): Map<String, Any?> =
	this.toIndexKeyMap().privateDeepQuery(path.splitBy(JsonSchemaReference), listOf(), referenceCase)

/**根据指定的[JsonSchemaReference]递归查询当前集合，返回匹配的引用-值映射，默认使用[StandardReference]。*/
fun <T> Iterable<T>.deepQuery(path: String, referenceCase: ReferenceCase = StandardReference): Map<String, Any?> =
	this.toIndexKeyMap().privateDeepQuery(path.splitBy(JsonSchemaReference), listOf(), referenceCase)

/**根据指定的[JsonSchemaReference]递归查询当前映射，返回匹配的引用-值映射，默认使用[StandardReference]。*/
fun <K, V> Map<K, V>.deepQuery(path: String, referenceCase: ReferenceCase = StandardReference): Map<String, Any?> =
	this.toStringKeyMap().privateDeepQuery(path.splitBy(JsonSchemaReference), listOf(), referenceCase)

/**根据指定的[JsonSchemaReference]递归查询当前序列，返回匹配的引用-值映射，默认使用[StandardReference]。*/
fun <T> Sequence<T>.deepQuery(path: String, referenceCase: ReferenceCase = StandardReference): Map<String, Any?> =
	this.toIndexKeyMap().privateDeepQuery(path.splitBy(JsonSchemaReference), listOf(), referenceCase)

private fun Map<String, Any?>.privateDeepQuery(subPaths: List<String>, preSubPaths: List<String>,
	referenceCase: ReferenceCase = StandardReference): Map<String, Any?> {
	return this.flatMap { (key, value) ->
		val currentSubPath = subPaths.first()
		val currentSubPaths = subPaths.drop(1)
		//每次递归需要创建新的子路径列表
		val currentPreSubPaths = preSubPaths + key
		//如果不是集合类型，则拼接成完整路径，与值一同返回
		val fixedValue = when {
			currentSubPaths.isEmpty() -> return@flatMap listOf(currentPreSubPaths.joinBy(referenceCase) to value).filter {
				//TODO 提取为枚举
				when {
					//如果子路径表示一个列表或映射，例如："[]" "-" "{}"
					currentSubPath in arrayOf("[]", "-", "{}") -> true
					//如果子路径表示一个列表占位符，例如："[WeaponList]"
					currentSubPath matches """\[.+]""".toRegex() -> true
					//如果子路径表示一个范围，例如："1..10" "a..b"
					currentSubPath matches """\d+\.\.\d+""".toRegex() -> key in currentSubPath.split("..").let { it[0]..it[1] }
					//如果子路径表示一个映射占位符，例如："{Category}"
					currentSubPath matches """\{.+}""".toRegex() -> true
					//如果子路径表示一个正则表达式，例如："regex.*Name"
					currentSubPath startsWith "regex:" -> key matches currentSubPath.substring(6).toRegex()
					//如果子路径表示一个索引或键，例如："1" "Name"`
					else -> key == currentSubPath
				}
			}
			value is Array<*> -> value.toIndexKeyMap()
			value is Iterable<*> -> value.toIndexKeyMap()
			value is Map<*, *> -> value.toStringKeyMap()
			value is Sequence<*> -> value.toIndexKeyMap()
			else -> return@flatMap listOf<Pair<String, Any?>>()
		}
		return@flatMap fixedValue.privateDeepQuery(currentSubPaths, currentPreSubPaths, referenceCase).toList()
	}.toMap()
}

//REGION Convert extensions

/**将当前列表转化为并发列表。*/
fun <T> List<T>.asConcurrent(): CopyOnWriteArrayList<T> = CopyOnWriteArrayList(this)

/**将当前集转化为并发集。*/
fun <T> Set<T>.asConcurrent(): CopyOnWriteArraySet<T> = CopyOnWriteArraySet(this)

/**将当前映射转化为并发映射。*/
fun <K, V> Map<K, V>.asConcurrent(): ConcurrentHashMap<K, V> = ConcurrentHashMap(this)


/**将当前键值对数组转化为可变映射。*/
fun <K, V> Array<Pair<K, V>>.toMutableMap(): MutableMap<K, V> = this.toMap().toMutableMap()

/**将当前键值对列表转化为可变映射。*/
fun <K, V> List<Pair<K, V>>.toMutableMap(): MutableMap<K, V> = this.toMap().toMutableMap()

/**将当前键值对序列转化为可变映射。*/
fun <K, V> Sequence<Pair<K, V>>.toMutableMap(): MutableMap<K, V> = this.toMap().toMutableMap()


/**将当前数组转化成以键为值的映射。*/
inline fun <T> Array<out T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associate { (i, e) -> i.toString() to e }
}

/**将当前集合转化成以键为值的映射。*/
inline fun <T> Iterable<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associate { (i, e) -> i.toString() to e }
}

/**将当前映射转换成以字符串为键的映射。*/
inline fun <K, V> Map<K, V>.toStringKeyMap(): Map<String, V> {
	return this as? Map<String, V> ?: this.mapKeys { (k, _) -> k.toString() }
}

/**将当前映射转换成以字符串为值的映射。*/
inline fun <K, V> Map<K, V>.toStringValueMap(): Map<K, String> {
	return this as? Map<K, String> ?: this.mapValues { (_, v) -> v.toString() }
}

/**将当前映射转换成以字符串为键和值的映射。*/
inline fun <K, V> Map<K, V>.toStringKeyValueMap(): Map<String, String> {
	return this as? Map<String, String> ?: this.map { (k, v) -> k.toString() to v.toString() }.toMap()
}

/**将当前序列转化成以键为值的映射。*/
fun <T> Sequence<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associate { (i, e) -> i.toString() to e }
}

//REGION Specific operations

/**得到指定索引的值，如果出错，则返回空字符串。*/
inline fun Array<String>.getOrEmpty(index: Int): String = this.getOrElse(index) { "" }

/**得到指定索引的值，如果出错，则返回空字符串。*/
inline fun List<String>.getOrEmpty(index: Int): String = this.getOrElse(index) { "" }


/**去除起始的空白行。*/
inline fun <T : CharSequence> Array<out T>.dropBlank(): List<T> = this.dropWhile { it.isBlank() }

/**去除尾随的空白行。*/
inline fun <T : CharSequence> Array<out T>.dropLastBlank(): List<T> = this.dropLastWhile { it.isBlank() }

/**去除起始的空白行。*/
inline fun <T : CharSequence> Iterable<T>.dropBlank(): List<T> = this.dropWhile { it.isBlank() }

/**去除尾随的空白行。*/
inline fun <T : CharSequence> List<T>.dropLastBlank(): List<T> = this.dropLastWhile { it.isBlank() }

/**去除起始的空白行。*/
inline fun <T : CharSequence> Sequence<T>.dropBlank(): Sequence<T> = this.dropWhile { it.isBlank() }


/**过滤空字符串。*/
inline fun <T : CharSequence> Array<out T>.filterNotEmpty(): List<T> = this.filter { it.isNotEmpty() }

/**过滤空白字符串。*/
inline fun <T : CharSequence> Array<out T>.filterNotBlank(): List<T> = this.filter { it.isNotBlank() }

/**过滤空字符串。*/
inline fun <T : CharSequence> Iterable<T>.filterNotEmpty(): List<T> = this.filter { it.isNotEmpty() }

/**过滤空白字符串。*/
inline fun <T : CharSequence> Iterable<T>.filterNotBlank(): List<T> = this.filter { it.isNotBlank() }

/**过滤空字符串。*/
inline fun <T : CharSequence> Sequence<T>.filterNotEmpty(): Sequence<T> = this.filter { it.isNotEmpty() }

/**过滤空白字符串。*/
inline fun <T : CharSequence> Sequence<T>.filterNotBlank(): Sequence<T> = this.filter { it.isNotBlank() }
