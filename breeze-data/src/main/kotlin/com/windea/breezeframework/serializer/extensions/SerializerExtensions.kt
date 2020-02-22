package com.windea.breezeframework.serializer.extensions

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.serializer.*
import java.io.*

//这里使用typeOf<T>()会导致NotImplementedError，因此需要使用javaTypeOf<T>()

/**从指定字符串读取指定类型的数据。*/
inline fun <reified T> Serializer.read(string: String): T = read(string, javaTypeOf<T>())

/**从指定文件读取指定类型的数据。*/
inline fun <reified T> Serializer.read(file: File): T = read(file, javaTypeOf<T>())
