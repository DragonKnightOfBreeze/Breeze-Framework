package com.windea.breezeframework.core.extensions

import kotlin.test.*

class CollectionExtensionsKtTest {
	@Test
	fun repeatExtensionTest() {
		println(listOf(1, 2, 3).repeat(3))
		println(listOf(1, 2, 3).repeatOrdinal(3))
		//println(listOf(1, 2, 3).repeatChunked(3))
	}

	@Test
	fun dropBlankTest() {
		assertEquals(1, listOf("123").dropBlank().size)
		assertEquals(1, listOf("", "123").dropBlank().size)
		assertEquals(1, listOf("", "", "123").dropBlank().size)
		assertEquals(3, listOf("123", "", "123").dropBlank().size)
		assertEquals(3, listOf("", "123", "", "123").dropBlank().size)
	}

	@Test
	fun fillToSize() {
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
	fun deepGetAndSet() {
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

		assertFailsWith<IllegalArgumentException> {
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
	fun deepQuery() {
		val list = listOf(
			listOf(1, 2, 3),
			listOf(11, 22, 33),
			listOf(111, 222, 333),
			mapOf("a" to listOf("a"), "b" to listOf("b"))
		)
		assertFailsWith<IllegalArgumentException> {
			println(list.deepQuery<Any?>("/"))
		}
		println(list.deepQuery<Int>("/[]"))
		println(list.deepQuery<Any>("/[]"))
		println(list.deepQuery<List<Int>>("/0..2"))
		println(list.deepQuery<Int>("/0..2/1"))
		println(list.deepQuery<Int>("/0..2/1..2"))
		println(list.deepQuery<List<String>>("/3/{}"))
		println(list.deepQuery<String>("/3/re:\\w/0"))
	}

	@Test
	fun deepFlatten() {
		val list = listOf(
			listOf(1, 2, 3),
			listOf(11, 22, 33),
			listOf(111, 222, 333, listOf(444)),
			mapOf("a" to listOf("a"), "b" to listOf("b"))
		)
		list.deepFlatten().also { println(it) }
		list.deepFlatten(1).also { println(it) }
		list.deepFlatten(2).also { println(it) }
		list.deepFlatten(3).also { println(it) }
		list.deepFlatten(4).also { println(it) }
	}

	@Test
	fun ifNotEmptyTest() {
		assertTrue(listOf<Int>().ifEmpty { listOf(123) }.isNotEmpty())
		assertTrue(arrayOf(1, 2, 3).ifNotEmpty { it.sliceArray(1..2) }.isNotEmpty())
		assertTrue(listOf(1, 2, 3).ifNotEmpty { it.slice(1..2) }.isNotEmpty())
		assertTrue(mapOf(1 to 1).ifNotEmpty { it.mapValues { (_, v) -> v + 1 } }.isNotEmpty())
	}

	@Test
	fun typeCheckTest() {
		assertTrue(arrayOf("a").isArrayOf<String>())
		assertTrue(listOf("a").isIterableOf<String>())
		assertTrue(mapOf("a" to "a").isMapOf<String, String>())
		assertTrue(sequenceOf("a").isSequenceOf<String>())
	}
}
