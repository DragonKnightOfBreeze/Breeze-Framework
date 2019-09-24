package com.windea.breezeframework.data.extensions

import org.junit.*
import org.junit.Assert.*

//TESTED

class CoreExtensionsKtTest {
	@Test
	fun testEquals() {
		val foo1 = Foo(123, "abc")
		val foo2 = Foo(233, "abc")
		val foo3 = Foo(123, "abc")
		assertNotEquals(foo1, foo2)
		assertEquals(foo1, foo3)
	}
	
	@Test
	fun testToString() {
		val foo = Foo(123, "abc")
		assertEquals(foo.toString(), "Foo(a=123, b='abc')")
	}
}

class Foo(val a: Int, val b: String) {
	override fun equals(other: Any?): Boolean {
		return equalsByPredicate(this, other) { a, b -> a.a == b.a && a.b == b.b }
	}
	
	override fun hashCode(): Int {
		return hashcodeBySelect(this) { arrayOf(it.a, it.b) }
	}
	
	override fun toString(): String {
		return toStringBySelect(this) { arrayOf(it::a to it.a, it::b to it.b) }
	}
}
