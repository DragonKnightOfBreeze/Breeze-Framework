package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.dsl.*

/*** (No document.)*/
@MermaidDsl
inline fun mermaidConfig(block:Mermaid.Config.() -> Unit) = Mermaid.config.block()
