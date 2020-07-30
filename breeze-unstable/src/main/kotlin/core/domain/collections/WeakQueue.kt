package com.windea.breezeframework.core.domain.collections

import com.windea.breezeframework.core.annotations.*

/**
 * 简化的队列。
 *
 * 这个集合是线程不安全的。
 * @see java.util.Queue
 */
@UnstableImplementationApi
interface WeakQueue<E> : Collection<E> {
	fun add(element: E): Boolean

	fun remove(): E

	fun removeOrNull(): E?

	fun peek(): E

	fun peekOrNull(): E?
}
