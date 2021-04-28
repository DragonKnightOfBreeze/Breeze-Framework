// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model.collections

import icu.windea.breezeframework.core.annotation.*

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
