package com.windea.breezeframework.core.extensions

import org.junit.*
import kotlin.contracts.*

//Ignore Tests

class CoreExtensionsKtTest {
	@Test
	fun with() {
	}
	
	@Test
	fun clamp() {
	}
	
	@Test
	fun reject() {
	}
	
	@Test
	fun reject1() {
	}
	
	@Test
	fun nearestStackInfo() {
		DELAY("delay!")
		FIXME("fix me!")
		TODO("todo!")
	}
	
	@Test
	fun nearestLogger() {
	}
	
	@ExperimentalContracts
	@Test //TESTED
	fun onceTest() {
		//a, a
		once { println("a") }
		once { println("b") }
		once { println("c") }
		once(true) { println("a") }
		once { println("b") }
	}
}
