@file:Suppress("NOTHING_TO_INLINE", "FunctionName")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import mu.*
import org.slf4j.*
import kotlin.contracts.*

private val logger = KotlinLogging.logger {}

//REGION Global extensions

/**转化为指定类型，或者抛出异常。用于链式调用。*/
inline fun <reified R> Any?.cast(): R = this as R

/**转化为指定类型，或者返回null。用于链式调用。*/
inline fun <reified R> Any?.castOrNull(): R? = this as? R

//REGION Standard.kt extensions

/**表明一个方法体推迟了实现。*/
@OutlookImplementationApi
inline fun DELAY() = Unit

/**返回一个模拟结果，以表明一个方法体推迟了实现。*/
@OutlookImplementationApi
inline fun <reified T> DELAY(lazyDummyResult: () -> T): T = lazyDummyResult()
	.also { nearestLogger().warn("An operation is delay-implemented.") }

/**返回一个模拟结果，以表明一个方法体推迟了实现，并指定原因。*/
@OutlookImplementationApi
inline fun <reified T> DELAY(reason: String, lazyDummyResult: () -> T): T = lazyDummyResult()
	.also { nearestLogger().warn("An operation is delay-implemented: $reason") }


/**打印一段消息，以表明一个方法体中存在问题。*/
@OutlookImplementationApi
inline fun FIXME() = run { nearestLogger().warn("There is an issue in this operation.") }

/**打印一段消息，以表明一个方法体中存在问题，并指明原因。*/
@OutlookImplementationApi
inline fun FIXME(message: String) = run { nearestLogger().warn("There is an issue in this operation: $message") }


@PublishedApi internal var enableOnce = false

/**执行且仅执行一次操作。可指定是否重置单次状态。*/
@ExperimentalContracts
inline fun once(resetStatus: Boolean = false, block: () -> Unit) {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	if(resetStatus) enableOnce = false
	if(enableOnce) return
	enableOnce = true
	block()
}

//REGION Precondition.kt extensions

/**如果判定失败，则抛出一个[UnsupportedOperationException]。*/
@OutlookImplementationApi
@ExperimentalContracts
inline fun reject(value: Boolean) {
	contract {
		returns() implies value
	}
	reject(value) { "Unsupported operation." }
}

/**如果判定失败，则抛出一个[UnsupportedOperationException]，带有懒加载的信息。*/
@OutlookImplementationApi
@ExperimentalContracts
inline fun reject(value: Boolean, lazyMessage: () -> Any) {
	contract {
		returns() implies value
	}
	if(!value) {
		val message = lazyMessage()
		throw UnsupportedOperationException(message.toString())
	}
}


/**得到最近的堆栈追踪信息。即，得到最近一个内联方法的调用处的信息。*/
@PublishedApi
internal inline fun nearestStackInfo(): StackTraceElement {
	try {
		throw RuntimeException()
	} catch(e: RuntimeException) {
		return e.stackTrace[0]
	}
}

/**得到最近的日志对象。即，得到最近一个内联方法的调用处的日志对象。显示的行数可能不正确。*/
@PublishedApi
internal inline fun nearestLogger(): Logger {
	return KotlinLogging.logger(nearestStackInfo().toString())
}
