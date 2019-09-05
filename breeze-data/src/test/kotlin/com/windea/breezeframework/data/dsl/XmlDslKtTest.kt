package com.windea.breezeframework.data.dsl

import kotlin.test.*

class XmlDslKtTest {
	@Test
	fun test() {
		val xml = Dsl.xml {
			-"comment"
			-"""
				comment
				second line
			""".trimIndent() wrap true
			"       element"{
				-"comment"
				+"text"
				+"""
					text
					text
				""".trimIndent()
				"element"("a" to "b", "c  " to "d") {
					+"text"
				}
				comment("""
					comment
					comment
				""".trimIndent())
				comment("""
					comment
					comment
				""".trimIndent()).wrap().indent()
				comment("""
					comment
					comment
				""".trimIndent()).wrap()
				comment("""
					comment
					comment
				""".trimIndent()).indent()
				comment("comment").indent().wrap()
				comment("comment").wrap()
				"element"{
					+"text"
				}.unwrap()
			}
		}
		println(xml)
	}
}
