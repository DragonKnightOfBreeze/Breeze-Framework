package com.windea.breezeframework.core.extensions

import kotlin.test.*

class CollectionExtensionsTest {
	@Test
	fun dropBlankTest() {
		assertEquals(1, listOf("123").dropBlank().size)
		assertEquals(1, listOf("", "123").dropBlank().size)
		assertEquals(1, listOf("", "", "123").dropBlank().size)
		assertEquals(3, listOf("123", "", "123").dropBlank().size)
		assertEquals(3, listOf("", "123", "", "123").dropBlank().size)
	}

	@Test
	fun fillToSizeTest() {
		val list = listOf("1", "2", "3")
		println(list.toMutableList().also { it.fill("1") })
		println(list)
		println(list.toMutableList().also { it.fillRange(1..2, "1") })
		println(list)
		println(list.fillStart(5, "1"))
		println(list)
		println(list.fillEnd(5, "1"))
		println(list)
	}

	@Test
	fun deepFlattenTest() {
		val list = listOf(
			listOf(1, 2, 3),
			listOf(11, 22, 33),
			listOf(111, 222, 333, listOf(444)),
			mapOf("a" to listOf("a"), "b" to listOf("b"))
		)
		list.deepFlatten<Any?>().also { println(it) }
		list.deepFlatten<Any?>(1).also { println(it) }
		list.deepFlatten<Any?>(2).also { println(it) }
		list.deepFlatten<Any?>(3).also { println(it) }
		list.deepFlatten<Any?>(4).also { println(it) }
	}

	@Test
	fun ifNotEmptyTest() {
		assertTrue(listOf<Int>().ifEmpty { listOf(123) }.isNotEmpty())
		assertTrue(arrayOf(1, 2, 3).ifNotEmpty { it.sliceArray(1..2) }.isNotEmpty())
		assertTrue(listOf(1, 2, 3).ifNotEmpty { it.slice(1..2) }.isNotEmpty())
		assertTrue(mapOf(1 to 1).ifNotEmpty { it.mapValues { (_, v) -> v + 1 } }.isNotEmpty())
	}

	@Test
	fun collapseTest() {
		val list = listOf("# 1", "## 1.1", "### 1.1.1", "## 1.2", "### 1.2.1", "### 1.2.2", "# 2")
		val list2 = list.collapse { it.count { e -> e == '#' } }
		println(list2)
	}

	@Test //DONE
	fun expandTest() {
		val list = listOf<Any?>(1, listOf(2, 3, 4), listOf(5, listOf(6, listOf(7))), 8)
		val flatList = list.flatMap { if(it is List<*>) it else listOf(it) }
		val expendList = list.expand<Any?> { if(it is List<*>) it else listOf() }
		println(list)
		println(flatList)
		println(expendList)
		println(list.deepFlatten<Any>())
		println(list.deepFlatten<Any>(1))
		println(list.deepFlatten<Any>(2))
		println(list.deepFlatten<Any>(3))
		println(list.deepFlatten<Any>(4))
	}
}
