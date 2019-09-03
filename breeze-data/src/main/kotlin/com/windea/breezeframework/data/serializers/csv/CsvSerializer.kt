package com.windea.breezeframework.data.serializers.csv

import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.reflect.extensions.*

interface CsvSerializer<S, C> : DataSerializer<S, C>, CsvLoader, CsvDumper {
	override val dataType: DataType get() = DataType.Csv
	
	companion object {
		val instance: CsvSerializer<*, *> by lazy {
			when {
				checkClassForName("com.fasterxml.jackson.dataformat.csv.CsvMapper") -> JacksonCsvSerializer()
				else -> throw IllegalStateException("Please contains at least one data serializer impl in classpath.")
			}
		}
	}
}


interface CsvLoader : DataLoader {
	override val dataType: DataType get() = DataType.Csv
}


interface CsvDumper : DataDumper {
	override val dataType: DataType get() = DataType.Csv
}
