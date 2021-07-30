package icu.windea.breezeframework.core.extension

import org.junit.*

class SystemExtensionsTest {
	@Test
	fun evalTest() {
		eval<Int>("javascript") { "1+2" }.also { println(it) }
		eval<Int>("JavaScript") { "1+2" }.also { println(it) }
		eval<Int>("js") { "1+2" }.also { println(it) }
	}

	@Test
	fun execTest() {
	}
}
