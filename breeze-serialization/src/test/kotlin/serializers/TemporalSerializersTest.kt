package com.windea.breezeframework.serialization.serializers

import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.junit.*
import java.time.*

class TemporalSerializersTest {
	@Test
	@ImplicitReflectionSerializer
	fun test() {
		val map = Json.stringify(LocalDateTimeData(LocalDateTime.now()))
		println(map)
		
		val data = Json.parse<LocalDateTimeData>(map)
		println(data)
	}
}

@Serializable
data class LocalDateTimeData(@Serializable(LocalDateTimeSerializer::class) val date: LocalDateTime)
