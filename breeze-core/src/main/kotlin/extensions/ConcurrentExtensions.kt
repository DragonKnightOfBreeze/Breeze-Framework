@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extensions

import java.util.concurrent.*
import java.util.concurrent.atomic.*
import java.util.concurrent.locks.*

/**构建一个空的线程安全的并发列表。*/
inline fun <T> concurrentListOf(): CopyOnWriteArrayList<T> = CopyOnWriteArrayList()

/**构建一个线程安全的并发列表。*/
fun <T> concurrentListOf(vararg elements: T): CopyOnWriteArrayList<T> = CopyOnWriteArrayList(elements)

/**构建一个空的线程安全的并发集。*/
inline fun <T> concurrentSetOf(): CopyOnWriteArraySet<T> = CopyOnWriteArraySet()

/**构建一个线程安全的并发集。*/
fun <T> concurrentSetOf(vararg elements: T): CopyOnWriteArraySet<T> = CopyOnWriteArraySet(elements.toSet())

/**构建一个空的线程安全的并发映射。*/
inline fun <K, V> concurrentMapOf(): ConcurrentHashMap<K, V> = ConcurrentHashMap()

/**构建一个线程安全的并发映射。*/
fun <K, V> concurrentMapOf(vararg pairs: Pair<K, V>): ConcurrentHashMap<K, V> = ConcurrentHashMap(pairs.toMap())


/**将当前列表转化为新的并发列表。*/
fun <T> List<T>.asConcurrent(): CopyOnWriteArrayList<T> = if(this is CopyOnWriteArrayList) this else CopyOnWriteArrayList(this)

/**将当前集转化为新的并发集。*/
fun <T> Set<T>.asConcurrent(): CopyOnWriteArraySet<T> = if(this is CopyOnWriteArraySet) this else CopyOnWriteArraySet(this)

/**将当前映射转化为新的并发映射。*/
fun <K, V> Map<K, V>.asConcurrent(): ConcurrentMap<K, V> = if(this is ConcurrentMap) this else ConcurrentHashMap(this)


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
