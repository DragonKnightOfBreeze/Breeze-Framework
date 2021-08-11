// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.type

import icu.windea.breezeframework.core.annotation.*

/**
 * 代表有一个没有接收者的代码块。
 *
 * Represents a code block with no receivers.
 */
@UnstableApi
typealias Block0 = () -> Unit

/**
 * 代表一个有一个接收者的代码块。
 *
 * Represents a code block with one receiver.
 */
@UnstableApi
typealias Block<T> = T.() -> Unit

/**
 * 代表一个没有参数的代码块。
 *
 * Represents a action with no arguments.
 */
@UnstableApi
typealias Action0 = () -> Unit

/**
 * 代表一个有一个参数的代码块。
 *
 * Represents a action with one argument.
 */
@UnstableApi
typealias Action<T> = (T) -> Unit
