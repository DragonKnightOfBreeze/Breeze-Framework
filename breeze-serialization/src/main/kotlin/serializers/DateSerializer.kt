package com.windea.breezeframework.serialization.serializers

import kotlinx.serialization.*
import kotlinx.serialization.internal.*
import java.text.*
import java.util.*

@Serializer(Date::class)
class DateSerializer(format: String = "yyyy/MM/dd HH:mm:ss") : KSerializer<Date> {
	private val dateFormat: DateFormat = SimpleDateFormat(format)

	override val descriptor: SerialDescriptor = StringDescriptor.withName("java.util.Date")

	override fun serialize(encoder: Encoder, obj: Date) {
		encoder.encodeString(dateFormat.format(obj))
	}

	override fun deserialize(decoder: Decoder): Date {
		return dateFormat.parse(decoder.decodeString())
	}
}
