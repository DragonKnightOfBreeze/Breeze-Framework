package com.windea.breezeframework.core.extensions

import org.junit.*

class AnyExtensionsTest {
	@Test
	fun testCast() {
		(123 as Number).javaClass.andPrintln()
		(123 as Number).javaClass.andPrintln()
	}
}
