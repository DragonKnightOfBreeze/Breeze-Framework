// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl.api

/**
 * 可生成内容。
 * @property generateContent 是否生成内容。
 */
@DslApiMarker
interface Generatable {
	var generateContent: Boolean
}
