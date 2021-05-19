// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

/**
 * 可配置的对象。
 */
interface Configurable {
	/**
	 * 配置参数。
	 */
	val configParams: Map<String, Any?>

	/**
	 * 根据指定的配置复制当前对象，返回一个新对象。
	 */
	@Suppress("UNCHECKED_CAST")
	fun configure(configParams: Map<String, Any?>): Configurable
}

