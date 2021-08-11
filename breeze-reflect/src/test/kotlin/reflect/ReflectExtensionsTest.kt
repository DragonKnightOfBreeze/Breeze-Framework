package icu.windea.breezeframework.reflect

import icu.windea.breezeframework.core.extension.*
import kotlin.system.*
import kotlin.test.*

class ReflectExtensionsTest {
	@Test
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
		println(nameOf(A::abc.parameters[0]))
	}

	@Test
	fun testClassAndKClass() {
		measureNanoTime { A::class.simpleName }.also { println(it) }
		measureNanoTime { A::class.java.simpleName }.also { println(it) }
	}

	@Test
	fun isInstanceOfTest() {
		//assertTrue(1 isInstanceOf Int::class)
		//assertTrue(1 isInstanceOf Number::class)
		//assertTrue(1 isInstanceOf Comparable::class)
		//assertTrue(1 isInstanceOf Any::class)
		//
		//assertTrue(1.toBigInteger() isInstanceOf BigInteger::class)
		//assertTrue(1.toBigInteger() isInstanceOf Number::class)
		//assertTrue(1.toBigInteger() isInstanceOf Comparable::class)
		//assertTrue(1.toBigInteger() isInstanceOf Any::class)
	}
}

class A {
	val foo = 1
	val bar = "123"
	fun abc(name: String) {
		println("hello, $name!")
	}
}
