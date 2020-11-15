// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.alibaba.fastjson.*
import com.fasterxml.jackson.databind.json.*
import com.google.gson.*
import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.model.*
import com.windea.breezeframework.serialization.extensions.defaultJsonSerializer
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.lang.reflect.*
import java.time.temporal.*
import java.util.*

/**
 * Json序列化器。
 */
@BreezeComponent
interface JsonSerializer : DataSerializer {
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

		override fun <T> serialize(target: T): String {
			return gson.toJson(target)
		}

		override fun <T> deserialize(value: String, type: Class<T>): T {
			return gson.fromJson(value, type)
		}

		override fun <T> deserialize(value: String, type: Type): T {
			return gson.fromJson(value, type)
		}
	}

	/**
	 * 由FastJson实现的Json序列化器。
	 *
	 * @see com.alibaba.fastjson.JSON
	 */
	class FastJsonSerializer : JsonSerializer, DelegateSerializer {
		override fun <T> serialize(target: T): String {
			return JSON.toJSONString(target)
		}

		override fun <T> deserialize(value: String, type: Class<T>): T {
			return JSON.parseObject(value, type)
		}

		override fun <T> deserialize(value: String, type: Type): T {
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
			jsonDelegate = Json(Json, block)
		}

		override fun <T> serialize(target: T): String {
			if(target == null) return "null"
			return json.encodeToString(json.serializersModule.serializer(target.javaClass),target)
		}

		override fun <T> deserialize(value: String, type: Class<T>): T {
			return json.decodeFromString(json.serializersModule.serializer(type), value) as T
		}

		override fun <T> deserialize(value: String, type: Type): T {
			return json.decodeFromString(json.serializersModule.serializer(type), value) as T
		}
	}

	/**
	 * 框架本身实现的Json序列化器。
	 */
	class BreezeJsonSerializer : JsonSerializer ,Configurable<BreezeJsonSerializer.ConfigBuilder>{
		var config = Config()
			private set

		override fun configure(block: ConfigBuilder.() -> Unit) {
			config = ConfigBuilder().apply(block).build()
		}

		override fun <T> serialize(target: T): String {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Class<T>): T {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Type): T {
			TODO()
		}

		private fun <T> doSerialize(value:T):String{
			return when {
				value == null -> "null"
				value is Array<*> -> doSerializeArray(value)
				value is Iterable<*> -> doSerializeIterable(value)
				value is Map<*,*> -> doSerializeMap(value)
				value is Sequence<*> -> doSerializeSequence(value)
				value is Number || value is Boolean || value is Char || value is CharSequence || value is Date || value is Temporal -> doSerializeStringLike(value)
				else -> doSerializeMapLike(value)
			}
		}

		private fun doSerializeArray(value:Array<*>):String{
			TODO()
		}

		private fun doSerializeIterable(value:Iterable<*>):String{
			TODO()
		}

		private fun doSerializeSequence(value:Sequence<*>):String{
			TODO()
		}

		private fun doSerializeMap(value:Map<*,*>):String{
			TODO()
		}

		private fun doSerializeStringLike(value: Any): String {
			return value.toString()
		}

		private fun doSerializeMapLike(value:Any):String{
			return doSerializeMap(MapLikeSerializer.serialize(value))
		}

		data class Config(
			val indent:String = "  ",
			val prettyPrint:Boolean = false
		):DataEntity{
			val quote = '\"'
		}

		data class ConfigBuilder(
			var indent:String = "  ",
			var prettyPrint:Boolean = false
		):DataBuilder<Config>{
			override fun build(): Config {
				return Config(indent,prettyPrint)
			}
		}
	}
	//endregion
}
