// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.api

/**
 * 可生成内容。
 * @property generateContent 是否生成内容。
 */
@DslApiMarker
interface Generatable {
	var generateContent: Boolean
}
