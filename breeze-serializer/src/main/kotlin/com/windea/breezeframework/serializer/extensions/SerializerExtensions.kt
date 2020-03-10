package com.windea.breezeframework.serializer.extensions

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.serializer.Serializer
import com.windea.breezeframework.serializer.impl.*
import kotlinx.serialization.*
import java.io.*

//如果使用KotlinxSerialization，则需要委托给特定的方法
//如果直接调用typeOf<T>()，会导致NotImplementedError，因此需要调用使用javaTypeOf<T>()

/**从指定字符串读取指定类型的数据。*/
inline fun <reified T : Any> Serializer.read(string: String): T {
	return when(this) {
		is KotlinSerializer<*> -> readForKotlinSerializer<T>(string)
		else -> read(string, javaTypeOf<T>())
	}
}

/**从指定文件读取指定类型的数据。*/
inline fun <reified T : Any> Serializer.read(file: File): T {
	return when(this) {
		is KotlinSerializer<*> -> readForKotlinSerializer<T>(file.readText())
		else -> read(file, javaTypeOf<T>())
	}
}

@PublishedApi
@OptIn(ImplicitReflectionSerializer::class)
internal inline fun <reified T : Any> KotlinSerializer<*>.readForKotlinSerializer(string: String): T {
	return this.delegate.parse(string)
}