package com.windea.breezeframework.linq

import java.util.stream.*

/**
 * 基于Stream的Linq实现。
 *
 * @see java.util.stream.Stream
 */
class StreamLinq<S, T>(
	val isParallel: Boolean = false,
	val statement: (Stream<S>) -> Stream<T>
) : Linq<S, T> {
	override fun where(predicate: (T) -> Boolean): Linq<S, T> {
		return StreamLinq { statement(it).filter(predicate) }
	}
	
	override fun distinct(): Linq<S, T> {
		return StreamLinq { statement(it).distinct() }
	}
	
	override fun <K> distinctBy(selector: (T) -> K): Linq<S, T> {
		val set = HashSet<K>()
		return StreamLinq { statement(it).filter { key -> set.add(selector(key)) } }
	}
	
	override fun <K : Comparable<K>> orderBy(selector: (T) -> K?): Linq<S, T> {
		return StreamLinq { statement(it).sorted(compareBy(selector)) }
	}
	
	override fun orderBy(comparator: Comparator<T>): Linq<S, T> {
		return StreamLinq { statement(it).sorted(comparator) }
	}
	
	override fun <K : Comparable<K>> orderByDesc(selector: (T) -> K?): Linq<S, T> {
		return StreamLinq { statement(it).sorted(compareByDescending(selector)) }
	}
	
	override fun orderByDesc(comparator: Comparator<T>): Linq<S, T> {
		return StreamLinq { statement(it).sorted(comparator.reversed()) }
	}
	
	override fun <K> groupBy(keySelector: (T) -> K): Linq<S, Pair<K, List<T>>> {
		return StreamLinq { statement(it).collect(Collectors.groupingBy(keySelector)).toList().toStream(isParallel) }
	}
	
	override fun limit(start: Int, end: Int): Linq<S, T> {
		return StreamLinq { statement(it).skip(start.toLong()).limit(end.toLong()) }
	}
	
	override fun limitDesc(start: Int, end: Int): Linq<S, T> {
		throw UnsupportedOperationException("Operation 'limitDesc' of linq implementation 'StreamLinq' is not supported.")
	}
	
	override fun <R> select(transform: (T) -> R): Linq<S, R> {
		return StreamLinq { statement(it).map(transform) }
	}
	
	override fun <R> selectMany(transform: (T) -> Iterable<R>): Linq<S, R> {
		return StreamLinq { statement(it).flatMap { e -> StreamSupport.stream(transform(e).spliterator(), isParallel) } }
	}
	
	override fun <R> union(other: Linq<S, T>): Linq<S, T> {
		return StreamLinq { Stream.concat(statement(it), (other as? StreamLinq<S, T> ?: throw typeMismatch()).statement(it)).distinct() }
	}
	
	override fun <R> unionAll(other: Linq<S, T>): Linq<S, T> {
		return StreamLinq { Stream.concat(statement(it), (other as? StreamLinq<S, T> ?: throw typeMismatch()).statement(it)) }
	}
	
	private fun typeMismatch(): UnsupportedOperationException {
		return UnsupportedOperationException("Linq implementation type mismatch, use both `StreamLinq` instead.")
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
