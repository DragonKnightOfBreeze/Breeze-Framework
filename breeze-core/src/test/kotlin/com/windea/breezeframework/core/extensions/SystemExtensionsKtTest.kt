package com.windea.breezeframework.core.extensions

import org.junit.*

class SystemExtensionsKtTest {
	@Test //TESTED
	fun getSystemAttributes() {
		println(systemProperties.userName)
		println(systemProperties.userCountry)
		println(systemProperties.userLanguage)
	}
	
	@Test
	fun eval() {
		println("hello")
		val a = eval<Int>("js") { "2+2" }
		println(a)
		val b = eval<Int> { "2+2" } //why null??
		println(b)
		
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
		println(nearestStackInfo())
	}
}
