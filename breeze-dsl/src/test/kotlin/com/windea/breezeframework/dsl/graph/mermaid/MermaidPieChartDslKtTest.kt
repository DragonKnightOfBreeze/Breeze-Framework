package com.windea.breezeframework.dsl.graph.mermaid

import org.junit.*

class MermaidPieChartDslKtTest {
	@Test //TESTED SO EASY
	fun test1() {
		println(mermaidPieChart {
			part("Study", 20)
			part("Play", 30)
			part("Sleep", 50)
		})
	}
}
