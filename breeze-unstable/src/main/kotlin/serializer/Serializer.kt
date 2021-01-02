// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serializer

import java.io.*
import java.lang.reflect.*

/**
 * 序列化器。
 *
 * 序列化器用于将数据序列化为指定的数据格式，或者从指定的数据格式反序列化为数据。
 *
 * 序列化器的实现默认由`breeze-mapper`提供，但也可以依赖于第三方库，如`jackson`，`kotlinx-serialization`，`gson`等。
 * 如果其实现为`jackson`，当classpath中存在`jackson-module-kotlin`时，可以对数据类进行构造参数绑定。
 */
interface Serializer {
	/**从指定字符串读取指定类型的数据。*/
	fun <T : Any> read(string: String, type: Class<T>): T

	/**从指定字符串读取指定类型的数据。*/
	fun <T : Any> read(string: String, type: Type): T

	/**从指定文件读取指定类型的数据。*/
	fun <T : Any> read(file: File, type: Class<T>): T

	/**从指定文件读取指定类型的数据。*/
	fun <T : Any> read(file: File, type: Type): T

	/**写入数据到字符串。*/
	fun <T : Any> write(data: T): String

	/**写入数据到文件。默认以易读格式输出。*/
	fun <T : Any> write(data: T, file: File)
}
