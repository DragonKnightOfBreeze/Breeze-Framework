// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl

import com.windea.breezeframework.dsl.api.*

/**
 * DSL入口。DSL定义结构的入口节点。
 *
 * DSL entry. entry node of dsl definition structure.
 */
@DslApiMarker
interface DslEntry {
	fun toContentString(): String = ""
}
