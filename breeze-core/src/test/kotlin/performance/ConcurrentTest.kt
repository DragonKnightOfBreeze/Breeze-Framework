// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.performance

import java.util.concurrent.*
import kotlin.test.*

class ConcurrentTest {
	@Test
	fun testFuture() {
		val executor = Executors.newFixedThreadPool(5);
		println(System.nanoTime())
		val s1 = executor.submit { Thread.sleep(1000) }.get()
		val s2 = executor.submit { Thread.sleep(2000) }.get()
		val s3 = executor.submit { Thread.sleep(3000) }.get()
		println(System.nanoTime())

	}
}
