// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl

import com.windea.breezeframework.dsl.api.*

/**
 * DSL文档。DSL定义结构的顶级节点。
 *
 * DSL document. Top node of DSL definition structure.
 */
@DslApiMarker
interface DslDocument {
	override fun toString(): String
}
