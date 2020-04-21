@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.serializer

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.serializer.impl.*
import kotlinx.serialization.*
import java.io.*

//如果使用KotlinxSerialization，则需要委托给特定的方法
//如果直接调用typeOf<T>()，会导致NotImplementedError，因此需要调用使用javaTypeOf<T>()

/**从指定字符串读取指定类型的数据。*/
@OptIn(ImplicitReflectionSerializer::class)
inline fun <reified T : Any> Serializer.read(string: String): T {
	return when(this) {
		is KotlinSerializer<*> -> delegate.parse(string)
		else -> read(string, javaTypeOf<T>())
	}
}

/**从指定文件读取指定类型的数据。*/
@OptIn(ImplicitReflectionSerializer::class)
inline fun <reified T : Any> Serializer.read(file: File): T {
	return when(this) {
		is KotlinSerializer<*> -> delegate.parse(file.readText())
		else -> read(file, javaTypeOf<T>())
	}
}

//这些方法是为了便于进行序列化和反序列化而定义的，但是可能会在一定程度上污染代码提示

/**序列化当前对象，返回序列化后的字符串。*/
inline fun <T : Any> T.serialize(dataFormat:DataFormat): String {
	return dataFormat.serializer.write(this)
}

/**序列化当前对象，将序列化后的字符串写入指定文件。*/
inline fun <T : Any> T.serialize(dataFormat:DataFormat, file: File) {
	dataFormat.serializer.write(this, file)
}

/**反序列化当前字符串，返回指定泛型的对象。*/
inline fun <reified T : Any> String.deserialize(dataFormat:DataFormat): T {
	return dataFormat.serializer.read(this)
}

/**反序列化当前文件中文本，返回指定泛型的对象。*/
inline fun <reified T : Any> File.deserialize(dataFormat:DataFormat): T {
	return dataFormat.serializer.read(this)
}
