package com.windea.breezeframework.data.serializers.xml

import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.reflect.extensions.*

interface XmlSerializer : Serializer {
	companion object {
		val instance: XmlSerializer = when {
			checkClassForName("com.fasterxml.jackson.dataformat.xml.XmlMapper") -> JacksonXmlSerializer
			else -> throw IllegalStateException("Please contain at least one serializer implementation in classpath.")
		}
	}
}

interface XmlSerializerConfig : SerializerConfig
