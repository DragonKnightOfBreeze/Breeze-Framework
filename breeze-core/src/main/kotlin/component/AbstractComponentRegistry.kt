// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

abstract class AbstractComponentRegistry<T : Component> : ComponentRegistry<T> {
	protected val components = mutableListOf<T>()

	override fun values(): List<T> {
		return components
	}

	override fun register(component: T) {
		components.add(component)
	}
}
