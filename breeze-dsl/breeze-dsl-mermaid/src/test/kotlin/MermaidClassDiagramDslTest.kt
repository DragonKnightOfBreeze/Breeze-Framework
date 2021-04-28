package icu.windea.breezeframework.dsl.mermaid

import icu.windea.breezeframework.dsl.mermaid.classdiagram.*
import icu.windea.breezeframework.dsl.mermaid.classdiagram.MermaidClassDiagramDsl.*
import kotlin.test.*

class MermaidClassDiagramDslTest {
	@Test
	fun test1() {
		println(mermaidClassDiagramDsl {
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
				annotation(AnnotationType.Enumeration)
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
