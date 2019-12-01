@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import java.util.*

/**如果当前Optional对象为空，则返回null，否则返回它的值。*/
inline fun <T> Optional<T>.orNull(): T? = this.orElse(null)

/**从一个可空对象创建Optional对象。*/
@Deprecated("不建议在Kotlin中直接使用Optional对象。", level = DeprecationLevel.HIDDEN)
inline fun <T> T?.toOptional(): Optional<T> = Optional.ofNullable(this)
