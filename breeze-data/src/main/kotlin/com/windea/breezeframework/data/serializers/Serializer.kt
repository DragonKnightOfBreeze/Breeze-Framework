package com.windea.breezeframework.data.serializers

import java.io.*
import java.lang.reflect.*
import kotlin.reflect.*
import kotlin.reflect.jvm.*

//DONE 允许读取指定泛型类型的数据
//TODO 考虑编写自己的简洁而灵活的实现
//TODO 考虑使用扩展库`kotlinx-serialization`，但是缺少具体的对于yaml、xml等格式的实现

//REGION top interfaces

/**序列化器。其实现依赖于第三方库，如Gson。需要将必要的实现库添加到classpath中。*/
interface Serializer {
	/**从指定字符串读取指定类型的数据。*/
	fun <T> load(string: String, type: Class<T>): T
	
	/**从指定文件读取指定类型的数据。*/
	fun <T> load(file: File, type: Class<T>): T
	
	/**从指定字符串读取指定类型的数据。*/
	fun <T> load(string: String, type: Type): T
	
	/**从指定文件读取指定类型的数据。*/
	fun <T> load(file: File, type: Type): T
	
	/**转储数据到字符串。*/
	fun <T> dump(data: T): String
	
	/**转储数据到文件。*/
	fun <T> dump(data: T, file: File)
}

/**序列化器的配置。*/
interface SerializerConfig

//REGION reified extensions

/**从指定字符串读取指定类型的数据。*/
inline fun <reified T> Serializer.load(string: String): T {
	val javaType = T::class.java
	return when {
		javaType.typeParameters.isEmpty() -> load(string, javaType)
		else -> load(string, typeOf<T>().javaType)
	}
}

/**从指定文件读取指定类型的数据。*/
inline fun <reified T> Serializer.load(file: File): T {
	val javaType = T::class.java
	return when {
		javaType.typeParameters.isEmpty() -> load(file, javaType)
		else -> load(file, typeOf<T>().javaType)
	}
}
