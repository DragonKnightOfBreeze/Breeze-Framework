// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("MermaidDslExtensions")

package com.windea.breezeframework.dsl.mermaid

/**
 * 配置[MermaidDsl]。
 */
@MermaidDslMarker
inline fun mermaidDslConfig(block: MermaidDsl.DslConfig.() -> Unit) {
	MermaidDsl.DslConfig.block()
}
