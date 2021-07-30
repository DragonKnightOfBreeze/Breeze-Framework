// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization

import kotlin.test.*

class SerializerTest {
	@Test
	fun test1() {
		val a = mapOf("name" to "Windea", "gender" to "Female")
		val b = """{"name":"Windea","gender":"Female"}""".trimIndent()
		assertEquals(a, b.deserializeDataBy(DataFormats.Json))
	}

	@Test
	fun test2() {
		val a = mapOf("name" to "Windea", "gender" to "Female")
		val b = """{"name":"Windea","gender":"Female"}""".trimIndent()
		assertEquals(b, a.serializeDataBy<Map<*, *>>(DataFormats.Json))
	}
}
