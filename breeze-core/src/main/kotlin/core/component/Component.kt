// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 组件用于实现某个特定的功能，可以自由进行扩展。
 * 一般情况下，组件需要注册，并且需要提供一个无参构造方法，以便正确启用。
 *
 * Component can be used to implement some specific function, and is extensible.
 * Normally, it should be registered and provide a no-arg constructor to be enabled correctly.
 */
interface Component {
	/**
	 * 组件参数。
	 *
	 * 组件参数默认会向下传递。
	 */
	val componentParams: Map<String, Any?> get() = emptyMap()

	/**
	 * 根据指定的组件参数复制当前组件，返回一个新组件。
	 */
	fun copy(componentParams: Map<String, Any?>): Component {
		throw UnsupportedOperationException("Cannot copy component of type: ${javaClass.name}.")
	}
}
