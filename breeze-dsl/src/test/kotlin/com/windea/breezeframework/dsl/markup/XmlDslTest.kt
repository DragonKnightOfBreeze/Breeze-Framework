package com.windea.breezeframework.dsl.markup

import com.windea.breezeframework.dsl.*
import kotlin.test.*

//TESTED VERY NICE!

class XmlDslTest {
	@Test
	fun test() {
		val xml = xml {
			comment("comment")
			comment("""
				comment
				second line
			""".trimIndent()) wrap true
			"element"{
				//"attr1"("value1")
				
				+"text"
				+"""
					text
					text
				""".trimIndent()
				element("elem") {
					text("element text")
				}
				"element"("a" to "b", "c" to "d") {
					+"text"
				}
				comment("""
					comment
					comment
				""".trimIndent())
				comment("""
					comment
					comment
				""".trimIndent()) wrap true indent true
				comment("""
					comment
					comment
				""".trimIndent()) wrap true
				comment("""
					comment
					comment
				""".trimIndent()) indent true
				comment("comment") indent true wrap true
				comment("comment") wrap true
				"element"{
					+"text"
				} wrap false
			}
		}
		println(xml)
	}
}
