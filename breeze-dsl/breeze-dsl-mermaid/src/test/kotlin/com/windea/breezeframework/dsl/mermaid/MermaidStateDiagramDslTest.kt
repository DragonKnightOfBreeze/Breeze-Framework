package com.windea.breezeframework.dsl.mermaid

import kotlin.test.*

class MermaidStateDiagramDslTest {
	@Test
	fun test1() {
		println(mermaidStateDiagram {
			state("Attack") text "Attack with long sword."
			state("Defence") text "Defence with middle shield."

			initState() links "Idle"
			"Idle" links "Attack" text "When player is nearby."
			"Idle" links "Move" text "When player is escaping."
			"<AnyState>" links "Die" text "When this NPC's HP <= 0."
			"Die" links finishState()

			note(leftOf("Attack")) text "Here the NPC attack."
		})
	}
}
