// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.misc

import icu.windea.breezeframework.core.extension.*
import org.junit.*

class ReifiedTest {
	inline fun <reified T> test() {
		println(T::class.java)
		println(javaTypeOf<T>())
	}

	@Test
	fun test1() {
		test<Int>()
		test<List<Int>>()
		val a = javaTypeOf<Int>()
		val b = javaTypeOf<String>()
		val c = javaTypeOf<List<Int>>()
		println()
	}

	inline fun <reified T> javaClassOf(): Class<T> {
		return T::class.java
	}

	@Test
	fun test2() {
		val clz: Class<Int> = javaClassOf()
	}
}
