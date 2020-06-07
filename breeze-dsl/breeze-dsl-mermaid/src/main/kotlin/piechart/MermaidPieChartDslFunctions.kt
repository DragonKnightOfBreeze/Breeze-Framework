package com.windea.breezeframework.dsl.mermaid.piechart

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.piechart.MermaidPieChart.*
@MermaidPieChartDsl
inline fun mermaidPieChart(block:Document.() -> Unit) = Document().apply(block)
@MermaidPieChartDsl
fun Document.title(text:String) =
	Title(text).also { title = it }
@MermaidPieChartDsl
fun MermaidPieChartDslEntry.section(key:String, value:Number) =
	Section(key, value.toString()).also { sections += it }
