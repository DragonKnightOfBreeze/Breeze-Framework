/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

@file:JvmName("TodoExtensions")
@file:Suppress("FunctionName", "NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.*

/**方法推迟实现时使用到的错误。这个错误不会在对应的TODO方法中被抛出。*/
class DelayImplementedError(message: String = "An operation is delay implemented.") : Error(message)

/**表明一个操作推迟了实现。*/
@TodoMarker
@InlineOnly
@JvmSynthetic
inline fun DELAY() {
	printTodo(DelayImplementedError(), 32)
}

/**表明一个方法体推迟了实现，并指定原因。*/
@TodoMarker
@InlineOnly
@JvmSynthetic
inline fun DELAY(reason: String) {
	printTodo(DelayImplementedError("An operation is delay implemented: $reason"), 32)
}

/**表明一个操作推迟了实现。返回模拟结果。*/
@TodoMarker
@InlineOnly
@JvmSynthetic
inline fun <T> DELAY(lazyDummyResult: () -> T): T {
	printTodo(DelayImplementedError(), 32)
	return lazyDummyResult()
}

/**表明一个方法体推迟了实现，并指定原因。返回模拟结果。*/
@TodoMarker
@InlineOnly
@JvmSynthetic
inline fun <T> DELAY(reason: String, lazyDummyResult: () -> T): T {
	printTodo(DelayImplementedError("An operation is delay implemented: $reason"), 32)
	return lazyDummyResult()
}


/**方法存在问题时使用到的错误。这个错误不会在对应的TODO方法中被抛出。*/
class ImplementedWithAnIssueError(message: String = "An operation is implemented with an issue.") : Error(message)

/**表明一个方法体中存在问题。*/
@TodoMarker
@InlineOnly
@JvmSynthetic
inline fun FIXME() {
	printTodo(ImplementedWithAnIssueError(), 33)
}

/**表明一个方法体中存在问题，并指明原因。*/
@TodoMarker
@InlineOnly
@JvmSynthetic
inline fun FIXME(message: String) {
	printTodo(ImplementedWithAnIssueError("An operation is implemented with an issue: $message"), 33)
}


@PublishedApi
internal fun printTodo(throwable: Throwable, colorCode: Int) {
	println()
	throwable.message?.let { println("\u001B[${colorCode}m$it\u001B[0m") }
	throwable.printStackTrace()
}
