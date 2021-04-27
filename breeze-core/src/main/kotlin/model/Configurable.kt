// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

/**
 * 可配置的对象。
 */
interface Configurable<T:Configurable<T>>{
	/**
	 * 配置信息。
	 */
	val configurableInfo: ConfigurableInfo

	/**
	 * 使用指定的配置配置当前对象。
	 *
	 * 注意：继承此方法时需要先调用`super.configure(params)`。
	 */
	fun configure(params: Map<String, Any?>) {
		configurableInfo.setConfig(params)
	}

	/**
	 * 根据指定的配置复制当前对象。
	 */
	fun copy(params:Map<String,Any?>): T
}

