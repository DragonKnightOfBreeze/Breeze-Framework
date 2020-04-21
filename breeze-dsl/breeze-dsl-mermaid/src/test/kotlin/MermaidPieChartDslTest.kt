package com.windea.breezeframework.dsl.mermaid

import com.windea.breezeframework.dsl.mermaid.piechart.*
import org.junit.*

class MermaidPieChartDslTest {
	@Test
	fun test1() {
		println(mermaidPieChart {
			section("Study", 20)
			section("Play", 30)
			section("Sleep", 50)
		})
	}

	@Test
	fun test2() {
		println(mermaidPieChart {
			title("My Plan")
			section("Study", 20)
			section("Play", 30)
			section("Sleep", 50)
		})
	}
}
