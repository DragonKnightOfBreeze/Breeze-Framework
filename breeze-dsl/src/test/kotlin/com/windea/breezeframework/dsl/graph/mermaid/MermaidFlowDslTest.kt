package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidFlowChartDirection.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidFlowChartLinkArrowShape.*
import kotlin.test.*

class MermaidFlowChartDslTest {
	@Test //TESTED VERY NICE!
	fun test1() {
		val graph1 = mermaidFlowChart(LR) {
			link(node("S", "Source"), node("T", "Target"))
			link(node("S2", "Source"), node("T2", "Target"), "Link Text")
			link(node("S3", "Source"), node("T3", "Target"), "Text") arrowShape DottedArrow
		}
		println(graph1)
	}
	
	@Test //TESTED VERY NICE!
	fun test2() {
		val graph2 = mermaidFlowChart(LR) {
			link(node("S", "Source"), node("T", "Target"))
			link(node("S2", "Source"), node("T2", "Target"), "Link Text")
			link(node("S3", "Source"), node("T3", "Target"), "Text") arrowShape DottedArrow
		} indent false
		println(graph2)
	}
	
	@Test //TESTED VERY NICE!
	fun test3() {
		val graph3 = mermaidFlowChart(LR) {
			link(node("S", "Source"), node("T", "Target"))
			link(node("S3", "Source"), node("T3", "Target"), "Text") arrowShape DottedArrow
			subGraph("SubGraph") {
				link(node("S", "Source"), node("T", "Target"))
				link(node("S3", "Source"), node("T3", "Target"), "Text") arrowShape DottedArrow
			}
		}
		println(graph3)
	}
}
