// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

/**
 * Represents an invocable action node,
 * which is used in an action chain.
 */
interface ActionNode<T, out R> {
	fun predicate(value: T): Boolean

	fun action(value: T): R
}
