package com.windea.breezeframework.dsl.graph.mermaid

import com.windea.breezeframework.dsl.graph.mermaid.MermaidClassDiagramAnnotation.Type.*
import kotlin.test.*

class MermaidClassDiagramDslTest {
	@Test //TESTED OK, @ReplaceWith WORKS
	fun test1() {
		//居然真的可以这样写。。。
		println(mermaidClassDiagram {
			`class`("Person") {
				property("name")
				property("gender") type "Gender"
			}
			`class`("BreezeKnight") {
				property("weapons") type "List<Weapon>"
				property("magics") type "List<Magic>"
				protected(property("memberId")) type "Int"
				public(method("tellStoriesAndTales"()))
				method("helloBreezeFramework"())
				method("weaponAttack"("weaponName"))
				method("magicCast"("magicName"))
				method("dragonDrive"("dragon", "licence"))
			}
			`class`("Gender") {
				annotation(Enumeration)
				property("Male")
				property("Female")
				property("ImmortalMale")
				property("ImmortalFemale")
			}
			`class`("Weapon")
			`class`("Magic")
			
			"Person" inheritedBy "BreezeKnights" text "Here are dragon knights!" cardinality ("1" to "*")
		})
	}
}
