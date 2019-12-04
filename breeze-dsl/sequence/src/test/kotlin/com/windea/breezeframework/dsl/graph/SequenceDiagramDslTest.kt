package com.windea.breezeframework.dsl.graph

import com.windea.breezeframework.dsl.graph.SequenceDiagramMessage.ArrowShape.*
import kotlin.test.*

class SequenceDiagramDslTest {
	@Test //TESTED
	fun test1() {
		println(sequenceDiagram {
			title("Here is title")
			message("A", Arrow, "B") text "Normal line"
			message("B", DashedArrow, "C") text "Dashed line"
			message("C", OpenArrow, "D") text "Open arrow"
			note(leftOf("A")) text "Note"
		})
	}
}
