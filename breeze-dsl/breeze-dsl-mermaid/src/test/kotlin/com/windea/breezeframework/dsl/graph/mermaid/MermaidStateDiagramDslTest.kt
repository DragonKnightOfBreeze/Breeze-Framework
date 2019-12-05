package com.windea.breezeframework.dsl.graph.mermaid

import kotlin.test.*

class MermaidStateDiagramDslTest {
	@Test
	fun test1() {
		println(mermaidStateDiagram {
			state("Attack") text "Attack with long sword."
			state("Defence") text "Defence with middle shield."
			
			initState() fromTo "Idle"
			"Idle" fromTo "Attack" text "When player is nearby."
			"Idle" fromTo "Move" text "When player is escaping."
			"<AnyState>" fromTo "Die" text "When this NPC's HP <= 0."
			"Die" fromTo finishState()
			
			note(leftOf("Attack")) text "Here the NPC attack."
		})
	}
}
