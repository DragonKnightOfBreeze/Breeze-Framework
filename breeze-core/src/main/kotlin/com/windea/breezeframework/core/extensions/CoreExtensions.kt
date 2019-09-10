@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.api.*
import mu.*
import org.slf4j.*
import kotlin.contracts.*

private val logger = KotlinLogging.logger {}


/**转化为指定类型，或者抛出异常。用于链式调用。*/
inline fun <reified R> Any?.cast() = this as R

/**转化为指定类型，或者返回null。用于链式调用。*/
inline fun <reified R> Any?.castOrNull() = this as? R


/**从二元素元组构造三元素元组。*/
infix fun <A, B, C> Pair<A, B>.and(that: C): Triple<A, B, C> = Triple(this.first, this.second, that)

/**取在指定范围内的夹值。*/
infix fun <T : Comparable<T>> T.clamp(range: ClosedRange<T>): T = this.coerceIn(range)


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
