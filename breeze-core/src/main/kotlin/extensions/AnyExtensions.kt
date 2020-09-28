/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

@file:JvmName("AnyExtensions")
@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.domain.*

//真的可以这么做

/**
 * 得到当前对象的带有泛型参数信息的Java类型对象。
 */
inline val <reified T> T.javaType get() = object : TypeReference<T>() {}.type


//注意cast方法不适用于不同泛型的类型，因为它们实际上是同一种类型。

/**
 * 将当前对象转换为指定类型。如果转换失败，则抛出异常。
 */
inline fun <reified T> Any?.cast(): T {
	return this as T
}

/**
 * 将当前对象转换为指定类型。如果转换失败，则返回null。
 */
inline fun <reified T> Any?.castOrNull():T? {
	return this as? T
}


//注意convert方法仅支持部分类型，但可以自行扩展

/**
 * 将当前对象转化为指定类型。如果转换失败，则抛出异常。转化后的对象是基于一般转化逻辑得到的新对象。
 */
inline fun <reified T> Any?.convert(): T {
	return if(this is T) this else ConverterService.convert(this)
}

/**
 * 将当前对象转化为指定类型。如果转换失败，则返回null。转化后的对象是基于一般转化逻辑得到的新对象。
 */
inline fun <reified T> Any?.convertOrNull():T? {
	return if(this is T) this else ConverterService.convertOrNull(this)
}


/**
 * 如果满足条件，则根据指定的转化方法，转化当前对象，否则不做处理
 */
inline fun <T> T.where(condition: Boolean, transform: (T) -> T): T {
	return if(condition) transform(this) else this
}


inline fun <T:Any> T.toBreeze(): Breeze<T> {
	return Breeze(this)
}
