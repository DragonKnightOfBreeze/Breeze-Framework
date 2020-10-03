// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("ScopeFunctionExtensions")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*
import kotlin.contracts.*

/**
 * Calls the specified function [block] with the given [arg1] and [arg2] as its arguments and returns its result.
 */
@InlineOnly
@JvmSynthetic
inline fun <T1,T2,R> with(arg1:T1,arg2:T2,block:(T1,T2) -> R):R{
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	return block(arg1,arg2)
}

/**
 * Calls the specified function [block] with the given [arg1], [arg2] and [arg3] as its arguments and returns its result.
 */
@InlineOnly
@JvmSynthetic
inline fun <T1,T2,T3,R> with(arg1:T1,arg2:T2,arg3:T3,block:(T1,T2,T3) -> R):R{
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	return block(arg1,arg2,arg3)
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

/**
 * Calls the specified function [block] only once,
 * if the [resetStatus] returns `true`, reset the once status.
 */
@InlineOnly
@JvmSynthetic
inline fun once(resetStatus: Boolean = false, block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	if(resetStatus) enableOnce = false
	if(enableOnce) return
	enableOnce = true
	block()
}

@PublishedApi internal var enableOnce = false
