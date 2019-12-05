package com.windea.breezeframework.dsl.mermaid

import org.junit.*

class MermaidPieChartDslTest {
	@Test //TESTED SO EASY
	fun test1() {
		println(mermaidPieChart {
			section("Study", 20)
			section("Play", 30)
			section("Sleep", 50)
		})
	}
}
