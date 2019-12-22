@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import kotlin.contracts.*

//region Standard.kt extensions (Todo functions)
/**表明一个操作推迟了实现。*/
inline fun DELAY() = DELAY { Unit }

/**表明一个方法体推迟了实现，并指定原因。*/
inline fun DELAY(reason: String) = DELAY(reason) { Unit }

/**表明一个操作推迟了实现。返回模拟结果。*/
inline fun <T> DELAY(lazyDummyResult: () -> T): T = lazyDummyResult().also {
	println("An operation is delay implemented.".let { "\u001B[33m$it\u001B[0m" })
	println("Location: $currentClassFullName".let { "\u001B[33m$it\u001B[0m" })
}

/**表明一个方法体推迟了实现，并指定原因。返回模拟结果。*/
inline fun <T> DELAY(reason: String, lazyDummyResult: () -> T): T = lazyDummyResult().also {
	println("An operation is delay implemented: $reason".let { "\u001B[33m$it\u001B[0m" })
	println("Location: $currentClassFullName".let { "\u001B[33m$it\u001B[0m" })
}

/**表明一个方法体中存在问题。*/
inline fun FIXME() = run {
	println("An operation has an unresolved issue.".let { "\u001B[91m$it\u001B[0m" })
	println("Location: $currentClassFullName".let { "\u001B[91m$it\u001B[0m" })
}

/**表明一个方法体中存在问题，并指明原因。*/
inline fun FIXME(message: String) = run {
	println("An operation has an unresolved issue: $message".let { "\u001B[91m$it\u001B[0m" })
	println("Location: $currentClassFullName".let { "\u001B[91m$it\u001B[0m" })
}

@PublishedApi internal inline val currentClassFullName get() = Exception().stackTrace[0].className
//endregion

//region Standard.kt extensions (Scope functions)
/**当满足条件时，以接收者为代码体的接收者，执行一段代码并返回转化后的结果，否则返回自身。*/
inline fun <T> T.run(condition: Boolean, block: T.() -> T): T {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	return if(condition) this.block() else this
}

/**当满足条件时，以接收者为代码体的参数，执行一段代码并返回转化后的结果，否则返回自身。*/
inline fun <T> T.let(condition: Boolean, block: (T) -> T): T {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	return if(condition) block(this) else this
}

/**尝试执行一段代码，并在发生异常时打印堆栈信息。*/
inline fun tryOrPrint(block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	try {
		block()
	} catch(e: Exception) {
		e.printStackTrace()
	}
}

/**尝试执行一段代码，并忽略异常。*/
inline fun tryOrIgnore(block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	try {
		block()
	} catch(e: Exception) {
	}
}

@PublishedApi internal var enableOnce = false

/**执行一段代码且仅执行一次。可指定是否重置单次状态。*/
inline fun once(resetStatus: Boolean = false, block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.AT_MOST_ONCE)
	}
	if(resetStatus) enableOnce = false
	if(enableOnce) return
	enableOnce = true
	block()
}
//endregion

//region Precondition.kt extensions
/**如果判定失败，则抛出一个[UnsupportedOperationException]。*/
inline fun accept(value: Boolean) {
	contract {
		returns() implies value
	}
	accept(value) { "Unsupported operation." }
}

/**如果判定失败，则抛出一个[UnsupportedOperationException]，带有懒加载的信息。*/
inline fun accept(value: Boolean, lazyMessage: () -> Any) {
	contract {
		returns() implies value
	}
	if(!value) {
		val message = lazyMessage()
		throw UnsupportedOperationException(message.toString())
	}
}

/**如果判定失败，则抛出一个[UnsupportedOperationException]。*/
inline fun <T> acceptNotNull(value: T?) {
	contract {
		returns() implies (value != null)
	}
	acceptNotNull(value) { "Unsupported operation." }
}

/**如果判定失败，则抛出一个[UnsupportedOperationException]，带有懒加载的信息。*/
inline fun <T> acceptNotNull(value: T?, lazyMessage: () -> Any): T {
	contract {
		returns() implies (value != null)
	}
	if(value == null) {
		val message = lazyMessage()
		throw UnsupportedOperationException(message.toString())
	} else {
		return value
	}
}
//endregion
