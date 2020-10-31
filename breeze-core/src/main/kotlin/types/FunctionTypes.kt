// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.types

/**
 * 代表有一个没有接收者的代码块。
 *
 * Represents a code block with no receivers.
 */
typealias Block0 = () -> Unit

/**
 * 代表一个有一个接收者的代码块。
 *
 * Represents a code block with one receiver.
 */
typealias Block<T> = T.() -> Unit

/**
 * 代表一个没有参数的代码块。
 *
 * Represents a action with no arguments.
 */
typealias Action0 = () -> Unit

/**
 * 代表一个有一个参数的代码块。
 *
 * Represents a action with one argument.
 */
typealias Action<T> = (T) -> Unit
