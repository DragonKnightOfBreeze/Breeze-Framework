package com.windea.breezeframework.dsl.graph.mermaid

import org.junit.*

class MermaidPieChartDslKtTest {
	@Test //TESTED SO EASY
	fun test1() {
		println(mermaidPieChart {
			section("Study", 20)
			section("Play", 30)
			section("Sleep", 50)
		})
	}
	
	annotation class FooAnnotation(val abc: Array<String> = [])
}
