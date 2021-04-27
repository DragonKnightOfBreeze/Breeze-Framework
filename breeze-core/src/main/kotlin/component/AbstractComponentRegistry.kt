// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

abstract class AbstractComponentRegistry<T : Component> : ComponentRegistry<T> {
	@Volatile private var shouldRegisterDefault = true
	@Volatile private var startRegisterDefault = false
	private val _components  = mutableListOf<T>()

	protected val components by lazy { _components.apply {
		startRegisterDefault = true
		registerDefault()
		shouldRegisterDefault = false
	} }

	override fun values(): List<T> {
		return if(shouldRegisterDefault && startRegisterDefault) _components else components
	}

	override fun register(component: T) {
		val components = if(shouldRegisterDefault && startRegisterDefault) _components else components
		components.add(component)
	}

	protected abstract fun registerDefault()
}
