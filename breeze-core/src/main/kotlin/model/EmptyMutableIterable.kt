// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

import java.io.Serializable

internal object EmptyMutableIterable : MutableIterable<Nothing>, Serializable {
	override fun iterator(): MutableIterator<Nothing> = EmptyMutableIterator

	override fun equals(other: Any?): Boolean = other is MutableIterable<*> && !other.iterator().hasNext()

	override fun hashCode(): Int = 1

	override fun toString(): String = "[]"
}
