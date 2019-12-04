package com.windea.breezeframework.data.serializers.xml

import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.reflect.extensions.*

/**
 * Xml的序列化器。
 *
 * 注意：其实现依赖于第三方库，如`jackson`。
 */
interface XmlSerializer : Serializer {
	companion object {
		val instance: XmlSerializer = when {
			checkClassForName("com.fasterxml.jackson.dataformat.xml.XmlMapper") -> JacksonXmlSerializer
			else -> throw IllegalStateException("Please contain at least one serializer implementation in classpath.")
		}
	}
}

interface XmlSerializerConfig : SerializerConfig
