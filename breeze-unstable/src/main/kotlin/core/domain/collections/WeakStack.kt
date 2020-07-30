package com.windea.breezeframework.core.domain.collections

import com.windea.breezeframework.core.annotations.*

/**
 * 简单的栈。
 *
 * 这个集合是线程不安全的。
 * @see java.util.Stack
 * @see java.util.Deque
 */
@UnstableImplementationApi
interface WeakStack<E> : Collection<E> {
	fun push(element: E): Boolean

	fun pop(): E

	fun popOrNull(): E?

	fun peek(): E

	fun peekOrNull(): E?
}
