package com.windea.breezeframework.serializer.impl.xml

import com.fasterxml.jackson.dataformat.xml.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.serializer.*

/**
 * Xml的序列化器。
 *
 * 注意：其实现依赖于第三方库，如`jackson`。
 */
interface XmlSerializer : Serializer {
	companion object {
		private const val jacksonXmlClassName = "com.fasterxml.jackson.dataformat.xml.XmlMapper"

		/**得到Yaml的序列化器的实例。*/
		val instance: XmlSerializer = when {
			presentInClassPath(jacksonXmlClassName) -> JacksonXmlSerializer
			else -> throw IllegalStateException("No supported xml serializer implementation found in classpath.")
		}


		/**配置JacksonXml的序列化器。*/
		fun configureJacksonXml(block: (XmlMapper) -> Unit) {
			block(JacksonXmlSerializer.mapper)
		}
	}
}
