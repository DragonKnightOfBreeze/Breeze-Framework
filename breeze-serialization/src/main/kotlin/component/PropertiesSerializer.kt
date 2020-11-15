// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.component

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.serialization.extension.*
import java.lang.reflect.*
import java.util.*

/**
 * Properties的序列化器。
 */
@BreezeComponent
interface PropertiesSerializer : DataSerializer {
	override val dataType: DataType get() = DataType.Properties

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
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的轻量的序列化器。
	 */
	companion object Default: PropertiesSerializer by defaultPropertiesSerializer

	/**
	 * 由Jackson实现的Properties的序列化器。
	 *
	 * @see com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper
	 */
	class JacksonPropertiesSerializer : JacksonSerializer.JacksonPropertiesSerializer()

	/**
	 * 由Breeze Framework实现的轻量的Properties的序列化器。
	 */
	class BreezePropertiesSerializer : BreezeSerializer.BreezePropertiesSerializer()
}
