// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("SerializationExtensions")

package com.windea.breezeframework.serialization.extension

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.serialization.*
import com.windea.breezeframework.serialization.serializer.*
import java.lang.reflect.*

/**
 * 根据指定的序列化器，序列化当前对象。
 *
 * @see Serializer
 */
@BreezeComponentExtension
fun <T,V> T.serializeBy(serializer:Serializer<V>): V {
	return serializer.serialize(this)
}

/**
 * 根据指定的序列化器，反序列化当前格式。
 *
 * @see Serializer
 */
@BreezeComponentExtension
inline fun <reified T,V> V.deserializeBy(serializer: Serializer<V>):T{
	return serializer.deserialize(this,javaTypeOf<T>())
}

/**
 * 根据指定的序列化器，反序列化当前格式。
 *
 * @see Serializer
 */
@BreezeComponentExtension
fun <T,V> V.deserializeBy(serializer: Serializer<V>,type:Class<T>):T{
	return serializer.deserialize(this,type)
}

/**
 * 根据指定的序列化器，反序列化当前格式。
 *
 * @see Serializer
 */
@BreezeComponentExtension
fun <T,V> V.deserializeBy(serializer: Serializer<V>,type:Type):T{
	return serializer.deserialize(this,type)
}


/**
 * 根据指定的数据类型，序列化当前对象。
 *
 * 这个方法使用的序列化器可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的轻量的序列化器。
 *
 * @see DataFormat
 */
@BreezeComponentExtension
fun <T> T.serializeDataBy(dataFormat: DataFormat):String{
	return dataFormat.serialize(this)
}

/**
 * 根据指定的数据类型，反序列化当前文本。
 *
 * 这个方法使用的序列化器可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的轻量的序列化器。
 *
 * @see DataFormat
 */
@BreezeComponentExtension
inline fun <reified T> String.deserializeDataBy(dataFormat: DataFormat):T{
	return dataFormat.deserialize(this, javaTypeOf<T>())
}

/**
 * 根据指定的数据类型，反序列化当前文本。
 *
 * 这个方法使用的序列化器可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的轻量的序列化器。
 *
 * @see DataFormat
 */
@BreezeComponentExtension
fun <T> String.deserializeDataBy(dataFormat: DataFormat,type:Class<T>):T{
	return dataFormat.deserialize(this,type)
}

/**
 * 根据指定的数据类型，反序列化当前文本。
 *
 * 这个方法使用的序列化器可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的轻量的序列化器。
 *
 * @see DataFormat
 */
@BreezeComponentExtension
fun <T> String.deserializeDataBy(dataFormat: DataFormat,type: Type):T{
	return dataFormat.deserialize(this,type)
}
