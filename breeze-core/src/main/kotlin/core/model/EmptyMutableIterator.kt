// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

internal object EmptyMutableIterator : MutableIterator<Nothing> {
	override fun hasNext(): Boolean = false

	override fun next(): Nothing = throw NoSuchElementException()

	override fun remove() = throw IllegalStateException()

	override fun equals(other: Any?): Boolean = other is MutableIterator<*> && !other.hasNext()

	override fun hashCode(): Int = 1
}
