// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

/**
 * 随机值生成器。
 *
 * 随机值生成器用于基于给定的参数生成随机值。
 */
interface RandomGenerator<T>: ConfigurableComponent {
	/**
	 * 生成随机值。
	 */
	fun generate():T

	companion object Registry: AbstractComponentRegistry<RandomGenerator<*>>(){
		init {
			registerDefaultRandomGenerators()
		}

		private fun registerDefaultRandomGenerators(){

		}
	}

	//region Default Generators
	class RandomIntGenerator{

	}
	//endregion
}
