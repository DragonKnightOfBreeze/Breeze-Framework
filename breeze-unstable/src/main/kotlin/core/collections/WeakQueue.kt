// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model.collections

import com.windea.breezeframework.core.annotation.*

/**
 * 简化的队列。
 *
 * 这个集合是线程不安全的。
 * @see java.util.Queue
 */
@UnstableApi
interface WeakQueue<E> : Collection<E> {
	fun add(element: E): Boolean

	fun remove(): E

	fun removeOrNull(): E?

	fun peek(): E

	fun peekOrNull(): E?
}
