package com.windea.breezeframework.core

import com.windea.breezeframework.core.dsl.*
import com.windea.breezeframework.core.dsl.data.*
import org.junit.*

class DslTests {
	@Test
	fun xmlDslTest() {
		val str = Dsl.xml {
			comment("123456")
			element("123", "a" to 1) {
				comment("333").n()
				element("abc")
				element("a") { text("text") }
				element("a") { +"text2" }.n(false)
			}
		}.toString()
		//<!--123456-->
		//<123 a="1">
		//  <!--
		//    333
		//  -->
		//  <abc></abc>
		//  <a>
		//    text
		//  </a>
		//  <a>text2</a>
		//</123>
		println(str)
	}
}
