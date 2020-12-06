// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.component

import com.alibaba.fastjson.serializer.*
import com.windea.breezeframework.core.*
import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.reflect.extension.*
import com.windea.breezeframework.serialization.extension.*
import java.io.*
import java.lang.reflect.*
import java.time.temporal.*
import java.util.*
import kotlin.jvm.internal.*
import kotlin.reflect.*
import kotlin.system.*

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

		constructor(configBuilder: ConfigBuilder) {
			this.configBuilder = configBuilder
		}

		constructor(configBuilder: ConfigBuilder.() -> Unit) {
			this.configBuilder = ConfigBuilder().apply(configBuilder)
		}

		private var configBuilder: ConfigBuilder = ConfigBuilder()
		val config by lazy { configBuilder.build() }

		override fun configure(block: ConfigBuilder.() -> Unit) {
			configBuilder = ConfigBuilder().apply(block)
		}

		override fun <T> serialize(target: T): String {
			return doSerialize(target)
		}

		override fun <T> deserialize(value: String, type: Class<T>): T {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Type): T {
			TODO()
		}

		companion object {
			private const val space = ' '
			private const val doubleQuote = '\"'
			private const val singleQuote = '\''
			private const val separator = ','
			private const val keyValueSeparator = ':'
			private const val arrayPrefix = '['
			private const val arraySuffix = ']'
			private const val objectPrefix = '{'
			private const val objectSuffix = '}'
		}

		data class Config(
			val indent: String = "  ",
			val lineSeparator: String = "\n",
			val doubleQuoted: Boolean = true,
			val unquoteKey: Boolean = false,
			val unquoteValue: Boolean = false,
			val prettyPrint: Boolean = false,
		) : DataEntity {
			val quote = if(doubleQuoted) doubleQuote else singleQuote
			val escapedQuote = "\\" + quote
		}

		data class ConfigBuilder(
			var indent: String = "  ",
			var lineSeparator: String = "\n",
			var doubleQuoted: Boolean = true,
			var unquoteKey: Boolean = false,
			var unquoteValue: Boolean = false,
			var prettyPrint: Boolean = false
		) : Builder<Config> {
			override fun build(): Config {
				return Config(indent, lineSeparator, doubleQuoted, unquoteKey, unquoteValue, prettyPrint)
			}
		}

		//TODO 优化性能

		private fun <T> doSerialize(target: T): String {
			return StringBuilder(2048).apply { doSerialize(target, this) }.toString()
		}

		private fun <T> doSerialize(target: T, buffer: Appendable, depth: Int = 1) {
			when {
				target == null -> doSerializeNull(buffer)
				target is Boolean -> doSerializeBoolean(target, buffer)
				target is Number -> doSerializeNumber(target, buffer)
				target is String -> doSerializeString(target, buffer)
				target.isStringLike() -> doSerializeString(target.toString(), buffer)
				target is Array<*> -> doSerializeArray(target, buffer, depth)
				target is Iterable<*> -> doSerializeIterable(target, buffer, depth)
				target is Sequence<*> -> doSerializeSequence(target, buffer, depth)
				target is Map<*, *> -> doSerializeMap(target, buffer, depth)
				target.isMapLike() -> doSerializeMap(target.serializeBy(MapLikeSerializer), buffer)
				else -> throw UnsupportedOperationException("Unsupported serialize value type '$target.javaClass.name'.")
			}
		}

		private fun doSerializeKey(target: String, buffer: Appendable) {
			if(config.unquoteKey) {
				target.appendTo(buffer)
			} else {
				config.quote.appendTo(buffer)
				for(c in target) {
					if(c == config.quote) config.escapedQuote.appendTo(buffer) else c.appendTo(buffer)
				}
				//target.appendTo(buffer)
				config.quote.appendTo(buffer)
			}
		}

		private fun doSerializeNull(buffer: Appendable) {
			"null".appendTo(buffer)
		}

		private fun doSerializeBoolean(target: Boolean, buffer: Appendable) {
			target.toString().appendTo(buffer)
		}

		private fun doSerializeNumber(target: Number, buffer: Appendable) {
			target.toString().appendTo(buffer)
		}

		private fun doSerializeString(target: String, buffer: Appendable) {
			//measureNanoTime {
				if(config.unquoteValue) {
					target.appendTo(buffer)
				} else {
					config.quote.appendTo(buffer)
					for(c in target) {
						if(c == config.quote) config.escapedQuote.appendTo(buffer) else c.appendTo(buffer)
					}
					//target.appendTo(buffer)
					config.quote.appendTo(buffer)
				}
			//}.also{ println("doSerializeString $target: $it")}
		}

		private fun doSerializeArray(target: Array<*>, buffer: Appendable, depth: Int = 1) {
			//measureNanoTime {
			arrayPrefix.appendTo(buffer)
			var appendSeparator = false
			for(e in target) {
				if(appendSeparator) separator.appendTo(buffer) else appendSeparator = true
				if(config.prettyPrint) {
					config.lineSeparator.appendTo(buffer)
					repeat(depth) { config.indent.appendTo(buffer) }
				}
				doSerialize(e, buffer, depth + 1)
			}
			if(config.prettyPrint) {
				config.lineSeparator.appendTo(buffer)
				if(depth != 1) repeat(depth - 1) { config.indent.appendTo(buffer) }
			}
			arraySuffix.appendTo(buffer)
			//}.also{ println("doSerializeArray $target: $it")}
		}

		private fun doSerializeIterable(target: Iterable<*>, buffer: Appendable, depth: Int = 1) {
			//measureNanoTime {
			arrayPrefix.appendTo(buffer)
			var shouldWriteSeparator = false
			for(e in target) {
				if(shouldWriteSeparator) separator.appendTo(buffer) else shouldWriteSeparator = true
				if(config.prettyPrint) {
					config.lineSeparator.appendTo(buffer)
					repeat(depth) { config.indent.appendTo(buffer) }
				}
				doSerialize(e, buffer, depth + 1)
			}
			if(config.prettyPrint) {
				config.lineSeparator.appendTo(buffer)
				if(depth != 1) repeat(depth - 1) { config.indent.appendTo(buffer) }
			}
			arraySuffix.appendTo(buffer)
			//}.also{ println("doSerializeIterable $target: $it")}
		}

		private fun doSerializeSequence(target: Sequence<*>, buffer: Appendable, depth: Int = 1) {
			//measureNanoTime {
			arrayPrefix.appendTo(buffer)
			var shouldWriteSeparator = false
			for(e in target) {
				if(shouldWriteSeparator) separator.appendTo(buffer) else shouldWriteSeparator = true
				if(config.prettyPrint) {
					config.lineSeparator.appendTo(buffer)
					repeat(depth) { config.indent.appendTo(buffer) }
				}
				doSerialize(e, buffer, depth + 1)
			}
			if(config.prettyPrint) {
				config.lineSeparator.appendTo(buffer)
				if(depth != 1) repeat(depth - 1) { config.indent.appendTo(buffer) }
			}
			arraySuffix.appendTo(buffer)
			//}.also{ println("doSerializeSequence $target: $it")}
		}

		private fun doSerializeMap(target: Map<*, *>, buffer: Appendable, depth: Int = 1) {
			//measureNanoTime {
			objectPrefix.appendTo(buffer)
			var shouldWriteSeparator = false
			for((k, v) in target) {
				if(shouldWriteSeparator) separator.appendTo(buffer) else shouldWriteSeparator = true
				if(config.prettyPrint) {
					config.lineSeparator.appendTo(buffer)
					repeat(depth) { config.indent.appendTo(buffer) }
				}
				doSerializeKey(k.toString(), buffer)
				keyValueSeparator.appendTo(buffer)
				if(config.prettyPrint) space.appendTo(buffer)
				doSerialize(v, buffer, depth + 1)
			}
			if(config.prettyPrint) {
				config.lineSeparator.appendTo(buffer)
				if(depth != 1) repeat(depth - 1) { config.indent.appendTo(buffer) }
			}
			objectSuffix.appendTo(buffer)
			//}.also{ println("doSerializeMap $target: $it")}
		}

		private fun Any?.isStringLike(): Boolean {
			return this is CharSequence || this is Char || this is Temporal || this is Date
		}

		private fun Any?.isMapLike(): Boolean {
			return true
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
