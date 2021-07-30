// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.collections

import icu.windea.breezeframework.core.annotation.*

/**
 * 树。
 *
 * 树由一个值和下面的多个节点组成，每个节点都是一个嵌套的树。
 *
 * 这个集合是线程不安全的。
 */
@UnstableApi
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

