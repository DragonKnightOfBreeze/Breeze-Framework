package com.windea.breezeframework.game

import com.windea.breezeframework.reflect.extensions.*
import kotlin.test.*

class CoreExtensionsTest {
	@Test //TESTED OK
	fun test1() {
		println(javaTypeOf<Int>())
		println(javaTypeOf<List<String>>())
		println(javaTypeOf<Map<String, String>>())
	}
	
	@Test
	fun testNameOf() {
		println(nameOf<A>())
		println(nameOf(A::class))
		println(nameOf(A::foo))
		println(nameOf(A::abc))
		val a = A::abc.parameters[0]
		println(nameOf(A::abc.parameters[0]))
	}
}

class A {
	val foo = 1
	val bar = "123"
	fun abc(name: String) {
		println("hello, $name!")
	}
}
