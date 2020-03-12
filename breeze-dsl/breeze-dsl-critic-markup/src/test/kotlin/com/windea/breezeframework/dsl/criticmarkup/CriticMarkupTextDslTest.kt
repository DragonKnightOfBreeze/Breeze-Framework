package com.windea.breezeframework.dsl.criticmarkup

import org.junit.*
import kotlin.system.*

class CriticMarkupTextDslTest {
	@Test
	fun test1() {
		criticMarkupText {
			"""
				Hello, ${append("world")}!
				Nice to ${highlight("meet")} you!
			""".trimIndent()
		}.also { println(it) }
	}

	@Test
	fun test2() {
		//110467199 内联block构建方法 ~=
		//127938799 不内联构建方法
		measureNanoTime {
			criticMarkupText {
				"""
				Hello, ${append("world")}!
				Nice to ${highlight("meet")} you!
			""".trimIndent()
			}.toString()
		}.also { println(it) }
	}
}
