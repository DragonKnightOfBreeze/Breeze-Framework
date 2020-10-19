// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl.api

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.dsl.api.*

/**
 * 内容可分割的DSL元素。
 */
@DslApiMarker
interface Splitable {
	var splitContent: Boolean
}
