// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.dsl

import icu.windea.breezeframework.dsl.api.*

/**
 * DSL文档。DSL定义结构的顶级节点。
 *
 * DSL document. Top node of DSL definition structure.
 */
@DslApiMarker
interface DslDocument {
	override fun toString(): String
}
