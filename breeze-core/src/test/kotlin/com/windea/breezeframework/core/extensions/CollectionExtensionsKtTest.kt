package com.windea.breezeframework.core.extensions

import org.junit.*

class CollectionExtensionsKtTest {
	@Test //TESTED
	fun repeatExtensionTest() {
		println(listOf(1, 2, 3).repeat(3))
		println(listOf(1, 2, 3).flatRepeat(3))
		println(listOf(1, 2, 3).repeatChunked(3))
	}
	
	@Test //TESTED
	fun dropBlankTest() {
		println(listOf("123").dropBlank())
		println(listOf("", "123").dropBlank())
		println(listOf("", "", "123").dropBlank())
		println(listOf("123", "", "123").dropBlank())
		println(listOf("", "123", "", "123").dropBlank())
	}
	
	@Test //TESTED
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
}
