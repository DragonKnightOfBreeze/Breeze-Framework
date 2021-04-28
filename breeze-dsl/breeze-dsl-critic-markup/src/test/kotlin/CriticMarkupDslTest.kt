package icu.windea.breezeframework.dsl.criticmarkup

import org.junit.*
import kotlin.system.*

class CriticMarkupDslTest {
	@Test
	fun test1() {
		criticMarkupDsl {
			"""
				Hello, ${append("world")}!
				Nice to ${highlight("meet")} you!
			""".trimIndent()
		}.also { println(it) }
	}

	@Test
	fun test2() {
		measureTimeMillis {
			repeat(100000) {
				criticMarkupDsl {
					"""
				Hello, ${append("world")}!
				Nice to ${highlight("meet")} you!
			""".trimIndent()
				}.toString()
			}
		}.also { println(it) }
	}
}
