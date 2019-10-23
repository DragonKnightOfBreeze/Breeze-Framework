package com.windea.breezeframework.dsl.text

import org.junit.*

class CriticMarkupTextDslTest {
	@Test //TESTED SO EASY
	fun test2() {
		criticMarkupText {
			"""
				Hello, ${append("world")}!
				Nice to ${highlight("meet")} you!
			""".trimIndent()
		}.also { println(it) }
	}
}
