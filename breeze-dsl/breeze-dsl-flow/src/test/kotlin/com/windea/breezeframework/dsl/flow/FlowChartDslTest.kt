package com.windea.breezeframework.dsl.flow

import com.windea.breezeframework.dsl.flow.FlowChart.ConnectionDirection.*
import com.windea.breezeframework.dsl.flow.FlowChart.ConnectionPath.*
import com.windea.breezeframework.dsl.flow.FlowChart.ConnectionStatus.*
import kotlin.test.*

class FlowChartDslTest {
	//st=>start: Start:>http://www.google.com[blank]
	//e=>end:>http://www.google.com
	//op1=>operation: My Operation
	//sub1=>subroutine: My Subroutine
	//cond=>condition: Yes
	//or No?:>http://www.google.com
	//io=>inputoutput: catch something...
	//para=>parallel: parallel tasks
	//
	//st->op1->cond
	//cond(yes)->io->e
	//cond(no)->para
	//para(path1, bottom)->sub1(right)->op1
	//para(path2, top)->op1

	@Test
	fun test1() {
		println(flowChart {
			start("st") text "Start" newUrlLink "http://www.google.com"
			end("e") urlLink "http://www.google.com"
			operation("op1") text "My Operation"
			subroutine("sub1") text "My Subroutine"
			condition("cond") text "Yes or No?" urlLink "http://www.google.com"
			inputOutput("io") text "catch something..."
			parallel("para") text "parallel tasks"

			"st" links "op1" links "cond"
			"cond"(Yes) links "io" links "e"
			"cond"(No) links "para"
			"para"(Path1, Bottom) links "sub1"(Right) links "op1"
			"para"(Path2, Top) links "op1"
		})
	}
}
