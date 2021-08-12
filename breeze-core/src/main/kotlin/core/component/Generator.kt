// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 生成器。
 *
 * 生成器用于基于一定规则生成需要的值。
 */
interface Generator<out T>: Component {
	/**
	 * 生成需要的值。
	 */
	fun generate(): T
}
