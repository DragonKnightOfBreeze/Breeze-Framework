package com.windea.breezeframework.game

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.reflect.extensions.*
import kotlin.system.*
import kotlin.test.*

class CoreExtensionsTest {
	@Test //TESTED OK
	fun test1() {
		println(javaTypeOf<Int>())
		println(javaTypeOf<List<String>>())
		println(javaTypeOf<Map<String, String>>())
	}

	@Test //TESTED OK
	fun testNameOf() {
		println(nameOf<A>())
		println(nameOf(A::class))
		println(nameOf(A::foo))
		println(nameOf(A::abc))
		println(nameOf(A::abc.parameters[0]))
	}

	@Test
	fun testClassAndKClass() {
		measureNanoTime { A::class.simpleName }.also { println(it) }
		measureNanoTime { A::class.java.simpleName }.also { println(it) }

	}
}

class A {
	val foo = 1
	val bar = "123"
	fun abc(name: String) {
		println("hello, $name!")
	}
}
