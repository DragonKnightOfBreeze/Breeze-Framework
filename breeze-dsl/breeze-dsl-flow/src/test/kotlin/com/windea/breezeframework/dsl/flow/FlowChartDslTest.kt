package com.windea.breezeframework.dsl.flow

import com.windea.breezeframework.dsl.flow.FlowChartConnection.Direction.*
import com.windea.breezeframework.dsl.flow.FlowChartConnection.Path.*
import com.windea.breezeframework.dsl.flow.FlowChartConnection.Status.*
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
			
			"st" fromTo "op1" fromTo "cond"
			"cond"(Yes) fromTo "io" fromTo "e"
			"cond"(No) fromTo "para"
			"para"(Path1, Bottom) fromTo "sub1"(Right) fromTo "op1"
			"para"(Path2, Top) fromTo "op1"
		})
	}
}
