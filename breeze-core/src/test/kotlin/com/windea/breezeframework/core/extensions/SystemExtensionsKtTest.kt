package com.windea.breezeframework.core.extensions

import org.junit.*

class SystemExtensionsKtTest {
	@Test //TESTED
	fun getSystemAttributes() {
		println(SystemProperties.userName)
		println(SystemProperties.userCountry)
		println(SystemProperties.userLanguage)
	}
	
	//@Test
	//fun eval() {
	//	println("hello")
	//	val a = eval<Int>("js") { "2+2" }
	//	println(a)
	//	val b = eval<Int> { "2+2" } //why null??
	//	println(b)
	//}
	
	@Test
	fun exac() {
	}
}
