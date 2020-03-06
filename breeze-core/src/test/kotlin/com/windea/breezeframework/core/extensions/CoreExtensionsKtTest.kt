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
		DELAY()
		FIXME()
		TODO()
	}

	@Test
	fun nearestLogger() {
	}

	@ExperimentalContracts
	@Test
	fun onceTest() {
		//a, a
		once { println("a") }
		once { println("b") }
		once { println("c") }
		once(true) { println("a") }
		once { println("b") }
	}
}
