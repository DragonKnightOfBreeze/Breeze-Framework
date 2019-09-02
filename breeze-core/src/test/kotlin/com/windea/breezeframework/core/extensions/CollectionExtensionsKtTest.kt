package com.windea.breezeframework.core.extensions

import org.junit.*

class CollectionExtensionsKtTest {
	@Test //TESTED
	fun repeatExtensionTest() {
		println(listOf(1, 2, 3).repeat(3))
		println(listOf(1, 2, 3).flatRepeat(3))
		println(listOf(1, 2, 3).repeatChunked(3))
	}
}
