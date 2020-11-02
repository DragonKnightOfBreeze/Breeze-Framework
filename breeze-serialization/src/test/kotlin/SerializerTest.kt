// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import kotlin.test.*

class SerializerTest {
	@Test
	fun test1() {
		val a = mapOf("name" to "Windea", "gender" to "Female")
		val b = """{"name":"Windea","gender":"Female"}""".trimIndent()
		assertEquals(a, b.deserializeBy(DataType.Json))
		assertEquals(b, a.serializeBy<Map<*,*>>(DataType.Json))
	}
}
