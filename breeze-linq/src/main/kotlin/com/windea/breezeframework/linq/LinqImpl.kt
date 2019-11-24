package com.windea.breezeframework.linq

/**
 * 默认的Linq实现。
 *
 * 委托给Kotlin集合框架。
 */
class LinqImpl<S, T>(
	private val statement: (Iterable<S>) -> Iterable<T>
) : Linq<S, T> {
	override fun <R> select(transform: (T) -> R): Linq<S, R> {
		return LinqImpl { statement(it).map(transform) }
	}
	
	override fun where(predicate: (T) -> Boolean): Linq<S, T> {
		return LinqImpl { statement(it).filter(predicate) }
	}
	
	override fun <R : Comparable<R>> orderBy(selector: (T) -> R?): Linq<S, T> {
		return LinqImpl { statement(it).sortedBy(selector) }
	}
	
	override fun orderBy(comparator: Comparator<T>): Linq<S, T> {
		return LinqImpl { statement(it).sortedWith(comparator) }
	}
	
	override fun limit(start: Int, end: Int): Linq<S, T> {
		return LinqImpl { statement(it).toList().subList(start, end) }
	}
	
	override fun <K> groupBy(keySelector: (T) -> K): Linq<S, Pair<K, List<T>>> {
		return LinqImpl { statement(it).groupBy(keySelector).toList() }
	}
	
	override fun invoke(source: Collection<S>): List<T> {
		return statement(source).toList()
	}
	
	companion object {
		fun <T> init(): Linq<T, T> = LinqImpl { it }
	}
}
