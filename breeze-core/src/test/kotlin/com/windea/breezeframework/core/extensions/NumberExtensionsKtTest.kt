package com.windea.breezeframework.core.extensions

import org.junit.*

class NumberExtensionsKtTest {
	@Test
	fun factorial() {
		println(1.factorial())
		println(100.factorial())
		println(1000.factorial())
		//println(Long.MAX_VALUE.factorial()) //太恐怖了。。。
	}

	@Test
	fun cumulative() {
		println(1.cumulative())
		println(100.cumulative())
		println(1000.cumulative())
		//println(Long.MAX_VALUE.cumulative()) //虽然最终能够得出结果，但是仍然很恐怖
	}
}
