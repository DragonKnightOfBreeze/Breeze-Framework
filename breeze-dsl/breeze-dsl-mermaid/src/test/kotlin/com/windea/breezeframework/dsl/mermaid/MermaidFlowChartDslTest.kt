package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.dsl.mermaid.MermaidFlowChart.Direction.*
import com.windea.breezeframework.dsl.mermaid.MermaidFlowChartLink.ArrowShape.*
import kotlin.test.*

class MermaidFlowChartDslTest {
	@Test //PERFECT!
	fun test4() {
		println(mermaidFlowChart(LR) {
			(node("S") text "Source") links (node("T") text "Target")
			(node("S2") text "Source") links (node("T2") text "Target") text "Link Text"
			(node("S3") text "Source") links (node("T3") text "Target") text "Text" arrowShape DottedArrow
			node("A") links node("B") links node("C")
		})
	}
}
