package com.windea.breezeframework.data.serializers

import com.windea.breezeframework.reflect.extensions.*
import java.io.*
import java.lang.reflect.*

//region top interfaces
/**
 * 序列化器。
 *
 * 注意：其实现依赖于第三方库，如`jackson`, `gson`。
 *
 * 注意：当classpath中存在`jackson-module-kotlin`时，可以对数据类进行构造参数绑定。
 */
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
//endregion

//region reified extensions

//NOTE use typeOf<T>() here cause NotImplementedError (Java type is not yet supported for types created with ...)
//NOTE use user-defined javaTypeOf<T>() here instead, reference to jackson's TypeReference implementation.

/**从指定字符串读取指定类型的数据。*/
inline fun <reified T> Serializer.load(string: String): T = load(string, javaTypeOf<T>())

/**从指定文件读取指定类型的数据。*/
inline fun <reified T> Serializer.load(file: File): T = load(file, javaTypeOf<T>())
//endregion
