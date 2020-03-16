package com.windea.breezeframework.core.extensions

import java.util.concurrent.*
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
