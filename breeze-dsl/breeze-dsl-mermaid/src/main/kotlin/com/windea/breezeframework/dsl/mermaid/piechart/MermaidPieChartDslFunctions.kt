package com.windea.breezeframework.dsl.mermaid.piechart

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.piechart.MermaidPieChart.*

@TopDslFunction
@MermaidPieChartDsl
inline fun mermaidPieChart(block:Document.() -> Unit) = Document().apply(block)


@DslFunction
@MermaidPieChartDsl
fun Document.title(text:String) =
	Title(text).also { title = it }

@DslFunction
@MermaidPieChartDsl
fun MermaidPieChartDslEntry.section(key:String, value:Number) =
	Section(key, value.toString()).also { sections += it }
