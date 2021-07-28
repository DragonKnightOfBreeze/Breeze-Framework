// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

//TODO GenericDefaultGenerator

/**
 * 默认值生成器。
 *
 * 默认值生成器用于生成默认值。
 */
interface DefaultGenerator<T> : Component {
	val targetType: Class<T>

	/**
	 * 生成默认值。
	 */
	fun generate(): T

	override fun copy(componentParams: Map<String, Any?>): DefaultGenerator<T> {
		throw UnsupportedOperationException("Cannot copy component of type: ${javaClass.name}.")
	}
}
