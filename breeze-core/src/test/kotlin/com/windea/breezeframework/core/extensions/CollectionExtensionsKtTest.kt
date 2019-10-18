package com.windea.breezeframework.core.extensions

import org.junit.*

class CollectionExtensionsKtTest {
	@Test //TESTED
	fun repeatExtensionTest() {
		println(listOf(1, 2, 3).repeat(3))
		println(listOf(1, 2, 3).flatRepeat(3))
		println(listOf(1, 2, 3).repeatChunked(3))
	}
	
	@Test
	fun dropBlankTest() {
		println(listOf("123").dropBlank())
		println(listOf("", "123").dropBlank())
		println(listOf("", "", "123").dropBlank())
		println(listOf("123", "", "123").dropBlank())
		println(listOf("", "123", "", "123").dropBlank())
	}
	
	@Test //TESTED
	fun fillToSize() {
		val list = mutableListOf("1", "1", "1")
		println(list.fillEnd(2, "1"))
		println(list.fillEnd(3, "1"))
		println(list.fillEnd(4, "1"))
	}
}
