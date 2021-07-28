// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 随机值生成器。
 *
 * 随机值生成器用于基于给定的参数生成随机值。
 */
interface RandomGenerator<T> : Component {
	/**目标类型。*/
	val targetType: Class<T>

	/**
	 * 生成随机值。
	 */
	fun generate(): T

	override fun copy(componentParams: Map<String, Any?>): RandomGenerator<T> {
		throw UnsupportedOperationException("Cannot copy component of type: ${javaClass.name}.")
	}
}
