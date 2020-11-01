// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.alibaba.fastjson.*
import com.fasterxml.jackson.databind.json.*
import com.google.gson.*
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
	 * 由Jackson实现的Json序列化器。
	 *
	 * @see com.fasterxml.jackson.databind.json.JsonMapper
	 */
	object JacksonJsonSerializer : JsonSerializer, JacksonSerializer, DelegateSerializer {
		private val mapper by lazy { JsonMapper() }

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
	 * 由Gson实现的Json序列化器。
	 *
	 * @see com.google.gson.Gson
	 */
	object GsonSerializer : JsonSerializer, DelegateSerializer {
		private val gsonBuilder by lazy { GsonBuilder() }
		private val gson by lazy { gsonBuilder.create() }

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
	object FastJsonSerializer : JsonSerializer, DelegateSerializer {
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
	object KotlinxJsonSerializer : JsonSerializer,KotlinxSerializer,DelegateSerializer {
		private val json by lazy { Json }

		override fun <T : Any> serialize(value: T): String {
			return json.encodeToString(serializer(value::class.java), value)
		}

		override fun <T : Any> deserialize(value: String, type: Class<T>): T {
			return json.decodeFromString(serializer(type), value) as T
		}

		override fun <T : Any> deserialize(value: String, type: Type): T {
			return json.decodeFromString(serializer(type), value) as T
		}
	}

	/**
	 * 默认的Json序列化器。
	 */
	object BreezeJsonSerializer:JsonSerializer{
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
