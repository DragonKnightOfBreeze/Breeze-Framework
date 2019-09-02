package com.windea.breezeframework.data.serializers.xml

import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.serializers.*

interface XmlSerializer : DataSerializer, XmlLoader, XmlDumper {
	override val dataType: DataType get() = DataType.Xml
}

interface XmlLoader : DataLoader {
	override val dataType: DataType get() = DataType.Xml
}

interface XmlDumper : DataDumper {
	override val dataType: DataType get() = DataType.Xml
}
