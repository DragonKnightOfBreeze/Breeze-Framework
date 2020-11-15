// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.component

import com.fasterxml.jackson.databind.json.*
import com.fasterxml.jackson.dataformat.javaprop.*
import com.fasterxml.jackson.dataformat.xml.*
import com.fasterxml.jackson.dataformat.yaml.*
import com.windea.breezeframework.core.*
import com.windea.breezeframework.core.annotation.*
import java.lang.reflect.*
import java.util.*

/**
 * 由Jackson委托实现的序列化器。
 *
 * @see com.fasterxml.jackson.databind.ObjectMapper
 */
@BreezeComponent
interface JacksonSerializer : DelegateSerializer {
	/**
	 * 由Jackson实现的Json的序列化器。
	 *
	 * @see com.fasterxml.jackson.databind.json.JsonMapper
	 */
	open class JacksonJsonSerializer : JsonSerializer, JacksonSerializer, Configurable<JsonMapper> {
		val mapper by lazy { JsonMapper() }

		init {
			mapper.findAndRegisterModules()
		}

		override fun configure(block: JsonMapper.() -> Unit) {
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
	}

	/**
	 * 由Jackson实现的Yaml的序列化器。
	 *
	 * @see com.fasterxml.jackson.dataformat.yaml.YAMLMapper
	 */
	open class JacksonYamlSerializer : YamlSerializer, JacksonSerializer, Configurable<YAMLMapper> {
		val mapper by lazy { YAMLMapper() }

		init {
			mapper.findAndRegisterModules()
		}

		override fun configure(block: YAMLMapper.() -> Unit) {
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

		override fun serializeAll(value: List<Any>): String {
			throw UnsupportedOperationException("Cannot not find suitable method to delegate.")
		}

		override fun deserializeAll(value: String): List<Any> {
			throw UnsupportedOperationException("Cannot not find suitable method to delegate.")
		}
	}

	/**
	 * 由Jackson实现的Xml的序列化器。
	 *
	 * @see com.fasterxml.jackson.dataformat.xml.XmlMapper
	 */
	open class JacksonXmlSerializer : XmlSerializer, JacksonSerializer, Configurable<XmlMapper> {
		val mapper by lazy { XmlMapper() }

		init {
			mapper.findAndRegisterModules()
		}

		override fun configure(block: XmlMapper.() -> Unit) {
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
	}

	/**
	 * 由Jackson实现的Properties的序列化器。
	 *
	 * @see com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper
	 */
	open class JacksonPropertiesSerializer : PropertiesSerializer, JacksonSerializer, Configurable<JavaPropsMapper> {
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

		override fun <T> serializeProperties(target: T): Properties {
			return Properties().apply { this.putAll(mapper.writeValueAsProperties(target)) }
		}

		override fun <T> deserializeProperties(properties: Properties, type: Class<T>): T {
			return mapper.readPropertiesAs(properties, type)
		}

		override fun <T> deserializeProperties(properties: Properties, type: Type): T {
			return mapper.readPropertiesAs(properties, mapper.typeFactory.constructType(type))
		}
	}
}
