// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 组件用于实现某个特定的功能，可以自由进行扩展。
 * 一般情况下，组件需要注册，并且提供一个无参构造方法，以便正确启用。
 *
 * Component can be used to implement some specific function, and is extensible.
 * Normally, it should be registered and provide a no-arg constructor to be enabled correctly.
 */
interface Component {
	/**
	 * 组件ID。
	 *
	 * Component id.
	 */
	val componentId get() = if(componentParams.isEmpty()) javaClass.name else javaClass.name + '@' + componentParams

	/**
	 * 组件参数。组件参数默认会向下传递。
	 *
	 * Component parameters. By default, component parameters will pass down.
	 */
	val componentParams: Map<String, Any?> get() = emptyMap()

	fun componentEquals(other: Any?): Boolean {
		if(other !is Component) return false
		return componentId == other.componentId && componentParams.toString() == other.componentParams.toString()
	}

	fun componentHashcode(): Int {
		return if(componentParams.isEmpty()) 0 else componentParams.toString().hashCode()
	}

	fun componentToString(): String {
		return componentId
	}

	fun componentCopy(componentParams: Map<String, Any?> = emptyMap()): Component {
		throw UnsupportedOperationException("Cannot copy component of type: ${javaClass.name}.")
	}
}

