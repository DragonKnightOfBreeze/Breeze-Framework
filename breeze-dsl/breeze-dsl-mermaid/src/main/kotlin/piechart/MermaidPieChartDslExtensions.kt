package com.windea.breezeframework.dsl.mermaid.piechart

import com.windea.breezeframework.dsl.mermaid.piechart.MermaidPieChartDslDefinitions.*

@MermaidPieChartDslMarker
inline fun mermaidPieChartDsl(block: MermaidPieChartDsl.() -> Unit) = MermaidPieChartDsl().apply(block)


@MermaidPieChartDslMarker
fun MermaidPieChartDsl.title(text: String) = Title(text).also { title = it }

@MermaidPieChartDslMarker
fun IChartDslEntry.section(key: String, value: Number) = Section(key, value.toString()).also { sections += it }
