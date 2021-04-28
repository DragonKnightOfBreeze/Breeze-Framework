// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl

import icu.windea.breezeframework.dsl.api.*

/**
 * DSL元素。DSL定义结构的成员节点。
 *
 * DSL element. Member node of dsl definition structure.
 */
@DslApiMarker
interface DslElement {
	override fun toString(): String
}

