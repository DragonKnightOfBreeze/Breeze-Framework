package com.windea.breezeframework.dsl.graph

import com.windea.breezeframework.dsl.graph.SequenceDiagramMessage.ArrowShape.*
import kotlin.test.*

class SequenceDiagramDslTest {
	@Test //TESTED
	fun test1() {
		println(sequenceDiagram {
			title("Here is title")
			message("A", Arrow, "B", "Normal line")
			message("B", DashedArrow, "C", "Dashed line")
			message("C", OpenArrow, "D", "Open arrow")
			note(leftOf("A"), "Note")
		})
	}
}
