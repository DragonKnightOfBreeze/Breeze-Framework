package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.dsl.*
import com.windea.breezeframework.dsl.mermaid.MermaidFlowDirection.*
import com.windea.breezeframework.dsl.mermaid.MermaidFlowLinkArrowShape.*
import kotlin.test.*

//TESTED VERY NICE!

class MermaidFlowDslTest {
	@Test
	fun test1() {
		val graph1 = mermaidFlowChart(LR) {
			link(node("S", "Source"), node("T", "Target"))
			link(node("S2", "Source"), node("T2", "Target"), "Link Text")
			link(node("S3", "Source"), node("T3", "Target"), "Text") arrowShape DottedArrow
		}
		println(graph1)
	}
	
	@Test
	fun test2() {
		val graph2 = mermaidFlowChart(LR) {
			link(node("S", "Source"), node("T", "Target"))
			link(node("S2", "Source"), node("T2", "Target"), "Link Text")
			link(node("S3", "Source"), node("T3", "Target"), "Text") arrowShape DottedArrow
		} indent false
		println(graph2)
	}
	
	@Test
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
