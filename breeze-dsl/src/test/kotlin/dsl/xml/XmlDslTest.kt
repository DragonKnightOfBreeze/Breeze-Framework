package icu.windea.breezeframework.dsl.xml

import kotlin.test.*

class XmlDslTest {
	@Test
	fun test() {
		val xml = xmlDsl {
			comment("comment")
			comment("""
				comment
				second line
			""".trimIndent())
			"element"{
				+"inlineText"
				+"""
					inlineText
					inlineText
				""".trimIndent()
				element("e") {
					text("element inlineText")
				}
				"element"("a" to "b", "c" to "d") {
					+"inlineText"
				}
			}
		}
		println(xml)
	}
}
