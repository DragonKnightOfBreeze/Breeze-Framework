package com.windea.breezeframework.dsl

import com.windea.breezeframework.dsl.mermaid.*
import com.windea.breezeframework.dsl.mermaid.MermaidFlowDirection.*
import com.windea.breezeframework.dsl.mermaid.MermaidFlowLinkArrow.*
import kotlin.test.*

//TESTED VERY NICE!

class MermaidFlowDslTest {
	@Test
	fun test1() {
		val graph1 = mermaidFlow(LR) {
			link(node("S", "Source"), node("T", "Target"))
			link(node("S2", "Source"), node("T2", "Target"), linkText = "Link Text")
			link(node("S3", "Source"), node("T3", "Target"), DottedArrow, "Text")
		}
		println(graph1)
	}
	
	@Test
	fun test2() {
		val graph2 = mermaidFlow(LR) {
			link(node("S", "Source"), node("T", "Target"))
			link(node("S2", "Source"), node("T2", "Target"), linkText = "Link Text")
			link(node("S3", "Source"), node("T3", "Target"), DottedArrow, "Text")
		} indent false
		println(graph2)
	}
	
	@Test
	fun test3() {
		val graph3 = mermaidFlow(LR) {
			link(node("S", "Source"), node("T", "Target"))
			link(node("S3", "Source"), node("T3", "Target"), DottedArrow, "Text")
			subGraph("SubGraph") {
				link(node("S", "Source"), node("T", "Target"))
				link(node("S3", "Source"), node("T3", "Target"), DottedArrow, "Text")
			}
		}
		println(graph3)
	}
}
