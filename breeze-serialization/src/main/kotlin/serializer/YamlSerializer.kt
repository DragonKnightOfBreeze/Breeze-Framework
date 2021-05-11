// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.serializer

import icu.windea.breezeframework.serialization.*
import icu.windea.breezeframework.serialization.extension.*

/**
 * Yaml数据的序列化器。
 *
 * @see JacksonYamlSerializer
 * @see SnakeYamlSerializer
 */
interface YamlSerializer : DataSerializer {
	override val dataFormat: DataFormat get() = DataFormat.Yaml

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
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的序列化器。
	 */
	companion object: YamlSerializer by defaultYamlSerializer
}
