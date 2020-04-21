package com.windea.breezeframework.core.extensions

/**将当前对象转换为指定类型。如果转化失败，则抛出异常。注意这个方法不适用于不同泛型的类型，因为它们实际上是同一种类型。*/
inline fun <reified R> Any?.cast():R = this as R

/**将当前对象安全地转换为指定类型。如果转化失败，则返回null。注意这个方法不适用于不同泛型的类型，因为它们实际上是同一种类型。*/
inline fun <reified R> Any?.safeCast():R? = this as? R
