// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.windea.breezeframework.serialization.components.*
import com.windea.breezeframework.serialization.extensions.*
import kotlin.test.*

class SerializerTest {
	@Test
	fun test1() {
		val a = mapOf("name" to "Windea", "gender" to "Female")
		val b = """{"name":"Windea","gender":"Female"}""".trimIndent()
		assertEquals(a, b.deserializeBy(DataType.Json))
	}

	@Test
	fun test2(){
		val a = mapOf("name" to "Windea", "gender" to "Female")
		val b = """{"name":"Windea","gender":"Female"}""".trimIndent()
		assertEquals(b, a.serializeBy<Map<*,*>>(DataType.Json))
	}
}
