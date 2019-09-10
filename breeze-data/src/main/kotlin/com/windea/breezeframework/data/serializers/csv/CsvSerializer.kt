package com.windea.breezeframework.data.serializers.csv

import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.reflect.extensions.*

interface CsvSerializer : Serializer {
	companion object {
		val instance: CsvSerializer = when {
			checkClassForName("com.fasterxml.jackson.dataformat.csv.CsvMapper") -> JacksonCsvSerializer
			else -> throw IllegalStateException("Please contains at least one data serializer impl in classpath.")
		}
	}
}

interface CsvSerializerConfig : SerializerConfig


