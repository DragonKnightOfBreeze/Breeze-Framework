// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.fasterxml.jackson.dataformat.javaprop.*
import com.windea.breezeframework.core.domain.*
import java.lang.reflect.*
import java.util.*

/**
 * Properties的序列化器。
 */
interface PropertiesSerializer : Serializer {
	override val dataType: DataType get() = DataType.Properties

	/**
	 * 序列化指定属性对象。
	 */
	fun <T : Any> serializeProperties(value: T): Properties

	/**
	 * 反序列化指定属性对象。
	 */
	fun <T : Any> deserializeProperties(properties: Properties, type: Class<T>): T

	/**
	 * 反序列化指定属性对象。
	 */
	fun <T : Any> deserializeProperties(properties: Properties, type: Type): T

	//region Properties Serializers
	/**
	 * 由Jackson实现的Properties序列化器。
	 *
	 * @see com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper
	 */
	object JacksonPropertiesSerializer : PropertiesSerializer, JacksonSerializer, Configurable<JavaPropsMapper> {
		val mapper by lazy { JavaPropsMapper() }

		init {
			mapper.findAndRegisterModules()
		}

		override fun configure(block: JavaPropsMapper.() -> Unit) {
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

		override fun <T : Any> serializeProperties(value: T): Properties {
			return Properties().apply{ this.putAll(mapper.writeValueAsProperties(value)) }
		}

		override fun <T : Any> deserializeProperties(properties: Properties, type: Class<T>): T {
			return mapper.readPropertiesAs(properties,type)
		}

		override fun <T : Any> deserializeProperties(properties: Properties, type: Type): T {
			return mapper.readPropertiesAs(properties,mapper.typeFactory.constructType(type))
		}
	}

	/**
	 * 默认的Properties序列化器。
	 */
	object BreezePropertiesSerializer:PropertiesSerializer{
		override fun <T : Any> serializeProperties(value: T): Properties {
			TODO()
		}

		override fun <T : Any> deserializeProperties(properties: Properties, type: Class<T>): T {
			TODO()
		}

		override fun <T : Any> deserializeProperties(properties: Properties, type: Type): T {
			TODO()
		}

		override fun <T : Any> serialize(value: T): String {
			TODO()
		}

		override fun <T : Any> deserialize(value: String, type: Type): T {
			TODO()
		}

		override fun <T : Any> deserialize(value: String, type: Class<T>): T {
			TODO()
		}

	}
	//endregion
}
