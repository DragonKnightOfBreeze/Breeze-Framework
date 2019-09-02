package com.windea.breezeframework.data.serializers.csv

import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.serializers.*

interface CsvSerializer : DataSerializer, CsvLoader, CsvDumper {
	override val dataType: DataType get() = DataType.Csv
}

interface CsvLoader : DataLoader {
	override val dataType: DataType get() = DataType.Csv
}

interface CsvDumper : DataDumper {
	override val dataType: DataType get() = DataType.Csv
}
