// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

/**
 * 可配置的组件。
 *
 * Configurable Component.
 */
interface ConfigurableComponent:Component{
	fun configure(params:Map<String,Any?>){}
}
