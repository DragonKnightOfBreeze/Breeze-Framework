// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

interface BoundComponent<T>: Component {
	val actualTargetType:Class<out T>

	fun bindingActualTargetType(actualTargetType:Class<*>) : BoundComponent<T>
}
