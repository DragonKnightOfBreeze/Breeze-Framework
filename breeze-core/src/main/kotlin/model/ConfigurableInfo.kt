// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

/**
 * 配置信息。
 */
class ConfigurableInfo{
	var params :Map<String,Any?> =emptyMap()
	private set

	//var paramsString = ""
	//private set

	internal fun setConfig(params:Map<String,Any?>){
		this.params = params
		//this.paramsString = params.toString()
	}
}
