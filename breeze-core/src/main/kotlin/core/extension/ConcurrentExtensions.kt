// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("ConcurrentExtensions") @file:Suppress("NOTHING_TO_INLINE")

package icu.windea.breezeframework.core.extension

import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.*
import java.util.concurrent.locks.Condition

//region common extensions
/**
 * 休眠线程。
 * @throws InterruptedException 如果线程被中断。
 */
@Throws(InterruptedException::class)
fun sleep(timeoutMillis: Long) {
	Thread.sleep(timeoutMillis)
}

/**
 * 休眠线程。
 * @throws InterruptedException 如果线程被中断。
 */
@Throws(InterruptedException::class)
fun sleep(timeout: Long, timeUnit: TimeUnit) {
	timeUnit.sleep(timeout)
}

/**
 * 尝试休眠线程。如果线程被中断，则捕获[InterruptedException]，并重置中断状态。
 */
fun trySleep(timeoutMillis: Long) {
	var interrupted = false
	try {
		sleep(timeoutMillis)
	} catch (e: InterruptedException) {
		interrupted = true
	} finally {
		if (interrupted) Thread.currentThread().interrupt()
	}
}

/**
 * 尝试休眠线程。如果线程被中断，则捕获[InterruptedException]，并重置中断状态。
 */
fun trySleep(timeout: Long, timeUnit: TimeUnit) {
	var interrupted = false
	try {
		sleep(timeout, timeUnit)
	} catch (e: InterruptedException) {
		interrupted = true
	} finally {
		if (interrupted) Thread.currentThread().interrupt()
	}
}

//com.google.common.util.concurrent.Uninterruptibles.sleepUninterruptibly

/**
 * 休眠线程，并保证线程不被中断。
 */
fun forceSleep(timeoutMillis: Long) {
	var interrupted = false
	try {
		var remainingMillis = timeoutMillis
		val end = System.currentTimeMillis() + remainingMillis
		while (true) {
			try {
				//TimeUnit.sleep() treats negative timeouts just like zero.
				TimeUnit.MILLISECONDS.sleep(remainingMillis)
				return
			} catch (e: InterruptedException) {
				interrupted = true
				remainingMillis = end - System.currentTimeMillis()
			}
		}
	} finally {
		if (interrupted) Thread.currentThread().interrupt()
	}
}

/**
 * 休眠线程，并保证线程不被中断。
 */
fun forceSleep(timeout: Long, timeUnit: TimeUnit) {
	var interrupted = false
	try {
		var remainingNanos = timeUnit.toNanos(timeout)
		val end = System.nanoTime() + remainingNanos
		while (true) {
			try {
				//TimeUnit.sleep() treats negative timeouts just like zero.
				TimeUnit.NANOSECONDS.sleep(remainingNanos)
				return
			} catch (e: InterruptedException) {
				interrupted = true
				remainingNanos = end - System.nanoTime()
			}
		}
	} finally {
		if (interrupted) Thread.currentThread().interrupt()
	}
}

/**
 * 循环重试一段代码，直到返回true或者达到总计超时时间。如果线程被中断，则继续重试。
 * @return `true`表示重试成功，`false`表示重试超时。
 */
fun retryUntil(intervalMillis: Long, timeoutMillis: Long, action: () -> Boolean): Boolean {
	val startTime = System.currentTimeMillis()
	while (true) {
		if (action()) return true
		val endTime = System.currentTimeMillis()
		if (endTime - startTime >= timeoutMillis) return false
		trySleep(intervalMillis)
	}
}

/**
 * 循环重试一段代码，直到返回true或者达到总计超时时间。如果线程被中断，则继续重试。
 * @return `true`表示重试成功，`false`表示重试超时。
 */
fun retryUntil(interval: Long, timeout: Long, timeUnit: TimeUnit, action: () -> Boolean): Boolean {
	val timeoutMillis = TimeUnit.MILLISECONDS.convert(timeout, timeUnit) //注意被调用的timeUnit是被转化为的timeUnit
	val startTime = System.currentTimeMillis()
	while (true) {
		if (action()) return true
		val endTime = System.currentTimeMillis()
		if (endTime - startTime >= timeoutMillis) return false
		trySleep(interval, timeUnit)
	}
}

/**
 * 循环重试一段代码，直到返回true或者达到重试次数。如果线程被中断，则继续重试。
 * @return `true`表示重试成功，`false`表示重试超时。
 */
fun retryUntilTimes(intervalMillis: Long, times: Int, action: () -> Boolean): Boolean {
	var t = 1
	while (true) {
		if (action()) return true
		if (t++ >= times) return false
		trySleep(intervalMillis)

	}
}

/**
 * 循环重试一段代码，直到返回true或者达到重试次数。如果线程被中断，则继续重试。
 * @return `true`表示重试成功，`false`表示重试超时。
 */
fun retryUntilTimes(interval: Long, timeUnit: TimeUnit, times: Int, action: () -> Boolean): Boolean {
	var t = 1
	while (true) {
		if (action()) return true
		if (t++ >= times) return false
		trySleep(interval, timeUnit)
	}
}

@JvmSynthetic
inline fun <T> Condition.withAwait(signalAll: Boolean = true, action: () -> T): T {
	this.await()
	try {
		return action()
	} finally {
		if (signalAll) signalAll() else signal()
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

//region convert extensions
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
 * 将当前引用转化为原子引用。
 */
fun <T> T.asAtomicReference() = AtomicReference(this)

/**
 * 将当前引用数组转化为原子引用数组。
 */
fun <T> Array<out T>.asAtomicReferenceArray() = AtomicReferenceArray(this)
//endregion
