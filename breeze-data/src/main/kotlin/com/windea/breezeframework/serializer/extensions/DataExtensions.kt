@file:JvmName("DataExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.serializer.extensions

import com.windea.breezeframework.serializer.*
import java.io.*

/**序列化当前对象，返回序列化后的字符串。*/
inline fun <T> T.serialize(dataType: DataType): String {
	return dataType.serializer.write(this)
}

/**序列化当前对象，将序列化后的字符串写入指定文件。*/
inline fun <T> T.serialize(dataType: DataType, file: File) {
	dataType.serializer.write(this, file)
}

/**反序列化当前字符串，返回指定泛型的对象。*/
inline fun <reified T> String.deserialize(dataType: DataType): T {
	return dataType.serializer.read(this)
}

/**反序列化当前文件中文本，返回指定泛型的对象。*/
inline fun <reified T> File.deserialize(dataType: DataType): T {
	return dataType.serializer.read(this)
}
