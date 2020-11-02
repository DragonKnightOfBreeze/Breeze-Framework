// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serializer.impl.properties

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.mapper.impl.*
import com.windea.breezeframework.serializer.*
import java.lang.reflect.*
import java.util.*
import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper as JacksonPropertiesMapper

/**
 * Properties的序列化器。
 *
 * 其实现默认由`breeze-mapper`提供，但也可以依赖于第三方库，如`jackson`。
 */
interface PropertiesSerializer : Serializer {
	/**从指定Properties对象读取指定类型的数据。*/
	fun <T : Any> read(properties: Properties, type: Class<T>): T

	/**从指定Properties对象读取指定类型的数据。*/
	fun <T : Any> read(properties: Properties, type: Type): T

	/**写入所有数据到Properties对象。*/
	fun <T : Any> write(data: T, properties: Properties)

	companion object {
		private const val jacksonPropertiesClassName = "com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper"

		/**得到Properties的序列化器的实例。*/
		val instance: PropertiesSerializer = when {
			presentInClassPath(jacksonPropertiesClassName) -> JacksonPropertiesSerializer
			else -> BreezePropertiesSerializer
			//else -> throw IllegalStateException("No supported properties serializer implementation found in classpath.")
		}

		/**配置BreezeProperties的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		@OptionalApi
		fun configureBreezeProperties(block: (PropertiesMapper.Config.Builder) -> Unit) {
			block(BreezePropertiesSerializer.configBuilder)
		}

		/**配置JacksonProperties的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		@OptionalApi
		fun configureJacksonProperties(block: (JacksonPropertiesMapper) -> Unit) {
			block(JacksonPropertiesSerializer.mapper)
		}
	}
}

