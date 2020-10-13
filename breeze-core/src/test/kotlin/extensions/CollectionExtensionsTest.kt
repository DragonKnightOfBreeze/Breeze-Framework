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
	fun deepGetAndSetTest() {
		val array = arrayOf(0, 1, 2, arrayOf(0, arrayOf(0, 1), 2))
		val list = listOf(0, arrayOf(0, 1), 2, listOf(0, 1, 2), 4, mapOf("a" to 0))
		val intMutableList = mutableListOf(0, 1, 2)
		val mutableList = mutableListOf(0, intMutableList, mutableMapOf("a" to 0))

		mutableList.deepSet("/2/a", "abc")
		println(mutableList)

		array.deepGet<Int>("/0").also { println(it) }
		array.deepGet<Int>("/3/0").also { println(it) }
		list.deepGet<Int>("/0").also { println(it) }
		list.deepGet<Int>("/1/0").also { println(it) }
		list.deepGet<Int>("/3/0").also { println(it) }
		list.deepGet<Int>("/5/a").also { println(it) }

		println()

		assertFails {
			array.deepSet("/3/1/0", "abc")
			array.deepGet<Int>("/3/1/0").also { println(it) }
			println(array.contentDeepToString())
		}

		println()

		array.deepSet("/3/1/0", 111)
		array.deepGet<Int>("/3/1/0").also { println(it) }
		println(array.contentDeepToString())

		println()

		mutableList.deepSet("/0", 111)
		mutableList.deepGet<Int>("/0").also { println(it) }
		mutableList.deepSet("/1/0", 111)
		mutableList.deepGet<Int>("/1/0").also { println(it) }
		mutableList.deepSet("/2/a", 111)
		mutableList.deepGet<Int>("/2/a").also { println(it) }
		println(mutableList)

		mutableList.deepSet("/0", "abc")
		println(mutableList)

		//返回值泛型会被擦除，因此这里会在方法外抛出ClassCastException
		//mutableList.deepGet<String>("/0").also { println(it) }
		assertFailsWith<IllegalArgumentException> {
			mutableList.deepSet("/0/0", 1)
		}
		//输入值类型会被擦除，因此这里不会抛出异常，除非需要进行强制转化
		//mutableList.deepSet("/1/0", "abc")
		//mutableList.deepGet<Any>("/1/0").also { println(it) }
		//println(mutableList)
		//
		//println(intMutableList[0])
		//println(intMutableList)
	}

	@Test
	fun deepQueryTest() {
		assertEquals(listOf(2),listOf(1,2,3).deepQueryBy("/2"))
		assertEquals(listOf(2),mapOf("a" to 1, "b" to 2, "c" to 3).deepQueryBy("/b"))
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
