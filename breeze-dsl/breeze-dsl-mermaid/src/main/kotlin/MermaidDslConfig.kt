/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.dsl.*

/**
 * Dsl config of [MermaidDsl].
 */
object MermaidDslConfig : DslConfig {
	var indent: String = "  "
	var doubleQuoted: Boolean = true

	internal val quote get() = if(doubleQuoted) '\"' else '\''
}
