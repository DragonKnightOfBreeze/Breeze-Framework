package icu.windea.breezeframework.dsl.sequence

import icu.windea.breezeframework.dsl.sequence.SequenceDiagramDsl.ArrowShape.*
import kotlin.test.*

class SequenceDiagramDslTest {
	@Test
	fun test1() {
		println(sequenceDiagramDsl {
			title("Here is title")
			message("A", Arrow, "B") text "Normal line"
			message("B", DashedArrow, "C") text "Dashed line"
			message("C", OpenArrow, "D") text "Open arrow"
			"D" links "E"
			note(leftOf("A")) text "Note"
		})
	}
}
