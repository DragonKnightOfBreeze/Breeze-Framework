// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("ResultExtensions")
@file:Suppress("NOTHING_TO_INLINE","RESULT_CLASS_IN_RETURN_TYPE")

package icu.windea.breezeframework.core.extension

/**
 * Prints the original encapsulated [Throwable] exception and its backtrace to the standard error stream
 * if this instance represents failure.
 */
inline fun <T> Result<T>.andPrintStackTrace(): Result<T> = this.onFailure { it.printStackTrace() }

/**
 * Prints the encapsulated value to the standard output stream
 * if this instance represents success.
 */
inline fun <T> Result<T>.andPrint(): Result<T> = this.onSuccess { print(it) }

/**
 * Prints the encapsulated value and the line separator to the standard output stream
 * if this instance represents success.
 */
inline fun <T> Result<T>.andPrintln(): Result<T> = this.onSuccess { println(it) }
