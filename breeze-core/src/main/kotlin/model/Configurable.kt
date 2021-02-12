// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

/**
 * 可配置的对象。
 */
interface Configurable<T> {
	/**
	 * 基于特定的配置对象，配置当前对象。一般来说，需要在使用前配置完毕。
	 */
	fun configure(block: T.() -> Unit)
}
