// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

interface BoundRandomGenerator<T> : RandomGenerator<T>, BoundComponent<T> {
	override fun bindingActualTargetType(actualTargetType: Class<*>): BoundRandomGenerator<T>
}
