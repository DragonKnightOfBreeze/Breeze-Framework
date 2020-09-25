/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.core.domain

/**
 * Represents an invocable action node,
 * which is used in an action chain.
 */
interface ActionNode<T,out R> {
	fun predicate(value:T):Boolean

	fun action(value:T):R
}
