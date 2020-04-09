@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import java.util.concurrent.*
import java.util.concurrent.atomic.*
import java.util.concurrent.locks.*

/**(No document.)*/
@JvmSynthetic
inline fun <T> Condition.withAwait(signalAll:Boolean = true, action:() -> T):T {
	this.await()
	try {
		return action()
	} finally {
		if(signalAll) signalAll() else signal()
	}
}

/**(No document.)*/
@JvmSynthetic
inline fun <T> Semaphore.withAcquire(permits:Int = 1, action:() -> T):T {
	this.acquire(permits)
	try {
		return action()
	} finally {
		release(permits)
	}
}


/**(No document.)*/
inline fun Int.toAtomic() = AtomicInteger(this)

/**(No document.)*/
inline fun Long.toAtomic() = AtomicLong(this)

/**(No document.)*/
inline fun IntArray.toAtomic() = AtomicIntegerArray(this)

/**(No document.)*/
inline fun LongArray.toAtomic() = AtomicLongArray(this)

/**(No document.)*/
inline fun <T> T.toAtomic() = AtomicReference(this)

/**(No document.)*/
inline fun <T> Array<out T>.toAtomic() = AtomicReferenceArray(this)
