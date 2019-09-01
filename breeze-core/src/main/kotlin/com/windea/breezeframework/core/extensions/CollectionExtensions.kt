@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.enums.*

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


/**判断当前数组中的任意元素是否被另一数组包含。*/
infix fun <T> Array<out T>.anyIn(other: Array<out T>): Boolean = this.any { it in other }

/**判断当前数组中的任意元素是否被另一集合包含。*/
infix fun <T> Array<out T>.anyIn(other: Iterable<T>): Boolean = this.any { it in other }

/**判断当前集合中的任意元素是否被另一数组包含。*/
infix fun <T> Iterable<T>.anyIn(other: Array<out T>): Boolean = this.any { it in other }

/**判断当前集合中的任意元素是否被另一集合包含。*/
infix fun <T> Iterable<T>.anyIn(other: Iterable<T>): Boolean = this.any { it in other }


/**判断当前数组是否以指定元素开始。*/
infix fun <T> Array<out T>.startsWith(element: T): Boolean = this.firstOrNull() == element

/**判断当前数组是否以任意指定元素开始。*/
infix fun <T> Array<out T>.startsWith(elements: Array<out T>): Boolean = this.firstOrNull() in elements

/**判断当前数组是否以指定元素结束。*/
infix fun <T> Array<out T>.endsWith(element: T): Boolean = this.firstOrNull() == element

/**判断当前数组是否以任意指定元素结束。*/
infix fun <T> Array<out T>.endsWith(elements: Array<out T>): Boolean = this.firstOrNull() in elements

/**判断当前集合是否以指定元素开始。*/
infix fun <T> Iterable<T>.startsWith(element: T): Boolean = this.firstOrNull() == element

/**判断当前集合是否以任意指定元素开始。*/
infix fun <T> Iterable<T>.startsWith(elements: Array<out T>): Boolean = this.firstOrNull() in elements

/**判断当前集合是否以指定元素结束。*/
infix fun <T> Iterable<T>.endsWith(element: T): Boolean = this.firstOrNull() == element

/**判断当前可迭代对象是否以任意指定元素结束。*/
infix fun <T> Iterable<T>.endsWith(elements: Array<out T>): Boolean = this.firstOrNull() in elements


/**如果当前数组不为空，则返回转换后的值。*/
@Suppress("UPPER_BOUND_CANNOT_BE_ARRAY")
inline fun <C : Array<*>> C.ifNotEmpty(transform: (C) -> C): C {
	return if(this.isEmpty()) this else transform(this)
}

/**如果当前集合不为空，则返回转换后的值。推荐仅用于长链式方法调用。*/
inline fun <C : Collection<*>> C.ifNotEmpty(transform: (C) -> C): C {
	return if(this.isEmpty()) this else transform(this)
}

/**如果当前映射不为空，则返回转换后的值。推荐仅用于长链式方法调用。*/
inline fun <C : Map<*, *>> C.ifNotEmpty(transform: (C) -> C): C {
	return if(this.isEmpty()) this else transform(this)
}


/**得到指定索引的元素，发生异常则得到默认值。*/
fun <T> Array<out T>.getOrDefault(index: Int, defaultValue: T): T = this.getOrElse(index) { defaultValue }

/**得到指定索引的元素，发生异常则得到默认值。*/
fun <T> List<T>.getOrDefault(index: Int, defaultValue: T): T = this.getOrElse(index) { defaultValue }


/**重复当前集合中的元素到指定次数。*/
fun <T> Iterable<T>.repeat(n: Int): List<T> {
	require(n >= 0) { "Count 'n' must be non-negative, but was $n." }
	
	return mutableListOf<T>().also { list -> repeat(n) { list += this } }
}


/**移除指定范围内的元素。*/
fun <T> MutableList<T>.removeAllAt(indices: IntRange) {
	for(index in indices.reversed()) this.removeAt(index)
}


/**将指定索引的元素插入到另一索引处。后者为移动前的索引，而非移动后的索引。*/
fun <T> MutableList<T>.move(fromIndices: Int, toIndex: Int): T {
	val element = this[fromIndices]
	this.add(toIndex, element)
	return this.removeAt(fromIndices)
}

/**将指定索引范围内的元素插入到以另一索引为起点处。后者为移动前的索引，而非移动后的索引。*/
fun <T> MutableList<T>.moveAll(fromIndices: IntRange, toIndex: Int) {
	val elements = this.slice(fromIndices)
	this.addAll(toIndex, elements)
	this.removeAllAt(fromIndices)
}


/**根据指定的转换操作，将映射中的键与值加入到指定的可添加对添加对象。默认转换操作是`$k=$v`。*/
fun <K, V, A : Appendable> Map<K, V>.joinTo(buffer: A, separator: CharSequence = ", ", prefix: CharSequence = "", postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...", transform: ((Map.Entry<K, V>) -> CharSequence)? = null): A {
	return this.entries.joinTo(buffer, separator, prefix, postfix, limit, truncated, transform)
}

/**根据指定的转换操作，将映射中的键与值加入到字符串。默认转换操作是`$k=$v`。*/
fun <K, V> Map<K, V>.joinToString(separator: CharSequence = ", ", prefix: CharSequence = "", postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...", transform: ((Map.Entry<K, V>) -> CharSequence)? = null): String {
	return this.joinTo(StringBuilder(), separator, prefix, postfix, limit, truncated, transform).toString()
}


/**绑定当前数组中的元素以及另一个数组中满足指定预测的首个元素。过滤总是不满足的情况。*/
inline fun <T, R : Any> Array<out T>.zipWithFirst(other: Array<out R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**绑定当前集合中的元素以及另一个集合中满足指定预测的首个元素。过滤总是不满足的情况。*/
inline fun <T, R : Any> Array<out T>.zipWithFirst(other: Iterable<R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**绑定当前集合中的元素以及另一个集合中满足指定预测的首个元素。过滤总是不满足的情况。*/
inline fun <T, R : Any> Iterable<T>.zipWithFirst(other: Array<out R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}

/**绑定当前集合中的元素以及另一个集合中满足指定预测的首个元素。过滤总是不满足的情况。*/
inline fun <T, R : Any> Iterable<T>.zipWithFirst(other: Iterable<R>, predicate: (T, R) -> Boolean): List<Pair<T, R>> {
	return this.mapNotNull { e1 -> other.firstOrNull { e2 -> predicate(e1, e2) }?.let { e1 to it } }
}


/**根据指定的标准引用得到当前数组中的元素。*/
fun <T> Array<out T>.deepGet(path: String): Any? =
	this.toIndexKeyMap().privateDeepGet(path.splitBy(ReferenceCase.StandardReference))

/**根据指定的标准引用得到当前列表中的元素。*/
fun <T> List<T>.deepGet(path: String): Any? =
	this.toIndexKeyMap().privateDeepGet(path.splitBy(ReferenceCase.StandardReference))

/**根据指定的标准引用得到当前映射中的元素。*/
fun <K, V> Map<K, V>.deepGet(path: String): Any? =
	this.toStringKeyMap().privateDeepGet(path.splitBy(ReferenceCase.StandardReference))

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
		else -> throw IllegalArgumentException("[ERROR] There is not a value related to this reference path.")
	}
	return fixedValue.privateDeepGet(currentSubPaths)
}


/**递归平滑映射当前数组，返回路径-值映射，默认使用标准引用[ReferenceCase.StandardReference]。可以指定层级[hierarchy]，默认为全部层级。*/
fun <T> Array<out T>.deepFlatten(hierarchy: Int = -1, pathCase: ReferenceCase = ReferenceCase.StandardReference): Map<String, Any?> =
	this.toIndexKeyMap().privateDeepFlatten(hierarchy, listOf(), pathCase)

/**递归平滑映射当前集合，返回路径-值映射，默认使用标准引用[ReferenceCase.StandardReference]。可以指定层级[hierarchy]，默认为全部层级。*/
fun <T> Iterable<T>.deepFlatten(hierarchy: Int = -1, pathCase: ReferenceCase = ReferenceCase.StandardReference): Map<String, Any?> =
	this.toIndexKeyMap().privateDeepFlatten(hierarchy, listOf(), pathCase)

/**递归平滑映射当前映射，返回路径-值映射，默认使用标准引用[ReferenceCase.StandardReference]。可以指定层级[hierarchy]，默认为全部层级。*/
fun <K, V> Map<K, V>.deepFlatten(hierarchy: Int = -1, pathCase: ReferenceCase = ReferenceCase.StandardReference): Map<String, Any?> =
	this.toStringKeyMap().privateDeepFlatten(hierarchy, listOf(), pathCase)

private fun Map<String, Any?>.privateDeepFlatten(hierarchy: Int = -1, preSubPaths: List<String>, pathCase: ReferenceCase = ReferenceCase.StandardReference): Map<String, Any?> {
	return this.flatMap { (key, value) ->
		val currentHierarchy = if(hierarchy <= 0) hierarchy else hierarchy - 1
		//每次递归需要创建新的子路径列表
		val currentPreSubPaths = preSubPaths + key
		//如果不是集合类型，则拼接成完整路径，与值一同返回
		val fixedValue = when {
			currentHierarchy == 0 -> return@flatMap listOf(currentPreSubPaths.joinBy(pathCase) to value)
			value is Array<*> -> value.toIndexKeyMap()
			value is Iterable<*> -> value.toIndexKeyMap()
			value is Map<*, *> -> value.toStringKeyMap()
			else -> return@flatMap listOf(currentPreSubPaths.joinBy(pathCase) to value)
		}
		return@flatMap fixedValue.privateDeepFlatten(currentHierarchy, currentPreSubPaths, pathCase).toList()
	}.toMap()
}


/**根据指定的Json路径[ReferenceCase.JsonSchemaReference]递归查询当前数组，返回匹配的路径-值映射，默认使用标准引用[ReferenceCase.StandardReference]*/
fun <T> Array<out T>.deepQuery(path: String, pathCase: ReferenceCase = ReferenceCase.StandardReference): Map<String, Any?> =
	this.toIndexKeyMap().privateDeepQuery(path.splitBy(ReferenceCase.JsonSchemaReference), listOf(), pathCase)

/**根据指定的Json路径[ReferenceCase.JsonSchemaReference]递归查询当前集合，返回匹配的路径-值映射，默认使用标准引用[ReferenceCase.StandardReference]*/
fun <T> Iterable<T>.deepQuery(path: String, pathCase: ReferenceCase = ReferenceCase.StandardReference): Map<String, Any?> =
	this.toIndexKeyMap().privateDeepQuery(path.splitBy(ReferenceCase.JsonSchemaReference), listOf(), pathCase)

/**根据指定的Json路径[ReferenceCase.JsonSchemaReference]递归查询当前映射，返回匹配的路径-值映射，默认使用标准引用[ReferenceCase.StandardReference]*/
fun <K, V> Map<K, V>.deepQuery(path: String, pathCase: ReferenceCase = ReferenceCase.StandardReference): Map<String, Any?> =
	this.toStringKeyMap().privateDeepQuery(path.splitBy(ReferenceCase.JsonSchemaReference), listOf(), pathCase)

private fun Map<String, Any?>.privateDeepQuery(subPaths: List<String>, preSubPaths: List<String>, pathCase: ReferenceCase = ReferenceCase.StandardReference): Map<String, Any?> {
	return this.flatMap { (key, value) ->
		val currentSubPath = subPaths.first()
		val currentSubPaths = subPaths.drop(1)
		//每次递归需要创建新的子路径列表
		val currentPreSubPaths = preSubPaths + key
		//如果不是集合类型，则拼接成完整路径，与值一同返回
		val fixedValue = when {
			currentSubPaths.isEmpty() -> return@flatMap listOf(currentPreSubPaths.joinBy(pathCase) to value).filter {
				when {
					//如果子路径表示一个列表或映射，例如："[]" "-" "{}"
					currentSubPath in arrayOf("[]", "-", "{}") -> true
					//如果子路径表示一个列表占位符，例如："[WeaponList]"
					currentSubPath matches "\\[.+]".toRegex() -> true
					//如果子路径表示一个范围，例如："1..10" "a..b"
					currentSubPath matches "^\\d+\\.\\.\\d+$".toRegex() -> key in currentSubPath.split("..").let { it[0]..it[1] }
					//如果子路径表示一个映射占位符，例如："{Category}"
					currentSubPath matches "\\{.+}".toRegex() -> true
					//如果子路径表示一个正则表达式，例如："regex.*Name"
					currentSubPath startsWith "regex:" -> key matches "^${currentSubPath.removePrefix("regex:")}$".toRegex()
					//如果子路径表示一个索引或键，例如："1" "Name"`
					else -> key == currentSubPath
				}
			}
			value is Array<*> -> value.toIndexKeyMap()
			value is Iterable<*> -> value.toIndexKeyMap()
			value is Map<*, *> -> value.toStringKeyMap()
			else -> return@flatMap listOf<Pair<String, Any?>>()
		}
		return@flatMap fixedValue.privateDeepQuery(currentSubPaths, currentPreSubPaths, pathCase).toList()
	}.toMap()
}


/**将当前数组转化成以键为值的映射。*/
fun <T> Array<out T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associate { (i, e) -> i.toString() to e }
}

/**将当前集合转化成以键为值的映射。*/
fun <T> Iterable<T>.toIndexKeyMap(): Map<String, T> {
	return this.withIndex().associate { (i, e) -> i.toString() to e }
}

/**将当前映射转换成以字符串为键的映射。*/
fun <K, V> Map<K, V>.toStringKeyMap(): Map<String, V> {
	return this.mapKeys { (k, _) -> k.toString() }
}


/**得到指定索引的值，如果出错，则返回空字符串。*/
fun Array<String>.getOrEmpty(index: Int): String = this.getOrElse(index) { "" }

/**得到指定索引的值，如果出错，则返回空字符串。*/
fun List<String>.getOrEmpty(index: Int): String = this.getOrElse(index) { "" }


/**去除第一行空白行。*/
fun <C : CharSequence> Array<out C>.dropBlank(): List<C> = this.dropWhile { it.isBlank() }

/**去除最后一行空白行。*/
fun <C : CharSequence> Array<out C>.dropLastBlank(): List<C> = this.dropLastWhile { it.isBlank() }

/**去除第一行空白行。*/
fun <C : CharSequence> Iterable<C>.dropBlank(): List<C> = this.dropWhile { it.isBlank() }

/**去除最后一行空白行。*/
fun <C : CharSequence> List<C>.dropLastBlank(): List<C> = this.dropLastWhile { it.isBlank() }


/**过滤空字符串。*/
fun <C : CharSequence> Array<out C>.filterNotEmpty(): List<C> = this.filter { it.isNotEmpty() }

/**过滤空白字符串。*/
fun <C : CharSequence> Array<out C>.filterNotBlank(): List<C> = this.filter { it.isNotEmpty() }

/**过滤空字符串。*/
fun <C : CharSequence> Iterable<C>.filterNotEmpty(): List<C> = this.filter { it.isNotEmpty() }

/**过滤空白字符串。*/
fun <C : CharSequence> Iterable<C>.filterNotBlank(): List<C> = this.filter { it.isNotEmpty() }


/**映射当前带索引值集合的索引，返回带有新的索引的带索引值集合。*/
inline fun <T> Iterable<IndexedValue<T>>.mapIndices(transform: (Int) -> Int): Iterable<IndexedValue<T>> {
	return this.map { (index, value) -> IndexedValue(transform(index), value) }
}

/**映射当前带索引值集合的值，返回带有新的值的带索引值集合。*/
inline fun <T> Iterable<IndexedValue<T>>.mapValues(transform: (T) -> T): Iterable<IndexedValue<T>> {
	return this.map { (index, value) -> IndexedValue(index, transform(value)) }
}


/**@see com.windea.breezeframework.core.extensions.repeat*/
operator fun <T> Iterable<T>.times(n: Int): List<T> = this.repeat(n)

/**@see kotlin.collections.chunked*/
operator fun <T> Iterable<T>.div(n: Int): List<List<T>> = this.chunked(n)

/**@see kotlin.collections.slice*/
operator fun <T> Array<out T>.get(indexRange: IntRange): List<T> = this.slice(indexRange)

/**@see kotlin.collections.slice*/
operator fun <T> List<T>.get(range: IntRange): List<T> = this.slice(range)
