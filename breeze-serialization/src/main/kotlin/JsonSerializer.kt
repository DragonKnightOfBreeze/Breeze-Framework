// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.alibaba.fastjson.*
import com.fasterxml.jackson.databind.json.*
import com.google.gson.*
import com.windea.breezeframework.core.domain.*
import kotlinx.serialization.*
import kotlinx.serialization.getContextualOrDefault
import kotlinx.serialization.json.*
import kotlinx.serialization.modules.*
import java.lang.reflect.*
import kotlin.reflect.full.*

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
	object JacksonJsonSerializer : JsonSerializer, JacksonSerializer, Configurable<JsonMapper> {
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
	object GsonSerializer : JsonSerializer, DelegateSerializer, Configurable<GsonBuilder> {
		private val gsonBuilder by lazy { GsonBuilder() }
		val gson by lazy { gsonBuilder.create() }

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
	@OptIn(UnsafeSerializationApi::class)
	object KotlinxJsonSerializer : JsonSerializer, KotlinxSerializer, Configurable<JsonBuilder> {
		private var jsonDelegate: Json = Json

		val json by lazy { jsonDelegate }

		override fun configure(block: JsonBuilder.() -> Unit) {
			jsonDelegate = Json(Json.Default, block)
		}

		override fun <T : Any> serialize(value: T): String {
			//FIXME 序列化有泛型参数的类型时会出错
			val kSerializer = json.serializersModule.getContextual(value::class)
			                  ?: value::class.serializerOrNull()
			                  ?: serializer(value::class.createType())
			return json.encodeToString(kSerializer as KSerializer<T>, value)
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
	object BreezeJsonSerializer : JsonSerializer {
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
