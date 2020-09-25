/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

@file:JvmName("ConcurrentExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import java.util.concurrent.*
import java.util.concurrent.atomic.*
import java.util.concurrent.locks.*
import kotlin.concurrent.*

/**将当前整数转化为原子类型。*/
inline fun Int.asAtomic() = AtomicInteger(this)

/**将当前长整数转化为原子类型。*/
inline fun Long.asAtomic() = AtomicLong(this)

/**将当前整数数组转化为原子类型。*/
inline fun IntArray.asAtomic() = AtomicIntegerArray(this)

/**将当前长整数数组转化为原子类型。*/
inline fun LongArray.asAtomic() = AtomicLongArray(this)

/**将当前引用转化为原子类型。*/
inline fun <T> T.asAtomic() = AtomicReference(this)

/**将当前引用数组转化为原子类型。*/
inline fun <T> Array<out T>.asAtomic() = AtomicReferenceArray(this)


@JvmSynthetic
inline fun <T> Condition.withAwait(signalAll:Boolean = true, action:() -> T):T {
	this.await()
	try {
		return action()
	} finally {
		if(signalAll) signalAll() else signal()
	}
}

@JvmSynthetic
inline fun <T> Semaphore.withAcquire(permits:Int = 1, action:() -> T):T {
	this.acquire(permits)
	try {
		return action()
	} finally {
		release(permits)
	}
}
