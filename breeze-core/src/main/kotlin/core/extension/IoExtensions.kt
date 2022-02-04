// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("IoExtensions")

package icu.windea.breezeframework.core.extension

import java.io.Closeable
import java.io.Flushable

/**
 * Unconditionally close a [Closeable].
 *
 * Equivalent to [Closeable.close], except any exceptions will be ignored.
 * This is typically used in finally blocks.
 */
fun Closeable?.closeQuietly() {
	try {
		this?.close()
	} catch (e: Exception) {
		//ignore
	}
}

/**
 * Unconditionally flush a [Flushable].
 *
 * Equivalent to [Flushable.flush], except any exceptions will be ignored.
 * This is typically used in finally blocks.
 */
fun Flushable?.flushQuietly() {
	try {
		this?.flush()
	} catch (e: Exception) {
		//ignore
	}
}
