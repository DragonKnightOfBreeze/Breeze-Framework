// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.component

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.serialization.extension.*

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

	/**
	 * 默认的Yaml的序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的轻量的序列化器。
	 */
	companion object Default: YamlSerializer by defaultYamlSerializer

	/**
	 * 由Jackson实现的Yaml的序列化器。
	 *
	 * @see com.fasterxml.jackson.dataformat.yaml.YAMLMapper
	 */
	class JacksonYamlSerializer : JacksonSerializer.JacksonYamlSerializer()

	/**
	 * 由SnakeYaml实现的Yaml的序列化器。
	 * @see org.yaml.snakeyaml.Yaml
	 */
	class SnakeYamlSerializer : com.windea.breezeframework.serialization.component.SnakeYamlSerializer()

	/**
	 * 由Breeze Framework实现的轻量的Yaml的序列化器。
	 */
	class BreezeYamlSerializer : BreezeSerializer.BreezeYamlSerializer()
}
