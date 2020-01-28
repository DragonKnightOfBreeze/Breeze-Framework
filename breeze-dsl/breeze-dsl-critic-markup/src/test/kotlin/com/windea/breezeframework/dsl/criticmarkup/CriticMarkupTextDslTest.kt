package com.windea.breezeframework.dsl.criticmarkup

import org.junit.*

class CriticMarkupTextDslTest {
	@Test SO EASY
	fun test2() {
		criticMarkupText {
			"""
				Hello, ${append("world")}!
				Nice to ${highlight("meet")} you!
			""".trimIndent()
		}.also { println(it) }
	}
}
