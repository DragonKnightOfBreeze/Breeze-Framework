package com.windea.breezeframework.core.extension

import org.junit.Test
import kotlin.test.*

class DataClassExtensionsTest {
	@Test
	fun testEquals() {
		val foo1 = Foo(123, "abc")
		val foo2 = Foo(123, "abc")
		val foo3 = Foo(233, "abc")
		assertTrue(equalsBy(foo1, foo2) { arrayOf(a, b) })
		assertFalse(equalsBy(foo1, foo3) { arrayOf(a, b) })
	}

	@Test
	fun testToString() {
		val foo = Foo(123, "abc")
		assertEquals("Foo(a=123, b=abc)", toStringByReference(foo) { arrayOf(::a, ::b) })
	}

	class Foo(val a: Int, val b: String)
}
