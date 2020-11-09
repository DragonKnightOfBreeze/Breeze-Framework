// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.alibaba.fastjson.*
import com.fasterxml.jackson.databind.json.*
import com.google.gson.*
import com.windea.breezeframework.core.domain.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.lang.reflect.*

/**
 * Json序列化器。
 */
interface JsonSerializer : Serializer {
	override val dataType: DataType get() = DataType.Json

	//region Json Serializers
	/**
	 * 默认的Json序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
	 */
	companion object Default: JsonSerializer by defaultJsonSerializer

	/**
	 * 由Jackson实现的Json序列化器。
	 *
	 * @see com.fasterxml.jackson.databind.json.JsonMapper
	 */
	class JacksonJsonSerializer : JsonSerializer, JacksonSerializer, Configurable<JsonMapper> {
		val mapper by lazy { JsonMapper() }

		init {
			mapper.findAndRegisterModules()
		}

		override fun configure(block: JsonMapper.() -> Unit) {
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
	 * 由Gson实现的Json序列化器。
	 *
	 * @see com.google.gson.Gson
	 */
	class GsonSerializer : JsonSerializer, DelegateSerializer, Configurable<GsonBuilder> {
		private val gsonBuilder by lazy { GsonBuilder() }
		val gson: Gson by lazy { gsonBuilder.create() }

		override fun configure(block: GsonBuilder.() -> Unit) {
			gsonBuilder.block()
		}

		override fun <T : Any> serialize(value: T): String {
			return gson.toJson(value)
		}

		override fun <T : Any> deserialize(value: String, type: Class<T>): T {
			return gson.fromJson(value, type)
		}

		override fun <T : Any> deserialize(value: String, type: Type): T {
			return gson.fromJson(value, type)
		}
	}

	/**
	 * 由FastJson实现的Json序列化器。
	 *
	 * @see com.alibaba.fastjson.JSON
	 */
	class FastJsonSerializer : JsonSerializer, DelegateSerializer {
		override fun <T : Any> serialize(value: T): String {
			return JSON.toJSONString(value)
		}

		override fun <T : Any> deserialize(value: String, type: Class<T>): T {
			return JSON.parseObject(value, type)
		}

		override fun <T : Any> deserialize(value: String, type: Type): T {
			return JSON.parseObject(value, type)
		}
	}

	/**
	 * 由Kotlinx Serialization实现的Json序列化器。
	 *
	 * @see kotlinx.serialization.json.Json
	 */
	@Suppress("UNCHECKED_CAST")
	@OptIn(ExperimentalSerializationApi::class)
	class KotlinxJsonSerializer : JsonSerializer, KotlinxSerializer, Configurable<JsonBuilder> {
		private var jsonDelegate: Json = Json

		val json by lazy { jsonDelegate }

		override fun configure(block: JsonBuilder.() -> Unit) {
			jsonDelegate = Json(Json.Default, block)
		}

		override fun <T : Any> serialize(value: T): String {
			return json.encodeToString(json.serializersModule.serializer(value.javaClass),value)
		}

		override fun <T : Any> deserialize(value: String, type: Class<T>): T {
			return json.decodeFromString(json.serializersModule.serializer(type), value) as T
		}

		override fun <T : Any> deserialize(value: String, type: Type): T {
			return json.decodeFromString(json.serializersModule.serializer(type), value) as T
		}
	}

	/**
	 * 框架本身实现的Json序列化器。
	 */
	class BreezeJsonSerializer : JsonSerializer {
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
