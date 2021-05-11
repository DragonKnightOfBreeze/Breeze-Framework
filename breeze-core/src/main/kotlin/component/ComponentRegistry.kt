// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 组件注册中心用于注册和检索组件。
 *
 * Component Registry can be used to register and index components.
 */
interface ComponentRegistry<T: Component>{
	/**
	 * 得到已注册的组件列表。
	 *
	 * Get registered component list.
	 */
	fun values():List<T>

	/**
	 * 注册指定的组件。
	 *
	 * Register specific component.
	 */
	fun register(component:T)
}
