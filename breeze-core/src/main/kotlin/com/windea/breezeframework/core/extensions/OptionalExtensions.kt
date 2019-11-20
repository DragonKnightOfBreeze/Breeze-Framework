@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import java.util.*

/**如果当前Optional对象为空，则返回null，否则返回它的值。*/
inline fun <T> Optional<T>.orNull(): T? = this.orElse(null)
