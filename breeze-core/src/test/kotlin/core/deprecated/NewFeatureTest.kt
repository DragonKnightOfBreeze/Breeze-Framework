package icu.windea.breezeframework.core.deprecated

import kotlin.test.*

class NewFeatureTest {
	@Test
	fun buildListTest() {
		val list = buildList {
			this.add(1)
			this.add(2)
			this.add(3)
		}
		println(list)
	}

	@Test
	fun scanTest() {
		val sequence1 = listOf(1, 2, 3).fold(0) { a, b -> a + b } //6
		val sequence2 = listOf(1, 2, 3).scan(0) { a, b -> a + b } //[0, 1, 3, 6]
		//val sequence3 = listOf(1, 2, 3).scanReduce { a, b -> a + b } //[1, 3, 6]
		println(sequence1)
		println(sequence2)
		//println(sequence3)
	}
}
