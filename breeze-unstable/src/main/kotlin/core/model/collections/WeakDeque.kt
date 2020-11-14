// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model.collections

import com.windea.breezeframework.core.annotations.*

/**
 * 简化的双向队列。
 *
 * 这个集合是线程不安全的。
 * @see java.util.Deque
 */
@UnstableApi
interface WeakDeque<E> : WeakQueue<E>, WeakStack<E> {
	fun addFirst(element: E): Boolean

	fun addLast(element: E): Boolean

	fun removeFirst(): E

	fun removeLast(): E

	fun removeFirstOrNull(): E?

	fun removeLastOrNull(): E?

	fun peekFirst(): E

	fun peekLast(): E

	fun peekFirstOrNull(): E?

	fun peekLastOrNull(): E?
}
