package com.windea.breezeframework.serializer.impl.properties

import com.fasterxml.jackson.dataformat.javaprop.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.serializer.*
import java.lang.reflect.*
import java.util.*

/**
 * Properties的序列化器。
 *
 * 注意：其实现依赖于第三方库，如`jackson`。
 */
interface PropertiesSerializer : Serializer {
	/**从指定Java属性对象读取指定类型的数据。*/
	fun <T> load(properties: Properties, type: Class<T>): T

	/**从指定Java属性对象读取指定类型的数据。*/
	fun <T> load(properties: Properties, type: Type): T

	/**转储所有数据到Java属性对象。*/
	fun <T> dump(data: T, properties: Properties)

	companion object {
		private const val jacksonPropertiesClassName = "com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper"

		/**得到Properties的序列化器的实例。*/
		val instance: PropertiesSerializer = when {
			presentInClassPath(jacksonPropertiesClassName) -> JacksonPropertiesSerializer
			else -> throw IllegalStateException("No supported properties serializer implementation found in classpath.")
		}


		/**配置JacksonProperties的序列化器。*/
		fun configureJacksonProperties(block: (JavaPropsMapper) -> Unit) {
			block(JacksonPropertiesSerializer.mapper)
		}
	}
}

