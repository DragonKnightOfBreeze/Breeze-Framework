// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("PreconditionExtensions") @file:Suppress("NOTHING_TO_INLINE")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.annotation.InlineOnly
import kotlin.contracts.contract

/**如果判定失败，则抛出一个[UnsupportedOperationException]。*/
@InlineOnly
@JvmSynthetic
inline fun accept(value: Boolean) {
	contract {
		returns() implies value
	}
	accept(value) { "Unsupported operation." }
}

/**如果判定失败，则抛出一个[UnsupportedOperationException]，带有懒加载的信息。*/
@InlineOnly
@JvmSynthetic
inline fun accept(value: Boolean, lazyMessage: () -> Any) {
	contract {
		returns() implies value
	}
	if (!value) {
		val message = lazyMessage()
		throw UnsupportedOperationException(message.toString())
	}
}

/**如果判定失败，则抛出一个[UnsupportedOperationException]。*/
@InlineOnly
@JvmSynthetic
inline fun <T> acceptNotNull(value: T?) {
	contract {
		returns() implies (value != null)
	}
	acceptNotNull(value) { "Unsupported operation." }
}

/**如果判定失败，则抛出一个[UnsupportedOperationException]，带有懒加载的信息。*/
@InlineOnly
@JvmSynthetic
inline fun <T> acceptNotNull(value: T?, lazyMessage: () -> Any): T {
	contract {
		returns() implies (value != null)
	}
	if (value == null) {
		val message = lazyMessage()
		throw UnsupportedOperationException(message.toString())
	} else {
		return value
	}
}


/**判断指定名字的Class是否出现在classpath中并且可加载。*/
fun presentInClassPath(className: String): Boolean {
	return runCatching { Class.forName(className) }.isSuccess
}
