package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.dsl.graph.mermaid.MermaidFlowChart.Direction.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidFlowChartLink.ArrowShape.*
import kotlin.test.*

class MermaidFlowChartDslTest {
	@Test //TESTED PERFECT!
	fun test4() {
		println(mermaidFlowChart(LR) {
			(node("S") text "Source") fromTo (node("T") text "Target")
			(node("S2") text "Source") fromTo (node("T2") text "Target") text "Link Text"
			(node("S3") text "Source") fromTo (node("T3") text "Target") text "Text" arrowShape DottedArrow
			node("A") fromTo node("B") fromTo node("C")
		})
	}
}
