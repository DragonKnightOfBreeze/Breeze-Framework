package com.windea.breezeframework.serialization.serializers

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.util.*
import kotlin.test.*

class DateSerializerTest {
	@Test
	@ImplicitReflectionSerializer
	fun test1() {
		val map = Json.stringify(DateData(Date()))
		println(map)

		val data = Json.parse<DateData>(map)
		println(data)
	}
}

@Serializable
data class DateData(@Serializable(DateSerializer::class) val date: Date)
