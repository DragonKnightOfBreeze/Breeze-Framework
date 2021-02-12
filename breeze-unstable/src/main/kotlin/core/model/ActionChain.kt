// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

/**
 * Represents an invocable action chain,
 * which implies the *Chain of Responsibility* pattern.
 */
class ActionChain<T, R>(
	val value: T,
	val nodes: Array<ActionNode<T, R>>,
) {
	fun execute(): R {
		for(node in nodes) {
			if(node.predicate(value)) return node.action(value)
		}
		throw UnsupportedOperationException()
	}
}
