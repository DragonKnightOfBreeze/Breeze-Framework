@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.data.extensions

import com.windea.breezeframework.data.enums.*
import java.io.*

/**序列化当前对象，返回序列化后的字符串。*/
inline fun <T : Any> T.serialize(dataType: DataType): String {
	return dataType.serializer.dump(this)
}

/**序列化当前对象，将序列化后的字符串写入指定文件。*/
inline fun <T : Any> T.serialize(dataType: DataType, file: File) {
	dataType.serializer.dump(this, file)
}

/**序列化当前对象，将序列化后的字符串写入指定写入器。*/
inline fun <T : Any> T.serialize(dataType: DataType, writer: Writer) {
	return dataType.serializer.dump(this, writer)
}


/**反序列化当前字符串，返回指定泛型的对象。*/
inline fun <reified T : Any> String.deserialize(dataType: DataType): T {
	return dataType.serializer.load(this, T::class.java)
}
/**反序列化当前文件中文本，返回指定泛型的对象。*/
inline fun <reified T : Any> File.deserialize(dataType: DataType): T {
	return dataType.serializer.load(this, T::class.java)
}

/**反序列化当前读取器中文本，返回指定泛型的对象。*/
inline fun <reified T : Any> Reader.deserialize(dataType: DataType): T {
	return dataType.serializer.load(this, T::class.java)
}
