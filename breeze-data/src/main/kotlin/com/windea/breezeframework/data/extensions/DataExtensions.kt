@file:JvmName("DataExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.data.extensions

import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.serializers.*
import java.io.*

//region serialize extensions
/**序列化当前对象，返回序列化后的字符串。*/
inline fun <T> T.serialize(dataType: DataType): String {
	return dataType.serializer.dump(this)
}

/**序列化当前对象，将序列化后的字符串写入指定文件。*/
inline fun <T> T.serialize(dataType: DataType, file: File) {
	dataType.serializer.dump(this, file)
}

/**反序列化当前字符串，返回指定泛型的对象。*/
inline fun <reified T> String.deserialize(dataType: DataType): T {
	return dataType.serializer.load(this)
}

/**反序列化当前文件中文本，返回指定泛型的对象。*/
inline fun <reified T> File.deserialize(dataType: DataType): T {
	return dataType.serializer.load(this)
}
//endregion
