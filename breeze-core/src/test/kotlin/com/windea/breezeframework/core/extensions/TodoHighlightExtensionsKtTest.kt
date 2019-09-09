package com.windea.breezeframework.core.extensions

import org.junit.*

class TodoHighlightExtensionsKtTest {
	@Test
	fun DELAYTest() {
		val abc = DELAY { "abc" }
		println(abc)
	}
	
	@Test
	fun FixmeTest() {
		FIXME()
	}
	
	@Test
	fun Fixme1Test() {
		FIXME("123")
	}
	
	@Test
	fun REGIONTest() {
	}
}
