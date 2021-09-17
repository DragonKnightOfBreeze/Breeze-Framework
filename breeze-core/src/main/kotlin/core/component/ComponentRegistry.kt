// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.MutableMap
import kotlin.collections.set

/**
 * 组件注册中心用于注册和检索组件。
 *
 * Component Registry can be used to register and index components.
 */
abstract class ComponentRegistry<T : Component> {
	private val backendComponents: MutableMap<String, T> = ConcurrentHashMap()
	@Volatile private var registerDefaultFinished = false

	/**
	 * 得到已注册的组件映射。
	 *
	 * Get registered component map.
	 */
	val components by lazy {
		backendComponents.apply {
			registerDefault()
			registerDefaultFinished = true
		}
	}

	/**
	 * 注册指定的组件。
	 *
	 * Register specific component.
	 */
	fun register(component: T) {
		val components = if (registerDefaultFinished) components else backendComponents
		components[component.componentId] = component
	}

	/**
	 * 注册默认组件。
	 *
	 * Register default components.
	 */
	protected abstract fun registerDefault()
}
