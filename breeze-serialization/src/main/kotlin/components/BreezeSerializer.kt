// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.core.model.*
import com.windea.breezeframework.reflect.extensions.*
import com.windea.breezeframework.serialization.extensions.*
import java.lang.reflect.*
import java.time.temporal.*
import java.util.*
import kotlin.jvm.internal.*
import kotlin.reflect.*

/**
 * 由Breeze Framework实现的轻量的数据的序列化器。
 */
@BreezeComponent
interface BreezeSerializer {
	//DONE 基本实现序列化功能
	//TODO 应用配置
	//TODO 基本实现反序列化功能
	//TODO 应用配置

	/**
	 * 由Breeze Framework实现的轻量的Json的序列化器。
	 */
	@Suppress("unused")
	open class BreezeJsonSerializer : JsonSerializer, BreezeSerializer, Configurable<BreezeJsonSerializer.ConfigBuilder> {
		constructor()

		constructor(configBuilder: ConfigBuilder = ConfigBuilder()) {
			this.config = configBuilder.build()
		}

		constructor(configBuilder: ConfigBuilder.() -> Unit) {
			this.config = ConfigBuilder().apply(configBuilder).build()
		}

		var config: Config = Config()
			private set

		private val quote = '\"'

		override fun configure(block: ConfigBuilder.() -> Unit) {
			config = ConfigBuilder().apply(block).build()
		}

		override fun <T> serialize(target: T): String {
			return buildString {
				doSerialize(target, this)
			}
		}

		override fun <T> deserialize(value: String, type: Class<T>): T {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Type): T {
			TODO()
		}

		private fun <T> doSerialize(target: T, builder: StringBuilder): String {
			when {
				target == null -> doSerializeNull(target, builder)
				target is Boolean -> doSerializeBoolean(target, builder)
				target is Number -> doSerializeNumber(target, builder)
				target.isStringLike() -> doSerializeString(target.toString(), builder)
				target is Array<*> -> doSerializeArray(target, builder)
				target is Iterable<*> -> doSerializeIterable(target, builder)
				target is Sequence<*> -> doSerializeSequence(target, builder)
				target is Map<*, *> -> doSerializeMap(target, builder)
				target.isMapLike() -> doSerializeMap(target.serializeBy(MapLikeSerializer), builder)
				else -> throw UnsupportedOperationException("Unsupported serialize value type '$target.javaClass.name'.")
			}
			return builder.toString()
		}

		private fun doSerializeNull(target: Any?, builder: StringBuilder) {
			target.appendTo(builder)
		}

		private fun doSerializeBoolean(target: Boolean, builder: StringBuilder) {
			target.appendTo(builder)
		}

		private fun doSerializeNumber(target: Number, builder: StringBuilder) {
			target.appendTo(builder)
		}

		private fun doSerializeString(target: String, builder: StringBuilder) {
			target.quote(quote).appendTo(builder)
		}

		private fun doSerializeArray(target: Array<*>, builder: StringBuilder) {
			target.joinTo(builder, ",", "[", "]") { e ->
				buildString {
					doSerialize(e, this)
				}
			}
		}

		private fun doSerializeIterable(target: Iterable<*>, builder: StringBuilder) {
			target.joinTo(builder, ",", "[", "]") { e ->
				buildString {
					doSerialize(e, this)
				}
			}
		}

		private fun doSerializeSequence(target: Sequence<*>, builder: StringBuilder) {
			target.joinTo(builder, ",", "[", "]") { e ->
				buildString {
					doSerialize(e, this)
				}
			}
		}

		private fun doSerializeMap(target: Map<*, *>, builder: StringBuilder) {
			target.joinTo(builder, ",", "{", "}") { (k, v) ->
				buildString {
					doSerializeString(k.toString(), this)
					append(":")
					doSerialize(v, this)
				}
			}
		}

		private fun Any?.isStringLike(): Boolean {
			return this is CharSequence || this is Char || this is Temporal || this is Date
		}

		private fun Any?.isMapLike(): Boolean {
			return true
		}

		data class Config(
			val indent: String = "  ",
			val lineSeparator: String = "\n",
			val prettyPrint: Boolean = false,
		) : DataEntity

		data class ConfigBuilder(
			var indent: String = "  ",
			var lineSeparator: String = "\n",
			var prettyPrint: Boolean = false
		) : DataBuilder<Config> {
			override fun build(): Config {
				return Config(indent, lineSeparator, prettyPrint)
			}
		}
	}

	/**
	 * 由Breeze Framework实现的轻量的Yaml的序列化器。
	 */
	open class BreezeYamlSerializer : YamlSerializer, BreezeSerializer {
		override fun serializeAll(value: List<Any>): String {
			TODO()
		}

		override fun deserializeAll(value: String): List<Any> {
			TODO()
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

	}

	/**
	 * 由Breeze Framework实现的轻量的Xml的序列化器。
	 */
	open class BreezeXmlSerializer : XmlSerializer, BreezeSerializer {
		override fun <T> serialize(target: T): String {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Class<T>): T {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Type): T {
			TODO()
		}
	}

	/**
	 * 由Breeze Framework实现的轻量的Properties的序列化器。
	 */
	open class BreezePropertiesSerializer : PropertiesSerializer, BreezeSerializer {
		override fun <T> serializeProperties(target: T): Properties {
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

	/**
	 * 由Breeze Framework实现的轻量的Csv的序列化器。
	 */
	open class BreezeCsvSerializer : CsvSerializer, BreezeSerializer {
		override fun <T> serialize(target: T): String {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Class<T>): T {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Type): T {
			TODO()
		}
	}

	/**
	 * 由Breeze Framework实现的轻量的Tsv的序列化器。
	 */
	open class BreezeTsvSerializer : TsvSerializer, BreezeSerializer {
		override fun <T> serialize(target: T): String {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Class<T>): T {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Type): T {
			TODO()
		}
	}

	/**
	 * 由Breeze Framework实现的轻量的类映射对象的序列化器。
	 */
	@Suppress("UNCHECKED_CAST")
	open class BreezeMapLikeSerializer : MapLikeSerializer, BreezeSerializer {
		/**
		 * 序列化指定对象为映射。
		 */
		override fun <T> serialize(target: T): Map<String, Any?> {
			if(target == null) return emptyMap()
			//使用Java反射，映射第一层属性，不进行递归映射
			return target.javaClass.getters.associateBy(
				{ it.name[3].toLowerCase() + it.name.substring(4) },
				{ it.invoke(target) }
			)
		}

		/**
		 * 反序列化指定映射为对象。
		 */
		override fun <T> deserialize(value: Map<String, Any?>, type: Class<T>): T {
			return runCatching {
				//存在无参构造时，使用Java反射，直接实例化对象
				val result = type.getConstructor().newInstance()
				//然后尝试根据名字对所有非final的字段赋值
				for((n, v) in value) {
					type.getDeclaredField(n).apply { trySetAccessible() }.set(result, v)
				}
				result
			}.getOrElse {
				//不存在无参构造时，使用Kotlin反射，尝试根据主构造方法实例化对象，并尝试根据名字对所有参数赋值
				val filteredMap = value.toMutableMap()
				val kClass = Reflection.getOrCreateKotlinClass(type) as KClass<*>
				val result = kClass.constructors.first().let { c ->
					c.callBy(c.parameters.associateWith { filteredMap.remove(it.name);value[it.name] })
				}
				//然后再尝试对剩下的所有非final的字段赋值
				for((n, v) in filteredMap) {
					type.getDeclaredField(n).apply { trySetAccessible() }.set(result, v)
				}
				return result as T
			}
		}

		/**
		 * 反序列化指定映射为对象。
		 */
		override fun <T> deserialize(value: Map<String, Any?>, type: Type): T {
			return deserialize(value, type.erasedType) as T
		}
	}
}
