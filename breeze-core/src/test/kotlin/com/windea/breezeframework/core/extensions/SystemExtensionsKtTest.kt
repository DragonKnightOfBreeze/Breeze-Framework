package com.windea.breezeframework.core.extensions

import org.junit.*

class SystemExtensionsKtTest {
	@Test //TESTED
	fun getSystemAttributes() {
		println(systemAttributes.userName)
		println(systemAttributes.userCountry)
		println(systemAttributes.userLanguage)
	}
	
	@Test
	fun eval() {
		println("hello")
		eval<Unit> {
			"""
				print("hello world!")
			""".trimIndent()
		}
	}
	
	@Test
	fun exac() {
	}
	
	@Test //TESTED
	fun getNearestStackTrace() {
		//com.windea.breezeframework.core.extensions.SystemExtensionsKtTest.getNearestStackTrace(SystemExtensionsKtTest.kt:42)
		inlineFun()
	}
	
	@Suppress("NOTHING_TO_INLINE")
	inline fun inlineFun() {
		println(nearestStackTrace())
	}
}
