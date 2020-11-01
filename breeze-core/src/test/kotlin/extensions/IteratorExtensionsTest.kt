// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.extensions

import kotlin.test.*

class IteratorExtensionsTest {
	@Test
	fun nextTest(){
		val list = listOf(1,2,3,4,6,7)
		assertEquals(3, list.iterator().next { current, _ -> current == 3 })
		assertEquals(2, list.iterator().next { _, prev -> prev != null && prev == 1 })
		assertEquals(6, list.iterator().next { current, prev -> prev != null && current-prev == 2 })
	}
}
