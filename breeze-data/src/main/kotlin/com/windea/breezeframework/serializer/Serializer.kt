package com.windea.breezeframework.serializer

import java.io.*
import java.lang.reflect.*

/**
 * 序列化器。
 *
 * * 其实现依赖于第三方库，如`jackson`，`gson`，`kotlinx-serialization`等。
 * * 如果其实现为`jackson`，当classpath中存在`jackson-module-kotlin`时，可以对数据类进行构造参数绑定。
 */
interface Serializer {
	/**从指定字符串读取指定类型的数据。*/
	fun <T> read(string: String, type: Class<T>): T

	/**从指定字符串读取指定类型的数据。*/
	fun <T> read(string: String, type: Type): T

	/**从指定文件读取指定类型的数据。*/
	fun <T> read(file: File, type: Class<T>): T

	/**从指定文件读取指定类型的数据。*/
	fun <T> read(file: File, type: Type): T

	/**转储数据到字符串。*/
	fun <T> write(data: T): String

	/**转储数据到文件。*/
	fun <T> write(data: T, file: File)
}

