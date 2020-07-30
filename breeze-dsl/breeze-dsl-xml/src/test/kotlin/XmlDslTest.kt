package com.windea.breezeframework.dsl.xml

import com.windea.breezeframework.dsl.*
import kotlin.test.*

//TESTED VERY NICE!

class XmlDslTest {
	@Test
	fun test() {
		val xml = xmlDsl {
			comment("comment")
			comment("""
				comment
				second line
			""".trimIndent()) wrap true
			"element"{
				+"inlineText"
				+"""
					inlineText
					inlineText
				""".trimIndent()
				element("elem") {
					text("element inlineText")
				}
				"element"("a" to "b", "c" to "d") {
					+"inlineText"
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
					+"inlineText"
				} wrap false
			}
		}
		println(xml)
	}
}
