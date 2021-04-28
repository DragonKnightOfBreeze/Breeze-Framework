// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.windea.breezeframework.serialization.serializer.*
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
			"weapon" to listOf("BreezesLanding", "BreathOfBreeze"),
			"belong" to mapOf(
				"Country" to "Invoka",
				"Organization" to "BreezeKnights"
			)
		)
		println(a.serializeBy(BreezeJsonSerializer()))
	}

	//3~10倍 性能差距到底是怎么来的？
	//1458238000
	//274115400
	//537588500
	//342167900
	@Test
	fun jsonSerializePerformanceTest() {
		val a = mapOf(
			"name" to "Windea",
			"gender" to "Female",
			"age" to 3000,
			"weapon" to arrayOf("BreezesLanding", "BreathOfBreeze")
		)
		val b = Array(100) { a }
		val s1:String
		val s2:String
		println(measureNanoTime {s1=  b.serializeBy(BreezeJsonSerializer()) })
		println(measureNanoTime { s2= b.serializeBy(JacksonJsonSerializer()) })
		println(measureNanoTime { b.serializeBy(GsonSerializer()) })
		println(measureNanoTime { b.serializeBy(FastJsonSerializer()) })
		println(measureNanoTime { buildString { append(s1) } })
		println(s1 == s2)
	}
}
