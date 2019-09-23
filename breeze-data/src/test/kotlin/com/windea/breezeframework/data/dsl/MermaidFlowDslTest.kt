package com.windea.breezeframework.data.dsl

import com.windea.breezeframework.data.dsl.text.*
import com.windea.breezeframework.data.dsl.text.MermaidFlowDirection.*
import com.windea.breezeframework.data.dsl.text.MermaidFlowLinkShape.*
import kotlin.test.*

//TESTED VERY NICE!

class MermaidFlowDslTest {
	@Test
	fun test1() {
		val graph1 = mermaidGraph(LR) {
			link(node("S", "Source"), node("T", "Target"))
			link(node("S2", "Source"), node("T2", "Target"), linkText = "Link Text")
			link(node("S3", "Source"), node("T3", "Target"), Dotted, "Text")
		}
		println(graph1)
	}
	
	@Test
	fun test2() {
		val graph2 = mermaidGraph(LR) {
			link(node("S", "Source"), node("T", "Target"))
			link(node("S2", "Source"), node("T2", "Target"), linkText = "Link Text")
			link(node("S3", "Source"), node("T3", "Target"), Dotted, "Text")
		} indent false
		println(graph2)
	}
	
	@Test
	fun test3() {
		val graph3 = mermaidGraph(LR) {
			link(node("S", "Source"), node("T", "Target"))
			link(node("S3", "Source"), node("T3", "Target"), Dotted, "Text")
			subGraph("SubGraph") {
				link(node("S", "Source"), node("T", "Target"))
				link(node("S3", "Source"), node("T3", "Target"), Dotted, "Text")
			}
		}
		println(graph3)
	}
}
