package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidFlowChart.Direction.*
import com.windea.breezeframework.dsl.graph.mermaid.MermaidFlowChartLink.ArrowShape.*
import kotlin.test.*

class MermaidFlowChartDslTest {
	@Test //TESTED VERY NICE!
	fun test1() {
		val graph1 = mermaidFlowChart(LR) {
			link(node("S") text "Source", node("T") text "Target")
			link(node("S2") text "Source", node("T2") text "Target") text "Link Text"
			link(node("S3") text "Source", node("T3") text "Target") text "Text" arrowShape DottedArrow
		}
		println(graph1)
	}
	
	@Test //TESTED VERY NICE!
	fun test2() {
		val graph2 = mermaidFlowChart(LR) {
			link(node("S") text "Source", node("T") text "Target")
			link(node("S2") text "Source", node("T2") text "Target") text "Link Text"
			link(node("S3") text "Source", node("T3") text "Target") text "Text" arrowShape DottedArrow
		} indent false
		println(graph2)
	}
	
	@Test //TESTED VERY NICE!
	fun test3() {
		val graph3 = mermaidFlowChart(LR) {
			link(node("S") text "Source", node("T") text "Target")
			link(node("S") text "Source", node("T") text "Target") text "Text" arrowShape DottedArrow
			subGraph("SubGraph") {
				link(node("S") text "Source", node("T") text "Target")
				link(node("S3") text "Source", node("T3") text "Target") text "Text" arrowShape DottedArrow
			}
		}
		println(graph3)
	}
	
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
