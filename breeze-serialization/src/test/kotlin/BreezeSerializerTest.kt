// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.windea.breezeframework.serialization.component.*
import com.windea.breezeframework.serialization.extension.*
import kotlin.system.*
import kotlin.test.*

class BreezeSerializerTest {
	@Test
	fun jsonSerializeTest() {
		val a = mapOf(
			"name" to "Windea",
			"gender" to "Female",
			"age" to 3000,
			"weapon" to arrayOf("BreezesLanding", "BreathOfBreeze")
		)
		println(a.serializeBy(BreezeSerializer.BreezeJsonSerializer()))
	}

	//1799108400
	//174341900
	//320961700
	//241256700
	@Test
	fun jsonSerializePerformanceTest() {
		val a = mapOf(
			"name" to "Windea",
			"gender" to "Female",
			"age" to 3000,
			"weapon" to arrayOf("BreezesLanding", "BreathOfBreeze")
		)
		val b = List(100) { a }
		val s1: String
		val s2: String
		val s3: String
		val s4: String
		println(measureNanoTime { s1 = b.serializeBy(JsonSerializer.BreezeJsonSerializer()) })
		println(measureNanoTime { s2 = b.serializeBy(JsonSerializer.JacksonJsonSerializer()) })
		println(measureNanoTime { s3 = b.serializeBy(JsonSerializer.GsonSerializer()) })
		println(measureNanoTime { s4 = b.serializeBy(JsonSerializer.FastJsonSerializer()) })
		println(s1)
		println(s2)
	}
}
