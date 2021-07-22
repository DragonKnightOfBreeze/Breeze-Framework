package icu.windea.breezeframework.dsl.mermaid

import icu.windea.breezeframework.dsl.mermaid.piechart.*
import org.junit.*

class MermaidPieChartDslTest {
	@Test
	fun test1() {
		println(mermaidPieChartDsl {
			section("Study", 20)
			section("Play", 30)
			section("Sleep", 50)
		})
	}

	@Test
	fun test2() {
		println(mermaidPieChartDsl {
			title("My Plan")
			section("Study", 20)
			section("Play", 30)
			section("Sleep", 50)
		})
	}
}
