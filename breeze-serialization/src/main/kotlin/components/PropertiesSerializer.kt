// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.fasterxml.jackson.dataformat.javaprop.*
import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.model.*
import com.windea.breezeframework.serialization.extensions.defaultPropertiesSerializer
import java.lang.reflect.*
import java.util.*

/**
 * Properties的序列化器。
 */
@BreezeComponent
interface PropertiesSerializer : DataSerializer {
	override val dataType: DataType get() = DataType.Properties

	/**
	 * 序列化指定属性对象。
	 */
	fun <T> serializeProperties(value: T): Properties

	/**
	 * 反序列化指定属性对象。
	 */
	fun <T> deserializeProperties(properties: Properties, type: Class<T>): T

	/**
	 * 反序列化指定属性对象。
	 */
	fun <T> deserializeProperties(properties: Properties, type: Type): T

	//region Properties Serializers
	/**
	 * 默认的Properties序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
	 */
	companion object Default: PropertiesSerializer by defaultPropertiesSerializer

	/**
	 * 由Jackson实现的Properties序列化器。
	 *
	 * @see com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper
	 */
	class JacksonPropertiesSerializer : PropertiesSerializer, JacksonSerializer, Configurable<JavaPropsMapper> {
		val mapper by lazy { JavaPropsMapper() }

		init {
			mapper.findAndRegisterModules()
		}

		override fun configure(block: JavaPropsMapper.() -> Unit) {
			mapper.block()
		}

		override fun <T> serialize(target: T): String {
			return mapper.writeValueAsString(target)
		}

		override fun <T> deserialize(value: String, type: Class<T>): T {
			return mapper.readValue(value, type)
		}

		override fun <T> deserialize(value: String, type: Type): T {
			return mapper.readValue(value, mapper.typeFactory.constructType(type))
		}

		override fun <T> serializeProperties(value: T): Properties {
			return Properties().apply{ this.putAll(mapper.writeValueAsProperties(value)) }
		}

		override fun <T> deserializeProperties(properties: Properties, type: Class<T>): T {
			return mapper.readPropertiesAs(properties,type)
		}

		override fun <T> deserializeProperties(properties: Properties, type: Type): T {
			return mapper.readPropertiesAs(properties,mapper.typeFactory.constructType(type))
		}
	}

	/**
	 * 框架本身实现的Properties序列化器。
	 */
	class BreezePropertiesSerializer: PropertiesSerializer {
		override fun <T> serializeProperties(value: T): Properties {
			TODO()
		}

		override fun <T> deserializeProperties(properties: Properties, type: Class<T>): T {
			TODO()
		}

		override fun <T> deserializeProperties(properties: Properties, type: Type): T {
			TODO()
		}

		override fun <T> serialize(target: T): String {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Type): T {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Class<T>): T {
			TODO()
		}

	}
	//endregion
}
