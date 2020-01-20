package com.windea.breezeframework.game

import kotlin.reflect.*
import kotlin.test.*

class KTypeExtensionsTest {
	@Test
	fun test1() {
		typeOf<List<String>>().also { println(it) }
		test2(1)
	}

	inline fun <reified T> test2(value: T) {
		typeOf<List<T>>().also { println(it) }
	}
}
