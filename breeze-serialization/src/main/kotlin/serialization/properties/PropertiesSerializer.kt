// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.properties

import icu.windea.breezeframework.serialization.*
import java.lang.reflect.*
import java.util.*

/**
 * Properties的序列化器。
 *
 * @see BreezePropertiesSerializer
 * @see JacksonPropertiesSerializer
 */
interface PropertiesSerializer : DataSerializer {
	override val dataFormat: DataFormat get() = DataFormats.Properties

	/**
	 * 序列化指定属性对象。
	 */
	fun <T> serializeProperties(target: T): Properties

	/**
	 * 反序列化指定属性对象。
	 */
	fun <T> deserializeProperties(properties: Properties, type: Class<T>): T

	/**
	 * 反序列化指定属性对象。
	 */
	fun <T> deserializeProperties(properties: Properties, type: Type): T

	/**
	 * 默认的Properties的序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
	 */
	companion object Default : PropertiesSerializer by defaultPropertiesSerializer
}
