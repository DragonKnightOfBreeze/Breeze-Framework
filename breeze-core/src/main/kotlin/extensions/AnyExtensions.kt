@file:Suppress("unused")

package com.windea.breezeframework.core.extensions

import java.util.*

/**
 * 将当前对象转换为单例集合。
 *
 * 支持的类型参数：[Collection] [MutableCollection] [Set] [MutableSet] [List] [MutableList]。
 */
inline fun <T, reified R> T.toSingleton(): R {
	return when(val type = R::class) {
		Collection::class -> Collections.singleton(this)
		MutableCollection::class -> Collections.singleton(this)
		Set::class -> Collections.singleton(this)
		MutableSet::class -> Collections.singleton(this)
		List::class -> Collections.singletonList(this)
		MutableList::class -> Collections.singletonList(this)
		else -> throw UnsupportedOperationException("Unsupported singleton collection type '${type.simpleName}'")
	} as R
}

/**
 * 如果当前对象不为null，则将当前对象转换为单例集合，否则转化为空集合。
 *
 * 支持的类型参数：[Collection] [MutableCollection] [Set] [MutableSet] [List] [MutableList]。
 */
inline fun <T, reified R> T.toSingletonOrEmpty(): R {
	return when(val type = R::class) {
		Collection::class -> if(this == null) Collections.emptySet() else Collections.singleton(this)
		MutableCollection::class -> if(this == null) Collections.emptySet() else Collections.singleton(this)
		Set::class -> if(this == null) Collections.emptySet() else Collections.singleton(this)
		MutableSet::class -> if(this == null) Collections.emptySet() else Collections.singleton(this)
		List::class -> if(this == null) Collections.emptyList() else Collections.singletonList(this)
		MutableList::class -> if(this == null) Collections.emptyList() else Collections.singletonList(this)
		else -> throw UnsupportedOperationException("Unsupported singleton collection type '${type.simpleName}'")
	} as R
}


/**
 * 将当前对象转换为指定类型。如果转换失败，则抛出异常。
 *
 * 注意这个方法不适用于不同泛型的类型，因为它们实际上是同一种类型。
 */
inline fun <reified T> Any?.cast(): T = this as T

/**
 * 将当前对象安全地转换为指定类型。如果转换失败，则返回null。
 *
 * 注意这个方法不适用于不同泛型的类型，因为它们实际上是同一种类型。
 */
inline fun <reified T> Any?.safeCast():T? = this as? T


inline fun <reified T> Any?.convert():T = TODO()

inline fun <reified T> Any?.safeConvert():T = TODO()
