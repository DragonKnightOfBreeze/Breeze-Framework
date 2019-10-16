package com.windea.breezeframework.dsl.text

import com.windea.breezeframework.dsl.text.CriticMarkupInlineBuilder.cmAppend
import com.windea.breezeframework.dsl.text.CriticMarkupInlineBuilder.cmHighlight
import org.junit.*

class CriticMarkupDslTest {
	@Test
	fun test2() {
		criticMarkup {
			"""
				Hello, ${cmAppend("world")}!
				Nice to ${cmHighlight("meet")} you!
			""".trimIndent()
		}.also { println(it) }
	}
	
	@Test
	fun test3() {
		"""
			Hello, ${cmAppend("world")}!
			Nice to ${cmHighlight("meet")} you!
		""".trimIndent().also { println(it) }
	}
}
