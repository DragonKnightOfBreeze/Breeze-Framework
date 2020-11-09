// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain

/**
 * 可配置的对象。
 */
interface Configurable<T> {
	/**
	 * 基于特定的配置对象，配置当前对象。一般来说，需要在使用前配置完毕。
	 */
	fun configure(block:T.()->Unit)
}
