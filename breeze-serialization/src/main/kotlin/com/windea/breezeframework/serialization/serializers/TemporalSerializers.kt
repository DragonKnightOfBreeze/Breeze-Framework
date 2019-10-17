package com.windea.breezeframework.serialization.serializers

import kotlinx.serialization.*
import kotlinx.serialization.internal.*
import java.time.*
import java.time.format.*
import java.time.temporal.*

sealed class TemporalSerializer<T : Temporal> : KSerializer<T> {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.temporal.Temporal")
	
	override fun serialize(encoder: Encoder, obj: T) {
		val temporal = obj.fromTemporal()
		encoder.encodeString(temporal)
	}
	
	override fun deserialize(decoder: Decoder): T {
		val string = decoder.decodeString()
		return string.toTemporal()
	}
	
	abstract fun T.fromTemporal(): String
	
	abstract fun String.toTemporal(): T
}

//NOTE remove annotation for NoSuchMethodException
class LocalDateSerializer(
	val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE
) : TemporalSerializer<LocalDate>() {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.LocalDate")
	
	override fun LocalDate.fromTemporal(): String = this.format(formatter)
	
	override fun String.toTemporal(): LocalDate = LocalDate.parse(this, formatter)
}

//NOTE remove annotation for NoSuchMethodException
class LocalTimeSerializer(
	val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME
) : TemporalSerializer<LocalTime>() {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.LocalTime")
	
	override fun LocalTime.fromTemporal(): String = this.withNano(0).format(formatter) //trim nanoSecond
	
	override fun String.toTemporal(): LocalTime = LocalTime.parse(this, formatter).withNano(0) //trim nanoSecond
}

//NOTE remove annotation for NoSuchMethodException
class LocalDateTimeSerializer(
	val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
) : TemporalSerializer<LocalDateTime>() {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("java.time.LocalDateTime")
	
	override fun LocalDateTime.fromTemporal(): String = this.withNano(0).format(formatter) //trim nanoSecond
	
	override fun String.toTemporal(): LocalDateTime = LocalDateTime.parse(this, formatter).withNano(0) //trim nanoSecond
}
