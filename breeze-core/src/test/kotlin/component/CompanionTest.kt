// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import org.junit.*

open class CompanionTest {
	@Test
	fun testInit(){
		println(AAA.A.a)
	}
}

interface C<T:AAA> {

}

interface AAA {
	companion object Foo:C<AAA> {
		private val list = mutableListOf<Any>()

		init {
			list.add(A)
		}
	}

	object A : AAA {
		val a = A::class.java
	}
}
