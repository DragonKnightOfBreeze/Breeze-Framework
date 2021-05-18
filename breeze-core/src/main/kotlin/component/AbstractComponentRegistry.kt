// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import java.util.concurrent.atomic.*

abstract class AbstractComponentRegistry<T : Component> : ComponentRegistry<T> {
	private val backendComponents  = mutableListOf<T>()
	private val registerDefaultFinished = AtomicBoolean(false)

	protected val components by lazy { backendComponents.apply {
		registerDefault()
		registerDefaultFinished.set(true)
	} }

	override fun values(): List<T> {
		return if(registerDefaultFinished.get()) components else backendComponents
	}

	override fun register(component: T) {
		val components = if(registerDefaultFinished.get()) components else backendComponents
		components.add(component)
	}

	protected abstract fun registerDefault()
}
