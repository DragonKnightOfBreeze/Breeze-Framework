@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.linq

//TODO 语句的编写顺序与执行顺序的问题

/**
 * Linq（语言集成查询）。
 *
 * Linq仅存储查询语句，而不存储查询源。
 * Linq的语法类似Sql，但是以`from`语句开始。
 */
interface Linq<S, T> {
	infix fun where(predicate: (T) -> Boolean): Linq<S, T>

	fun distinct(): Linq<S, T>

	infix fun <K> distinctBy(selector: (T) -> K): Linq<S, T>

	infix fun <K : Comparable<K>> orderBy(selector: (T) -> K?): Linq<S, T>

	infix fun orderBy(comparator: Comparator<T>): Linq<S, T>

	infix fun <K : Comparable<K>> orderByDesc(selector: (T) -> K?): Linq<S, T>

	infix fun orderByDesc(comparator: Comparator<T>): Linq<S, T>

	infix fun <K> groupBy(keySelector: (T) -> K): Linq<S, Pair<K, List<T>>>

	fun limit(start: Int, end: Int): Linq<S, T>

	infix fun limit(range: IntRange): Linq<S, T> = limit(range.first, range.last)

	infix fun limit(end: Int): Linq<S, T> = limit(0, end)

	fun limitDesc(start: Int, end: Int): Linq<S, T>

	infix fun limitDesc(range: IntRange): Linq<S, T> = limitDesc(range.first, range.last)

	infix fun limitDesc(end: Int): Linq<S, T> = limitDesc(0, end)

	infix fun <R> select(transform: (T) -> R): Linq<S, R>

	infix fun <R> selectMany(transform: (T) -> Iterable<R>): Linq<S, R>

	infix fun <R> union(other: Linq<S, T>): Linq<S, T>

	infix fun <R> unionAll(other: Linq<S, T>): Linq<S, T>

	operator fun invoke(source: Collection<S>): List<T>
}


/**Linq的实现类型。*/
enum class LinqImplementationType {
	Default, ByStream, ByParallelStream
}

/**创建Linq语句。可选Linq的实现类型，默认为委托给Kotlin集合框架实现。*/
@JvmOverloads
fun <T> from(type: LinqImplementationType = LinqImplementationType.Default): Linq<T, T> = when(type) {
	LinqImplementationType.Default -> LinqImpl.init()
	LinqImplementationType.ByStream -> StreamLinq.init()
	LinqImplementationType.ByParallelStream -> StreamLinq.init(true)
}


/**对当前字符串进行语言集成查询。*/
infix fun <R> String.linq(linqStatement: Linq<Char, R>): List<R> = linqStatement(this.toList())

/**对当前数组进行语言集成查询。*/
infix fun <T, R> Array<out T>.linq(linqStatement: Linq<T, R>): List<R> = linqStatement(this.toList())

/**对当前集合进行语言集成查询。*/
infix fun <T, R> Collection<T>.linq(linqStatement: Linq<T, R>): List<R> = linqStatement(this)

/**对当前映射进行语言集成查询。*/
infix fun <K, V, R> Map<K, V>.linq(linqStatement: Linq<Pair<K, V>, R>): List<R> = linqStatement(this.toList())
