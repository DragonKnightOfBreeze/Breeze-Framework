// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

/**
 * 配置信息。
 */
class ConfigurableInfo {
	var configParams: Map<String, Any?> = emptyMap()
		private set

	//var configParamsString = ""
	//private set

	internal fun setConfig(configParams: Map<String, Any?>) {
		this.configParams = configParams
		//this.configParamsString = configParams.toString()
	}
}
