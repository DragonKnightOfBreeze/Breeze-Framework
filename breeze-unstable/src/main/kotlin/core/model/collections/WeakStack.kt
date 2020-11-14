// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model.collections

import com.windea.breezeframework.core.annotations.*

/**
 * 简单的栈。
 *
 * 这个集合是线程不安全的。
 * @see java.util.Stack
 * @see java.util.Deque
 */
@UnstableApi
interface WeakStack<E> : Collection<E> {
	fun push(element: E): Boolean

	fun pop(): E

	fun popOrNull(): E?

	fun peek(): E

	fun peekOrNull(): E?
}
