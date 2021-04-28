// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("ConcurrentExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package icu.windea.breezeframework.core.extension

import java.util.concurrent.*
import java.util.concurrent.atomic.*
import java.util.concurrent.locks.*

//region Operation Extensions
@JvmSynthetic
inline fun <T> Condition.withAwait(signalAll: Boolean = true, action: () -> T): T {
	this.await()
	try {
		return action()
	} finally {
		if(signalAll) signalAll() else signal()
	}
}

@JvmSynthetic
inline fun <T> Semaphore.withAcquire(permits: Int = 1, action: () -> T): T {
	this.acquire(permits)
	try {
		return action()
	} finally {
		release(permits)
	}
}
//endregion

//region Convert Extensions
/**
 *将当前布尔值转化为原子类型。
 */
fun Boolean.asAtomic() = AtomicBoolean(this)

/**
 * 将当前整数转化为原子类型。
 */
fun Int.asAtomic() = AtomicInteger(this)

/**
 * 将当前长整数转化为原子类型。
 */
fun Long.asAtomic() = AtomicLong(this)

/**
 * 将当前整数数组转化为原子类型。
 */
fun IntArray.asAtomic() = AtomicIntegerArray(this)

/**
 * 将当前长整数数组转化为原子类型。
 */
fun LongArray.asAtomic() = AtomicLongArray(this)

/**
 * 将当前引用转化为原子类型。
 */
fun <T> T.asAtomic() = AtomicReference(this)

/**
 * 将当前引用数组转化为原子类型。
 */
fun <T> Array<out T>.asAtomic() = AtomicReferenceArray(this)
//endregion
