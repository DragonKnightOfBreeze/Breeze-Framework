package com.windea.breezeframework.core.extensions

import org.junit.*

class TodoHighlightExtensionsKtTest {
	@Test
	fun DELAYTest() {
		val abc = DELAY { "abc" }
	}
	
	@Test
	fun ISSUETest() {
		ISSUE()
	}
	
	@Test
	fun ISSUE1Test() {
		ISSUE("123")
	}
	
	@Test
	fun REGIONTest() {
	}
}
