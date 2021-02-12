// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.linq

/**
 * Linq（语言集成查询）。
 *
 * * Linq仅存储查询语句，而不存储查询源。
 * * Linq的语法类似Sql，但是以`from`语句开始。
 * * 这个实现按照正序执行Linq。
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


