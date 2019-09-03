package com.windea.breezeframework.data.serializers.xml

import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.reflect.extensions.*

interface XmlSerializer<S, C> : DataSerializer<S, C>, XmlLoader, XmlDumper {
	override val dataType: DataType get() = DataType.Xml
	
	companion object {
		val instance: XmlSerializer<*, *> by lazy {
			when {
				checkClassForName("com.fasterxml.jackson.dataformat.xml.XmlMapper") -> JacksonXmlSerializer()
				else -> throw IllegalStateException("Please contains at least one data serializer impl in classpath.")
			}
		}
	}
}

interface XmlLoader : DataLoader {
	override val dataType: DataType get() = DataType.Xml
}

interface XmlDumper : DataDumper {
	override val dataType: DataType get() = DataType.Xml
}
