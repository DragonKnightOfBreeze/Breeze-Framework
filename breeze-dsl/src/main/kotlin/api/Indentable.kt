// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.api

/**
 * 可缩进内容。
 * @property indentContent 是否缩进内容。
 */
@DslApiMarker
interface Indentable {
	var indentContent: Boolean
}
