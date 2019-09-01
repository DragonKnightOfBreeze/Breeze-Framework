package com.windea.breezeframework.core.extensions

import org.junit.*

class SystemExtensionsKtTest {
	@Test
	fun getSystemAttributes() {
		println(systemAttributes.userName)
		println(systemAttributes.userLocale)
		println(systemAttributes.userCountry)
		println(systemAttributes.userLanguage)
	}
	
	@Test
	fun eval() {
		eval<Unit> {
			"""
				print("hello world!")
			""".trimIndent()
		}
	}
	
	@Test
	fun exac() {
	}
}
