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
				"Gender"(property("gender"))
			}
			`class`("BreezeKnight") {
				property("weapons")
				property("magics")
				protected("Int"(property("memberId")))
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
			//relation("Person", "BreezeKnight", MermaidClassDiagramRelationType.Inheritance)
			"BreezeKnight" inherits "Person" text "Here are dragon knights!" cardinality ("*" to "1")
		})
	}
}
