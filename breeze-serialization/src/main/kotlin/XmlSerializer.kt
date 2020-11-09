// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.fasterxml.jackson.databind.json.*
import com.fasterxml.jackson.dataformat.xml.*
import com.windea.breezeframework.core.domain.*
import java.lang.reflect.*

/**
 * Xml序列化器。
 */
interface XmlSerializer : Serializer {
	override val dataType: DataType get() = DataType.Xml

	//region Xml Serializers
	/**
	 * 默认的Xml序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
	 */
	companion object Default:XmlSerializer by defaultXmlSerializer

	/**
	 * 由Jackson实现的Xml序列化器。
	 *
	 * @see com.fasterxml.jackson.dataformat.xml.XmlMapper
	 */
	object JacksonXmlSerializer : XmlSerializer, JacksonSerializer, Configurable<XmlMapper> {
		 val mapper by lazy { XmlMapper() }

		init {
			mapper.findAndRegisterModules()
		}

		override fun configure(block: XmlMapper.() -> Unit) {
			mapper.block()
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
	 * 框架本身实现的Xml序列化器。
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
