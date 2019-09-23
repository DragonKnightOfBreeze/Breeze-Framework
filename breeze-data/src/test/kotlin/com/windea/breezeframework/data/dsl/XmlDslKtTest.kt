package com.windea.breezeframework.data.dsl

import com.windea.breezeframework.data.dsl.text.*
import kotlin.test.*

//TESTED VERY NICE!

class XmlDslKtTest {
	@Test
	fun test() {
		val xml = xml {
			-"comment"
			-"""
				comment
				second line
			""".trimIndent() wrap true
			"element"{
				//"attr1"("value1")
				
				-"comment"
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
