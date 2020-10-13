package com.windea.breezeframework.core.extensions

import org.junit.*

class AnyExtensionsTest {
	@Test
	fun testConvert() {
		"123".convert<Int>().andPrintln()
		"123".convertOrNull<Int>().andPrintln()
		123.convertOrNull<String>().andPrintln()
		123.convertOrNull<String>().andPrintln()
	}

	@Test
	fun testCast() {
		(123 as Number).javaClass.andPrintln()
		(123 as Number).javaClass.andPrintln()
	}
}
