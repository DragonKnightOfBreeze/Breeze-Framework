// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import java.lang.reflect.*

/**
 * 根据指定的数据类型，序列化当前对象。
 */
@BreezeComponentExtension
fun <T:Any> T.serializeBy(dataType:DataType):String{
	return dataType.serialize(this)
}

/**
 * 根据指定的数据类型，反序列化当前文本。
 */
@BreezeComponentExtension
inline fun <reified T:Any> String.deserializeBy(dataType:DataType):T{
	return dataType.deserialize(this, javaTypeOf<T>())
}

/**
 * 根据指定的数据类型，反序列化当前文本。
 */
@BreezeComponentExtension
fun <T:Any> String.deserializeBy(dataType: DataType,type:Class<T>):T{
	return dataType.deserialize(this,type)
}

/**
 * 根据指定的数据类型，反序列化当前文本。
 */
@BreezeComponentExtension
fun <T:Any> String.deserializeBy(dataType: DataType,type: Type):T{
	return dataType.deserialize(this,type)
}
