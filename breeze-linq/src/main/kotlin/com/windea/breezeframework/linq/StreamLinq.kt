package com.windea.breezeframework.linq

import java.util.stream.*

/**
 * 基于Stream的Linq实现。
 *
 * 委托给[java.util.stream.Stream]。
 */
class StreamLinq<S, T>(
	private val isParallel: Boolean = false,
	private val statement: (Stream<S>) -> Stream<T>
) : Linq<S, T> {
	override fun <R> select(transform: (T) -> R): Linq<S, R> {
		return StreamLinq { statement(it).map(transform) }
	}
	
	override fun where(predicate: (T) -> Boolean): Linq<S, T> {
		return StreamLinq { statement(it).filter(predicate) }
	}
	
	override fun <K : Comparable<K>> orderBy(selector: (T) -> K?): Linq<S, T> {
		return StreamLinq { statement(it).sorted(compareBy(selector)) }
	}
	
	override fun orderBy(comparator: Comparator<T>): Linq<S, T> {
		return StreamLinq { statement(it).sorted(comparator) }
	}
	
	override fun limit(start: Int, end: Int): Linq<S, T> {
		return StreamLinq { statement(it).skip(start.toLong()).limit(end.toLong()) }
	}
	
	override fun <K> groupBy(keySelector: (T) -> K): Linq<S, Pair<K, List<T>>> {
		return StreamLinq { statement(it).collect(Collectors.groupingBy(keySelector)).toList().toStream(isParallel) }
	}
	
	override fun invoke(source: Collection<S>): List<T> {
		return statement(source.toStream(isParallel)).collect(Collectors.toList())
	}
	
	private fun <T> Collection<T>.toStream(isParallel: Boolean = false): Stream<T> {
		return if(!isParallel) stream() else parallelStream()
	}
	
	companion object {
		fun <T> init(isParallel: Boolean = false) = StreamLinq<T, T>(isParallel) { it }
	}
}
