package com.windea.breezeframework.data.serializers.properteis

import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.serializers.*

interface PropertiesSerializer : DataSerializer, PropertiesLoader, PropertiesDumper {
	override val dataType: DataType get() = DataType.Properties
}

interface PropertiesLoader : DataLoader {
	override val dataType: DataType get() = DataType.Properties
}

interface PropertiesDumper : DataDumper {
	override val dataType: DataType get() = DataType.Properties
}
