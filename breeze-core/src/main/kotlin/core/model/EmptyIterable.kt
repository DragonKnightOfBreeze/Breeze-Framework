// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

import java.io.*

internal object EmptyIterable : Iterable<Nothing>, Serializable {
	override fun iterator(): Iterator<Nothing> = EmptyIterator

	override fun equals(other: Any?): Boolean = other is Iterable<*> && !other.iterator().hasNext()

	override fun hashCode(): Int = 1

	override fun toString(): String = "[]"
}


