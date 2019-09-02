package com.windea.breezeframework.data.serializers.json

import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.serializers.*

interface JsonSerializer : DataSerializer, JsonLoader, JsonDumper {
	override val dataType: DataType get() = DataType.Json
}

interface JsonLoader : DataLoader {
	override val dataType: DataType get() = DataType.Json
}

interface JsonDumper : DataDumper {
	override val dataType: DataType get() = DataType.Json
}
