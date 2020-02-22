@file:JvmName("DataExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.serializer.extensions

import com.windea.breezeframework.serializer.*
import java.io.*

/**序列化当前对象，返回序列化后的字符串。*/
inline fun <T : Any> T.serialize(dataType: DataType): String {
	return dataType.serializer.write(this)
}

/**序列化当前对象，将序列化后的字符串写入指定文件。*/
inline fun <T : Any> T.serialize(dataType: DataType, file: File) {
	dataType.serializer.write(this, file)
}

/**反序列化当前字符串，返回指定泛型的对象。*/
inline fun <reified T : Any> String.deserialize(dataType: DataType): T {
	return dataType.serializer.read(this)
}

/**反序列化当前文件中文本，返回指定泛型的对象。*/
inline fun <reified T : Any> File.deserialize(dataType: DataType): T {
	return dataType.serializer.read(this)
}

///**重新序列化当前字符串，返回重新序列化后的字符串。*/
//inline fun <reified T:Any> String.reserialize(fromDataType: DataType,toDataType: DataType):String{
//	return this.deserialize<T>(fromDataType).serialize(toDataType)
//}
//
///**重新序列化当前文件中的文本，将重新序列化后的字符串写入当前文件。*/
//inline fun <reified T:Any> File.reserialize(fromDataType: DataType,toDataType: DataType){
//	this.deserialize<T>(fromDataType).serialize(toDataType)
//}
