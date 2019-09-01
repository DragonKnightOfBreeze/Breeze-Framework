@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import kotlin.contracts.*

/**取在指定范围内的夹值。*/
infix fun <T : Comparable<T>> T.clamp(range: ClosedRange<T>): T {
	return this.coerceIn(range)
}

/**从二元素元组构造三元素元组。*/
infix fun <A, B, C> Pair<A, B>.with(third: C): Triple<A, B, C> {
	return Triple(this.first, this.second, third)
}


/**如果判定失败，则抛出一个[UnsupportedOperationException]。*/
@ExperimentalContracts
inline fun reject(value: Boolean) {
	contract {
		returns() implies value
	}
	reject(value) { "Unsupported operation." }
}

/**如果判定失败，则抛出一个[UnsupportedOperationException]，带有懒加载的信息。*/
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
