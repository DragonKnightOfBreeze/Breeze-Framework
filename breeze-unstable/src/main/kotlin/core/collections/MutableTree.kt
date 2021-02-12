// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model.collections

import com.windea.breezeframework.core.annotation.*

/**
 * 可修改的树。
 *
 * 树由一个值和下面的多个节点组成，每个节点都是一个嵌套的树。
 *
 * 这个集合是线程不安全的。
 */
@UnstableApi
interface MutableTree<E> : Tree<E> {
	fun addValue(value: E): Boolean
	fun addNode(node: Tree<E>): Boolean
	fun removeValue(value: E): Boolean
	fun removeNode(node: Tree<E>): Boolean
	fun clear()
}
