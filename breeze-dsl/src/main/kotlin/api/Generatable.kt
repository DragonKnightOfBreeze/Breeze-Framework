// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.api

import com.windea.breezeframework.core.annotations.*

/**
 * 内容可生成的DSL元素。
 */
@DslApiMarker
interface Generatable {
	var generateContent: Boolean
}