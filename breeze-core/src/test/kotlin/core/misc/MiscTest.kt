// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.misc

import org.junit.*

class MiscTest {
	@Test
	fun testSort() {
		val l1 = listOf(1, 2, 3)
		val l2 = listOf("1", "2", "3")
		println(listOf(1, 2, 3, 4, 5).sortedByList(listOf("1", "2", "3", "4", "5")) { it.toString() })
		println(listOf(1, 2, 3, 4, 5).sortedByList(listOf("1", "3", "5", "4", "2")) { it.toString() })
		println(listOf(1, 2, 3, 4, 5).sortedByList(listOf("3", "5")) { it.toString() })
	}

	fun <T, E> List<T>.sortedByList(list: List<E>, selector: (T) -> E): List<T> {
		val unsortedIndex = list.size
		return sortedBy {
			val index = list.indexOf(selector(it))
			if(index == -1) unsortedIndex else index
		}
	}
}
