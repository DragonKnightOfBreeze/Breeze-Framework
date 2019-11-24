@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import java.util.*

/**
 * 如果当前Optional对象为空，则返回null，否则返回它的值。
 *
 * 由于Kotlin拥有优秀的可空类型处理系统，不建议在Kotlin中直接使用Optional对象，
 * 因此，可通过该扩展将Optional对象转化为可空对象。
 */
inline fun <T> Optional<T>.orNull(): T? = this.orElse(null)
