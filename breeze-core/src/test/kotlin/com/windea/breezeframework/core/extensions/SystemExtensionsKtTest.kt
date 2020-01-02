package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.consts.*
import org.junit.*

class SystemExtensionsKtTest {
	@Test //TESTED
	fun getSystemAttributes() {
		println(SystemProperties.userName)
		println(SystemProperties.userCountry)
		println(SystemProperties.userLanguage)
	}

	@Test //TESTED
	fun evalTest() {
		eval<Int>("javascript") { "1+2" }.also { println(it) }
		eval<Int>("JavaScript") { "1+2" }.also { println(it) }
		eval<Int>("js") { "1+2" }.also { println(it) }
	}

	@Test
	fun exacTest() {
	}
}
