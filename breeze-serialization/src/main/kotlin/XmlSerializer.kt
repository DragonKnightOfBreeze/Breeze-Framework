// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.fasterxml.jackson.databind.json.*
import com.fasterxml.jackson.dataformat.xml.*
import java.lang.reflect.*

/**
 * Xml序列化器。
 */
interface XmlSerializer : Serializer {
	override val dataType: DataType get() = DataType.Xml

	//region Xml Serializers
	/**
	 * 由Jackson实现的Xml序列化器。
	 *
	 * @see com.fasterxml.jackson.dataformat.xml.XmlMapper
	 */
	object JacksonXmlSerializer : XmlSerializer, JacksonSerializer, DelegateSerializer {
		private val mapper by lazy { XmlMapper() }

		init {
			mapper.findAndRegisterModules()
		}

		override fun <T : Any> serialize(value: T): String {
			return mapper.writeValueAsString(value)
		}

		override fun <T : Any> deserialize(value: String, type: Class<T>): T {
			return mapper.readValue(value, type)
		}

		override fun <T : Any> deserialize(value: String, type: Type): T {
			return mapper.readValue(value, mapper.typeFactory.constructType(type))
		}
	}

	/**
	 * 默认的Xml序列化器。
	 */
	object BreezeXmlSerializer:XmlSerializer{
		override fun <T : Any> serialize(value: T): String {
			TODO()
		}

		override fun <T : Any> deserialize(value: String, type: Class<T>): T {
			TODO()
		}

		override fun <T : Any> deserialize(value: String, type: Type): T {
			TODO()
		}
	}
	//endregion
}
