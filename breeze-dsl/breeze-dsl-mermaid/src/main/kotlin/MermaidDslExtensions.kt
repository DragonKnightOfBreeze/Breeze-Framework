package com.windea.breezeframework.dsl.mermaid

/**Configure a [MermaidDsl] by [MermaidDslConfig].*/
@MermaidDslMarker
inline fun mermaidDslConfig(block: MermaidDslConfig.() -> Unit) = MermaidDslConfig.block()
