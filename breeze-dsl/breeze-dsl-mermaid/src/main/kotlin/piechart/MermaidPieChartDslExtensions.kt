// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("MermaidPieChartDslExtensions")

package com.windea.breezeframework.dsl.mermaid.piechart

import com.windea.breezeframework.dsl.mermaid.piechart.MermaidPieChartDsl.*

/**
 * 开始构建[MermaidPieChartDsl]。
 */
@MermaidPieChartDslMarker
inline fun mermaidPieChartDsl(block: DslDocument.() -> Unit) = DslDocument().apply(block)

/**
 * 创建一个[MermaidPieChartDsl.Title]并注册。
 */
@MermaidPieChartDslMarker
fun DslDocument.title(text: String): Title {
	return Title(text).also { title = it }
}

/**
 * 创建一个[MermaidPieChartDsl.Section]并注册。
 */
@MermaidPieChartDslMarker
fun ChartDslEntry.section(key: String, value: Number): Section {
	return Section(key, value.toString()).also { sections += it }
}
