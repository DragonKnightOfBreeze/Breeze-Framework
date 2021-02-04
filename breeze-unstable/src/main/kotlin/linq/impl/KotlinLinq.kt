// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.linq.impl

import com.windea.breezeframework.linq.*

/**基于Kotlin的集合框架的Linq实现。*/
class KotlinLinq<S, T>(
	val statement: (Iterable<S>) -> Iterable<T>,
) : Linq<S, T> {
	override fun where(predicate: (T) -> Boolean): Linq<S, T> {
		return KotlinLinq { statement(it).filter(predicate) }
	}

	override fun distinct(): Linq<S, T> {
		return KotlinLinq { statement(it).distinct() }
	}

	override fun <K> distinctBy(selector: (T) -> K): Linq<S, T> {
		return KotlinLinq { statement(it).distinctBy(selector) }
	}

	override fun <R : Comparable<R>> orderBy(selector: (T) -> R?): Linq<S, T> {
		return KotlinLinq { statement(it).sortedBy(selector) }
	}

	override fun orderBy(comparator: Comparator<T>): Linq<S, T> {
		return KotlinLinq { statement(it).sortedWith(comparator) }
	}

	override fun <K : Comparable<K>> orderByDesc(selector: (T) -> K?): Linq<S, T> {
		return KotlinLinq { statement(it).sortedByDescending(selector) }
	}

	override fun orderByDesc(comparator: Comparator<T>): Linq<S, T> {
		return KotlinLinq { statement(it).sortedWith(comparator.reversed()) }
	}

	override fun <K> groupBy(keySelector: (T) -> K): Linq<S, Pair<K, List<T>>> {
		return KotlinLinq { statement(it).groupBy(keySelector).toList() }
	}

	override fun limit(start: Int, end: Int): Linq<S, T> {
		return KotlinLinq { statement(it).toList().let { list -> list.subList(start, end.coerceAtMost(list.size)) } }
	}

	override fun limitDesc(start: Int, end: Int): Linq<S, T> {
		return KotlinLinq { statement(it).toList().let { list -> list.subList(list.size - end.coerceAtMost(list.size), list.size - start) } }
	}

	override fun <R> select(transform: (T) -> R): Linq<S, R> {
		return KotlinLinq { statement(it).map(transform) }
	}

	override fun <R> selectMany(transform: (T) -> Iterable<R>): Linq<S, R> {
		return KotlinLinq { statement(it).flatMap(transform) }
	}

	override fun <R> union(other: Linq<S, T>): Linq<S, T> {
		return KotlinLinq { statement(it) + (other as? KotlinLinq<S, T> ?: typeMismatched()).statement(it) }
	}

	override fun <R> unionAll(other: Linq<S, T>): Linq<S, T> {
		return KotlinLinq { statement(it) union (other as? KotlinLinq<S, T> ?: typeMismatched()).statement(it) }
	}

	private fun typeMismatched(): Nothing {
		throw UnsupportedOperationException("Linq implementation type mismatch, use both `KotlinLinq` instead.")
	}

	override fun invoke(source: Collection<S>): List<T> {
		return statement(source).toList()
	}

	companion object {
		internal fun <T> init(): Linq<T, T> = KotlinLinq { it }
	}
}
