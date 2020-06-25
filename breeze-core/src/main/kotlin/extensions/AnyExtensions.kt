@file:Suppress("unused")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import java.lang.reflect.*
import kotlin.reflect.*

/**
 * 将当前对象转换为指定类型。如果转化失败，则抛出异常。
 *
 * 注意这个方法不适用于不同泛型的类型，因为它们实际上是同一种类型。
 */
inline fun <reified T> Any?.cast():T = this as T
//if(!isInstance(value)) throw ClassCastException("Value cannot be cast to $qualifiedName") else return value as T

/**
 * 将当前对象安全地转换为指定类型。如果转化失败，则返回null。
 *
 * 注意这个方法不适用于不同泛型的类型，因为它们实际上是同一种类型。
 */
inline fun <reified T> Any?.safeCast():T? = this as? T
//return if(isInstance(value)) value as T else null


inline fun <reified T> Any?.convert():T = TODO()

inline fun <reified T> Any?.safeConvert():T = TODO()

internal object Converters
