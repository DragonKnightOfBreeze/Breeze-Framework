// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model.deprecated

import icu.windea.breezeframework.core.model.Identifiable
import icu.windea.breezeframework.core.model.Identifiable.Companion.delegate
import org.junit.*

class IdentifiableTest {
	@Test
	fun test1() {
		println(Foo(1, "a") == Foo(1, "a"))
		println(Foo(1, "a") == Foo(1, "b"))
		println(Foo(1, "a").equals(Bar(1, "a")))
		println(Foo(1, "a").equals(Bar(1, "b")))
	}
}

class Foo(override val id: Long, val name: String) : Identifiable<Long> by delegate(id)

class Bar(override val id: Long, val name: String) : Identifiable<Long> by delegate(id)
