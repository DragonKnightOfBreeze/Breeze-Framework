// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.serialization.*
import com.windea.breezeframework.serialization.extension.*
import java.lang.reflect.*
import java.util.*

/**
 * Properties的序列化器。
 *
 * @see JacksonPropertiesSerializer
 * @see BreezePropertiesSerializer
 */
@BreezeComponent
interface PropertiesSerializer : DataSerializer {
	override val dataFormat: DataFormat get() = DataFormat.Properties

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
}
