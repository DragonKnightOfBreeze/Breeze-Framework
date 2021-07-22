// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl.api

/**
 * 可缩进内容。
 * @property indentContent 是否缩进内容。
 */
@DslApiMarker
interface Indentable {
	var indentContent: Boolean
}
