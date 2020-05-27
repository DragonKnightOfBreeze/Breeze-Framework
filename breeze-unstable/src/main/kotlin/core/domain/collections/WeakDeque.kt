package com.windea.breezeframework.core.domain.collections

import com.windea.breezeframework.core.annotations.*

/**
 * 简化的双向队列。
 *
 * 这个集合是线程不安全的。
 * @see java.util.Deque
 */
@UnstableImplementationApi
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
