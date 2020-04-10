package com.windea.breezeframework.serializer

import org.intellij.lang.annotations.*
import kotlin.test.*

class LanguageAnnotationTest {
	@Language("Kotlin")
	private fun foo(arg1: String) {
		println(arg1)
	}

	@Test
	fun test1() {
		foo("123")
	}
}
