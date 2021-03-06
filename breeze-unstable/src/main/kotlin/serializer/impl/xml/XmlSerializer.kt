// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serializer.impl.xml

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.mapper.impl.*
import com.windea.breezeframework.serializer.*
import com.fasterxml.jackson.dataformat.xml.XmlMapper as JacksonXmlMapper

/**
 * Xml的序列化器。
 *
 * 其实现默认由`breeze-mapper`提供，但也可以依赖于第三方库，如`jackson`。
 */
interface XmlSerializer : Serializer {
	companion object {
		private const val jacksonXmlClassName = "com.fasterxml.jackson.dataformat.xml.XmlMapper"

		/**得到Yaml的序列化器的实例。*/
		val instance: XmlSerializer = when {
			presentInClassPath(jacksonXmlClassName) -> JacksonXmlSerializer
			else -> BreezeXmlSerializer
			//else -> throw IllegalStateException("No supported xml serializer implementation found in classpath.")
		}

		/**配置BreezeXml的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		@OptionalApi
		fun configureBreezeXml(block: (XmlMapper.Config.Builder) -> Unit) {
			block(BreezeXmlSerializer.configBuilder)
		}

		/**配置JacksonXml的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		@OptionalApi
		fun configureJacksonXml(block: (JacksonXmlMapper) -> Unit) {
			block(JacksonXmlSerializer.mapper)
		}
	}
}
