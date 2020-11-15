// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.fasterxml.jackson.dataformat.yaml.*
import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.model.*
import com.windea.breezeframework.serialization.extensions.defaultYamlSerializer
import org.yaml.snakeyaml.*
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.representer.*
import java.lang.reflect.*

/**
 * Yaml的序列化器。
 */
@BreezeComponent
interface YamlSerializer : DataSerializer {
	override val dataType: DataType get() = DataType.Yaml

	/**
	 * 序列化指定的一组对象。
	 */
	fun serializeAll(value:List<Any>):String

	/**
	 * 反序列化指定的文本为一组对象。
	 */
	fun deserializeAll(value:String):List<Any>

	//region Yaml Serializers
	/**
	 * 默认的Yaml序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
	 */
	companion object Default: YamlSerializer by defaultYamlSerializer

	/**
	 * 由Jackson实现的Yaml序列化器。
	 *
	 * @see com.fasterxml.jackson.dataformat.yaml.YAMLMapper
	 */
	class JacksonYamlSerializer : YamlSerializer, JacksonSerializer, Configurable<YAMLMapper> {
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
	 * 由SnakeYaml实现的Yaml序列化器。
	 * @see org.yaml.snakeyaml.Yaml
	 */
	class SnakeYamlSerializer : YamlSerializer, DelegateSerializer, Configurable<Pair<LoaderOptions, DumperOptions>> {
		private val loaderOptions = LoaderOptions()
		private val dumperOptions = DumperOptions()
		val yaml by lazy { Yaml(Constructor(), Representer(), dumperOptions, loaderOptions) }

		init {
			dumperOptions.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
		}

		override fun configure(block: Pair<LoaderOptions, DumperOptions>.() -> Unit) {
			(loaderOptions to dumperOptions).block()
		}

		override fun <T> serialize(target: T): String {
			return yaml.dump(target)
		}

		override fun <T> deserialize(value: String, type: Class<T>): T {
			return yaml.loadAs(value, type)
		}

		override fun <T> deserialize(value: String, type: Type): T {
			return yaml.load(value)
		}

		override fun serializeAll(value: List<Any>): String {
			return yaml.dumpAll(value.iterator())
		}

		override fun deserializeAll(value: String): List<Any> {
			return yaml.loadAll(value).toList()
		}
	}

	/**
	 * 框架本身实现的Yaml序列化器。
	 */
	class BreezeYamlSerializer: YamlSerializer {
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
	//endregion
}
