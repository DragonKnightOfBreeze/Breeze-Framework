// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl

import com.windea.breezeframework.dsl.api.*

/**
 * DSL元素。DSL定义结构的成员节点。
 *
 * DSL element. Member node of dsl definition structure.
 */
@DslApiMarker
interface DslElement {
	override fun toString(): String
}

