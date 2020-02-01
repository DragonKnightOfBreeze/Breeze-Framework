package com.windea.breezeframework.core.domain.collections

/**
 * 树。
 *
 * 树由一个值和下面的多个节点组成，每个节点都是一个嵌套的树。
 *
 * 这个集合是线程不安全的。
 */
interface Tree<out E> : Iterable<E> {
	val value: E
	val values: Collection<E>
	val nodes: Collection<Tree<E>>
	val size: Int
	fun isEmpty(): Boolean
	fun containsValue(value: @UnsafeVariance E): Boolean
	fun containsNode(node: Tree<@UnsafeVariance E>): Boolean
	operator fun get(value: @UnsafeVariance E): Tree<E>
	fun getOrDefault(value: @UnsafeVariance E, defaultValue: Tree<@UnsafeVariance E>): Tree<E>
}

/**
 * 可修改的树。
 *
 * 树由一个值和下面的多个节点组成，每个节点都是一个嵌套的树。
 *
 * 这个集合是线程不安全的。
 */
interface MutableTree<E> : Tree<E> {
	fun addValue(value: E): Boolean
	fun addNode(node: Tree<E>): Boolean
	fun removeValue(value: E): Boolean
	fun removeNode(node: Tree<E>): Boolean
	fun clear()
}
