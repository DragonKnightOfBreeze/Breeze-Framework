// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("OptionalExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extension

import java.util.*

/**如果当前Optional对象为空，则返回null，否则返回它的值。*/
inline fun <T> Optional<T>.orNull(): T? = this.orElse(null)

/**从一个可空对象创建Optional对象。*/
@Deprecated("It is not recommended to use Optional objects in Kotlin directly.", level = DeprecationLevel.HIDDEN)
inline fun <T> T.toOptional(): Optional<T> = Optional.ofNullable(this)
