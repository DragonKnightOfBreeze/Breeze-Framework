package com.windea.breezeframework.data.serializers.properties

import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.reflect.extensions.*
import java.lang.reflect.*
import java.util.*

interface PropertiesSerializer : Serializer {
	/**从指定Java属性对象读取指定类型的数据。*/
	fun <T> load(properties: Properties, type: Class<T>): T
	
	/**从指定Java属性对象读取指定类型的数据。*/
	fun <T> load(properties: Properties, type: Type): T
	
	/**转储所有数据到Java属性对象。*/
	fun <T> dump(data: T, properties: Properties)
	
	companion object {
		val instance: PropertiesSerializer = when {
			checkClassForName("com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper") -> JacksonPropertiesSerializer
			else -> throw IllegalStateException("Please contains at least one data serializer impl in classpath.")
		}
	}
}

interface PropertiesSerializerConfig : SerializerConfig
