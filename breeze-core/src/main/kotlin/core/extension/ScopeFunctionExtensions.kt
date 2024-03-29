// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("ScopeFunctionExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.annotation.*
import kotlin.contracts.*

@InlineOnly
@JvmSynthetic
inline fun pass() {

}

@InlineOnly
@JvmSynthetic
inline fun <T> T.pass(): T {
	return this
}

/**
 * Calls the specified function [block] with the given [arg1] and [arg2] as its arguments and returns its result.
 */
@InlineOnly
@JvmSynthetic
inline fun <T1, T2, R> with(arg1: T1, arg2: T2, block: (T1, T2) -> R): R {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	return block(arg1, arg2)
}

/**
 * Calls the specified function [block] with the given [arg1], [arg2] and [arg3] as its arguments and returns its result.
 */
@InlineOnly
@JvmSynthetic
inline fun <T1, T2, T3, R> with(arg1: T1, arg2: T2, arg3: T3, block: (T1, T2, T3) -> R): R {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	return block(arg1, arg2, arg3)
}

/**
 * If the [condition] returns `true`,
 * calls the specified function [block] with `this` value as its argument and returns its result.
 *
 * If not, returns `this` value.
 */
@InlineOnly
@JvmSynthetic
inline fun <T : R, R> T.where(condition: Boolean, block: (T) -> R): R {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	return if(condition) block(this) else this
}
