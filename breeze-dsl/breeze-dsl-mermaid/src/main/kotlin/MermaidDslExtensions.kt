/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.dsl.mermaid

/**Configure a [MermaidDsl] by [MermaidDslConfig].*/
@MermaidDslMarker
inline fun mermaidDslConfig(block: MermaidDslConfig.() -> Unit) = MermaidDslConfig.block()
