// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

internal object EmptyIterator : Iterator<Nothing> {
	override fun hasNext(): Boolean = false

	override fun next(): Nothing = throw NoSuchElementException()

	override fun equals(other: Any?): Boolean = other is Iterator<*> && !other.hasNext()

	override fun hashCode(): Int = 1
}


