// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("MermaidDslExtensions")

package icu.windea.breezeframework.dsl.mermaid

/**
 * 配置[MermaidDsl]。
 */
@MermaidDslMarker
inline fun mermaidDslConfig(block: MermaidDsl.DslConfig.() -> Unit) {
	MermaidDsl.DslConfig.block()
}
