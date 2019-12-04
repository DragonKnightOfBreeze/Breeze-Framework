package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.dsl.graph.mermaid.MermaidClassDiagramAnnotation.Type.*
import kotlin.test.*

class MermaidClassDiagramDslTest {
	@Test //TESTED OK
	fun test1() {
		println(mermaidClassDiagram {
			`class`("Person") {
				statement("name")
				statement("gender" type "Gender")
			}
			`class`("BreezeKnight") {
				statement("weapons" type "List<Weapon>")
				statement("magics" type "List<Magic>")
				protected(statement("memberId" type "Int"))
				public(statement("tellStoriesAndTales"()))
				statement("helloBreezeFramework"())
				statement("weaponAttack"("weaponName" type "String"))
				statement("magicCast"("magicName"))
				statement("dragonDrive"("dragon", "licence"))
			}
			`class`("Gender") {
				annotation(Enumeration)
				statement("Male")
				statement("Female")
				statement("ImmortalMale")
				statement("ImmortalFemale")
			}
			`class`("Weapon")
			`class`("Magic")
			
			"Person" inheritedBy "BreezeKnights" text "Here are dragon knights!" cardinality ("1" to "*")
		})
	}
}
