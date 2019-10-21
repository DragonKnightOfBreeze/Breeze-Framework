package com.windea.breezeframework.serialization.serializers

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import kotlin.test.*

class RangeSerializersTest {
	@Test
	@ImplicitReflectionSerializer
	fun test1() {
		val map = Json.stringify(RangeData(1, "2", 1..2))
		println(map)
		
		val data = Json.parse<RangeData>(map)
		println(data)
	}
	
	@Test
	@ImplicitReflectionSerializer
	fun test2() {
		val map = Json.stringify(FloatingPointRangeData(1.0..2.0))
		println(map)
		
		val data = Json.parse<FloatingPointRangeData>(map)
		println(data)
	}
}

@Serializable
data class RangeData(val first: Int, val second: String, @Serializable(IntRangeSerializer::class) val range: IntRange)

@Serializable
data class FloatingPointRangeData(@Serializable(FloatingPointRangeSerializer::class) val range: ClosedFloatingPointRange<Double>)
