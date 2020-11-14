// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("SerializationExtensions")

package com.windea.breezeframework.serialization.extensions

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.serialization.components.*
import java.lang.reflect.*

/**
 * 根据指定的数据类型，序列化当前对象。
 *
 * 这个方法使用的序列化器可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
 *
 * @see DataType
 * @see Serializer
 */
@BreezeComponentExtension
fun <T:Any> T.serializeBy(dataType: DataType):String{
	return dataType.serialize(this)
}

/**
 * 根据指定的数据类型，反序列化当前文本。
 *
 * 这个方法使用的序列化器可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
 *
 * @see DataType
 * @see Serializer
 */
@BreezeComponentExtension
inline fun <reified T:Any> String.deserializeBy(dataType: DataType):T{
	return dataType.deserialize(this, javaTypeOf<T>())
}

/**
 * 根据指定的数据类型，反序列化当前文本。
 *
 * 这个方法使用的序列化器可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
 *
 * @see DataType
 * @see Serializer
 */
@BreezeComponentExtension
fun <T:Any> String.deserializeBy(dataType: DataType,type:Class<T>):T{
	return dataType.deserialize(this,type)
}

/**
 * 根据指定的数据类型，反序列化当前文本。
 *
 * 这个方法使用的序列化器可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
 *
 * @see DataType
 * @see Serializer
 */
@BreezeComponentExtension
fun <T:Any> String.deserializeBy(dataType: DataType,type: Type):T{
	return dataType.deserialize(this,type)
}
