@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.linq

//TODO 语句的编写顺序与执行顺序的问题
//TODO 允许聚合函数
//TODO 反向/倒序的扩展

/**
 * Linq（语言集成查询）。
 *
 * Linq仅存储查询语句，而不存储查询源。
 * Linq的语法类似Sql，但是以`from`语句开始。
 */
interface Linq<S, T> {
	infix fun <R> select(transform: (T) -> R): Linq<S, R>
	
	infix fun where(predicate: (T) -> Boolean): Linq<S, T>
	
	fun distinct(): Linq<S, T>
	
	infix fun <K> distinctBy(selector: (T) -> K): Linq<S, T>
	
	infix fun <K : Comparable<K>> orderBy(selector: (T) -> K?): Linq<S, T>
	
	infix fun orderBy(comparator: Comparator<T>): Linq<S, T>
	
	infix fun <K : Comparable<K>> orderByDesc(selector: (T) -> K?): Linq<S, T>
	
	infix fun orderByDesc(comparator: Comparator<T>): Linq<S, T>
	
	fun limit(start: Int, end: Int): Linq<S, T>
	
	infix fun limit(range: IntRange): Linq<S, T> = limit(range.first, range.last)
	
	infix fun limit(end: Int): Linq<S, T> = limit(0, end)
	
	fun limitDesc(start: Int, end: Int): Linq<S, T>
	
	infix fun limitDesc(range: IntRange): Linq<S, T> = limitDesc(range.first, range.last)
	
	infix fun limitDesc(end: Int): Linq<S, T> = limitDesc(0, end)
	
	infix fun <K> groupBy(keySelector: (T) -> K): Linq<S, Pair<K, List<T>>>
	
	operator fun invoke(source: Collection<S>): List<T>
}

/**Linq的实现类型。*/
enum class LinqImplementationType {
	Default, Stream, ParallelStream
}

/**创建Linq语句。可选Linq的实现类型，默认为委托给Kotlin集合框架实现。*/
inline fun <reified T> from(type: LinqImplementationType = LinqImplementationType.Default): Linq<T, T> = when(type) {
	LinqImplementationType.Default -> LinqImpl.init()
	LinqImplementationType.Stream -> StreamLinq.init()
	LinqImplementationType.ParallelStream -> StreamLinq.init(true)
}

/**对当前数组进行语言集成查询。*/
inline infix fun <T, R> Array<out T>.linq(linqStatement: Linq<T, R>): List<R> = linqStatement(this.toList())

/**对当前集合进行语言集成查询。*/
inline infix fun <T, R> Collection<T>.linq(linqStatement: Linq<T, R>): List<R> = linqStatement(this)

/**对当前映射进行语言集成查询。*/
inline infix fun <K, V, R> Map<K, V>.linq(linqStatement: Linq<Pair<K, V>, R>): List<R> = linqStatement(this.toList())
