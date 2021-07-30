package icu.windea.breezeframework.game

import kotlin.reflect.*
import kotlin.test.*

class KTypeExtensionsTest {
	@Test
	fun test1() {
		typeOf<List<String>>().also { println(it) }
		test2<Int>()
	}

	inline fun <reified T> test2() {
		typeOf<List<T>>().also { println(it) }
	}
}
