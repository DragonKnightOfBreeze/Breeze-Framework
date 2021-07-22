package icu.windea.breezeframework.dsl.mermaid

import icu.windea.breezeframework.dsl.mermaid.flowchart.*
import icu.windea.breezeframework.dsl.mermaid.flowchart.MermaidFlowChartDsl.ArrowShape.*
import icu.windea.breezeframework.dsl.mermaid.flowchart.MermaidFlowChartDsl.Direction.*
import kotlin.system.*
import kotlin.test.*

class MermaidFlowChartDslTest {
	@Test
	fun test4() {
		measureTimeMillis {
			mermaidFlowChartDsl(LR) {
				(node("S") text "Source") links (node("T") text "Target")
				(node("S2") text "Source") links (node("T2") text "Target") text "Link Text"
				(node("S3") text "Source") links (node("T3") text "Target") text "Text" arrowShape DottedArrow
				node("A") links node("B") links node("C")
			}.toString()
		}.also { println(it) }
	}
}
