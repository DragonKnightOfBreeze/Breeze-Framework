// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model.collections

import com.windea.breezeframework.core.annotation.*

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
