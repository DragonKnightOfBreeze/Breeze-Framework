/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

@file:JvmName("ResultExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

//需要在每个内联方法上抑制这个编译错误

/**
 * Prints the original encapsulated [Throwable] exception and its backtrace to the standard error stream
 * if this instance represents failure.
 */
@Suppress("RESULT_CLASS_IN_RETURN_TYPE")
inline fun <T> Result<T>.andPrintStackTrace(): Result<T> = this.onFailure { it.printStackTrace() }

/**
 * Prints the encapsulated value to the standard output stream
 * if this instance represents success.
 */
@Suppress("RESULT_CLASS_IN_RETURN_TYPE")
inline fun <T> Result<T>.andPrint(): Result<T> = this.onSuccess { print(it) }

/**
 * Prints the encapsulated value and the line separator to the standard output stream
 * if this instance represents success.
 */
@Suppress("RESULT_CLASS_IN_RETURN_TYPE")
inline fun <T> Result<T>.andPrintln(): Result<T> = this.onSuccess { println(it) }
